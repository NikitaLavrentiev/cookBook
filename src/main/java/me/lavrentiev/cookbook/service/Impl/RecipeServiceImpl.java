package me.lavrentiev.cookbook.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import me.lavrentiev.cookbook.exeptions.ValidationException;
import me.lavrentiev.cookbook.model.Recipe;
import me.lavrentiev.cookbook.service.FileRecipeService;
import me.lavrentiev.cookbook.service.RecipeService;
import me.lavrentiev.cookbook.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final FileRecipeService fileRecipeService;
    private static long idCounter = 1;
    private Map<Long, Recipe> recipeMap = new HashMap<>();
    private ValidationService validationService;

    public RecipeServiceImpl(FileRecipeService fileRecipeService, ValidationService validationService) {
        this.fileRecipeService = fileRecipeService;
        this.validationService = validationService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Recipe save(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        saveToFile();
        return recipeMap.put(idCounter++, recipe);
    }

    @Override
    public Optional<Recipe> getById(Long id) {
        return Optional.ofNullable(recipeMap.get(id));
    }

    @Override
    public Recipe update(Long id, Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        saveToFile();
        return recipeMap.replace(id, recipe);
    }

    @Override
    public Recipe delete(Long id) {
        return recipeMap.remove(id);
    }

    @Override
    public Map<Long, Recipe> getAll() {
        return recipeMap;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileRecipeService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileRecipeService.readFromFile();
            new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
