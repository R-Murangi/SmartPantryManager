package za.co.student.smartpantry;

import java.util.LinkedHashMap;
import java.util.Map;

public class Recipe {
    public String name;
    public Map<String, Double> ingredients = new LinkedHashMap<>();
    public String method;

    public Recipe(String name, String method) {
        this.name = name;
        this.method = method;
    }

    public Recipe needs(String ingredient, double quantity) {
        ingredients.put(ingredient, quantity);
        return this;
    }
}
