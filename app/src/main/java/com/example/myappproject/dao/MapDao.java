package com.example.myappproject.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myappproject.MainActivity;
import com.example.myappproject.MapActivity;
import com.example.myappproject.dto.MapDto;
import com.example.myappproject.R;

import java.util.ArrayList;

public class MapDao {

    SQLiteDatabase db;
    String tableName;
    Integer[] pidList = {R.drawable.icon01, R.drawable.icon02, R.drawable.icon03, R.drawable.icon04,
            R.drawable.icon05, R.drawable.icon06};

    public MapDao(SQLiteDatabase db, String tableName){
        this.db = db;
        this.tableName = tableName;

        if(imgCount() == 0){
            insert();
        }
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (MID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "  LOCATION TEXT NOT NULL, " +
                "  TOURIST TEXT NOT NULL, " +
                "  COUNTRY TEXT NOT NULL, " +
                "  CONTENT TEXT, " +
                "  IMG INTEGER, " +
                "  LAT TEXT, " +
                "  LNG TEXT );";
        db.execSQL(sql);
    }

    public void insert(){
        ArrayList<MapDto> list = MainActivity.mapList;
        for(int i=0; i<pidList.length; i++) {
            String sql = "INSERT INTO " + tableName + "(LOCATION, TOURIST, COUNTRY, CONTENT, IMG, LAT, LNG) " +
                    " VALUES('" + list.get(i).getLocation() + "', '" +
                    list.get(i).getTourist() + "', '" +
                    list.get(i).getCountry() + "', '" +
                    list.get(i).getContent() + "', " + pidList[i] + ", '" +
                    list.get(i).getLat() + "', '" +
                    list.get(i).getLng() + "')";
            db.execSQL(sql);
        }
    }

    public int imgCount(){
        int result = 0;
        String sql = "SELECT COUNT(*) FROM " + tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            result = cursor.getInt(0);
        }
        return result;
    }

    public MapDto search(String search){
        MapDto mapDto = new MapDto();
        String sql = "SELECT MID, LOCATION, TOURIST, COUNTRY, CONTENT, IMG, LAT, LNG " +
                " FROM " + tableName + " WHERE LOWER(LOCATION) LIKE LOWER('%" + search + "%');";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            mapDto.setMid(cursor.getInt(0));
            mapDto.setLocation(cursor.getString(1));
            mapDto.setTourist(cursor.getString(2));
            mapDto.setCountry(cursor.getString(3));
            mapDto.setContent(cursor.getString(4));
            mapDto.setImg(Integer.parseInt(cursor.getString(5)));
            mapDto.setLat(cursor.getString(6));
            mapDto.setLng(cursor.getString((7)));
            mapDto.setCount(cursor.getCount());
        }

        return mapDto;
    }
}
