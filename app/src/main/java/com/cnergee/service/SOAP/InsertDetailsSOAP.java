package com.cnergee.service.SOAP;

import android.util.Log;

import com.cnergee.service.obj.DSACAF;
import com.cnergee.service.obj.MarshalCharacter;
import com.cnergee.service.obj.MarshalDouble;
import com.cnergee.service.util.Constants;
import com.cnergee.service.util.Utils;
import com.cnergee.service.utils.MyUtils;
//import com.dsa.cnergee.obj.DSACAF;
//import com.dsa.cnergee.obj.MarshalCharacter;
//import com.dsa.cnergee.obj.MarshalDouble;
//import com.utils.MyUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class InsertDetailsSOAP 
{
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
    private String jsonResponse;
    ArrayList<DSACAF> dsacafArrayList = new ArrayList<>();
    
       public InsertDetailsSOAP(String WSDL_TARGET_NAMESPACE, String SOAP_URL, String METHOD_NAME)
          {
    	   MyUtils.l(":", "Login Soap Executed");
		
		    this.METHOD_NAME = METHOD_NAME;
		    this.SOAP_URL = SOAP_URL;
		    this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
          }
       
       public String InsertMiniCAFDetails(DSACAF dsacaf, String status, String Param, String TrackID)throws SocketTimeoutException,SecurityException,Exception
       {
    	 String result = "OK";
    	 try
		  {

			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,METHOD_NAME);
			
			PropertyInfo pi = new PropertyInfo();
			pi = new PropertyInfo();
			pi.setName(DSACAF.objname);
			pi.setValue(dsacaf);
			pi.setType(dsacaf.getClass());
			request.addProperty(pi);

			 pi = new PropertyInfo();
			 pi.setName("status");
			 pi.setValue(status);
			 pi.setType(String.class);
			 request.addProperty(pi);

			 pi = new PropertyInfo();
			 pi.setName("Param");
			 pi.setValue(Param);
			 pi.setType(String.class);
			 request.addProperty(pi);

			 pi = new PropertyInfo();
			 pi.setName("TrackID");
			 pi.setValue(TrackID);
			 pi.setType(String.class);
			 request.addProperty(pi);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;

		    envelope.setOutputSoapObject(request);
			envelope.encodingStyle = SoapSerializationEnvelope.ENC;
			envelope.implicitTypes = true;
			
			envelope.addMapping(WSDL_TARGET_NAMESPACE, "objDsaCaf",new DSACAF().getClass());
			
			MarshalDouble mdouble = new MarshalDouble();
			mdouble.register(envelope);
			
			MarshalCharacter mchar = new MarshalCharacter();
			mchar.register(envelope);

		    HttpTransportSE transportSE = new HttpTransportSE(SOAP_URL);
		    transportSE.debug = true;
		   
		  
			transportSE.call(WSDL_TARGET_NAMESPACE+METHOD_NAME, envelope);

			MyUtils.l("envolop","value is "+envelope);
			MyUtils.l("Web Service","Request:"+transportSE.requestDump);
			MyUtils.l("Web Service","Response:"+transportSE.responseDump);

			jsonResponse = envelope.getResponse().toString();
				
			//MyUtils.l("Web Service","Response:"+jsonResponse);
		    return result;
		}
		catch(Exception e)
		{
			//MyUtils.l("Web Service","Error:"+e);
			return e.toString();
		}
	  }
	
   public String getJsonResponse()
	  {
		return jsonResponse;
		
	  }


    public String CallInsertDetailsSOAP(DSACAF objDsaCaf) {
        try{
            SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

            Log.i("#########  ", " START ");


		/*Log.i(" IMEI No ", Authobj.getIMEINo());
		Log.i(" SIM SR.No ", Authobj.getMobileNumber());
		Log.i(" Mobile User ", Authobj.getMobLoginId());
		Log.i(" Mobile Password ", Authobj.getMobUserPass());
		Log.i(" Cust ", Authobj.getCliectAccessId());
		Log.i(" WSDL_TARGET_NAMESPACE ", WSDL_TARGET_NAMESPACE);
		Log.i(" METHOD_NAME ", METHOD_NAME);
		Log.i(" SOAP_ACTION ", SOAP_URL + METHOD_NAME);*/

            PropertyInfo pi = new PropertyInfo();


            pi = new PropertyInfo();
            pi.setName(DSACAF.objname);
            pi.setValue(objDsaCaf);
            pi.setType(objDsaCaf.getClass());
            request.addProperty(pi);

           /* pi = new PropertyInfo();
            pi.setName("status");
            pi.setValue("0");
            pi.setType(String.class);
            request.addProperty(pi);*/

            pi = new PropertyInfo();
            pi.setName("Param");
            pi.setValue("C");
            pi.setType(String.class);
            request.addProperty(pi);

           /* pi = new PropertyInfo();
            pi.setName("TrackID");
            pi.setValue("0");
            pi.setType(String.class);
            request.addProperty(pi);*/



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            // envelope.bodyOut = request;

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.encodingStyle = SoapSerializationEnvelope.ENC;
            // envelope.setOutputSoapObject(request);
            envelope.implicitTypes = true;

            MarshalDouble md = new MarshalDouble();
            md.register(envelope);
            envelope.addMapping(WSDL_TARGET_NAMESPACE, "objDsaCaf",
                    new DSACAF().getClass());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
            androidHttpTransport.debug = true;


            androidHttpTransport.call(WSDL_TARGET_NAMESPACE+ METHOD_NAME,envelope);
            Utils.log("Data sent",""+androidHttpTransport.requestDump);

            Utils.log("AppSettingSoap ","request:"+androidHttpTransport.requestDump);
            Utils.log("AppSettingSoap ","response :"+androidHttpTransport.responseDump);
            Object response = envelope.getResponse();
            Utils.log("Response from server",""+response);
            //setValidUser((Boolean)response);


            return "OK";
        }
        catch(Exception e){
            Utils.log("Error in AppSettingSoap",""+e);
            return e.toString();
        }
    }

    private Object getSOAPDateString(Date itemValue) {
        String lFormatTemplate = "yyyy-MM-dd'T'hh:mm:ss'Z'";
        DateFormat lDateFormat = new SimpleDateFormat(lFormatTemplate);
        String lDate = lDateFormat.format(itemValue);

        return lDate;
    }

}
    	  

