package me.lavrentiev.cookbook.service;

import me.lavrentiev.cookbook.model.Ingredient;
import me.lavrentiev.cookbook.model.Recipe;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public boolean validate(Recipe recipe) {
        return recipe.getName() != null
                && recipe.getSteps() != null
                && !recipe.getSteps().isEmpty()
                && recipe.getIngredients() != null
                && !recipe.getIngredients().isEmpty();
    }

    @Override
    public boolean validate(Ingredient ingredient) {
        return ingredient.getName() != null
                && ingredient.getMeasureUnit() != null;
    }
}
