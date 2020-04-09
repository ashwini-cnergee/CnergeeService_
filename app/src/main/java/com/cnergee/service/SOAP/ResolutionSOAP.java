package com.cnergee.service.SOAP;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cnergee.service.ComplaintDetailsActivity;
import com.cnergee.service.obj.Authentication;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;
import com.traction.ashok.marshals.MarshalLong;

public class ResolutionSOAP {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private static final String TAG = "CnergeeService"; 
	private ArrayList<String> resolutionType = new ArrayList<String>();
	private ArrayList<String> resolutionId = new ArrayList<String>();
	
	public ResolutionSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}
	
	public String getResolutionType(Long UserId,AuthenticationMobile Authobj)throws SocketException,SocketTimeoutException,Exception{
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
		String str_msg = "ok";
		try{
		PropertyInfo pi = new PropertyInfo();
		
		pi.setName("UserId");
		pi.setValue(UserId);
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

		
		
		androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
		Utils.log("Resolution Soap","request: "+androidHttpTransport.requestDump);
		Utils.log("Resolution Soap","response: "+androidHttpTransport.responseDump);
		ComplaintDetailsActivity.str_response_resolution=androidHttpTransport.responseDump;
		
			/*if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
				
				SoapObject response = (SoapObject) envelope.getResponse();
				if (response != null) {
					
					Log.i(TAG+" >>>> ",response.toString());
					response = (SoapObject) response.getProperty("NewDataSet");
					if (response.getPropertyCount() > 0) {
						for (int i = 0; i < response.getPropertyCount(); i++) {
							SoapObject tableObj = (SoapObject) response.getProperty(i);
							
							resolutionType.add(tableObj.getPropertyAsString("ResolutionName").toString());
							resolutionId.add(tableObj.getPropertyAsString("ResolutionId").toString());
							
						}
						
					} else {
						//complaintList.add(new ComplaintListObj());	
					}
				} else {
					//complaintList.add(new ComplaintListObj());
				}

			} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
													// FAILURE
				SoapFault soapFault = (SoapFault) envelope.bodyIn;
				return str_msg="error";	
				//complaintList.add(new ComplaintListObj());
			}*/
			
		return str_msg;
	

	/*
	 *I/CnergeeService >>>> (  619): anyType{NewDataSet=anyType{Table=anyType{Comptid=7; ComplaintNo=BR12502131605002C    ; UserId=25; IsRead=true; IsUpdated=false; I
sClosed=false; IsPush=true; }; Table=anyType{Comptid=4; ComplaintNo=BR12302131919003C    ; UserId=25; IsRead=false; IsUpdated=false; IsClosed=false; IsPush=fals
e; }; Table=anyType{Comptid=5; ComplaintNo=BR12402131301001C    ; UserId=25; IsRead=false; IsUpdated=false; IsClosed=false; IsPush=false; }; Table=anyType{Compt
id=8; ComplaintNo=BR12502131616002C    ; UserId=25; IsRead=false; IsUpdated=false; IsClosed=false; IsPush=false; }; Table=anyType{Comptid=9; ComplaintNo=BR12502
131905003C    ; UserId=25; IsRead=false; IsUpdated=false; IsClosed=false; IsPush=false; }; }; }
	 */
	/**
	 * @return the complaintList
	 */

	
	}catch(Exception e){
		return str_msg="error";
		
	}
}
	
	public ArrayList<String> resolutionType(){
		return resolutionType;
	}
	
	public ArrayList<String> resolutionId(){
		return resolutionId;
	}

}
