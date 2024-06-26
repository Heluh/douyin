package douyin.entity.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
 

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
	 * 作者
	 */
	private Long artistId;

	/**
	 * 点赞数量
	 */
	private Integer likeCount;

}
