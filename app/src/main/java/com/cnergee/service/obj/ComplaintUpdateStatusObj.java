/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation: 4 March. 2013
 *
 * Version 1.0
 *
 */

/*
 *  Used for updating the status e.g notification
 *  
 */
package com.cnergee.service.obj;

public class ComplaintUpdateStatusObj {

	private Long userId;
	private String complaintNo;
	private boolean status;
	private String actionType;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getComplaintNo() {
		return complaintNo;
	}
	public void setComplaintNo(String complaintNo) {
		this.complaintNo = complaintNo;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	
}
