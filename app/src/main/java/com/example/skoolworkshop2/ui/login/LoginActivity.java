package com.example.skoolworkshop2.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skoolworkshop2.R;
import com.example.skoolworkshop2.dao.localData.LocalAppStorage;
import com.example.skoolworkshop2.logic.menuController.MenuController;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity{
    private LocalAppStorage localAppStorage;
    private MenuController menuController;
    private TextView username;
    private TextInputLayout password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        View root = (View) findViewById(R.id.activity_login);
        localAppStorage = new LocalAppStorage(getBaseContext());
//        menuController = new MenuController(root);

//        BottomNavigationView menu = root.findViewById(R.id.activity_login);
//        menu.getMenu().getItem(3).setChecked(true);

        username = (TextView)findViewById(R.id.activity_login_et_username);
        password = (TextInputLayout)findViewById(R.id.activity_login_et_password);
        button = (Button)findViewById(R.id.activity_login_btn_login);

        button.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("username: " + username.getText().toString() + " password: " + password.getEditText().getText());
            }
        }));
    }
}
