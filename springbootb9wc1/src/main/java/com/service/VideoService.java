package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.VideoEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.VideoVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.VideoView;


/**
 * 视频信息
 *
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
public interface VideoService extends IService<VideoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<VideoVO> selectListVO(Wrapper<VideoEntity> wrapper);
   	
   	VideoVO selectVO(@Param("ew") Wrapper<VideoEntity> wrapper);
   	
   	List<VideoView> selectListView(Wrapper<VideoEntity> wrapper);
   	
   	VideoView selectView(@Param("ew") Wrapper<VideoEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<VideoEntity> wrapper);
   	

}

