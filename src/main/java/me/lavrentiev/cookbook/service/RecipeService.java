package me.lavrentiev.cookbook.service;

import me.lavrentiev.cookbook.model.Recipe;

import java.util.Map;
import java.util.Optional;

public interface RecipeService {
    Long addRecipe(Recipe recipe);

    Optional<Recipe> getById (Long id);

    Recipe update(Long id, Recipe recipe);

    boolean delete(Long id);

    Map<Long, Recipe> getAll();
}
