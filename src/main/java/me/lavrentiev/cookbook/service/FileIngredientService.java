package me.lavrentiev.cookbook.service;

public interface FileIngredientService {
    boolean saveToFile(String json);

    String readFromFile();
}
