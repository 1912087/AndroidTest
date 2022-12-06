package com.example.myappproject.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.example.myappproject.R;
import com.example.myappproject.dto.HotplaceDto;

import java.io.InputStream;
import java.util.ArrayList;

public class HotplaceDao {

    SQLiteDatabase db;
    String tableName;
    /*Integer[] pidList = {R.drawable.hp_01, R.drawable.hp_02, R.drawable.hp_03, R.drawable.hp_04,
            R.drawable.hp_05,R.drawable.hp_06};*/
    Integer[] pidList = {R.drawable.hotplace1, R.drawable.hotplace2, R.drawable.hotplace3, R.drawable.hotplace4,
            R.drawable.hotplace5,R.drawable.hotplace6};
    String[] pnameList = {"Education City Stadium", "Hanoi Opera House", "Big Ben", "de Young Museum", "Sydney Opera House", "Cristo Redentor"};
    String[] pLocation = {"Lusail", "Da Nang", "London", "California San Francisco", "Sydney", "Rio de Janeiro"};
    String[] pCountry = {"Qatar", "Vietnam", "UK", "USA", "Australia", "Brazil"};

    public HotplaceDao(SQLiteDatabase db, String tableName){
        this.db = db;
        this.tableName = tableName;

        if(selectCount() == 0){
            insert();
        }
    }

    public void insert(){
        for(int i=0; i<pidList.length; i++){
            String sql = "INSERT INTO " + tableName + " (PID, PNAME, PLOCATION, PCOUNTRY) " +
                    " VALUES(" + pidList[i] + ", '" + pnameList[i] + "', '"
                    + pLocation[i] + "', '" + pCountry[i] + "');";

            db.execSQL(sql);
        }
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (PID INTEGER PRIMARY KEY, " +
                "  PNAME TEXT NOT NULL, " +
                "  PLOCATION TEXT, " +
                "  PCOUNTRY TEXT, " +
                "  PCONTENT TEXT);";
        db.execSQL(sql);
    }

    public ArrayList<HotplaceDto> select(){
        ArrayList<HotplaceDto> list = new ArrayList<>();
        String sql = "SELECT PID, PNAME, PLOCATION, PCOUNTRY, PCONTENT FROM " + tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            HotplaceDto dto = new HotplaceDto();
            dto.setPid(cursor.getInt(0));
            dto.setPname(cursor.getString(1));
            dto.setPlocation(cursor.getString(2));
            dto.setPcountry(cursor.getString(3));
            dto.setPcontent(cursor.getString(4));

            list.add(dto);
        }

        return list;
    }

    /*public Integer[] select(){
        Integer[] pid = new Integer[6];
        int count = 0;
        String sql = "SELECT PID FROM " + tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            pid[count] = cursor.getInt(0);
            count++;
        }
        return pid;
    }*/
    //리턴타입, 파라미터 개수, 파라미터 있을 때 없을 때

    //반환되는 로우수가 없을 경우 Insert 하도록 진행
    public int selectCount(){
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            count = cursor.getInt(0);
        }

        return count;
    }

}
