package com.cnergee.service;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.GenerateTrackIdSOAP;
import com.cnergee.service.SOAP.InsertAfterPaymentSOAP;
import com.cnergee.service.SOAP.InsertDetailsSOAP;
import com.cnergee.service.customview.MyButtonView;
import com.cnergee.service.customview.MyTextView;
import com.cnergee.service.loadingview.ProgressWheel;
import com.cnergee.service.obj.AppConstants1;
import com.cnergee.service.obj.DSACAF;
import com.cnergee.service.utils.AlertsBoxFactory;
import com.cnergee.service.utils.DialogUtils;
import com.cnergee.service.utils.MyUtils;
//import com.dsa.cnergee.SOAP.GenerateTrackIdSOAP;
//import com.dsa.cnergee.SOAP.InsertAfterPaymentSOAP;
//import com.dsa.cnergee.SOAP.InsertDetailsSOAP;
//import com.dsa.cnergee.customview.MyButtonView;
//import com.dsa.cnergee.customview.MyTextView;
//import com.dsa.cnergee.loadingview.ProgressWheel;
//import com.dsa.cnergee.obj.DSACAF;
import com.ezetap.sdk.AppConstants;
import com.ezetap.sdk.EzeConstants;
import com.ezetap.sdk.EzetapPayApis;
import com.ezetap.sdk.TransactionDetails;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
//import com.utils.AlertsBoxFactory;
//import com.utils.DialogUtils;
//import com.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

public class EzetapActivity extends Activity {
		MyTextView tv_amount,tv_mob_no,tv_order_no;
		MyButtonView btn_next,btn_back;
		DSACAF dsacaf;
		AlertDialog  alert ;
		AlertDialog.Builder   alertDialogBuilder;
		boolean finish;
		SharedPreferences pref;
	    private String sharedPreferences_name;
		String CRMUserId="",UserLoginName="";
		String TrackId="";
		String trans_status="", paymentid="", transrefno="", transid="", transerror="";
		//TextView tv_version;

	LinearLayout ll_all_data;
	ProgressWheel progress_wheel;

	public static NiftyDialogBuilder dialog_box;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ezetap_layout);
		sharedPreferences_name = getString(R.string.shared_preferences_name);
		pref = getApplicationContext()
				.getSharedPreferences(sharedPreferences_name, 0);
		initailizeControls();
		//pref=getApplicationContext().getSharedPreferences("Login", 0);
		CRMUserId=pref.getString("UserId","");
		UserLoginName=pref.getString("UserName","");
		//tv_version=(TextView)findViewById(R.id.tv_ver);
		try {
			PackageInfo pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
			String versionName = pinfo.versionName;
			MyUtils.l("versionName",":"+versionName);
			//tv_version.setText("Version "+versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (MyUtils.isOnline(EzetapActivity.this)) {
			new GenerateTrackIdAsync().execute();
		}
		else{
			//showFinishDialog("Please connect to internet and try again!");
			DialogUtils.show_dialog(EzetapActivity.this,"Confirmation","Please Connect to the internet"+"\n"+"Please try again!",true);
		}
	  BaseApplication.getEventBus().register(this);
	}
	
	public void initailizeControls(){
		dsacaf=(DSACAF)this.getIntent().getSerializableExtra(MyUtils.DSA_OBJECT);

		
		tv_amount=(MyTextView) findViewById(R.id.tv_amount);
		tv_mob_no=(MyTextView) findViewById(R.id.tv_mob);
		tv_order_no=(MyTextView) findViewById(R.id.tv_order_no);

		ll_all_data=(LinearLayout)findViewById(R.id.ll_all_data);
		progress_wheel=(ProgressWheel) findViewById(R.id.progress_wheel);

		tv_amount.setText(""+dsacaf.getAmount());
		tv_mob_no.setText(""+dsacaf.getMobileNumber());

		//tv_order_no.setText(""+dsacaf.ge);
		//tv_amount.setText("12.00");
		//tv_mob_no.setText("8012452589");
		
		btn_next=(MyButtonView) findViewById(R.id.btn_next);
		btn_back=(MyButtonView) findViewById(R.id.btn_back);
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new InsertDetails().execute();
			}
		});
		
	 	btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//showBackDialog("Your selection will be lost"+"\n"+"Are you sure?");
				show_dialog_backCAF(EzetapActivity.this,"Confirmation","Your selection will be lost"+"\n"+"Are you sure?",true);
			}
		});
	}
	
	 public  void SEND_DATA(){
		 alertDialogBuilder  = new AlertDialog.Builder(EzetapActivity.this);
	        alertDialogBuilder.setMessage("Are you sure you want to submit?")
	        .setCancelable(false)
	        .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int id){
	                new InsertDetails().execute();
	            }
	        });
	        alertDialogBuilder.setNegativeButton("NO",
	                new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialog, int id){
	                dialog.cancel();
	            }
	        });
	       alert  = alertDialogBuilder.create();
	       alert.show();
 }
	 public class GenerateTrackIdAsync extends AsyncTask<String, Void, Void>{
		  String request = " ";
		  String response="";
		  ProgressDialog prgDialog;
		 @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			prgDialog=new ProgressDialog(EzetapActivity.this);
			prgDialog.setCancelable(false);
			prgDialog.setMessage(getString(R.string.please_wait));
		}
		 
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			GenerateTrackIdSOAP generateTrackIdSOAP=new GenerateTrackIdSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.SOAP_URL), getString(R.string.METHOD_GET_TRANSACTION_ID));
			try {
				request=generateTrackIdSOAP.GetTransactionId(dsacaf.getCliectAccessId(),CRMUserId);
				response=generateTrackIdSOAP.getJsonResponse();
						
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
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
			prgDialog.dismiss();
			if(request.equalsIgnoreCase("OK")){
				TrackId=response;
				tv_order_no.setText(TrackId);
			}
			else{
				//showFinishDialog("We are unable to generate OrderId"+"\n"+"Please try again!");
				DialogUtils.show_dialog(EzetapActivity.this,"Confirmation","We are unable to generate OrderId"+"\n"+"Please try again!",true);
			}
		}
		 
	 }
	 
	  public class InsertDetails extends AsyncTask<String, Void, Void>
	  {
		  String rslt = " ";
		  String response="";
		 // ProgressDialog prgDialog;
		 
	   protected void onPreExecute() 
		 {
				// TODO Auto-generated method stub
		    super.onPreExecute();
			/*prgDialog= new ProgressDialog(EzetapActivity.this);
			prgDialog.setMessage("Please wait...");
			prgDialog.show();*/
			 progress_wheel.setVisibility(View.VISIBLE);
			 ll_all_data.setVisibility(View.GONE);
		  }
		
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
	      try
	       {
			  InsertDetailsSOAP insert_details=new InsertDetailsSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.SOAP_URL), getString(R.string.METHOD_INSERT_DETAILS));
			  rslt = insert_details.InsertMiniCAFDetails(dsacaf,"pending","E",TrackId);
			  response=insert_details.getJsonResponse();
		   }
		  catch(Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		  
		protected void onPostExecute(Void request) 
		 {
			progress_wheel.setVisibility(View.GONE);
			ll_all_data.setVisibility(View.VISIBLE);
			if(rslt.equalsIgnoreCase("OK")){
			if(response.length()>0)
			  {
			    if(response.contains("#"))
			      {
			    String	str[]=response.split("#");
			    MyUtils.l("String 1",":"+str[0]);
			    MyUtils.l("String 2", ":"+str[1]);
			    	if(str[1].equalsIgnoreCase("success"))
			    	 {
			    		//Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();
			    		//Display_msg("Data Inserted Successfully");
						 show_dialog_backCAF(EzetapActivity.this,"Confirmation","Data Inserted Successfully",false);
			    		startCardActivity();
			   			finish=true;
			   		 }
			    	if(str[1].equalsIgnoreCase("error"))
			   		{
			    		//Toast.makeText(getApplicationContext(), "Error:"+str[0], Toast.LENGTH_LONG).show();
			   			//Display_msg(str[0]);
						show_dialog_backCAF(EzetapActivity.this,"Confirmation",str[0],false);
			   			finish=false;
			   		}
			      }
			  }
			}
			else{
				//showFinishDialog("We are unable to send Caf Form Details"+"\n"+"Please try again!");
				DialogUtils.show_dialog(EzetapActivity.this,"Confirmation","We are unable to send Caf Form Details"+"\n"+"Please try again!",true);
			}
			  super.onPostExecute(request);
			 // prgDialog.dismiss();
		   }
	    }
	  public  void Display_msg(String msg){
			 alertDialogBuilder  = new AlertDialog.Builder(EzetapActivity.this);
			 alertDialogBuilder.setMessage(msg).setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener()
		        {
		            public void onClick(DialogInterface dialog, int id){
		            if(finish==true)
		            	{
		            		//finish();
		            		dialog.dismiss();
		            	}
		            	else
		            	{
		            		dialog.dismiss();
		            	}
		            	
		            }
		        });
		        
		       alert  = alertDialogBuilder.create();
		       alert.show();
		   }
	  
	  private void startCardActivity() {
			
			try {
				Double pay=1.00;
				/*EzetapPayApis.create(NextActivity.API_CONFIG).startCardPayment(
						this, AppConstants.REQ_CODE_PAY_CARD,UserLoginName,
						dsacaf.get_Amount(),
						TrackId, 0,
						dsacaf.get_MobileNumber(),
						TrackId, null, null, null);*/
				
				EzetapPayApis.create(NextActivity.API_CONFIG).startCardPayment(
						this, AppConstants.REQ_CODE_PAY_CARD,UserLoginName,
						pay,
						TrackId, 0,
						dsacaf.getMobileNumber(),
						TrackId, null, null, null);
				

			} catch (Exception e) {
				e.printStackTrace();
				AlertsBoxFactory
						.showAlert(
								"Not able to call Ezetap utils. " + e.getMessage(),
								EzetapActivity.this);
			}
		}
	  
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
			Log.v(""+EzetapActivity.this.getClass().getSimpleName(), "ResultCode=" + resultCode);
	  		
			switch (resultCode) {
			case EzeConstants.RESULT_SUCCESS:
				TransactionDetails aTxn;
				MyUtils.l("Transaction ","Successful");
				try {
					aTxn = TransactionDetails.getTransactionDetails(new JSONObject(data.getStringExtra(EzeConstants.KEY_RESPONSE_DATA)));
				
					MyUtils.l("Transaction"," transaction Executed :"+EzeConstants.KEY_RESPONSE_DATA);
				trans_status="success";
				paymentid=aTxn.getTransactionId();				
				transrefno=aTxn.getReverseReferenceNumber(); 
				transid=aTxn.getAuthCode();
				transerror=aTxn.getCustomerReceiptUrl();
				MyUtils.l("Transaction Id"," is:"+ aTxn.getTransactionId());
  				MyUtils.l("Transaction getAmount"," is:"+ aTxn.getAmount());
  				MyUtils.l("Transaction getAmountFormatted"," is:"+ aTxn.getAmountFormatted());
  				MyUtils.l("Transaction getAuthCode"," is:"+ aTxn.getAuthCode());
  				MyUtils.l("Transaction getBatchNumber"," is:"+ aTxn.getBatchNumber());
  				MyUtils.l("Transaction getCardType"," is:"+ aTxn.getCardType());
  				MyUtils.l("Transaction getCustomerMobile"," is:"+ aTxn.getCustomerMobile());
  				MyUtils.l("Transaction getCustomerReceiptUrl"," is:"+ aTxn.getCustomerReceiptUrl());
  				MyUtils.l("Transaction getExternalRefNum"," is:"+ aTxn.getExternalRefNum());
  				MyUtils.l("Transaction getInvoiceNumber"," is:"+ aTxn.getInvoiceNumber());
  				MyUtils.l("Transaction getMerchantName"," is:"+ aTxn.getMerchantName());
  				MyUtils.l("Transaction getStatus"," is:"+ aTxn.getStatus());
  				MyUtils.l("Transaction getReverseReferenceNumber"," is:"+ aTxn.getReverseReferenceNumber());
  				MyUtils.l("Transaction getTimeStamp"," is:"+ aTxn.getTimeStamp());
  				MyUtils.l("Transaction getTotalAmount"," is:"+ aTxn.getTotalAmount());
  				MyUtils.l("Transaction getPaymentMode"," is:"+ aTxn.getPaymentMode());
  				MyUtils.l("","is:"+ aTxn.getNameOnCard());
  				MyUtils.l("Status","is:"+ aTxn.getStatus());
  				MyUtils.l("", ""+aTxn.getAuthCode());
  				TransactionResponseDialog("Transaction is Successful");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			
			case EzeConstants.RESULT_FAILED:	
				MyUtils.l("Transaction ","Failed");
				trans_status="failed";
				
				paymentid="";				
				transrefno=""; 
				transid=""; 
				transerror="error";
				
				TransactionResponseDialog("Transaction is failed or canceled");
				break;
				
			default:
				MyUtils.l("Transaction ","Failed");
				trans_status="failed";
				
				paymentid="";				
				transrefno=""; 
				transid=""; 
				transerror="error";
				TransactionResponseDialog("Transaction is failed or canceled");
				break;
			}
		}
		
		public class InserAfterPayment extends AsyncTask<String, Void, String>{
			String rslt="";
			String response="";	
			  ProgressDialog prgDialog;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				prgDialog= new ProgressDialog(EzetapActivity.this);
				prgDialog.setMessage("Please wait..."+"\n"+"Please do not press any key.");
				prgDialog.show();
				prgDialog.setCancelable(false);
			}
			
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				 InsertAfterPaymentSOAP insertAfterPaymentSOAP=new InsertAfterPaymentSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.SOAP_URL), getString(R.string.METHOD_GET_INSERT_AFTER_PAYMENT));
				 try {
					rslt= insertAfterPaymentSOAP.InsertAfterPaymentDetails(dsacaf.getCliectAccessId(),TrackId, paymentid, trans_status, transrefno, transid, transerror);
					response=insertAfterPaymentSOAP.getJsonResponse();
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				prgDialog.dismiss();
				if(rslt.equalsIgnoreCase("OK")){
					if(response.equalsIgnoreCase("success")){
						EzetapActivity.this.finish();
						BaseApplication.getEventBus().post(new FinishEvent(CAF_Activity.class.getSimpleName()));
					}
					if(response.equalsIgnoreCase("error")){
						EzetapActivity.this.finish();
					}
				}
				else{
					//showFinishDialog("We are unable to send Caf Form Details"+"\n"+"Please try again!");
					DialogUtils.show_dialog(EzetapActivity.this,"Confirmation","We are unable to send Caf Form Details"+"\n"+"Please try again!",true);
				}
			}
			
		}
		
		public void TransactionResponseDialog(String Message){
			AlertDialog.Builder builder = new AlertDialog.Builder(EzetapActivity.this);

			builder.setMessage(
					Html.fromHtml("<font color='#20B2AA'>" + Message
							+ "</font>"))
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(
							Html.fromHtml("<font color='#20B2AA'><b>Confirmation</b></font>"))
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@TargetApi(Build.VERSION_CODES.HONEYCOMB)
								public void onClick(DialogInterface dialog, int id) {
									if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
										  new InserAfterPayment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
										}
										else {
											new InserAfterPayment().execute();
										}
								}
							});
			
			builder.show();
				
		}
		@Override
		public void onBackPressed() {
		// TODO Auto-generated method stub
		
		}
		
		public void showFinishDialog(String Message){
			AlertDialog.Builder builder = new AlertDialog.Builder(EzetapActivity.this);
			builder.setMessage(
					Html.fromHtml("<font color='#20B2AA'>" + Message
							+ "</font>"))
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(
							Html.fromHtml("<font color='#20B2AA'><b>Confirmation</b></font>"))
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@TargetApi(Build.VERSION_CODES.HONEYCOMB)
								public void onClick(DialogInterface dialog, int id) {
									EzetapActivity.this.finish();
								}
							});
			
			builder.show();
		}
		
		 public void showBackDialog(String Message){
				AlertDialog.Builder builder = new AlertDialog.Builder(EzetapActivity.this);
				builder.setMessage(
						Html.fromHtml("<font color='#20B2AA'>" + Message
								+ "</font>"))
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(
								Html.fromHtml("<font color='#20B2AA'><b>Confirmation</b></font>"))
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									@TargetApi(Build.VERSION_CODES.HONEYCOMB)
									public void onClick(DialogInterface dialog, int id) {
										EzetapActivity.this.finish();
									}
								})
				.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							@TargetApi(Build.VERSION_CODES.HONEYCOMB)
							public void onClick(DialogInterface dialog, int id) {
								
							}
						});
				
				builder.show();
			}


	public static void show_dialog_backCAF(final Context ctx, String title, String message, final boolean finish) {
		Effectstype effect = Effectstype.Slidetop;
		dialog_box = NiftyDialogBuilder.getInstance(ctx);
		dialog_box
				/*
				 * .withTitle("Confirmation") .withTitle(null) no title
				 * .withTitleColor(ctx.getString(R.color.theme_color))
				 */
				// def
				.withTitle(null)
				.withDividerColor("#eeeeee")
				// def
				// .withMessage(Message) //.withMessage(null) no Msg
				.withMessage(null)
				.withMessageColor("#eeeeee")
				// def | withMessageColor(int resid)
				.withDialogColor("#eeeeee")
				// def | withDialogColor(int resid) //def
				.withIcon(
						ctx.getResources().getDrawable(R.drawable.ic_launcher))
				.isCancelableOnTouchOutside(true) // def | isCancelable(true)
				.withDuration(700)
				// def
				.withEffect(effect).setCustomView(R.layout.dialog_box, ctx)// def
				// Effectstype.Slidetop

				.show();
		dialog_box.setCancelable(false);
		MyTextView tv_title = (MyTextView) dialog_box
				.findViewById(R.id.tv_title);
		MyTextView tv_meesage = (MyTextView) dialog_box
				.findViewById(R.id.tv_message);
		MyButtonView btn_ok = (MyButtonView) dialog_box
				.findViewById(R.id.btn_next);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (dialog_box != null) {
					dialog_box.dismiss();
				}
				if (finish) {
					((Activity) ctx).finish();
					((Activity) ctx).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				}else{
					((Activity) ctx).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				}
			}
		});
		tv_title.setText(title);
		tv_meesage.setText(message);
	}
}
