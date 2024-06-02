package douyin.entity.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

/**
 * 视频信息
 * 接收传参的实体类  
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了） 
 * 取自ModelAndView 的model名称
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
@Getter
@Setter
public class VideoModel implements Serializable {
	private static final long serialVersionUID = 1L;

		
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
	 *
	 * 最近点击时间
	 */
		
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 
	private Date clickTime;


	/**
	 * 作者
	 */
	private Long artistId;

	/**
	 * 点赞数量
	 */
	private Integer likeCount;

}
