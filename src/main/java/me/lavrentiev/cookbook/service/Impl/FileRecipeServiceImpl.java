package me.lavrentiev.cookbook.service.Impl;

import me.lavrentiev.cookbook.service.FileRecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileRecipeServiceImpl implements FileRecipeService {
    @Value("${path.to.data.file.recipe}")
    private String dataFilePath;
    @Value("${name.of.data.file.recipe}")
    private String dataFileName;

    @Override
    public boolean saveToFile(String json) {
        try {
            if (json != null && !json.isBlank() && !json.isEmpty()) {
                deleteDataFile();
                Files.writeString(Path.of(dataFilePath, dataFileName), json);
            } else {
                Files.writeString(Path.of(dataFilePath, dataFileName), json);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, dataFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean deleteDataFile() {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
