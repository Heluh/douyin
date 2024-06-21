package douyin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;


@TableName("videos")
@Getter
@Setter
public class VideoEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public VideoEntity() {
		
	}
	
	public VideoEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 视频名称
	 */
					
	private String name;

	
	/**
	 * 视频封面
	 */
					
	private String cover;
	
	/**
	 * 视频url
	 */
					
	private String url;

	
	/**
	 * 视频介绍
	 */
					
	private String introduction;
	
	/**
	 * 发布日期
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat 		
	private Date releaseDate;


	/**
	 * 作者id
	 * 外键
	 */
	private Long artistId;


	/**
	 * 点赞数量
	 */
	private Integer likeCount;
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addTime;

}
