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
import com.example.budgetchecker.controllers.security.PasswordGenerator;
import com.example.budgetchecker.controllers.sharedPreferences.SharedPreferencesHelper;
import com.example.budgetchecker.models.db.DBHelper;
import com.example.budgetchecker.models.db.DBUsers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    Button regBtn;
    EditText loginEt;
    EditText passEt;
    EditText rePassEt;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        regBtn = v.findViewById(R.id.reg_register_btn);
        loginEt = v.findViewById(R.id.reg_login_et);
        passEt = v.findViewById(R.id.reg_password_et);
        rePassEt = v.findViewById(R.id.reg_repassword_et);

        // Нажатие кнопки регистрации
        regBtn.setOnClickListener(view -> RegisterBtnClick());

        return v;
    }

    // Нажатие кнопки регистрации
    public void RegisterBtnClick() {
        if (loginEt.getText().toString().isEmpty()) {
            Toast.makeText(AppContext.getAppContext(), "Login is empty", Toast.LENGTH_SHORT).show();
        } else if (passEt.getText().toString().isEmpty()) {
            Toast.makeText(AppContext.getAppContext(), "Password is empty", Toast.LENGTH_SHORT).show();
        } else if (rePassEt.getText().toString().isEmpty()) {
            Toast.makeText(AppContext.getAppContext(), "Confirm your password", Toast.LENGTH_SHORT).show();
        } else {
            DBHelper dbHelper = new DBHelper(AppContext.getAppContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Проверка, что пользователя нет в БД
            if (DBUsers.getUserID(db, loginEt.getText().toString(), passEt.getText().toString()) == -1) {
                // Проверка, что пароли совпадают
                if (passEt.getText().toString().equals(rePassEt.getText().toString())) {


                    // Добавление нового пользователя в БД
                    int userID = DBUsers.addUser(db, loginEt.getText().toString(), passEt.getText().toString());
                    SharedPreferencesHelper.setInt("userID", userID);
                    Toast.makeText(AppContext.getAppContext(), "User was registered successfully", Toast.LENGTH_SHORT).show();
                    Toast.makeText(AppContext.getAppContext(), String.format("UserID: %s", SharedPreferencesHelper.getInt("userID")), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AppContext.getAppContext(), "Passwords are not equal", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AppContext.getAppContext(), "User already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }
}