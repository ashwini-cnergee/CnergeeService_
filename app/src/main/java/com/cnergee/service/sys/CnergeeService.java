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
package com.cnergee.service.sys;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.ComplaintDetailsActivity;
import com.cnergee.service.SOAP.ComplaintCallerSOAP;
import com.cnergee.service.SOAP.ComplaintStatusUpdateSOAP;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.ComplaintListObj;
import com.cnergee.service.util.StatusUpdateHelper;
import com.cnergee.service.util.Utils;


public class CnergeeService extends Service  {
	
	public static Context context;
	private static final String TAG = "CnergeeService";  
    public static String rslt = "";
    public static ArrayList<ComplaintListObj> complaintList = new ArrayList<ComplaintListObj>();
    Utils utils = new Utils();
    private String sharedPreferences_name;
    private AuthenticationMobile Authobj;
    PackageInfo pInfo;
    
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    public void onCreate() { 
        super.onCreate();
       // Toast.makeText(this, "Cnergee Service Created.)", Toast.LENGTH_LONG).show();
        /*Utils.log(TAG, TAG + ": Service Created");*/
        context = this;
        
        sharedPreferences_name = getString(R.string.shared_preferences_name);
		SharedPreferences sharedPreferences = getApplicationContext()
				.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
		utils.setSharedPreferences(sharedPreferences);

		
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Authobj = new AuthenticationMobile();
		Authobj.setMobileNumber(utils.getMobileNumber());
		Authobj.setMobLoginId(utils.getMobLoginId());
		Authobj.setMobUserPass(utils.getMobUserPass());
		Authobj.setIMEINo(utils.getIMEINo());
		Authobj.setCliectAccessId(utils.getCliectAccessId());
		Authobj.setMacAddress(utils.getMacAddress());
		Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
		Authobj.setAppVersion(pInfo.versionName);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*Utils.log(TAG, TAG + ": Cnergee Service Destroyed");*/
       // utils = null;
       // complaintList = null;
       // Authobj = null;
    }
   /* @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Toast.makeText(this, "ServiceClass.onStart()", Toast.LENGTH_LONG).show();
        Utils.log("Testing", "Service got started");
    }
*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Utils.log("Cnergee service","started");
      //  initDoSomethingTimer();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
        sharedPreferences_name = getString(R.string.shared_preferences_name);
		SharedPreferences sharedPreferences = getApplicationContext()
				.getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
		utils.setSharedPreferences(sharedPreferences);

		Authobj = new AuthenticationMobile();
		Authobj.setMobileNumber(utils.getMobileNumber());
		Authobj.setMobLoginId(utils.getMobLoginId());
		Authobj.setMobUserPass(utils.getMobUserPass());
		Authobj.setIMEINo(utils.getIMEINo());
		Authobj.setCliectAccessId(utils.getCliectAccessId());
		Authobj.setMacAddress(utils.getMacAddress());
		Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
		Authobj.setAppVersion(pInfo.versionName);
      		
      /*  Utils.log(TAG, TAG + ": Cnergee Service Started "+utils.getAppUserId());
        Utils.log(TAG, TAG + ": IS NET CONNECT  "+utils.isOnline(context));*/
        
        if(utils.isOnline(context)){
	      //  complaintListWebService.execute((String)null);
	        
			ComplaintCallerSOAP soap = new ComplaintCallerSOAP(getApplicationContext().getResources().getString(
					R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(),getApplicationContext()
							.getResources().getString(
									R.string.METHOD_GET_COMPLAINT));
			if(Authobj == null){
				Authobj = new AuthenticationMobile();
				Authobj.setMobileNumber(utils.getMobileNumber());
				Authobj.setMobLoginId(utils.getMobLoginId());
				Authobj.setMobUserPass(utils.getMobUserPass());
				Authobj.setIMEINo(utils.getIMEINo());
				Authobj.setCliectAccessId(utils.getCliectAccessId());
				Authobj.setMacAddress(utils.getMacAddress());
				Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
				Authobj.setAppVersion(pInfo.versionName);
			}
			try {
				String rslt = soap.setComplaintList(Long.parseLong(utils.getAppUserId()), Authobj);
				if (rslt.trim().equalsIgnoreCase("ok")) {
					complaintList = soap.getComplaintList();
					//Log.i("*********** ",""+complaintList.size());
					doSomething();
				}
				
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return START_STICKY;    
    }

    private void doSomething() {
       /* Utils.log(TAG, TAG + ": did something!!");*/
     
        Random rand = new Random();
        Iterator iter = complaintList.iterator();
		
		while(iter.hasNext()){
			 int notifID = rand.nextInt(100);
			 ComplaintListObj complObj = (ComplaintListObj)iter.next(); 	
			 
			 String id = complObj.getComplaintNo().trim();
			 //boolean isUpdated = complObj.isUpdated();
			 boolean isPush = complObj.isPush();
			 
			 if(!isPush){
				 updateStatus(id);
				 
				 //Utils.log(TAG, TAG + " COMPL ID "+id);
				 
			    Intent intent = new Intent(this,	ComplaintDetailsActivity.class);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Bundle bundle = new Bundle();
				String strListSize = bundle.getString("ListSize");
				bundle.putString("complaintNo", complObj.getComplaintNo());
				bundle.putBoolean("isRead", complObj.isRead());
				bundle.putString("ListSize", strListSize);
				intent.putExtra("com.cnergee.service.complaint.details.screen.INTENT", bundle);
				//startActivity(intent);
		
				PendingIntent detailsIntent = PendingIntent.getActivity(this, 1, intent, notifID);
				//PendingIntent detailsIntent = PendingIntent.getBroadcast(this, 0, intent);
				
		        NotificationManager notifyMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		        Notification notifyObj = new Notification(
		                R.drawable.alert, 
		                getString(R.string.app_notification_label_1),
		                System.currentTimeMillis());
		        
		        CharSequence from = getString(R.string.app_notification_label_2);
		        CharSequence message = "Complaint No."+id;        
		        //notifyObj.setLatestEventInfo(this, from, message, detailsIntent);
		     
		      //Value indicates the current number of events represented by the notification
		     //   notifyObj.number=++count;
		        //Set default vibration
		        notifyObj.defaults |= Notification.DEFAULT_VIBRATE;
		        //Set default notification sound
		        notifyObj.defaults |= Notification.DEFAULT_SOUND;
		        //Clear the status notification when the user selects it
		        notifyObj.flags|=Notification.FLAG_AUTO_CANCEL;   
		        //Send notification
		        notifyMgr.notify(notifID, notifyObj);
		        
		        //---100ms delay, vibrate for 250ms, pause for 100 ms and
		        // then vibrate for 500ms---
		       /* notif.vibrate = new long[] { 100, 250, 100, 500};        
		        nm.notify(notifID, notif);*/
			 }
		       
		}
    }
    
    private void updateStatus(String complID){
    	
    	//Log.i("*********** STATUS CHANGE... ",""+complID);
    	
    	
    	try {
    		ComplaintStatusUpdateSOAP soap = new ComplaintStatusUpdateSOAP(getApplicationContext().getResources().getString(
    				R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(),getApplicationContext()
    						.getResources().getString(
    								R.string.METHOD_UPDATE_STATUS));
        	
        	StatusUpdateHelper helper = new StatusUpdateHelper(soap,Long.parseLong(utils.getAppUserId()),complID,Authobj,"P",true);
        	helper.updateStatus();
			
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
    	
    	
    }
 
/*    private void setDatabase(ComplaintObj complaint){
    	DatabaseHandler db = new DatabaseHandler(this);
    	db.addComplaint(complaint);
    	
    	ComplaintObj tmpObj = db.getComplaintObj(complaint.getDocketNo());
    	if(tmpObj != null){
    		Log.i(" >>>>>>>>> "," INSERT....");
    	}else{
    		Log.i(" >>>>>>>>> "," NOT INSERT....");
    	}
    	
    }*/
}
