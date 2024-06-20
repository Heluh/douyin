package douyin.entity.view;

import douyin.entity.UserEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
 


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
