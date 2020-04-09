/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  22 Dec. 2012
 *
 * Version 1.1
 *
 */
package com.cnergee.service.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.cnergee.service.obj.AuthenticationMobile;


public class Utils {

	public static final String VIEW_DATE_FORMAT = "dd-MMM-yyyy";
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public String MobileNumber, MobLoginId, MobUserPass,IMEINo, app_username,app_user_id,
			app_password,language,CliectAccessId,Dynamic_Url,MacAddress,PhoneUniqueId;

	public static boolean DASHBOARD_OPEN=false; 
	
	public static String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	public final static String xml_folder = "cnergeeservice";
	public final static String TARGET_BASE_PATH = extStorageDirectory + "/" + xml_folder + "/";
	public static boolean SHOW_LOG=true;
	
	public void setSharedPreferences(SharedPreferences sharedPreferences) {
		MobileNumber = sharedPreferences.getString("MobileNumber", "");
		MobLoginId = sharedPreferences.getString("MobLoginId", "");
		MobUserPass = sharedPreferences.getString("MobUserPass", "");
		IMEINo = sharedPreferences.getString("IMEINo", "");
		language = sharedPreferences.getString("LANGUAGE", "");
		app_username = sharedPreferences.getString("USER_NAME", "");
		app_password = sharedPreferences.getString("USER_PASSWORD", "");
		app_user_id =  sharedPreferences.getString("USER_ID","");
		CliectAccessId =  sharedPreferences.getString("CliectAccessId","");		
		Dynamic_Url =  sharedPreferences.getString("Dynamic_Url","");
		MacAddress =  sharedPreferences.getString("MacAddress","");
		PhoneUniqueId =  sharedPreferences.getString("PhoneUniqueId","");
		//Utils.log("MacAddress and PhoneUniqueId",MacAddress+" and "+PhoneUniqueId);
	}
	
	public void clearSharedPreferences(SharedPreferences sharedPreferences) {
		 
		SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.remove("USER_NAME");
	    editor.remove("USER_PASSWORD");
	    editor.remove("USER_ID");
	//    editor.clear();
	    editor.commit();
	}
	
	
	/**
	 * @return the app_username
	 */
	public String getAppUserName() {
		return app_username;
	}

	/**
	 * @param app_username
	 *            the app_username to set
	 */
	public void setAppUserName(String app_username) {
		this.app_username = app_username;
	}

	/**
	 * @return the app_password
	 */
	public String getAppPassword() {
		return app_password;
	}

	/**
	 * @param app_password
	 *            the app_password to set
	 */
	public void setAppPassword(String app_password) {
		this.app_password = app_password;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return MobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	/**
	 * @return the mobLoginId
	 */
	public String getMobLoginId() {
		return MobLoginId;
	}

	/**
	 * @param mobLoginId the mobLoginId to set
	 */
	public void setMobLoginId(String mobLoginId) {
		MobLoginId = mobLoginId;
	}

	/**
	 * @return the mobUserPass
	 */
	public String getMobUserPass() {
		return MobUserPass;
	}

	/**
	 * @param mobUserPass the mobUserPass to set
	 */
	public void setMobUserPass(String mobUserPass) {
		MobUserPass = mobUserPass;
	}

	/**
	 * @return the iMEINo
	 */
	public String getIMEINo() {
		return IMEINo;
	}

	/**
	 * @param iMEINo the iMEINo to set
	 */
	public void setIMEINo(String iMEINo) {
		IMEINo = iMEINo;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the app_user_id
	 */
	public String getAppUserId() {
		return app_user_id;
	}

	/**
	 * @param app_user_id the app_user_id to set
	 */
	public void setAppUserId(String app_user_id) {
		this.app_user_id = app_user_id;
	}

	
	
	public  String getCliectAccessId() {
		return CliectAccessId;
	}

	public void setCliectAccessId(String cliectAccessId) {
		CliectAccessId = cliectAccessId;
	}
	

	/**
	 * @param Dynamic_Url the Dynamic_Url to set
	 */
	
	public String getDynamic_Url() {
		return Dynamic_Url;
	}


	/**
	 * @param Dynamic_Url the Dynamic_Url to set
	 */
	
	public void setDynamic_Url(String dynamic_Url) {
		Dynamic_Url = dynamic_Url;
	}
	
	public String getMacAddress() {
		return MacAddress;
	}

	public void setMacAddress(String macAddress) {
		MacAddress = macAddress;
	}

	public String getPhoneUniqueId() {
		return PhoneUniqueId;
	}

	public void setPhoneUniqueId(String phoneUniqueId) {
		PhoneUniqueId = phoneUniqueId;
	}

	/*
	 * METHOD
	 * CHECK XML File IS EXIST IN SD CARD 
	 * Method Name: isXMLFile(String xml_file)
	 * Parameter 1: xml_file IS NAME OF FILE 
	 * 
	 * */
	
	public  boolean isXMLFile(String xml_file) {
		
		File file = new File(TARGET_BASE_PATH,xml_file );
		boolean exists = file.exists();
		
		if (!exists) {
			// It returns false if File or directory does not exist
			if (!file.isFile()) {
				return false;
			} else {
				return true;
			}
		} else {
			// It returns true if File or directory exists
			return true;
		}
	}
	

	public static void writeXMLFile(String xml_file_name,String strXML,Context ctx) {
		 Log.i(">>>XML <<< ", strXML);
		try {
			File direct = new File(extStorageDirectory + "/" + xml_folder);
			
			if (!direct.exists()) {
				direct.mkdirs(); // directory is created;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}

		FileOutputStream fOut = null;
		OutputStreamWriter xmlOutWriter = null;

		try {
			File xmlFile = new File(TARGET_BASE_PATH+xml_file_name);
			xmlFile.createNewFile();
			fOut = new FileOutputStream(xmlFile);
			xmlOutWriter = new OutputStreamWriter(fOut);
			xmlOutWriter.append(strXML);
			Utils.log("String in","XML is "+strXML);
			Toast.makeText(ctx,
					"Done writing '/cnergee/'"+xml_file_name+" file.",
					Toast.LENGTH_LONG).show();
			// new ReadPackageListXML().execute();

		}catch(IOException io){
			io.printStackTrace();
			Toast.makeText(ctx, io.getMessage(), Toast.LENGTH_LONG)
			.show();
		}catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG)
					.show();
		} finally {
			try {
				if(xmlOutWriter != null){
					xmlOutWriter.flush();
					xmlOutWriter.close();
				}
				if(xmlOutWriter != null){
					fOut.flush();
					fOut.close();
				}

			} catch (IOException io) {
			}
			strXML = null;
		}
							
	}
	
	public boolean isOnline(Context context) {
	    
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
		    
	    // or if function is out side of your Activity then you need context of your Activity
	    // and code will be as following
	    // (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo)
	    {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedWifi = true;
	                System.out.println("WIFI CONNECTION AVAILABLE");
	                AuthenticationMobile.isWiFi=true;
	            } else
	            {
	                System.out.println("WIFI CONNECTION NOT AVAILABLE");
	                AuthenticationMobile.isWiFi=false;
	                
	            }
	        }
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	        {
	            if (ni.isConnected())
	            {
	                haveConnectedMobile = true;
	                System.out.println("MOBILE INTERNET CONNECTION AVAILABLE");
	                AuthenticationMobile.isWiFi=false;
	            } else
	            {
	                System.out.println("MOBILE INTERNET CONNECTION NOT AVAILABLE");
	                AuthenticationMobile.isWiFi=false;
	            }
	        }
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	    
	    /*NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;*/
	    
	   /* if (cm.getActiveNetworkInfo() != null
	            && cm.getActiveNetworkInfo().isAvailable()
	            && cm.getActiveNetworkInfo().isConnected()) {
	        return true;
	    } else {
	        Log.v("Utils : ", "Internet Connection Not Present");
	        return false;
	    }*/
	    
	}
	public static void log(String name,String value){
		if(SHOW_LOG){
			Log.d(name,value);
		}
	}
	
}
