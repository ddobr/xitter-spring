package ddobr.xitter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Slf4j
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Map<String, Object> model) {
        model.put("appName", "XITTER");


        return  "index";
    }

    @PostMapping("/check")
    public String check(Map<String, Object> req) {
        log.info("Получили: {}", req);

        return  "index";
    }
}
