/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation: 4 March. 2013
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
import com.cnergee.service.obj.ComplaintUpdateStatusObj;
import com.cnergee.service.util.Utils;
import com.traction.ashok.marshals.MarshalLong;

public class ComplaintStatusUpdateSOAP {

	
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private static final String TAG = "CnergeeService"; 
	private String responseMessage;
	
	public ComplaintStatusUpdateSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}
	
	public String updateComplaintStatus(ComplaintUpdateStatusObj updateObj, AuthenticationMobile Authobj)throws SocketException,SocketTimeoutException,Exception{
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
				
		/*Log.i(" #	#####################  ", " START ");
		Log.i(TAG+" userId ", ""+updateObj.getUserId());
		Log.i(TAG+" ComplaintNo ", ""+updateObj.getComplaintNo());*/
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
		pi.setValue(updateObj.getUserId());
		pi.setType(Long.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("ComplaintNo");
		pi.setValue(updateObj.getComplaintNo());
		pi.setType(String.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("Status");
		pi.setValue(updateObj.isStatus());
		pi.setType(Boolean.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("ActionType");
		pi.setValue(updateObj.getActionType());
		pi.setType(String.class);
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
		
		Utils.log("ComplaintStatusSOAP","request: "+androidHttpTransport.requestDump);
		Utils.log("ComplaintStatusSOAP","response: "+androidHttpTransport.responseDump);
		
		if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
			
			Object response = envelope.getResponse();
			if (response != null) {
				/*Log.i(" RESPONSE ", response.toString());*/
				setResponseMessage(response.toString());
			
			} else {
				str_msg = "ok";
			}

		} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
															// FAILURE
			SoapFault soapFault = (SoapFault) envelope.bodyIn;
			str_msg = "failed";
		}
		return str_msg;
	}
	
	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
}
