package com.example.budgetchecker.controllers.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.budgetchecker.views.fragments.LoginFragment;
import com.example.budgetchecker.views.fragments.RegisterFragment;

// Класс адаптера для переключения между фрагментами регистрации и логина в приложение
public class LogRegViewPagerAdapter extends FragmentStateAdapter {
    // Число фрагментов для переключения
    private static final Integer FRAGMENTS = 2;

    public LogRegViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new RegisterFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return FRAGMENTS;
    }
}
