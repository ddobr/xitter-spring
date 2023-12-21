package ddobr.xitter.dto;

import ddobr.xitter.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    private  Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


    private boolean shouldCreateNew;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}
