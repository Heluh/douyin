package douyin.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import douyin.annotation.IgnoreAuth;
import douyin.annotation.LoginUser;
import douyin.entity.UserEntity;
import douyin.service.VideoLikeService;
import douyin.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import douyin.entity.VideoEntity;
import douyin.entity.view.VideoView;
import douyin.service.VideoService;
import douyin.utils.R;
import douyin.utils.MPUtil;
import douyin.service.StoreupService;
import douyin.entity.StoreupEntity;

/**
 * 视频信息 后端接口
 */
@RestController
@RequestMapping("/videoinfo")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private StoreupService storeupService;

    @Autowired
    private VideoLikeService videoLikeService;

    /**
     * 分页查询视频列表，按照点赞数排序
     */
    @IgnoreAuth
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params,
                  VideoEntity video,
                  @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseStart,
                  @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseEnd){
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        if(releaseStart != null) wrapper.ge("release_date", releaseStart);
        if(releaseEnd != null) wrapper.le("release_date", releaseEnd);

        params.put("order", "desc");
        params.put("sort", "like_count");
        PageUtils page = videoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(wrapper, video), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 分页查询我的视频列表
     */
    @RequestMapping(value = "/mylist", method = RequestMethod.GET)
    public R mylist(@LoginUser UserEntity user, @RequestParam Map<String, Object> params, VideoEntity video){
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("artist_id", user.getId());
        PageUtils page = videoService.queryPage(params, wrapper);
        return R.ok().put("data", page);
    }

    /**
     * 点赞
     */
    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    public R like(@LoginUser UserEntity user, @PathVariable("id") Long videoId){
        videoLikeService.likeOrUnlikeVideo(videoId, user.getId());
        return R.ok();
    }

    /**
     * 查询用户是否点赞视频
     */
    @RequestMapping(value = "/islike/{id}", method = RequestMethod.GET)
    public R isLike(@LoginUser UserEntity user, @PathVariable("id") Long videoId){
        boolean isLike = videoLikeService.isLike(videoId, user.getId());
        return R.ok().put("data", isLike);
    }


    /**
     * 列表
     */
    @RequestMapping("/lists")
    public R list(VideoEntity video){
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        wrapper.allEq(MPUtil.allEQMapPre(video, "video"));
        return R.ok().put("data", videoService.list(wrapper));
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(VideoEntity video){
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        wrapper.allEq(MPUtil.allEQMapPre(video, "video"));
        VideoView videoView =  videoService.selectView(wrapper);
        return R.ok("查询视频信息成功").put("data", videoView);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        VideoEntity video = videoService.getById(id);
        video.setClickTime(new Date());
        videoService.updateById(video);
        return R.ok().put("data", video);
    }

    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody VideoEntity video, HttpServletRequest request){
        video.setId(new Date().getTime() + Math.round(Math.random() * 1000));
        videoService.save(video);
        return R.ok();
    }


    /**
     * 前端智能排序
     */
    @IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, VideoEntity video, HttpServletRequest request, String pre){
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        Map<String, Object> newMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = entry.getKey();
            if (pre.endsWith(".")) {
                newMap.put(pre + newKey, entry.getValue());
            } else if (StringUtils.isEmpty(pre)) {
                newMap.put(newKey, entry.getValue());
            } else {
                newMap.put(pre + "." + newKey, entry.getValue());
            }
        }
        params.put("sort", "clicktime");
        params.put("order", "desc");
        Page<VideoEntity> page = videoService.page(new Page<>(), MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(wrapper, video), params), params));
        return R.ok().put("data", page);

    }

    /**
     * 协同算法（按收藏推荐）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params, VideoEntity video, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        String inteltypeColumn = "shipinfenlei";
        List<StoreupEntity> storeups = storeupService.list(new QueryWrapper<StoreupEntity>()
                .eq("type", 1)
                .eq("userid", userId)
                .eq("tablename", "video")
                .orderByDesc("addtime"));
        List<String> inteltypes = new ArrayList<>();
        Integer limit = params.get("limit") == null ? 10 : Integer.parseInt(params.get("limit").toString());
        List<VideoEntity> videoList = new ArrayList<>();
        //去重
        if(storeups != null && !storeups.isEmpty()) {
            for(StoreupEntity s : storeups) {
                videoList.addAll(videoService.list(new QueryWrapper<VideoEntity>().eq(inteltypeColumn, s.getInteltype())));
            }
        }
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        params.put("sort", "id");
        params.put("order", "desc");
        Page<VideoEntity> page = videoService.page(new Page<>(), MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(wrapper, video), params), params));
        List<VideoEntity> pageList = page.getRecords();
        if(videoList.size() < limit) {
            int toAddNum = Math.min(limit - videoList.size(), pageList.size());
            for(VideoEntity o1 : pageList) {
                boolean addFlag = true;
                for(VideoEntity o2 : videoList) {
                    if(o1.getId().intValue() == o2.getId().intValue()) {
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag) {
                    videoList.add(o1);
                    if(--toAddNum == 0) break;
                }
            }
        } else if(videoList.size() > limit) {
            videoList = videoList.subList(0, limit);
        }
        page.setRecords(videoList);
        return R.ok().put("data", page);
    }
}
