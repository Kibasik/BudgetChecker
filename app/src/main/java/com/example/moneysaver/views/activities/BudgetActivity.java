package com.example.moneysaver.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.moneysaver.R;
import com.example.moneysaver.views.fragments.BudgetFragment;

public class BudgetActivity extends AppCompatActivity {
    BudgetFragment budgetFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        budgetFragment = new BudgetFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragments_container, budgetFragment, "Budget").commit();
    }
}