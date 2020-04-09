/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  25 Feb. 2013
 *
 * @Author 
 * Ashok Parmar
 * 
 * Version 1.0
 *
 */
package com.cnergee.service.obj;

public class ComplaintListObj {

	private Long userId;
	private String complaintId;
	private String memberLoginId;
	private boolean isRead;
	private boolean isUpdated;
	private boolean isSclosed;
	private boolean isPush;
	private boolean isLocalRead;
	
	
	private String ComplaintNo;
	public ComplaintListObj(){}
	
	public ComplaintListObj (String ComplaintNo ){
		this.ComplaintNo = ComplaintNo;
	}
	
	public ComplaintListObj (String ComplaintNo,String MemberId ){
		this.ComplaintNo = ComplaintNo;
		this.memberLoginId=MemberId;
	}
	/**
	 * @return the complaintNo
	 */
	public String getComplaintNo() {
		return ComplaintNo;
	}

	/**
	 * @param complaintNo the complaintNo to set
	 */
	public void setComplaintNo(String complaintNo) {
		ComplaintNo = complaintNo;
	}
	
	public String getMemberLoginId()
	{
		return memberLoginId;
	}
	public void SetMemberLoginId(String memberLoginId)
	{
		this.memberLoginId = memberLoginId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public boolean isUpdated() {
		return isUpdated;
	}
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}
	public boolean isSclosed() {
		return isSclosed;
	}
	public void setSclosed(boolean isSclosed) {
		this.isSclosed = isSclosed;
	}
	public boolean isPush() {
		return isPush;
	}
	public void setPush(boolean isPush) {
		this.isPush = isPush;
	}
	
	public boolean isLocalRead() {
		return isLocalRead;
	}

	public void setLocalRead(boolean isLocalRead) {
		this.isLocalRead = isLocalRead;
	}
	
	
}
