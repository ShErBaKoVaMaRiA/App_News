package com.example.app_news;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AuthenticatorException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Authorization extends AppCompatActivity {
    EditText txtlogin;
    EditText txtpass;
    Button btnenter;
    Button btnreg;
    DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        setTitle("Авторизация");

        txtlogin = (EditText) findViewById(R.id.txt_login);
        txtpass = (EditText) findViewById(R.id.txt_pass);
        btnenter = (Button) findViewById(R.id.btn_enter);
        btnreg = (Button) findViewById(R.id.btn_reg);
        DB = new DatabaseHelper(this);


        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = txtlogin.getText().toString();
                String pass = txtpass.getText().toString();

                if(login.equals("")||pass.equals("")) Toast.makeText(Authorization.this, "Поля не заполнены!", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkusernamepass = DB.CheckAll(login, pass);
                    if(checkusernamepass==true){
                        String role = DB.RoleDB(login, pass);
                        if (role.equals("Администратор")){
                            Intent intent  = new Intent(getApplicationContext(), NewsActivity.class);
                            startActivity(intent);
                        }
                        else if (role.equals("Читатель")){
                            Intent intent  = new Intent(getApplicationContext(), NewsLineActivity.class);
                            startActivity(intent);
                        }
                    }else{ Toast.makeText(Authorization.this, "Ошибка!", Toast.LENGTH_SHORT).show(); }
                }
            }
        });
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
            }
        });
    }
}