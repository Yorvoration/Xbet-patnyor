package com.UzCodeMD.xpak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.lifecycle.Observer;

public class DataLogin extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String  DATABASE_NAME = "chalkash.db";
    private static final String  TABLE_NAME = "malumotjami";

    public static final String Col_1 = "ID";
    public static final String Col_2 = "LOGIN";
    public static final String Col_3 = "PAROL";
    public static final String Col_4 = "TEL";
    public static final String Col_5 = "DAVLAT";
    public static final String Col_6 = "ISM";
    public static final String Col_7 = "FAMILYA";
    public static final String Col_8 = "POCHTA";
    public static final String Col_9 = "NET_KASH";
    public static final String Col_10 = "BUGUNPUL";
    public static final String Col_11 = "KECHAGIPUL";
    public static final String Col_12 = "HAFTAPUL";
    public static final String Col_13 = "OYPUL";
    public static final String Col_14= "JAMIPUL";
    public static final String Col_15 = "YANGILIKLAR";
    public static final String Col_16 = "MUROJATMAYDON";
    public static final String Col_17 = "TARIXOTKAZMA";
    public static final String Col_18 = "ALOQA";
    public static final String Col_19 = "PROMOKOD";
    public static final String Col_20 = "USERID";

    public DataLogin(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LOGIN TEXT,PAROL TEXT,TEL TEXT,DAVLAT TEXT,ISM TEXT,FAMILYA TEXT,POCHTA TEXT,NET_KASH TEXT,BUGUNPUL TEXT,KECHAGIPUL TEXT,HAFTAPUL TEXT,OYPUL TEXT,JAMIPUL TEXT,YANGILIKLAR TEXT,MUROJATMAYDON TEXT,TARIXOTKAZMA TEXT,ALOQA TEXT,PROMOKOD TEXT,USERID TEXT)");
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
    //funksiya o`zgartirish
    public Boolean ozgartir(String id,String login,String parol,String tun){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_6,login);
        contentValues.put(Col_7,parol);
        contentValues.put(Col_12,tun);
        int result = sqLiteDatabase.update(TABLE_NAME,contentValues,"ID =?",new String[]{id});
        if (result>0){
            return true;
        }
        else {
            return false;
        }
    }

    //funksiya o`chirihs
    public Integer ochir(String id){
        SQLiteDatabase ddb = this.getWritableDatabase();
        int i = ddb.delete(TABLE_NAME,"ID =?",new String[]{id});
        return i;
    }
    //kiritish funksiyasi


    public Boolean kiritish(String login, String parol,String tel, String davlat, String ism, String familya,
                            String pochta,String net_kash, String bugunpul, String kechapul, String haftapul,
                            String oypul, String jamipul, String yangiliklar,String murojatmaydaon,
                            String tarixotkazma,String aloqa,String promokod,String userid) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            ContentValues contentValues1 = new ContentValues();

            contentValues.put(Col_2,login);
            contentValues.put(Col_3,parol);
            contentValues.put(Col_4,tel);
            contentValues.put(Col_5,davlat);
            contentValues.put(Col_6,ism);
            contentValues.put(Col_7,familya);
            contentValues.put(Col_8,pochta);
            contentValues.put(Col_9,net_kash);
            contentValues.put(Col_10,bugunpul);
            contentValues.put(Col_11,kechapul);
            contentValues.put(Col_12,haftapul);
            contentValues.put(Col_13,oypul);
            contentValues.put(Col_14,jamipul);
            contentValues.put(Col_15,yangiliklar);
            contentValues.put(Col_16,murojatmaydaon);
            contentValues.put(Col_17,tarixotkazma);
            contentValues.put(Col_18,aloqa);
            contentValues.put(Col_19,promokod);
            contentValues.put(Col_20,userid);

            long result = db.insert(TABLE_NAME,null,contentValues);
            db.close();

            if (result==-1){
                return false;
            }
            else {
                return true;
            }
    }
}
