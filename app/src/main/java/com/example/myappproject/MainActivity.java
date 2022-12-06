package com.example.myappproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myappproject.dao.HotplaceDao;
import com.example.myappproject.dao.MapDao;
import com.example.myappproject.dao.MemberDao;
import com.example.myappproject.dto.HotplaceDto;
import com.example.myappproject.dto.MapDto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //DB
    static SQLiteDatabase db;
    static MemberDao memberDao; //해당 프로젝트 내에서는 전부 호출할 수 있음 (단, '클래스명.객체명', '클래스명.메소드명' 형태로 호출할 것)
    static HotplaceDao hotplaceDao;
    static MapDao mapDao;
    String hotTableName = "hotplace";
    String mapTableName = "map";
    static public ArrayList<MapDto> mapList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Json Parsing
        mapList = jsonParsing(getJson());
        //Log.d("result", " ---> " + mapList.get(0).getLocation());

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
    }//onCreate

    public String getJson(){
        String json = "";

        try {
            InputStream is = getAssets().open("Map.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer , "UTF-8");

        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }

    public ArrayList<MapDto> jsonParsing(String json){
        ArrayList<MapDto> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jarray = jsonObject.getJSONArray("maps");

            for(int i=0; i<jarray.length(); i++){
                JSONObject jobject = jarray.getJSONObject(i);

                MapDto dto = new MapDto();
                dto.setLocation(jobject.getString("location"));
                dto.setTourist(jobject.getString("tourist"));
                dto.setCountry(jobject.getString("country"));
                dto.setContent(jobject.getString("content"));
                dto.setLat(jobject.getString("lat"));
                dto.setLng(jobject.getString("lng"));

                list.add(dto);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}