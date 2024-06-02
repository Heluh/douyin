package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.utils.PageUtils;
import com.entity.CommentEntity;
import java.util.List;
import java.util.Map;
import com.entity.vo.CommentVO;
import org.apache.ibatis.annotations.Param;
import com.entity.view.CommentView;


/**
 * 视频信息评论表
 *
 * @author 
 * @email 
 * @date 2022-07-27 21:34:38
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<CommentVO> selectListVO(Wrapper<CommentEntity> wrapper);
   	
   	CommentVO selectVO(@Param("ew") Wrapper<CommentEntity> wrapper);
   	
   	List<CommentView> selectListView(Wrapper<CommentEntity> wrapper);
   	
   	CommentView selectView(@Param("ew") Wrapper<CommentEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<CommentEntity> wrapper);
   	

}

