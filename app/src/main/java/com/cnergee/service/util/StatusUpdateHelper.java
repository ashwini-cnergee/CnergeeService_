
package com.cnergee.service.util;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.cnergee.service.SOAP.ComplaintStatusUpdateSOAP;
import com.cnergee.service.obj.AuthenticationMobile;
import com.cnergee.service.obj.ComplaintUpdateStatusObj;

public class StatusUpdateHelper {

	private ComplaintStatusUpdateSOAP soap;
	private Long userId;
	private String complaintNo;
	private AuthenticationMobile Authobj;
	private String actionType;
	private boolean status;
	
	public StatusUpdateHelper (ComplaintStatusUpdateSOAP soap,Long userId,String complaintNo,AuthenticationMobile Authobj,String actionType,boolean status){
		this.soap = soap;
		this.userId = userId;
		this.complaintNo = complaintNo;
		this.Authobj = Authobj;
		this.actionType = actionType;
		this.status = status;
	}
	
	public void updateStatus() throws SocketTimeoutException, SocketException,Exception{
	    	
		
	     	ComplaintUpdateStatusObj obj = new ComplaintUpdateStatusObj();

	    	/*obj.setComment("");
			Calendar c = Calendar.getInstance();
			SimpleDateFormat viewDateFormatter = new SimpleDateFormat("ddMMyyyy");
			String currentDate = viewDateFormatter.format(c.getTime());
			obj.setComplaintDate(currentDate+"000000");*/
			obj.setComplaintNo(complaintNo);
			obj.setActionType(actionType);
			obj.setUserId(userId);
			obj.setStatus(status);
			
	    	try {
				String res = soap.updateComplaintStatus(obj, Authobj);
				if(!res.equalsIgnoreCase("ok") ){
					/*Log.i("*********** STATUS NOT CHANGE... ",""+complaintNo);*/
				}
			} catch (SocketTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
	    	
	    	
	    }
	  
}
