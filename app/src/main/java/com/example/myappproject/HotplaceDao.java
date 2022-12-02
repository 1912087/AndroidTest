package com.example.myappproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HotplaceDao {

    SQLiteDatabase db;
    String tableName;
    Integer[] pidList = {R.drawable.hp_01, R.drawable.hp_02, R.drawable.hp_03, R.drawable.hp_04,
            R.drawable.hp_05,R.drawable.hp_06};
    String[] pnameList = {"Bangkok", "Bangkok", "Bangkok", "Bangkok", "Bangkok", "Bangkok"};

    public HotplaceDao(SQLiteDatabase db, String tableName){
        this.db = db;
        this.tableName = tableName;

        if(selectCount() == 0){
            insert();
        }
    }

    public void insert(){
        for(int i=0; i<pidList.length; i++){
            String sql = "INSERT INTO " + tableName + " (PID, PNAME) " +
                    " VALUES(" + pidList[i] + ", '" + pnameList[i] + "');";

            db.execSQL(sql);
        }
    }

    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (PID INTEGER PRIMARY KEY, " +
                "  PNAME TEXT NOT NULL, " +
                "  PCONTENT TEXT);";
        db.execSQL(sql);
    }

    public Integer[] select(){
        Integer[] pid = new Integer[6];
        int count = 0;
        String sql = "SELECT PID FROM " + tableName + ";";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            pid[count] = cursor.getInt(0);
            count++;
        }
        return pid;
    }
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
