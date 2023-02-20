package me.lavrentiev.cookbook.service;

import java.io.File;

public interface FileIngredientService {
    boolean saveToFile(String json);

    String readFromFile();

    boolean deleteDataFile();

    File getDataFile();
}
