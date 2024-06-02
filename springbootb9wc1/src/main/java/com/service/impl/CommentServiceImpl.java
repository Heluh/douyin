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


import com.dao.CommentDao;
import com.entity.CommentEntity;
import com.service.CommentService;
import com.entity.vo.CommentVO;
import com.entity.view.CommentView;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CommentEntity> page = this.selectPage(
                new Query<CommentEntity>(params).getPage(),
                new EntityWrapper<CommentEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<CommentEntity> wrapper) {
		  Page<CommentView> page =new Query<CommentView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
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
