package me.lavrentiev.cookbook.service;

import me.lavrentiev.cookbook.model.Ingredient;
import me.lavrentiev.cookbook.model.Recipe;

public interface ValidationService {
    boolean validate(Recipe recipe);

    boolean validate(Ingredient ingredient);
}
