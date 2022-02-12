package com.example.budgetchecker.controllers.appContext;

import android.app.Application;
import android.content.Context;

// Класс для хранения контекста приложения
// Необходим для получения контекста из классов
public class AppContext extends Application {
    private static Object context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    // Получение контекста приложения
    public static Context getAppContext() {
        return (Context)context;
    }
}
