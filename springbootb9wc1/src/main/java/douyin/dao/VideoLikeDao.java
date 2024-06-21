package douyin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import douyin.entity.VideoLikeEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface VideoLikeDao extends BaseMapper<VideoLikeEntity> {
    Optional<VideoLikeEntity> findByVideoIdAndUserId(Long videoId, Long userId);

    void deleteByVideoIdAndUserId(Long videoId, Long userId);

    List<Long> getLikeVideoIds(Long userId);
}