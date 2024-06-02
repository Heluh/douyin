package douyin.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import douyin.utils.PageUtils;
import douyin.entity.CommentEntity;
import java.util.List;
import java.util.Map;
import douyin.entity.vo.CommentVO;
import douyin.entity.view.CommentView;
import org.apache.ibatis.annotations.Param;

/**
 * 视频信息评论表
 *
 * @date 2022-07-27 21:34:38
 */
public interface CommentService extends IService<CommentEntity> {

	PageUtils queryPage(Map<String, Object> params);

	List<CommentVO> selectListVO(Wrapper<CommentEntity> wrapper);

	CommentVO selectVO(@Param("ew") Wrapper<CommentEntity> wrapper);

	List<CommentView> selectListView(Wrapper<CommentEntity> wrapper);

	CommentView selectView(@Param("ew") Wrapper<CommentEntity> wrapper);

	PageUtils queryPage(Map<String, Object> params, Wrapper<CommentEntity> wrapper);
}
