package com.example.moneysaver.controllers.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneysaver.R;
import com.example.moneysaver.models.classes.BudgetItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

// Адаптер для заполнения родительского списка элементов
public class BdgtRecyclerViewParentAdapter extends RecyclerView.Adapter<BdgtRecyclerViewParentAdapter.ParentViewHolder> {
    private final HashMap<Integer, ArrayList<BudgetItem>> parentItemsList;
    private final RecyclerView.RecycledViewPool viewPool= new RecyclerView.RecycledViewPool();
    private final ArrayList<String> days = new ArrayList<>(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));

    public class ParentViewHolder extends RecyclerView.ViewHolder {
        private final TextView dayNumber;
        private final TextView dayName;
        private final TextView monthYear;
        private final TextView totalValue;
        public RecyclerView childRecyclerView;

        public ParentViewHolder(View itemView) {
            super(itemView);
            dayNumber = itemView.findViewById(R.id.day_number);
            dayName = itemView.findViewById(R.id.day_name);
            monthYear = itemView.findViewById(R.id.month_year);
            totalValue = itemView.findViewById(R.id.total_value);
            childRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }

    public BdgtRecyclerViewParentAdapter(HashMap<Integer, ArrayList<BudgetItem>> list){
        this.parentItemsList = list;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_recyclerview_parent, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParentViewHolder holder, int position) {
        int key = (int)parentItemsList.keySet().toArray()[position];

        if (parentItemsList.get(key).size() > 0) {
            ArrayList<BudgetItem> childrenList = parentItemsList.get(key);
            Calendar calendar = Calendar.getInstance();
            String month = new SimpleDateFormat("MMMM").format(calendar.getTime());
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            double totalValue = getChildrenTotalValue(childrenList);
            holder.dayNumber.setText(String.valueOf(key));
            holder.dayName.setText(days.get(key % 7));
            holder.monthYear.setText(month.concat(" ").concat(year));
            holder.totalValue.setText(String.format("%.2f", totalValue));

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.childRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.setInitialPrefetchItemCount(parentItemsList.size());
            BdgtRecyclerViewChildAdapter childAdapter = new BdgtRecyclerViewChildAdapter(childrenList);
            holder.childRecyclerView.setLayoutManager(linearLayoutManager);
            holder.childRecyclerView.setAdapter(childAdapter);
            holder.childRecyclerView.setRecycledViewPool(viewPool);
        }
    }

    @Override
    public int getItemCount() {
        return parentItemsList.size();
    }

    private double getChildrenTotalValue(ArrayList<BudgetItem> childrenList) {
        double res = 0;

        for (BudgetItem child : childrenList) {
            if (child.isExpense()) {
                res -= child.getValue();
            }
            else {
                res += child.getValue();
            }
        }

        return res;
    }

    private ArrayList<BudgetItem> createChildList() {
        ArrayList<BudgetItem> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            list.add(new BudgetItem());
        }

        return list;
    }
}
