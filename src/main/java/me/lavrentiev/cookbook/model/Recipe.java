package me.lavrentiev.cookbook.model;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> steps;
    @Override
    public String toString() {
        return name + "\n Время приготовления: " + cookingTime;
    }

}
