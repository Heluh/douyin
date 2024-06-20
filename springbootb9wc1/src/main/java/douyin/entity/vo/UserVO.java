package douyin.entity.vo;

import java.io.Serializable;
 


public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	 			
	/**
	 * 密码
	 */
	
	private String password;
		
	/**
	 * 姓名
	 */
	
	private String name;
		
	/**
	 * 性别
	 */
	
	private String sex;
		
	/**
	 * 头像
	 */
	
	private String avatar;
		
	/**
	 * 手机
	 */
	
	private String phone;
				
	
	/**
	 * 设置：密码
	 */
	 
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
				
	
	/**
	 * 设置：姓名
	 */
	 
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
				
	
	/**
	 * 设置：性别
	 */
	 
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	/**
	 * 获取：性别
	 */
	public String getSex() {
		return sex;
	}
				
	
	/**
	 * 设置：头像
	 */
	 
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	/**
	 * 获取：头像
	 */
	public String getAvatar() {
		return avatar;
	}
				
	
	/**
	 * 设置：手机
	 */
	 
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * 获取：手机
	 */
	public String getPhone() {
		return phone;
	}
			
}
