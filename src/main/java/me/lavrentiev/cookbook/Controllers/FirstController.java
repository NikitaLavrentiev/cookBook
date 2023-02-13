package me.lavrentiev.cookbook.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping("/")
    public String start() {
        return "Start";
    }

    @GetMapping("/info")
    public String info() {
        return "Ученик: Никита </br>" +
                "Название проекта: Книга рецептов </br>" +
                "Дата создания: 04.02.2023. </br>" +
                "Описание проекта: Это будет книга с рецептами";
    }
}
