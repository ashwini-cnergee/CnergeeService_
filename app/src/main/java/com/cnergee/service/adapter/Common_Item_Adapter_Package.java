package com.cnergee.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.cnergee.broadbandservice.R;
import com.cnergee.service.CAF_Activity;
import com.cnergee.service.obj.Common_Item;

import java.util.ArrayList;

public class Common_Item_Adapter_Package extends ArrayAdapter<Common_Item>
{
    ArrayList<Common_Item> alConnection=new ArrayList<Common_Item>();
    Context context;

    public Common_Item_Adapter_Package(Context context, int resource, ArrayList<Common_Item> alConnection)
    {
        super(context, resource,alConnection);
        this.alConnection = alConnection;
        this.context = context;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getView(position, convertView, parent);
    }


    public View getView(int position,View convertView,ViewGroup parent)
    {
        Common_Item conn_Type=(Common_Item)this.getItem(position);

        ViewHolder holder;

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.row_item1,parent,false);

            holder=new ViewHolder();
            holder.connection_name=(TextView)convertView.findViewById(R.id.tv_select1);
            holder.package_price = (TextView)convertView.findViewById(R.id.tv_package_amount1);

            convertView.setTag(holder);

        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.connection_name.setText(conn_Type.getItem_name());

        holder.package_price.setText(conn_Type.getItem_amount());
        return convertView;

    }

    public int getPosition(Common_Item position) {

        return alConnection.indexOf(position);

    }

    public static class ViewHolder

    {
        public TextView connection_name,package_price;

    }


}
