package com.cnergee.service.SOAP;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cnergee.service.obj.Authentication;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;
import com.traction.ashok.marshals.MarshalLong;

public class UnreadNotificationCountSOAP {


	private String WSDL_TARGET_NAMESPACE;
	private String METHOD_NAME;
	private String SOAP_URL;
	
	private String TAG="UnreadNotificationCountSOAP";
	private String jsonResponse="";
	
	public UnreadNotificationCountSOAP(String WSDL_TARGET_NAMESPACE,String SOAP_URL,String METHOD_NAME){
		this.WSDL_TARGET_NAMESPACE=WSDL_TARGET_NAMESPACE;
		this.SOAP_URL=SOAP_URL;
		this.METHOD_NAME=METHOD_NAME;
	}
	
	public String getUnreadNotifications(String UserId,AuthenticationMobile AuthObj) throws SocketException,SocketTimeoutException,Exception{
		String result="OK";
		try{	
			SoapObject request= new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
		
			PropertyInfo pi= new PropertyInfo();
			pi.setName("UserId");
			pi.setValue(Long.valueOf(UserId));
			pi.setType(Long.class);
			request.addProperty(pi);
			
			pi = new PropertyInfo();
			pi.setName(AuthenticationMobile.AuthName);
			pi.setValue(AuthObj);
			pi.setType(AuthObj.getClass());
			request.addProperty(pi);
			
			SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet=true;
			envelope.setOutputSoapObject(request);
			envelope.encodingStyle = SoapSerializationEnvelope.ENC;
			envelope.implicitTypes = true;
			envelope.addMapping(WSDL_TARGET_NAMESPACE, "Authobj",
					new Authentication().getClass());

			MarshalLong mlong = new MarshalLong();
			mlong.register(envelope);
			
			HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
			androidHttpTransport.debug = true;
			Utils.log(TAG+"","Before Call:");
			androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
			Utils.log(TAG+"","After Call:");
			Utils.log(TAG+"","request:"+androidHttpTransport.requestDump);
			Utils.log(TAG+"","response:"+androidHttpTransport.responseDump);
			
			Utils.log(TAG+"","URL :"+SOAP_URL);
			 jsonResponse= envelope.getResponse().toString();
			
			
			return result;
		}
	catch(Exception e){
		return "error";
	}
	}
	
	public String getUnreadJsonResponse(){
		return jsonResponse;
	}
	
	

}
