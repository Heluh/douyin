package douyin.service.impl;

import douyin.dao.StoreupDao;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import douyin.utils.PageUtils;
import douyin.utils.Query;

import douyin.entity.StoreupEntity;
import douyin.service.StoreupService;
import douyin.entity.vo.StoreupVO;
import douyin.entity.view.StoreupView;

@Service
public class StoreupServiceImpl extends ServiceImpl<StoreupDao, StoreupEntity> implements StoreupService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<StoreupEntity> page = this.page(
				new Query<StoreupEntity>(params).getPage(),
				new QueryWrapper<StoreupEntity>()
		);
		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<StoreupEntity> wrapper) {
		Page<StoreupView> page = new Query<StoreupView>(params).getPage();
		page.setRecords(baseMapper.selectListView(page, wrapper));
		return new PageUtils(page);
	}

	@Override
	public List<StoreupVO> selectListVO(Wrapper<StoreupEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}

	@Override
	public StoreupVO selectVO(Wrapper<StoreupEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}

	@Override
	public List<StoreupView> selectListView(Wrapper<StoreupEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public StoreupView selectView(Wrapper<StoreupEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
}
