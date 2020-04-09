package com.cnergee.service;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.ezetap.ApConfigConstants;
import com.cnergee.service.utils.MyUtils;

import com.ezetap.sdk.AppConstants;
import com.ezetap.sdk.EzetapApiConfig;
import com.ezetap.sdk.EzetapPayApis;


public class NextActivity extends Activity
{
    Button btn_caf_form,btn_logout;
    final Context context=this;
    public static EzetapApiConfig API_CONFIG = new EzetapApiConfig(
			ApConfigConstants.authMode, ApConfigConstants.productionAppKey,
			ApConfigConstants.merchantName, ApConfigConstants.currencyCode,
			ApConfigConstants.appMode, ApConfigConstants.captureSignature,
			ApConfigConstants.preferredChannel);
    
    String Username="";
    SharedPreferences pref;
	private String sharedPreferences_name;
    public boolean is_card_allowed=false;
    TextView tv_version;
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_next);

		sharedPreferences_name = getString(R.string.shared_preferences_name);
		pref = getApplicationContext()
				.getSharedPreferences(sharedPreferences_name, 0);
	
		
		btn_caf_form=(Button)findViewById(R.id.btn_caf_form);
		btn_logout=(Button)findViewById(R.id.btn_logout);
		
      //  tv_version=(TextView)findViewById(R.id.tv_ver);
		
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
			String versionName = pinfo.versionName;
			//tv_version.setText("Version "+versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//pref=getApplicationContext().getSharedPreferences("Login", 0);
	     Username = pref.getString("UserName", "");
	     is_card_allowed=pref.getBoolean(MyUtils.EZETAP_ALLOWED, false);
	     
	     if(is_card_allowed){
		
			EzetapPayApis.create(NextActivity.API_CONFIG)
			.initializeDevice(NextActivity.this,
					AppConstants.REQ_CODE_PAY_CARD, Username);
	
			EzetapPayApis.create(NextActivity.API_CONFIG)
			.checkForUpdate(NextActivity.this,
					AppConstants.REQ_CODE_PAY_CARD, Username);
	     }
	     else{
	    	 
	     }
		
		btn_caf_form.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v)
			 {
				if(MyUtils.isOnline(NextActivity.this)){
					Intent i=new Intent(context,CAF_Activity.class);
					startActivity(i);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Connect to internet !", Toast.LENGTH_LONG).show();
				}
			 }
		});
		
		btn_logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = getSharedPreferences("Login", 0);
				preferences.edit().remove("UserName").commit();
				preferences.edit().remove("Password").commit();
				preferences.edit().remove("UserId").commit();
				//preferences.edit().clear().commit();
				NextActivity.this.finish();
				Intent i=new Intent(context,MainActivity.class);
				startActivity(i);	
			}
		});
    } 
    
 } 
