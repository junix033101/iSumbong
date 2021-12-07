package com.example.isumbong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class database extends SQLiteOpenHelper {
    private static final String CODE_TABLE = "CODE_TABLE";
    private static final String ID = "ID";
    private static final String PNP_SECTOR = "PNP_SECTOR";
    private static final String CODE = "CODE";

    private static final String CHIEF_CODE_TABLE = "CHIEF_CODE_TABLE";

    private static final String ADMIN_TABLE = "ADMIN_TABLE";
    private static final String USER = "USERNAME";
    private static final String PASS = "PASSWORD";
    private static final String NAME = "NAME";
    private static final String ID_NO = "ID_NO";
    private static final String EMAIL = "EMAIL";
    private static final String PROFIlE_IMG = "PROFILE_IMG";

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
    private static final String DATE = "DATE";

    private static final String VERIFIED_SERIAL_TABLE= "VERIFIED_SERIAL_TABLE";
    private static final String VERIFIED_SERIAL_ID = "VERIFIED_SERIAL_ID";
    private static final String DATE_VERIFIED = "DATE_VERIFIED";

    private static final String INCIDENT_REPORT_TABLE= "INCIDENT_REPORT_TABLE";
    private static final String INCIDENT_TYPE = "INCIDENT_TYPE";
    private static final String REPORT_ID = "REPORT_ID";
    private static final String OFFICER= "OFFICER";
    private static final String ATTACHED_SERIAL = "ATTACHED_SERIAL";
    private static final String REPORT_SERIAL = "REPORT_SERIAL";

    private static final String EDIT_TABLE= "EDIT_TABLE";
    private static final String EDIT_ID = "EDIT_ID";
    private static final String DATE_EDITED = "DATE_EDITED";

    private static final String FILES_TABLE= "FILES_TABLE";
    private static final String FILE_ID = "FILES_ID";
    private static final String PATH = "PATH";

//    private static final String OFFENSES_TABLE= "OFFENSES_TABLE";
//    private static final String OFFENSE_ID = "OFFENSE_ID";
//    private static final String OFFENSES = "OFFENSES";
//    private static final String REPORTS = "REPORTS";





    public database(@Nullable Context context) {
        super(context, "isumbong.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createCode = "CREATE TABLE " + CODE_TABLE + " (" + ID + " INTEGER PRIMARY KEY, " + PNP_SECTOR+ " TEXT, " + CODE + " TEXT)";
        db.execSQL(createCode);

        String createChiefCode = "CREATE TABLE " + CHIEF_CODE_TABLE + " (" + ID + " INTEGER PRIMARY KEY, " + PNP_SECTOR+ " TEXT, " + CODE + " TEXT)";
        db.execSQL(createChiefCode);

        String createDefaultSec = "INSERT INTO CODE_TABLE(PNP_SECTOR, CODE)\n" +
                "VALUES ('Cebu Provincial Jail','N9TT-9G0A'),('CITOM','QK6A-JI6S')";
        db.execSQL(createDefaultSec);

        String createDefChiefCode = "INSERT INTO CHIEF_CODE_TABLE(PNP_SECTOR, CODE)\n" +
                "VALUES ('Cebu Provincial Jail','4GE4-ETEV'),('CITOM','2YNV-3L4Y')";
        db.execSQL(createDefChiefCode);

        String createAdmin = "CREATE TABLE " + ADMIN_TABLE + " (" + USER + " TEXT PRIMARY KEY, " + PASS + " TEXT, " + NAME + " TEXT, " + ID_NO + " TEXT,"+EMAIL+" TEXT, " +PROFIlE_IMG+" )";
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

        String createSerial = "CREATE TABLE " + SERIAL_TABLE + " (" + SERIAL_ID + " INTEGER PRIMARY KEY," + VICTIMS_ID + " INT, " + SERIAL_NUMBER + " TEXT,"+DATE+" TEXT)";
        db.execSQL(createSerial);

        String createVerifiedSerial = "CREATE TABLE " + VERIFIED_SERIAL_TABLE + " (" + VERIFIED_SERIAL_ID + " INTEGER PRIMARY KEY," + VICTIMS_ID + " INT, "+ SERIAL_NUMBER + " TEXT,"+DATE_VERIFIED+" TEXT, "+USER+" TEXT)";
        db.execSQL(createVerifiedSerial);

        String createIncidentReport = "CREATE TABLE " + INCIDENT_REPORT_TABLE + " (" + REPORT_ID + " INTEGER PRIMARY KEY," + REPORT_SERIAL + " TEXT," + INCIDENT_TYPE + " TEXT, "+ OFFICER + " TEXT,"+ EMAIL + " TEXT,"+PNP_SECTOR+" TEXT, "+DATE+" TEXT, "+STATEMENT+" TEXT,"+
        ATTACHED_SERIAL+" TEXT)";
        db.execSQL(createIncidentReport);

        String createEditedReport = "CREATE TABLE " + EDIT_TABLE + " (" + EDIT_ID + " INTEGER PRIMARY KEY," + SERIAL_NUMBER + " TEXT, "+ OFFICER + " TEXT,"+DATE_EDITED+" TEXT)";
        db.execSQL(createEditedReport);

        String createDls = "CREATE TABLE " + FILES_TABLE + " (" + FILE_ID + " INTEGER PRIMARY KEY," + SERIAL_NUMBER + " TEXT, "+ USER + " TEXT,"+PATH+" )";
        db.execSQL(createDls);
//
//        String createOffenses = "CREATE TABLE " + OFFENSES_TABLE + " (" + OFFENSE_ID + " INTEGER PRIMARY KEY,"+LICENSE_NUMBER+" TEXT, "+ REPORTS + " TEXT, "+ OFFENSES + " TEXT, "+DATE+" TEXT )";
//        db.execSQL(createOffenses);

        String createOffensesList = "INSERT INTO OFFENSES_TABLE(LICENSE_NUMBER, OFFENSES, DATE)\n" +
                "VALUES ('DO6-11-009385','Reckless Driving','Oct 16, 2021'), ('DO6-11-009385','Drunk Driving','Aug 08, 2020'), ('DO6-11-009385','Speeding', 'March 31, 2019')";
        db.execSQL(createOffensesList);
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
    public String getChiefCode(String sector) {
        String check = "";
        String queryString = "SELECT * FROM " + CHIEF_CODE_TABLE + " WHERE PNP_SECTOR ='" + sector + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            check= cursor.getString(2);
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

    public boolean InsertSerial(int victims_id, String serial, String date){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VICTIMS_ID, victims_id);
        cv.put(SERIAL_NUMBER, serial);
        cv.put(DATE, date);
        long insert = db.insert(SERIAL_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }

    public boolean InsertVerifiedSerial(int victims_id, String serial, String date,String user){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(VICTIMS_ID, victims_id);
        cv.put(SERIAL_NUMBER, serial);
        cv.put(DATE_VERIFIED,date);
        cv.put(USER,user);
        long insert = db.insert(VERIFIED_SERIAL_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }

    public boolean InsertIncidentReport(String type, String officer, String email, String sector,
    String date, String statement, String serial, String reportS){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(INCIDENT_TYPE, type);
        cv.put(OFFICER, officer);
        cv.put(EMAIL, email);
        cv.put(PNP_SECTOR, sector);
        cv.put(DATE , date);
        cv.put(STATEMENT, statement);
        cv.put(ATTACHED_SERIAL, serial);
        cv.put(REPORT_SERIAL, reportS);
        long insert = db.insert(INCIDENT_REPORT_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;

        return check;
    }

//    public boolean InsertOffenses (String lic,String date,String report){
//        boolean check = false;
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(LICENSE_NUMBER, lic);
//        cv.put(DATE, date);
//        cv.put(REPORTS, report);
//        long insert = db.insert(OFFENSES_TABLE, null, cv);
//        if (insert == -1) {
//            check = false;
//        } else
//            check = true;
//
//        return check;
//    }

    public boolean InsertPDFs(String serial, String user,String path){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SERIAL_NUMBER, serial);
        cv.put(USER, user);
        cv.put(PATH,path);
        long insert = db.insert(FILES_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }

    public boolean InsertEdits(String serial, String officer,String date){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SERIAL_NUMBER, serial);
        cv.put(OFFICER, officer);
        cv.put(DATE_EDITED,date);
        long insert = db.insert(EDIT_TABLE, null, cv);
        if (insert == -1) {
            check = false;
        } else
            check = true;
        return check;
    }

    public boolean updateEdit(String serial, String date){
        boolean check = true;
        String query ="UPDATE EDIT_TABLE SET DATE_EDITED ='"+date+"' WHERE SERIAL_NUMBER = '"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public void deleteEdit(String serial){
        String query = "DELETE FROM EDIT_TABLE WHERE SERIAL_NUMBER ='"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
    }


    public boolean updateImg(String profile, String user){
        boolean check = true;
        String query ="UPDATE ADMIN_TABLE SET PROFILE_IMG ='"+profile+"' WHERE USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }

    public String getProfile(String user){
        String img = "";
        String queryString = "SELECT * FROM " + ADMIN_TABLE + " WHERE USERNAME = '"+user+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            img = cursor.getString(5);
        }
        cursor.close();
        db.close();
        return img;
    }

    public int getSerialID(String serial){
        int serial_id = 0;
        String queryString = "SELECT * FROM " + SERIAL_TABLE + " WHERE SERIAL_NUMBER = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            serial_id = cursor.getInt(2);
        }
        cursor.close();
        db.close();
        return serial_id;
    }

    public ArrayList<String> getSerial(){
        ArrayList<String> serial = new ArrayList<>();
        String query = "SELECT SERIAL_NUMBER FROM SERIAL_TABLE ORDER BY SERIAL_NUMBER asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                serial.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return serial;
    }

    public ArrayList<String> getVerifiedSerial(){
        ArrayList<String> serial = new ArrayList<>();
        String query = "SELECT SERIAL_NUMBER FROM VERIFIED_SERIAL_TABLE ORDER BY SERIAL_NUMBER asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                serial.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return serial;
    }
    public int getIDxVserial(String vserial){
        int victims_id = 0;
        String queryString = "SELECT * FROM " + VERIFIED_SERIAL_TABLE + " WHERE SERIAL_NUMBER = '"+vserial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            victims_id = cursor.getInt(1);
        }
        cursor.close();
        db.close();
        return victims_id;
    }

    //get id based on serial
    public int getVictimxSerial(String serial){
        int victims_id = 0;
        String queryString = "SELECT * FROM " + SERIAL_TABLE + " WHERE SERIAL_NUMBER = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            victims_id = cursor.getInt(1);
        }
        cursor.close();
        db.close();
        return victims_id;
    }
    public String getNumVictims(int victims_id){
        String num = "";
        String queryString = "SELECT * FROM " + VICTIMS_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            num = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return num;
    }

    public ArrayList<Victim> getVictimsInfo(int victims_id){
        ArrayList<Victim> info = new ArrayList<>();
        String queryString = "SELECT * FROM " + LIST_OF_VICTIMS_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do{
                info.add(new Victim(cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return info;
    }

    public String getAccidentImg(int victims_id){
        String img_a = "";
        String queryString = "SELECT * FROM " + ACCIDENT_INFO_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            img_a = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return img_a;
    }
    public String getLicenseImg(int victims_id){
        String img_l = "";
        String queryString = "SELECT * FROM " + ACCIDENT_INFO_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            img_l = cursor.getString(4);
        }
        cursor.close();
        db.close();
        return img_l;
    }

    public String getLicenseNumber(int victims_id){
        String license = "";
        String queryString = "SELECT * FROM " + ACCIDENT_INFO_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            license = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return license;
    }

    public String getVehicleImg(int victims_id){
        String img_v = "";
        String queryString = "SELECT * FROM " + VEHICLE_INFO_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            img_v = cursor.getString(4);
        }
        cursor.close();
        db.close();
        return img_v;
    }
    public String getOrImg(int victims_id){
        String img_o = "";
        String queryString = "SELECT * FROM " + VEHICLE_INFO_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            img_o = cursor.getString(5);
        }
        cursor.close();
        db.close();
        return img_o;
    }

    public String getPlateNumber(int victims_id){
        String license = "";
        String queryString = "SELECT * FROM " + VEHICLE_INFO_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            license = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return license;
    }
    public String getVehicleType(int victims_id){
        String license = "";
        String queryString = "SELECT * FROM " +VEHICLE_INFO_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            license = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return license;
    }
    public String getStatement(int victims_id){
        String license = "";
        String queryString = "SELECT * FROM " + STATEMENT_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            license = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return license;
    }

    public double getLatitude(int victims_id){
        double lat = 0.0;
        String queryString = "SELECT * FROM " + LOCATION_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            lat = cursor.getDouble(3);
        }
        cursor.close();
        db.close();
        return lat;
    }

    public double getLongitude(int victims_id){
        double lng = 0.0;
        String queryString = "SELECT * FROM " + LOCATION_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            lng = cursor.getDouble(4);
        }
        cursor.close();
        db.close();
        return lng;
    }
    public String getLocation(int victims_id){
        String loc = "";
        String queryString = "SELECT * FROM " + LOCATION_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            loc = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return loc;
    }
    public String getDate(int victims_id){
        String date = "";
        String queryString = "SELECT * FROM " + SERIAL_TABLE + " WHERE VICTIMS_ID = '"+victims_id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            date = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return date;
    }

    public String getName(String user){
        String name = "";
        String queryString = "SELECT * FROM " + ADMIN_TABLE + " WHERE USERNAME = '"+user+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return name;

    }
    public String getPnpSector(String code){
        String sector = "";
        String queryString = "SELECT * FROM " + CODE_TABLE + " WHERE CODE = '"+code+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            sector = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return sector;
    }

    public String getVdate(int id){
        String vdate = "";
        String queryString = "SELECT * FROM " + VERIFIED_SERIAL_TABLE + " WHERE VICTIMS_ID = '"+id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            vdate = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return vdate;
    }
    public String getIdNo(String user){
        String id = "";
        String queryString = "SELECT * FROM " + ADMIN_TABLE + " WHERE USERNAME = '"+user+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            id = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return id;
    }
    public String getUser(int id){
        String user = "";
        String queryString = "SELECT * FROM " + VERIFIED_SERIAL_TABLE + " WHERE VICTIMS_ID = '"+id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            user = cursor.getString(4);
        }
        cursor.close();
        db.close();
        return user;
    }
    public String getEmail(String user){
        String email = "";
        String queryString = "SELECT * FROM " + ADMIN_TABLE + " WHERE USERNAME = '"+user+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            email = cursor.getString(4);
        }
        cursor.close();
        db.close();
        return email;
    }

    public ArrayList<String> getReportSerial(){
        ArrayList<String> serial = new ArrayList<>();
        String query = "SELECT REPORT_SERIAL FROM INCIDENT_REPORT_TABLE ORDER BY REPORT_SERIAL asc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                serial.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return serial;
    }
    public String getReportOfficer(String serial){
        String officer = "";
        String queryString = "SELECT * FROM " + INCIDENT_REPORT_TABLE + " WHERE REPORT_SERIAL = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
             officer = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return officer;
    }
    public String getReportEmail(String serial){
        String email = "";
        String queryString = "SELECT * FROM " + INCIDENT_REPORT_TABLE + "  WHERE REPORT_SERIAL = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            email = cursor.getString(4);
        }
        cursor.close();
        db.close();
        return email;
    }
    public String getReporSector(String serial){
        String sector = "";
        String queryString = "SELECT * FROM " + INCIDENT_REPORT_TABLE + "  WHERE REPORT_SERIAL = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            sector = cursor.getString(5);
        }
        cursor.close();
        db.close();
        return sector;
    }
    public String getReportDate(String serial){
        String date = "";
        String queryString = "SELECT * FROM " + INCIDENT_REPORT_TABLE + "  WHERE REPORT_SERIAL = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            date = cursor.getString(6);
        }
        cursor.close();
        db.close();
        return date;
    }
    public String getReportStatement(String serial){
        String s = "";
        String queryString = "SELECT * FROM " + INCIDENT_REPORT_TABLE + "  WHERE REPORT_SERIAL = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            s = cursor.getString(7);
        }
        cursor.close();
        db.close();
        return s;
    }
    public String getReportAttached(String serial){
        String att = "";
        String queryString = "SELECT * FROM " + INCIDENT_REPORT_TABLE + "  WHERE REPORT_SERIAL = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            att = cursor.getString(8);
        }
        cursor.close();
        db.close();
        return att;
    }
    public void DeleteVreport(String serial){
        String query = "DELETE FROM VERIFIED_SERIAL_TABLE WHERE SERIAL_NUMBER ='"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
    }
    public void DeleteIreport(String serial){
        String query = "DELETE FROM INCIDENT_REPORT_TABLE WHERE REPORT_SERIAL ='"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
    }

    public Cursor getRecentReport(){
        String query = "SELECT  *  FROM SERIAL_TABLE ORDER BY SERIAL_ID DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }
    public Cursor getRecentVerified(){
        String query = "SELECT  *  FROM VERIFIED_SERIAL_TABLE ORDER BY VERIFIED_SERIAL_ID DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }
    public Cursor getRecentIncedent(){
        String query = "SELECT  *  FROM INCIDENT_REPORT_TABLE ORDER BY REPORT_ID DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }
    public boolean update(int num, int id){
        boolean check = true;
        String query ="UPDATE VICTIMS_TABLE SET NUMBER_OF_VICTIMS ='"+num+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public Cursor updateVictimsInfo(int id){
        String query = "UPDATE * FROM VICTIMS_TABLE WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor;
    }

    public void updateAccImg(String aImg, int id){
        boolean check = true;
        String query = "UPDATE ACCIDENT_INFO_TABLE SET ACCIDENT_IMG='"+aImg+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
    }
    public boolean updatelicImg(String lImg,int id){
        boolean check = true;
        String query = "UPDATE ACCIDENT_INFO_TABLE SET LICENSE_IMG='"+lImg+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateCrImg(String cImg,int id){
        boolean check = true;
        String query = "UPDATE VEHICLE_INFO_TABLE SET VEHICLE_IMG='"+cImg+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateOrImg(String oImg,int id){
        boolean check = true;
        String query = "UPDATE VEHICLE_INFO_TABLE SET OR_IMG='"+oImg+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateLic(String lic,int id){
        boolean check = true;
        String query = "UPDATE ACCIDENT_INFO_TABLE SET LICENSE_NUMBER ='"+lic+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updatePlate(String plate,int id){
        boolean check = true;
        String query = "UPDATE VEHICLE_INFO_TABLE SET PLATE_NUMBER ='"+plate+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateType(String type,int id){
        boolean check = true;
        String query = "UPDATE VEHICLE_INFO_TABLE SET VEHICLE_TYPE ='"+type+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateLocation(double lat,double lng, String loc, int id){
        boolean check = true;
        String query = "UPDATE LOCATION_TABLE SET ADDRESS_PIN='"+loc+"', LATITUDE ='"+lat+"', LONGITUDE ='"+lng+"'  WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateStatement(String s,int id){
        boolean check = true;
        String query = "UPDATE STATEMENT_TABLE SET STATEMENT ='"+s+"' WHERE VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean updateIReport(String s,String serial){
        boolean check = true;
        String query = "UPDATE INCIDENT_REPORT_TABLE SET STATEMENT ='"+s+"' WHERE REPORT_SERIAL = '"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public String Username (String serial){
        String user = "";
        String queryString = "SELECT * FROM " + VERIFIED_SERIAL_TABLE + "  WHERE SERIAL_NUMBER = '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            user = cursor.getString(4);
        }
        cursor.close();
        db.close();
        return user;
    }
    public boolean Checker(String s) {
        boolean check = false;
        String queryString = "SELECT*FROM EDIT_TABLE WHERE SERIAL_NUMBER = '" + s + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            check = true;
        }
        cursor.close();
        db.close();
        return check;
    }

    public ArrayList<String> getPdfDls(String user){
        ArrayList<String> serial = new ArrayList<>();
        String query = "SELECT SERIAL_NUMBER FROM FILES_TABLE WHERE USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                serial.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return serial;
    }
    public String path(String user, String serial){
        String path = "";
        String queryString = "SELECT * FROM " + FILES_TABLE + " WHERE USERNAME = '"+user+"' AND SERIAL_NUMBER = '"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            path = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return path;
    }
    public String getEditDate(String serial){
        String date = "";
        String queryString = "SELECT * FROM " + EDIT_TABLE + " WHERE SERIAL_NUMBER = '"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            date = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return date;
    }
    public boolean CheckerFile(String s, String user) {
        boolean check = false;
        String queryString = "SELECT*FROM FILES_TABLE WHERE SERIAL_NUMBER = '" + s + "' AND  USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            check = true;
        }
        cursor.close();
        db.close();
        return check;
    }
    public boolean updateFile(String s,String user){
        boolean check = true;
        String query = "UPDATE FILES_TABLE SET SERIAL_NUMBER ='"+s+"' WHERE SERIAL_NUMBER = '"+s+"' AND  USERNAME = '"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }
    public boolean CheckAccident() {
        boolean check = false;
        String query = "SELECT  *  FROM SERIAL_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            check = true;
        }
        cursor.close();
        db.close();
        return check;
    }

    public boolean CheckVerified() {
        boolean check = false;
        String query = "SELECT  *  FROM VERIFIED_SERIAL_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            check = true;
        }
        cursor.close();
        db.close();
        return check;
    }
    public boolean CheckIncident() {
        boolean check = false;
        String query = "SELECT  *  FROM INCIDENT_REPORT_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            check = true;
        }
        cursor.close();
        db.close();
        return check;
    }

    public ArrayList<String> getOffenses(String lic){
        ArrayList<String> off = new ArrayList<>();
        String query = "SELECT OFFENSES, DATE FROM OFFENSES_TABLE WHERE LICENSE_NUMBER = '"+lic+"' AND OFFENSES IS NOT NULL";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
         int data = cursor.getColumnIndex("OFFENSES");
        int data1 = cursor.getColumnIndex("DATE");
        if(cursor.moveToFirst()){
            do{
                off.add(cursor.getString(data) + " ("+cursor.getString(data1)+")");
            }while(cursor.moveToNext());
        }
        return off;
    }


    public ArrayList<String> getReports(String lic){
        ArrayList<String> rep = new ArrayList<>();
        String query = "SELECT REPORTS,DATE FROM OFFENSES_TABLE WHERE LICENSE_NUMBER = '"+lic+"' AND REPORTS IS NOT NULL ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int data = cursor.getColumnIndex("REPORTS");
        int data1 = cursor.getColumnIndex("DATE");
        if(cursor.moveToFirst()){
            do{
                rep.add(cursor.getString(data) + " ("+cursor.getString(data1)+")");
            }while(cursor.moveToNext());
        }
        return rep;
    }

    public boolean CheckVerifiedSerial(String serial) {
        boolean check = false;
        String query = "SELECT  *  FROM VERIFIED_SERIAL_TABLE WHERE SERIAL_NUMBER = '"+serial+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            check = true;
        }
        cursor.close();
        db.close();
        return check;
    }
    public ArrayList<Integer> getVictimInfoIds(int id){
        ArrayList<Integer> ids = new ArrayList<>();
        String queryString = "SELECT * FROM " + LIST_OF_VICTIMS_TABLE + " WHERE VICTIMS_ID = '"+id+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do{
                ids.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ids;
    }

    public boolean updateVictimsInfo(Victim victim, int id){
        String name= victim.getName();
        String gender = victim.getGender();
        String age= victim.getAge();
        boolean check = true;
        String query ="UPDATE VICTIMS_INFO_TABLE SET NAME ='"+name+"',GENDER = '"+gender+"',AGE = '"+age+"'  WHERE LIST_OF_VICTIMS_ID = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(query);
        return check;
    }







}
