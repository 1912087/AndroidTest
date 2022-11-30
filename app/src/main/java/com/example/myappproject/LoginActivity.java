package com.example.myappproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class LoginActivity extends Activity {

    ImageView btnSignUp;
    EditText editEmail, editPass;
    ImageView btnLogin;

    //DB
    SQLiteDatabase db;
    String tableName = "member";

    static MemberDao memberDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);
        btnLogin = (ImageView) findViewById(R.id.btnLogin);

        //DB 연동
        db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        memberDao = new MemberDao(db, tableName);
        memberDao.createTable();

        btnSignUp = (ImageView) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(getApplicationContext(), com.example.myappproject.SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               if(valiCheck()){
                   AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
                   MemberDto memberDto = new MemberDto();
                   memberDto.setEmail(editEmail.getText().toString());
                   memberDto.setPass(editPass.getText().toString());
                   int result = memberDao.login(memberDto);

                   if(result == 1){
                       dlg.setMessage("로그인 성공");
                       dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(getApplicationContext(), "로그인!", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }else{
                       dlg.setMessage("로그인 실패");
                       dlg.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Toast.makeText(getApplicationContext(), "로그인...실패...", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
                   dlg.show();
               }
           }
        });

    }

    //Validation Check
    public boolean valiCheck(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
        if(editEmail.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            editEmail.requestFocus();
            return false;
        }else if(editPass.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            editPass.requestFocus();
            return false;
        }else{
            return true;
        }
    }
}
