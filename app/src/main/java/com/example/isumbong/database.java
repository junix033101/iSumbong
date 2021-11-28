package com.example.isumbong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class database extends SQLiteOpenHelper {


    private static final String LIST_OF_VICTIMS_TABLE = "VICTIMS_INFO_TABLE";
    private static final String LIST_OF_VICTIMS_ID = "LIST_OF_VICTIMS_ID";
    private static final String AGE = "AGE";
    private static final String GENDER = "GENDER";
    private static final String VICTIMS_ID = "VICTIMS_ID";
    private static final String NAME = "NAME";

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



    public database(@Nullable Context context) {
        super(context, "isumbong.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    //null
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
//
//
//    public boolean update(int num, int id){
//        boolean check = true;
//        String query ="UPDATE VICTIMS_TABLE SET NUMBER_OF_VICTIMS ='"+num+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public Cursor updateVictimsInfo(int id){
//        String query = "UPDATE * FROM VICTIMS_TABLE WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//        return cursor;
//    }
//
//    public void updateAccImg(String aImg, int id){
//        boolean check = true;
//        String query = "UPDATE ACCIDENT_INFO_TABLE SET ACCIDENT_IMG='"+aImg+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//    }
//    public boolean updatelicImg(String lImg,int id){
//        boolean check = true;
//        String query = "UPDATE ACCIDENT_INFO_TABLE SET LICENSE_IMG='"+lImg+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updateCrImg(String cImg,int id){
//        boolean check = true;
//        String query = "UPDATE VEHICLE_INFO_TABLE SET VEHICLE_IMG='"+cImg+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updateOrImg(String oImg,int id){
//        boolean check = true;
//        String query = "UPDATE VEHICLE_INFO_TABLE SET OR_IMG='"+oImg+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updateLic(String lic,int id){
//        boolean check = true;
//        String query = "UPDATE ACCIDENT_INFO_TABLE SET LICENSE_NUMBER ='"+lic+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updatePlate(String plate,int id){
//        boolean check = true;
//        String query = "UPDATE VEHICLE_INFO_TABLE SET PLATE_NUMBER ='"+plate+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updateType(String type,int id){
//        boolean check = true;
//        String query = "UPDATE VEHICLE_INFO_TABLE SET VEHICLE_TYPE ='"+type+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updateLocation(double lat,double lng, String loc, int id){
//        boolean check = true;
//        String query = "UPDATE LOCATION_TABLE SET ADDRESS_PIN='"+loc+"', LATITUDE ='"+lat+"', LONGITUDE ='"+lng+"'  WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updateStatement(String s,int id){
//        boolean check = true;
//        String query = "UPDATE STATEMENT_TABLE SET STATEMENT ='"+s+"' WHERE VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//    public boolean updateIReport(String s,String serial){
//        boolean check = true;
//        String query = "UPDATE INCIDENT_REPORT_TABLE SET STATEMENT ='"+s+"' WHERE REPORT_SERIAL = '"+serial+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }
//
//    public ArrayList<Integer> getVictimInfoIds(int id){
//        ArrayList<Integer> ids = new ArrayList<>();
//        String queryString = "SELECT * FROM " + LIST_OF_VICTIMS_TABLE + " WHERE VICTIMS_ID = '"+id+"'" ;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(queryString, null);
//        if (cursor.moveToFirst()) {
//            do{
//                ids.add(cursor.getInt(0));
//            }while(cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return ids;
//    }
//
//    public boolean updateVictimsInfo(Victim victim, int id){
//        String name= victim.getName();
//        String gender = victim.getGender();
//        String age= victim.getAge();
//        boolean check = true;
//        String query ="UPDATE VICTIMS_INFO_TABLE SET NAME ='"+name+"',GENDER = '"+gender+"',AGE = '"+age+"'  WHERE LIST_OF_VICTIMS_ID = '"+id+"'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        db.execSQL(query);
//        return check;
//    }







}
