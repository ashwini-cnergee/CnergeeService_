package com.cnergee.service.SOAP;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

import com.cnergee.service.AppSettingActivity;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;

public class GetDynamicUrlSOAP {
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	
	
	public GetDynamicUrlSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}
	
	
	public String getDynamicUrl(String clientAccessId)throws SocketException,SocketTimeoutException,Exception {
		
		try {
	
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
		PropertyInfo pi = new PropertyInfo();
		
		pi.setName("ClientAccessId");
		pi.setValue(clientAccessId);
		pi.setType(String.class);
		request.addProperty(pi);
		
	
	
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;

		envelope.implicitTypes = true;
		envelope.addMapping(WSDL_TARGET_NAMESPACE, "Authobj",
				new AuthenticationMobile().getClass());

		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
		androidHttpTransport.debug = true;
		
		
		
	
			//Log.i("#####################", " CALL ");
			androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
		
			Utils.log("GetDynamicUrlSOAP ","request : "+androidHttpTransport.requestDump);
			Utils.log("GetDynamicUrlSOAP ","response : "+androidHttpTransport.responseDump);
			
			Log.i("#####################", " RESPONSE "+envelope.getResponse().toString());
			AppSettingActivity.DynamicUrl=envelope.getResponse().toString();
			return "ok";
		}
		 catch (Exception e) {
			 Utils.log("GetDynamicUrlSOAP","error"+e);
			e.printStackTrace();
			return e.toString();
		}
	}

}
