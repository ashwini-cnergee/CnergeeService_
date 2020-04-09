package com.cnergee.service.obj;

public class Common_Item 
{
   String item_id,item_name;
   String item_amount,item_validity;
   String channel_mgr,Role_Name;



public Common_Item(String item_id, String item_name) {
	super();
	this.item_id = item_id;
	this.item_name = item_name;
}


public String getItem_id() {
	return item_id;
}

public void setItem_id(String item_id) {
	this.item_id = item_id;
}

public String getItem_name() {
	return item_name;
}

public void setItem_name(String item_name) {
	this.item_name = item_name;
}

public String getItem_validity() {
	return item_validity;
}

public void setItem_validity(String item_validity) {
	this.item_validity = item_validity;
}

public String getChannel_mgr() {
	return channel_mgr;
}

public void setChannel_mgr(String channel_mgr) {
	this.channel_mgr = channel_mgr;
}

public String getRole_Name() {
	return Role_Name;
}

public void setRole_Name(String role_Name) {
	Role_Name = role_Name;
}

    public Common_Item(String packageId, String planName, String planAmount) {
        super();
        this.item_id = packageId;
        this.item_name = planName;
        this.item_amount = planAmount;
    }


    public String getItem_amount() {
        return item_amount;
    }

    public void setItem_amount(String item_amount) {
        this.item_amount = item_amount;
    }
   
}
