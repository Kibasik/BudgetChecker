package com.example.budgetchecker.controllers.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.budgetchecker.controllers.appContext.AppContext;

// Класс для работы с SharedPreferences
public class SharedPreferencesHelper {
    private static final String spName = "BudgetChecker";

    // Получение строки из SharedPreferences по имени переменной
    public static String getString(String name) {
        SharedPreferences sp = AppContext.getAppContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(name, "");
    }

    // Сохранение строки в SharedPreferences
    public static void setString(String name, String value) {
        SharedPreferences sp = AppContext.getAppContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.apply();
    }

    // Получение Integer из SharedPreferences по имени переменной
    public static Integer getInt(String name) {
        SharedPreferences sp = AppContext.getAppContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(name, -1);
    }

    // Сохранение Integer в SharedPreferences
    public static void setInt(String name, Integer value) {
        SharedPreferences sp = AppContext.getAppContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.apply();
    }
}
