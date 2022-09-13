package com.UzCodeMD.xpak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataSozlama extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "chalkashsoz.db";
    private static final String  TABLE_NAME = "sozlama";

    public static final String Col_1 = "ID";
    public static final String Col_2 = "KALIT";
    public static final String Col_3 = "TUN";
    public static final String Col_4 = "TIL";

    public DataSozlama(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, KALIT TEXT,TUN TEXT,TIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }


    //funksiya o`qish
    public Cursor oqish(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("Select * from " + TABLE_NAME + " ORDER BY ID DESC LIMIT 1",null);
        return cursor;
    }

    public Boolean kiritish(String kalit, String tun,String til) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_2,kalit);
            contentValues.put(Col_3,tun);
            contentValues.put(Col_4,til);

            long result = db.insert(TABLE_NAME,null,contentValues);
            db.close();

            if (result==-2){
                return false;
            }
            else {
                return true;
            }
    }
    //funksiya o`zgartirish
    public Boolean ozgartir(String id,String kalit, String tun,String til){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_1,id);
        contentValues.put(Col_2,kalit);
        contentValues.put(Col_3,tun);
        contentValues.put(Col_4,til);
        int result = sqLiteDatabase.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        if (result>0){
            return true;
        }
        else {
            return false;
        }
    }
}
