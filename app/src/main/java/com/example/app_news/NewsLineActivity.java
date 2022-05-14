package com.example.app_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsLineActivity extends AppCompatActivity {
    DatabaseHelper databasehelper;
    RecyclerView news;
    ArrayList<Model> list = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_line);
        setTitle("Новости");
        try {
        databasehelper = new DatabaseHelper(this);
        news = findViewById(R.id.spisok_news);
        news.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        news.setLayoutManager(layoutManager);
        Cursor res = databasehelper.getDataNews();
        if(res.getCount() == 0){
            Toast.makeText(this,"Данные отсутвуют!", Toast.LENGTH_SHORT).show();
            return;
        }
        while (res.moveToNext()){
            String header = res.getString(0);
            String text = res.getString(1);
            list.add(new Model(header,text));
        }

            news.setAdapter(new Adapter(getApplicationContext(), list));
        } catch (Exception e)
        {
            e.printStackTrace();
            displayExceptionMessage(e.getMessage());
        }
    }
    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}