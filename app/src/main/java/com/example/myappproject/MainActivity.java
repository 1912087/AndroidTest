package com.example.myappproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //DB
    static SQLiteDatabase db;
    static MemberDao memberDao; //해당 프로젝트 내에서는 전부 호출할 수 있음 (단, '클래스명.객체명', '클래스명.메소드명' 형태로 호출할 것)
    static HotplaceDao hotplaceDao;
    static MapDao mapDao;
    String hotTableName = "hotplace";
    String mapTableName = "map";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DB 연동
        db = openOrCreateDatabase("gotour", MODE_PRIVATE, null);
        memberDao = new MemberDao(db);
        hotplaceDao = new HotplaceDao(db, hotTableName);
        mapDao = new MapDao(db, mapTableName);
        memberDao.createTable();
        hotplaceDao.createTable();
        mapDao.createTable();

        ImageView tvStart = (ImageView) findViewById(R.id.tvStart);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "고투어 시작", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        
    }
}