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
        UserVideoEntity userVideoEntity1 = userVideoDao.findByUserIdAndVideoId(userVideoEntity.getUserId(), userVideoEntity.getVideoId());

        if (userVideoEntity1 != null) {
            userVideoEntity1.setWatchTime(userVideoEntity.getWatchTime());
            userVideoDao.updateById(userVideoEntity1);
        }else{
            userVideoDao.insert(userVideoEntity);
        }


    }
}
