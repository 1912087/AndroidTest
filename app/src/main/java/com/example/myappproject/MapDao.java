package com.example.myappproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MapDao {

    SQLiteDatabase db;
    String tableName;
    Integer[] pidList = {R.drawable.hp_01, R.drawable.hp_02, R.drawable.hp_03, R.drawable.hp_04,
            R.drawable.hp_05,R.drawable.hp_06};


    String[] latList = {"25.310778683847857"};
    String[] lngList = {"51.42442216908817"};

    public MapDao(SQLiteDatabase db, String tableName){
        this.db = db;
        this.tableName = tableName;
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

    public void update_img(){
        for(int i=0; i<pidList.length; i++){
            String sql = "UPDATE map SET IMG = '" + pidList[i] + "' WHERE MID = " + (i+1);
            db.execSQL(sql);
        }
    }

    public MapDto search(String search){
        MapDto mapDto = new MapDto();
        String sql = "SELECT MID, LOCATION, COUNTRY, CONTENT, IMG, LAT, LNG " +
                " FROM map WHERE (LOCATION LIKE '%" + search + "%' OR COUNTRY LIKE '%" + search + "%');";
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
