package douyin.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import douyin.utils.PageUtils;
import douyin.entity.UserEntity;
import java.util.List;
import java.util.Map;
import douyin.entity.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import douyin.entity.view.UserView;


/**
 * 用户
 *
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<UserVO> selectListVO(Wrapper<UserEntity> wrapper);
   	
   	UserVO selectVO(@Param("ew") Wrapper<UserEntity> wrapper);
   	
   	List<UserView> selectListView(Wrapper<UserEntity> wrapper);
   	
   	UserView selectView(@Param("ew") Wrapper<UserEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<UserEntity> wrapper);

	// 更新当前用户基本信息
	void update(UserEntity user);

}

