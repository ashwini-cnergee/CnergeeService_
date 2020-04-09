package com.cnergee.service.obj;

import android.content.Intent;
import android.os.Build;

import com.itextpdf.xmp.XMPDateTime;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Hashtable;

import androidx.annotation.RequiresApi;


public class DSACAF extends PropertyInfo implements KvmSerializable, Serializable  {

	private static final long serialVersionUID = 1L;
	String MobLoginId ,LoginPassword ,CliectAccessId ,MobileNumber ,CAFNo ,CAFDate ,Gender ,DOB ,
            FlatNo ,Address , FirstName ,MiddleName ,LastName ,EmailId ,PanNo ,PayMode ,
            PaymodeDate ,PaymodeNo ,RoleName ,SourceName ,ProspectId ,ReceiptNo ,
            BankName ,PackageName ,RouterOfferType ,
            SecurityDepositType ,RouterModel ,SecurityDepositDescription ,RouterSecurityPayMode ,
            RouterSecurityPayModeType ,RouterSecurityChequeNumber ,RouterSecurityChequeDate ,RouterSecurityBankName ,
            GponOfferType ,GponModel,MemberLoginID ,IPAddress ,MACAddress ,
            VLANID ,MobileNumberSecondary ,EmailIdSecondary ,InstCityId ,InstAddress ,InstPinCode ,BillingAddress ,
            BillingCityId ,BillingPinCode ,PermanentAddress ,PermanentCityId ,PermanentPinCode ,SMEEntityID ,SMETypeID ,
            CorporationName ,IdProof ,IdProofNumber ,GSTNumber ,Latitude ,Longitude ,PackageDiscountGivenBy ,
            InstDiscountGivenBy ,Comment ,PaymentDate,PackageStartDate,strPaymentDate,strPackageStartDate;

	//String ActivationDate;
	int Nationality,CreatedBy,RoleId, CityId ,LocationId ,AreaId ,SubAreaId ,BuildingId ,TowerId ,FloorNo,Pincode ,ISPId ,ConnectionTypeId,PckgId,AssignTo,
    StatusId,ActualCreatedBy ,RouterId, GponId, SourceId,IPPoolId,IPNodeType,CustTypeId,CustSubTypeId,PPParentMemberId ,BillingCycleTypeId
            ,NoOfMac,NoOfSimUse,sid,CablingType,NoofMACAllowed,RMUserID ,KAMUserID,InstDiscount,PackageDiscount;

	double PckgCharge,InstCharge,Amount,RouterSecurityTotalAmount,GponRefundAmt,GponCharges,RouterCharges,
    RouterRefundAmt,SecurityDepositAmt,SecurityDepositRefundAmt,OpeningBalance;

    Boolean IsDSAProspect,BindToMac,IsPostPaid,IsIPPOE,PrimisesType;

    public static String objname = "objDsaCaf";

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMobLoginId() {
        return MobLoginId;
    }

    public void setMobLoginId(String mobLoginId) {
        MobLoginId = mobLoginId;
    }

    public String getLoginPassword() {
        return LoginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        LoginPassword = loginPassword;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public String getCliectAccessId() {
        return CliectAccessId;
    }

    public void setCliectAccessId(String cliectAccessId) {
        CliectAccessId = cliectAccessId;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getCAFNo() {
        return CAFNo;
    }

    public void setCAFNo(String CAFNo) {
        this.CAFNo = CAFNo;
    }

    public String getCAFDate() {
        return CAFDate;
    }

    public void setCAFDate(String CAFDate) {
        this.CAFDate = CAFDate;
    }

    public int getSourceId() {
        return SourceId;
    }

    public void setSourceId(int sourceId) {
        SourceId = sourceId;
    }

    public int getCablingType() {
        return CablingType;
    }

    public void setCablingType(int cablingType) {
        CablingType = cablingType;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public int getSubAreaId() {
        return SubAreaId;
    }

    public void setSubAreaId(int subAreaId) {
        SubAreaId = subAreaId;
    }

    public int getBuildingId() {
        return BuildingId;
    }

    public void setBuildingId(int buildingId) {
        BuildingId = buildingId;
    }

    public int getTowerId() {
        return TowerId;
    }

    public void setTowerId(int towerId) {
        TowerId = towerId;
    }

    public int getFloorNo() {
        return FloorNo;
    }

    public void setFloorNo(int floorNo) {
        FloorNo = floorNo;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public void setFlatNo(String flatNo) {
        FlatNo = flatNo;
    }

    public int getPincode() {
        return Pincode;
    }

    public void setPincode(int pincode) {
        Pincode = pincode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getISPId() {
        return ISPId;
    }

    public void setISPId(int ISPId) {
        this.ISPId = ISPId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPanNo() {
        return PanNo;
    }

    public void setPanNo(String panNo) {
        PanNo = panNo;
    }

    public int getConnectionTypeId() {
        return ConnectionTypeId;
    }

    public void setConnectionTypeId(int connectionTypeId) {
        ConnectionTypeId = connectionTypeId;
    }

    public String getPayMode() {
        return PayMode;
    }

    public void setPayMode(String payMode) {
        PayMode = payMode;
    }

    public int getPckgId() {
        return PckgId;
    }

    public void setPckgId(int pckgId) {
        PckgId = pckgId;
    }

    public double getPckgCharge() {
        return PckgCharge;
    }

    public void setPckgCharge(double pckgCharge) {
        PckgCharge = pckgCharge;
    }

    public double getInstCharge() {
        return InstCharge;
    }

    public void setInstCharge(double instCharge) {
        InstCharge = instCharge;
    }



    public String getPaymodeDate() {
        return PaymodeDate;
    }

    public void setPaymodeDate(String paymodeDate) {
        PaymodeDate = paymodeDate;
    }

    public String getPaymodeNo() {
        return PaymodeNo;
    }

    public void setPaymodeNo(String paymodeNo) {
        PaymodeNo = paymodeNo;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int roleId) {
        RoleId = roleId;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public String getProspectId() {
        return ProspectId;
    }

    public void setProspectId(String prospectId) {
        ProspectId = prospectId;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public Boolean getIsDSAProspect() {
        return IsDSAProspect;
    }

    public void setIsDSAProspect(Boolean isDSAProspect) {
        IsDSAProspect = isDSAProspect;
    }

    public int getAssignTo() {
        return AssignTo;
    }

    public void setAssignTo(int assignTo) {
        AssignTo = assignTo;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public int getActualCreatedBy() {
        return ActualCreatedBy;
    }

    public void setActualCreatedBy(int actualCreatedBy) {
        ActualCreatedBy = actualCreatedBy;
    }

    public int getRouterId() {
        return RouterId;
    }

    public void setRouterId(int routerId) {
        RouterId = routerId;
    }

    public double getRouterCharges() {
        return RouterCharges;
    }

    public void setRouterCharges(double routerCharges) {
        RouterCharges = routerCharges;
    }

    public String getRouterOfferType() {
        return RouterOfferType;
    }

    public void setRouterOfferType(String routerOfferType) {
        RouterOfferType = routerOfferType;
    }

    public double getRouterRefundAmt() {
        return RouterRefundAmt;
    }

    public void setRouterRefundAmt(double routerRefundAmt) {
        RouterRefundAmt = routerRefundAmt;
    }

    public double getSecurityDepositAmt() {
        return SecurityDepositAmt;
    }

    public void setSecurityDepositAmt(double securityDepositAmt) {
        SecurityDepositAmt = securityDepositAmt;
    }

    public String getSecurityDepositType() {
        return SecurityDepositType;
    }

    public void setSecurityDepositType(String securityDepositType) {
        SecurityDepositType = securityDepositType;
    }

    public double getSecurityDepositRefundAmt() {
        return SecurityDepositRefundAmt;
    }

    public void setSecurityDepositRefundAmt(double securityDepositRefundAmt) {
        SecurityDepositRefundAmt = securityDepositRefundAmt;
    }

    public String getRouterModel() {
        return RouterModel;
    }

    public void setRouterModel(String routerModel) {
        RouterModel = routerModel;
    }

    public String getSecurityDepositDescription() {
        return SecurityDepositDescription;
    }

    public void setSecurityDepositDescription(String securityDepositDescription) {
        SecurityDepositDescription = securityDepositDescription;
    }

    public String getRouterSecurityPayMode() {
        return RouterSecurityPayMode;
    }

    public void setRouterSecurityPayMode(String routerSecurityPayMode) {
        RouterSecurityPayMode = routerSecurityPayMode;
    }

    public String getRouterSecurityPayModeType() {
        return RouterSecurityPayModeType;
    }

    public void setRouterSecurityPayModeType(String routerSecurityPayModeType) {
        RouterSecurityPayModeType = routerSecurityPayModeType;
    }

    public double getRouterSecurityTotalAmount() {
        return RouterSecurityTotalAmount;
    }

    public void setRouterSecurityTotalAmount(double routerSecurityTotalAmount) {
        RouterSecurityTotalAmount = routerSecurityTotalAmount;
    }

    public String getRouterSecurityChequeNumber() {
        return RouterSecurityChequeNumber;
    }

    public void setRouterSecurityChequeNumber(String routerSecurityChequeNumber) {
        RouterSecurityChequeNumber = routerSecurityChequeNumber;
    }

    public String getRouterSecurityChequeDate() {
        return RouterSecurityChequeDate;
    }

    public void setRouterSecurityChequeDate(String routerSecurityChequeDate) {
        RouterSecurityChequeDate = routerSecurityChequeDate;
    }

    public String getRouterSecurityBankName() {
        return RouterSecurityBankName;
    }

    public void setRouterSecurityBankName(String routerSecurityBankName) {
        RouterSecurityBankName = routerSecurityBankName;
    }

    public int getGponId() {
        return GponId;
    }

    public void setGponId(int gponId) {
        GponId = gponId;
    }

    public double getGponCharges() {
        return GponCharges;
    }

    public void setGponCharges(double gponCharges) {
        GponCharges = gponCharges;
    }

    public String getGponOfferType() {
        return GponOfferType;
    }

    public void setGponOfferType(String gponOfferType) {
        GponOfferType = gponOfferType;
    }

    public double getGponRefundAmt() {
        return GponRefundAmt;
    }

    public void setGponRefundAmt(double gponRefundAmt) {
        GponRefundAmt = gponRefundAmt;
    }

    public String getGponModel() {
        return GponModel;
    }

    public void setGponModel(String gponModel) {
        GponModel = gponModel;
    }

    public String getMemberLoginID() {
        return MemberLoginID;
    }

    public void setMemberLoginID(String memberLoginID) {
        MemberLoginID = memberLoginID;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getMACAddress() {
        return MACAddress;
    }

    public void setMACAddress(String MACAddress) {
        this.MACAddress = MACAddress;
    }

    public boolean getBindToMac() {
        return BindToMac;
    }

    public void setBindToMac(boolean bindToMac) {
        BindToMac = bindToMac;
    }

    public int getIPPoolId() {
        return IPPoolId;
    }

    public void setIPPoolId(int IPPoolId) {
        this.IPPoolId = IPPoolId;
    }

    public int getIPNodeType() {
        return IPNodeType;
    }

    public void setIPNodeType(int IPNodeType) {
        this.IPNodeType = IPNodeType;
    }

    public int getNoofMACAllowed() {
        return NoofMACAllowed;
    }

    public void setNoofMACAllowed(int noofMACAllowed) {
        NoofMACAllowed = noofMACAllowed;
    }

    public int getCustTypeId() {
        return CustTypeId;
    }

    public void setCustTypeId(int custTypeId) {
        CustTypeId = custTypeId;
    }

    public int getCustSubTypeId() {
        return CustSubTypeId;
    }

    public void setCustSubTypeId(int custSubTypeId) {
        CustSubTypeId = custSubTypeId;
    }

    public boolean getIsPostPaid() {
        return IsPostPaid;
    }

    public void setIsPostPaid(boolean isPostPaid) {
        IsPostPaid = isPostPaid;
    }

    public int getPPParentMemberId() {
        return PPParentMemberId;
    }

    public void setPPParentMemberId(int PPParentMemberId) {
        this.PPParentMemberId = PPParentMemberId;
    }

    public int getBillingCycleTypeId() {
        return BillingCycleTypeId;
    }

    public void setBillingCycleTypeId(int billingCycleTypeId) {
        BillingCycleTypeId = billingCycleTypeId;
    }

    public int getNoOfMac() {
        return NoOfMac;
    }

    public void setNoOfMac(int noOfMac) {
        NoOfMac = noOfMac;
    }

    public int getNoOfSimUse() {
        return NoOfSimUse;
    }

    public void setNoOfSimUse(int noOfSimUse) {
        NoOfSimUse = noOfSimUse;
    }

    public int getNationality() {
        return Nationality;
    }

    public void setNationality(int nationality) {
        Nationality = nationality;
    }

    /*public static String getAuthName() {
        return AuthName;
    }

    public static void setAuthName(String authName) {
        AuthName = authName;
    }*/

    public boolean getIsIPPOE() {
        return IsIPPOE;
    }

    public void setIsIPPOE(boolean isIPPOE) {
        IsIPPOE = isIPPOE;
    }

    public String getVLANID() {
        return VLANID;
    }

    public void setVLANID(String VLANID) {
        this.VLANID = VLANID;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getMobileNumberSecondary() {
        return MobileNumberSecondary;
    }

    public void setMobileNumberSecondary(String mobileNumberSecondary) {
        MobileNumberSecondary = mobileNumberSecondary;
    }

    public String getEmailIdSecondary() {
        return EmailIdSecondary;
    }

    public void setEmailIdSecondary(String emailIdSecondary) {
        EmailIdSecondary = emailIdSecondary;
    }

    public String getInstCityId() {
        return InstCityId;
    }

    public void setInstCityId(String instCityId) {
        InstCityId = instCityId;
    }

    public boolean getPrimisesType() {
        return PrimisesType;
    }

    public void setPrimisesType(boolean primisesType) {
        PrimisesType = primisesType;
    }

    public String getInstAddress() {
        return InstAddress;
    }

    public void setInstAddress(String instAddress) {
        InstAddress = instAddress;
    }

    public String getInstPinCode() {
        return InstPinCode;
    }

    public void setInstPinCode(String instPinCode) {
        InstPinCode = instPinCode;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        BillingAddress = billingAddress;
    }

    public String getBillingCityId() {
        return BillingCityId;
    }

    public void setBillingCityId(String billingCityId) {
        BillingCityId = billingCityId;
    }

    public String getBillingPinCode() {
        return BillingPinCode;
    }

    public void setBillingPinCode(String billingPinCode) {
        BillingPinCode = billingPinCode;
    }

    public String getPermanentAddress() {
        return PermanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        PermanentAddress = permanentAddress;
    }

    public String getPermanentCityId() {
        return PermanentCityId;
    }

    public void setPermanentCityId(String permanentCityId) {
        PermanentCityId = permanentCityId;
    }

    public String getPermanentPinCode() {
        return PermanentPinCode;
    }

    public void setPermanentPinCode(String permanentPinCode) {
        PermanentPinCode = permanentPinCode;
    }

    public int getRMUserID() {
        return RMUserID;
    }

    public void setRMUserID(int RMUserID) {
        this.RMUserID = RMUserID;
    }

    public int getKAMUserID() {
        return KAMUserID;
    }

    public void setKAMUserID(int KAMUserID) {
        this.KAMUserID = KAMUserID;
    }

    public String getSMEEntityID() {
        return SMEEntityID;
    }

    public void setSMEEntityID(String SMEEntityID) {
        this.SMEEntityID = SMEEntityID;
    }

    public String getSMETypeID() {
        return SMETypeID;
    }

    public void setSMETypeID(String SMETypeID) {
        this.SMETypeID = SMETypeID;
    }

    public String getCorporationName() {
        return CorporationName;
    }

    public void setCorporationName(String corporationName) {
        CorporationName = corporationName;
    }

    public String getIdProof() {
        return IdProof;
    }

    public void setIdProof(String idProof) {
        IdProof = idProof;
    }

    public String getIdProofNumber() {
        return IdProofNumber;
    }

    public void setIdProofNumber(String idProofNumber) {
        IdProofNumber = idProofNumber;
    }

    public String getGSTNumber() {
        return GSTNumber;
    }

    public void setGSTNumber(String GSTNumber) {
        this.GSTNumber = GSTNumber;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public int getPackageDiscount() {
        return PackageDiscount;
    }

    public void setPackageDiscount(int packageDiscount) {
        PackageDiscount = packageDiscount;
    }

    public int getInstDiscount() {
        return InstDiscount;
    }

    public void setInstDiscount(int instDiscount) {
        InstDiscount = instDiscount;
    }

    public String getPackageDiscountGivenBy() {
        return PackageDiscountGivenBy;
    }

    public void setPackageDiscountGivenBy(String packageDiscountGivenBy) {
        PackageDiscountGivenBy = packageDiscountGivenBy;
    }

    public String getPackageStartDate() {
        return PackageStartDate;
    }

    public void setPackageStartDate(String packageStartDate) {
        PackageStartDate = packageStartDate;
    }

    public String getStrPaymentDate() {
        return strPaymentDate;
    }

    public void setStrPaymentDate(String strPaymentDate) {
        this.strPaymentDate = strPaymentDate;
    }

    public String getStrPackageStartDate() {
        return strPackageStartDate;
    }

    public void setStrPackageStartDate(String strPackageStartDate) {
        this.strPackageStartDate = strPackageStartDate;
    }

    /*public String getActivationDate() {
        return ActivationDate;
    }

    public void setActivationDate(String activationDate) {
        ActivationDate = activationDate;
    }*/

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

  /*  public double getOpeningBalance() {
        return OpeningBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        OpeningBalance = openingBalance;
    }*/

    public String getInstDiscountGivenBy() {
        return InstDiscountGivenBy;
    }

    public void setInstDiscountGivenBy(String instDiscountGivenBy) {
        InstDiscountGivenBy = instDiscountGivenBy;
    }

    public String   getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    @Override
    public Object getProperty(int arg0) {
        switch (arg0) {
            case 0:
                return getMobLoginId();
            case 1 :
                return getLoginPassword();
            case 2 :
                return getCreatedBy();
            case 3:
                return getCliectAccessId();
            case 4:
                return getMobileNumber();
            case 5:
                return getCAFNo();
            case 6:
                return getCAFDate();
            case 7:
                return getSourceId();
            case 8:
                return getCablingType();
            case 9:
                return getGender();
            case 10:
                return getDOB();
            case 11:
                return getCityId();
            case 12:
                return getLocationId();
            case 13:
                return getAreaId();
            case 14:
                return getSubAreaId();
            case 15:
                return getBuildingId();
            case 16:
                return getTowerId();
            case 17:
                return getFloorNo();
            case 18:
                return getFlatNo();
            case 19:
                return getPincode();
            case 20:
                return getAddress();
            case 21:
                return getISPId();
            case 22 :
                return getFirstName();
            case 23:
                return getMiddleName();
            case 24:
                return getLastName();
            case 25:
                return getEmailId();
            case 26:
                return getPanNo();
            case 27:
                return getConnectionTypeId();
            case 28:
                return getPayMode();
            case 29:
                return getPckgId();
            case 30:
                return getPckgCharge();
            case 31:
                return getInstCharge();
            case 32:
                return getPaymodeDate();
            case 33:
                return getPaymodeNo();
            case 34:
                return getRoleId();
            case 35:
                return getRoleName();
            case 36:
                return getSourceName();
            case 37:
                return getProspectId();
            case 38:
                return getAmount();
            case 39:
                return getIsDSAProspect();
            case 40:
                return getAssignTo();
            case 41:
                return getStatusId();
            case 42:
                return getReceiptNo();
            case 43:
                return getBankName();
            case 44:
                return getPackageName();
            case 45:
                return getActualCreatedBy();
            case 46:
                return getRouterId();
            case 47:
                return getRouterCharges();
            case 48:
                return getRouterOfferType();
            case 49:
                return getRouterRefundAmt();
            case 50:
                return getSecurityDepositAmt();
            case 51:
                return getSecurityDepositType();
            case 52:
                return getSecurityDepositRefundAmt();
            case 53:
                return getRouterModel();
            case 54:
                return getSecurityDepositDescription();
            case 55:
                return getRouterSecurityPayMode();
            case 56:
                return getRouterSecurityPayModeType();
            case 57:
                return getRouterSecurityTotalAmount();
            case 58:
                return getRouterSecurityChequeNumber();
            case 59:
                return getRouterSecurityChequeDate();
            case 60:
                return getRouterSecurityBankName();
            case 61:
                return getGponId();
            case 62:
                return getGponCharges();
            case 63:
                return getGponOfferType();
            case 64:
                return getGponRefundAmt();
            case 65:
                return getGponModel();
            case 66:
                return getMemberLoginID();
            case 67:
                return getIPAddress();
            case 68:
                return getMACAddress();
            case 69:
                return getBindToMac();
            case 70:
                return getIPPoolId();
            case 71:
                return getIPNodeType();
            case 72:
                return getNoofMACAllowed();
            case 73:
                return getCustTypeId();
            case 74:
                return getCustSubTypeId();
            case 75:
                return getIsPostPaid();
            case 76:
                return getPPParentMemberId();
            case 77:
                return getBillingCycleTypeId();
            case 78:
                return getNoOfMac();
            case 79:
                return getNoOfSimUse();
            case 80:
                return getNationality();
            case 81:
                return getIsIPPOE();
            case 82:
                return getVLANID();
            case 83:
                return getSid();
            case 84:
                return getMobileNumberSecondary();
            case 85:
                return getEmailIdSecondary();
            case 86:
                return getInstCityId();
            case 87:
                return getPrimisesType();
            case 88:
                return getInstAddress();
            case 89:
                return getInstPinCode();
            case 90:
                return getBillingAddress();
            case 91:
                return getBillingCityId();
            case 92:
                return getBillingPinCode();
            case 93:
                return getPermanentAddress();
            case 94:
                return getPermanentCityId();
            case 95:
                return getPermanentPinCode();
            case 96:
                return getRMUserID();
            case 97:
                return getKAMUserID();
            case 98:
                return getSMEEntityID();
            case 99:
                return getSMETypeID();
            case 100:
                return getCorporationName();
            case 101:
                return getIdProof();
            case 102:
                return getIdProofNumber();
            case 103:
                return getGSTNumber();
            case 104:
                return getLatitude();
            case 105:
                return getLongitude();
            case 106:
                return getPackageDiscount();
            case 107:
                return getInstDiscount();
            case 108:
                return getPackageDiscountGivenBy();
            case 109:
                return getInstDiscountGivenBy();
            case 110:
                return getComment();
            case 111:
                return  getStrPaymentDate();
            case 112:
                return  getStrPackageStartDate();
        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 113;
    }

    @Override
    public void setProperty(int i, Object value) {
        switch (i){

            case 0:MobLoginId = value.toString();
                break;
            case 1 :LoginPassword = value.toString();
                break;
            case 2 :CreatedBy = Integer.parseInt(value.toString());
                break;
            case 3:CliectAccessId = value.toString();
                break;
            case 4:MobileNumber = value.toString();
                break;
            case 5:CAFNo = value.toString();
                break;
            case 6:CAFDate = value.toString();
                break;
            case 7:SourceId = Integer.parseInt(value.toString());
                break;
            case 8:CablingType = Integer.parseInt(value.toString());
                break;
            case 9:Gender = value.toString();
                break;
            case 10:DOB = value.toString();
                break;
            case 11:CityId = Integer.parseInt(value.toString());
                break;
            case 12:LocationId = Integer.parseInt(value.toString());
                break;
            case 13:AreaId = Integer.parseInt(value.toString());
                break;
            case 14:SubAreaId = Integer.parseInt(value.toString());
                break;
            case 15:BuildingId = Integer.parseInt(value.toString());
                break;
            case 16:TowerId = Integer.parseInt(value.toString());
                break;
            case 17:FloorNo = Integer.parseInt(value.toString());
                break;
            case 18:FlatNo = value.toString();
                break;
            case 19:Pincode = Integer.parseInt(value.toString());
                break;
            case 20:Address = value.toString();
                break;
            case 21:ISPId = Integer.parseInt(value.toString());
                break;
            case 22 :FirstName = value.toString();
                break;
            case 23:MiddleName = value.toString();
                break;
            case 24:LastName = value.toString();
                break;
            case 25:EmailId = value.toString();
                break;
            case 26:PanNo = value.toString();
                break;
            case 27:ConnectionTypeId = Integer.parseInt(value.toString());
                break;
            case 28:PayMode = value.toString();
                break;
            case 29:PckgId = Integer.parseInt(value.toString());
                break;
            case 30:PckgCharge = Double.parseDouble((value.toString()));
                break;
            case 31:InstCharge = Double.parseDouble(value.toString());
                break;
            case 32:PaymodeDate = value.toString();
                break;
            case 33:PaymodeNo = value.toString();
                break;
            case 34:RoleId = Integer.parseInt(value.toString());
                break;
            case 35:RoleName = value.toString();
                break;
            case 36:SourceName = value.toString();
                break;
            case 37:ProspectId = value.toString();
                break;
            case 38:Amount = Double.parseDouble(value.toString());
                break;
            case 39:IsDSAProspect = Boolean.valueOf(value.toString());
                break;
            case 40:AssignTo = Integer.parseInt(value.toString());
                break;
            case 41:StatusId = Integer.parseInt(value.toString());
                break;
            case 42:ReceiptNo = value.toString();
                break;
            case 43:BankName = value.toString();
                break;
            case 44:PackageName = value.toString();
                break;
            case 45:ActualCreatedBy = Integer.parseInt(value.toString());
                break;
            case 46:RouterId = Integer.parseInt(value.toString());
                break;
            case 47:RouterCharges = Double.parseDouble(value.toString());
                break;
            case 48:RouterOfferType = value.toString();
                break;
            case 49:RouterRefundAmt = Double.parseDouble(value.toString());
                break;
            case 50:SecurityDepositAmt = Double.parseDouble(value.toString());
                break;
            case 51:SecurityDepositType = value.toString();
                break;
            case 52:SecurityDepositRefundAmt = Double.parseDouble(value.toString());
                break;
            case 53:RouterModel = value.toString();
                break;
            case 54:SecurityDepositDescription = value.toString();
                break;
            case 55:RouterSecurityPayMode = value.toString();
                break;
            case 56:RouterSecurityPayModeType = value.toString();
                break;
            case 57:RouterSecurityTotalAmount = Double.parseDouble(value.toString());
                break;
            case 58:RouterSecurityChequeNumber = value.toString();
                break;
            case 59:RouterSecurityChequeDate = value.toString();
                break;
            case 60:RouterSecurityBankName = value.toString();
                break;
            case 61:GponId = Integer.parseInt(value.toString());
                break;
            case 62:GponCharges = Double.parseDouble(value.toString());
                break;
            case 63:GponOfferType = value.toString();
                break;
            case 64:GponRefundAmt = Double.parseDouble(value.toString());
                break;
            case 65:GponModel = value.toString();
                break;
            case 66:MemberLoginID = value.toString();
                break;
            case 67:IPAddress = value.toString();
                break;
            case 68:MACAddress = value.toString();
                break;
            case 69:BindToMac = Boolean.valueOf(value.toString());
                break;
            case 70:IPPoolId = Integer.parseInt(value.toString());
                break;
            case 71:IPNodeType = Integer.parseInt(value.toString());
                break;
            case 72:NoofMACAllowed = Integer.parseInt(value.toString());
                break;
            case 73:CustTypeId = Integer.parseInt(value.toString());
                break;
            case 74:CustSubTypeId = Integer.parseInt(value.toString());
                break;
            case 75:IsPostPaid = Boolean.valueOf(value.toString());
                break;
            case 76:PPParentMemberId = Integer.parseInt(value.toString());
                break;
            case 77:BillingCycleTypeId = Integer.parseInt(value.toString());
                break;
            case 78:NoOfMac = Integer.parseInt(value.toString());
                break;
            case 79:NoOfSimUse = Integer.parseInt(value.toString());
                break;
            case 80:Nationality = Integer.parseInt(value.toString());
                break;
            case 81:IsIPPOE = Boolean.valueOf(value.toString());
                break;
            case 82:VLANID = value.toString();
                break;
            case 83:sid = Integer.parseInt(value.toString());
                break;
            case 84:MobileNumberSecondary = value.toString();
                break;
            case 85:EmailIdSecondary = value.toString();
                break;
            case 86:InstCityId = value.toString();
                break;
            case 87:PrimisesType = Boolean.valueOf(value.toString());
                break;
            case 88:InstAddress = value.toString();
                break;
            case 89:InstPinCode = value.toString();
                break;
            case 90:BillingAddress = value.toString();
                break;
            case 91:BillingCityId = value.toString();
                break;
            case 92:BillingPinCode = value.toString();
                break;
            case 93:PermanentAddress = value.toString();
                break;
            case 94:PermanentCityId = value.toString();
                break;
            case 95:PermanentPinCode = value.toString();
                break;
            case 96:RMUserID = Integer.parseInt(value.toString());
                break;
            case 97:KAMUserID = Integer.parseInt(value.toString());
                break;
            case 98:SMEEntityID = value.toString();
                break;
            case 99:SMETypeID = value.toString();
                break;
            case 100:CorporationName = value.toString();
                break;
            case 101:IdProof = value.toString();
                break;
            case 102:IdProofNumber = value.toString();
                break;
            case 103:GSTNumber = value.toString();
                break;
            case 104:Latitude = value.toString();
                break;
            case 105:Longitude = value.toString();
                break;
            case 106:PackageDiscount = Integer.parseInt(value.toString());
                break;
            case 107:InstDiscount = Integer.parseInt(value.toString());
                break;
            case 108:PackageDiscountGivenBy = value.toString();
                break;
            case 109:
                InstDiscountGivenBy = value.toString();
                break;
            case 110:
                Comment = value.toString();
                break;
            case 111:
                strPaymentDate = value.toString();
                break;
            case 112:
                strPackageStartDate = value.toString();
                break;





        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getPropertyInfo(int i, Hashtable hashtable,PropertyInfo info) {
        switch (i){

            case 0: info.type = PropertyInfo.STRING_CLASS;
                info.name = "MobLoginId";
                break;
            case 1: info.type = PropertyInfo.STRING_CLASS;
                info.name = "LoginPassword";
                break;
            case 2: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CreatedBy";
                break;
            case 3: info.type = PropertyInfo.STRING_CLASS;
                info.name = "CliectAccessId";
                break;
            case 4: info.type = PropertyInfo.STRING_CLASS;
                info.name = "MobileNumber";
                break;
            case 5: info.type = PropertyInfo.STRING_CLASS;
                info.name = "CAFNo";
                break;
            case 6: info.type = PropertyInfo.STRING_CLASS;
                info.name = "CAFDate";
                break;
            case 7: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SourceId";
                break;
            case 8: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CablingType";
                break;
            case 9: info.type = PropertyInfo.STRING_CLASS;
                info.name = "Gender";
                break;
            case 10: info.type = PropertyInfo.STRING_CLASS;
                info.name = "DOB";
                break;
            case 11: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CityId";
                break;
            case 12: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "LocationId";
                break;
            case 13: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AreaId";
                break;
            case 14: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SubAreaId";
                break;
            case 15: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "BuildingId";
                break;
            case 16: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TowerId";
                break;
            case 17: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "FloorNo";
                break;
            case 18: info.type = PropertyInfo.STRING_CLASS;
                info.name = "FlatNo";
                break;
            case 19: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Pincode";
                break;
            case 20: info.type = PropertyInfo.STRING_CLASS;
                info.name = "Address";
                break;
            case 21: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ISPId";
                break;
            case 22: info.type = PropertyInfo.STRING_CLASS;
                info.name = "FirstName";
                break;
            case 23: info.type = PropertyInfo.STRING_CLASS;
                info.name = "MiddleName";
                break;
            case 24: info.type = PropertyInfo.STRING_CLASS;
                info.name = "LastName";
                break;
            case 25: info.type = PropertyInfo.STRING_CLASS;
                info.name = "EmailId";
                break;
            case 26: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PanNo";
                break;
            case 27: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ConnectionTypeId";
                break;
            case 28: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PayMode";
                break;
            case 29: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PckgId";
                break;
            case 30:  info.type = Double.class;
                info.name = "PckgCharge";
                break;
            case 31:  info.type = Double.class;
                info.name = "InstCharge";
                break;
            case 32: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PaymodeDate";
                break;
            case 33: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PaymodeNo";
                break;
            case 34: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RoleId";
                break;
            case 35: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RoleName";
                break;
            case 36: info.type = PropertyInfo.STRING_CLASS;
                info.name = "SourceName";
                break;
            case 37: info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProspectId";
                break;
            case 38:  info.type = Double.class;
                info.name = "Amount";
                break;
            case 39: info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsDSAProspect";
                break;
            case 40: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AssignTo";
                break;
            case 41: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StatusId";
                break;
            case 42: info.type = PropertyInfo.STRING_CLASS;
                info.name = "ReceiptNo";
                break;
            case 43: info.type = PropertyInfo.STRING_CLASS;
                info.name = "BankName";
                break;
            case 44: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PackageName";
                break;
            case 45: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ActualCreatedBy";
                break;
            case 46: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RouterId";
                break;
            case 47: info.type = Double.class;
                info.name = "RouterCharges";
                break;
            case 48: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RouterOfferType";
                break;
            case 49: info.type = Double.class;
                info.name = "RouterRefundAmt";
                break;
            case 50: info.type = Double.class;
                info.name = "SecurityDepositAmt";
                break;
            case 51: info.type = PropertyInfo.STRING_CLASS;
                info.name = "SecurityDepositType";
                break;
            case 52: info.type = Double.class;
                info.name = "SecurityDepositRefundAmt";
                break;
            case 53: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RouterModel";
                break;
            case 54: info.type = PropertyInfo.STRING_CLASS;
                info.name = "SecurityDepositDescription";
                break;
            case 55: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RouterSecurityPayMode";
                break;
            case 56: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RouterSecurityPayModeType";
                break;
            case 57: info.type = Double.class;
                info.name = "RouterSecurityTotalAmount";
                break;
            case 58: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RouterSecurityChequeNumber";
                break;
            case 59: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RouterSecurityChequeDate";
                break;
            case 60: info.type = PropertyInfo.STRING_CLASS;
                info.name = "RouterSecurityBankName";
                break;
            case 61: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "GponId";
                break;
            case 62: info.type = Double.class;
                info.name = "GponCharges";
                break;
            case 63: info.type = PropertyInfo.STRING_CLASS;
                info.name = "GponOfferType";
                break;
            case 64: info.type = Double.class;
                info.name = "GponRefundAmt";
                break;
            case 65: info.type = PropertyInfo.STRING_CLASS;
                info.name = "GponModel";
                break;
            case 66: info.type = PropertyInfo.STRING_CLASS;
                info.name = "MemberLoginID";
                break;
            case 67: info.type = PropertyInfo.STRING_CLASS;
                info.name = "IPAddress";
                break;
            case 68: info.type = PropertyInfo.STRING_CLASS;
                info.name = "MACAddress";
                break;
            case 69: info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "BindToMac";
                break;
            case 70: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "IPPoolId";
                break;
            case 71: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "IPNodeType";
                break;
            case 72: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "NoofMACAllowed";
                break;
            case 73: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CustTypeId";
                break;
            case 74: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CustSubTypeId";
                break;
            case 75: info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsPostPaid";
                break;
            case 76: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PPParentMemberId";
                break;
            case 77: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "BillingCycleTypeId";
                break;
            case 78: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "NoOfMac";
                break;
            case 79: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "NoOfSimUse";
                break;
            case 80: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Nationality";
                break;

            case 81: info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsIPPOE";
                break;
            case 82: info.type = PropertyInfo.STRING_CLASS;
                info.name = "VLANID";
                break;
            case 83: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "sid";
                break;
            case 84: info.type = PropertyInfo.STRING_CLASS;
                info.name = "MobileNumberSecondary";
                break;
            case 85: info.type = PropertyInfo.STRING_CLASS;
                info.name = "EmailIdSecondary";
                break;
            case 86: info.type = PropertyInfo.STRING_CLASS;
                info.name = "InstCityId";
                break;
            case 87: info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "PrimisesType";
                break;
            case 88: info.type = PropertyInfo.STRING_CLASS;
                info.name = "InstAddress";
                break;
            case 89: info.type = PropertyInfo.STRING_CLASS;
                info.name = "InstPinCode";
                break;
            case 90: info.type = PropertyInfo.STRING_CLASS;
                info.name = "BillingAddress";
                break;
            case 91: info.type = PropertyInfo.STRING_CLASS;
                info.name = "BillingCityId";
                break;
            case 92: info.type = PropertyInfo.STRING_CLASS;
                info.name = "BillingPinCode";
                break;
            case 93: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PermanentAddress";
                break;
            case 94: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PermanentCityId";
                break;
            case 95: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PermanentPinCode";
                break;
            case 96: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RMUserID";
                break;
            case 97: info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "KAMUserID";
                break;
            case 98: info.type = PropertyInfo.STRING_CLASS;
                info.name = "SMEEntityID";
                break;
            case 99: info.type = PropertyInfo.STRING_CLASS;
                info.name = "SMETypeID";
                break;
            case 100: info.type = PropertyInfo.STRING_CLASS;
                info.name = "CorporationName";
                break;
            case 101: info.type = PropertyInfo.STRING_CLASS;
                info.name = "IdProof";
                break;
            case 102: info.type = PropertyInfo.STRING_CLASS;
                info.name = "IdProofNumber";
                break;
            case 103: info.type = PropertyInfo.STRING_CLASS;
                info.name = "GSTNumber";
                break;
            case 104: info.type = PropertyInfo.STRING_CLASS;
                info.name = "Latitude";
                break;
            case 105: info.type = PropertyInfo.STRING_CLASS;
                info.name = "Longitude";
                break;
            case 106: info.type = Integer.class;
                info.name = "PackageDiscount";
                break;
            case 107: info.type = Integer.class;
                info.name = "InstDiscount";
                break;

            case 108: info.type = PropertyInfo.STRING_CLASS;
                info.name = "PackageDiscountGivenBy";
                break;
            case 109:info.type = PropertyInfo.STRING_CLASS;
                info.name = "InstDiscountGivenBy";
                break;

            case 110:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Comment";
                break;
            case 111:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "strPaymentDate";
                break;
            case 112:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "strPackageStartDate";
                break;


        }

    }
}
