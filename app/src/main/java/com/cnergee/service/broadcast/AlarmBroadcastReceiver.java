package com.cnergee.service.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cnergee.service.sys.GpsTrackerService;
import com.cnergee.service.util.Utils;



public class AlarmBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Utils.log("Alarm broadcast","started");
		String activity= intent.getStringExtra("activity");
		if(activity==null){
			activity="roaming";
		}
		Intent i= new  Intent(context, GpsTrackerService.class);
		i.putExtra("activity", activity);
		context.startService(i);
	}

}
