package com.cnergee.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.customview.MyButtonView;
import com.cnergee.service.customview.MyTextView;

public class AllinOne extends AppCompatActivity {

    Button gps_location_activity, Ekyc_activity, imagetopdf;
    MyButtonView e_signature;
    MyTextView gps_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allinone);

        gps_location_activity = (Button)findViewById(R.id.gps_location_activity);
        //gps_location_activity.setClickable(true);
        Ekyc_activity = (Button)findViewById(R.id.Ekyc_activity);
        imagetopdf = (Button)findViewById(R.id.imagetopdf);

        e_signature = (MyButtonView) findViewById(R.id.e_signature);

        gps_location = (MyTextView)findViewById(R.id.gps_location);


        gps_location_activity.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //AllinOne.this.finish();
                Intent dashboardIntent= new Intent(AllinOne.this,MapsActivity.class);
                startActivity(dashboardIntent);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("gps", 0); // 0 - for private mode
                sharedPreferences.getString("address","");
                if (!sharedPreferences.getString("address","").equals("")){
                    gps_location.setText(sharedPreferences.getString("address",""));
                }
                 else{
                    gps_location.setText("Click on GPS Tracking");
                }
            }
        });

        Ekyc_activity.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //AllinOne.this.finish();
                Intent dashboardIntent= new Intent(AllinOne.this,activity_ekyc.class);
                startActivity(dashboardIntent);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("gps", 0); // 0 - for private mode
                sharedPreferences.getString("address","");
                if (!sharedPreferences.getString("address","").equals("")){
                    gps_location.setText(sharedPreferences.getString("address",""));
                }
                else{
                    gps_location.setText("Click on GPS Tracking");
                }
            }
        });



        imagetopdf.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //AllinOne.this.finish();
                Intent dashboardIntent= new Intent(AllinOne.this,ImagestoPdf.class);
                startActivity(dashboardIntent);

            }
        });


        e_signature.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //AllinOne.this.finish();
                Intent dashboardIntent= new Intent(AllinOne.this,Ecature.class);
                startActivity(dashboardIntent);

            }
        });
    }





}
