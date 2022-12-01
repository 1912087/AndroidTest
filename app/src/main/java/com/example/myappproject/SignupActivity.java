package com.example.myappproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.w3c.dom.Text;

public class SignupActivity extends Activity {

    EditText editEmail, editPass, editConPass;
    CheckBox chkAgree;
    ImageView btnSignUp, btnHome;
    TextView conCheck, emailCheck;

    //DB
    //SQLiteDatabase db;
    //String tableName = "member";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPass = (EditText) findViewById(R.id.editPass);
        editConPass = (EditText) findViewById(R.id.editConPass);
        chkAgree = (CheckBox) findViewById(R.id.chkAgree);
        btnSignUp = (ImageView) findViewById(R.id.btnSignUp);
        conCheck = (TextView) findViewById(R.id.conCheck);
        emailCheck = (TextView) findViewById(R.id.emailCheck);
        btnHome = (ImageView) findViewById(R.id.btnHome);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valiCheck()){
                    MemberDto memberDto = new MemberDto();
                    memberDto.setEmail(editEmail.getText().toString());
                    memberDto.setPass(editPass.getText().toString());
                    int result = MainActivity.memberDao.emailCheck(memberDto);
                    if(result == 0){
                        MainActivity.memberDao.signUp(memberDto);
                        AlertDialog.Builder dlg = new AlertDialog.Builder(SignupActivity.this);
                        dlg.setMessage("회원가입 성공");
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int witch){
                                finish();
                            }
                        });
                        dlg.show();
                    }else{
                        emailCheck.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //home(로그인) 이벤트 처리
        btnHome.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               finish();
           }
        });

    }//onCreate()

    //Validation Check
    public boolean valiCheck(){
        if(editEmail.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
            editEmail.requestFocus();
            return false;
        }else if(editPass.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            editPass.requestFocus();
            return false;
        }else if(editConPass.getText().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호 확인을 해주세요", Toast.LENGTH_SHORT).show();
            editConPass.requestFocus();
            return false;
        }else if(!chkAgree.isChecked()){
            Toast.makeText(getApplicationContext(), "약관에 동의해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            //패스워드 확인
            //값이 있는지 없는지 전부 체크한 후 비교할 것
            if(!editPass.getText().toString().equals(editConPass.getText().toString())) {
                conCheck.setVisibility(View.VISIBLE);
                editPass.setText("");
                editConPass.setText("");
                editPass.requestFocus();
                return false;
            }else{
                conCheck.setVisibility(View.GONE);
                return true;
            }
        }
    }
}
