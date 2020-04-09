/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  22 Dec. 2012
 *
 * Version 1.1
 *
 
package com.cnergee.service.util;


import com.cnergee.service.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeMenuArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
 
	public HomeMenuArrayAdapter(Context context, String[] values) {
		super(context, R.layout.home_menu, values);
		this.context = context;
		this.values = values;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.home_menu, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
 
		// Change icon based on name
		String menu = values[position];
  
		if (menu.equals("Logout")) {
			imageView.setImageResource(R.drawable.logout);
		} else if (menu.equals("Update Status")) {
			imageView.setImageResource(R.drawable.edit);
		} else if (menu.equals("Complaint Logs")) {
			imageView.setImageResource(R.drawable.order_history);
		} else {
			imageView.setImageResource(R.drawable.logo_cnergee);
		}
 
		return rowView;
	}
}
*/