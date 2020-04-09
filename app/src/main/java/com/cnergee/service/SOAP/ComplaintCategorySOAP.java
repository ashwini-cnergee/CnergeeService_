/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation: 5 Mar. 2013
 *
 * Version 1.0
 *
 */
package com.cnergee.service.SOAP;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cnergee.service.obj.Authentication;
import com.cnergee.service.obj.AuthenticationMobile;
import com.traction.ashok.marshals.MarshalLong;

public class ComplaintCategorySOAP {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private static final String TAG = "CnergeeService"; 
	private String xmlResponse;
	
	
	public ComplaintCategorySOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}
	
	public String setComplaintCategoryList(long userId,AuthenticationMobile Authobj)throws SocketException,SocketTimeoutException,Exception{
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
		
		/*Log.i(" #	#####################  ", " START ");
		Log.i(TAG+" userId ", ""+userId);*/
		/*Log.i(TAG+" IMEI No ", Authobj.getIMEINo());
		Log.i(TAG+" Mobile ", Authobj.getMobileNumber());
		Log.i(TAG+" Mobile User ", Authobj.getMobLoginId());
		Log.i(TAG+" Mobile Password ", Authobj.getMobUserPass());*/
		/*Log.i(TAG+" WSDL_TARGET_NAMESPACE ", WSDL_TARGET_NAMESPACE);
		Log.i(TAG+" METHOD_NAME ", METHOD_NAME);
		Log.i(TAG+" SOAP_ACTION ", WSDL_TARGET_NAMESPACE + METHOD_NAME);
		Log.i("#####################", "");*/
		
		PropertyInfo pi = new PropertyInfo();
		
		pi.setName("UserId");
		pi.setValue(userId);
		pi.setType(Long.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName(AuthenticationMobile.AuthName);
		pi.setValue(Authobj);
		pi.setType(Authobj.getClass());
		request.addProperty(pi);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		envelope.encodingStyle = SoapSerializationEnvelope.ENC;
		envelope.implicitTypes = true;
		envelope.addMapping(WSDL_TARGET_NAMESPACE, "Authobj",
				new Authentication().getClass());

		MarshalLong mlong = new MarshalLong();
		mlong.register(envelope);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
		androidHttpTransport.debug = true;

		String str_msg = "ok";
		androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
		
		if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
			String ss = androidHttpTransport.responseDump;
			setXMLData(ss);
			
		} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
			// FAILURE
			SoapFault soapFault = (SoapFault) envelope.bodyIn;
			return soapFault.getMessage().toString();
		}
		
		return str_msg;
	}
	
	public void setXMLData(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}

	public String getXMLData() {
		return xmlResponse;
	}
}
