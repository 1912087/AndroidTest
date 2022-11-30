package com.example.myappproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MemberDao {

    SQLiteDatabase db;
    String tableName;

    public MemberDao(SQLiteDatabase db, String tableName){
        this.db = db;
        this.tableName = tableName;
    }

    //table Create
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (EMAIL TEXT NOT NULL, " +
                " PASS TEXT NOT NULL);";
        db.execSQL(sql);
    }

    //login
    public int login(MemberDto memberDto){
        int result = 0;
        String sql = "SELECT COUNT(*) FROM " + tableName +
                " WHERE EMAIL = '" + memberDto.getEmail() +
                "' AND PASS = '" + memberDto.getPass() + "';";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            result = cursor.getInt(0);
        }

        return result;
    }

    //Email Check
    public int emailCheck(MemberDto memberDto){
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE EMAIL = '" + memberDto.getEmail() + "';";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            return cursor.getInt(0);
        }

        return 0;
    }

    //Sign up
    public void signUp(MemberDto memberDto){
        String sql = "INSERT INTO " + tableName + "(EMAIL, PASS) VALUES('" +
                memberDto.getEmail() + "', '" +
                memberDto.getPass() + "');";
        db.execSQL(sql);
    }
}
