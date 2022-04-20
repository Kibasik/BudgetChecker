package com.example.moneysaver.models.classes;

import com.example.moneysaver.controllers.appContext.AppContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

// Класс для описания объекта расхода/дохода
public class BudgetItem {
    private final static String CATEGORIES_FILE = "Categories.txt";
    private final static String CATEGORIES_EXCEPTIONS_FILE = "Categories Exceptions.txt";
    private  static ArrayList<String> categoriesListExceptions = new ArrayList<>();
    private int image;
    private String category;
    private double value;
    private boolean isExpense;
    private Calendar date;
    private String note;

    private ArrayList<String> getListFromFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(AppContext.getAppContext().getAssets().open(fileName)));

            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
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

        return list;
    }

    private String categoryProperName(String category) {
        String res = "";

        if (category.contains("_")) {
            String[] words = category.replace("_", " ").trim().split(" ");

            for (String word : words) {
                res = res.concat(word.substring(0, 1).toUpperCase().concat(word.substring(1))).concat(" ");
            }
        }
        else {
            if (categoriesListExceptions.contains(category)) {
                res = category.toUpperCase();
            }
            else {
                res = category.substring(0, 1).toUpperCase().concat(category.substring(1));
            }
        }

        return res.trim();
    }

    public BudgetItem() {
        int day = 1 + (int) Math.round(Math.random() * (28 - 1));
        int month = 1 + (int) Math.round(Math.random() * (12 - 1));
        int year = 1900 + (int) Math.round(Math.random() * (2000 - 1900));
        ArrayList<String> categoriesList = getListFromFile(CATEGORIES_FILE);
        categoriesListExceptions = getListFromFile(CATEGORIES_EXCEPTIONS_FILE);
        Random rand = new Random();
        String category = categoriesList.get(rand.nextInt(categoriesList.size()));
        double value = 1 + rand.nextDouble() * (1000 - 1);
        this.image = AppContext.getAppContext().getResources().getIdentifier(category, "drawable", AppContext.getAppContext().getPackageName());
        this.category = categoryProperName(category);
        this.value = Math.round(value * 100.0) / 100.0;
        this.isExpense = Math.random() < 0.5;
//        this.date = new GregorianCalendar(year, month, day, 0, 0).getTime();
        this.date = new GregorianCalendar(2022, 2, day, 0, 0);
        this.note = "Test Note";
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean isExpense) {
        this.isExpense = isExpense;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
