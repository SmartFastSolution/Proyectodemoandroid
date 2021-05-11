package com.ec.proyectodemoandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ec.proyectodemoandroid.controllers.AdministracionFiles;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        try {
            String[] perms = {"android.permission.INTERNET"
                    ,"android.permission.ACCESS_WIFI_STATE"
                    ,"android.permission.ACCESS_COARSE_LOCATION"
                    ,"android.permission.ACCESS_FINE_LOCATION"
                    ,"android.permission.CAMERA"
                    ,"android.permission.READ_EXTERNAL_STORAGE"
                    ,"android.permission.WRITE_EXTERNAL_STORAGE"
                    ,"android.permission.READ_INTERNAL_STORAGE"
                    ,"android.permission.WRITE_INTERNAL_STORAGE"
                    ,"android.permission.BLUETOOTH"
                    ,"android.permission.BLUETOOTH_ADMIN"};
            int permsRequestCode = 200;
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, permsRequestCode);
            }*/

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    Log.i("Permisos", "Se tienen los permisos!");
                } else {
                    //ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION }, 1222);
                    ActivityCompat.requestPermissions(this, perms, permsRequestCode);
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        Log.i("Permisos", "Se tienen los permisos!");
                    }
                }
            }



        } catch (Exception permEx) { }
    }

    public void bnt_inicio(View view) {
        Intent intent = new Intent(this, activity_login.class);
        startActivity(intent);
    }
}