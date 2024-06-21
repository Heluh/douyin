package douyin.service;

import douyin.entity.UserVideoEntity;
import java.util.List;

public interface UserVideoService {
    //增加浏览记录
    void addWatchRecord(UserVideoEntity userVideoEntity);

    List<Long> getWatchedVideoIds(Long userId);
}
