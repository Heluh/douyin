package douyin.controller;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import douyin.annotation.IgnoreAuth;
import douyin.annotation.Limit;
import douyin.annotation.LoginUser;
import douyin.entity.UserEntity;
import douyin.entity.UserVideoEntity;
import douyin.service.UserVideoService;
import douyin.service.VideoLikeService;
import douyin.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import douyin.entity.VideoEntity;
import douyin.entity.view.VideoView;
import douyin.service.VideoService;
import douyin.service.StoreupService;
import douyin.entity.StoreupEntity;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private UserVideoService userVideoService;

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 分页查询视频列表，按照点赞数排序
     */
    @IgnoreAuth
    @Limit(key="videoinfo:list", permitsPerSecond=5, timeout=500, msg="接口限流，请稍后再试")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params,
                  VideoEntity video,
                  @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseStart,
                  @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseEnd,
                  @LoginUser UserEntity user){
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        if(releaseStart != null) wrapper.ge("release_date", releaseStart);
        if(releaseEnd != null) wrapper.le("release_date", releaseEnd);

        //如果用户已经登录，返回的视频列表中排除用户已经看过的视频
        if(user != null) {
            // 获取用户已经看过的所有视频的 ID 列表
            List<Long> watchedVideoIds = userVideoService.getWatchedVideoIds(user.getId());
            // 在查询视频列表时排除这些 ID
            if(!watchedVideoIds.isEmpty())
                wrapper.notIn("v.id", watchedVideoIds);
        }

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
    @Limit(key="videoinfo:like", permitsPerSecond=5, timeout=500, msg="接口限流，请稍后再试")
    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    public R like(@LoginUser UserEntity user, @PathVariable("id") Long videoId){
        videoLikeService.likeOrUnlikeVideo(videoId, user.getId());
        return R.ok();
    }

    /**
     * 用户浏览视频
     */
    @RequestMapping(value = "/click/{id}", method = RequestMethod.POST)
    public R click(@PathVariable("id") Long videoId, @LoginUser UserEntity user){
        UserVideoEntity userVideoEntity = new UserVideoEntity();
        userVideoEntity.setVideoId(videoId);
        userVideoEntity.setUserId(user.getId());
        userVideoEntity.setWatchTime(new Date());
        userVideoService.addWatchRecord(userVideoEntity);
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
     *  查询用户点赞的视频
     */
    @RequestMapping(value = "/likelist", method = RequestMethod.GET)
    public R likeList(@LoginUser UserEntity user, @RequestParam Map<String, Object> params){
        QueryWrapper<VideoEntity> wrapper = new QueryWrapper<>();
        wrapper.in("v.id", videoLikeService.getLikeVideoIds(user.getId()));
        System.out.println(videoLikeService.getLikeVideoIds(user.getId()));
        PageUtils page = videoService.queryPage(params, wrapper);
        return R.ok().put("data", page);
    }

    /**
     * 用户上传视频
     */
    @RequestMapping(path="/add", method = RequestMethod.POST)
    public R add(@LoginUser UserEntity user,
                 @RequestParam("video") MultipartFile videoFile,
                 @RequestParam("cover") MultipartFile coverFile,
                 @RequestParam String introduction){
        String name = videoFile.getOriginalFilename();
        name = PinyinUtil.toPinyin(name);
        String objectName = System.currentTimeMillis() +"_"+name;
        String videoUrl;
        try {
            byte[] bytes = videoFile.getBytes();
            videoUrl = aliOssUtil.upload(bytes, objectName);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("上传视频失败");
        }

        // 处理封面文件
        String coverName = coverFile.getOriginalFilename();
        coverName = PinyinUtil.toPinyin(coverName);
        String coverObjectName = System.currentTimeMillis() +"_"+ coverName;
        String coverUrl;
        try {
            byte[] coverBytes = coverFile.getBytes();
            coverUrl = aliOssUtil.upload(coverBytes, coverObjectName);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("上传封面失败");
        }

        VideoEntity video = new VideoEntity();
        video.setIntroduction(introduction);
        video.setCover(coverUrl);
        video.setUrl(videoUrl);
        video.setArtistId(user.getId());
        video.setName(name);
        video.setReleaseDate(new Date());
        videoService.save(video);
        return R.ok();
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

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public R delete(@LoginUser UserEntity user, @PathVariable("id") Long videoId) {
        VideoEntity video = videoService.getById(videoId);
        if (video == null || !video.getArtistId().equals(user.getId())) {
            return R.error("没有权限删除该视频");
        }
        videoService.removeById(videoId);
        return R.ok();
    }

}
