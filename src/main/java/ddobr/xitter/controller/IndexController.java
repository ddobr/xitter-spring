package ddobr.xitter.controller;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@Nullable  @CookieValue("userid") String useridCookie) {

        if (useridCookie != null) {
            return "redirect:/posts";
        }

        return  "redirect:/auth";
    }
}
