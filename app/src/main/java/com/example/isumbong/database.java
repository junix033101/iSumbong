package com.example.isumbong;

import static android.provider.Telephony.Carriers.PASSWORD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.nio.charset.CodingErrorAction;

public class database extends SQLiteOpenHelper {
    private static final String CODE_TABLE = "CODE_TABLE";
    private static final String ID = "ID";
    private static final String PNP_SECTOR = "PNP_SECTOR";
    private static final String CODE = "CODE";

    private static final String ADMIN_TABLE = "ADMIN_TABLE";
    private static final String USER = "USERNAME";
    private static final String PASS = "PASSWORD";
    private static final String NAME = "NAME";
    private static final String ID_NO = "ID_NO";
    private static final String EMAIL = "EMAIL";




    public database(@Nullable Context context) {
        super(context, "isumbong.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createCode = "CREATE TABLE " + CODE_TABLE + " (" + ID + " INTEGER PRIMARY KEY, " + PNP_SECTOR+ " TEXT, " + CODE + " TEXT)";
        db.execSQL(createCode);

        String createDefaultSec = "INSERT INTO CODE_TABLE(PNP_SECTOR, CODE)\n" +
                "VALUES ('Cebu Provincial Jail','N9TT-9G0A'),('Cebu City Police Office Station 5','QK6A-JI6S')";
        db.execSQL(createDefaultSec);

        String createAdmin = "CREATE TABLE " + ADMIN_TABLE + " (" + USER + " TEXT PRIMARY KEY, " + PASS + " TEXT, " + NAME + " TEXT, " + ID_NO + " TEXT,"+EMAIL+" TEXT)";
        db.execSQL(createAdmin);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //null
    }

    public boolean setCode(String code) {
        boolean check = false;
        String queryString = "SELECT * FROM " + CODE_TABLE + " WHERE CODE ='" + code + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            if (code.matches(cursor.getString(2))) {
                check = true;
            }
        }
        cursor.close();
        db.close();
        return check;
    }

    public boolean createAdmin(String user, String pass, String name, String id, String email) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER, user);
        cv.put(PASS, pass);
        cv.put(NAME, name);
        cv.put(ID_NO, id);
        cv.put(EMAIL, email);
        long insert = db.insert(ADMIN_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }

    public boolean setLogin(String user, String pass) {
        boolean check = false;
        String queryString = "SELECT * FROM " + ADMIN_TABLE + " WHERE USERNAME ='" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            if (user.matches(cursor.getString(0)) && pass.matches(cursor.getString(1))) {
                check = true;
            }
        }
        cursor.close();
        db.close();
        return check;
    }








}
