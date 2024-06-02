package com.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.CommentEntity;
import com.entity.view.CommentView;

import com.service.CommentService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 视频信息评论表
 * 后端接口
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, CommentEntity comment,
                  HttpServletRequest request){
        EntityWrapper<CommentEntity> ew = new EntityWrapper<CommentEntity>();

		PageUtils page = commentService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, comment), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, CommentEntity comment,
                  HttpServletRequest request){
        EntityWrapper<CommentEntity> ew = new EntityWrapper<CommentEntity>();

		PageUtils page = commentService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, comment), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CommentEntity comment){
       	EntityWrapper<CommentEntity> ew = new EntityWrapper<CommentEntity>();
      	ew.allEq(MPUtil.allEQMapPre( comment, "comment")); 
        return R.ok().put("data", commentService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CommentEntity comment){
        EntityWrapper<CommentEntity> ew = new EntityWrapper<CommentEntity>();
 		ew.allEq(MPUtil.allEQMapPre( comment, "comment")); 
		CommentView commentView =  commentService.selectView(ew);
		return R.ok("查询视频信息评论表成功").put("data", commentView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CommentEntity comment = commentService.selectById(id);
        return R.ok().put("data", comment);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CommentEntity comment = commentService.selectById(id);
        return R.ok().put("data", comment);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CommentEntity comment, HttpServletRequest request){
    	comment.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(comment);
        commentService.insert(comment);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CommentEntity comment, HttpServletRequest request){
    	comment.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(comment);
        commentService.insert(comment);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CommentEntity comment, HttpServletRequest request){
        //ValidatorUtils.validateEntity(comment);
        commentService.updateById(comment);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        commentService.deleteBatchIds(Arrays.asList(ids));
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
		
		Wrapper<CommentEntity> wrapper = new EntityWrapper<CommentEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = commentService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	







}
