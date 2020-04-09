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

public class AppSettingSoap {
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	
	public AppSettingSoap(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}

	public String CallAppSettingSOAP(AuthenticationMobile Authobj)throws SocketException,SocketTimeoutException,Exception{
		try{
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
		
		Log.i(" #	#####################  ", " START ");

		
		/*Log.i(" IMEI No ", Authobj.getIMEINo());
		Log.i(" SIM SR.No ", Authobj.getMobileNumber());
		Log.i(" Mobile User ", Authobj.getMobLoginId());
		Log.i(" Mobile Password ", Authobj.getMobUserPass());
		Log.i(" Cust ", Authobj.getCliectAccessId());
		Log.i(" WSDL_TARGET_NAMESPACE ", WSDL_TARGET_NAMESPACE);
		Log.i(" METHOD_NAME ", METHOD_NAME);
		Log.i(" SOAP_ACTION ", SOAP_URL + METHOD_NAME);*/

		PropertyInfo pi = new PropertyInfo();
		

		pi = new PropertyInfo();
		pi.setName(AuthenticationMobile.AuthName);
		pi.setValue(Authobj);
		pi.setType(Authobj.getClass());
		request.addProperty(pi);
		
		

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		// envelope.bodyOut = request;
	
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;
		// envelope.setOutputSoapObject(request);
		envelope.implicitTypes = true;
		envelope.addMapping(WSDL_TARGET_NAMESPACE, "Authobj",
				new Authentication().getClass());

		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
		androidHttpTransport.debug = true;
	
		
		androidHttpTransport.call(WSDL_TARGET_NAMESPACE+ METHOD_NAME,envelope);
		Utils.log("Data sent",""+androidHttpTransport.requestDump);
		
		Utils.log("AppSettingSoap ","request:"+androidHttpTransport.requestDump);
		Utils.log("AppSettingSoap ","response :"+androidHttpTransport.responseDump);
		Object response = envelope.getResponse();
		Utils.log("Response from server",""+response);
		//setValidUser((Boolean)response);
		setValidUser(Boolean.parseBoolean(response.toString()));
		
		return "OK";
		}
		catch(Exception e){
			Utils.log("Error in AppSettingSoap",""+e);
			return e.toString();
		}
	}
	
	private Boolean isValidUser=false;
	
	public Boolean isValidUser(){
		return isValidUser;
	}
	 private void setValidUser(Boolean validUser){
		 this.isValidUser=validUser;
	 }
}
