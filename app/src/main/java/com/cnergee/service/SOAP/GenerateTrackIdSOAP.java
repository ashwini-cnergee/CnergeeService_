package com.cnergee.service.SOAP;

import com.cnergee.service.utils.MyUtils;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;

public class GenerateTrackIdSOAP {
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	//private String TAG = "GET_MINI_CAFDATA";
	private String jsonResponse; 
	
	
	public GenerateTrackIdSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL, String METHOD_NAME)
	{
		MyUtils.l(":", "Soap Executed");
		
		this.METHOD_NAME = METHOD_NAME;
		this.SOAP_URL = SOAP_URL;
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
	}
	
	public String GetTransactionId(String ClientAccessId,String UserId) throws SocketTimeoutException,SecurityException,Exception
		 
	  {
		String result = "OK";
		
		try
		{
			
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,METHOD_NAME);
			
			
			 PropertyInfo pi = new PropertyInfo();
			 pi.setName(MyUtils.CLIENTACCESSNAME);
			 pi.setValue(ClientAccessId);
			 pi.setType(String.class);
			 request.addProperty(pi);
			 
			 pi = new PropertyInfo();
			 pi.setName("UserId");
			 pi.setValue(UserId);
			 pi.setType(String.class);
			 request.addProperty(pi);
			
			
			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.dotNet = true;

		     envelope.setOutputSoapObject(request);
			 envelope.encodingStyle = SoapSerializationEnvelope.ENC;
			 envelope.implicitTypes = true;
				

			 HttpTransportSE transportSE = new HttpTransportSE(SOAP_URL);
			 transportSE.debug = true;
		   
		  
			 transportSE.call(WSDL_TARGET_NAMESPACE+METHOD_NAME, envelope);
				
			 MyUtils.l("Web Service","Request:"+transportSE.requestDump);
			 MyUtils.l("Web Service","Response:"+transportSE.responseDump);
	          
			 jsonResponse = envelope.getResponse().toString();
				
			// MyUtils.l("Web Service","Response:"+jsonResponse);
		   
		  
			return result;
		}
		catch(Exception e)
		{
			MyUtils.l("Web Service","Error:"+e);
			
			return "error";
		}
	}
	
	public String getJsonResponse()
	  {
		return jsonResponse;
		
	  } 

	}
