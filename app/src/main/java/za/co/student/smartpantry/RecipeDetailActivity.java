package za.co.student.smartpantry; 

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ((TextView)findViewById(R.id.detailName)).setText(getIntent().getStringExtra("name"));
        ((TextView)findViewById(R.id.detailIngredients)).setText(getIntent().getStringExtra("ingredients"));
        ((TextView)findViewById(R.id.detailMethod)).setText(getIntent().getStringExtra("method"));
        ((Button)findViewById(R.id.backButton)).setOnClickListener(v -> finish());
    }
}
