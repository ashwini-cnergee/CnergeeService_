package com.cnergee.service;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.AllTicketDetailsSOAP;
import com.cnergee.service.SOAP.GetStatusListSOAP;
import com.cnergee.service.SOAP.GetStatusSOAP;
import com.cnergee.service.SOAP.UpdateAllTicketDetailSOAP;
import com.cnergee.service.SOAP.UpdateNotificationServiceSOAP;
import com.cnergee.service.broadcast.AlarmBroadcastReceiver;
import com.cnergee.service.database.DatabaseHandler;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;

public class DeactivationDetailsActivity extends Activity{

	String TicketNo="",Flag="";
	ProgressDialog prgDialog;
	AuthenticationMobile Authobj;
	Utils utils;	
	SharedPreferences sharedPreferences;
	private String TAG="DeactivationDetailsActivity";
	PackageInfo pInfo;
	DatabaseHandler dbHandler;
	String xml_status="deactivation_status";
	TextView tvDeactivationNo,tvCustName,tvAddr,tvMobNo,tvCustMemLoginId,tvLastComment;
	TextView tvHeaderView;
	Spinner spStatus;
	Button btnBack,btnUpdate;
	EditText etComments;
	ImageView ivHome,ivLeftIcon;
	ArrayList<String> alStatusId;
	ArrayList<String> alStatusName;
	ArrayAdapter<String> aaStatus;
	String getUpdateDataString="";
	String currentStatus="0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_deactivation_details);
		Intent i= getIntent();
		TicketNo=i.getStringExtra("TicketNo");
		Flag=i.getStringExtra("flag");
		Utils.log("TicketNo",":"+TicketNo);
		
		initializeControls();
		utils=new Utils();
		 sharedPreferences = getApplicationContext()
					.getSharedPreferences(getString(R.string.shared_preferences_name), 0); // 0 - for private mode
			
		utils.setSharedPreferences(sharedPreferences);
		tvHeaderView.setText("Deactivation Details");
		
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		alStatusId= new ArrayList<String>();
		alStatusName= new ArrayList<String>();
		
		aaStatus=new ArrayAdapter<String>(DeactivationDetailsActivity.this,	R.layout.spinner_item, alStatusName);
		spStatus.setAdapter(aaStatus);
		
		Authobj = new AuthenticationMobile();
		Authobj.setMobileNumber(utils.getMobileNumber());
		Authobj.setMobLoginId(utils.getMobLoginId());
		Authobj.setMobUserPass(utils.getMobUserPass());
		Authobj.setIMEINo(utils.getIMEINo());
		Authobj.setCliectAccessId(utils.getCliectAccessId());
		Authobj.setMacAddress(utils.getMacAddress());
		Authobj.setPhoneUniqueId(utils.getPhoneUniqueId());
		Authobj.setAppVersion(pInfo.versionName);
		dbHandler=new DatabaseHandler(DeactivationDetailsActivity.this);
		
		if(!dbHandler.isExist(DatabaseHandler.TABLE_DEACTIVATION, TicketNo)){
			dbHandler.insertTicketRow(DatabaseHandler.TABLE_DEACTIVATION, TicketNo);
			new UpdateNotificationServiceAsyncTask().execute();
		}
		//new UpdateEnquiryDetailsAsyncTask().execute();
		if(!utils.isXMLFile(xml_status)){
			if(utils.isOnline(DeactivationDetailsActivity.this)){
			 new GetStatusListAsyncTask().execute();
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
				builder.setMessage(Html.fromHtml("<font color='#FA0606'>Unable to connect to the Internet.<br>Can't display the details because your device isn't connected to the Internet.</font>"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Internet Connection</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
				        	   Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
								backIntent.putExtra("flag", Flag);
								startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		else{
			if(utils.isOnline(DeactivationDetailsActivity.this)){
		new GetDeactivationDetailsAsynTask().execute();
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
				builder.setMessage(Html.fromHtml("<font color='#FA0606'>Unable to connect to the Internet.<br>Can't display the details because your device isn't connected to the Internet.</font>"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Internet Connection</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
				        	   Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
								backIntent.putExtra("flag", Flag);
								startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		new GetPackageAsyncTask().execute();
		bindStatusXML();
		}
		
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.log("Update"," Clicked");
				
				if(!spStatus.getSelectedItem().toString().equalsIgnoreCase("Select Status")){
					if(etComments.getText().toString().length()>0){
						new UpdateDeactivationDetailsAsyncTask().execute();
					}
					else{
						Toast.makeText(DeactivationDetailsActivity.this, "Please enter commment!!", Toast.LENGTH_LONG).show();
						Utils.log("Length","is:"+etComments.getText().length());
						
					}
				}
				else{
					Utils.log("Spinner","is:"+spStatus.getSelectedItem().toString());
					Toast.makeText(DeactivationDetailsActivity.this, "Please enter status!!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
				backIntent.putExtra("flag", Flag);
				startActivity(backIntent);
			}
		});
		
		ivHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent homeIntent= new Intent(DeactivationDetailsActivity.this,DashboardActivity.class);					
				startActivity(homeIntent);
			}
		});
	}
	
	public void initializeControls(){
		
		tvDeactivationNo=(TextView) findViewById(R.id.tvDeactivationNo);
		tvCustName=(TextView) findViewById(R.id.tvDeactivationCustName);
		tvAddr=(TextView) findViewById(R.id.tvDeactivationAddress);
		tvMobNo=(TextView) findViewById(R.id.tvDeactivationMobNo);
		tvLastComment=(TextView) findViewById(R.id.tvDeactivationLastComment);
		tvCustMemLoginId=(TextView) findViewById(R.id.tvDMemberLoginId);
		
		
		tvHeaderView=(TextView) findViewById(R.id.headerView);
		
		spStatus=(Spinner) findViewById(R.id.spDeactivationStatus);
		btnBack=(Button) findViewById(R.id.backBtn);
		btnUpdate=(Button) findViewById(R.id.updateBtn);
		ivHome=(ImageView) findViewById(R.id.ivHome);
		etComments=(EditText) findViewById(R.id.etComment);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	/*	finish();
		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
		backIntent.putExtra("flag", Flag);
		startActivity(backIntent);*/
	}
	
	/*
	 * class: GetStatusListAsyncTask AyncTask
	 * Used For:These class used for Get All status for enquiry Deatils to bind with Spinner.
	 * @param:
	 * */
	
	/**
	 * @param AsyncTask used to get all status for enquiry Details to bind with Spinner.
	 */
	public class GetStatusListAsyncTask extends AsyncTask<String, Void, Void>{

		GetStatusListSOAP getStatusListSOAP;
		String getStatusListResult="";
		String xm_status_list_response="";
		ProgressDialog prgDialog1;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			alStatusId=new ArrayList<String>();
			alStatusName=new ArrayList<String>();
			
			prgDialog1= new ProgressDialog(DeactivationDetailsActivity.this);
			prgDialog1.setCancelable(false);
			prgDialog1.setMessage("Please Wait...");
			prgDialog1.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			getStatusListSOAP=new GetStatusListSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_GET_ALL_STATUS_LIST));
			try {
				getStatusListResult=getStatusListSOAP.getStatusList(utils.getAppUserId(), "D", Authobj);
				xm_status_list_response=getStatusListSOAP.getXMLResponse();
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(getStatusListResult.length()>0){
				if(getStatusListResult.equalsIgnoreCase("OK")){
					if(xm_status_list_response.length()>0){
						Utils.writeXMLFile(xml_status, xm_status_list_response, DeactivationDetailsActivity.this);
						bindStatusXML();
						new GetDeactivationDetailsAsynTask().execute();
						
					}
					else{
						if(prgDialog1!=null){
							if(prgDialog1.isShowing()){
								prgDialog1.dismiss();
							}
						}
						/*
						  *  When xml response is null
						  * */
						AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
						builder.setMessage(Html.fromHtml("Problem with status list, Please Contact Admin"))
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
						       .setCancelable(false)
						       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	finish();
						       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
						           }
						       });
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
				else{
					if(prgDialog1!=null){
						if(prgDialog1.isShowing()){
							prgDialog1.dismiss();
						}
					}
					/*
					  *  When result is not returned as 'OK'
					  * */
					AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
					builder.setMessage(Html.fromHtml("Problem Fetching status data from server, Please Contact Admin"))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
			else{
			
				if(prgDialog1!=null){
					if(prgDialog1.isShowing()){
						prgDialog1.dismiss();
					}
				}
				
				/*
				 * When result is blank
				 * */
				AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
				builder.setMessage(Html.fromHtml("Error while retrieving status data from server, Please Contact Admin"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
					       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
					       		backIntent.putExtra("flag", Flag);
					       		startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
			
			if(prgDialog1!=null){
				if(prgDialog1.isShowing()){
					prgDialog1.dismiss();
				}
			}
		}
		
}
	
	
	/*
	 * class: GetDeactivationDetailsAsynTask AyncTask
	 * Used For:These class used for Get Details for particular Ticket.
	 * @param:
	 * */
	
	/**
	 * @param AsyncTask   used to get all Deactivation of Subscriber details for specific Ticket Number.
	 */
	public class GetDeactivationDetailsAsynTask extends AsyncTask<String, Void, Void>{
		AllTicketDetailsSOAP allDetailsSOAP;
		String deactivationDeatilsResult="";
		String json_deact_details_response="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			prgDialog= new ProgressDialog(DeactivationDetailsActivity.this);
			prgDialog.setCancelable(false);
			prgDialog.setMessage("Please Wait...");
			prgDialog.show();
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			allDetailsSOAP= new AllTicketDetailsSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_GET_ALL_TICKET_DETAILS));
			try {
				deactivationDeatilsResult=allDetailsSOAP.getAllDetails(utils.getAppUserId(), TicketNo, "DD", Authobj);
				json_deact_details_response=allDetailsSOAP.getJsonResponse();
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Utils.log(TAG+"","Result: "+deactivationDeatilsResult);
			Utils.log(TAG+"","JSON RESPONSE: "+json_deact_details_response);
			if(prgDialog!=null){
				if(prgDialog.isShowing()){
					prgDialog.dismiss();
				}
			}
			bindStatusXML();
			if(deactivationDeatilsResult.length()>0){
				if(deactivationDeatilsResult.equalsIgnoreCase("OK")){
					if(json_deact_details_response.length()>0){
						conevrtDeactivationDetailsFromJson(json_deact_details_response);
						bindStatusXML();
						new UpdateNotificationServiceAsyncTask().execute();
					}
					else{
						/*
						  *  When json response is null
						  * */
						AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
						builder.setMessage(Html.fromHtml("No data on server for this Subscriber, Please Contact Admin"))
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
						       .setCancelable(false)
						       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	finish();
						       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
						           }
						       });
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
				else{
					/*
					  *  When result is not returned as 'OK'
					  * */
					AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
					builder.setMessage(Html.fromHtml("Problem Fetching data from server, Please Contact Admin"))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
			else{
			
				/*
				 * When result is blank
				 * */
				AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
				builder.setMessage(Html.fromHtml("Error while retrieving data from server, Please Contact Admin"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
					       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
					       		backIntent.putExtra("flag", Flag);
					       		startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
			
		}
		
	}
	
	
	/*
	 * class: UpdateDeactivationDetailsAsyncTask AyncTask
	 * Used For:These class used to update the status and comment for particular Ticket Number
	 * @param:
	 * */
	
	/**
	 * @param AsyncTask used to update the status and comments for particular Ticket Number on server
	 */
	public class UpdateDeactivationDetailsAsyncTask extends AsyncTask<String, Void, Void> {

	UpdateAllTicketDetailSOAP updateAllTicketDetailSOAP;
	String updateDeactDetailsResult="";
	String update_deact_details_response="";
	ProgressDialog prgDialog;
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		prgDialog= new ProgressDialog(DeactivationDetailsActivity.this);
		prgDialog.setCancelable(false);
		prgDialog.setMessage("Please Wait...");
		prgDialog.show();
	}
	
	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		updateAllTicketDetailSOAP=new UpdateAllTicketDetailSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_UPDATE_ALL_TICKET_DETAILS));
		try {
			updateDeactDetailsResult=updateAllTicketDetailSOAP.updateAllTicketDetails(utils.getAppUserId(), TicketNo, etComments.getText().toString(), alStatusId.get(spStatus.getSelectedItemPosition()), "Discontinue",  Authobj);
			update_deact_details_response=updateAllTicketDetailSOAP.getJsonResponse();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(prgDialog!=null){
			if(prgDialog.isShowing()){
				prgDialog.dismiss();
			}
		}
		Utils.log("Result","is:"+updateDeactDetailsResult);
		if(updateDeactDetailsResult.length()>0){
			if(updateDeactDetailsResult.equalsIgnoreCase("OK")){
				if(update_deact_details_response.length()>0){
					AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
					builder.setMessage(update_deact_details_response)
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
					
					if(sharedPreferences.getBoolean("location", false)){
						Intent i = new Intent(DeactivationDetailsActivity.this,AlarmBroadcastReceiver.class);
						i.putExtra("activity", "deactivate");
						sendBroadcast(i);
					}
				}
				else{
					AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
					builder.setMessage("We are unable to process your request.")
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   finish();
						       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
						       		backIntent.putExtra("flag", Flag);
						       		startActivity(backIntent);
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
				builder.setMessage(update_deact_details_response)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   finish();
					       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
					       		backIntent.putExtra("flag", Flag);
					       		startActivity(backIntent);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		
	}
	
}
	
	
	/*
	 * class: UpdateNotificationServiceAsyncTask AyncTask
	 * Used For:These class used to update read status for specific Ticket Number on the server
	 * 
	 * */
	
	/**
	 * @param AsyncTask used to update read status for specific Ticket Number on the server
	 */
	public class UpdateNotificationServiceAsyncTask extends AsyncTask<String, Void, Void>{
		UpdateNotificationServiceSOAP updateNotificationServiceSOAP;
		String result="";
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
		//	Utils.log("DeactivationDetailsActivity PreExcute","started");
		}
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
		//	Utils.log("DeactivationDetailsActivity doInBackground","started");
			updateNotificationServiceSOAP= new UpdateNotificationServiceSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), utils.getDynamic_Url(), getString(R.string.METHOD_UPDATE_ALL_NOTIFICATION));
			try {
				updateNotificationServiceSOAP.updateNotifications(utils.getAppUserId(), "", "","",TicketNo, "R", Authobj);
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		//	Utils.log("DeactivationDetailsActivity onPostExecute","started");
			
		}
	}

	
	/**
	 * @param Method used to convert json response into string and print those string to respected textviews.
	 */
	public void conevrtDeactivationDetailsFromJson(String json) {
		if (json != null) {
			if(json.length()>0){
			try {
				JSONObject mainJson = new JSONObject(json);
				
				if(mainJson.has("NewDataSet")){
				JSONObject newDatasetJson = mainJson.getJSONObject("NewDataSet");
				if (newDatasetJson.has("Table")) {

					if (newDatasetJson.get("Table") instanceof JSONObject) {

						JSONObject TableJsonObject = newDatasetJson.getJSONObject("Table");
						if(TableJsonObject.has("Address")){
							tvAddr.setText(TableJsonObject.getString("Address"));
						}
						
						if(TableJsonObject.has("Comments")){
							tvLastComment.setText(TableJsonObject.getString("Comments"));
						}
						
						if(TableJsonObject.has("CustomerName")){
							tvCustName.setText(TableJsonObject.getString("CustomerName"));
						}
						
						if(TableJsonObject.has("MemberDeactComplaintNo")){
							tvDeactivationNo.setText(TableJsonObject.getString("MemberDeactComplaintNo"));
						}
						if(TableJsonObject.has("MemberLoginID")){
							tvCustMemLoginId.setText(TableJsonObject.getString("MemberLoginID"));
						}
						
						if(TableJsonObject.has("MobileNo")){
							tvMobNo.setText(TableJsonObject.getString("MobileNo"));
						}
						
						if(TableJsonObject.has("StatusId")){
							currentStatus=TableJsonObject.getString("StatusId");
						}
						
						
					}
					else{
						 /*
						  *  In JSON FORMAT 'Table' instance exist but not the JSONObject
						  * */
						
						AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
						builder.setMessage(Html.fromHtml("Problem with the data. Please contact Admin"))
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
						       .setCancelable(false)
						       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
										finish();
						           }
						       });
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
				else{
					 /*
					  *  In JSON FORMAT 'Table' instance is not exist
					  * */
					
					AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
					builder.setMessage(Html.fromHtml("Problem with the server data. Please contact Admin"))
							.setIcon(android.R.drawable.ic_dialog_alert)
							.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
					       .setCancelable(false)
					       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
									finish();
					           }
					       });
					AlertDialog alert = builder.create();
					alert.show();
				}
			}
				else{
					
				}
			} catch (Exception e) {

			}
			}
		}
	}

	/**
	 * @param Method used to bind status xml to spinner stored in sd card.
	 */
	public void bindStatusXML(){
		try{
			if(alStatusId!=null){
				alStatusId.clear();
			}
			if(alStatusName!=null){
				alStatusName.clear();
			}
			Document doc=null;
			try {
				
			
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			 doc = db.parse(new File(Utils.TARGET_BASE_PATH + "/" + xml_status));
				doc.getDocumentElement().normalize();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			NodeList nodeList = doc.getElementsByTagName("Table");
			alStatusName.add("Select Status");
			alStatusId.add("0");
			for(int i=0;i<nodeList.getLength();i++)
			{
				Node node = nodeList.item(i);
				Element firstElmnt = (Element) node;
				NodeList nameList = firstElmnt.getElementsByTagName("Status");
				Element nameElement = (Element) nameList.item(0);
				nameList = nameElement.getChildNodes();
				String StatusName=((Node) nameList.item(0)).getNodeValue();
				
				Element secondElmnt = (Element) node;
				NodeList nameList1 = secondElmnt.getElementsByTagName("StatusId");
				Element nameElement1 = (Element) nameList1.item(0);
				nameList1 = nameElement1.getChildNodes();
				String StatusId=((Node) nameList1.item(0)).getNodeValue();
				 
				Utils.log("StatusName",":"+StatusName);
				Utils.log("StatusId",":"+StatusId);
				
				alStatusId.add(StatusId);
				alStatusName.add(StatusName);
			}
			spStatus.setAdapter(aaStatus);
			aaStatus.notifyDataSetChanged();
			aaStatus=new ArrayAdapter<String>(DeactivationDetailsActivity.this,	R.layout.spinner_item, alStatusName);
			spStatus.setAdapter(aaStatus);
			for(int j=0;j<alStatusId.size();j++){
				if(currentStatus.equalsIgnoreCase(alStatusId.get(j))){
					spStatus.setSelection(j);
					break;
				}
				else{
					spStatus.setSelection(0);
				}
			}
			
			if(spStatus.getSelectedItem().toString().equalsIgnoreCase("Closed")){
				AlertDialog.Builder builder = new AlertDialog.Builder(DeactivationDetailsActivity.this);
				builder.setMessage(Html.fromHtml("This Enquiry has been closed."))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(Html.fromHtml("<font color='#FA0606'>Alert</font>"))
				       .setCancelable(false)
				       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	finish();
				       		Intent backIntent= new Intent(DeactivationDetailsActivity.this,AllListActvity.class);	
				       		backIntent.putExtra("flag", Flag);
				       		startActivity(backIntent);
				       		dbHandler.DeleteTicketRow(DatabaseHandler.TABLE_DEACTIVATION, TicketNo);
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		catch(Exception e){
			
		}
	}
	

	/*
	 * class: GetPackageAsyncTask AyncTask
	 * Used For:These class used to check if any change in the status list and based on result 
	 * delete XML file and bind new XML file to Spinner. 
	 * 
	 * */
	
	/**
	 * @param AsyncTask used to check if any change in the status list and based on result 
	 * delete XML file and bind new XML file to Spinner. 
	 */
	public class GetPackageAsyncTask extends AsyncTask<String, Void, Void>{
			
			String getDataResult="";
			ProgressDialog  prgDialog1;
			 @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				 File direct = new File(Utils.extStorageDirectory + "/" + Utils.xml_folder);
					if(direct.exists()){
						direct.delete();
					}
				 prgDialog1= new ProgressDialog(DeactivationDetailsActivity.this);
				 prgDialog1.setMessage("Please wait...");
				 prgDialog1.setCancelable(false);
				 prgDialog1.show();
				 getUpdateDataString="";
			}
			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				GetStatusSOAP getStatusSOAP= new GetStatusSOAP(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
								utils.getDynamic_Url(), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_STATUS)
										); 
				try {
					Utils.log("UserLoginName","is: "+utils.getAppUserName());
					
					getDataResult=getStatusSOAP.getPackageSOAP(Authobj,utils.getAppUserName(),"S");
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			 @Override
			protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					if(prgDialog1!=null){
						if(prgDialog1.isShowing())
						prgDialog1.dismiss();
					}
				try{
					Utils.log("getDataResult","is: "+getDataResult);
					if(getDataResult.length()>0){
						if(getDataResult.equalsIgnoreCase("ok")){
							if(getUpdateDataString.length()>0){
								if(getUpdateDataString.equalsIgnoreCase("ResolutionUpdate")){
									
									Utils.log("PackageUpdate","is "+getUpdateDataString);
																		
											
									new GetStatusListAsyncTask().execute();
									new UpdatePackageAsyncTask().execute();
								}
							}
						}
						else{
							//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
						}
					}
					else{
							//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
					}
					
					}catch(Exception e){
						
					}
					
					
				}
		 }


	/*
	 * class: UpdatePackageAsyncTask AyncTask
	 * Used For:These class used to update read for status list
	 * 
	 * */
	
	/**
	 * @param AsyncTask used to update read for status list
	 */
	public class UpdatePackageAsyncTask extends AsyncTask<String, Void, Void>{
			
			String getDataResult="";
			
			 @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
			
			}
			@Override
			protected Void doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				GetStatusSOAP getStatusSOAP= new GetStatusSOAP(
						getApplicationContext().getResources().getString(
								R.string.WSDL_TARGET_NAMESPACE),
								utils.getDynamic_Url(), getApplicationContext()
								.getResources().getString(
										R.string.METHOD_GET_STATUS)
										); 
				try {
					Utils.log("UserLoginName","is: "+utils.getAppUserName());
					
					getDataResult=getStatusSOAP.getPackageSOAP(Authobj,utils.getAppUserName(),"U");
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			 @Override
			protected void onPostExecute(Void result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					//prgDialog1.dismiss();
				try{
					if(getDataResult.length()>0){
						if(getDataResult.equalsIgnoreCase("ok")){
							if(getUpdateDataString.length()>0){
								if(!getUpdateDataString.equalsIgnoreCase("ResolutionUpdate")){
									
									//new PackageListWebService().execute();
											
								}
							}
						}
						else{
							//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
						}
					}
					else{
							//AlertsBoxFactory.showAlert("Please Try Again !!", AppSettingctivity.this);
					}
					
					}catch(Exception e){
						
					}
					
					
				}
		 }
	 
}
