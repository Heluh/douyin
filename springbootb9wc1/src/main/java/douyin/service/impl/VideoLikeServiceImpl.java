package douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import douyin.dao.VideoDao;
import douyin.dao.VideoLikeDao;
import douyin.entity.VideoEntity;
import douyin.entity.VideoLikeEntity;
import douyin.service.VideoLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoLikeServiceImpl implements VideoLikeService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private VideoLikeDao videoLikeDao;

    @Override
    @Transactional
    public void likeOrUnlikeVideo(Long videoId, Long userId) {
        QueryWrapper<VideoLikeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId).eq("user_id", userId);
        VideoLikeEntity likeEntity = videoLikeDao.selectOne(queryWrapper);
        VideoEntity video = videoDao.selectById(videoId);

        if (video == null) {
            throw new RuntimeException("Video not found");
        }

        if (likeEntity != null) {
            // 用户已经点赞，则取消点赞
            videoLikeDao.deleteByVideoIdAndUserId(videoId, userId);
            video.setLikeCount(video.getLikeCount() - 1);
        } else {
            // 用户没有点赞，则添加点赞
            VideoLikeEntity like = new VideoLikeEntity();
            like.setVideoId(videoId);
            like.setUserId(userId);
            like.setLiked(true);

            videoLikeDao.insert(like);
            video.setLikeCount(video.getLikeCount() + 1);
        }

        videoDao.updateById(video);
    }

    @Override
    public boolean isLike(Long videoId, Long id) {
        QueryWrapper<VideoLikeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId).eq("user_id", id);
        VideoLikeEntity likeEntity = videoLikeDao.selectOne(queryWrapper);
        return likeEntity != null;
    }
}
