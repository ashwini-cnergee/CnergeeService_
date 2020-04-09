package com.cnergee.service.obj;

public class Package
{
   String PlanName,PlanAmount,AreaCode,PackageValidity,ServiceTax,AreaCodeFilter,PackageId;

   public Package(String planName, String planAmount, String areaCode,String packageValidity, String serviceTax, String areaCodeFilter,String packageId)
    {
	super();
	PlanName = planName;
	PlanAmount = planAmount;
	AreaCode = areaCode;
	PackageValidity = packageValidity;
	ServiceTax = serviceTax;
	AreaCodeFilter = areaCodeFilter;
	PackageId = packageId;
   }

/**
 * @return the planName
 */
public String getPlanName() {
	return PlanName;
}

/**
 * @param planName the planName to set
 */
public void setPlanName(String planName) {
	PlanName = planName;
}

/**
 * @return the planAmount
 */
public String getPlanAmount() {
	return PlanAmount;
}

/**
 * @param planAmount the planAmount to set
 */
public void setPlanAmount(String planAmount) {
	PlanAmount = planAmount;
}

/**
 * @return the areaCode
 */
public String getAreaCode() {
	return AreaCode;
}

/**
 * @param areaCode the areaCode to set
 */
public void setAreaCode(String areaCode) {
	AreaCode = areaCode;
}

/**
 * @return the packageValidity
 */
public String getPackageValidity() {
	return PackageValidity;
}

/**
 * @param packageValidity the packageValidity to set
 */
public void setPackageValidity(String packageValidity) {
	PackageValidity = packageValidity;
}

/**
 * @return the serviceTax
 */
public String getServiceTax() {
	return ServiceTax;
}

/**
 * @param serviceTax the serviceTax to set
 */
public void setServiceTax(String serviceTax) {
	ServiceTax = serviceTax;
}

/**
 * @return the areaCodeFilter
 */
public String getAreaCodeFilter() {
	return AreaCodeFilter;
}

/**
 * @param areaCodeFilter the areaCodeFilter to set
 */
public void setAreaCodeFilter(String areaCodeFilter) {
	AreaCodeFilter = areaCodeFilter;
}

/**
 * @return the packageId
 */
public String getPackageId() {
	return PackageId;
}

/**
 * @param packageId the packageId to set
 */
public void setPackageId(String packageId) {
	PackageId = packageId;
}
   
   
}
