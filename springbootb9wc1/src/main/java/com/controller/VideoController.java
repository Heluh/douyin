package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.VideoEntity;
import com.entity.view.VideoView;

import com.service.VideoService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.service.StoreupService;
import com.entity.StoreupEntity;

/**
 * 视频信息
 * 后端接口
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
@RestController
@RequestMapping("/videoinfo")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private StoreupService storeupService;

    
    /**
     * 分页查询列表
     */
	@IgnoreAuth
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params,
                  VideoEntity video,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseStart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date releaseEnd,
		HttpServletRequest request){
        EntityWrapper<VideoEntity> ew = new EntityWrapper<VideoEntity>();
                if(releaseStart!=null) ew.ge("releaseDate", releaseStart);
                if(releaseEnd!=null) ew.le("releaseDate", releaseEnd);

		PageUtils page = videoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, video), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( VideoEntity video){
       	EntityWrapper<VideoEntity> ew = new EntityWrapper<VideoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( video, "video")); 
        return R.ok().put("data", videoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(VideoEntity video){
        EntityWrapper<VideoEntity> ew = new EntityWrapper<VideoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( video, "video")); 
		VideoView videoView =  videoService.selectView(ew);
		return R.ok("查询视频信息成功").put("data", videoView);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        VideoEntity video = videoService.selectById(id);
		video.setClickTime(new Date());
		videoService.updateById(video);
        return R.ok().put("data", video);
    }
    

//    /**
//     * 后端保存
//     */
//    @RequestMapping("/save")
//    public R save(@RequestBody VideoEntity video, HttpServletRequest request){
//        video.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
//    	//ValidatorUtils.validateEntity(video);
//        videoService.insert(video);
//        return R.ok();
//    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody VideoEntity video, HttpServletRequest request){
    	video.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(video);
        videoService.insert(video);
        return R.ok();
    }

    /**
     * 点赞
     */
    @IgnoreAuth
    @RequestMapping(value = "/like/{id}", method = RequestMethod.POST)
    public R like(@PathVariable("id") Long id){
        VideoEntity video = videoService.selectById(id);
        video.setLikeCount(video.getLikeCount()+1);
        videoService.updateById(video);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody VideoEntity video, HttpServletRequest request){
        //ValidatorUtils.validateEntity(video);
        videoService.updateById(video);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        videoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<VideoEntity> wrapper = new EntityWrapper<VideoEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = videoService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params, VideoEntity video, HttpServletRequest request, String pre){
        EntityWrapper<VideoEntity> ew = new EntityWrapper<VideoEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
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
		PageUtils page = videoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, video), params), params));
        return R.ok().put("data", page);
    }


    /**
     * 协同算法（按收藏推荐）
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params, VideoEntity video, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        String inteltypeColumn = "shipinfenlei";
        List<StoreupEntity> storeups = storeupService.selectList(new EntityWrapper<StoreupEntity>().eq("type", 1).eq("userid", userId).eq("tablename", "video").orderBy("addtime", false));
        List<String> inteltypes = new ArrayList<String>();
        Integer limit = params.get("limit")==null?10:Integer.parseInt(params.get("limit").toString());
        List<VideoEntity> videoList = new ArrayList<VideoEntity>();
        //去重
        if(storeups!=null && storeups.size()>0) {
            for(StoreupEntity s : storeups) {
                videoList.addAll(videoService.selectList(new EntityWrapper<VideoEntity>().eq(inteltypeColumn, s.getInteltype())));
            }
        }
        EntityWrapper<VideoEntity> ew = new EntityWrapper<VideoEntity>();
        params.put("sort", "id");
        params.put("order", "desc");
        PageUtils page = videoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, video), params), params));
        List<VideoEntity> pageList = (List<VideoEntity>)page.getList();
        if(videoList.size()<limit) {
            int toAddNum = (limit-videoList.size())<=pageList.size()?(limit-videoList.size()):pageList.size();
            for(VideoEntity o1 : pageList) {
                boolean addFlag = true;
                for(VideoEntity o2 : videoList) {
                    if(o1.getId().intValue()==o2.getId().intValue()) {
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag) {
                    videoList.add(o1);
                    if(--toAddNum==0) break;
                }
            }
        } else if(videoList.size()>limit) {
            videoList = videoList.subList(0, limit);
        }
        page.setList(videoList);
        return R.ok().put("data", page);
    }





}
