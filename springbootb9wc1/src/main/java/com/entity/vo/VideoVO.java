package com.entity.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 视频信息
 * 手机端接口返回实体辅助类 
 * （主要作用去除一些不必要的字段）
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
@Getter
@Setter
public class VideoVO implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 主键id
	 */
	private Long id;
		
	/**
	 * 视频封面
	 */
	
	private String cover;
		
	/**
	 * 视频内容
	 */
	
	private String url;

		
	/**
	 * 视频介绍
	 */
	
	private String introduction;
		
	/**
	 * 发布日期
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date releaseDate;
		
	/**
	 * 最近点击时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date clickTime;

	/**
	 * 作者
	 */
	private Integer artistId;

	/**
	 * 作者名称
	 */
	private String artistName;

	/**
	 * 点赞数量
	 */
	private Integer likeCount;

}
