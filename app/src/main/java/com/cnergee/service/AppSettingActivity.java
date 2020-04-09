/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  07 Feb. 2013
 *
 * @Author 
 * Ashok Parmar
 * 
 * Version 1.0
 *
 */
package com.cnergee.service;


import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.GetDynamicUrlSOAP;
import com.cnergee.service.caller.AppSettingCaller;
import com.cnergee.service.obj.AppConstants1;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.TelephonyInfo;
import com.cnergee.service.util.FontTypefaceHelper;
import com.cnergee.service.util.Utils;
import com.traction.ashok.util.AlertsBoxFactory;

public class AppSettingActivity extends Activity {
	//private String[] languages = { "English", "Hindi", "Gujrati","Marathi" };
	private String[] languages = { "English"};
//	private String[] languages = { "English", "Hindi","Marathi" };
	private String logtag = getClass().getSimpleName();
	private Spinner spinnerList;
	private Context context = null;
	Button btnSave = null; 
	EditText username, password ,etCustId;
	private String imei,simserialno,language;
	String sharedPreferences_name;
	Utils utils = new Utils();
	FontTypefaceHelper fontTypeface = new FontTypefaceHelper();
	public static Boolean AccessCodeFlag=false;
	public static String rslt="";
	public static String  DynamicUrl="";
	boolean hasGps;
	SharedPreferences sharedPreferences ;
	String MacAddress="",PhoneUniqueId="";
	
	PackageInfo pInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_app_setting);
		context = this;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TextView versionView = (TextView) findViewById(R.id.versionView);
		String app_ver;
		try {
			app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
			versionView.setText("Ver."+app_ver);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final Locale[] availableLocales=Locale.getAvailableLocales();
		for(final Locale locale : availableLocales)
		  Utils.log("Applog",":"+locale.getDisplayName()+":"+locale.getLanguage()+":"
		    +locale.getCountry()+":values-"+locale.toString().replace("_","-r"));
		
		/*PackageManager pm = getApplicationContext().getPackageManager();
		hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		if(hasGps)
			Toast.makeText(this, "Gps Available", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "Gps Not Available", Toast.LENGTH_SHORT).show();*/
		
		if(AppConstants1.hasGPSDevice(this)){
			AppConstants1.GPS_AVAILABLE=true;
			//Toast.makeText(this, "Gps Available Main", Toast.LENGTH_SHORT).show();
		}
		else{
			AppConstants1.GPS_AVAILABLE=false;
			//Toast.makeText(this, "Gps Not Available Main", Toast.LENGTH_SHORT).show();
		}
		
		
		TextView headerView = (TextView)findViewById(R.id.headerView);
		headerView.setText(getString(R.string.app_setting_title));
		
		sharedPreferences_name = getString(R.string.shared_preferences_name);
		
		 Locale locale = getResources().getConfiguration().locale;
	   //  Utils.log(logtag, "lang = " + locale.getLanguage());
	     language = locale.getLanguage();
	     
	     spinnerList = (Spinner) findViewById(R.id.languageList);
	     btnSave = (Button) findViewById(R.id.save);
	     btnSave.setOnClickListener(btnSaveOnClickListener);
	     
	     username = (EditText) findViewById(R.id.username);
	   //  username.setBackgroundColor(Color.LTGRAY);
			
	     password = (EditText) findViewById(R.id.password);
	    // password.setBackgroundColor(Color.LTGRAY);
	     
	     etCustId = (EditText) findViewById(R.id.etCustId);
	   //  etCustId.setBackgroundColor(Color.LTGRAY);

	     spinnerList.setPrompt("select language");
		 
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,languages);
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spinnerList.setAdapter(adapter);
		 
		 spinnerList.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
				
					String selecte_lang = spinnerList.getSelectedItem().toString();
					 // overrideFonts(context,view);
					if(selecte_lang.equalsIgnoreCase("Gujrati")){
						language = "gu";
					}else if (selecte_lang.equalsIgnoreCase("Hindi")){
						language = "hi";
					}else if (selecte_lang.equalsIgnoreCase("Marathi")){
						language = "ma";
					}else{
						language = "en";
					}
						/*Toast.makeText(AppSettingActivity.this,
								"Selected language " + selecte_lang,
								Toast.LENGTH_LONG).show();*/
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
		   	});
		 TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(AppSettingActivity.this);

	        String imeiSIM1 = telephonyInfo.getImeiSIM1();
	        String imeiSIM2 = telephonyInfo.getImeiSIM2();

	        boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
	        boolean isSIM2Ready = telephonyInfo.isSIM2Ready();

	        boolean isDualSIM = telephonyInfo.isDualSIM();

	      
	        Utils.log("Check:"," IME1 : " + imeiSIM1 + "\n" +
	                " IME2 : " + imeiSIM2 + "\n" +
	                " IS DUAL SIM : " + isDualSIM + "\n" +
	                " IS SIM1 READY : " + isSIM1Ready + "\n" +
	                " IS SIM2 READY : " + isSIM2Ready + "\n");

		 
		 	TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			
		 	WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		 	if(manager!=null){
		 	WifiInfo info = manager.getConnectionInfo();
		 	MacAddress = info.getMacAddress();
		 	Utils.log("Wifi address","is: "+MacAddress);
		 	}
		 	PhoneUniqueId= 	Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		 	
//         	imei = tm.getDeviceId().toString();
//			simserialno = tm.getSimSerialNumber(); 

            simserialno="8991200050108669743";
            imei="359175107170026";


			//Utils.log("404000335873959 Sim NO",":"+tm.getSimSerialNumber());
			
			//Utils.log("404000335873959 getSubscriberId NO",":"+tm.getSubscriberId());
			//simserialno = "0000000000000000000"; //for WIFI
			if(getString(R.string.app_run_mode).equalsIgnoreCase("dev")){
				Toast.makeText(this, "Development", Toast.LENGTH_LONG).show();
				
				//imei = "352840079866532";
				//simserialno = "8991860044789955027";
			
				//FOR Development
//				simserialno="89918740400066529375";
//				imei="868409041258389";
//
			//	For DVOIs
			  /*	imei = "911364600334468";
				simserialno = "8991200010303976475f";*/
				
				/*//FOR LS vinay	1234
				imei = "911336600846063";
				simserialno = "89910732401301683586";*/
				
				/*imei = "354905054556172";
				simserialno = "89917990071259207563";*/
				
				/*//FOR JRaju
				imei = "911336600636530";
				simserialno = "89910722401301438793";*/
				
				/*//FOR SVS
				imei = "351754060240253";
				simserialno = "404000335873959";*/
				
			/*	//FOR NET9ONLINE
				imei = "869162023987272";
				simserialno = "8991200014804012343f";*/
				
				
/*
				//FOR Spearhead
				simserialno="8991921310278996831";
				imei="356631055626537";
			
				
				
			
			/*		//FOR Yashtel
				imei = "868981023556212";
				simserialno = "8991860044605819142f";*/
				
				
				/*//FOR LS
				 * 
				simserialno="8991200014805547107f";
				imei="911367804438316";*/
				
				
			/*	//FOR AIRNET
				simserialno="89911100182037635247";
				imei="867935021838049";*/
				
				/*//FOR VCPL
				imei = "354799061509187";
				simserialno = "89910377390274594492";*/
				
				
				/*//FOR ETHRENET
				imei = "355490067601876";
				simserialno = "89912210000030323162";*/
				
				/*//FOR APPLE
				imei = "911336601418052";
				simserialno = "89910722201403281434";*/
					
				/*//FOR EOS
				imei = "354865077637666";
				simserialno = "8991000900714406355";*/
				
				/*//FOR Yashtel
				imei = "357146059105734";
				simserialno = "8991860044601691834";*/
				
				
				
				//kartik 1234
			}
			
			sharedPreferences= getApplicationContext()
					.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode

			username.setText(sharedPreferences.getString("MobLoginId", ""));
			password.setText(sharedPreferences.getString("MobUserPass", ""));
			etCustId.setText(sharedPreferences.getString("CliectAccessId", ""));
			String strLan = sharedPreferences.getString("LANGUAGE", "");
			
		    if(strLan.equalsIgnoreCase("gu")){
				spinnerList.setSelection(2);	
			}else if (strLan.equalsIgnoreCase("hi")){
				spinnerList.setSelection(1);
				}else if (strLan.equalsIgnoreCase("ma")){
					spinnerList.setSelection(3);
				}else{
					spinnerList.setSelection(0);
				}	
			final Button btnBack = (Button) findViewById(R.id.back);
			btnBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
					Intent intent = new Intent(AppSettingActivity.this, LoginActivity.class);
					startActivity(intent);
				}
			});
			
	}
	Button.OnClickListener btnSaveOnClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			 if(etCustId.length()>0){
				 if(username.length()>0){
      			   if(password.length()>0){
      				   AlertDialog.Builder builder = new AlertDialog.Builder(context);
      				   builder.setMessage(Html.fromHtml("<font color='#FFA500'><b>Are you sure you want to process?</b></font>"))
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(Html.fromHtml("<font color='#FFA500'>Change Web Service Authentication</font>"))
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   if(utils.isOnline(AppSettingActivity.this)){
							//new ValidateAccessCode().execute();
			        		   new GetDynamicURLAsyncTask().execute();
			        	   }
			        	   else{
			        		   AlertsBoxFactory.showAlert("Please Check Internet Connection", AppSettingActivity.this);
			        	   }
			           }
			           
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                //isConfirm = false;
			           }
			       });
		
			AlertDialog alert = builder.create();
			alert.show();
			 }
			else{
     		   password.requestFocus();
     		   password.setError(Html.fromHtml("<font color='red'>Please Enter Mobile Password</font>"));
     		 
     	   }
				 }
      		   else{
      			   username.requestFocus();
      			   username.setError(Html.fromHtml("<font color='red'>Please Enter Mobile Login Id</font>"));
      		   }
      	   }
      	   else{
      		   etCustId.requestFocus();
      		   etCustId.setError(Html.fromHtml("<font color='red'>Please Enter Access Code</font>"));
      	   }	   
			 
		}
	};
	
	@Override
	protected void onDestroy() {
	        super.onDestroy();
	        System.runFinalizersOnExit(true);

	 }
	@Override
	protected void onPause() {
		super.onPause();
		//finish();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_app_setting, menu);
		return true;
	}

	protected class ValidateAccessCode extends AsyncTask<String, Void, Void>{

		private ProgressDialog Dialog = new ProgressDialog(AppSettingActivity.this);
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Dialog.setMessage(getString(R.string.app_please_wait_label));
			Dialog.show();
			//fontTypeface.dialogFontOverride(context,Dialog);
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
				AuthenticationMobile Authobj = new AuthenticationMobile();
				Authobj.setMobileNumber(simserialno);
				Authobj.setMobLoginId(username.getText().toString());
				Authobj.setMobUserPass(password.getText().toString());
				Authobj.setIMEINo(imei);
				Authobj.setCliectAccessId(etCustId.getText().toString());
				Authobj.setMacAddress(MacAddress);
				Authobj.setPhoneUniqueId(PhoneUniqueId);
				Authobj.setAppVersion(pInfo.versionName);
				AppSettingCaller appSettingCaller= new AppSettingCaller(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
						DynamicUrl, getApplicationContext()
								.getResources().getString(
										R.string.METHOD_APP_SETTING),
										Authobj);
				
				appSettingCaller.join();
				appSettingCaller.start();
				rslt="START";
				while (rslt == "START") {
					try {
						Thread.sleep(10);
					} catch (Exception ex) {
					}
				}
				
			}
			catch(Exception e){
				//AlertsBoxFactory.showErrorAlert(e.toString(),context );	
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Dialog.dismiss();
			Utils.log("Result ",""+rslt);
			if (rslt.trim().equalsIgnoreCase("OK")) {
				
				if (AccessCodeFlag) {
					
					SharedPreferences sharedPreferences = getApplicationContext()
							.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
							SharedPreferences.Editor editor = sharedPreferences.edit();
							Utils.log("SimserialNo",":"+simserialno);
							editor.putString("MobileNumber",simserialno);
							editor.putString("MobLoginId", username.getText().toString());
							editor.putString("MobUserPass", password.getText().toString());
							editor.putString("IMEINo", imei);
							editor.putString("LANGUAGE", language);
							editor.putString("CliectAccessId", etCustId.getText().toString());
							editor.putString("MacAddress", MacAddress);
							editor.putString("PhoneUniqueId", PhoneUniqueId);
							editor.commit();
							

							/*
							 * SavePreferences("VENDOR_CODE", vendorcode.getText().toString());
							 * SavePreferences("AUT_USERNAME", username.getText().toString());
							 * SavePreferences("AUT_PASSWORD", password.getText().toString());
							 */

							/*Log.i(logtag,"MobileNumber "+ simserialno);
							Log.i(logtag,"MobLoginId "+ username.getText().toString());
							Log.i(logtag,"MobUserPass "+ password.getText().toString());
							Log.i(logtag,"IMEINo "+ imei);
							Log.i(logtag,"LANGUAGE "+ language);
							Log.i(logtag,"CliectAccessId "+ etCustId.getText().toString());*/
							
							Toast.makeText(AppSettingActivity.this,
									"WebService Authentication set. Please login!",
									Toast.LENGTH_SHORT).show();
							finish();
							Intent intent = new Intent(AppSettingActivity.this, LoginActivity.class);
							startActivity(intent);
				}
				else{
					   etCustId.requestFocus();
		      		   etCustId.setError(Html.fromHtml("<font color='red'>Please Check Access Code,Mobile Login Id and Mobile Password</font>"));
				}
			}
			else{
				AlertsBoxFactory.showAlert(rslt,context );
			}
		}
	}
	
	
	public class GetDynamicURLAsyncTask extends AsyncTask<String, Void, Void>{
		 ProgressDialog prgDialog;
		 String rsltDynamicUrl="";
		 GetDynamicUrlSOAP soap;
		 @Override
		protected void onPreExecute() {
		// TODO Auto-generated method stub
			super.onPreExecute();
			prgDialog= new ProgressDialog(AppSettingActivity.this);
			prgDialog.setMessage("Please wait...");
			prgDialog.setCancelable(false);
			prgDialog.show();
			
			
		}
		 
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			soap = new GetDynamicUrlSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.GLOBAL_SOAP_URL),getString(R.string.METHOD_GET_DYNAMIC_URL));
			//soap = new GetDynamicUrlSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.SOAP_URL),getString(R.string.METHOD_GET_DYNAMIC_URL));
			try {
				rsltDynamicUrl=soap.getDynamicUrl(etCustId.getText().toString());
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			prgDialog.dismiss();
		try{
			if(rsltDynamicUrl.length()>0){
				if(rsltDynamicUrl.equalsIgnoreCase("ok")){
					if(DynamicUrl.length()>0){
						if(!DynamicUrl.equalsIgnoreCase("anyType{}")){
							
							SharedPreferences sharedPreferences = getApplicationContext()
									.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
									SharedPreferences.Editor editor = sharedPreferences.edit();
									editor.putString("Dynamic_Url",DynamicUrl);
									editor.commit();
									new ValidateAccessCode().execute();
						}
					}
				}
				else{
					AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingActivity.this);
				}
			}
			else{
				AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingActivity.this);
			}
			
			}catch(Exception e){
				
				prgDialog.dismiss();
			}
			
			
		}
}
	

}
