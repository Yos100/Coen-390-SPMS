package com.spm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class FacebookLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbarc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        TextView facebookusername = (TextView) findViewById(R.id.facebookusername);
        TextView facebookpassword = (TextView) findViewById(R.id.facebookpassword);

        MaterialButton facebooklogin = (MaterialButton) findViewById(R.id.facebooklogin);

        facebooklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(facebookusername.getText().toString().equals("admin") && facebookpassword.getText().toString().equals("admin")){
                    openMainActivity();
                }else{
                    Toast.makeText(FacebookLogin.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}