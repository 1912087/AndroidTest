package com.example.myappproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myappproject.dto.MemberDto;

public class LoginActivity extends Activity {

    ImageView btnSignUp;
    EditText editEmail, editPass;
    ImageView ivLogin, logExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);
        ivLogin = (ImageView) findViewById(R.id.ivLogin);
        logExit = (ImageView) findViewById(R.id.logExit);

        btnSignUp = (ImageView) findViewById(R.id.btnSignUp);
        
        //회원가입 페이지 이동
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(getApplicationContext(), com.example.myappproject.SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        //로그인
        ivLogin.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               if(valiCheck()){
                   AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
                   MemberDto memberDto = new MemberDto();
                   memberDto.setEmail(editEmail.getText().toString());
                   memberDto.setPass(editPass.getText().toString());
                   int result = MainActivity.memberDao.login(memberDto);

                   if(result == 1){
                       dlg.setMessage("로그인 성공");
                       dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Intent intent = new Intent(getApplicationContext(), HotplaceActivity.class);
                               startActivity(intent);
                               finish();
                           }
                       });
                   }else{
                       dlg.setMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
                       dlg.setNegativeButton("닫기", null);
                   }
                   dlg.show();
               }
           }
        });//ivLogin Click Event

        logExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });

    }//onCreate method

    //Validation Check
    public boolean valiCheck(){
        AlertDialog.Builder dlg = new AlertDialog.Builder(LoginActivity.this);
        if(editEmail.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
            editEmail.requestFocus();
            return false;
        }else if(editPass.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            editPass.requestFocus();
            return false;
        }else{
            return true;
        }
    }//valiCheck method
}//MainActivity
