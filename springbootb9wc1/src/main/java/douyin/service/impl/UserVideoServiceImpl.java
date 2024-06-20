package douyin.service.impl;

import douyin.dao.UserVideoDao;
import douyin.entity.UserVideoEntity;
import douyin.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVideoServiceImpl implements UserVideoService {
    @Autowired
    private UserVideoDao userVideoDao;

    @Override
    public void addWatchRecord(UserVideoEntity userVideoEntity) {
        userVideoDao.insert(userVideoEntity);
    }
}
