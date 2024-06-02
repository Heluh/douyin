package douyin.service.impl;

import douyin.dao.CommentDao;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import douyin.utils.PageUtils;
import douyin.utils.Query;

import douyin.entity.CommentEntity;
import douyin.service.CommentService;
import douyin.entity.vo.CommentVO;
import douyin.entity.view.CommentView;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<CommentEntity> page = this.page(
				new Query<CommentEntity>(params).getPage(),
				new QueryWrapper<CommentEntity>()
		);
		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CommentEntity> wrapper) {
		Page<CommentView> page = new Query<CommentView>(params).getPage();
		page.setRecords(baseMapper.selectListView(page, wrapper));
		return new PageUtils(page);
	}

	@Override
	public List<CommentVO> selectListVO(Wrapper<CommentEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}

	@Override
	public CommentVO selectVO(Wrapper<CommentEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}

	@Override
	public List<CommentView> selectListView(Wrapper<CommentEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public CommentView selectView(Wrapper<CommentEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
}
