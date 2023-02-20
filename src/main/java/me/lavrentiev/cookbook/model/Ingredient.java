package me.lavrentiev.cookbook.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class Ingredient {
    private String name;
    private int time;
    private String measureUnit;
    @Override
    public String toString() {
        return name + " - " + time + " " + measureUnit;
    }

}
