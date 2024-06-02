package douyin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import douyin.entity.VideoEntity;
import douyin.entity.vo.VideoVO;
import douyin.entity.view.VideoView;
import org.springframework.stereotype.Repository;

/**
 * 视频信息
 */
@Repository
public interface VideoDao extends BaseMapper<VideoEntity> {

	List<VideoVO> selectListVO(@Param("page") Page<VideoVO> page, @Param("ew") Wrapper<VideoEntity> wrapper);

	VideoVO selectVO(@Param("ew") Wrapper<VideoEntity> wrapper);

	List<VideoView> selectListView(@Param("ew") Wrapper<VideoEntity> wrapper);

	IPage<VideoView> selectListView(IPage<VideoEntity> page, @Param("ew") Wrapper<VideoEntity> wrapper);

	VideoView selectView(@Param("ew") Wrapper<VideoEntity> wrapper);
}
