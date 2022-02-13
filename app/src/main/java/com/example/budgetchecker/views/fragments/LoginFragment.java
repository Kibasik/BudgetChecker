package com.example.budgetchecker.views.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetchecker.R;
import com.example.budgetchecker.controllers.appContext.AppContext;
import com.example.budgetchecker.controllers.sharedPreferences.SharedPreferencesHelper;
import com.example.budgetchecker.models.db.DBHelper;
import com.example.budgetchecker.models.db.DBUsers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    Button loginBtn;
    EditText loginEt;
    EditText passEt;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        loginBtn = v.findViewById(R.id.log_login_btn);
        loginEt = v.findViewById(R.id.log_login_et);
        passEt = v.findViewById(R.id.log_password_et);

        // Нажатие кнопки входа
        loginBtn.setOnClickListener(view -> loginBtnClick());

        return v;
    }

    // Нажатие кнопки входа
    public void loginBtnClick() {
        if (loginEt.getText().toString().isEmpty()) {
            Toast.makeText(AppContext.getAppContext(), "Login is empty", Toast.LENGTH_SHORT).show();
        } else if (passEt.getText().toString().isEmpty()) {
            Toast.makeText(AppContext.getAppContext(), "Password is empty", Toast.LENGTH_SHORT).show();
        } else {
            DBHelper dbHelper = new DBHelper(AppContext.getAppContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Проверка, что пользователь есть в БД
            if (DBUsers.getUserID(db, loginEt.getText().toString(), passEt.getText().toString()) != -1 &&
                    DBUsers.getUserID(db, loginEt.getText().toString(), passEt.getText().toString()) == SharedPreferencesHelper.getInt("userID")) {
                Toast.makeText(AppContext.getAppContext(), "User exists", Toast.LENGTH_SHORT).show();
                Toast.makeText(AppContext.getAppContext(), String.format("UserID: %S", SharedPreferencesHelper.getInt("userID")), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppContext.getAppContext(), "Wrong login or password", Toast.LENGTH_SHORT).show();
            }
        }

//        // Создание объекта класса DBHelper для работы с БД
//        DBHelper dbHelper = new DBHelper(AppContext.getAppContext());
//        // Открытие БД
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        // Создание курсора для получения данных
//        Cursor cursor = DBUsers.getUsers(db);
//            while (cursor.moveToNext()) {
//                Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();
//            }
//
//        String securePass = "";
//        try {
//            securePass = PasswordGenerator.generateSecurePassword(userPass.getText().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}