package me.lavrentiev.cookbook.service;

import java.io.File;

public interface FileRecipeService {

    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean deleteDataFile();

}
