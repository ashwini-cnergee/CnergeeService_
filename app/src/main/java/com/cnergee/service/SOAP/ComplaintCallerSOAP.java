/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  25 Feb 2013
 *
 * Version 1.0
 *
 */
package com.cnergee.service.SOAP;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cnergee.service.obj.Authentication;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.ComplaintListObj;
import com.cnergee.service.util.Utils;
import com.traction.ashok.marshals.MarshalLong;

public class ComplaintCallerSOAP {
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private static final String TAG = "CnergeeService"; 
	private ArrayList<ComplaintListObj> complaintList = new ArrayList<ComplaintListObj>();
	
	public ComplaintCallerSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}
	
	public String setComplaintList(long userId,AuthenticationMobile Authobj)throws SocketException,SocketTimeoutException,Exception{
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
		ArrayList<ComplaintListObj> complaintList = new ArrayList<ComplaintListObj>();
		androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
		Utils.log("ComplaintCallerSOAP","request: "+androidHttpTransport.requestDump);
		Utils.log("ComplaintCallerSOAP","response: "+androidHttpTransport.responseDump);
			if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
				
				SoapObject response = (SoapObject) envelope.getResponse();
				if (response != null) {
					
					/*Log.i(TAG+" >>>> ",response.toString());*/
					response = (SoapObject) response.getProperty("NewDataSet");
					if (response.getPropertyCount() > 0) {
						for (int i = 0; i < response.getPropertyCount(); i++) {
							SoapObject tableObj = (SoapObject) response.getProperty(i);
							ComplaintListObj obj = new ComplaintListObj();
							obj.setComplaintId(tableObj.getPropertyAsString("Comptid").toString());
							obj.setComplaintNo(tableObj.getPropertyAsString("ComplaintNo").toString());
							obj.setPush(Boolean.parseBoolean(tableObj.getPropertyAsString("IsPush").toString()));
							obj.setRead(Boolean.parseBoolean(tableObj.getPropertyAsString("IsRead").toString()));
							obj.setSclosed(Boolean.parseBoolean(tableObj.getPropertyAsString("IsClosed").toString()));
							obj.setUpdated(Boolean.parseBoolean(tableObj.getPropertyAsString("IsUpdated").toString()));
							obj.SetMemberLoginId(tableObj.getPropertyAsString("MemberLoginID").toString());
							obj.setUserId(userId);
							
							Utils.log("ID", "is:"+tableObj.getPropertyAsString("MemberLoginID").toString());
							complaintList.add(obj);						
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
				//complaintList.add(new ComplaintListObj());
			}
			setComplaintList(complaintList);
		return str_msg;
	}

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
	public ArrayList<ComplaintListObj> getComplaintList() {
		return complaintList;
	}

	/**
	 * @param complaintList the complaintList to set
	 */
	public void setComplaintList(ArrayList<ComplaintListObj> complaintList) {
		this.complaintList = complaintList;
	}
	
	
	
}
