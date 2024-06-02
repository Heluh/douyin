package com.entity.view;

import com.entity.UserEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 用户
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
@TableName("users")
public class UserView extends UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public UserView(){
	}
 
 	public UserView(UserEntity userEntity){
 	try {
			BeanUtils.copyProperties(this, userEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
