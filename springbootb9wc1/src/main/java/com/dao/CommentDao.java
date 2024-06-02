package com.dao;

import com.entity.CommentEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.CommentVO;
import com.entity.view.CommentView;


/**
 * 视频信息评论表
 * 
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
public interface CommentDao extends BaseMapper<CommentEntity> {
	
	List<CommentVO> selectListVO(@Param("ew") Wrapper<CommentEntity> wrapper);
	
	CommentVO selectVO(@Param("ew") Wrapper<CommentEntity> wrapper);
	
	List<CommentView> selectListView(@Param("ew") Wrapper<CommentEntity> wrapper);

	List<CommentView> selectListView(Pagination page, @Param("ew") Wrapper<CommentEntity> wrapper);
	
	CommentView selectView(@Param("ew") Wrapper<CommentEntity> wrapper);
	

}
