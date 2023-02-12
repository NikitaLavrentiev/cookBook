package me.lavrentiev.cookbook.service;

import me.lavrentiev.cookbook.model.Recipe;

import java.util.Optional;

public interface RecipeService {
    Recipe save(Recipe recipe);

    Optional<Recipe> getById (Long id);
}
