package com.UzCodeMD.xpak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataAdminPanel extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "panel.db";
    private static final String  TABLE_NAME = "adminpanel";

    public static final String Col_1 = "ID";
    public static final String Col_2 = "LOGIN";
    public static final String Col_3 = "KIRISHVAQTI";
    public static final String Col_4 = "SONGIFAOLLIK";

    public DataAdminPanel(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LOGIN TEXT,SONGIFAOLLIK TEXT,KIRISHVAQTI TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }


    //funksiya o`qish
    public Cursor readAdmin(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("Select * from " + TABLE_NAME + " ORDER BY ID DESC LIMIT 1",null);
        return cursor;
    }

    public Boolean writeAdmin(String login, String songifaollik,String kirishvaqti) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_2,login);
            contentValues.put(Col_3,songifaollik);
            contentValues.put(Col_4,kirishvaqti);

            long result = db.insert(TABLE_NAME,null,contentValues);
            db.close();

            if (result==-1){
                return false;
            }
            else {
                return true;
            }
    }
    //funksiya o`zgartirish
    public Boolean updateAdmin(String id,String login, String songifaollik,String kirishvaqti){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,id);
        contentValues.put(Col_2,login);
        contentValues.put(Col_3,songifaollik);
        contentValues.put(Col_4,kirishvaqti);
        int result = sqLiteDatabase.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        if (result>0){
            return true;
        }
        else {
            return false;
        }
    }
}
