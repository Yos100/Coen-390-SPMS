package com.spm.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UserAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        getSupportActionBar().setTitle("Account");
    }
}