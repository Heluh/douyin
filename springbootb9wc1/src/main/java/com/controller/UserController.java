package com.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.UserEntity;
import com.entity.view.UserView;

import com.service.UserService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.RedisUtil;

/**
 * 用户
 * 后端接口
 * @author 
 * @email
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha, HttpServletRequest request) {
		UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("username", username));
		System.out.println(username);
		if(user == null) {
			return R.error("账号不存在");
		}

		// 创建BCryptPasswordEncoder对象
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 验证密码
		if(!passwordEncoder.matches(password, user.getPassword())) {
			return R.error("密码不正确");
		}

		String token = tokenService.generateToken(user.getId(), username,"users",  "用户" );
		redisUtil.set(token, user.getId(), 1, TimeUnit.HOURS);
		return R.ok().put("token", token);
	}
	
	/**
     * 注册
     */
	@IgnoreAuth
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public R register(@RequestBody UserEntity user){
		UserEntity user1 = userService.selectOne(new EntityWrapper<UserEntity>().eq("username", user.getUsername()));
		if(user1!=null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		user.setId(uId);

		// 对密码进行加密
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userService.insert(user);
		return R.ok();
	}

	
	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public R logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return R.ok("退出成功");
	}
	
	/**
     * 获取用户的session用户信息
     */
    @RequestMapping("/session")
    public R getCurrUser(HttpServletRequest request){
    	Long id = (Long)request.getSession().getAttribute("userId");
        UserEntity user = userService.selectById(id);
        return R.ok().put("data", user);
    }
    
    /**
     * 密码重置
     */
    @IgnoreAuth
	@RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request){
    	UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("yonghuming", username));
    	if(user==null) {
    		return R.error("账号不存在");
    	}
        user.setPassword("123456");
        userService.updateById(user);
        return R.ok("密码已重置为：123456");
    }


    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, UserEntity yonghu,
				  HttpServletRequest request){
        EntityWrapper<UserEntity> ew = new EntityWrapper<UserEntity>();

		PageUtils page = userService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yonghu), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( UserEntity yonghu){
       	EntityWrapper<UserEntity> ew = new EntityWrapper<UserEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu")); 
        return R.ok().put("data", userService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(UserEntity yonghu){
        EntityWrapper<UserEntity> ew = new EntityWrapper<UserEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yonghu, "yonghu")); 
		UserView userView =  userService.selectView(ew);
		return R.ok("查询用户成功").put("data", userView);
    }


    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        UserEntity yonghu = userService.selectById(id);
        return R.ok().put("data", yonghu);
    }
    


    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody UserEntity yonghu, HttpServletRequest request){
    	yonghu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(yonghu);
    	UserEntity user = userService.selectOne(new EntityWrapper<UserEntity>().eq("yonghuming", yonghu.getUsername()));
		if(user!=null) {
			return R.error("用户已存在");
		}
		yonghu.setId(new Date().getTime());
        userService.insert(yonghu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody UserEntity yonghu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yonghu);
        userService.updateById(yonghu);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        userService.deleteBatchIds(Arrays.asList(ids));
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
		
		Wrapper<UserEntity> wrapper = new EntityWrapper<UserEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}


		int count = userService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	







}
