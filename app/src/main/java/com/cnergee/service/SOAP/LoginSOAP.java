/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  28 Jan. 2013
 *
 * Version 1.0
 *
 */
package com.cnergee.service.SOAP;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

import com.cnergee.service.obj.Authentication;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;

public class LoginSOAP {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private static final String TAG = "CnergeeService"; 
	private String userId;
	private boolean isvalid;

	
	public LoginSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}

	public String CallLoginSOAP(String username, String password,
			AuthenticationMobile Authobj)throws SocketException,SocketTimeoutException,Exception{
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
		/*Log.i(" #	#####################  ", " START ");

		Log.i(TAG+" username ", username);
		Log.i(TAG+" password ", password);
		Log.i(TAG+" IMEI No ", Authobj.getIMEINo());
		Log.i(TAG+" SIM SR.No ", Authobj.getMobileNumber());
		Log.i(TAG+" Mobile User ", Authobj.getMobLoginId());
		Log.i(TAG+" Mobile Password ", Authobj.getMobUserPass());
		Log.i(TAG+" Cust ", Authobj.getCliectAccessId());
		Log.i(TAG+" WSDL_TARGET_NAMESPACE ", WSDL_TARGET_NAMESPACE);
		Log.i(TAG+" METHOD_NAME ", METHOD_NAME);
		Log.i(TAG+" SOAP_ACTION ", SOAP_URL + METHOD_NAME);*/

		/*Authentication aut = new Authentication();
		aut.setVendorCode(Authenticat_VendorCode);
		aut.setUserName(Authenticat_user);
		aut.setPassword(Authenticat_pass);*/

		PropertyInfo pi = new PropertyInfo();
		pi.setName("UserLoginName");
		pi.setValue(username);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName("LoginPassword");
		pi.setValue(password);
		pi.setType(String.class);
		request.addProperty(pi);

		pi = new PropertyInfo();
		pi.setName(AuthenticationMobile.AuthName);
		pi.setValue(Authobj);
		pi.setType(Authobj.getClass());
		request.addProperty(pi);
		
		Log.i("Start", Authobj.getMobileNumber());
		Log.i("AuthObj", Authobj.getCliectAccessId());
		Log.i("END", Authobj.getIMEINo());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		// envelope.bodyOut = request;
	
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		/*Log.i("Request", request.toString());*/
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;
		// envelope.setOutputSoapObject(request);
		envelope.implicitTypes = true;
		envelope.addMapping(WSDL_TARGET_NAMESPACE, "Authobj",
				new Authentication().getClass());
//*********************************************************************************************		
				
		
	    
//****************************************************************************************	    
		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
		androidHttpTransport.debug = true;
		Utils.log("url is",""+WSDL_TARGET_NAMESPACE + METHOD_NAME);
		/*Utils.log("url is",""+WSDL_TARGET_NAMESPACE + METHOD_NAME);
		Utils.log("request is before",""+androidHttpTransport.requestDump);
		Utils.log("response is before",""+androidHttpTransport.responseDump);
		Utils.log("Connection  is",""+androidHttpTransport.getServiceConnection());*/
		androidHttpTransport.call(WSDL_TARGET_NAMESPACE+ METHOD_NAME,envelope);
		Utils.log("Login URL is ",""+SOAP_URL);
		Utils.log("Login request is ",""+androidHttpTransport.requestDump);
		Utils.log("Login response ",""+androidHttpTransport.responseDump);
		Object response = envelope.getResponse();
		/*Log.i(TAG+" LOGIN RESPONSE ",response.toString());*/
		
		try{
			if(Integer.parseInt(response.toString()) > 0){
				setIsValidUser(true);	
				setUserId(response.toString().trim());
			}else{
				setIsValidUser(false);
			}
		}catch(NumberFormatException n){
			/*Log.i(TAG+" LOGIN RESPONSE ",n.getMessage());*/
		}
		
		return "OK";
		
	}

	
	public void setIsValidUser(boolean isvalid) {
		this.isvalid = isvalid;
	}

	public boolean isValidUser() {
		return isvalid;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	

}
