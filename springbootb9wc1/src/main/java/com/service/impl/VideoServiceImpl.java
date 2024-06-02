package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.VideoDao;
import com.entity.VideoEntity;
import com.service.VideoService;
import com.entity.vo.VideoVO;
import com.entity.view.VideoView;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoDao, VideoEntity> implements VideoService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<VideoEntity> page = this.selectPage(
                new Query<VideoEntity>(params).getPage(),
                new EntityWrapper<VideoEntity>()
        );
        return new PageUtils(page);
    }

	@Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<VideoEntity> wrapper) {

		Page<VideoVO> page = new Query<VideoVO>(params).getPage();
		// 执行查询并设置结果到 Page 对象中
		List<VideoVO> records = baseMapper.selectListVO(page, wrapper);

		page.setRecords(records);

		// 将 Page 对象转换为 PageUtils 对象并返回
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
