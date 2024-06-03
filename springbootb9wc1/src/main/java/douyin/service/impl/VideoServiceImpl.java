package douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import douyin.dao.VideoDao;
import douyin.entity.VideoEntity;
import douyin.entity.view.VideoView;
import douyin.entity.vo.VideoVO;
import douyin.service.VideoService;
import douyin.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoDao, VideoEntity> implements VideoService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<VideoEntity> page = new Page<>(
				Long.parseLong(params.get("page").toString()),
				Long.parseLong(params.get("limit").toString())
		);
		page = this.page(page, new QueryWrapper<>());
		return new PageUtils(page);
	}


	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<VideoEntity> wrapper) {
		Page<VideoVO> page = new Page<>(Long.parseLong(params.get("page").toString()), Long.parseLong(params.get("limit").toString()));
		List<VideoVO> records = baseMapper.selectListVO(page, wrapper);
		page.setRecords(records);
		return new PageUtils(page);
	}

	@Override
	public List<VideoVO> selectListVO(Wrapper<VideoEntity> wrapper) {
		return null;
// 		return baseMapper.selectListVO(wrapper);
	}

	@Override
	public VideoVO selectVO(Wrapper<VideoEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}

	@Override
	public List<VideoView> selectListView(Wrapper<VideoEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public VideoView selectView(Wrapper<VideoEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}
}
