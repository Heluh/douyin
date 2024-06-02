package douyin.dao;

import douyin.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import douyin.entity.view.UserView;
import douyin.entity.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户
 *
 * @author
 * @email
 * @date 2022-07-27 21:34:38
 */
@Repository
public interface UserDao extends BaseMapper<UserEntity> {

	List<UserVO> selectListVO(@Param("ew") Wrapper<UserEntity> wrapper);

	UserVO selectVO(@Param("ew") Wrapper<UserEntity> wrapper);

	List<UserView> selectListView(@Param("ew") Wrapper<UserEntity> wrapper);

	List<UserView> selectListView(Page<UserView> page, @Param("ew") Wrapper<UserEntity> wrapper);

	UserView selectView(@Param("ew") Wrapper<UserEntity> wrapper);
}
