package me.lavrentiev.cookbook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String start() {
        return "Start";
    }

    @GetMapping
    public String info() {
        return "";
    }
}
