package me.lavrentiev.cookbook.service.Impl;

import me.lavrentiev.cookbook.service.FileRecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileRecipeServiceImpl implements FileRecipeService {
    @Value("${path.to.data.file.recipe}")
    private String dataFilePath;
    @Value("${name.of.data.file.recipe}")
    private String dataFileName;
    @Value("name.of.data.file.txt.recipe")
    private String dataTxtFileName;

    @Override
    public boolean saveToFile(String json) {
        try {
                deleteDataFile();
                Files.writeString(Path.of(dataFilePath, dataFileName), json);
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


    public boolean deleteDataFile() {
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

    @Override
    public boolean cleanDataFile() {
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

    @Override
    public Path returnPath() {
        Path path = Path.of(dataFilePath, dataTxtFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    @Override
        public Path saveToFile (String content, Path path) throws IOException {
            createNewFile(path);
            return Files.writeString(path, content);
        }


        private void createNewFile(Path path) throws IOException {
            Files.deleteIfExists(path);
            Files.createFile(path);
        }

    @Override
    public File getDataFile() {
        return new File(dataFilePath + "/" + dataFileName);
    }

}

