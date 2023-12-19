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

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        return user;
    }
}
