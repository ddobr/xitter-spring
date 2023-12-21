package ddobr.xitter.dto;

import ddobr.xitter.entity.Post;
import ddobr.xitter.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostDTO {
    private Long id;

    @NotBlank
    private String text;

    @NotNull
    private Long authorId;

    private boolean liked;

    private User author;

    private int likesAmount;

    public Post toEntity() {
        Post post = new Post();
        post.setText(text);

        return post;
    }
}
