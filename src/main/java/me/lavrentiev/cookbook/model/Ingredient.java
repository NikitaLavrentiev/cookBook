package me.lavrentiev.cookbook.model;

import java.util.Objects;

public class Ingredient {
    private String name;
    private int time;
    private String measureUnit;

    public Ingredient(String name, int time, String measureUnit) {
        this.name = name;
        this.time = time;
        this.measureUnit = measureUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", measureUnit='" + measureUnit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient that)) return false;
        return time == that.time && name.equals(that.name) && measureUnit.equals(that.measureUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, time, measureUnit);
    }
}
