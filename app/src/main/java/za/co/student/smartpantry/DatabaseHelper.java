package za.co.student.smartpantry;

import android.content.ContentValues; 
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "smart_pantry.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry_date TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, ingredients TEXT NOT NULL, method TEXT NOT NULL)");
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // A future version can add database migration steps here.
    }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Tomato Egg Scramble", "eggs:2;tomato:2", "Chop the tomatoes. Cook them in a pan, add beaten eggs and stir until set.");
        addRecipe(db, "Cheese Omelette", "eggs:2;cheese:30", "Beat the eggs, cook in a pan, add cheese, fold and serve.");
        addRecipe(db, "Garlic Tomato Pasta", "pasta:100;tomato:2;garlic:1", "Boil pasta. Cook chopped tomato and garlic, then mix with drained pasta.");
        addRecipe(db, "Potato Egg Hash", "potato:2;eggs:2;onion:1", "Dice and cook potato with onion until soft. Add eggs and cook through.");
        addRecipe(db, "Banana Pancakes", "banana:1;eggs:2", "Mash banana, mix with eggs, then cook small pancakes in a lightly oiled pan.");
        addRecipe(db, "Simple Fried Rice", "rice:150;egg:1;carrot:1", "Cook carrot pieces, add cooked rice and egg, then stir-fry until hot.");
        addRecipe(db, "Cheesy Potato Bake", "potato:3;cheese:50;milk:100", "Slice potatoes, layer with milk and cheese, then bake until tender.");
        addRecipe(db, "Tomato Onion Salad", "tomato:2;onion:1", "Chop tomato and onion, combine and season if available.");
        addRecipe(db, "Apple Banana Bowl", "apple:1;banana:1", "Wash and chop the apple and banana, then mix in a bowl.");
        addRecipe(db, "Carrot Potato Soup", "carrot:2;potato:2;onion:1", "Chop vegetables, simmer in water until soft, then mash or blend.");
        addRecipe(db, "Garlic Cheese Toast", "bread:2;cheese:30;garlic:1", "Spread crushed garlic on bread, add cheese and toast until melted.");
        addRecipe(db, "Egg Sandwich", "bread:2;eggs:2", "Boil or scramble eggs and place them between bread slices.");
        addRecipe(db, "Tomato Cheese Pasta", "pasta:100;tomato:2;cheese:30", "Boil pasta, cook tomatoes into a simple sauce and stir in cheese.");
        addRecipe(db, "Carrot Apple Salad", "carrot:1;apple:1", "Grate the carrot and apple, mix and serve fresh.");
        addRecipe(db, "Onion Potato Cakes", "potato:2;onion:1;eggs:1", "Grate potato, finely chop onion, mix with egg and pan-fry small cakes.");
        addRecipe(db, "Milk Banana Smoothie", "banana:1;milk:200", "Blend the banana and milk until smooth.");
        addRecipe(db, "Cheese Egg Toast", "bread:2;eggs:1;cheese:20", "Cook the egg, place it on bread with cheese and toast until melted.");
        addRecipe(db, "Tomato Carrot Stew", "tomato:2;carrot:1;onion:1", "Chop the vegetables and simmer with a little water until tender.");
        addRecipe(db, "Apple Pancakes", "apple:1;eggs:2;flour:50", "Grate the apple, mix with eggs and flour, then cook small pancakes.");
        addRecipe(db, "Garlic Potato Wedges", "potato:3;garlic:1", "Cut potatoes into wedges, mix with crushed garlic and bake until golden.");
    }

    private void addRecipe(SQLiteDatabase db, String name, String ingredients, String method) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("method", method);
        db.insert("recipes", null, values);
    }

    public long addIngredient(String name, double quantity, String unit, String expiry) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry_date", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().insert("pantry", null, values);
    }

    public int updateIngredient(long id, String name, double quantity, String unit, String expiry) {
        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry_date", expiry == null ? "" : expiry.trim());
        return getWritableDatabase().update("pantry", values, "id=?", new String[]{String.valueOf(id)});
    }

    public int deleteIngredient(long id) {
        return getWritableDatabase().delete("pantry", "id=?", new String[]{String.valueOf(id)});
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> items = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry_date FROM pantry ORDER BY name COLLATE NOCASE", null);
        try {
            while (c.moveToNext()) {
                items.add(new Ingredient(c.getLong(0), c.getString(1), c.getDouble(2), c.getString(3), c.getString(4)));
            }
        } finally {
            c.close();
        }
        return items;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT name,ingredients,method FROM recipes ORDER BY name", null);
        try {
            while (c.moveToNext()) {
                Recipe r = new Recipe(c.getString(0), c.getString(2));
                String[] parts = c.getString(1).split(";");
                for (String part : parts) {
                    String[] pair = part.split(":");
                    if (pair.length == 2) r.needs(pair[0], Double.parseDouble(pair[1]));
                }
                recipes.add(r);
            }
        } finally {
            c.close();
        }
        return recipes;
    }
}
