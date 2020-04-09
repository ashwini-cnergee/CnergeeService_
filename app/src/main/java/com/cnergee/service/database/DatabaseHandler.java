/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  07 Feb. 2013
 *
 * @Author 
 * Ashok Parmar
 * 
 * Version 1.0
 *
 */
package com.cnergee.service.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cnergee.service.obj.ComplaintObj;
import com.cnergee.service.util.Utils;

public class DatabaseHandler extends SQLiteOpenHelper {

	private String TAG="DatabaseHandler";
	 // Database Version
    private static final int DATABASE_VERSION = 5;
 
    // Database Name
    private static final String DATABASE_NAME = "CnergeeServiceManager";
 
    // Contacts table name
    public static final String TABLE_CONTACTS = "complaint";
    
    private static final String TABLE_LOCATION = "location";
    private static final String TABLE_PROSPECT = "prospect_master";
 
    // Contacts Table Columns names
   // private static final String docket_no = "docketno";
    private static final String compl_no = "complaintno";
    //private static final String compl_det = "complaint";
    private static final String compl_category = "complcategory";
    private static final String compl_id = "complid";
    private static final String user_id = "userid";
    private static final String cust_name = "custname";
    private static final String cust_add = "custadd";
    private static final String cust_phone = "custphone";
    private static final String comments = "comments";
    
    private static final String status = "status";
    private static final String date = "date";
    private static final String time = "time";
    private static final String assdate = "assdate";
    
    public static final String ROW_ID="_id";
    public static final String LATITUDE="latitude";
    public static final String LONGITUDE="longitude";
    public static final String MEMBER_ID="member_id";
    public static final String DATE="datetime";
   
    public static final String PROVIDER="provider";
    public static final String GPS_STATUS="gps_status";
    public static final String ACTIVITY="activity";
    
    public static final String TABLE_ENQUIRY="enquiry";
    public static final String TICKET_NUMBER="ticket_no";
    
    public static final String TABLE_SHIFTING="shifting";
    public static final String TABLE_DEACTIVATION="deactivation";
    
    
    
  /*  public static final String ENQUIRY_CUST_NAME="cust_name";
    public static final String ENQUIRY_ADDRESS="address";
    public static final String ENQUIRY_CONN_DATE="enquiry_conn_date";
    public static final String ENQUIRY_VISIT_DATE="enquiry_visit_date";
    public static final String ENQUIRY_STATUS_ID="enquiry_sttus_id";
    public static final String ENQUIRY_COMMENTS="comments";
    public static final String ENQUIRY_MOBILE="mob_number";*/
    
    String CREATE_ENQUIRY_TABLE = "CREATE TABLE " + TABLE_ENQUIRY + "("
            + ROW_ID + " INTEGER PRIMARY KEY," 
            + TICKET_NUMBER + " TEXT)";
            /*+ ENQUIRY_CUST_NAME + " TEXT,"
            + ENQUIRY_ADDRESS + " TEXT,"
            + ENQUIRY_CONN_DATE + " TEXT," 
            + ENQUIRY_VISIT_DATE + " TEXT,"
            + ENQUIRY_STATUS_ID + " TEXT,"
            + ENQUIRY_COMMENTS + " TEXT,"
            + ENQUIRY_MOBILE + " TEXT )"*/
    
    String CREATE_SHIFTING_TABLE = "CREATE TABLE " + TABLE_SHIFTING + "("
            + ROW_ID + " INTEGER PRIMARY KEY," 
            + TICKET_NUMBER + " TEXT)";
    
    String CREATE_DEACTIVATION_TABLE = "CREATE TABLE " + TABLE_DEACTIVATION + "("
            + ROW_ID + " INTEGER PRIMARY KEY," 
            + TICKET_NUMBER + " TEXT)";
    
    String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
            + ROW_ID + " INTEGER PRIMARY KEY," 
            + LATITUDE + " TEXT,"
            + LONGITUDE + " TEXT,"
            + MEMBER_ID + " TEXT,"
            + DATE + " TEXT," 
            + ACTIVITY + " TEXT,"
            + PROVIDER + " TEXT,"
            + GPS_STATUS + " TEXT )";
    
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		 String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
	                + compl_no + " TEXT PRIMARY KEY," 
	                + compl_category + " TEXT,"
	                + compl_id + " TEXT,"
	                + user_id + " TEXT,"
	                + cust_name + " TEXT,"
	                + cust_add + " TEXT,"
	                + cust_phone + " TEXT,"
	                + status + " TEXT,"
	                 + comments + " TEXT,"
	                
	                + date + " TEXT,"
	                + time + " TEXT,"
	                + assdate + " TEXT )";
		 
	        db.execSQL(CREATE_CONTACTS_TABLE);
	        db.execSQL(CREATE_LOCATION_TABLE);
	        db.execSQL(CREATE_ENQUIRY_TABLE);
	        db.execSQL(CREATE_SHIFTING_TABLE);
	        db.execSQL(CREATE_DEACTIVATION_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		 // Drop older table if existed
     
        // Create tables again
        
       
	}
	// Adding new complaints
    public void addComplaint(ComplaintObj complaint) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(compl_no, complaint.getComplaintNo());
        values.put(cust_name, complaint.getCustomerName());
        values.put(compl_category, complaint.getComplaintCategory());
        values.put(compl_id, complaint.getComplaintId());
        values.put(user_id, complaint.getUserId());
        values.put(cust_add, complaint.getCustomerAddress());
        values.put(cust_phone, complaint.getPhone());
        values.put(status, complaint.getStatus());
        values.put(comments, complaint.getComments());
        values.put(date, complaint.getComplaintDate());
        values.put(time, "");
        values.put(assdate, complaint.getAssignedDate());
        
 
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    
    // Getting single contact
    public ComplaintObj getComplaintObj(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { compl_no,cust_name,cust_add,cust_phone,date,comments,compl_category,compl_id,status,user_id,assdate}, compl_no + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
       
        ComplaintObj complaint=null;
        if (cursor != null){
        	  if (cursor.moveToFirst()) {
        		  	complaint = new ComplaintObj();
        	        complaint.setComplaintNo(cursor.getString(0));
        	        complaint.setComments(cursor.getString(5));
        	        complaint.setComplaintDate(cursor.getString(4));
        	        complaint.setPhone(cursor.getString(3));
        	        complaint.setCustomerAddress(cursor.getString(2));
        	        complaint.setCustomerName(cursor.getString(1));
        	        complaint.setComplaintCategory(Integer.parseInt(cursor.getString(6)));
        	        complaint.setComplaintId(Integer.parseInt(cursor.getString(7)));
        	        complaint.setStatus(cursor.getString(8));
        	        complaint.setUserId(cursor.getString(9));
        	        complaint.setAssignedDate(cursor.getString(10));
        	        
        	        
        	  }
        }
        
        cursor.close();
        db.close(); // Closing database connection
        // return complaint
        return complaint;
    }
 
    
    // Getting All Contacts
    public List<ComplaintObj> getAllContacts() {
        List<ComplaintObj> contactList = new ArrayList<ComplaintObj>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	ComplaintObj complaint = new ComplaintObj();
                complaint.setComplaintNo(cursor.getString(0));
              /*  complaint.setName(cursor.getString(1));
                complaint.setPhoneNumber(cursor.getString(2));*/
                // Adding contact to list
                contactList.add(complaint);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close(); // Closing database connection
        
        // return contact list
        return contactList;
    }
 
    // Updating single Complaint
    public int updateComplaint(String complaintNo,String statusVal,String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(status,statusVal);
        values.put(comments,comment);
   
        // updating row
        int intUpdate =  db.update(TABLE_CONTACTS, values, compl_no + " = ?",
                new String[] { String.valueOf(complaintNo) });
        db.close();
        return intUpdate;
    }
 // Deleting single Complaint
    public void deleteComplaint(String complaintNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, compl_no + " = ?",
                new String[] { String.valueOf(complaintNo) });
        db.close();
    }
 // Getting Complaint Count
    public int getComplaintCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
      
        db.close(); // Closing database connection
        // return count
        return count;
    }
 
    public long insertLocation(String str_latitude,String str_longitude,String str_memid,String str_date,String str_provider,String str_gps_status,String str_activity){
    	SQLiteDatabase db= this.getWritableDatabase();
    		long i=-1;
    		ContentValues cv = new ContentValues();
    		cv.put(LATITUDE, str_latitude);
    		cv.put(LONGITUDE, str_longitude);
    		cv.put(MEMBER_ID, str_memid);
    		cv.put(DATE, str_date);
    		
    		cv.put(PROVIDER, str_provider);
    		cv.put(GPS_STATUS, str_gps_status);
    		cv.put(ACTIVITY, str_activity);
    		i=db.insert(TABLE_LOCATION, null, cv);
    		db.close();
    		return i;
    		
    	}
    	
    	public Cursor getLocation(){
    		SQLiteDatabase db= this.getWritableDatabase();
    		Cursor mCur = null;
    		mCur=db.query(TABLE_LOCATION, null, null, null, null, null, null);
    		Utils.log("Count is",":"+mCur.getCount());
    		db.close();
    		return mCur;
    		
    	}
    	
    	public void DeleteRow(String row_id){
    		SQLiteDatabase db= this.getWritableDatabase();
    		int i=db.delete(TABLE_LOCATION, ROW_ID+"=?", new String[]{row_id});
    		Utils.log("Row Deleted","is: "+i);
    		db.close();
    		
    	}
    
    	
    	public void DeleteTicketRow(String TableName,String ticket_number){
    		SQLiteDatabase db= this.getWritableDatabase();
    		int i=db.delete(TableName, TICKET_NUMBER+"=?", new String[]{ticket_number});
    		Utils.log("Ticket Row Deleted","is: "+i);
    		db.close();
    		
    	}
    	
    	public boolean isExist(String TableName,String TicketNumber){
    		try{
    		String countQuery = "SELECT  * FROM " + TableName+" WHERE "+TICKET_NUMBER+"= '"+TicketNumber+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
          
            db.close();
            Utils.log("isExist","Count:"+count);
            if(count>0){
            	return true;
            }
            else{
            	return false;
            }
    		}
    		catch(Exception e){
    			Utils.log(TAG+"","isExist"+e);
    			return false;
    		}
    	}
    	
    	 public long insertTicketRow(String TableName,String TicketNo){
    	    		SQLiteDatabase db= this.getWritableDatabase();
    	    		long i=-1;
    	    		ContentValues cv = new ContentValues();
    	    		cv.put(TICKET_NUMBER, TicketNo);    	    		
    	    		i=db.insert(TableName, null, cv);
    	    		db.close();
    	    		return i;
    	    		
    	    	}
    	 public int getTicketCount(String TableName){
    		 int count=0;
    		 try{
    	    		String countQuery = "SELECT  * FROM " + TableName;
    	            SQLiteDatabase db = this.getReadableDatabase();
    	            Cursor cursor = db.rawQuery(countQuery, null);
    	             count = cursor.getCount();
    	            cursor.close();
    	          
    	            db.close();
    		 }  catch(Exception e){
    	    			Utils.log(TAG+"","getTicketCount"+e);
    	    			return count;
    	    		}
    		 return count;
    	 }
    	 
    	 public ArrayList<String> getAllTickets(String TableName){
    		 ArrayList<String> alTicketsDb=new ArrayList<String>();
    		 try{
    		
    		 String countQuery = "SELECT  * FROM " + TableName;
    		 SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.rawQuery(countQuery, null);
             if(cursor!=null){
            	 if(cursor.getCount()>0){
            		 while(cursor.moveToNext()){
            			 alTicketsDb.add(cursor.getString(cursor.getColumnIndex(TICKET_NUMBER)));
            		 }
            	 }
             }
             return alTicketsDb;
    		 }
    		 catch(Exception e){
    			 return alTicketsDb;
    		 }
    	 }
}
