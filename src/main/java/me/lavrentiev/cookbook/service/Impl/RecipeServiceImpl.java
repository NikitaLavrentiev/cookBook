package me.lavrentiev.cookbook.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lavrentiev.cookbook.exeptions.ValidationException;
import me.lavrentiev.cookbook.model.Recipe;
import me.lavrentiev.cookbook.service.FileRecipeService;
import me.lavrentiev.cookbook.service.RecipeService;
import me.lavrentiev.cookbook.service.ValidationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    public Long addRecipe(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipeMap.put(idCounter++, recipe);
        saveToFile();
        return idCounter;
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
        recipeMap.replace(id, recipe);
        saveToFile();
        return recipeMap.get(id);
    }

    @Override
    public boolean delete(Long id) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            saveToFile();
            return true;
        }
        return false;
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
           recipeMap =  new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
