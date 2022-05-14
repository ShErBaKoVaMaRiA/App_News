package com.example.app_news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "app_news.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "users"; // название таблицы в бд
    static final String TABLE_NEWS = "news";
    // названия столбцов
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_POSITION = "position";

    public static final String COLUMN_HEADER = "header";
    public static final String COLUMN_TEXT = "text";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (" + COLUMN_LOGIN
                + " TEXT, " + COLUMN_PASSWORD + " TEXT,"+COLUMN_POSITION+" TEXT);");
        db.execSQL("CREATE TABLE news ("+ COLUMN_HEADER
                + " TEXT, " + COLUMN_TEXT + " TEXT);");
        // добавление начальных данных
//        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_LOGIN
//                + ", " + COLUMN_PASSWORD  + ", "+COLUMN_POSITION+") VALUES ('admin_123', 'admin_123','Администратор');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NEWS);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
    public Boolean insertDB(String login, String password, String position){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COLUMN_LOGIN, login);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_POSITION, position);
        long result = db.insert(TABLE, null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }
    public Boolean CheckAll(String login, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE + " where " + COLUMN_LOGIN + " = ? and "+COLUMN_PASSWORD+" = ?", new String[] {login,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public String RoleDB(String login, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select "+COLUMN_POSITION+" from "+TABLE+" where "+COLUMN_LOGIN+" = ? and "+COLUMN_PASSWORD+" = ?", new String[] {login,password});
        if (cursor.moveToFirst())
            return cursor.getString(0);
        return null;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from "+TABLE,null);
    }
    public Boolean insertNews(String header, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HEADER,header);
        contentValues.put(COLUMN_TEXT,text);
        long result = db.insert("News",null,contentValues);
        return  result != -1;
    }
    public Boolean updateNews(String header, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HEADER,header);
        contentValues.put(COLUMN_TEXT,text);
        long result = db.update(TABLE_NEWS,contentValues,COLUMN_HEADER+" = ?", new String[] {header});
        return  result != -1;
    }

    public Boolean deleteNews(String header){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HEADER,header);
        long result = db.delete(TABLE_NEWS,COLUMN_HEADER+" = ?",new String[]{header});
        return  result != -1;
    }

    public Cursor getDataNews(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from "+TABLE_NEWS,null);
    }
}
