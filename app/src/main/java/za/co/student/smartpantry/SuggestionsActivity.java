package za.co.student.smartpantry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SuggestionsActivity extends AppCompatActivity {
    private DatabaseHelper db;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        db = new DatabaseHelper(this);
        RecyclerView recycler = findViewById(R.id.recipeRecycler);
        TextView empty = findViewById(R.id.emptyText);
        Button back = findViewById(R.id.backButton);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        List<Recipe> matches = RecipeMatcher.findStrictMatches(db.getAllIngredients(), db.getAllRecipes());
        empty.setVisibility(matches.isEmpty() ? View.VISIBLE : View.GONE);
        recycler.setAdapter(new RecipeAdapter(matches, recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("name", recipe.name);
            intent.putExtra("method", recipe.method);
            intent.putExtra("ingredients", formatIngredients(recipe));
            startActivity(intent);
        }));
        back.setOnClickListener(v -> finish());
    }

    private String formatIngredients(Recipe recipe) {
        StringBuilder text = new StringBuilder();
        for (java.util.Map.Entry<String, Double> entry : recipe.ingredients.entrySet()) {
            text.append("• ").append(entry.getKey()).append(" — ").append(entry.getValue()).append("\n");
        }
        return text.toString();
    }
}
