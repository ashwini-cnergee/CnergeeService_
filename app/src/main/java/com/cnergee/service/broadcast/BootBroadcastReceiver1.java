package com.cnergee.service.broadcast;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.sys.NotificationService;
import com.cnergee.service.util.Utils;

public class BootBroadcastReceiver1 extends BroadcastReceiver {
	private String sharedPreferences_name;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Utils.log("*********************Boot Broadcast************* ","**************called***********");
		SharedPreferences sharedPreferences;
		sharedPreferences_name = context.getString(R.string.shared_preferences_name);
		 sharedPreferences = context.getApplicationContext().getSharedPreferences(sharedPreferences_name, 0); // 0 - for private mode
		Editor edit= sharedPreferences.edit();
		edit.putBoolean("check_first", false);
		edit.commit();
		
		Intent intentService1 = new Intent(context,AlarmBroadcastReceiver.class);
		PendingIntent pintent1 = PendingIntent.getBroadcast(context, 0, intentService1, 0);
		Calendar cal = Calendar.getInstance();
		AlarmManager alarm1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		// Start every 30 seconds
		//alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent);
		// Start every 1mon..
		alarm1.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 15*60*1000, pintent1);
		
		
		Intent intentService = new Intent(context,NotificationService.class);
		
		Calendar cal1 = Calendar.getInstance();
		PendingIntent pintent = PendingIntent.getService(context, 0, intentService, 0);
		AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		// Start every 30 seconds
		//alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 30*1000, pintent);
		// Start every 1mon..
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), 60000*4, pintent);
		
	}

}
