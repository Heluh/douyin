package douyin.service;

import org.springframework.transaction.annotation.Transactional;

public interface VideoLikeService {
    @Transactional
    void likeOrUnlikeVideo(Long videoId, Long userId);

    boolean isLike(Long videoId, Long id);
}
