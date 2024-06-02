package douyin.entity.view;

import douyin.entity.VideoEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

/**
 * 视频信息
 * 后端返回视图实体辅助类   
 * （通常后端关联的表或者自定义的字段需要返回使用）
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
@TableName("videos")
public class VideoView extends VideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public VideoView(){
	}
 
 	public VideoView(VideoEntity videoEntity){
 	try {
			BeanUtils.copyProperties(this, videoEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		
	}
}
