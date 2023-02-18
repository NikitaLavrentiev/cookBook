package me.lavrentiev.cookbook.service;

import me.lavrentiev.cookbook.model.Ingredient;


import java.util.Map;
import java.util.Optional;

public interface IngredientService {
    Long addIngredient(Ingredient ingredient);

    Optional<Ingredient> getById(Long id);
    Ingredient update(Long id, Ingredient ingredient);

    boolean delete (Long id);
    Map<Long, Ingredient> getAll();
}
