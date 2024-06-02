package douyin.dao;

import douyin.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.annotations.Param;
import douyin.entity.vo.CommentVO;
import douyin.entity.view.CommentView;
import org.springframework.stereotype.Repository;


/**
 * 视频信息评论表
 *
 * @author
 * @email
 * @date 2022-07-27 21:34:38
 */
@Repository
public interface CommentDao extends BaseMapper<CommentEntity> {

    List<CommentVO> selectListVO(@Param("ew") Wrapper<CommentEntity> wrapper);

    CommentVO selectVO(@Param("ew") Wrapper<CommentEntity> wrapper);

    List<CommentView> selectListView(@Param("ew") Wrapper<CommentEntity> wrapper);

    List<CommentView> selectListView(Page<CommentView> page, @Param("ew") Wrapper<CommentEntity> wrapper);

    CommentView selectView(@Param("ew") Wrapper<CommentEntity> wrapper);
}
