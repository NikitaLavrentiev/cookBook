package me.lavrentiev.cookbook.service.Impl;

import me.lavrentiev.cookbook.exeptions.ValidationException;
import me.lavrentiev.cookbook.model.Ingredient;
import me.lavrentiev.cookbook.service.IngredientService;
import me.lavrentiev.cookbook.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long idCounter = 1;
    private Map<Long, Ingredient> ingredientMap = new HashMap<>();
    private ValidationService validationService;

    public IngredientServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        return ingredientMap.put(idCounter++, ingredient);
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
        return ingredientMap.replace(id, ingredient);
    }

    @Override
    public Ingredient delete(Long id) {
        return ingredientMap.remove(id);
    }

    @Override
    public Map<Long, Ingredient> getAll() {
        return ingredientMap;
    }
}
