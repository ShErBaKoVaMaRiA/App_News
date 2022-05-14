package com.example.app_news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    EditText login, password;
    Button btnreg, btnenter;
    DatabaseHelper DB;
    Spinner cmbposition;
    String[] info_position = {"Администратор", "Читатель"};
    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Регистрация");
        login = (EditText) findViewById(R.id.txt_login);
        password = (EditText) findViewById(R.id.txt_pass);
        btnreg = (Button) findViewById(R.id.btn_reg);
        btnenter = (Button) findViewById(R.id.btn_enter);
        DB = new DatabaseHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, info_position);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbposition = findViewById(R.id.cmb_position);
        cmbposition.setAdapter(adapter);
        cmbposition.setPrompt("Выберите роль для пользователя");
        cmbposition.setSelection(1);

        getInfoUsers();
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = login.getText().toString();
                String pass = password.getText().toString();
                position = cmbposition.getSelectedItem().toString();

                if (user.equals("") || pass.equals(""))
                    Toast.makeText(Registration.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuser = DB.CheckAll(user, pass);
                        if (checkuser == false) { Boolean insert = DB.insertDB(user, pass, position);
                            if (insert == true) {
                                if (position.equals("Администратор")) {
                                    Intent intent = new Intent(getApplicationContext(), NewsActivity.class);
                                    startActivity(intent);
                                } else if (position.equals("Читатель")) {
                                    Intent intent = new Intent(getApplicationContext(), NewsLineActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(Registration.this, "Ошибка!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Registration.this, "Такой пользователь уже существует!", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });
        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Authorization.class);
                startActivity(intent);
            }
        });
}
    private void getInfoUsers(){
        Cursor res = DB.getData();
        if(res.getCount() == 0){
            Toast.makeText(this,"Пусто", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()){
            buffer.append("Логин: ").append(res.getString(0)).append("\n");
            buffer.append("Пароль: ").append(res.getString(1)).append("\n");
            buffer.append("Роль: ").append(res.getString(2)).append("\n\n");
        }

    }

}