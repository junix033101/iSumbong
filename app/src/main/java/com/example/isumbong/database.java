package com.example.isumbong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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

    private static final String LIST_OF_VICTIMS_TABLE = "VICTIMS_INFO_TABLE";
    private static final String LIST_OF_VICTIMS_ID = "LIST_OF_VICTIMS_ID";
    private static final String AGE = "AGE";
    private static final String GENDER = "GENDER";
    private static final String VICTIMS_ID = "VICTIMS_ID";

    private static final String VICTIMS_TABLE = "VICTIMS_TABLE";
    private static final String  NUMBER_OF_VICTIMS = "NUMBER_OF_VICTIMS";

    private static final String ACCIDENT_INFO_TABLE= "ACCIDENT_INFO_TABLE";
    private static final String ACCIDENT_IMG= "ACCIDENT_IMG";
    private static final String ACCIDENT_INFO_ID= "ACCIDENT_INFO_ID";
    private static final String LICENSE_NUMBER = "LICENSE_NUMBER";
    private static final String LICENSE_IMG= "LICENSE_IMG";

    private static final String VEHICLE_INFO_TABLE= "VEHICLE_INFO_TABLE";
    private static final String VEHICLE_IMG= "VEHICLE_IMG";
    private static final String VEHICLE_INFO_ID= "VEHICLE_INFO_ID";
    private static final String PLATE_NUMBER = "PLATE_NUMBER";
    private static final String OR_IMG= "OR_IMG";
    private static final String VEHICLE_TYPE= "VEHICLE_TYPE";

    private static final String STATEMENT_TABLE = "STATEMENT_TABLE";
    private static final String STATEMENT_ID = "STATEMENT_ID";
    private static final String  STATEMENT = "STATEMENT";

    private static final String LOCATION_TABLE = "LOCATION_TABLE";
    private static final String LOCATION_ID = "LOCATION_ID";
    private static final String  ADDRESS_PIN = "ADDRESS_PIN";
    private static final String  LATITUDE = "LATITUDE";
    private static final String  LONGITUDE = "LONGITUDE";

    private static final String SERIAL_TABLE = "SERIAL_TABLE";
    private static final String SERIAL_ID = "SERIAL_ID";
    private static final String  SERIAL_NUMBER = "SERIAL_NUMBER";







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

        String createVictimsInfo = "CREATE TABLE " + LIST_OF_VICTIMS_TABLE + " (" + LIST_OF_VICTIMS_ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + AGE + " TEXT, " + GENDER + " TEXT,"+VICTIMS_ID+" INT)";
        db.execSQL(createVictimsInfo);

        String createVictims = "CREATE TABLE " + VICTIMS_TABLE + " (" + VICTIMS_ID + " INTEGER PRIMARY KEY, " + NUMBER_OF_VICTIMS + " TEXT)";
        db.execSQL(createVictims);

        String createAccidentInfo = "CREATE TABLE " + ACCIDENT_INFO_TABLE+ " (" + ACCIDENT_INFO_ID + " INTEGER PRIMARY KEY, " + VICTIMS_ID + " INT, " + ACCIDENT_IMG+ ", " + LICENSE_NUMBER+ " TEXT, " +LICENSE_IMG+ " )";
        db.execSQL(createAccidentInfo);

        String createVehicleInfo = "CREATE TABLE " + VEHICLE_INFO_TABLE+ " (" + VEHICLE_INFO_ID + " INTEGER PRIMARY KEY, " + VICTIMS_ID + " INT, " + PLATE_NUMBER + " TEXT," + VEHICLE_TYPE+ " TEXT," + VEHICLE_IMG+ ", " +OR_IMG+ " )";
        db.execSQL(createVehicleInfo);

        String createStatement = "CREATE TABLE " + STATEMENT_TABLE + " (" + STATEMENT_ID + " INTEGER PRIMARY KEY," + VICTIMS_ID + " INT, " + STATEMENT + " TEXT)";
        db.execSQL(createStatement);

        String createLocation = "CREATE TABLE " + LOCATION_TABLE + " (" + LOCATION_ID + " INTEGER PRIMARY KEY," + VICTIMS_ID + " INT, " + ADDRESS_PIN + " TEXT, "+LATITUDE+" DOUBLE, "+LONGITUDE+" DOUBLE)";
        db.execSQL(createLocation);

        String createSerial = "CREATE TABLE " + SERIAL_TABLE + " (" + SERIAL_ID + " INTEGER PRIMARY KEY," + VICTIMS_ID + " INT, " + SERIAL_NUMBER + " TEXT)";
        db.execSQL(createSerial);
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
    public boolean InsertVictimInfo(Victim victim, int ID){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, victim.getName());
        cv.put(AGE, victim.getAge());
        cv.put(GENDER, victim.getGender());
        cv.put(VICTIMS_ID,ID);
        long insert = db.insert(LIST_OF_VICTIMS_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }
    public boolean InsertVictims(int num){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NUMBER_OF_VICTIMS, num);
        long insert = db.insert(VICTIMS_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;

    }
    public int victimsID(){
        int retval = 0;
        String query = "SELECT last_insert_rowid()";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            retval = cursor.getInt(0);
        }
        return retval;
    }
    public boolean InsertAccidentInfo(String img, String l_img, int victims_id, String license_num ) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VICTIMS_ID, victims_id);
        cv.put(ACCIDENT_IMG, img);
        cv.put(LICENSE_NUMBER, license_num);
        cv.put(LICENSE_IMG, l_img);
        long insert = db.insert(ACCIDENT_INFO_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;

    }
    public boolean InsertVehicleInfo(String vehicle_img, String or_img, int victims_id, String plate_num, String vehicle_type ) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VICTIMS_ID, victims_id);
        cv.put(PLATE_NUMBER, plate_num);
        cv.put(VEHICLE_TYPE, vehicle_type);
        cv.put(VEHICLE_IMG, vehicle_img);
        cv.put(OR_IMG, or_img);
        long insert = db.insert(VEHICLE_INFO_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }

    public boolean InsertStatement(int victims_id, String statement ) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VICTIMS_ID, victims_id);
        cv.put(STATEMENT, statement);
        long insert = db.insert(STATEMENT_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }
    public boolean InsertLocation(int victims_id, String address, double lat, double lng){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VICTIMS_ID, victims_id);
        cv.put(ADDRESS_PIN, address);
        cv.put(LATITUDE, lat);
        cv.put(LONGITUDE, lng);
        long insert = db.insert(LOCATION_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }

    public boolean InsertSerial(int victims_id, String serial){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VICTIMS_ID, victims_id);
        cv.put(SERIAL_NUMBER, serial);
        long insert = db.insert(SERIAL_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }





    public String getAccidentImg(int victims_id){
        String img_a = "";
        String queryString = "SELECT * FROM " + ACCIDENT_INFO_TABLE + "WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            img_a = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return img_a;
    }

    public String getLicenseNumber(int victims_id){
        String license = "";
        String queryString = "SELECT * FROM " + ACCIDENT_INFO_TABLE + "WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            license = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return license;
    }

    public String getLicenseImg(int victims_id){
        String img_l = "";
        String queryString = "SELECT * FROM " + ACCIDENT_INFO_TABLE + "WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            img_l = cursor.getString(4);
        }
        cursor.close();
        db.close();
        return img_l;
    }

}
