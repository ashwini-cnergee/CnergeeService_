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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.obj.ComplaintListObj;
import com.cnergee.service.util.Utils;


public class ComplaintListAdapter extends ArrayAdapter<ComplaintListObj> implements Filterable {

	Context context;
	int layoutResourceId;
	ComplaintListObj data[] = null;
	ComplaintListObj Displaydata[] = null;
	
	public ComplaintListAdapter(Context context, int layoutResourceId, ComplaintListObj[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.Displaydata=data;
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
		
		ComplaintListObj complObj = data[position];
		//Utils.log("getMemberLoginId","is: "+complObj.getComplaintNo());
		Utils.log("getComplaintId","is: "+complObj.getMemberLoginId());
		Utils.log("Length in ","Adapter:"+data.length);
		holder.txtTitle.setText(complObj.getComplaintNo());
		holder.txtuserid.setText(complObj.getMemberLoginId());
		//holder.imgIcon.setImageResource(complObj.icon);
		return row;
	}
	
	
	static class ComplaintListHolder
	{
		//ImageView imgIcon;
		TextView txtTitle;
		TextView txtuserid;
	}
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		
		Filter filter= new Filter() {
			
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				// TODO Auto-generated method stub
				Displaydata=(ComplaintListObj[])results.values;
				notifyDataSetChanged();
			}
			
			@SuppressLint("DefaultLocale")
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results= new FilterResults();
				ComplaintListObj Filterdata[] = {};
				ArrayList<ComplaintListObj>alFilterdata= new ArrayList<ComplaintListObj>();
				
				if (data == null) {
					Utils.log("Data results","Null: "+data.length);
					data = Displaydata; 
				}
				else{
					
					Utils.log("Data results","Not Null: "+data.length);
				}
					// saves the original data in mOriginalValues
					 if (constraint == null || constraint.length() == 0) {
						// alFilterdata.clear();
			                // set the Original result to return  
			                results.count = data.length;
			                results.values = data;
			            } else {
			                constraint = constraint.toString().toLowerCase();
			                for (int i = 0; i <data.length; i++) {
			                	ComplaintListObj complObj = data[i];
			                	Utils.log("data length","is: "+data.length);
			                	Utils.log("Filter results","is: "+data[i].getComplaintNo());
			                    String data1 = data[i].getComplaintNo();
			                    Utils.log("Search results","is: "+data1);
			                   // if (data1.toLowerCase().contains(constraint.toString())) {
			                    if (data1.toString().toLowerCase().startsWith(constraint.toString())) {
			                    	  Utils.log("constraint results","is: "+constraint.toString());
			                    	//   FilteredArrList.add(new Product(mOriginalValues.get(i).name,mOriginalValues.get(i).price));
			                       ComplaintListObj comp= new ComplaintListObj();
			                       comp.setComplaintId(data[i].getComplaintId());
			                       comp.setComplaintNo(data[i].getComplaintNo());
			                       comp.SetMemberLoginId(data[i].getMemberLoginId());
			                       alFilterdata.add(comp);
			                      // Utils.log("Last Length",": "+Filterdata.length);
			                       Filterdata = alFilterdata.toArray(new ComplaintListObj[alFilterdata.size()]);
			                       Utils.log("Last Length",": "+Filterdata.length);
			                       Utils.log("ArrayLIst Last Length",": "+alFilterdata.size());
			                        	/*	Filterdata = new ComplaintListObj[]
			                        				{comp};*/
			                        		// notifyDataSetInvalidated();
			                        	//	return results;
			                        		
			                    }
			                }
			               
			                // set the Filtered result to return
			                results.count = Filterdata.length;
			                results.values = Filterdata;
			            }     
	            
				
				return results;
			}
		};
		return filter;
	}
	/* public void filter(String charText) {
		  charText = charText.toLowerCase();
		  data=null;
		  if (charText.length() == 0) {
			  data=  Displaydata.clone();
		  } else {
			  for (int i = 0; i <Displaydata.length; i++) {
              	ComplaintListObj complObj = Displaydata[i];
              	 String data1 = Displaydata[i].getComplaintNo();
		  if (data1.toLowerCase().contains(charText)) {
			  ComplaintListObj comp= new ComplaintListObj();
              comp.setComplaintId(Displaydata[i].getComplaintId());
              comp.setComplaintNo(Displaydata[i].getComplaintNo());
              comp.SetMemberLoginId(Displaydata[i].getMemberLoginId());
              alFilterdata.add(comp);
		  }
		  }
		  }
		  notifyDataSetChanged();
		  }*/
	
}
