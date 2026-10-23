package za.co.student.smartpantry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder> {
    public interface OnIngredientClick { void onClick(Ingredient ingredient); }
    private final List<Ingredient> items;
    private final OnIngredientClick listener;

    public IngredientAdapter(List<Ingredient> items, OnIngredientClick listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ingredient, parent, false);
        return new Holder(v);
    }

    @Override public void onBindViewHolder(@NonNull Holder h, int position) {
        Ingredient item = items.get(position);
        h.name.setText(item.name);
        h.quantity.setText(String.format(Locale.getDefault(), "Quantity: %s %s%s",
                tidy(item.quantity), item.unit, item.expiryDate.isEmpty() ? "" : "  •  Expires: " + item.expiryDate));
        h.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    private String tidy(double n) { return n == (long)n ? String.valueOf((long)n) : String.valueOf(n); }
    @Override public int getItemCount() { return items.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, quantity;
        Holder(View v) { super(v); name = v.findViewById(R.id.ingredientName); quantity = v.findViewById(R.id.ingredientQuantity); }
    }
}
