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

import com.cnergee.service.ComplaintListActivity;
import com.cnergee.service.SOAP.ComplaintCallerSOAP;
import com.cnergee.service.obj.AuthenticationMobile;

public class ComplaintCaller extends Thread {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;

	private AuthenticationMobile Authobj;
	private long userId;
	private ComplaintCallerSOAP complaintSoap;
	
	public ComplaintCaller() {
	}

	public ComplaintCaller(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME, AuthenticationMobile Authobj) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
		this.Authobj = Authobj;
	}

	public void run() {
		
		try{
			complaintSoap = new ComplaintCallerSOAP(WSDL_TARGET_NAMESPACE, SOAP_URL,METHOD_NAME);
			ComplaintListActivity.rslt = complaintSoap.setComplaintList(getUserId(),Authobj);
			ComplaintListActivity.complaintList = complaintSoap.getComplaintList();
			
		}catch (SocketException e) {
			e.printStackTrace();
			ComplaintListActivity.rslt = "Internet connection not available!!";
		}catch (SocketTimeoutException e) {
			e.printStackTrace();
			ComplaintListActivity.rslt = "Internet connection not available!!";
		}catch (Exception e) {
			ComplaintListActivity.rslt = "Invalid web-service response.<br>"+e.toString();
		}
		
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
