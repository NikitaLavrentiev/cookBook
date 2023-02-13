package me.lavrentiev.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Ingredient {
    private String name;
    private int time;
    private String measureUnit;

}
