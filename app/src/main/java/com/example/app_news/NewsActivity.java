package com.example.app_news;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewsActivity extends AppCompatActivity {

    EditText txtheader, txttext;
    Button btninsert,btnselect,btnupdate,btndelete;
    DatabaseHelper databasehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("Работа с новостями");

        txtheader = findViewById(R.id.txt_header);
        txttext = findViewById(R.id.txt_text);
        btnselect = findViewById(R.id.btn_select);
        btninsert = findViewById(R.id.btn_insert);
        btnupdate = findViewById(R.id.btn_update);
        btndelete = findViewById(R.id.btn_delete);
        databasehelper = new DatabaseHelper(this);


        btninsert.setOnClickListener(v -> {
            Boolean checkInsertData = databasehelper.insertNews(txtheader.getText().toString(),txttext.getText().toString());
            if (checkInsertData)
                Toast.makeText(getApplicationContext(),"Новость добавлена", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Ошибка!", Toast.LENGTH_SHORT).show();

        });
        btnupdate.setOnClickListener(v -> {
            Boolean checkUpdateData = databasehelper.updateNews(txtheader.getText().toString(),txttext.getText().toString());
            if (checkUpdateData)
                Toast.makeText(getApplicationContext(),"Новость изменена!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Ошибка!", Toast.LENGTH_SHORT).show();
        });

        btndelete.setOnClickListener(v -> {
            Boolean checkDeleteData = databasehelper.deleteNews(txtheader.getText().toString());
            if (checkDeleteData)
                Toast.makeText(getApplicationContext(),"Новость удалена!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Ошибка!", Toast.LENGTH_SHORT).show();
        });

        btnselect.setOnClickListener(v -> {
            Cursor res = databasehelper.getDataNews();
            if(res.getCount() == 0){
                Toast.makeText(this,"Новостей!", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()){
                buffer.append("Заголовок: ").append(res.getString(0)).append("\n");
                buffer.append("Новость: ").append(res.getString(1)).append("\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Новости");
            builder.setMessage(buffer.toString());
            builder.show();
        });
    }
}