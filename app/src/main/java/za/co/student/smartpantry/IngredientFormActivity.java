package za.co.student.smartpantry;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class IngredientFormActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText nameInput, quantityInput, unitInput, expiryInput;
    private long itemId = -1;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_form);
        db = new DatabaseHelper(this);
        nameInput = findViewById(R.id.nameInput);
        quantityInput = findViewById(R.id.quantityInput);
        unitInput = findViewById(R.id.unitInput);
        expiryInput = findViewById(R.id.expiryInput);
        TextView title = findViewById(R.id.formTitle);
        Button save = findViewById(R.id.saveButton);
        Button delete = findViewById(R.id.deleteButton);
        Button cancel = findViewById(R.id.cancelButton);

        if (getIntent().hasExtra("id")) {
            itemId = getIntent().getLongExtra("id", -1);
            title.setText("Edit ingredient");
            nameInput.setText(getIntent().getStringExtra("name"));
            quantityInput.setText(String.valueOf(getIntent().getDoubleExtra("quantity", 1)));
            unitInput.setText(getIntent().getStringExtra("unit"));
            expiryInput.setText(getIntent().getStringExtra("expiry"));
            delete.setVisibility(Button.VISIBLE);
        }

        save.setOnClickListener(v -> saveIngredient());
        delete.setOnClickListener(v -> {
            if (itemId != -1) db.deleteIngredient(itemId);
            Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
        cancel.setOnClickListener(v -> finish());
    }

    private void saveIngredient() {
        String name = nameInput.getText().toString().trim();
        String quantityText = quantityInput.getText().toString().trim();
        String unit = unitInput.getText().toString().trim();
        String expiry = expiryInput.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(quantityText) || TextUtils.isEmpty(unit)) {
            Toast.makeText(this, "Please enter a name, quantity and unit.", Toast.LENGTH_LONG).show();
            return;
        }
        double quantity;
        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantity must be a number.", Toast.LENGTH_LONG).show();
            return;
        }
        if (quantity <= 0) {
            Toast.makeText(this, "Quantity must be greater than zero.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!expiry.isEmpty() && !expiry.matches("\\d{4}-\\d{2}-\\d{2}")) {
            Toast.makeText(this, "Use YYYY-MM-DD for the expiry date, or leave it blank.", Toast.LENGTH_LONG).show();
            return;
        }

        if (itemId == -1) db.addIngredient(name, quantity, unit, expiry);
        else db.updateIngredient(itemId, name, quantity, unit, expiry);
        Toast.makeText(this, "Ingredient saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
