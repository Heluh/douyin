package douyin.entity.view;

import douyin.entity.VideoEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 

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
