package me.lavrentiev.cookbook.service;
import me.lavrentiev.cookbook.exeptions.ValidationException;
import me.lavrentiev.cookbook.model.Recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RecipeServiceImpl implements RecipeService{
    private static long idCounter = 1;
    private Map<Long, Recipe> recipeMap = new HashMap<>();
    private ValidationService validationService;

    public RecipeServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }
    @Override
    public Recipe save(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        return recipeMap.put(idCounter++, recipe);
    }

    @Override
    public Optional<Recipe> getById(Long id) {
        return Optional.ofNullable(recipeMap.get(id));
    }

}
