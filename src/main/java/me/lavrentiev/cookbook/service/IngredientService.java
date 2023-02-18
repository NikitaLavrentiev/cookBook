package me.lavrentiev.cookbook.service;

import me.lavrentiev.cookbook.model.Ingredient;


import java.util.Map;
import java.util.Optional;

public interface IngredientService {
    Ingredient save(Ingredient ingredient);

    Optional<Ingredient> getById(Long id);
    Ingredient update(Long id, Ingredient ingredient);

    Ingredient delete (Long id);
    Map<Long, Ingredient> getAll();
}
