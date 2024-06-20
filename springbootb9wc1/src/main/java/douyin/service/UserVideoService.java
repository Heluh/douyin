package douyin.service;

import douyin.entity.UserVideoEntity;

public interface UserVideoService {
    //增加浏览记录
    void addWatchRecord(UserVideoEntity userVideoEntity);
}
