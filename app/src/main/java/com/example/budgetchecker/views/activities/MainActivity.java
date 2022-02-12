package com.example.budgetchecker.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.budgetchecker.R;
import com.example.budgetchecker.controllers.adapters.LogRegViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager2 vp;
    FragmentStateAdapter fsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp = findViewById(R.id.reg_log_view_pager);
        fsa = new LogRegViewPagerAdapter(this);
        vp.setAdapter(fsa);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}