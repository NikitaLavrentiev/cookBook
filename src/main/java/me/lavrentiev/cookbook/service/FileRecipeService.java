package me.lavrentiev.cookbook.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileRecipeService {

    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    boolean deleteDataFile();
    boolean cleanDataFile();

    Path returnPath();

    Path saveToFile(String content, Path path) throws IOException;

}
