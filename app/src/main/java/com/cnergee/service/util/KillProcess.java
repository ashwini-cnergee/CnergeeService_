package com.cnergee.service.util;


/*
 * Kill application when the root activity is killed.
 */

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;

public class KillProcess {

	private Context context;
	private ActivityManager activityManage;
	
	public KillProcess(Context context, ActivityManager activityManage){
		this.context = context;
		this.activityManage = activityManage;
	}
	
	public void killAppsProcess(){
		int pid = 0;
		//ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		
		List<ActivityManager.RunningAppProcessInfo> pids = activityManage.getRunningAppProcesses();
		   for(int i = 0; i < pids.size(); i++)
		   {
		       ActivityManager.RunningAppProcessInfo info = pids.get(i);
		       if(info.processName.equalsIgnoreCase("com.cnergee.service")){
		          pid = info.pid;
		          android.util.Log.i("*** KILL PID info.pid *** ",""+info.pid);
		       } 
		   }
		android.util.Log.i("*** KILL PID *** ",""+pid);
		android.util.Log.i("*** GET KILL PID *** ",""+android.os.Process.myPid());
		//android.util.Log.i("*** GET KILL PID *** ",""+android.os.Process.killProcess(android.os.Process.myPid()));
		
		if(pid > 0)
			android.os.Process.killProcess(pid);
		 System.exit(0);
		 System.gc();
	}
}
