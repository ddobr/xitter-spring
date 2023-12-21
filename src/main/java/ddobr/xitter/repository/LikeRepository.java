package ddobr.xitter.repository;

import ddobr.xitter.entity.Like;
import ddobr.xitter.entity.LikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, LikeKey> {
    Like findLikeByPostIdAndSenderId(Long postId, Long senderId);

    List<Like> findByPostId(Long postId);
}
