package ddobr.xitter.dto;

import ddobr.xitter.entity.Like;
import ddobr.xitter.entity.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeDTO {
    @NotNull
    private Long senderId;

    @NotNull
    private Long postId;

    public Like toEntity() {
        Like like = new Like();

        return like;
    }
}
