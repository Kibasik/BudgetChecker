package com.example.budgetchecker.models.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.ULocale;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.budgetchecker.controllers.appContext.AppContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

// Класс для работы с БД
public class DBHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "BudgetChecker.db";
    private final static int DB_VERSION = 1;
    private final static String DB_FIRST_VER = "DB_1.sql";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        readAndExecuteSQLScript(db, DB_FIRST_VER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String fileName = String.format(Locale.getDefault(),"DB_%d_TO_%d.sql", oldVersion, newVersion);
        readAndExecuteSQLScript(db, fileName);
    }

    // Чтение файла и выполнение SQL скрипта
    private void readAndExecuteSQLScript(SQLiteDatabase db, String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            AssetManager assetManager = AppContext.getAppContext().getAssets();
            BufferedReader reader = null;

            try {
                InputStream is = assetManager.open(fileName);
                InputStreamReader isr = new InputStreamReader(is);
                reader = new BufferedReader(isr);
                executeSQLScript(db, reader);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Выполнение SQL скрипта из файла
    private void executeSQLScript(SQLiteDatabase db, BufferedReader reader) throws IOException {
        String line;
        StringBuilder statement = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            statement.append(line);
            statement.append("\n");

            if (line.endsWith(";")) {
                db.execSQL(statement.toString());
                statement = new StringBuilder();
            }
        }
    }
}
