package douyin.entity.view;

import douyin.entity.CommentEntity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import org.springframework.beans.BeanUtils;

/**
 * 视频信息评论表
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("comment")
public class CommentView extends CommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public CommentView() {
	}

	public CommentView(CommentEntity commentEntity) {
		BeanUtils.copyProperties(commentEntity, this);
	}
}
