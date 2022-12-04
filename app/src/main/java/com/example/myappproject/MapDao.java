package com.example.myappproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MapDao {

    SQLiteDatabase db;
    String tableName;
    Integer[] pidList = {R.drawable.icon01, R.drawable.icon02, R.drawable.icon03, R.drawable.icon04,
            R.drawable.icon05, R.drawable.icon06};


    String[] latList = {"25.310778683847857"};
    String[] lngList = {"51.42442216908817"};

    public MapDao(SQLiteDatabase db, String tableName){
        this.db = db;
        this.tableName = tableName;

        if(imgCount() == 0){
            update_img();
        }
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (MID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  LOCATION TEXT NOT NULL, " +
                "  COUNTRY TEXT NOT NULL, " +
                "  CONTENT TEXT, " +
                "  IMG INTEGER, " +
                "  LAT TEXT, " +
                "  LNG TEXT );";
        db.execSQL(sql);
    }

    public void update(){
        //String sql = "alter table map rename column lid to mid;";
        String sql = "alter table map add column img INTEGER";
        db.execSQL(sql);
    }

    public int imgCount(){
        int result = 0;
        String sql = "SELECT COUNT(IMG) FROM " + tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            result = cursor.getInt(0);
        }
        return result;
    }

    public void update_img(){
        for(int i=0; i<pidList.length; i++){
            String sql = "UPDATE map SET IMG = '" + pidList[i] + "' WHERE MID = " + (i+1);
            db.execSQL(sql);
        }
    }

    public MapDto search(String search){
        MapDto mapDto = new MapDto();
        String sql = "SELECT MID, LOCATION, COUNTRY, CONTENT, IMG, LAT, LNG " +
                " FROM " + tableName + " WHERE LOCATION LIKE '%" + search + "%';";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            mapDto.setMid(cursor.getInt(0));
            mapDto.setLocation(cursor.getString(1));
            mapDto.setCountry(cursor.getString(2));
            mapDto.setContent(cursor.getString(3));
            mapDto.setImg(Integer.parseInt(cursor.getString(4)));
            mapDto.setLat(cursor.getString(5));
            mapDto.setLng(cursor.getString((6)));
            mapDto.setCount(cursor.getCount());
        }

        return mapDto;
    }
}
