package ddobr.xitter.controller;

import ddobr.xitter.dto.UserDTO;
import ddobr.xitter.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("list", userRepository.findAll());

        return "users";
    }

    @PostMapping("/add")
    public String add(@Valid UserDTO request, BindingResult result, Model model, HttpServletResponse response) {
        if (!result.hasErrors()) {
            userRepository.save(request.toEntity());

            return  "redirect:/users";
        } else  {
            log.info("has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
        }

        return index(model);
    }

    @PostMapping("/delete")
    public String deleteUser(@Positive Long id) {
        userRepository.deleteById(id);

        return "redirect:/users";
    }

}
