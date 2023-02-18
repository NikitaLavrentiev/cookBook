package me.lavrentiev.cookbook.exeptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String check) {
super("Ошибка валидации" + check);
    }
}
