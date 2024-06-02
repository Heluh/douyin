package com.dao;

import com.entity.VideoEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import org.apache.ibatis.annotations.Param;
import com.entity.vo.VideoVO;
import com.entity.view.VideoView;


/**
 * 视频信息
 * 
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
public interface VideoDao extends BaseMapper<VideoEntity> {
	
	List<VideoVO> selectListVO(Pagination page, @Param("ew") Wrapper<VideoEntity> wrapper);
	
	VideoVO selectVO(@Param("ew") Wrapper<VideoEntity> wrapper);
	
	List<VideoView> selectListView(@Param("ew") Wrapper<VideoEntity> wrapper);

	List<VideoView> selectListView(Pagination page, @Param("ew") Wrapper<VideoEntity> wrapper);
	
	VideoView selectView(@Param("ew") Wrapper<VideoEntity> wrapper);
	

}
