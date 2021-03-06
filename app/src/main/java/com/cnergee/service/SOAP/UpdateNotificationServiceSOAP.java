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

public class UpdateNotificationServiceSOAP {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private String TAG="UpdateNotificationServiceSOAP";
	
public UpdateNotificationServiceSOAP(String WSDL_TARGET_NAMESPACE,String SOAP_URL,String METHOD_NAME){
	this.WSDL_TARGET_NAMESPACE=WSDL_TARGET_NAMESPACE;
	this.SOAP_URL=SOAP_URL;
	this.METHOD_NAME=METHOD_NAME;
}
public String updateNotifications(String UserId,String ComplaintIds,String ProspectIds,String ShiftingIds,String DeactivationIds,String ActionType,AuthenticationMobile AuthObj) throws SocketException,SocketTimeoutException,Exception{
	String result="OK";
	try{	
		SoapObject request= new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
	
		PropertyInfo pi= new PropertyInfo();
		pi.setName("UserId");
		pi.setValue(Long.valueOf(UserId));
		pi.setType(Long.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("ComplaintNo");
		pi.setValue(ComplaintIds);
		pi.setType(String.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("ProspectNo");
		pi.setValue(ProspectIds);
		pi.setType(String.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("ShiftingNo");
		pi.setValue(ShiftingIds);
		pi.setType(String.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("DiscontinueNo");
		pi.setValue(DeactivationIds);
		pi.setType(String.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("ActionType");
		pi.setValue(ActionType);
		pi.setType(String.class);
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
		androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
		Utils.log(TAG+""+ActionType,"request:"+androidHttpTransport.requestDump);
		Utils.log(TAG+""+ActionType,"response:"+androidHttpTransport.responseDump);
		
		Utils.log(TAG+"","URL:"+SOAP_URL);
		String response= envelope.getResponse().toString();
		
		
		return result;
	}
catch(Exception e){
	Utils.log(TAG+" error",""+e);
	return "error";
}
}
}
