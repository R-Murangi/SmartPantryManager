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

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView recycler;
    private TextView countText;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        recycler = findViewById(R.id.pantryRecycler);
        countText = findViewById(R.id.countText);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        Button add = findViewById(R.id.addButton);
        Button suggestions = findViewById(R.id.suggestionsButton);
        Button settings = findViewById(R.id.settingsButton);
        add.setOnClickListener(v -> startActivity(new Intent(this, IngredientFormActivity.class)));
        suggestions.setOnClickListener(v -> startActivity(new Intent(this, SuggestionsActivity.class)));
        settings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }

    @Override protected void onResume() {
        super.onResume();
        if (db == null) db = new DatabaseHelper(this);
        List<Ingredient> items = db.getAllIngredients();
        countText.setText("Your pantry (" + items.size() + " items)");
        recycler.setAdapter(new IngredientAdapter(items, item -> {
            Intent intent = new Intent(this, IngredientFormActivity.class);
            intent.putExtra("id", item.id);
            intent.putExtra("name", item.name);
            intent.putExtra("quantity", item.quantity);
            intent.putExtra("unit", item.unit);
            intent.putExtra("expiry", item.expiryDate);
            startActivity(intent);
        }));
    }
}
