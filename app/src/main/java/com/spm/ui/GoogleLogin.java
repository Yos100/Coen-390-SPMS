package com.spm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class GoogleLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbarb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView email= (TextView) findViewById(R.id.email);
        TextView googlepass= (TextView) findViewById(R.id.googlepass);

        MaterialButton login = (MaterialButton) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("admin@gmail.com") && googlepass.getText().toString().equals("admin")){
                    openMainActivity();
                }else{
                    Toast.makeText(GoogleLogin.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}