package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button getLoc;
    Handler handler;
    double lat=0;
    double lon=0;
    int delay = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        getLoc = findViewById(R.id.btn1);
        handler = new Handler();
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPStracker gpStracker = new GPStracker(getApplicationContext());
                Location l = gpStracker.getLocation();
                if (l!=null){
                    lat = l.getLatitude();
                    lon = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "Latitude: "+lat+"\nLongitude: "+lon, Toast.LENGTH_LONG).show();
                }
            }
        });
        handler.postDelayed(new Runnable(){
            public void run(){
                if(lat!=0&&lon!=0){
                    GPStracker gpStracker2 = new GPStracker(getApplicationContext());
                    Location l2 = gpStracker2.getLocation();
                    double lat2 = l2.getLatitude();
                    double lon2 = l2.getLongitude();
                    double area = 0.00000000000000002;
                    if(lat2<lat-area || lat2>lat+area || lon2<lon-area || lon2>lon+area){
                        Toast.makeText(getApplicationContext(), "You're out of Your area!", Toast.LENGTH_SHORT).show();
                    }
                }
                handler.postDelayed(this, delay);
            }
        }, delay);


    }
}
