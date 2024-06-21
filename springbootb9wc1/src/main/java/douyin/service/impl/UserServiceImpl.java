package douyin.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import douyin.utils.PageUtils;
import douyin.utils.Query;

import douyin.dao.UserDao;
import douyin.entity.UserEntity;
import douyin.service.UserService;
import douyin.entity.vo.UserVO;
import douyin.entity.view.UserView;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<UserEntity> page = this.page(
				new Query<UserEntity>(params).getPage(),
				new QueryWrapper<UserEntity>()
		);
		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<UserEntity> wrapper) {
		Page<UserView> page = new Query<UserView>(params).getPage();
		page.setRecords(baseMapper.selectListView(page, wrapper));
		return new PageUtils(page);
	}

	@Override
	public List<UserVO> selectListVO(Wrapper<UserEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}

	@Override
	public UserVO selectVO(Wrapper<UserEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}

	@Override
	public List<UserView> selectListView(Wrapper<UserEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public UserView selectView(Wrapper<UserEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

	// 更新当前用户基本信息
	@Override
	public void update(UserEntity user) {
		baseMapper.updateById(user);
	}
}
