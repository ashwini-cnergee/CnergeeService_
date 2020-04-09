/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  05 Mar. 2013 
 *
 * Version 1.1
 *
 */
package com.cnergee.service.obj;

/*
 * 
 *  <Table>
      <StatusId>-1</StatusId>
      <Status>UserUpdate</Status>
      <ForProcess>Complaint</ForProcess>
      <IsCompleted>false</IsCompleted>
      <IsInternal>true</IsInternal>
      <IsLost>false</IsLost>
    </Table>
 */
public class StatusListObj {

	
	private String statusId;
	private String status;
	private String forProcess;
	private boolean isCompleted;
	private boolean isInternal;
	private boolean isLost;
	/**
	 * @return the statusId
	 */
	public String getStatusId() {
		return statusId;
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the forProcess
	 */
	public String getForProcess() {
		return forProcess;
	}
	/**
	 * @param forProcess the forProcess to set
	 */
	public void setForProcess(String forProcess) {
		this.forProcess = forProcess;
	}
	/**
	 * @return the isCompleted
	 */
	public boolean isCompleted() {
		return isCompleted;
	}
	/**
	 * @param isCompleted the isCompleted to set
	 */
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	/**
	 * @return the isInternal
	 */
	public boolean isInternal() {
		return isInternal;
	}
	/**
	 * @param isInternal the isInternal to set
	 */
	public void setInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}
	/**
	 * @return the isLost
	 */
	public boolean isLost() {
		return isLost;
	}
	/**
	 * @param isLost the isLost to set
	 */
	public void setLost(boolean isLost) {
		this.isLost = isLost;
	}
	
	
}
