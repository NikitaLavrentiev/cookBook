package me.lavrentiev.cookbook.service;

public interface FileRecipeService {

    boolean saveToFile(String json);

    String readFromFile();
}
