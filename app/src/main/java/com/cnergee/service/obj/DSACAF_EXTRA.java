package com.cnergee.service.obj;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

class DSACAF_EXTEA implements KvmSerializable, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String _MobLoginId;
    private Integer _CreatedBy;
    private String _CliectAccessId;
    private String _MobileNumber;

    private  String _Billing_Schema;
    private  String _Customer_type;
    private  String _Id_proof_type;
    private  String _Nationality;
    private  String _Service_type;
    private  String _GST_number;
    private  String _billing_address;
    private  String _billing_city;
    private  String _billing_pincode;
    private  String _permenet_address;
    private  String _permenet_city;
    private  String _permenet_pincode;
    private  String _MAC_address;
    private  String _Entity;
    private  String _Entity_type;
    private  String _Relationship_manager;
    private  String _KeyAcount_Manager;
    private  String _Activation_date;
    private  String _payment_date;

    private String _CAFNo;
    private String _CAFDate;
    private int _SourceId;
    private int _CablingType;
    private String _Gender;
    private String _DOB;
    private int _CityId;
    private int _LocationId;
    private int _AreaId;
    private int _SubAreaId;
    private int _BuildingId;
    private int _TowerId;
    private int _FloorNo;
    private String _FlatNo;
    private int _Pincode;
    private String _Address;
    private int _ISPId;
    private String _FirstName;
    private String _MiddleName;
    private String _LastName;
    private String _EmailId;
    private String _PanNo;
    private int _ConnectionTypeId;
    private String _PayMode;
    private Integer _PckgId;
    private Double _PckgCharge;
    private Double _InstCharge;
    private String _PaymodeDate;
    private String _PaymodeNo;
    private int _RoleId;
    private String _RoleName;
    private String _SourceName;
    private String _ProspectId;
    private Double _Amount;
    private Boolean _IsDSAProspect;
    private Integer _AssignTo;
    private int _StatusId;
    private int _UserTypeId;
    private String _ReceiptNo;
    private String _BankName;
    private String _PackageName;
    private int _actual_created_by;

    private int _Router_id;
    private Double _Router_charges;
    private String _Router_type;
    private String _RouterModel;
    private Double _Router_refund_amount;

    private int _Gpon_id;
    private Double _Gpon_charges;
    private String _Gpon_type;
    private String _GponModel;
    private Double _Gpon_refund_amount;

    private Double _Security_charges;
    private String _Security_type;
    private String _SDDescription;
    private Double _Security_refund_amount;
    private String _RS_pay_mode;
    private String _RS_pay_mode_type;
    private Double _RS_total_amount;
    private String _RS_cheque_dd_no;
    private String _RS_cheque_dd_date;
    private String _RS_bank_name;

    public static String AuthName = "objDsaCaf";

    public int getPropertyCount() {
        // TODO Auto-generated method stub
        return 83;
    }

    public Object getProperty(int arg0) {

        switch (arg0) {
            case 0:
                return get_MobLoginId();
            case 1:
                return get_CreatedBy();
            case 2:
                return get_CliectAccessId();
            case 3:
                return get_MobileNumber();
            case 4:
                return get_CAFNo();
            case 5:
                return get_CAFDate();
            case 6:
                return get_SourceId();
            case 7:
                return get_CablingType();
            case 8:
                return get_Gender();
            case 9:
                return get_DOB();
            case 10:
                return get_CityId();
            case 11:
                return get_LocationId();
            case 12:
                return get_AreaId();
            case 13:
                return get_SubAreaId();
            case 14:
                return get_BuildingId();
            case 15:
                return get_TowerId();
            case 16:
                return get_FloorNo();
            case 17:
                return get_FlatNo();
            case 18:
                return get_Pincode();
            case 19:
                return get_Address();
            case 20:
                return get_ISPId();
            case 21:
                return get_FirstName();
            case 22:
                return get_MiddleName();
            case 23:
                return get_LastName();
            case 24:
                return get_EmailId();
            case 25:
                return get_PanNo();
            case 26:
                return get_ConnectionTypeId();
            case 27:
                return get_PayMode();
            case 28:
                return get_PckgId();
            case 29:
                return get_PckgCharge();
            case 30:
                return get_InstCharge();
            case 31:
                return get_PaymodeDate();
            case 32:
                return get_PaymodeNo();
            case 33:
                return get_RoleId();
            case 34:
                return get_RoleName();
            case 35:
                return get_SourceName();
            case 36:
                return get_ProspectId();
            case 37:
                return get_Amount();
            case 38:
                return get_IsDSAProspect();
            case 39:
                return get_AssignTo();
            case 40:
                return get_StatusId();
            case 41:
                return get_UserTypeId();
            case 42:
                return get_ReceiptNo();
            case 43:
                return get_BankName();
            case 44:
                return get_PackageName();
            case 45:
                return get_actual_created_by();
            case 46:
                return get_Router_id();
            case 47:
                return get_Router_charges();
            case 48:
                return get_Router_type();
            case 49:
                return get_Router_refund_amount();
            case 50:
                return get_Security_charges();
            case 51:
                return get_Security_type();
            case 52:
                return get_Security_refund_amount();
            case 53:
                return get_RouterModel();
            case 54:
                return get_SDDescription();
            case 55:
                return get_RS_pay_mode();
            case 56:
                return get_RS_pay_mode_type();
            case 57:
                return getRS_total_amount();
            case 58:
                return getRS_cheque_dd_no();
            case 59:
                return getRS_cheque_dd_date();
            case 60:
                return getRS_bank_name();
            case 61:
                return get_Gpon_id();
            case 62:
                return get_Gpon_charges();
            case 63:
                return get_Gpon_type();
            case 64:
                return get_Gpon_refund_amount();
            case 65:
                return get_GponModel();
            case 66:
                return get_Billing_Schema();
            case 67:
                return get_Customer_type();
            case 68:
                return get_Id_proof_type();
            case 69:
                return get_Nationality();
            case 70:
                return get_Service_type();
            case 71:
                return get_GST_number();
            case 72:
                return get_billing_address();
            case 73:
                return get_billing_city();
            case 74:
                return get_billing_pincode();
            case 75:
                return get_permenet_address();
            case 76:
                return get_permenet_city();
            case 77:
                return get_billing_pincode();
            case 78:
                return get_MAC_address();
            case 79:
                return get_Entity();
            case 80:
                return get_Entity_type();
            case 81:
                return get_Relationship_manager();
            case 82:
                return get_KeyAcount_Manager();
            case 83:
                return get_Activation_date();
        }
        return null;
    }

    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
        switch (index) {
            case 0:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MobLoginId";
                break;
            case 1:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CreatedBy";
                break;
            case 2:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CliectAccessId";
                break;
            case 3:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MobileNumber";
                break;
            case 4:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CAFNo";
                break;
            case 5:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "CAFDate";
                break;
            case 6:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SourceId";
                break;
            case 7:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CablingType";
                break;
            case 8:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Gender";
                break;
            case 9:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "DOB";
                break;
            case 10:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "CityId";
                break;
            case 11:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "LocationId";
                break;
            case 12:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AreaId";
                break;
            case 13:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "SubAreaId";
                break;
            case 14:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "BuildingId";
                break;
            case 15:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "TowerId";
                break;
            case 16:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "FloorNo";
                break;
            case 17:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FlatNo";
                break;
            case 18:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "Pincode";
                break;
            case 19:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "Address";
                break;
            case 20:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ISPId";
                break;
            case 21:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "FirstName";
                break;
            case 22:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "MiddleName";
                break;
            case 23:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "LastName";
                break;
            case 24:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "EmailId";
                break;
            case 25:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PanNo";
                break;
            case 26:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ConnectionTypeId";
                break;
            case 27:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PayMode";
                break;
            case 28:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "PckgId";
                break;
            case 29:
                info.type = Double.class;
                info.name = "PckgCharge";
                break;
            case 30:
                info.type = Double.class;
                info.name = "InstCharge";
                break;
            case 31:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PaymodeDate";
                break;
            case 32:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PaymodeNo";
                break;
            case 33:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RoleId";
                break;
            case 34:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "RoleName";
                break;
            case 35:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "SourceName";
                break;
            case 36:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ProspectId";
                break;
            case 37:
                info.type = Double.class;
                info.name = "Amount";
                break;
            case 38:
                info.type = PropertyInfo.BOOLEAN_CLASS;
                info.name = "IsDSAProspect";
                break;
            case 39:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "AssignTo";
                break;
            case 40:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "StatusId";
                break;
            case 41:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "UserTypeId";
                break;
            case 42:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "ReceiptNo";
                break;
            case 43:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "BankName";
                break;
            case 44:
                info.type = PropertyInfo.STRING_CLASS;
                info.name = "PackageName";
                break;
            case 45:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "ActualCreatedBy";
                break;
            case 46:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "RouterId";
                break;
            case 47:
                info.type = Double.class;
                info.name = "RouterCharges";
                break;
            case 48:
                info.type = String.class;
                info.name = "RouterOfferType";
                break;
            case 49:
                info.type = Double.class;
                info.name = "RouterRefundAmt";
                break;
            case 50:
                info.type = Double.class;
                info.name = "SecurityDepositAmt";
                break;
            case 51:
                info.type = String.class;
                info.name = "SecurityDepositType";
                break;
            case 52:
                info.type = Double.class;
                info.name = "SecurityDepositRefundAmt";
                break;
            case 53:
                info.type = String.class;
                info.name = "RouterModel";
                break;
            case 54:
                info.type = String.class;
                info.name = "SecurityDepositDescription";
                break;
            case 55:
                info.type = String.class;
                info.name = "RouterSecurityPayMode";
                break;
            case 56:
                info.type = String.class;
                info.name = "RouterSecurityPayModeType";
                break;
            case 57:
                info.type = Double.class;
                info.name = "RouterSecurityTotalAmount";
                break;
            case 58:
                info.type = String.class;
                info.name = "RouterSecurityChequeNumber";
                break;
            case 59:
                info.type = String.class;
                info.name = "RouterSecurityChequeDate";
                break;
            case 60:
                info.type = String.class;
                info.name = "RouterSecurityBankName";
                break;
            case 61:
                info.type = PropertyInfo.INTEGER_CLASS;
                info.name = "GponId";
                break;
            case 62:
                info.type = Double.class;
                info.name = "GponCharges";
                break;
            case 63:
                info.type = String.class;
                info.name = "GponOfferType";
                break;
            case 64:
                info.type = Double.class;
                info.name = "GponRefundAmt";
                break;
            case 65:
                info.type = String.class;
                info.name = "GponModel";
                break;
            case 66:
                info.type = String.class;
                info.name = "BillingSchema";
                break;
            case 67:
                info.type = String.class;
                info.name = "CutomerType";
                break;
            case 68:
                info.type = String.class;
                info.name = "IDProofType";
                break;
            case 69:
                info.type = String.class;
                info.name = "Nationality";
                break;
            case 70:
                info.type = String.class;
                info.name = "ServiceType";
                break;
            case 71:
                info.type = String.class;
                info.name = "GSTNumber";
                break;
            case 72:
                info.type = String.class;
                info.name = "BillingAddress";
                break;
            case 73:
                info.type = String.class;
                info.name = "BillingCity";
                break;
            case 74:
                info.type = String.class;
                info.name = "BillingPincode";
                break;
            case 75:
                info.type = String.class;
                info.name = "PermanetAddress";
                break;
            case 76:
                info.type = String.class;
                info.name = "PermanetCity";
                break;
            case 77:
                info.type = String.class;
                info.name = "PermanetPincode";
                break;
            case 78:
                info.type = String.class;
                info.name = "MACAddress";
                break;
            case 79:
                info.type = String.class;
                info.name = "Entity";
                break;
            case 80:
                info.type = String.class;
                info.name = "EntityType";
                break;
            case 81:
                info.type = String.class;
                info.name = "ReletionshipMgr";
                break;
            case 82:
                info.type = String.class;
                info.name = "KeyAccountMgr";
                break;
            case 83:
                info.type = String.class;
                info.name = "ActivationDate";
                break;

        }
    }

    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                _MobLoginId = value.toString();
                break;
            case 1:
                _CreatedBy = Integer.parseInt(value.toString());
                break;
            case 2:
                _CliectAccessId = value.toString();
                break;
            case 3:
                _MobileNumber = value.toString();
                break;
            case 4:
                _CAFNo = value.toString();
                break;
            case 5:
                _CAFDate = value.toString();
                break;
            case 6:
                _SourceId = Integer.parseInt(value.toString());
                break;
            case 7:
                _CablingType = Integer.parseInt(value.toString());
                break;
            case 8:
                _Gender = value.toString();
                break;
            case 9:
                _DOB = value.toString();
                break;
            case 10:
                _CityId = Integer.parseInt(value.toString());
                break;
            case 11:
                _LocationId = Integer.parseInt(value.toString());
                break;
            case 12:
                _AreaId = Integer.parseInt(value.toString());
                break;
            case 13:
                _SubAreaId = Integer.parseInt(value.toString());
                break;
            case 14:
                _BuildingId = Integer.parseInt(value.toString());
                break;
            case 15:
                _TowerId = Integer.parseInt(value.toString());
                break;
            case 16:
                _FloorNo = Integer.parseInt(value.toString());
                break;
            case 17:
                _FlatNo = value.toString();
                break;
            case 18:
                _Pincode = Integer.parseInt(value.toString());
                break;
            case 19:
                _Address = value.toString();
                break;
            case 20:
                _ISPId = Integer.parseInt(value.toString());
                break;
            case 21:
                _FirstName = value.toString();
                break;
            case 22:
                _MiddleName = value.toString();
                break;
            case 23:
                _LastName = value.toString();
                break;
            case 24:
                _EmailId = value.toString();
                break;
            case 25:
                _PanNo = value.toString();
                break;
            case 26:
                _ConnectionTypeId = Integer.parseInt(value.toString());
                break;
            case 27:
                _PayMode = value.toString();
                break;
            case 28:
                _PckgId = Integer.parseInt(value.toString());
                break;
            case 29:
                _PckgCharge = Double.parseDouble(value.toString());
                break;
            case 30:
                _InstCharge = Double.parseDouble(value.toString());
                break;
            case 31:
                _PaymodeDate = value.toString();
                break;
            case 32:
                _PaymodeNo = value.toString();
                break;
            case 33:
                _RoleId = Integer.parseInt(value.toString());
                break;
            case 34:
                _RoleName = value.toString();
                break;
            case 35:
                _SourceName = value.toString();
                break;
            case 36:
                _ProspectId = value.toString();
                break;
            case 37:
                _Amount = Double.parseDouble(value.toString());
                break;
            case 38:
                _IsDSAProspect = Boolean.parseBoolean(value.toString());
                break;
            case 39:
                _AssignTo = Integer.parseInt(value.toString());
                break;
            case 40:
                _StatusId = Integer.parseInt(value.toString());
                break;
            case 41:
                _UserTypeId = Integer.parseInt(value.toString());
                break;
            case 42:
                _ReceiptNo = value.toString();
                break;
            case 43:
                _BankName = value.toString();
                break;
            case 44:
                _PackageName = value.toString();
                break;
            case 45:
                _actual_created_by = Integer.parseInt(value.toString());
                break;
            case 46:
                _Router_id = Integer.parseInt(value.toString());
                break;
            case 47:
                _Router_charges = Double.parseDouble(value.toString());
                break;
            case 48:
                _Router_type = (String) value;
                break;
            case 49:
                _Router_refund_amount = Double.parseDouble(value.toString());
                break;

            case 50:
                _Security_charges = Double.parseDouble(value.toString());
                break;
            case 51:
                _Security_type = (String) value;
                break;
            case 52:
                _Security_refund_amount = Double.parseDouble(value.toString());
                break;
            case 53:
                _RouterModel = (String) value;
                break;
            case 54:
                _SDDescription = (String) value;
                break;
            case 55:
                _RS_pay_mode = (String) value;
                break;
            case 56:
                _RS_pay_mode_type = (String) value;
                break;
            case 57:
                _RS_total_amount = Double.parseDouble(value.toString());
                break;
            case 58:
                _RS_cheque_dd_no = value.toString();
                break;
            case 59:
                _RS_cheque_dd_date = value.toString();
                break;
            case 60:
                _RS_bank_name = value.toString();
                break;
            case 61:
                _Gpon_id=Integer.parseInt(value.toString());
                break;
            case 62:
                _Gpon_charges=Double.parseDouble(value.toString());
                break;
            case 63:
                _Gpon_type= (String) value;
                break;
            case 64:
                _Gpon_refund_amount=Double.parseDouble(value.toString());
                break;
            case 65:
                _GponModel=(String) value;
                break;
            case 66:
                _Billing_Schema=(String) value;
                break;
            case 67:
                _Customer_type=(String) value;
                break;
            case 68:
                _Id_proof_type=(String) value;
                break;
            case 69:
                _Nationality=(String) value;
                break;
            case 70:
                _Security_type=(String) value;
                break;
            case 71:
                _GST_number=(String) value;
                break;
            case 72:
                _billing_address=(String) value;
                break;
            case 73:
                _billing_city=(String) value;
                break;
            case 74:
                _billing_pincode=(String) value;
                break;
            case 75:
                _permenet_address=(String) value;
                break;
            case 76:
                _permenet_city=(String) value;
                break;
            case 77:
                _permenet_pincode=(String) value;
                break;
            case 78:
                _MAC_address=(String) value;
                break;
            case 79:
                _MAC_address=(String) value;
                break;
            case 80:
                _Entity=(String) value;
                break;
            case 81:
                _Entity_type=(String) value;
                break;
            case 82:
                _Relationship_manager=(String) value;
                break;
            case 83:
                _KeyAcount_Manager=(String) value;
                break;

        }

    }

    public String get_MobLoginId() {
        return _MobLoginId;
    }

    public void set_MobLoginId(String _MobLoginId) {
        this._MobLoginId = _MobLoginId;
    }

    public Integer get_CreatedBy() {
        return _CreatedBy;
    }

    public void set_CreatedBy(Integer _CreatedBy) {
        this._CreatedBy = _CreatedBy;
    }

    public String get_CliectAccessId() {
        return _CliectAccessId;
    }

    public void set_CliectAccessId(String _CliectAccessId) {
        this._CliectAccessId = _CliectAccessId;
    }

    public String get_MobileNumber() {
        return _MobileNumber;
    }

    public void set_MobileNumber(String _MobileNumber) {
        this._MobileNumber = _MobileNumber;
    }

    public String get_CAFNo() {
        return _CAFNo;
    }

    public void set_CAFNo(String _CAFNo) {
        this._CAFNo = _CAFNo;
    }

    public String get_CAFDate() {
        return _CAFDate;
    }

    public void set_CAFDate(String _CAFDate) {
        this._CAFDate = _CAFDate;
    }

    public int get_SourceId() {
        return _SourceId;
    }

    public void set_SourceId(int _SourceId) {
        this._SourceId = _SourceId;
    }

    public int get_CablingType() {
        return _CablingType;
    }

    public void set_CablingType(int _CablingType) {
        this._CablingType = _CablingType;
    }

    public String get_Gender() {
        return _Gender;
    }

    public void set_Gender(String _Gender) {
        this._Gender = _Gender;
    }

    public String get_DOB() {
        return _DOB;
    }

    public void set_DOB(String _DOB) {
        this._DOB = _DOB;
    }

    public int get_CityId() {
        return _CityId;
    }

    public void set_CityId(int _CityId) {
        this._CityId = _CityId;
    }

    public int get_LocationId() {
        return _LocationId;
    }

    public void set_LocationId(int _LocationId) {
        this._LocationId = _LocationId;
    }

    public int get_AreaId() {
        return _AreaId;
    }

    public void set_AreaId(int _AreaId) {
        this._AreaId = _AreaId;
    }

    public int get_SubAreaId() {
        return _SubAreaId;
    }

    public void set_SubAreaId(int _SubAreaId) {
        this._SubAreaId = _SubAreaId;
    }

    public int get_BuildingId() {
        return _BuildingId;
    }

    public void set_BuildingId(int _BuildingId) {
        this._BuildingId = _BuildingId;
    }

    public int get_TowerId() {
        return _TowerId;
    }

    public void set_TowerId(int _TowerId) {
        this._TowerId = _TowerId;
    }

    public int get_FloorNo() {
        return _FloorNo;
    }

    public void set_FloorNo(int _FloorNo) {
        this._FloorNo = _FloorNo;
    }

    public String get_FlatNo() {
        return _FlatNo;
    }

    public void set_FlatNo(String _FlatNo) {
        this._FlatNo = _FlatNo;
    }

    public int get_Pincode() {
        return _Pincode;
    }

    public void set_Pincode(int _Pincode) {
        this._Pincode = _Pincode;
    }

    public String get_Address() {
        return _Address;
    }

    public void set_Address(String _Address) {
        this._Address = _Address;
    }

    public int get_ISPId() {
        return _ISPId;
    }

    public void set_ISPId(int _ISPId) {
        this._ISPId = _ISPId;
    }

    public String get_FirstName() {
        return _FirstName;
    }

    public void set_FirstName(String _FirstName) {
        this._FirstName = _FirstName;
    }

    public String get_MiddleName() {
        return _MiddleName;
    }

    public void set_MiddleName(String _MiddleName) {
        this._MiddleName = _MiddleName;
    }

    public String get_LastName() {
        return _LastName;
    }

    public void set_LastName(String _LastName) {
        this._LastName = _LastName;
    }

    public String get_EmailId() {
        return _EmailId;
    }

    public void set_EmailId(String _EmailId) {
        this._EmailId = _EmailId;
    }

    public String get_PanNo() {
        return _PanNo;
    }

    public void set_PanNo(String _PanNo) {
        this._PanNo = _PanNo;
    }

    public int get_ConnectionTypeId() {
        return _ConnectionTypeId;
    }

    public void set_ConnectionTypeId(int _ConnectionTypeId) {
        this._ConnectionTypeId = _ConnectionTypeId;
    }

    public String get_PayMode() {
        return _PayMode;
    }

    public void set_PayMode(String _PayMode) {
        this._PayMode = _PayMode;
    }

    public Integer get_PckgId() {
        return _PckgId;
    }

    public void set_PckgId(Integer _PckgId) {
        this._PckgId = _PckgId;
    }

    public Double get_PckgCharge() {
        return _PckgCharge;
    }

    public void set_PckgCharge(Double _PckgCharge) {
        this._PckgCharge = _PckgCharge;
    }

    public Double get_InstCharge() {
        return _InstCharge;
    }

    public void set_InstCharge(Double _InstCharge) {
        this._InstCharge = _InstCharge;
    }

    public String get_PaymodeDate() {
        return _PaymodeDate;
    }

    public void set_PaymodeDate(String _PaymodeDate) {
        this._PaymodeDate = _PaymodeDate;
    }

    public String get_PaymodeNo() {
        return _PaymodeNo;
    }

    public void set_PaymodeNo(String _PaymodeNo) {
        this._PaymodeNo = _PaymodeNo;
    }

    public int get_RoleId() {
        return _RoleId;
    }

    public void set_RoleId(int _RoleId) {
        this._RoleId = _RoleId;
    }

    public String get_RoleName() {
        return _RoleName;
    }

    public void set_RoleName(String _RoleName) {
        this._RoleName = _RoleName;
    }

    public String get_SourceName() {
        return _SourceName;
    }

    public void set_SourceName(String _SourceName) {
        this._SourceName = _SourceName;
    }

    public String get_ProspectId() {
        return _ProspectId;
    }

    public void set_ProspectId(String _ProspectId) {
        this._ProspectId = _ProspectId;
    }

    public Double get_Amount() {
        return _Amount;
    }

    public void set_Amount(Double _Amount) {
        this._Amount = _Amount;
    }

    public Boolean get_IsDSAProspect() {
        return _IsDSAProspect;
    }

    public void set_IsDSAProspect(Boolean _IsDSAProspect) {
        this._IsDSAProspect = _IsDSAProspect;
    }

    public Integer get_AssignTo() {
        return _AssignTo;
    }

    public void set_AssignTo(Integer _AssignTo) {
        this._AssignTo = _AssignTo;
    }

    public int get_StatusId() {
        return _StatusId;
    }

    public void set_StatusId(int _StatusId) {
        this._StatusId = _StatusId;
    }

    public int get_UserTypeId() {
        return _UserTypeId;
    }

    public void set_UserTypeId(int _UserTypeId) {
        this._UserTypeId = _UserTypeId;
    }

    public String get_ReceiptNo() {
        return _ReceiptNo;
    }

    public void set_ReceiptNo(String _ReceiptNo) {
        this._ReceiptNo = _ReceiptNo;
    }

    public String get_BankName() {
        return _BankName;
    }

    public void set_BankName(String _BankName) {
        this._BankName = _BankName;
    }

    public String get_PackageName() {
        return _PackageName;
    }

    public void set_PackageName(String _PackageName) {
        this._PackageName = _PackageName;
    }

    public int get_actual_created_by() {
        return _actual_created_by;
    }

    public void set_actual_created_by(int _actual_created_by) {
        this._actual_created_by = _actual_created_by;
    }

    public int get_Router_id() {
        return _Router_id;
    }

    public void set_Router_id(int _Router_id) {
        this._Router_id = _Router_id;
    }

    public Double get_Router_charges() {
        return _Router_charges;
    }

    public void set_Router_charges(Double _Router_charges) {
        this._Router_charges = _Router_charges;
    }

    public String get_Router_type() {
        return _Router_type;
    }

    public void set_Router_type(String _Router_type) {
        this._Router_type = _Router_type;
    }

    public Double get_Router_refund_amount() {
        return _Router_refund_amount;
    }

    public void set_Router_refund_amount(Double _Router_refund_amount) {
        this._Router_refund_amount = _Router_refund_amount;
    }

    public int get_Gpon_id() {
        return _Gpon_id;
    }

    public void set_Gpon_id(int _Gpon_id) {
        this._Gpon_id = _Gpon_id;
    }

    public Double get_Gpon_charges() {
        return _Gpon_charges;
    }

    public void set_Gpon_charges(Double _Gpon_charges) {
        this._Gpon_charges = _Gpon_charges;
    }

    public String get_Gpon_type() {
        return _Gpon_type;
    }

    public void set_Gpon_type(String _Gpon_type) {
        this._Gpon_type = _Gpon_type;
    }

    public String get_GponModel() {
        return _GponModel;
    }

    public void set_GponModel(String _GponModel) {
        this._GponModel = _GponModel;
    }

    public Double get_Gpon_refund_amount() {
        return _Gpon_refund_amount;
    }

    public void set_Gpon_refund_amount(Double _Gpon_refund_amount) {
        this._Gpon_refund_amount = _Gpon_refund_amount;
    }

    public Double get_Security_charges() {
        return _Security_charges;
    }

    public void set_Security_charges(Double _Security_charges) {
        this._Security_charges = _Security_charges;
    }

    public String get_Security_type() {
        return _Security_type;
    }

    public void set_Security_type(String _Security_type) {
        this._Security_type = _Security_type;
    }

    public Double get_Security_refund_amount() {
        return _Security_refund_amount;
    }

    public void set_Security_refund_amount(Double _Security_refund_amount) {
        this._Security_refund_amount = _Security_refund_amount;
    }

    public String get_RouterModel() {
        return _RouterModel;
    }

    public void set_RouterModel(String _RouterModel) {
        this._RouterModel = _RouterModel;
    }

    public String get_SDDescription() {
        return _SDDescription;
    }

    public void set_SDDescription(String _SDDescription) {
        this._SDDescription = _SDDescription;
    }

    public String get_RS_pay_mode() {
        return _RS_pay_mode;
    }

    public void set_RS_pay_mode(String _RS_pay_mode) {
        this._RS_pay_mode = _RS_pay_mode;
    }

    public String get_RS_pay_mode_type() {
        return _RS_pay_mode_type;
    }

    public void set_RS_pay_mode_type(String _RS_pay_mode_type) {
        this._RS_pay_mode_type = _RS_pay_mode_type;
    }

    public Double getRS_total_amount() {
        return _RS_total_amount;
    }

    public void setRS_total_amount(Double rS_total_amount) {
        _RS_total_amount = rS_total_amount;
    }

    public String getRS_cheque_dd_no() {
        return _RS_cheque_dd_no;
    }

    public void setRS_cheque_dd_no(String rS_cheque_dd_no) {
        _RS_cheque_dd_no = rS_cheque_dd_no;
    }

    public String getRS_cheque_dd_date() {
        return _RS_cheque_dd_date;
    }

    public void setRS_cheque_dd_date(String rS_cheque_dd_date) {
        _RS_cheque_dd_date = rS_cheque_dd_date;
    }

    public String getRS_bank_name() {
        return _RS_bank_name;
    }

    public void setRS_bank_name(String rS_bank_name) {
        _RS_bank_name = rS_bank_name;
    }

    public String get_Billing_Schema() {
        return _Billing_Schema;
    }

    public void set_Billing_Schema(String _Billing_Schema) {
        this._Billing_Schema = _Billing_Schema;
    }

    public String get_Customer_type() {
        return _Customer_type;
    }

    public void set_Customer_type(String _Customer_type) {
        this._Customer_type = _Customer_type;
    }

    public String get_Id_proof_type() {
        return _Id_proof_type;
    }

    public void set_Id_proof_type(String _Id_proof_type) {
        this._Id_proof_type = _Id_proof_type;
    }

    public String get_Nationality() {
        return _Nationality;
    }

    public void set_Nationality(String _Nationality) {
        this._Nationality = _Nationality;
    }

    public String get_Service_type() {
        return _Service_type;
    }

    public void set_Service_type(String _Service_type) {
        this._Service_type = _Service_type;
    }

    public String get_GST_number() {
        return _GST_number;
    }

    public void set_GST_number(String _GST_number) {
        this._GST_number = _GST_number;
    }

    public String get_billing_address() {
        return _billing_address;
    }

    public void set_billing_address(String _billing_address) {
        this._billing_address = _billing_address;
    }

    public String get_billing_city() {
        return _billing_city;
    }

    public void set_billing_city(String _billing_city) {
        this._billing_city = _billing_city;
    }

    public String get_billing_pincode() {
        return _billing_pincode;
    }

    public void set_billing_pincode(String _billing_pincode) {
        this._billing_pincode = _billing_pincode;
    }

    public String get_permenet_address() {
        return _permenet_address;
    }

    public void set_permenet_address(String _permenet_address) {
        this._permenet_address = _permenet_address;
    }

    public String get_permenet_city() {
        return _permenet_city;
    }

    public void set_permenet_city(String _permenet_city) {
        this._permenet_city = _permenet_city;
    }

    public String get_permenet_pincode() {
        return _permenet_pincode;
    }

    public void set_permenet_pincode(String _permenet_pincode) {
        this._permenet_pincode = _permenet_pincode;
    }

    public String get_MAC_address() {
        return _MAC_address;
    }

    public void set_MAC_address(String _MAC_address) {
        this._MAC_address = _MAC_address;
    }

    public String get_Entity() {
        return _Entity;
    }

    public void set_Entity(String _Entity) {
        this._Entity = _Entity;
    }

    public String get_Entity_type() {
        return _Entity_type;
    }

    public void set_Entity_type(String _Entity_type) {
        this._Entity_type = _Entity_type;
    }

    public String get_Relationship_manager() {
        return _Relationship_manager;
    }

    public void set_Relationship_manager(String _Relationship_manager) {
        this._Relationship_manager = _Relationship_manager;
    }

    public String get_KeyAcount_Manager() {
        return _KeyAcount_Manager;
    }

    public void set_KeyAcount_Manager(String _KeyAcount_Manager) {
        this._KeyAcount_Manager = _KeyAcount_Manager;
    }

    public String get_Activation_date() {
        return _Activation_date;
    }

    public void set_Actication_date(String _Activation_date) {
        this._Activation_date = _Activation_date;
    }

    public String get_payment_date() {
        return _payment_date;
    }

    public void set_payment_date(String _payment_date) {
        this._payment_date = _payment_date;
    }
}
