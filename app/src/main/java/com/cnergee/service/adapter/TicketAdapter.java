package com.cnergee.service.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.database.DatabaseHandler;

public class TicketAdapter extends ArrayAdapter<String> {
Context ctx;
ArrayList<String> alTicketList; 
ArrayList<String> alSubscriberName;
DatabaseHandler dbHandler;
String TableName;
	
	public TicketAdapter(Context context, int textViewResourceId,
			ArrayList<String> alTickets,ArrayList<String> alSubscriber,String TableName) {
		super(context, textViewResourceId, alTickets);
		// TODO Auto-generated constructor stub
		this.ctx= context;
		this.alTicketList=alTickets;
		this.alSubscriberName=alSubscriber;
		dbHandler= new DatabaseHandler(ctx);
		this.TableName=TableName;
	}
	
public class ViewHolder{
	TextView tvSubscName;
}
@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	ViewHolder holder=null;
	if(convertView==null){
		LayoutInflater inflater=((Activity)ctx).getLayoutInflater();
		convertView= inflater.inflate(R.layout.custom_lv_textview, null);
		holder=new ViewHolder();
		holder.tvSubscName = (TextView)convertView.findViewById(R.id.tv);
		convertView.setTag(holder);
	}
	else{
		holder=(ViewHolder)convertView.getTag();
	}
		String subsName=alSubscriberName.get(position);
		String TicketNumber=alTicketList.get(position);
		boolean dbTicketNo= dbHandler.isExist(TableName, TicketNumber);
		if(dbTicketNo){			
			
			holder.tvSubscName.setTextColor(Color.BLACK);	
			holder.tvSubscName.setText(subsName);
			
		}
		else{
			holder.tvSubscName.setTextColor(Color.RED);	
			holder.tvSubscName.setText(subsName);
		}
	
	return convertView;
	}
	
}
