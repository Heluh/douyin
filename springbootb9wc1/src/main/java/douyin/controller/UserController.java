package douyin.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

import douyin.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import douyin.annotation.IgnoreAuth;
import douyin.entity.UserEntity;
import douyin.entity.view.UserView;
import douyin.service.UserService;
import douyin.utils.PageUtils;
import douyin.utils.R;
import douyin.utils.MPUtil;
import douyin.utils.RedisUtil;
import douyin.utils.JwtUtil;

/**
 * 用户 后端接口
 */
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 登录
	 */
	@IgnoreAuth
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public R login(@RequestParam String username, @RequestParam String password) {
		UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("username", username));
		if (user == null) {
			return R.error("账号不存在");
		}

		// 创建BCryptPasswordEncoder对象
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// 验证密码
		if (!passwordEncoder.matches(password, user.getPassword())) {
			return R.error("密码不正确");
		}

		String token = JwtUtil.generateToken(user.getId(), username, "users", "用户");
		redisUtil.set(token, user.getId(), 1, TimeUnit.HOURS);
		return R.ok().put("token", token);
	}

	/**
	 * 注册
	 */
	@IgnoreAuth
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public R register(@RequestBody UserEntity user) {
		UserEntity user1 = userService.getOne(new QueryWrapper<UserEntity>().eq("username", user.getUsername()));
		if (user1 != null) {
			return R.error("注册用户已存在");
		}
		Long uId = new Date().getTime();
		user.setId(uId);

		// 对密码进行加密
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userService.save(user);
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
	 * 获取当前用户的信息
	 */
	@GetMapping
	public R getCurrUser(@LoginUser UserEntity user1) {
		UserEntity user = userService.getById(user1.getId());
		return R.ok().put("data", user);
	}

	/**
	 * 密码重置
	 */
	@IgnoreAuth
	@RequestMapping(value = "/resetPass")
	public R resetPass(String username, HttpServletRequest request) {
		UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq("username", username));
		if (user == null) {
			return R.error("账号不存在");
		}
		user.setPassword("123456");
		userService.updateById(user);
		return R.ok("密码已重置为：123456");
	}

	/**
	 * 查询
	 */
	@RequestMapping("/query")
	public R query(UserEntity user) {
		QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
		queryWrapper.allEq(MPUtil.allEQMapPre(user, "user"));
		UserView userView = userService.selectView(queryWrapper);
		return R.ok("查询用户成功").put("data", userView);
	}

	/**
	 * 前端详情
	 */
	@IgnoreAuth
	@RequestMapping("/detail/{id}")
	public R detail(@PathVariable("id") Long id) {
		UserEntity user = userService.getById(id);
		return R.ok().put("data", user);
	}


	/**
	 * 更新当前用户信息
	 */
	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@Transactional
	public R update(@LoginUser UserEntity user,
					@RequestParam String name,
					@RequestParam String sex,
					@RequestParam String phone) {

		UserEntity user1 = userService.getById(user.getId());
		user1.setName(name);
		user1.setSex(sex);
		user1.setPhone(phone);
		userService.update(user1);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		userService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}

}
