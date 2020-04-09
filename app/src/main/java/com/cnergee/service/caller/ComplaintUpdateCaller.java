/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  28 Jan. 2013
 *
 * Version 1.0
 *
 */
package com.cnergee.service.caller;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.cnergee.service.ComplaintDetailsActivity;
import com.cnergee.service.SOAP.ComplaintUpdateSOAP;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.ComplaintUpdateObj;

public class ComplaintUpdateCaller extends Thread  {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;

	private AuthenticationMobile Authobj;
	private ComplaintUpdateSOAP soap;
	private ComplaintUpdateObj updateObj;
	
	private String resolutionId;
	public ComplaintUpdateCaller() {
	}

	public ComplaintUpdateCaller(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME, AuthenticationMobile Authobj) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
		this.Authobj = Authobj;
	}
	
	public void run() {
		
		try{
			soap = new ComplaintUpdateSOAP(WSDL_TARGET_NAMESPACE, SOAP_URL,METHOD_NAME);
			ComplaintDetailsActivity.rslt = soap.updateComplaintStatus(getUpdateObj(), Authobj,resolutionId);
			ComplaintDetailsActivity.responseMessage = soap.getResponseMessage();
			
		}catch (SocketException e) {
			e.printStackTrace();
			ComplaintDetailsActivity.rslt = "Internet connection not available!!";
		}catch (SocketTimeoutException e) {
			e.printStackTrace();
			ComplaintDetailsActivity.rslt = "Internet connection not available!!";
		}catch (Exception e) {
			ComplaintDetailsActivity.rslt = "Invalid web-service response.<br>"+e.toString();
		}
		
	}

	/**
	 * @return the updateObj
	 */
	public ComplaintUpdateObj getUpdateObj() {
		return updateObj;
	}

	/**
	 * @param updateObj the updateObj to set
	 */
	public void setUpdateObj(ComplaintUpdateObj updateObj) {
		this.updateObj = updateObj;
	}
	
	
	public String getResolutionId() {
		return resolutionId;
	}

	
	public void setResolutionId(String resolutionId) {
		this.resolutionId=resolutionId;
	}
}
