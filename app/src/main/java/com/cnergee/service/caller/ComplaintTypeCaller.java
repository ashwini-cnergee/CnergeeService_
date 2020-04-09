/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  5 Mar. 2013
 *
 * Version 1.0
 *
 */
package com.cnergee.service.caller;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.cnergee.service.ComplaintDetailsActivity;
import com.cnergee.service.SOAP.ComplaintTypeSOAP;
import com.cnergee.service.obj.AuthenticationMobile;

public class ComplaintTypeCaller extends Thread {

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;

	private AuthenticationMobile Authobj;
	private long userId;
	private int complaintCategoryId;
	private ComplaintTypeSOAP Soap;

	public ComplaintTypeCaller(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME, AuthenticationMobile Authobj) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
		this.Authobj = Authobj;
	}
	
	public void run() {
		
		try{
			Soap = new ComplaintTypeSOAP(WSDL_TARGET_NAMESPACE, SOAP_URL,METHOD_NAME);
			ComplaintDetailsActivity.rslt = Soap.setComplaintTypeList(getUserId(),getComplaintCategoryId(),Authobj);
			ComplaintDetailsActivity.xmlComplType = Soap.getXMLData();
			
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

	/**
	 * @return the complaintCategoryId
	 */
	public int getComplaintCategoryId() {
		return complaintCategoryId;
	}

	/**
	 * @param complaintCategoryId the complaintCategoryId to set
	 */
	public void setComplaintCategoryId(int complaintCategoryId) {
		this.complaintCategoryId = complaintCategoryId;
	}
	
	
}
