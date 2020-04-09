package com.cnergee.service.caller;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.cnergee.service.AppSettingActivity;
import com.cnergee.service.SOAP.AppSettingSoap;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.util.Utils;

public class    AppSettingCaller extends Thread {
	AppSettingSoap appSettingSoap;

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;

	private AuthenticationMobile Authobj;
	
	public AppSettingCaller(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME, AuthenticationMobile Authobj) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
		this.Authobj = Authobj;
	}
	
	public void run() {

		try {
			appSettingSoap= new AppSettingSoap(WSDL_TARGET_NAMESPACE, SOAP_URL, METHOD_NAME);
			AppSettingActivity.rslt=appSettingSoap.CallAppSettingSOAP(Authobj);
			
			AppSettingActivity.AccessCodeFlag=appSettingSoap.isValidUser();
			
		}
		catch (SocketException e) {
			e.printStackTrace();
			Utils.log("1",""+e);
			AppSettingActivity.rslt = "Internet connection not available!!";
		}catch (SocketTimeoutException e) {
			e.printStackTrace();
			Utils.log("2",""+e);
			AppSettingActivity.rslt = "Internet connection not available!!";
		}catch (Exception e) {
			Utils.log("3",""+e);
			AppSettingActivity.rslt = "Invalid web-service response.<br>"+e.toString();
		}
	}
}
