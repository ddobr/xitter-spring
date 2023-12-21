package ddobr.xitter.controller;

import ddobr.xitter.dto.UserDTO;
import ddobr.xitter.entity.User;
import ddobr.xitter.repository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    @GetMapping
    public String index(Model model, @Nullable @CookieValue("userid") String useridCookie) {
        if (useridCookie != null) {
            return  "redirect:/posts";
        }

        return "auth";
    }

    @PostMapping("/authorize")
    public String authorize(@Valid UserDTO request, BindingResult result, Model model, HttpServletResponse response) {
        List<User> users = userRepository.findByUsername(request.getUsername());

        if (request.isShouldCreateNew()) {
            if (users.isEmpty()) {
                User userEntity = request.toEntity();
                userRepository.save(userEntity);

                // create a cookie
                Cookie cookie = new Cookie("userid", userEntity.getId().toString());
                cookie.setPath("/");
                //add cookie to response
                response.addCookie(cookie);

                return "redirect:/posts";
            } else {
                model.addAttribute("alreadyExists", "true");

                return  "auth";
            }
        } else {

            if (users.isEmpty()) {
                model.addAttribute("unknownUser", "true");

                return "auth";
            }

            User user = users.get(0);
            if (user.getPassword().equals(request.getPassword())) {
                // create a cookie
                Cookie cookie = new Cookie("userid", user.getId().toString());
                cookie.setPath("/");
                //add cookie to response
                response.addCookie(cookie);

                return "redirect:/posts";
            } else {
                model.addAttribute("unknownUser", "true");

                return  "auth";
            }
        }
    }
}
