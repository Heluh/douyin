package com.entity.view;

import com.entity.CommentEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 视频信息评论表
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
@TableName("comment")
public class CommentView extends CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public CommentView(){
	}
 
 	public CommentView(CommentEntity commentEntity){
 	try {
			BeanUtils.copyProperties(this, commentEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
