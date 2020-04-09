/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  22 Dec. 2012
 *
 * Version 1.1
 *
 */
package com.cnergee.service.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnergee.broadbandservice.R;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final String[] menuValues;
 
	public ImageAdapter(Context context, String[] menuValues) {
		this.context = context;
		this.menuValues = menuValues;
	}
	@Override
	public int getCount() {
		return menuValues.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View gridView;
	 
			if (convertView == null) {
	 
				gridView = new View(context);
	 
				// get layout from HomeMenu.xml
				gridView = inflater.inflate(R.layout.home_menu, null);
	 
				// set value into textview
				TextView textView = (TextView) gridView
						.findViewById(R.id.grid_item_label);
				textView.setText(menuValues[position]);
				//textView.setTextColor(R.color.label_blue_color);
				
	 
				// set image based on selected text
				ImageView imageView = (ImageView) gridView
						.findViewById(R.id.grid_item_image);
	 
				String mobile = menuValues[position];
	 
				if (mobile.equals("Logout")) {
					imageView.setImageResource(R.drawable.logoutt);
				} else if (mobile.equals("Update Status")) {
					imageView.setImageResource(R.drawable.edit);
				} else if (mobile.equals("Complaint Logs")) {
					imageView.setImageResource(R.drawable.order_history);
				} else {
					imageView.setImageResource(R.drawable.logo_cnergee);
				}
	 
			} else {
				gridView = (View) convertView;
			}
	 
			return gridView;
	}

}
