package com.cnergee.service.obj;

public class ComplaintObj {

	
	private String ComplaintNo;
	private String CustomerName;
	private String UserId;
	private String CustomerAddress;
	private String Phone;
	private String Status;
	private String Comments;
	private String ComplaintDate;
	private int complaintCategory;
	private int complaintId;
	private int StatusId;
	private String AssignedDate;
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
	
	
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return CustomerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	/**
	 * @return the customerAddress
	 */
	public String getCustomerAddress() {
		return CustomerAddress;
	}
	/**
	 * @param customerAddress the customerAddress to set
	 */
	public void setCustomerAddress(String customerAddress) {
		CustomerAddress = customerAddress;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return Phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		Phone = phone;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		Status = status;
	}
	
	
	public int getStatusId() {
		return StatusId;
	}
	public void setStatusId(int statusId) {
		StatusId = statusId;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return Comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		Comments = comments;
	}
	/**
	 * @return the complaintDate
	 */
	public String getComplaintDate() {
		return ComplaintDate;
	}
	/**
	 * @param complaintDate the complaintDate to set
	 */
	public void setComplaintDate(String complaintDate) {
		ComplaintDate = complaintDate;
	}
	
	public String getAssignedDate()
	{
		return AssignedDate;
	}
	public void setAssignedDate(String assignedDate)
	{
		AssignedDate = assignedDate;
	}
	/**
	 * @return the complaintCategory
	 */
	public int getComplaintCategory() {
		return complaintCategory;
	}
	/**
	 * @param complaintCategory the complaintCategory to set
	 */
	public void setComplaintCategory(int complaintCategory) {
		this.complaintCategory = complaintCategory;
	}
	/**
	 * @return the complaintId
	 */
	public int getComplaintId() {
		return complaintId;
	}
	/**
	 * @param complaintId the complaintId to set
	 */
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}
	
	
	
}
