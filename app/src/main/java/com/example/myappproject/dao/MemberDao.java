package com.example.myappproject.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myappproject.dto.MemberDto;

public class MemberDao {

    SQLiteDatabase db;
    public MemberDao(SQLiteDatabase db){
        this.db = db;
    }
    int version = 2;

    //table Create
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS member " +
                " (EMAIL TEXT NOT NULL, " +
                " PASS TEXT NOT NULL, " +
                " PHONE TEXT);";
        db.execSQL(sql);
    }

    /*public void onUpgrade(){
        db.execSQL("ALTER TABLE member ADD PHONE TEXT");
    }*/

    //login
    public int login(MemberDto memberDto){
        int result = 0;
        String sql = "SELECT COUNT(email) FROM member " +
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
        String sql = "SELECT COUNT(*) FROM member WHERE EMAIL = '" + memberDto.getEmail() + "';";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            return cursor.getInt(0);
        }

        return 0;
    }

    //Sign up
    public void signUp(MemberDto memberDto){
        String sql = "INSERT INTO member(EMAIL, PASS) VALUES('" +
                memberDto.getEmail() + "', '" +
                memberDto.getPass() + "');";
        db.execSQL(sql);
    }
}
