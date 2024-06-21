package douyin.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VideoLikeService {
    @Transactional
    void likeOrUnlikeVideo(Long videoId, Long userId);

    boolean isLike(Long videoId, Long id);

    List<Long> getLikeVideoIds(Long userId);
}
