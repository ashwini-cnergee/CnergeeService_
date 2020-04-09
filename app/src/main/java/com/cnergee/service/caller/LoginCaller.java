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

import com.cnergee.service.LoginActivity;
import com.cnergee.service.SOAP.LoginSOAP;
import com.cnergee.service.obj.AuthenticationMobile;


public class LoginCaller extends Thread {

	public LoginSOAP login;

	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;

	private AuthenticationMobile Authobj;

	public String username;
	public String password;

	public LoginCaller(){
	}

	public LoginCaller(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
			String METHOD_NAME, AuthenticationMobile Authobj) {
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
		this.SOAP_URL = SOAP_URL;
		this.METHOD_NAME = METHOD_NAME;
		this.Authobj = Authobj;
	}

	public void run() {

		try {
			login = new LoginSOAP(WSDL_TARGET_NAMESPACE, SOAP_URL, METHOD_NAME);
			LoginActivity.rslt = login.CallLoginSOAP(username, password,Authobj);
			LoginActivity.isVaildUser = login.isValidUser();
			if(login.isValidUser()){
				LoginActivity.userId = login.getUserId();
			}
		}catch (SocketException e) {
			e.printStackTrace();
			LoginActivity.rslt = "Internet connection not available!!";
		}catch (SocketTimeoutException e) {
			e.printStackTrace();
			LoginActivity.rslt = "Internet connection not available!!";
		}catch (Exception e) {
			LoginActivity.rslt = "Invalid web-service response.<br>"+e.toString();
		}
	}

}
