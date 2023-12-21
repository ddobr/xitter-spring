package ddobr.xitter.entity;


import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class LikeKey implements Serializable {
    private Long postId;
    private Long senderId;
}