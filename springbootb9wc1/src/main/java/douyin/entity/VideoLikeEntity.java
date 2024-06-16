package douyin.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;

@TableName("video_likes")
@Getter
@Setter
public class VideoLikeEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public VideoLikeEntity() {

    }

    public VideoLikeEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 点赞状态
     */
    private boolean liked;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Timestamp createdAt;
}
