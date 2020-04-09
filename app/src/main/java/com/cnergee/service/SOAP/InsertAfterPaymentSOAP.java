package com.cnergee.service.SOAP;

import com.cnergee.service.utils.MyUtils;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;

public class InsertAfterPaymentSOAP {
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
	//private String TAG = "GET_MINI_CAFDATA";
	private String jsonResponse; 
	
	
	public InsertAfterPaymentSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL, String METHOD_NAME)
	{
		MyUtils.l(":", "Soap Executed");
		
		this.METHOD_NAME = METHOD_NAME;
		this.SOAP_URL = SOAP_URL;
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
	}
	
	public String InsertAfterPaymentDetails(String clientAccessId,String trackid,String paymentid,String status,String transrefno,String transid,String transerror) throws SocketTimeoutException,SecurityException,Exception
		 
	  {
		String result = "OK";
		
		try
		{
			
			
			
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,METHOD_NAME);
			
			
			 PropertyInfo pi = new PropertyInfo();
			 pi.setName(MyUtils.CLIENTACCESSNAME);
			 pi.setValue(clientAccessId);
			 pi.setType(String.class);
			 request.addProperty(pi);
			 
			 pi = new PropertyInfo();
			 pi.setName("trackid");
			 pi.setValue(trackid);
			 pi.setType(String.class);
			 request.addProperty(pi);
			
			 pi = new PropertyInfo();
			 pi.setName("paymentid");
			 pi.setValue(paymentid);
			 pi.setType(String.class);
			 request.addProperty(pi);
			 
			 pi = new PropertyInfo();
			 pi.setName("status");
			 pi.setValue(status);
			 pi.setType(String.class);
			 request.addProperty(pi);
			 
			 pi = new PropertyInfo();
			 pi.setName("transrefno");
			 pi.setValue(transrefno);
			 pi.setType(String.class);
			 request.addProperty(pi);
			 
			 pi = new PropertyInfo();
			 pi.setName("transid");
			 pi.setValue(transid);
			 pi.setType(String.class);
			 request.addProperty(pi);
			 
			 pi = new PropertyInfo();
			 pi.setName("transerror");
			 pi.setValue(transerror);
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
