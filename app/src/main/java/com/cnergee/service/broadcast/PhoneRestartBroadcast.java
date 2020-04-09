package com.cnergee.service.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cnergee.service.util.Utils;

public class PhoneRestartBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Utils.log("*********************Phone Restart************* ","**************called***********");
	}


}
