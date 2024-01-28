package com.spm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class user_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        ImageView googlelogo = (ImageView)findViewById(R.id.googlelogo);
        ImageView facebooklogo= (ImageView)findViewById(R.id.facebooklogo);



        //admin admin
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    openMainActivity();


                }else{
                    Toast.makeText(user_login.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        googlelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGoogleLogin();
            }
        });
        facebooklogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebookLogin();

            }
        });

    }
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
    public void openGoogleLogin(){
        Intent intent = new Intent(this, GoogleLogin.class);
        startActivity(intent);
    }
    public void openFacebookLogin(){
        Intent intent = new Intent(this, FacebookLogin.class);
        startActivity(intent);
    }
}