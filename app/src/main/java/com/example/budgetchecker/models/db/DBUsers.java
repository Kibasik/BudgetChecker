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
    public static Integer addUser(SQLiteDatabase db, String userLogin, String userPass) {
        String securePass = "";

        try {
            securePass = PasswordGenerator.generateSecurePassword(userPass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ContentValues cv = new ContentValues();
        cv.put("user_id", 888);
        cv.put(login, userLogin);
        cv.put(pass, securePass);
        db.insert(tbl, null, cv);
        int userID = getUserID(db, userLogin, userPass);
        SharedPreferencesHelper.setInt("userID", userID);
        return userID;
    }

    // Получение всех пользователей из БД
    public static Cursor getUsers(SQLiteDatabase db) {
        return db.rawQuery("SELECT * FROM " + tbl, null);
    }

    // Получение ID пользователя из БД
    public static int getUserID(SQLiteDatabase db, String userLogin, String userPass) {
        String salt = "";
        int res;

        try {
            salt = SharedPreferencesHelper.getString("salt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!salt.isEmpty()) {
            String securePass = PasswordGenerator.generateSaltedPassword(userPass, salt);
            String query = String.format("SELECT * FROM %s WHERE UPPER(%s) LIKE UPPER('%%%s%%') AND UPPER(%s) LIKE UPPER('%%%s%%')",
                    tbl, login, userLogin, pass, securePass);
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            res = cursor.getInt(0);
            cursor.close();
        } else {
            res = -1;
        }

        return res;
    }
}
