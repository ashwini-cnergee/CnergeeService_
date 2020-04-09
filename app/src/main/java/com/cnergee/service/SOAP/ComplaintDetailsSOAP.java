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
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cnergee.service.obj.Authentication;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.ComplaintObj;
import com.cnergee.service.util.Utils;
import com.traction.ashok.marshals.MarshalLong;

public class ComplaintDetailsSOAP {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	private static final String TAG = "ComplaintDetailsSOAP"; 
	private ComplaintObj complaintObj;
	
	
	public ComplaintDetailsSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
	}
	
	public String setComplaintDetails(long userId, String ComplaintNo, AuthenticationMobile Authobj)throws SocketException,SocketTimeoutException,Exception{
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);
		Utils.log(TAG+"","***************************************Start Call****************************************:");
		/*Log.i(" #	#####################  ", " START ");
		Log.i(TAG+" userId ", ""+userId);
		Log.i(TAG+" ComplaintNo ", ""+ComplaintNo);*/
		/*Log.i(TAG+" IMEI No ", Authobj.getIMEINo());
		Log.i(TAG+" Mobile ", Authobj.getMobileNumber());
		Log.i(TAG+" Mobile User ", Authobj.getMobLoginId());
		Log.i(TAG+" Mobile Password ", Authobj.getMobUserPass());*/
		/*Log.i(TAG+" WSDL_TARGET_NAMESPACE ", WSDL_TARGET_NAMESPACE);
		Log.i(TAG+" METHOD_NAME ", METHOD_NAME);
		Log.i(TAG+" SOAP_ACTION ", SOAP_URL + METHOD_NAME);
		Log.i("#####################", "");*/
		
		PropertyInfo pi = new PropertyInfo();
		pi.setName("UserId");
		pi.setValue(userId);
		pi.setType(Long.class);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName("ComplaintNo");
		pi.setValue(ComplaintNo);
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
		envelope.addMapping(WSDL_TARGET_NAMESPACE, "Authobj", new Authentication().getClass());

		MarshalLong mlong = new MarshalLong();
		mlong.register(envelope);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
		androidHttpTransport.debug = true;

		String str_msg = "ok";
		Utils.log(TAG+"","Before Call:");
		androidHttpTransport.call(WSDL_TARGET_NAMESPACE + METHOD_NAME,envelope);
		Utils.log(TAG+"","Before Call:");
		Utils.log("ComplaintDetailsSOAP Request","is "+androidHttpTransport.requestDump);
		Utils.log("ComplaintDetailsSOAP Response","is "+androidHttpTransport.responseDump);
		if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
			SoapObject response = (SoapObject) envelope.getResponse();
			if (response != null) {
				/*Log.i(" RESPONSE ", response.toString());*/
				response = (SoapObject) response.getProperty("NewDataSet");
				if (response.getPropertyCount() > 0) {
					for (int i = 0; i < response.getPropertyCount(); i++) {
						SoapObject tableObj = (SoapObject) response
								.getProperty(i);
						setComplaintData(tableObj);
					}
					str_msg = "ok";
				} else {
					str_msg = "Complaint Details Are Not Found";
				}
			}
		} else if (envelope.bodyIn instanceof SoapFault) { // SoapFault =
															// FAILURE
			SoapFault soapFault = (SoapFault) envelope.bodyIn;
			return soapFault.getMessage().toString();
		}
		Utils.log(TAG+"","***************************************END Call****************************************:");
		return str_msg;
	}
	
	/*
	 *
 
 I/ RESPONSE ( 6559): anyType{NewDataSet=anyType{Table=anyType{MemberComplaintNo=
AR10503131522002C    ; Comments=Test; ComplaintDate=2013-03-05T15:23:31.27+05:30
; MobileNo=9930457672,; CustomerName=Mr. Punam  Pawar; BillAddress=RH NO 3 VENS
APT;Sec - 6; Airoli 1;Navi Mumbai.; ComplaintCategoryId=1; ComplaintId=33; }; };
<Table>
      <MemberComplaintNo>BR20204130952057C</MemberComplaintNo>
      <Comments>not abl to solve</Comments>
      <ComplaintDate>2013-04-02T09:53:14.657+05:30</ComplaintDate>
      <MobileNo>7666632160,</MobileNo>
      <CustomerName>Mr. Shahid Alnul Hoda</CustomerName>
      <BillAddress>Rachna Apt 2nd floor flat no 202.400614</BillAddress>
      <StatusId>12</StatusId>
      <Status>Pending</Status>
    </Table>


	 */
	protected void setComplaintData(SoapObject response){
		ComplaintObj obj = new ComplaintObj();
		obj.setComplaintNo(response.getPropertyAsString("MemberComplaintNo").toString());
		obj.setComments(response.getPropertyAsString("Comments").toString());
		obj.setComplaintDate(response.getPropertyAsString("CreatedDate").toString());
		obj.setAssignedDate(response.getPropertyAsString("AssignedDate").toString());
		obj.setPhone(response.getPropertyAsString("MobileNo").toString());
		obj.setCustomerAddress(response.getPropertyAsString("BillAddress").toString());
		obj.setCustomerName(response.getPropertyAsString("CustomerName").toString());
		obj.setUserId(response.getPropertyAsString("MemberLoginID").toString());
		obj.setComplaintCategory(Integer.parseInt(response.getPropertyAsString("ComplaintCategoryId").toString()));
		obj.setComplaintId(Integer.parseInt(response.getPropertyAsString("ComplaintId").toString()));
		obj.setStatusId(Integer.parseInt(response.getPropertyAsString("StatusId").toString()));
		obj.setStatus(response.getPropertyAsString("Status").toString());
		
		setComplaintObj(obj);
	}

	/**
	 * @return the complaintObj
	 */
	public ComplaintObj getComplaintObj() {
		return complaintObj;
	}

	/**
	 * @param complaintObj the complaintObj to set
	 */
	public void setComplaintObj(ComplaintObj complaintObj) {
		this.complaintObj = complaintObj;
	}
	
}
