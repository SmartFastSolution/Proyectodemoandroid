package com.ec.proyectodemoandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void bnt_Ingresar(View view) {
        Intent intent = new Intent(this, activity_menu.class);
        startActivity(intent);
    }
}