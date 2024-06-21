package douyin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import douyin.entity.UserVideoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVideoDao extends BaseMapper<UserVideoEntity> {

    UserVideoEntity findByUserIdAndVideoId(Long userId, Long videoId);

    List<Long> getWatchedVideoIds(Long userId);
}
