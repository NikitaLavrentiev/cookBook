package me.lavrentiev.cookbook.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String start() {
        return "Start";
    }

    @GetMapping("/info")
    public String info() {
        return """
                Ученик: Никита
                Название проекта: Книга рецептов
                Дата создания: 04.02.2023.
                Описание проекта: Это будет книга с рецептами
                """;
    }
}
