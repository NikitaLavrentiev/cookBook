package me.lavrentiev.cookbook.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.lavrentiev.cookbook.exeptions.ValidationException;
import me.lavrentiev.cookbook.model.Ingredient;
import me.lavrentiev.cookbook.service.FileIngredientService;
import me.lavrentiev.cookbook.service.IngredientService;
import me.lavrentiev.cookbook.service.ValidationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final FileIngredientService fileIngredientService;
    private static long idCounter = 1;
    private Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private ValidationService validationService;

    public IngredientServiceImpl(FileIngredientService fileIngredientService, ValidationService validationService) {
        this.fileIngredientService = fileIngredientService;
        this.validationService = validationService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Long addIngredient(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredientMap.put(idCounter++, ingredient);
        saveToFile();
        return idCounter;
    }

    @Override
    public Optional<Ingredient> getById(Long id) {
        return Optional.ofNullable(ingredientMap.get(id));
    } //Optional защищает от nullPointException

    @Override
    public Ingredient update(Long id, Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        ingredientMap.replace(id, ingredient);
        saveToFile();
        return ingredientMap.get(id);
    }

    @Override
    public boolean delete(Long id) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.remove(id);
            saveToFile();
            return true;
        }
            return false;
        }

    @Override
    public Map<Long, Ingredient> getAll() {
        return ingredientMap;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            fileIngredientService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileIngredientService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
