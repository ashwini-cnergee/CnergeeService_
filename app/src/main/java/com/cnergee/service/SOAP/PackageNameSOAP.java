package com.cnergee.service.SOAP;

//import com.dsa.cnergee.obj.Common_Item;
//import com.utils.MyUtils;

import com.cnergee.service.obj.Common_Item;
import com.cnergee.service.utils.MyUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class PackageNameSOAP 
{
	private String WSDL_TARGET_NAMESPACE;
	private String SOAP_URL;
	private String METHOD_NAME;
    private String jsonResponse; 
    ArrayList<Common_Item> alPackage= new ArrayList<Common_Item>();
    
    public PackageNameSOAP(String WSDL_TARGET_NAMESPACE,String SOAP_URL,String METHOD_NAME)
	{
    	MyUtils.l(":", "Soap Executed");
		
		this.METHOD_NAME = METHOD_NAME;
		this.SOAP_URL = SOAP_URL;
		this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
	}
    
    //public String GetPackageDetails(String MemberId,String ConnectionTypeId,String AreaCode,String CliectAccessId)
	public String GetPackageDetails(String UserLoginName, String Ispid, String areaid, String ConnectionTypeId, boolean IsPostPaid, int sid, String CliectAccessId)
	{
		String result="OK";
		
		try
		{
			String user_LoginName=UserLoginName;
			String isp_id=Ispid;
			String area_code=areaid;
			String Connection_TypeId =ConnectionTypeId;
			boolean is_postPaid =IsPostPaid;
			int s_id =sid;
			String clientcode=CliectAccessId;
			
			SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE,METHOD_NAME);

			PropertyInfo pinfo1 = new PropertyInfo();
			pinfo1.setName("UserLoginName");
			pinfo1.setValue(user_LoginName);
			pinfo1.setType(String.class);
			request.addProperty(pinfo1);

			PropertyInfo pinfo2 = new PropertyInfo();
			pinfo2.setName("Ispid");
			pinfo2.setValue(isp_id);
			pinfo2.setType(Integer.class);
			request.addProperty(pinfo2);

			PropertyInfo pinfo3 = new PropertyInfo();
			pinfo3.setName("areaid");
			pinfo3.setValue(area_code);
			pinfo3.setType(Integer.class);
			request.addProperty(pinfo3);

			PropertyInfo pinfo4 = new PropertyInfo();
			pinfo4.setName("ConnectionTypeId");
			pinfo4.setValue(Connection_TypeId);
			pinfo4.setType(Integer.class);
			request.addProperty(pinfo4);

			PropertyInfo pinfo5 = new PropertyInfo();
			pinfo5.setName("IsPostPaid");
			pinfo5.setValue(is_postPaid);
			pinfo5.setType(Boolean.class);
			request.addProperty(pinfo5);

			PropertyInfo pinfo6 = new PropertyInfo();
			pinfo6.setName("sid");
			pinfo6.setValue(s_id);
			pinfo6.setType(Integer.class);
			request.addProperty(pinfo6);

			PropertyInfo pinfo7 = new PropertyInfo();
			pinfo7.setName("CliectAccessId");
			pinfo7.setValue(clientcode);
			pinfo7.setType(String.class);
			request.addProperty(pinfo7);

			
			/*PropertyInfo pinfo1 = new PropertyInfo();
			pinfo1.setName("MemberId");
			pinfo1.setValue(id);
			pinfo1.setType(String.class);
	        request.addProperty(pinfo1);
	        
	        PropertyInfo pinfo2 = new PropertyInfo();
	        pinfo2.setName("ConnectionTypeId");
	        pinfo2.setValue(connectionId);
	        pinfo2.setType(String.class);
	        request.addProperty(pinfo2);
	        
	        PropertyInfo pinfo3 = new PropertyInfo();
			pinfo3.setName("AreaCode");
			pinfo3.setValue(areacode);
			pinfo3.setType(String.class);
	        request.addProperty(pinfo3);
	        
	        PropertyInfo pinfo4 = new PropertyInfo();
			pinfo4.setName(MyUtils.CLIENTACCESSNAME);
			pinfo4.setValue(CliectAccessId);
			pinfo4.setType(String.class);
	        request.addProperty(pinfo4);*/
	        
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.setOutputSoapObject(request);
	        envelope.dotNet = true;
	                                                                                      
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL);
	        androidHttpTransport.debug=true;
	        
 	       try
 	          {
 	        	androidHttpTransport.call(WSDL_TARGET_NAMESPACE+METHOD_NAME, envelope);
	            
 	        	MyUtils.l("Package Request",":"+androidHttpTransport.requestDump);
	            
 	        	MyUtils.l("Package",":"+androidHttpTransport.responseDump);
	            
	            //SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
	            //s = response.toString();
	            
	             if (envelope.bodyIn instanceof SoapObject)
				 { 
					SoapObject response = (SoapObject) envelope.getResponse();
					 if (response != null) 
					   {
						 response = (SoapObject) response.getProperty("NewDataSet");
						 if (response.getPropertyCount() > 0)
						  {
							for (int i = 0; i < response.getPropertyCount(); i++) 
							{
								 SoapObject tableObj = (SoapObject) response.getProperty(i);
								 String	 PackageId=tableObj.getPropertyAsString("PackageId").toString();
								 String	 PlanName=tableObj.getPropertyAsString("PackageFullName").toString();
								 String	 PackageValidity=tableObj.getPropertyAsString("PackageValidity").toString();
								 String	 PlanAmount=tableObj.getPropertyAsString("PackageRate").toString();
								 //String	 Local_AreaCode=tableObj.getPropertyAsString("AreaCode").toString();

								 //String	 ServiceTax=tableObj.getPropertyAsString("ServiceTax").toString();
								 //String	 AreaCodeFilter=tableObj.getPropertyAsString("AreaCodeFilter").toString();


                                        Common_Item common_Item = new Common_Item(PackageId, PlanName, PlanAmount);
                                        common_Item.setItem_validity(PackageValidity);
                                        alPackage.add(common_Item);


						   }
						}
					}
				}
 	        }
 	        catch(Exception e)
 	        {
 	        	
 	        	 e.printStackTrace();
 	        	return e.toString();
 	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return e.toString();
		}

      return result;
	}
    public ArrayList<Common_Item> getPackgeDetails(){
		return alPackage;
	}
}
