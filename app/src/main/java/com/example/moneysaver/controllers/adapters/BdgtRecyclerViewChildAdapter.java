package com.example.moneysaver.controllers.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneysaver.R;
import com.example.moneysaver.controllers.appContext.AppContext;
import com.example.moneysaver.models.classes.BudgetItem;

import java.util.ArrayList;

// Адаптер для заполнения дочернего списка элементов
public class BdgtRecyclerViewChildAdapter extends RecyclerView.Adapter<BdgtRecyclerViewChildAdapter.ChildViewHolder> {
    private final ArrayList<BudgetItem> childrenItemsList;

    public static class ChildViewHolder extends RecyclerView.ViewHolder{
        public TextView categoryName;
        public TextView categoryValue;
        public ImageView categoryImage;

        public ChildViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryValue = itemView.findViewById(R.id.category_value);
            categoryImage = itemView.findViewById(R.id.category_img);
        }
    }

    public BdgtRecyclerViewChildAdapter(ArrayList<BudgetItem> list){
        this.childrenItemsList = list;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_recyclerview_child, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        BudgetItem budgetItem = childrenItemsList.get(position);
        holder.categoryName.setText(budgetItem.getCategory());
        holder.categoryValue.setText(String.format("%.2f", budgetItem.getValue()));
        holder.categoryImage.setImageDrawable(resizeDrawable(budgetItem.getImage()));

        if (budgetItem.isExpense()) {
            holder.categoryValue.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return childrenItemsList.size();
    }

    private Drawable resizeDrawable(int index) {
        Bitmap bmp = BitmapFactory.decodeResource(AppContext.getAppContext().getResources(), index);
        Bitmap resizedBmp = Bitmap.createScaledBitmap(bmp, 80, 80, true);
        return new BitmapDrawable(AppContext.getAppContext().getResources(), resizedBmp);
    }
}
