package za.co.student.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.Holder> {
    public interface OnRecipeClick { void onClick(Recipe recipe); }
    private final List<Recipe> recipes;
    private final OnRecipeClick listener;

    public RecipeAdapter(List<Recipe> recipes, OnRecipeClick listener) {
        this.recipes = new ArrayList<>(recipes);
        this.listener = listener;
    }

    @NonNull @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recipe, parent, false);
        return new Holder(v);
    }

    @Override public void onBindViewHolder(@NonNull Holder h, int position) {
        Recipe recipe = recipes.get(position);
        h.name.setText(recipe.name);
        StringBuilder ingredientNames = new StringBuilder();
        for (String ingredient : recipe.ingredients.keySet()) {
            if (ingredientNames.length() > 0) ingredientNames.append(", ");
            ingredientNames.append(ingredient);
        }
        h.ingredients.setText("Needs: " + ingredientNames);
        h.itemView.setOnClickListener(v -> listener.onClick(recipe));
    }

    @Override public int getItemCount() { return recipes.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, ingredients;
        Holder(View v) { super(v); name = v.findViewById(R.id.recipeName); ingredients = v.findViewById(R.id.recipeIngredients); }
    }
}
