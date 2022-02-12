package com.example.budgetchecker.models.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.budgetchecker.controllers.security.PasswordGenerator;
import com.example.budgetchecker.controllers.sharedPreferences.SharedPreferencesHelper;

// Класс для работы с таблицей users
public class DBUsers extends DBHelper {
    public final static String login = "user_login";
    public final static String pass = "user_pass";
    public final static String tbl = "users";

    public DBUsers(@Nullable Context context) {
        super(context);
    }

    // Добавление нового пользователя в БД
    public static void addUser(SQLiteDatabase db, String userLogin, String userPass) {
        ContentValues cv = new ContentValues();
        cv.put(login, userLogin);
        cv.put(pass, userPass);
        db.insert(tbl, null, cv);
    }

    // Получение всех пользователей из БД
    public static Cursor getUsers(SQLiteDatabase db) {
        return db.rawQuery("SELECT * FROM " + tbl, null);
    }

    // Проверка, что пользователь уже есть в БД
    public static boolean isUserExists(SQLiteDatabase db, String userLogin, String userPass) {
        String salt = "";
        boolean res;

        try {
            salt = SharedPreferencesHelper.getString("salt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!salt.isEmpty()) {
            String saltedPass = PasswordGenerator.generateSaltedPassword(userPass, salt);
            String query = String.format("SELECT * FROM %s WHERE UPPER(%s) LIKE UPPER('%%%s%%') AND UPPER(%s) LIKE UPPER('%%%s%%')",
                    tbl, login, userLogin, pass, saltedPass);
            Cursor cursor = db.rawQuery(query, null);
            res = cursor.getCount() > 0;
            cursor.close();
        } else {
            res = false;
        }

        return res;
    }
}
