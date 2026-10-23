package za.co.student.smartpantry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecipeMatcher {
    // Returns only recipes for which every required ingredient and quantity is available.
    public static List<Recipe> findStrictMatches(List<Ingredient> pantry, List<Recipe> recipes) {
        Map<String, Double> available = new HashMap<>();
        for (Ingredient item : pantry) {
            String key = normalize(item.name);
            available.put(key, available.getOrDefault(key, 0.0) + item.quantity);
        }

        List<Recipe> matches = new ArrayList<>();
        for (Recipe recipe : recipes) {
            boolean canMake = true;
            for (Map.Entry<String, Double> required : recipe.ingredients.entrySet()) {
                String key = normalize(required.getKey());
                double amount = available.getOrDefault(key, 0.0);
                if (amount < required.getValue()) {
                    canMake = false;
                    break;
                }
            }
            if (canMake) matches.add(recipe);
        }
        return matches;
    }

    // Small normalisation helper for common singular/plural variations.
    public static String normalize(String name) {
        String value = name == null ? "" : name.trim().toLowerCase(Locale.ROOT);
        if (value.endsWith("ies") && value.length() > 3) return value.substring(0, value.length() - 3) + "y";
        if (value.endsWith("oes") && value.length() > 3) return value.substring(0, value.length() - 2);
        if (value.endsWith("s") && !value.endsWith("ss") && value.length() > 1) return value.substring(0, value.length() - 1);
        return value;
    }
}
