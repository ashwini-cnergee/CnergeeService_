/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  25 Feb. 2013
 *
 * @Author 
 * Ashok Parmar
 * 
 * Version 1.0
 *
 */
package com.cnergee.service.adapter;

import java.util.ArrayList;
import java.util.Locale;

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
import com.cnergee.service.obj.ComplaintListObj;
import com.cnergee.service.obj.ComplaintObj;
import com.cnergee.service.util.Utils;


public class ComplaintListAdapter1 extends ArrayAdapter<ComplaintListObj> {

	Context context;
	int layoutResourceId;
	ArrayList<ComplaintListObj> alComplaintList;
	ComplaintListObj data[] = null;
	//ArrayList<ComplaintListObj> alDisplayComplaintList;
	 private ArrayList<ComplaintListObj> arraylist= new ArrayList<ComplaintListObj>();
	ComplaintListObj Displaydata[] = null;
	DatabaseHandler dbHandler;
	
	public ComplaintListAdapter1(Context context, int layoutResourceId, ArrayList<ComplaintListObj> alComplaintList) {
		super(context, layoutResourceId, alComplaintList);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.alComplaintList = alComplaintList;
	//	this.arraylist=alComplaintList;
		this.arraylist.addAll(alComplaintList);
		Utils.log("arraylist","size is: "+arraylist.size());
		dbHandler=new DatabaseHandler(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ComplaintListHolder holder = null;


		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
	
			holder = new ComplaintListHolder();
			//holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView)row.findViewById(R.id.txtComplaintListId);
			holder.txtuserid = (TextView)row.findViewById(R.id.txtuserid);
			row.setTag(holder);
		}
		else{
			holder = (ComplaintListHolder)row.getTag();
		}
		
		ComplaintListObj complObj = alComplaintList.get(position);
	//	Utils.log("getComplaintNo","is: "+complObj.getComplaintNo());
	//	Utils.log("getMemberLoginId","is: "+complObj.getMemberLoginId());
		ComplaintObj compObj=dbHandler.getComplaintObj(complObj.getComplaintNo());
		
		if(compObj!=null){
			//Utils.log("Db Comp No",":"+compObj.getComplaintNo());
			//Utils.log("service Comp No",":"+complObj.getComplaintNo());
		if(compObj.getComplaintNo().equalsIgnoreCase(complObj.getComplaintNo())){
			
			
			Utils.log(" Comp No","BLACK:"+complObj.getComplaintNo());
			holder.txtTitle.setTextColor(Color.BLACK);
			holder.txtTitle.setText(complObj.getComplaintNo());
			holder.txtuserid.setTextColor(Color.BLACK);
			Utils.log("Read","Complaints");
			holder.txtuserid.setText(complObj.getMemberLoginId());
			
			
			
		}
		else{
			holder.txtTitle.setTextColor(Color.RED);
			holder.txtTitle.setText(complObj.getComplaintNo());
			holder.txtuserid.setTextColor(Color.RED);
			Utils.log("UnRead","Complaints");
			holder.txtuserid.setText(complObj.getMemberLoginId() );
			
		}
		}
		else{
			
			holder.txtTitle.setTextColor(Color.RED);
			holder.txtTitle.setText(complObj.getComplaintNo());
			holder.txtuserid.setTextColor(Color.RED);
			Utils.log("UnRead","Complaints");
			holder.txtuserid.setText(complObj.getMemberLoginId());
			
			
		}
		
		
		//holder.imgIcon.setImageResource(complObj.icon);
		return row;
	}
	
	
	static class ComplaintListHolder
	{
		//ImageView imgIcon;
		TextView txtTitle;
		TextView txtuserid;
	}
	
	  public void filter(String charText) {
	        charText = charText.toLowerCase(Locale.getDefault());
	        Utils.log("arraylist","size is before: "+arraylist.size());
	        alComplaintList.clear();
	        if (charText.length() == 0) {
	        	alComplaintList.addAll(arraylist);
	        } else {
	        	Utils.log("arraylist","size is: "+arraylist.size());
	            for (ComplaintListObj wp : arraylist) {
	                if (wp.getMemberLoginId().toLowerCase(Locale.getDefault())
	                        .contains(charText)) {
	                	alComplaintList.add(wp);
	                }
	            }
	        }
	    	Utils.log("alComplaintList","size is: "+alComplaintList.size());
	        notifyDataSetChanged();
	    }
	
}
