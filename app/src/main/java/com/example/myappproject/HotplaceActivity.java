package com.example.myappproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myappproject.dto.HotplaceDto;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

public class HotplaceActivity extends Activity {

    Gallery gallery;
    //Integer[] hpId = MainActivity.hotplaceDao.select();
    ArrayList<HotplaceDto> list = MainActivity.hotplaceDao.select();
    ImageView ivMap, ivPerson, ivBack, ivPoster;
    TextView tvName, tvLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotplace);

        gallery = (Gallery) findViewById(R.id.hp_gallery);
        ivMap = (ImageView) findViewById(R.id.ivMap);
        ivPerson = (ImageView) findViewById(R.id.ivPerson);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        tvName = (TextView) findViewById(R.id.tvName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);

        ivPoster.setClipToOutline(true);

        tvName.setText(list.get(0).getPname());
        tvLocation.setText(list.get(0).getPlocation() + ", " + list.get(0).getPcountry());

        MyGalleryAdapter adapter = new MyGalleryAdapter(this);
        gallery.setAdapter(adapter);

        //gallery의 아이템을 클릭하면 ivPoster 창에 영화포스터 출력하는 이벤트 처리
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "ddd", Toast.LENGTH_SHORT).show();
                ivPoster.setImageResource(list.get(position).getPid());
                tvName.setText(list.get(position).getPname());
                tvLocation.setText(list.get(position).getPlocation() + ", " + list.get(position).getPcountry());
//                ivPoster.setImageResource(posterId[position]);
//                ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        });

        ivMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(mapIntent);
            }
        });

        ivPerson.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               //System 종료
               //finish() -- 현재 액티비티만 종료
               AlertDialog.Builder dlg = new AlertDialog.Builder(HotplaceActivity.this);
               dlg.setTitle("confirm exit");
               dlg.setIcon(R.drawable.exit);
               dlg.setMessage("종료하시겠습니까?");
               dlg.setPositiveButton("exit", new DialogInterface.OnClickListener(){
                  @Override
                  public void onClick(DialogInterface dialog, int witch){
                      finishAffinity();
                      System.runFinalization(); //현재 실행 중인 모든 앱을 종료
                      System.exit(0);
                  }
               });
               dlg.setNegativeButton("logout", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                       startActivity(intent);
                       finish();
                   }
               });
               dlg.show();
           }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //MyGalleryAdapter 클래스 정의 : inner class
    class MyGalleryAdapter extends BaseAdapter {  //MainActivity$MyGalleryAdapter
        Context context;

        public MyGalleryAdapter(){}
        public MyGalleryAdapter(Context context){
            this.context = context;
        }

        //Adapter 기능을 구현하기 위한 4가지 메소드 정의 --> API를 참조하여 Adapter 클래스의 메소드
        public int getCount(){
            return list.size();
        }

        public Object getItem(int position){
            return null;
        }

        public long getItemId(int position){
            return 0;
        }

        public View getView(int position, View counterView, ViewGroup parent){
            ImageView imgView = new ImageView(context);
            imgView.setLayoutParams(new Gallery.LayoutParams(150, 150));
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgView.setBackground(getResources().getDrawable(R.drawable.imageborder));
            imgView.setClipToOutline(true);
            imgView.setImageResource(list.get(position).getPid());

            return imgView;
        }
    }//MyGalleryAdapter
}
