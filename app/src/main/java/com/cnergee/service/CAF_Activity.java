package com.cnergee.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.cnergee.service.SOAP.AddressDetailsSOAP;
import com.cnergee.service.SOAP.InsertDetailsSOAP;
import com.cnergee.service.SOAP.MiniCafDataSOAP;
import com.cnergee.service.SOAP.PackageNameSOAP;
import com.cnergee.service.adapter.Common_Item_Adapter;
import com.cnergee.service.adapter.Common_Item_Adapter_Package;
import com.cnergee.service.caller.InsertDetailsCaller;
import com.cnergee.service.customview.MyButtonView;
import com.cnergee.service.customview.MyEditTextView;
import com.cnergee.service.customview.MyTextView;
import com.cnergee.service.loadingview.ProgressWheel;
import com.cnergee.service.obj.Common_Item;
import com.cnergee.service.obj.DSACAF;
import com.cnergee.service.util.Utils;
import com.cnergee.service.utils.DialogUtils;
import com.cnergee.service.utils.MyUtils;
//import com.desmond.squarecamera.CameraActivity;
//import com.desmond.squarecamera.ImageUtility;
//import com.dsa.cnergee.SOAP.AddressDetailsSOAP;
//import com.dsa.cnergee.SOAP.InsertDetailsSOAP;
//import com.dsa.cnergee.SOAP.MiniCafDataSOAP;
//import com.dsa.cnergee.SOAP.PackageNameSOAP;
//import com.dsa.cnergee.adapter.Common_Item_Adapter;
//import com.dsa.cnergee.customview.MyButtonView;
//import com.dsa.cnergee.customview.MyTextView;
//import com.dsa.cnergee.loadingview.ProgressWheel;
//import com.dsa.cnergee.obj.Common_Item;
//import com.dsa.cnergee.obj.DSACAF;
import com.desmond.squarecamera.CameraActivity;
import com.desmond.squarecamera.ImageUtility;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.rengwuxian.materialedittext.MaterialEditText;
//import com.utils.DialogUtils;
//import com.utils.MyUtils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.RequiresApi;
import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

;import static android.widget.Toast.LENGTH_SHORT;

public class CAF_Activity extends Activity implements AdapterView.OnItemSelectedListener {
    private static final int REQUEST_CAMERA = 0;
    private int returnCode;

    FTPClient client = new FTPClient();
    String latitude,longitude,address;
    public static String rslt="";

    int SELECT_FILE = 1, j = 0;
    public static NiftyDialogBuilder dialog_box;
    static SharedPreferences pref;
    String img_store_path = "", image_name = "";
    String caf_img_path = "", address_img_path = "", identity_img_path = "", folder_name = "";

    boolean is_card_allowed = false;
    HashMap<String, String> hashMap_cm = new HashMap<String, String>();
    String[] str;
    boolean billing_schemeValue=true;

    Utils util;


    DSACAF dsacaf ;


    boolean is_caf_select = false, is_address_scan = false, is_identity_scan = false;

    ProgressWheel progress_wheel;
    private Calendar calendar;
    private int year, month, day;

    Geocoder geocoder;

    List<Address> addresses = null;

    public static String selected_router_id;
    public static String selected_gpon_id;
    public static String id_proof_id;
    String Login_UserId, Username , login_user_role , show_other_charges , source_id , source_name, role_name,package_name, package_amount ,  emailid
            , mbnumber, cheque_date, rs_cheque_date , activation_date_str, payment_date_str, clientaccess_id,password ;

    String city_id , location_id , conn_type_id , area_id , isp_id , sub_area_id , building_id , tower_id , floor_no , package_id , manager_id ,
            cabling_id ,  natinality_id  ,loc_city_id ,bill_city_id ,per_city_id ,bill_city_name;

    String selected_router_type , selected_gpon_type , selected_security_type ,selected_post_type,selected_natt_type;

    String gender_value , selected_gender , router_type, gpon_type, security_type ,post_type ,natt_type;

    String router_id = "0",  gpon_id = "0";
    String value = "CQ", selected_mode = "CS", selected_rs_pay_type = "CS", rs_pay_mode = "Same";
    String dob_to_send, cheque_date_to_send, caf_filled_to_send, RS_Cheque_date_send, actication_date_send, payment_date_send;

    double  gpon_charges = 0.0, gpon_refund = 0.0, security_deposit = 0.0, security_refund = 0.0,post_charges=0.0,post_refund=0.0 ,natt_charges =0.0,
            natt_refund =0.0,router_charges = 0.0, router_refund = 0.0,
            pkg_amount, installation_amount,pkg_discount_amount =0.0,installation_discount_amount=0.0, total_amount,  rs_router_amount, rs_gpon_amount,
            rs_security_amount, rs_post_amount,rs_natt_amount,rs_total_amount, rs_pay_amount;
    double router_amount , security_amount , gpon_amount ,post_amount ,natt_amount  ;


    String city;

    boolean dsa = true;
    boolean finish = false;

    String MemberId, AreaCode, ClientAccessId;

    ImageView Birth_calendar, CAf_filled_calendar, Cheque_calendar, RS_cheque_calendar, iv_logout, scannedImage, activation_date, payment_date;
    int RoleId;
    private Point mSize;

    static final int BIRTH_DATE_DISPLAY = 0, FORM_FILLED_DATE = 1, CHEQUE_DATE = 3, RS_CHEQUE_DATE = 4, ACTIVATION_DATE_final = 5, PAYMENT_DATE_FINAL = 6;


    ArrayList<Common_Item> al_connection_type, al_isp_type, al_city_type, al_router_type,al_service_type, al_gpon_type, al_cabling_type, al_user_list, al_manager_list, al_location_type,
            al_area_type, al_sub_area_type, al_building_type, al_tower_type, al_floor_type, alPackage,allPackage,
            al_cust_type, al_id_proof_type, al_nationality_type , al_entity_type, al_type_type , al_relationShip_mng_type, al_key_account_mng_type,
            al_billing_cycle_type, al_sp_install_by_type, al_pkdiscount_type, al_installdiscount_type;

    ArrayList<String> al_user_id, al_channnel_manager, al_images_upload;

    Spinner sp_connection, sp_city, sp_isp, sp_location, sp_area, sp_sub_area, sp_building, sp_tower, sp_floor, sp_pkg_name,
            sp_channel_manager, sp_select_user, sp_cabling_type, sp_router_type, sp_gpon_type;

    MyTextView tv_displayUser, tv_disp_caf, tv_disp_address, tv_disp_identity, activation_date_iv, payment_date_iv,
            tv_connection,tv_isp;
    ImageView tv_select_caf,tv_select_address,tv_select_identity;
    MaterialEditText et_caf_no, et_firstname, et_middlename, et_lastname, et_mbnumber, et_pannumber, et_emailid, et_flatno, et_pincode, et_addressline, et_router_model, et_router_charges, et_router_refund, et_security_charges,
            et_security_refund, et_security_description, et_gpon_model, et_gpon_charges, et_gpon_refund, et_post_charges,et_post_refund,install_pincode,
            et_post_desc,et_natt_charges,et_natt_refund,et_natt_desc,et_rs_cheque_dd_no, et_rs_bank_Name, et_chequeno, et_bankName/*,et_disp_img_name,et_address_scan*/,
            bill_addressline, bill_city, bill_pincode, per_addressline, per_city, per_pincode, member_loginId,
            gst_no, mac_add,et_installation_discount,et_pkgamount_discount,et_pkgamount;

          EditText  et_opening_balance,et_cash_amt,et_latitude,et_longitude;

    LinearLayout ll_router_security, linear_User, ll_channelmanager, linear_paymentmode, linear_radio_button, linear_cheque_details, ll_router_details, ll_total_charges_selection, ll_gpon_details, ll_security_details, ll_separate_payment, ll_rs_cheque_dd_no, ll_rs_cheque_date, ll_rs_bank_Name, ll_pm_cd_date, linear_cafform, ll_total_charges, adhar_opt, mac_add_ll, entity_ll, type_entity_ll,
            relationShip_mng_ll, key_account_mng_ll, ll_billing_cycle,ll_post_details,ll_natting_details;

    Common_Item_Adapter channel_mgr_adapter, location_Adapter, area_Adapter, sub_area_Adapter, building_Adapter, tower_Adapter, floor_Adapter,
            connection_Adapter, isp_Adapter, city_Adapter, router_Adapter, service_Adapter,gpon_Adapter, user_Adapter, cabling_Adapter, customer_Adapter, idproof_Adapter,
        nationality_Adapter, entity_Adapter, relationShip_mng_Adapter, key_account_mng_Adapter, billing_cycle_Adapter,
        sp_install_by_Adapter, type_Adapter, sp_pckdisc_Adapter, sp_installetiondiscount_Adapter;

    Common_Item_Adapter_Package package_Adapter;
    EditText  et_rs_amount, et_installation, et_total_amount,et_comment;

    MyTextView tv_displaydob, tv_disp_rs_cheque_date, tv_displaychequeDate, tv_displyfilleddate;

    RadioGroup select_gender, select_payment_mode, select_router, select_security, select_gpon, rg_same_separate,
            rg_radiobtn_pay_mode,rg_post_refund,rg_natt_refund,radioGroup1;

    RadioButton select_male, select_female,select_other, select_cash, select_cheque, select_edc, rb_chargable,
            rb_free, rb_refund, rb_security_refund, rb_nonrefund, rb_same, rb_separate, rb_rs_cash, rb_rs_cheque, rb_rs_dd,
            rb_chargable_gpon, rb_free_gpon, rb_refund_gpon,rb_post_nonrefund,rb_post_refund, rb_natt_nonrefund,rb_natt_refund,rb_own,rb_rented;

    CheckBox cb_router, cb_security, cb_gpon , cb_post_forward,cb_natting_charges;

    MyButtonView btn_submit, getlocationmap;


    FTPClient ftpClient = new FTPClient();

    String client_accesss_id;

    String userid, customer_type_corpo, billing_scheme_selected, service_type_id, entity_id, entity_type_id
           ,relationshipmgrid, keyacountmgrid, installbyid, packdicbyid, installtiondicbyid;


    ToggleSwitch billing_scheme;

    HashMap<String, HashMap<String, String>> hm_upload_image = new HashMap<>();
    HashMap<String, String> hm_data = new HashMap<String, String>();
    HashMap<String, String> hm_data_value = new HashMap<String, String>();
    private String sharedPreferences_name;

    boolean is_caf_upload = false, is_address_upload = false, is_identity_upload = false, PrimisesType=false;
    SharedPreferences sharedPreferences;

    public static  int r_key,s_key,g_key;
    public static  String cb_value;

    //////////////////////////Dummy Fields ////////////////////////////////////


    Spinner sp_customertype, id_list, nationality, service_type, entity, type, relationShip_mng, key_account_mng, billing_cycle,
            sp_pkdicountby, sp_installation_discount_by, sp_install_by  ,sp_loc_city ,sp_bill_city,sp_per_city;

    List<String> cust_type = new ArrayList<String>();
    List<String> id_proof = new ArrayList<String>();
    List<String> Nationality = new ArrayList<String>();
    List<String> entity_list = new ArrayList<String>();
    List<String> type_list = new ArrayList<String>();
    List<String> relationShip_mng_list = new ArrayList<String>();
    List<String> key_account_mng_list = new ArrayList<String>();
    List<String> billing_cycle_list = new ArrayList<String>();
    List<String> sp_pkdicountby_list = new ArrayList<String>();
    List<String> sp_installation_discount_by_list = new ArrayList<String>();
    List<String> sp_install_by_list = new ArrayList<String>();

    LinearLayout table_viwe, ll_customertype;
    CheckBox copybillingaddress, copyinstalladdress;


    Pattern MobilePattern = Pattern.compile("^[7-9][0-9]{9}$");
    Pattern pincodePattern = Pattern.compile("[0-9]{6}");
    //////////////////////////Dummy Fields ////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caf);
        dsacaf = new DSACAF();

        sharedPreferences_name = getString(R.string.shared_preferences_name);
        pref = getApplicationContext().getSharedPreferences(sharedPreferences_name, 0);

        client_accesss_id = pref.getString("CliectAccessId", "");
        userid = pref.getString("MobLoginId", "");

        is_card_allowed = pref.getBoolean(MyUtils.EZETAP_ALLOWED, false);

        sharedPreferences = getApplicationContext().getSharedPreferences("gps_location", 0);
        sharedPreferences.edit().clear();



        initControls();

        //billing_scheme.setChecked(true);
//        billing_scheme.setTextOn("PostPaid"); // displayed text of the Switch whenever it is in checked or on state
//        billing_scheme.setTextOff("Prepaid");
//
//        billing_scheme.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                //toggle.toggle();
//                if ( billing_scheme.isChecked()) {
//
//                   // billing_scheme.setTextOn("PostPaid");
//                    billing_scheme.setChecked(true);
//                } else {
//
//                 //   billing_scheme.setTextOff("Prepaid");
//                    billing_scheme.setChecked(false);
//
//                }
//            }
//        });

        billing_scheme = (ToggleSwitch) findViewById(R.id.billing_scheme);

        table_viwe = (LinearLayout) findViewById(R.id.table_viwe);
        ll_customertype = (LinearLayout) findViewById(R.id.ll_customertype);

        billing_scheme.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {


                billing_scheme_selected = String.valueOf(position);
                //billing_schemeValue = isChecked;

                if (position == 0) {
                    billing_schemeValue=true;
                    ll_customertype.setVisibility(View.GONE);
                    table_viwe.setVisibility(View.GONE);
                    ll_billing_cycle.setVisibility(View.GONE);
                } else {
                    billing_schemeValue=false;
                    ll_customertype.setVisibility(View.VISIBLE);
                    table_viwe.setVisibility(View.VISIBLE);
                    ll_billing_cycle.setVisibility(View.VISIBLE);
                   // if (al_sp)

                }
                Log.d("total button : ", position + "    " + isChecked    +"  "+billing_schemeValue);
            }
        });

        onclick();

        if (MyUtils.isOnline(CAF_Activity.this)) {
            new CallWebservice().execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please Connect to internet !", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("WrongViewCast")
    public void initControls() {
        getlocationmap = (MyButtonView) findViewById(R.id.getlocationmap);
        adhar_opt = (LinearLayout) findViewById(R.id.adhar_opt);
        copybillingaddress = (CheckBox) findViewById(R.id.copybillingaddress);
        copyinstalladdress = (CheckBox) findViewById(R.id.copyinstalladdress);
        sp_customertype = (Spinner) findViewById(R.id.sp_customertype);
        sp_customertype.setOnItemSelectedListener(this);
//        cust_type.add("Select Customer type");
//        cust_type.add("Individual");
//        cust_type.add("Corporate");
//        ArrayAdapter<String> cust_type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cust_type);
//        cust_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        sp_customertype.setAdapter(cust_type_adapter);

        id_list = (Spinner) findViewById(R.id.id_list);
        id_list.setOnItemSelectedListener(this);
//        id_proof.add("Select ID Proof");
//        id_proof.add("Adhar Card");
//        id_proof.add("PAN Card");
//        id_proof.add("Voter Id");
//        id_proof.add("Driving Licence");
//        id_proof.add("Password");
//
//        ArrayAdapter<String> ip_proof_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, id_proof);
//        ip_proof_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        id_list.setAdapter(ip_proof_adapter);

        id_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try{

                    if(al_id_proof_type.get(position).getItem_name().equalsIgnoreCase("<-SelectIdProof->")){

                        et_pannumber.setVisibility(View.GONE);
                    }else{
                        et_pannumber.setFloatingLabelText(al_id_proof_type.get(position).getItem_name());
                        et_pannumber.setHint("Enter " + al_id_proof_type.get(position).getItem_name() + " Number");
                        id_proof_id = al_id_proof_type.get(position).getItem_id();

                        Log.e("id_proof",":"+id_proof_id);
                        et_pannumber.setVisibility(View.VISIBLE);
                        switch (id_proof_id){
                            case "1" :
                                et_pannumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                                break;
                            default:
                                et_pannumber.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                        et_pannumber.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                switch (id_proof_id){
                                    case "1" :
                                        String aadharNumber = et_pannumber.getText().toString();
                                        Pattern aadharPattern = Pattern.compile("[0-9]{10}");
                                        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
                                        if(!isValidAadhar){
                                            et_pannumber.setError("Please Enter Valid Adhar Number.");
                                        }
                                        break;
                                    case "2":
                                        String pannumber = et_pannumber.getText().toString();
                                        Pattern panPattern = Pattern.compile("(([A-Za-z]{5})([0-9]{4})([a-zA-Z]{1}))");
                                        boolean isValidPan = panPattern.matcher(pannumber).matches();
                                        if(!isValidPan){
                                            et_pannumber.setError("Please Enter Valid PAN Number.");
                                        }
                                        break;
                                    case "3":
                                        String votaridnumber = et_pannumber.getText().toString();
                                        Pattern vPattern = Pattern.compile("(([a-zA-Z]){3}([0-9]){7})");
                                        boolean isValidVotar = vPattern.matcher(votaridnumber).matches();
                                        if(!isValidVotar){
                                            et_pannumber.setError("Please Enter Valid PAN Number.");
                                        }

                                        break;
                                    case "4":
                                        String drivingnumber = et_pannumber.getText().toString();
                                        Pattern dPattern = Pattern.compile("^[A-Z]{2}\\s[0-9]{2}\\s[A-Z]{2}\\s[0-9]{4}$");
                                        boolean isValiddriver = dPattern.matcher(drivingnumber).matches();
                                        if(!isValiddriver){
                                            et_pannumber.setError("Please Enter Valid Driving license Number.");
                                        }
                                        break;
                                    case "5":
                                        String passportnumber = et_pannumber.getText().toString();
                                        Pattern passportPattern = Pattern.compile("^[A-Z]{1}-[0-9]{7}$");
                                        boolean isValidPassport= passportPattern.matcher(passportnumber).matches();
                                        if(!isValidPassport){
                                            et_pannumber.setError("Please Enter Valid Passport Number.");
                                        }
                                }
                            }
                        });
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });





        nationality = (Spinner) findViewById(R.id.nationality);
        nationality.setOnItemSelectedListener(this);
//        Nationality.add("Select Nationality");
//        Nationality.add("Indian");
//        Nationality.add("American");
//        Nationality.add("Australian");
//        Nationality.add("Yugoslavian");
//        ArrayAdapter<String> nationality_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Nationality);
//        nationality_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        nationality.setAdapter(nationality_adapter);


        nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                try{

                    natinality_id = al_nationality_type.get(position).getItem_id();
                    if (al_nationality_type.get(position).getItem_id().equals("1")){

//                        et_pincode.setMaxCharacters(6);
//                        bill_pincode.setMaxCharacters(6);
//                        per_pincode.setMaxCharacters(6);



                        et_pincode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});
                        bill_pincode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});
                        per_pincode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});

                    }else{
                        et_pincode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
                        bill_pincode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
                        per_pincode.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        sp_customertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                try{

                    customer_type_corpo = al_cust_type.get(position).getItem_name();
                    if (customer_type_corpo.equals("Corporate")){
                        gst_no.setHint("GST is Mandatory");
                    }else{
                        gst_no.setHint("GST is (Optional)");
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        entity = (Spinner) findViewById(R.id.entity);
        entity.setOnItemSelectedListener(this);
//        entity_list.add("Select Entity");
//        entity_list.add("Proprietor");
//        entity_list.add("Partnership");
//        entity_list.add("Pvt Ltd");
//        entity_list.add("NGO");
//        entity_list.add("Trust");
//        entity_list.add("Club");
//        ArrayAdapter<String> entity_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, entity_list);
//        entity_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        entity.setAdapter(entity_adapter);


        entity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                try{

                    entity_id = al_entity_type.get(position).getItem_id();
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        type = (Spinner) findViewById(R.id.type);
        type.setOnItemSelectedListener(this);
//        type_list.add("Select Type of Entity");
//        type_list.add("Office");
//        type_list.add("Shop");
//        type_list.add("Restorer");
//        type_list.add("Clinic");
//        type_list.add("Service Center");
//        type_list.add("Club");
//        type_list.add("Other");
//        ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type_list);
//        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        type.setAdapter(type_adapter);


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                try{

                    entity_type_id = al_entity_type.get(position).getItem_id();
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ///relationShip_mng

        relationShip_mng = (Spinner) findViewById(R.id.relationShip_mng);
        relationShip_mng.setOnItemSelectedListener(this);
//        relationShip_mng_list.add("Select");
//        relationShip_mng_list.add("TestCRM");
//        ArrayAdapter<String> relationShip_mng_list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, relationShip_mng_list);
//        relationShip_mng_list_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        relationShip_mng.setAdapter(relationShip_mng_list_adapter);

        relationShip_mng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                try{
                    relationshipmgrid = al_relationShip_mng_type.get(position).getItem_id();
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        key_account_mng = (Spinner) findViewById(R.id.key_account_mng);
        key_account_mng.setOnItemSelectedListener(this);
//        key_account_mng_list.add("Select");
//        key_account_mng_list.add("TestCRM");
//        ArrayAdapter<String> key_account_mng_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, key_account_mng_list);
//        key_account_mng_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        key_account_mng.setAdapter(key_account_mng_adapter);

        key_account_mng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                try{

                    keyacountmgrid = al_key_account_mng_type.get(position).getItem_id();
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        billing_cycle = (Spinner) findViewById(R.id.billing_cycle);
        billing_cycle.setOnItemSelectedListener(this);
//        billing_cycle_list.add("Select");
//        billing_cycle_list.add("Monthly");
//        billing_cycle_list.add("Bi Monthly");
//        billing_cycle_list.add("Quarterly");
//        billing_cycle_list.add("Half Year");
//        billing_cycle_list.add("Yearly");
//        ArrayAdapter<String> billing_cycle_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, billing_cycle_list);
//        billing_cycle_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        billing_cycle.setAdapter(billing_cycle_adapter);

        //sp_pkdicountby, sp_installation_discount_by, sp_install_by;
            sp_pkdicountby = (Spinner) findViewById(R.id.sp_pkdicountby);
            sp_pkdicountby.setOnItemSelectedListener(this);
//        sp_pkdicountby_list.add("Select");
//        sp_pkdicountby_list.add("Smith");
//        sp_pkdicountby_list.add("John");
//        sp_pkdicountby_list.add("Harry");
//        ArrayAdapter<String> sp_pkdicountby_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sp_pkdicountby_list);
//        sp_pkdicountby_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        sp_pkdicountby.setAdapter(sp_pkdicountby_adapter);


        sp_installation_discount_by = (Spinner) findViewById(R.id.sp_installation_discount_by);
        sp_installation_discount_by.setOnItemSelectedListener(this);
//        sp_installation_discount_by_list.add("Select");
//        sp_installation_discount_by_list.add("Smith");
//        sp_installation_discount_by_list.add("John");
//        sp_installation_discount_by_list.add("Harry");
//        ArrayAdapter<String> sp_installation_discount_by_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sp_installation_discount_by_list);
//        sp_installation_discount_by_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        sp_installation_discount_by.setAdapter(sp_installation_discount_by_adapter);


        sp_install_by = (Spinner) findViewById(R.id.sp_install_by);
        sp_install_by.setOnItemSelectedListener(this);

        sp_loc_city = (Spinner) findViewById(R.id.sp_loc_city);
        sp_loc_city.setOnItemSelectedListener(this);

        sp_bill_city= (Spinner) findViewById(R.id.sp_bill_city);
        sp_bill_city.setOnItemSelectedListener(this);


        sp_per_city= (Spinner) findViewById(R.id.sp_per_city);
        sp_per_city.setOnItemSelectedListener(this);


//        sp_install_by_list.add("Select");
//        sp_install_by_list.add("Smith");
//        sp_install_by_list.add("John");
//        sp_install_by_list.add("Harry");
//        ArrayAdapter<String> sp_install_by_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sp_install_by_list);
//        sp_pkdicountby_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        sp_install_by.setAdapter(sp_install_by_adapter);


        getlocationmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dashboardIntent= new Intent(CAF_Activity.this,MapsActivity.class);
                dashboardIntent.putExtra("sp_city",city_id);
                dashboardIntent.putExtra("location_id",location_id);
                dashboardIntent.putExtra("area_id",area_id);
                dashboardIntent.putExtra("sub_area_id",sub_area_id);
                dashboardIntent.putExtra("building_id",building_id);
                dashboardIntent.putExtra("tower_id",tower_id);
                dashboardIntent.putExtra("floor_no",floor_no);
               // startActivity(dashboardIntent);
                startActivityForResult(dashboardIntent, 2);
                //getAddressformgooglemap();
               /* try{
                    city = addresses.get(0).getLocality();

                }catch (Exception e){

                }*/
//                int position =0;
//                //sp_city.setSelection(al_city_type.indexOf(city), true);
//                for (int i = 0; i <al_city_type.size() ; i++) {
//                    if(al_city_type.get(i).getItem_name().equalsIgnoreCase(city)){
//                        position = i;
//                    }
//                }

//                sp_city.setSelection(position, true);

            }
        });



        sp_installation_discount_by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try{
                    int selected_name = sp_installation_discount_by.getSelectedItemPosition();
                    installtiondicbyid = al_sp_install_by_type.get(selected_name).getItem_id();
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        sp_pkdicountby.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                try{

                    int selected_name = sp_pkdicountby.getSelectedItemPosition();
                    packdicbyid = al_sp_install_by_type.get(selected_name).getItem_id();


                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        service_type = (Spinner) findViewById(R.id.service_type);
        //billing_scheme = (Switch) findViewById(R.id.billing_scheme);
        new File(Environment.getExternalStorageDirectory() + "/ScanDocument").mkdirs();
        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);

        progress_wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        progress_wheel.setVisibility(View.VISIBLE);

        sp_connection = (Spinner) findViewById(R.id.sp_connection);
        sp_city = (Spinner) findViewById(R.id.sp_city);
        sp_isp = (Spinner) findViewById(R.id.sp_isp);
        sp_location = (Spinner) findViewById(R.id.sp_location);
        sp_area = (Spinner) findViewById(R.id.sp_area);
        sp_sub_area = (Spinner) findViewById(R.id.sp_sub_area);
        sp_building = (Spinner) findViewById(R.id.sp_building);
        sp_tower = (Spinner) findViewById(R.id.sp_tower);
        sp_floor = (Spinner) findViewById(R.id.sp_floor);
        sp_pkg_name = (Spinner) findViewById(R.id.sp_pkg_name);
        sp_channel_manager = (Spinner) findViewById(R.id.sp_channel_manager);
        sp_select_user = (Spinner) findViewById(R.id.sp_select_user);
       // sp_cabling_type = (Spinner) findViewById(R.id.sp_cabling_type);
        sp_router_type = (Spinner) findViewById(R.id.sp_router_type);
        sp_gpon_type = (Spinner) findViewById(R.id.sp_gpon_type);


        tv_displayUser = (MyTextView) findViewById(R.id.tv_displayUser);

        tv_connection= (MyTextView) findViewById(R.id.tv_connection);
        tv_isp= (MyTextView) findViewById(R.id.tv_isp);

        al_connection_type = new ArrayList<Common_Item>();
        al_isp_type = new ArrayList<Common_Item>();
        al_city_type = new ArrayList<Common_Item>();
        al_router_type = new ArrayList<Common_Item>();
        al_service_type = new ArrayList<Common_Item>();
        al_gpon_type = new ArrayList<Common_Item>();
        al_cabling_type = new ArrayList<Common_Item>();
        al_cust_type = new ArrayList<Common_Item>();
        al_user_list = new ArrayList<Common_Item>();
        al_manager_list = new ArrayList<Common_Item>();
        al_location_type = new ArrayList<Common_Item>();
        al_area_type = new ArrayList<Common_Item>();
        al_sub_area_type = new ArrayList<Common_Item>();
        al_building_type = new ArrayList<Common_Item>();
        al_tower_type = new ArrayList<Common_Item>();
        al_floor_type = new ArrayList<Common_Item>();
        alPackage = new ArrayList<Common_Item>();
        allPackage = new ArrayList<Common_Item>();
        al_id_proof_type = new ArrayList<Common_Item>();
        al_nationality_type = new ArrayList<Common_Item>();
        al_entity_type = new ArrayList<Common_Item>();
        al_relationShip_mng_type = new ArrayList<Common_Item>();
        al_key_account_mng_type = new ArrayList<Common_Item>();
        al_billing_cycle_type = new ArrayList<Common_Item>();
        al_sp_install_by_type = new ArrayList<Common_Item>();
        al_type_type = new ArrayList<Common_Item>();
        al_sp_install_by_type = new ArrayList<Common_Item>();
        al_pkdiscount_type = new ArrayList<Common_Item>();
        al_installdiscount_type = new ArrayList<Common_Item>();


        al_user_id = new ArrayList<String>();
        al_channnel_manager = new ArrayList<String>();
        al_images_upload = new ArrayList<String>();

        ll_router_security = (LinearLayout) findViewById(R.id.ll_router_security);
        linear_User = (LinearLayout) findViewById(R.id.linear_User);
        ll_channelmanager = (LinearLayout) findViewById(R.id.linear_Channel_manager);
        linear_paymentmode = (LinearLayout) findViewById(R.id.linear_paymentmode);
        linear_radio_button = (LinearLayout) findViewById(R.id.linear_radio_button);
        linear_cheque_details = (LinearLayout) findViewById(R.id.linear_cheque_details);
        ll_router_details = (LinearLayout) findViewById(R.id.ll_router_details);
        ll_total_charges_selection = (LinearLayout) findViewById(R.id.ll_total_charges_selection);
        ll_gpon_details = (LinearLayout) findViewById(R.id.ll_gpon_details);
        ll_security_details = (LinearLayout) findViewById(R.id.ll_security_details);
        ll_separate_payment = (LinearLayout) findViewById(R.id.ll_separate_payment);
        ll_rs_cheque_dd_no = (LinearLayout) findViewById(R.id.ll_rs_cheque_dd_no);
        ll_rs_cheque_date = (LinearLayout) findViewById(R.id.ll_rs_cheque_date);
        ll_rs_bank_Name = (LinearLayout) findViewById(R.id.ll_rs_bank_Name);
        ll_pm_cd_date = (LinearLayout) findViewById(R.id.ll_pm_cd_date);
        linear_cafform = (LinearLayout) findViewById(R.id.linear_cafform);
        ll_total_charges = (LinearLayout) findViewById(R.id.ll_total_charges);
        ll_billing_cycle = (LinearLayout) findViewById(R.id.ll_billing_cycle);
        ll_post_details= (LinearLayout) findViewById(R.id.ll_post_details);
        ll_natting_details= (LinearLayout) findViewById(R.id.ll_natting_details);

        mac_add_ll = (LinearLayout) findViewById(R.id.mac_add_ll);


        entity_ll = (LinearLayout) findViewById(R.id.entity_ll);
        type_entity_ll = (LinearLayout) findViewById(R.id.type_entity_ll);
        relationShip_mng_ll = (LinearLayout) findViewById(R.id.relationShip_mng_ll);
        key_account_mng_ll = (LinearLayout) findViewById(R.id.key_account_mng_ll);

        et_comment = (EditText)findViewById(R.id.et_comment);
        et_pkgamount = (MaterialEditText) findViewById(R.id.et_pkgamount);
        et_rs_amount = (EditText) findViewById(R.id.et_rs_amount);
        et_installation = (MaterialEditText) findViewById(R.id.et_installation);
        et_installation_discount = (MaterialEditText)findViewById(R.id.et_installation_discount);
        et_pkgamount_discount = (MaterialEditText)findViewById(R.id.et_pkgamountdiscout);
        et_total_amount = (EditText) findViewById(R.id.et_total_amount);

        tv_displaydob = (MyTextView) findViewById(R.id.tv_displaydob);
        tv_displyfilleddate = (MyTextView) findViewById(R.id.tv_displyfilleddate);
        tv_disp_rs_cheque_date = (MyTextView) findViewById(R.id.tv_disp_rs_cheque_date);
        tv_displaychequeDate = (MyTextView) findViewById(R.id.tv_displaychequeDate);

        tv_select_caf = (ImageView) findViewById(R.id.tv_select_caf);
        tv_select_address = (ImageView) findViewById(R.id.tv_select_address);
        tv_select_identity = (ImageView) findViewById(R.id.tv_select_identity);

        tv_disp_caf = (MyTextView) findViewById(R.id.tv_disp_caf);
        tv_disp_address = (MyTextView) findViewById(R.id.tv_disp_address);
        tv_disp_identity = (MyTextView) findViewById(R.id.tv_disp_identity);
        activation_date_iv = (MyTextView) findViewById(R.id.activation_date);
        payment_date_iv = (MyTextView) findViewById(R.id.payment_date);


        bill_addressline = (MaterialEditText) findViewById(R.id.bill_addressline);
       // bill_city = (MaterialEditText) findViewById(R.id.bill_city);
        bill_pincode = (MaterialEditText) findViewById(R.id.bill_pincode);
        per_addressline = (MaterialEditText) findViewById(R.id.per_addressline_);
        //per_city = (MaterialEditText) findViewById(R.id.per_city);
        per_pincode = (MaterialEditText) findViewById(R.id.per_pincode);
        member_loginId = (MaterialEditText) findViewById(R.id.member_loginId);
        gst_no = (MaterialEditText) findViewById(R.id.gst_no);
        mac_add = (MaterialEditText)findViewById(R.id.mac_add);
        et_opening_balance = (EditText)findViewById(R.id.et_opening_balance);
        et_latitude= (MaterialEditText)findViewById(R.id.et_latitude);
        et_longitude= (MaterialEditText)findViewById(R.id.et_longitude);
        copyinstalladdress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Log.e("install",":"+isChecked);
                    if (!et_addressline.getText().toString().equalsIgnoreCase("")){
                        if(!loc_city_id.equals("-1")) {
                        if(pincodePattern.matcher(install_pincode.getText().toString()).matches()){
                                bill_addressline.setText(et_addressline.getText().toString());
                                bill_pincode.setText(install_pincode.getText().toString());
                                sp_bill_city.setSelection(Integer.parseInt(loc_city_id));
                                bill_city_id = loc_city_id;
                            }else{
                                copyinstalladdress.toggle();
                                install_pincode.setError("Please enter installation pincode.");
                            }
                    }else{
                            copyinstalladdress.toggle();
                            Toast.makeText(CAF_Activity.this, "Please select Installation city",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        copyinstalladdress.toggle();
                        et_addressline.setError("Please enter installation address.");
                    }
                }else{
                    Log.e("install",":"+isChecked);
                    bill_addressline.setText("");
                    bill_pincode.setText(""); sp_bill_city.setSelection(0);

                }
            }
        });


        copybillingaddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Log.e("per",":"+isChecked);

                    if (!et_addressline.getText().toString().equalsIgnoreCase("")){
                        if(!loc_city_id.equals("-1")) {
                            if(pincodePattern.matcher(install_pincode.getText().toString()).matches()){
                                per_addressline.setText(et_addressline.getText().toString());
                                per_pincode.setText(install_pincode.getText().toString());
                                sp_per_city.setSelection(Integer.parseInt(loc_city_id));
                                bill_city_id = loc_city_id;
                            }else{
                                copybillingaddress.toggle();
                                install_pincode.setError("Please enter installation pincode.");
                            }
                        }else{
                            copybillingaddress.toggle();
                            Toast.makeText(CAF_Activity.this, "Please select Installation city",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        copybillingaddress.toggle();
                        et_addressline.setError("Please enter installation address.");
                    }
                }else{
                    Log.e("per",":"+isChecked);

                    per_addressline.setText("");
                    per_pincode.setText("");
                    sp_per_city.setSelection(0);
                }
            }
        });



        et_caf_no = (MaterialEditText) findViewById(R.id.et_caf_no);
        et_cash_amt= (EditText) findViewById(R.id.et_cash_amt);
        et_firstname = (MaterialEditText) findViewById(R.id.et_firstname);
        et_middlename = (MaterialEditText) findViewById(R.id.et_middlename);
        et_lastname = (MaterialEditText) findViewById(R.id.et_lastname);
        et_mbnumber = (MaterialEditText) findViewById(R.id.et_mbnumber);
        et_pannumber = (MaterialEditText) findViewById(R.id.et_pannumber);
        et_emailid = (MaterialEditText) findViewById(R.id.et_emailid);
        et_flatno = (MaterialEditText) findViewById(R.id.et_flatno);
        et_pincode = (MaterialEditText) findViewById(R.id.et_pincode);
        et_addressline = (MaterialEditText) findViewById(R.id.et_addressline);
        install_pincode= (MaterialEditText) findViewById(R.id.install_pincode);
        et_router_model = (MaterialEditText) findViewById(R.id.et_router_model);
        et_router_charges = (MaterialEditText) findViewById(R.id.et_router_charges);
        et_router_refund = (MaterialEditText) findViewById(R.id.et_router_refund);
        et_security_charges = (MaterialEditText) findViewById(R.id.et_security_charges);
        et_security_refund = (MaterialEditText) findViewById(R.id.et_security_refund);
        et_security_description = (MaterialEditText) findViewById(R.id.et_security_description);
        et_gpon_model = (MaterialEditText) findViewById(R.id.et_gpon_model);
        et_gpon_charges = (MaterialEditText) findViewById(R.id.et_gpon_charges);
        et_gpon_refund = (MaterialEditText) findViewById(R.id.et_gpon_refund);
        et_post_charges = (MaterialEditText) findViewById(R.id.et_post_charges);
        et_post_refund= (MaterialEditText) findViewById(R.id.et_post_refund);
        et_post_desc= (MaterialEditText) findViewById(R.id.et_post_desc);
        et_natt_charges= (MaterialEditText) findViewById(R.id.et_natt_charges);
        et_natt_refund= (MaterialEditText) findViewById(R.id.et_natt_refund);
        et_natt_desc= (MaterialEditText) findViewById(R.id.et_natt_desc);
        et_rs_cheque_dd_no = (MaterialEditText) findViewById(R.id.et_rs_cheque_dd_no);
        et_rs_bank_Name = (MaterialEditText) findViewById(R.id.et_rs_bank_Name);
        et_chequeno = (MaterialEditText) findViewById(R.id.et_chequeno);
        et_bankName = (MaterialEditText) findViewById(R.id.et_bankName);

        select_gender = (RadioGroup) findViewById(R.id.radioGroup0);
        select_male = (RadioButton) findViewById(R.id.rb_male);
        select_female = (RadioButton) findViewById(R.id.rb_female);
        select_other = (RadioButton) findViewById(R.id.rb_other);

        select_router = (RadioGroup) findViewById(R.id.rg_router_offer);
        rb_chargable = (RadioButton) findViewById(R.id.rb_chargable);
        rb_free = (RadioButton) findViewById(R.id.rb_free);
        rb_refund = (RadioButton) findViewById(R.id.rb_refund);
        rb_chargable.setChecked(true);

        select_security = (RadioGroup) findViewById(R.id.rg_security_refund);
        rb_security_refund = (RadioButton) findViewById(R.id.rb_security_refund);
        rb_nonrefund = (RadioButton) findViewById(R.id.rb_nonrefund);
        rb_security_refund.setChecked(true);

        select_gpon = (RadioGroup) findViewById(R.id.rg_gpon_offer);
        rb_chargable_gpon = (RadioButton) findViewById(R.id.rb_chargable_gpon);
        rb_free_gpon = (RadioButton) findViewById(R.id.rb_free_gpon);
        rb_refund_gpon = (RadioButton) findViewById(R.id.rb_refund_gpon);
        rb_chargable_gpon.setChecked(true);

        select_payment_mode = (RadioGroup) findViewById(R.id.radioGroup2);
        select_cash = (RadioButton) findViewById(R.id.rb_cash);
        select_cheque = (RadioButton) findViewById(R.id.rb_cheque);
       // select_edc = (RadioButton) findViewById(R.id.rb_edc);

        rg_same_separate = (RadioGroup) findViewById(R.id.rg_same_separate);
        rb_same = (RadioButton) findViewById(R.id.rb_same);
        rb_separate = (RadioButton) findViewById(R.id.rb_separate);

        rg_radiobtn_pay_mode = (RadioGroup) findViewById(R.id.rg_radiobtn_pay_mode);
        rb_rs_cash = (RadioButton) findViewById(R.id.rb_rs_cash);
        rb_rs_cheque = (RadioButton) findViewById(R.id.rb_rs_cheque);
        rb_rs_dd = (RadioButton) findViewById(R.id.rb_rs_dd);

        rg_post_refund= (RadioGroup) findViewById(R.id.rg_post_refund);
        rb_post_nonrefund= (RadioButton) findViewById(R.id.rb_post_nonrefund);
        rb_post_refund= (RadioButton) findViewById(R.id.rb_post_refund);

        rg_natt_refund= (RadioGroup) findViewById(R.id.rg_natt_refund);
        rb_natt_nonrefund= (RadioButton) findViewById(R.id.rb_natt_nonrefund);
        rb_natt_refund= (RadioButton) findViewById(R.id.rb_natt_refund);


        radioGroup1= (RadioGroup) findViewById(R.id.radioGroup1);
        rb_own= (RadioButton) findViewById(R.id.rb_own);
        rb_rented= (RadioButton) findViewById(R.id.rb_rented);

        cb_router = (CheckBox) findViewById(R.id.cb_router);
        cb_security = (CheckBox) findViewById(R.id.cb_security);
        cb_gpon = (CheckBox) findViewById(R.id.cb_gpon);
        cb_post_forward = (CheckBox) findViewById(R.id.cb_post_forward);
        cb_natting_charges= (CheckBox) findViewById(R.id.cb_natting_charges);

        btn_submit = (MyButtonView) findViewById(R.id.btn_submit);


        linear_paymentmode.setVisibility(View.VISIBLE);
        linear_radio_button.setVisibility(View.VISIBLE);
        linear_cheque_details.setVisibility(View.GONE);

        Birth_calendar = (ImageView) findViewById(R.id.iv_calender);
        CAf_filled_calendar = (ImageView) findViewById(R.id.iv_calender1);//to display form filled date
        Cheque_calendar = (ImageView) findViewById(R.id.iv_calender3);// to display cheque date
        RS_cheque_calendar = (ImageView) findViewById(R.id.iv_rs_cheque_date);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);

        activation_date = (ImageView) findViewById(R.id.iv_activation_calender);
        payment_date = (ImageView) findViewById(R.id.iv_payment_date_calender);


        emailid = et_emailid.getText().toString();
        mbnumber = et_mbnumber.getText().toString();
        et_rs_amount.setEnabled(false);

        router_type = "C";
        security_type = "R";
        gpon_type = "C";
        post_type = "N";
        natt_type ="N";

        init();

        setAsterisk();
    }

    public void onclick() {

        sp_connection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                et_pkgamount.setText("");
                Common_Item connection_item = (Common_Item) parent.getItemAtPosition(position);
                conn_type_id = connection_item.getItem_id();
                String connection_name = connection_item.getItem_name();
                area_id = "1";
                if (area_id.length() > 0) {
                    new PackageNameDetails().execute(MemberId, conn_type_id, AreaCode, ClientAccessId);
                }

                if (!(connection_name.equals("Normal")) && !(connection_name.equals("<-Select ConnectionType->"))) {
                    mac_add_ll.setVisibility(View.VISIBLE);
                    entity_ll.setVisibility(View.VISIBLE);
                    type_entity_ll.setVisibility(View.VISIBLE);
                    relationShip_mng_ll.setVisibility(View.VISIBLE);
                    key_account_mng_ll.setVisibility(View.VISIBLE);

                } else {
                    mac_add_ll.setVisibility(View.GONE);
                    entity_ll.setVisibility(View.GONE);
                    type_entity_ll.setVisibility(View.GONE);
                    relationShip_mng_ll.setVisibility(View.GONE);
                    key_account_mng_ll.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        sp_isp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item isp_item = (Common_Item) parent.getItemAtPosition(position);
                isp_id = isp_item.getItem_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        sp_loc_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item isp_item = (Common_Item) parent.getItemAtPosition(position);
                loc_city_id = isp_item.getItem_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        sp_bill_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item isp_item = (Common_Item) parent.getItemAtPosition(position);
                bill_city_id = isp_item.getItem_id();
                bill_city_name = isp_item.getItem_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        sp_per_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item isp_item = (Common_Item) parent.getItemAtPosition(position);
                per_city_id = isp_item.getItem_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                city = al_city_type.get(position).getItem_name();
                // TODO Auto-generated method stub
                Common_Item city_item = al_city_type.get(position);
                city_id = city_item.getItem_id();

                if(position!=0){
                    sp_location.setVisibility(View.VISIBLE);
                }else{
                    sp_location.setVisibility(View.GONE);
                }

                if (!city_id.equalsIgnoreCase("-1")) {
                    al_location_type.clear();
                    new CallAddressdetails("Location", city_id).execute();
                } else {

                    al_location_type.clear();
                    if (location_Adapter != null) {
                        location_Adapter.clear();
                        location_Adapter.notifyDataSetChanged();
                    }
                    al_area_type.clear();
                    if (area_Adapter != null) {
                        area_Adapter.clear();
                        area_Adapter.notifyDataSetChanged();
                    }
                    if (sub_area_Adapter != null) {
                        sub_area_Adapter.clear();
                        sub_area_Adapter.notifyDataSetChanged();
                    }
                    if (building_Adapter != null) {
                        building_Adapter.clear();
                        building_Adapter.notifyDataSetChanged();
                    }
                    if (tower_Adapter != null) {
                        tower_Adapter.clear();
                        tower_Adapter.notifyDataSetChanged();
                    }
                    if (floor_Adapter != null) {
                        floor_Adapter.clear();
                        floor_Adapter.notifyDataSetChanged();
                    }
                    boolean value = al_location_type.isEmpty();
                    MyUtils.l("value", ":" + value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        sp_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                Common_Item location_item = al_location_type.get(position);
                location_id = location_item.getItem_id();
                al_area_type.clear();

                if(position!=0){
                    sp_area.setVisibility(View.VISIBLE);
                }else{
                    sp_area.setVisibility(View.GONE);
                }

                if (!location_id.equalsIgnoreCase("-1"))
                    new CallAddressdetails("Area", location_id).execute();

                if (area_Adapter != null) {
                    area_Adapter.clear();
                    area_Adapter.notifyDataSetChanged();
                }
                if (sub_area_Adapter != null) {
                    sub_area_Adapter.clear();
                    sub_area_Adapter.notifyDataSetChanged();
                }
                if (building_Adapter != null) {
                    building_Adapter.clear();
                    building_Adapter.notifyDataSetChanged();
                }
                if (tower_Adapter != null) {
                    tower_Adapter.clear();
                    tower_Adapter.notifyDataSetChanged();
                }
                if (floor_Adapter != null) {
                    floor_Adapter.clear();
                    floor_Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        sp_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item area_item = al_area_type.get(position);
                area_id = area_item.getItem_id();
                al_sub_area_type.clear();
                if(position!=0){
                    sp_sub_area.setVisibility(View.VISIBLE);
                    sp_pkg_name.setVisibility(View.VISIBLE);
                }else{
                    sp_sub_area.setVisibility(View.GONE);
                    sp_pkg_name.setVisibility(View.VISIBLE);
                }
                if (!area_id.equalsIgnoreCase("-1"))
                    new CallAddressdetails("SubArea", area_id).execute();

                if (sub_area_Adapter != null) {
                    sub_area_Adapter.clear();
                    sub_area_Adapter.notifyDataSetChanged();
                }
                if (building_Adapter != null) {
                    building_Adapter.clear();
                    building_Adapter.notifyDataSetChanged();
                }
                if (tower_Adapter != null) {
                    tower_Adapter.clear();
                    tower_Adapter.notifyDataSetChanged();
                }
                if (floor_Adapter != null) {
                    floor_Adapter.clear();
                    floor_Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        sp_sub_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item subarea_item = al_sub_area_type.get(position);
                sub_area_id = subarea_item.getItem_id();
                if(position!=0){
                    sp_building.setVisibility(View.VISIBLE);
                }else{
                    sp_building.setVisibility(View.GONE);
                }
                al_building_type.clear();
                if (!sub_area_id.equalsIgnoreCase("-1"))
                    new CallAddressdetails("Building", sub_area_id).execute();

                if (building_Adapter != null) {
                    building_Adapter.clear();
                    building_Adapter.notifyDataSetChanged();
                }
                if (tower_Adapter != null) {
                    tower_Adapter.clear();
                    tower_Adapter.notifyDataSetChanged();
                }
                if (floor_Adapter != null) {
                    floor_Adapter.clear();
                    floor_Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        sp_building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item building_item = al_building_type.get(position);
                building_id = building_item.getItem_id();
                if(position!=0){
                    sp_tower.setVisibility(View.VISIBLE);
                }else{
                    sp_tower.setVisibility(View.GONE);
                }
                al_tower_type.clear();
                if (!building_id.equalsIgnoreCase("-1"))
                    new CallAddressdetails("Tower", building_id).execute();

                if (tower_Adapter != null) {
                    tower_Adapter.clear();
                    tower_Adapter.notifyDataSetChanged();
                }
                if (floor_Adapter != null) {
                    floor_Adapter.clear();
                    floor_Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        sp_tower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub
                Common_Item tower_item = al_tower_type.get(position);
                tower_id = tower_item.getItem_id();
                if(position!=0){
                    sp_floor.setVisibility(View.VISIBLE);
                }else{
                    sp_floor.setVisibility(View.GONE);
                }
                al_floor_type.clear();
                if (!tower_id.equalsIgnoreCase("-1"))
                    new CallAddressdetails("Floor", tower_id).execute();

                if (floor_Adapter != null) {
                    floor_Adapter.clear();
                    floor_Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        sp_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                Common_Item floor_item = al_floor_type.get(arg2);
                floor_no = floor_item.getItem_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        sp_pkg_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int position, long arg3) {
                // TODO Auto-generated method stub

                Common_Item common_Item = package_Adapter.getItem(sp_pkg_name.getSelectedItemPosition());
                package_name = common_Item.getItem_name();
                package_amount = common_Item.getItem_amount();
                package_id = common_Item.getItem_id();
                //MyUtils.l("Amount",":"+package_amount);


                et_pkgamount.setText(package_amount);
                calculate_amount();

                String selected = parent.getItemAtPosition(position).toString();
//                if (selected.equals("")) {
//                    et_pkgamount.setText("");
//                } else {
//                    et_pkgamount.setText(package_amount);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        sp_channel_manager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub


                Common_Item common_Item = channel_mgr_adapter.getItem(sp_channel_manager.getSelectedItemPosition());
                Log.d("Set Cahnnel Manager", "is:" + common_Item.getRole_Name());
                manager_id = common_Item.getItem_id();
                if (al_user_list != null) {
                    if (al_user_list.size() > 1) {

                        ArrayList<Common_Item> temp_user_list = new ArrayList<Common_Item>();
                        Common_Item userlist1 = new Common_Item(Login_UserId, Username);

                        userlist1.setRole_Name("DSA");
                        temp_user_list.add(userlist1);

                        for (int i = 0; i < al_user_list.size(); i++) {
                            if (manager_id.equalsIgnoreCase(al_user_list.get(i).getChannel_mgr())) {

                                temp_user_list.add(al_user_list.get(i));
                                Log.d("C.M Spinner", ":" + al_user_list.get(i).getRole_Name());
                                Log.d("", "");
                                Log.d("", "");

                            }
                        }
                        if (al_user_list.size() > 1) {
                            user_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, temp_user_list);
                            sp_select_user.setAdapter(user_Adapter);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        /*sp_select_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Common_Item common_Item = user_Adapter.getItem(arg2);
                source_id = common_Item.getItem_id();
                source_name = common_Item.getItem_name();
                MyUtils.l(" Old RoleId", "is:" + RoleId);

                MyUtils.l("RoleName", "is:" + common_Item.getRole_Name());
                Log.d("Source name", ":" + source_name);
                if (common_Item.getRole_Name() != null) {
                    if (common_Item.getRole_Name().equalsIgnoreCase("DSA")) {
                        RoleId = 4;
                    }
                    if (common_Item.getRole_Name().equalsIgnoreCase("DST")) {
                        RoleId = 1;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });*/


       /* sp_cabling_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Common_Item common_Item = cabling_Adapter.getItem(sp_cabling_type.getSelectedItemPosition());
                cabling_id = common_Item.getItem_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });*/


        sp_router_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Common_Item router_item = al_router_type.get(arg2);
                selected_router_id = router_item.getItem_id();


                MyUtils.l("Router_id", ":" + selected_router_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        sp_gpon_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                Common_Item gpon_item = al_router_type.get(arg2);
                selected_gpon_id = gpon_item.getItem_id();

                MyUtils.l("Gpon id", ":" + selected_gpon_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        cb_router.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                RS_totalAmount();
                if (cb_router.isChecked()) {
                    selected_router_type = "Chargable";
                    ll_router_details.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    sp_router_type.setSelection(-1);
                    router_Adapter.notifyDataSetChanged();
                    et_router_charges.setText("");
                    et_router_refund.setEnabled(false);
                    et_router_refund.setText("0.00");
                } else {

                    selected_router_type = "0";
                    ll_router_details.setVisibility(View.GONE);

                    ///ll_total_charges_selection.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.GONE);
                    rb_chargable.setChecked(true);
                    et_router_charges.setText("");
                    et_router_refund.setText("");
                }
            }
        });

        cb_gpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RS_totalAmount();
                if (cb_gpon.isChecked()) {
                    selected_gpon_type = "Chargable";
                    ll_gpon_details.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    et_gpon_charges.setText("");
                    sp_gpon_type.setSelection(-1);
                    et_gpon_refund.setEnabled(false);
                    et_gpon_refund.setText("0.00");
                } else {
                    selected_gpon_id = "0";
                    selected_gpon_type = "0";
                    ll_gpon_details.setVisibility(View.GONE);
                    //ll_total_charges_selection.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.GONE);
                    rb_chargable_gpon.setChecked(true);
                    et_gpon_charges.setText("");
                    et_gpon_refund.setText("");

                }
            }
        });


        cb_security.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                RS_totalAmount();
                if (cb_security.isChecked()) {
                    selected_security_type = "Refund";
                    ll_security_details.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    et_security_charges.setText("");
                    et_security_refund.setText("");
                } else {
                    selected_security_type = "0";
                    ll_security_details.setVisibility(View.GONE);
//                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.GONE);
                    rb_security_refund.setChecked(true);
                    et_security_charges.setText("");
                    et_security_refund.setText("");
                }
            }
        });

        cb_post_forward.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(cb_post_forward.isChecked()){
                    ll_post_details.setVisibility(View.VISIBLE);
                    selected_post_type ="NonRefund";
                    rb_post_nonrefund.setChecked(true);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    et_post_charges.setText("");
                    et_post_refund.setText("");
                }else{
                    ll_post_details.setVisibility(View.GONE);
                    selected_post_type = "0";
                    et_post_charges.setText("");
                    et_post_refund.setText("");
                    ll_total_charges_selection.setVisibility(View.GONE);
                }
            }
        });

        cb_natting_charges.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(cb_natting_charges.isChecked()){
                    ll_natting_details.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    selected_natt_type="NonRefund";
                    rb_natt_nonrefund.setChecked(true);
                    et_natt_charges.setText("");
                    et_natt_refund.setText("");
                }else{
                    ll_natting_details.setVisibility(View.GONE);
                    selected_natt_type="0";
                    et_natt_charges.setText("");
                    et_natt_refund.setText("");
                    ll_total_charges_selection.setVisibility(View.GONE);
                }
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if(!et_caf_no.getText().toString().equals("")){
                 if(!(member_loginId.getText().toString().length() !=6)){
                    if (!conn_type_id.equalsIgnoreCase("-1")) {
                        if (!isp_id.equalsIgnoreCase("-1")) {
                            if(!et_firstname.getText().toString().equals("")) {
                                if(!et_lastname.getText().toString().equals("")) {
                                    if(MobilePattern.matcher(et_mbnumber.getText().toString()).matches()) {
                                        if(!natinality_id.equalsIgnoreCase("-1")) {
                                            if(!id_proof_id.equals("-1")) {
                                                if(!et_pannumber.getText().toString().equals("")) {
                                                    if(!city_id.equals("-1")) {
                                                        if (!location_id.equals("-1")) {
                                                            if (!area_id.equals("-1")) {
                                                                if (!sub_area_id.equals("-1")) {
                                                                    if (!building_id.equals("-1")) {
                                                                        if (!tower_id.equals("-1")) {
                                                                            if (!floor_no.equals("-1")) {
                                                                                if (!et_flatno.getText().toString().equals("")) {
                                                                                    if (pincodePattern.matcher(et_pincode.getText().toString()).matches()) {
                                                                                        if (!et_addressline.getText().toString().equals("")) {
                                                                                            if (!loc_city_id.equals("-1")) {
                                                                                                if (pincodePattern.matcher(install_pincode.getText().toString()).matches()) {
                                                                                                     if ((!bill_addressline.getText().toString().equalsIgnoreCase(""))) {
                                                                                                        if ((!bill_city_id.equals("-1"))) {
                                                                                                            if((pincodePattern.matcher(bill_pincode.getText().toString()).matches())) {
                                                                                                                if (!per_addressline.getText().toString().equals("")) {
                                                                                                                    if(!per_city_id.equals("-1")) {
                                                                                                                     if(pincodePattern.matcher(per_pincode.getText().toString()).matches()){
                                                                                                    if (!package_id.equals("-1")) {
                                                                                                        if (!et_pkgamount.getText().toString().equals("") || !et_pkgamount.getText().toString().equals("0.0")) {

                                                                                                            setMiniCAF_details();
                                                                                                        } else {
                                                                                                            et_pkgamount.setError("Please add Package Amount.");
                                                                                                        }
                                                                                                    } else {
                                                                                                        Toast.makeText(CAF_Activity.this, "Please Select Package.", Toast.LENGTH_LONG).show();
                                                                                                    }
                                                                                                                    }else{
                                                                                                                            per_pincode.setError("Please enter Permanent Address Details or Select Checkbox If Same As Installation Address");
                                                                                                                     }
                                                                                                                    }else{
                                                                                                                        Toast.makeText(CAF_Activity.this, "Please enter Permanent Address Details or Select Checkbox If Same As Installation Address", Toast.LENGTH_LONG).show();
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    per_addressline.setError("Please enter Permanent Address Details or Select Checkbox If Same As Installation Address");
                                                                                                                }
                                                                                                            }else{
                                                                                                                bill_pincode.setError("Please enter Billing Address Details or Select Checkbox If Same As Installation Address.");
                                                                                                            }
                                                                                                        }else{
                                                                                                            Toast.makeText(CAF_Activity.this, "Please enter Billing Address Details or Select Checkbox If Same As Installation Address", Toast.LENGTH_LONG).show();

                                                                                                        }
                                                                                                    } else {
                                                                                                        bill_addressline.setError("Please enter Billing Address Details or Select Checkbox If Same As Installation Address");
                                                                                                    }
                                                                                                }else {
                                                                                                    install_pincode.setError("Please Enter valid Pincode For Installation City.");
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(CAF_Activity.this, "Please Select Installation City.", Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        } else {
                                                                                            et_addressline.setError("Please Either Enter Address or Get Location From Google Map.");
                                                                                        }
                                                                                    } else {
                                                                                        et_pincode.setError("Please Enter Valid Pincode.");
                                                                                    }
                                                                                } else {
                                                                                    et_flatno.setError("Please Enter Flat Number.");
                                                                                }
                                                                            } else {
                                                                                Toast.makeText(CAF_Activity.this, "Please Select Floor.", Toast.LENGTH_LONG).show();
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(CAF_Activity.this, "Please Select Tower.", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(CAF_Activity.this, "Please Select Building.", Toast.LENGTH_LONG).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(CAF_Activity.this, "Please Select Sub Area.", Toast.LENGTH_LONG).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(CAF_Activity.this, "Please Select Area.", Toast.LENGTH_LONG).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(CAF_Activity.this, "Please Select Location.", Toast.LENGTH_LONG).show();
                                                        }
                                                    }else{
                                                        Toast.makeText(CAF_Activity.this, "Please Select City.", Toast.LENGTH_LONG).show();
                                                    }
                                                }else{
                                                    et_pannumber.setError("Please Enter Id Proof Details.");
                                                }
                                            }else{
                                                Toast.makeText(CAF_Activity.this, "Please Select Id Proof.", Toast.LENGTH_LONG).show();
                                            }
                                        }else{
                                            Toast.makeText(CAF_Activity.this, "Please Select Nationality.", Toast.LENGTH_LONG).show();
                                        }
                                    }else{
                                        et_mbnumber.setError("Please Enter Valid Mobile Number..");

                                    }
                                }else{
                                    et_lastname.setError("Please Enter Applicant Last Name.");
                                }
                            }else{
                                et_firstname.setError("Please Enter Applicant Name.");
                            }
                        }else{
                            Toast.makeText(CAF_Activity.this, "Please Select ISP", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(CAF_Activity.this, "Please select Connection Type.", LENGTH_SHORT).show();
                    }
                 }else{
                     member_loginId.setError("Please Enter 6 Characters Member Login Id.");
                 }
             }else{
                 et_caf_no.setError("Please Enter CAF Number.");
             }


                //form_validation(false);

                MyUtils.l("SD Description",":"+dsacaf);
                if(caf_img_path.contains("/")&&address_img_path.contains("/")&&identity_img_path.contains("/")){
                    hm_data.put("CAF_IMAGE",caf_img_path);
                    hm_data.put("ADDRESS_IMAGE",address_img_path);
                    hm_data.put("IDENTITY_IMAGE",identity_img_path);
                    MyUtils.l("Hashmap",":"+hm_data.size());
                }else{
                    if(caf_img_path.contains("/")&&address_img_path.contains("/")){
                        hm_data.put("CAF_IMAGE",caf_img_path);
                        hm_data.put("ADDRESS_IMAGE",address_img_path);
                        MyUtils.l("Hashmap",":"+hm_data.size());
                    }else{
                        if(address_img_path.contains("/")&&identity_img_path.contains("/")){
                            hm_data.put("ADDRESS_IMAGE",address_img_path);
                            hm_data.put("IDENTITY_IMAGE",identity_img_path);
                            MyUtils.l("Hashmap",":"+hm_data.size());
                        }else{
                            if(caf_img_path.contains("/")&&identity_img_path.contains("/")){
                                hm_data.put("CAF_IMAGE",caf_img_path);
                                hm_data.put("IDENTITY_IMAGE",identity_img_path);
                                MyUtils.l("Hashmap",":"+hm_data.size());
                            }else{
                                if(caf_img_path.contains("/")){
                                    hm_data.put("CAF_IMAGE",caf_img_path);
                                    MyUtils.l("Hashmap",":"+hm_data.size());
                                }else{
                                    if(identity_img_path.contains("/")){
                                        hm_data.put("IDENTITY_IMAGE",identity_img_path);
                                        MyUtils.l("Hashmap",":"+hm_data.size());
                                    }else{
                                        if(address_img_path.contains("/")){
                                            hm_data.put("ADDRESS_IMAGE",address_img_path);
                                            MyUtils.l("Hashmap",":"+hm_data.size());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                MyUtils.l("Hashmap Data",":"+hm_data.size());
                hm_upload_image.put(et_caf_no.getText().toString(),hm_data);
                MyUtils.l("Hashmap upload data",":"+hm_upload_image.size());

                UploadPictureTask uploadfile = new UploadPictureTask(hm_upload_image);
                uploadfile.execute();
            }

        });


        tv_select_caf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show_scan_option(CAF_Activity.this,"Select Option","Select Option",false);
                /* Intent startCustomCameraIntent = new Intent(CAF_Activity.this, CameraActivity.class);
                startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
                */
                selectImage();
                is_caf_select = true;
            }
        });

        tv_select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show_scan_option(CAF_Activity.this,"Select Option","Select Option",false);
                selectImage();
                is_address_scan = true;
            }
        });

        tv_select_identity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                is_identity_scan = true;
            }
        });


        select_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_male:
                        gender_value = "M";
                        selected_gender = ((RadioButton) findViewById(select_gender.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected Gender", ":" + selected_gender);
                        break;

                    case R.id.rb_female:
                        gender_value = "F";
                        selected_gender = ((RadioButton) findViewById(select_gender.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected Gender", ":" + selected_gender);

                    case R.id.rb_other:
                        gender_value = "O";
                        selected_gender = ((RadioButton) findViewById(select_gender.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected Gender", ":" + selected_gender);

                    default:
                            gender_value = "M";
                            selected_gender = ((RadioButton) findViewById(select_gender.getCheckedRadioButtonId())).getText().toString();
                            MyUtils.l("Selected Gender", ":" + selected_gender);
                }
            }
        });

        select_router.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_chargable:
                        router_type = "C";

                        selected_router_type = ((RadioButton) findViewById(select_router.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected router_type", ":" + selected_router_type);
                        et_router_charges.setEnabled(true);
                        et_router_charges.setText("");
                        et_router_refund.setEnabled(false);
                        et_router_refund.setText("0.00");
                        router_refund = Double.parseDouble("0.0");
                        break;
                    case R.id.rb_free:
                        router_type = "F";
                        selected_router_type = ((RadioButton) findViewById(select_router.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected router_type", ":" + selected_router_type);
                        et_router_charges.setEnabled(false);
                        et_router_charges.setText("0.00");
                        et_router_refund.setEnabled(false);
                        et_router_refund.setText("0.00");
                        router_charges = Double.parseDouble("0.0");
                        rs_router_amount = Double.valueOf(router_charges);
                        router_refund = Double.parseDouble("0.0");
                        break;
                    case R.id.rb_refund:
                        router_type = "R";
                        selected_router_type = ((RadioButton) findViewById(select_router.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected router_type", ":" + selected_router_type);
                        et_router_charges.setEnabled(true);
                        et_router_charges.setText("");
                        et_router_refund.setEnabled(true);
                        et_router_refund.setText("");
                }
            }
        });


        select_gpon.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_chargable_gpon:
                        gpon_type = "C";

                        selected_gpon_type = ((RadioButton) findViewById(select_gpon.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected gpon_type", ":" + selected_gpon_type);
                        et_gpon_charges.setEnabled(true);
                        et_gpon_charges.setText("");
                        et_gpon_refund.setEnabled(false);
                        et_gpon_refund.setText("0.00");
                        gpon_refund = Double.parseDouble("0.0");
                        break;
                    case R.id.rb_free_gpon:
                        gpon_type = "F";
                        selected_gpon_type = ((RadioButton) findViewById(select_gpon.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected gpon_type", ":" + selected_gpon_type);
                        et_gpon_charges.setEnabled(false);
                        et_gpon_charges.setText("0.00");
                        et_gpon_refund.setEnabled(false);
                        et_gpon_refund.setText("0.00");
                        router_charges = Double.parseDouble("0.0");
                        rs_gpon_amount = Double.valueOf(router_charges);
                        gpon_refund = Double.parseDouble("0.0");
                        break;
                    case R.id.rb_refund_gpon:
                        gpon_type = "R";
                        selected_gpon_type = ((RadioButton) findViewById(select_gpon.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected router_type", ":" + selected_gpon_type);
                        et_gpon_charges.setEnabled(true);
                        et_gpon_charges.setText("");
                        et_gpon_refund.setEnabled(true);
                        et_gpon_refund.setText("");
                }
            }
        });


        rg_post_refund.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_post_refund:
                        post_type = "R";
                        selected_post_type = ((RadioButton) findViewById(rg_post_refund.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected selected_post_type", ":" + selected_post_type);
                        et_post_charges.setEnabled(true);
                        et_post_charges.setText("");
                        et_post_refund.setEnabled(true);
                        et_post_refund.setText("");

                        break;
                    case R.id.rb_post_nonrefund:
                        post_type = "N";
                        selected_post_type = ((RadioButton) findViewById(rg_post_refund.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected selected_post_type", ":" + selected_post_type);
                        et_post_charges.setEnabled(true);
                        et_post_refund.setEnabled(false);
                        et_post_refund.setText("0.00");
                        post_refund = Double.parseDouble("0.0");
                }
            }
        });

        rg_natt_refund.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_natt_refund:
                        natt_type = "R";
                        selected_natt_type = ((RadioButton) findViewById(rg_natt_refund.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected selected_natt_type", ":" + selected_natt_type);
                        et_natt_charges.setEnabled(true);
                        et_natt_charges.setText("");
                        et_natt_refund.setEnabled(true);
                        et_post_refund.setText("");

                        break;
                    case R.id.rb_natt_nonrefund:
                        natt_type = "N";
                        selected_natt_type = ((RadioButton) findViewById(rg_natt_refund.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected selected_natt_type", ":" + selected_natt_type);
                        et_natt_charges.setEnabled(true);
                        et_natt_refund.setEnabled(false);
                        et_natt_refund.setText("0.00");
                        natt_refund = Double.parseDouble("0.0");
                }
            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_own:
                        PrimisesType = false;

                        break;
                    case R.id.rb_rented:
                        PrimisesType = true;
                }
            }
        });

        select_security.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_security_refund:
                        security_type = "R";
                        selected_security_type = ((RadioButton) findViewById(select_security.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected router_type", ":" + selected_security_type);
                        et_security_charges.setEnabled(true);
                        et_security_charges.setText("");
                        et_security_refund.setEnabled(true);
                        et_security_refund.setText("");

                        break;
                    case R.id.rb_nonrefund:
                        security_type = "N";
                        selected_security_type = ((RadioButton) findViewById(select_security.getCheckedRadioButtonId())).getText().toString();
                        MyUtils.l("Selected router_type", ":" + selected_security_type);
                        et_security_charges.setEnabled(true);
                        et_security_refund.setEnabled(false);
                        et_security_refund.setText("0.00");
                        security_refund = Double.parseDouble("0.0");
                }
            }
        });

        select_payment_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_cash:
                        // do operations specific to this selection
                        linear_cheque_details.setVisibility(View.GONE);
                        selected_mode = "CS";
                        MyUtils.l("Selected mode", ":" + selected_mode);
                        break;

                    case R.id.rb_cheque:
                        // do operations specific to this selection
                        linear_cheque_details.setVisibility(View.VISIBLE);
                        et_chequeno.setEnabled(true);
                        et_chequeno.setFocusableInTouchMode(true);
                        et_chequeno.requestFocus();
                        selected_mode = "CQ";

                        MyUtils.l("Selected mode", ":" + selected_mode);
                        //et_bankName.setEnabled(true);
                        //et_bankName.requestFocus();
                        break;

                    /*case R.id.rb_edc:
                        // do operations specific to this selection
                        linear_cheque_details.setVisibility(View.GONE);
                        selected_mode = "EDC";

                        if (is_card_allowed) {
                            if (form_validation(true)) {
                                Intent eze_intent = new Intent(CAF_Activity.this, EzetapActivity.class);
                                eze_intent.putExtra(MyUtils.DSA_OBJECT, dsacaf);
                                startActivity(eze_intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            } else {
                                select_edc.setChecked(false);
                            }
                        } else {
                            //AlertsBoxFactory.showAlert("Credit/Debit card payment is not allowed.", CAF_Activity.this);
                            DialogUtils.show_dialog(CAF_Activity.this, "Confirmation", "Credit/Debit card payment is not allowed.", false);
                        }
                        MyUtils.l("Selected mode", ":" + selected_mode);

                        break;*/
                }
            }
        });

        rg_same_separate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_same:
                        ll_separate_payment.setVisibility(View.GONE);
                        rs_pay_mode = "Same";

                        break;

                    case R.id.rb_separate:
                        ll_separate_payment.setVisibility(View.VISIBLE);
                        rs_pay_mode = "Separate";
                        break;
                }
            }
        });


        rg_radiobtn_pay_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rb_rs_cash:
                        selected_rs_pay_type = "CS";
                        ll_rs_cheque_dd_no.setVisibility(View.GONE);
                        ll_pm_cd_date.setVisibility(View.GONE);
                        MyUtils.l("selected_rs_pay_type", ":" + selected_rs_pay_type);
                        break;
                    case R.id.rb_rs_cheque:
                        selected_rs_pay_type = "CQ";
                        ll_rs_cheque_dd_no.setVisibility(View.VISIBLE);
                        ll_pm_cd_date.setVisibility(View.VISIBLE);
                        MyUtils.l("selected_rs_pay_type", ":" + selected_rs_pay_type);
                        break;
                    case R.id.rb_rs_dd:
                        selected_rs_pay_type = "DD";
                        ll_rs_cheque_dd_no.setVisibility(View.VISIBLE);
                        ll_pm_cd_date.setVisibility(View.VISIBLE);
                        MyUtils.l("selected_rs_pay_type", ":" + selected_rs_pay_type);
                        break;
                }
            }
        });

        et_router_charges.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                    if (s.length() > 0) {
                        router_amount = Double.valueOf(s.toString());
                    } else {
                        router_amount = 0.0;
                    }
                    add_total_amount(total_amount);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });
        et_gpon_charges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                    if (s.length() > 0) {
                        gpon_amount = Double.valueOf(s.toString());
                    } else {
                        gpon_amount = 0.0;
                    }
                    add_total_amount(total_amount);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_post_charges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    post_amount = Double.valueOf(s.toString());
                } else {
                    post_amount = 0.0;
                }
                add_total_amount(total_amount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_natt_charges.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    natt_amount = Double.valueOf(s.toString());
                } else {
                    natt_amount = 0.0;
                }
                add_total_amount(total_amount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        et_security_charges.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    security_amount = Double.valueOf(s.toString());
                } else {
                    security_amount = 0.0;
                }
                add_total_amount(total_amount);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });

        et_installation.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    installation_amount = Double.valueOf(s.toString());
                    Double pkg_amount = Double.valueOf(et_pkgamount.getText().toString());

                    if(total_amount < 0 ){
                        Toast.makeText(CAF_Activity.this,"Please add installation amount.",Toast.LENGTH_LONG).show();
                    }
                } else {
                    installation_amount = 0.0;
                }
                calculate_amount();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                calculate_amount();
                //add_total_amount(total_amount);
            }
        });


        et_installation_discount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    installation_discount_amount = Double.valueOf(s.toString());
                    Double pkg_amount = Double.valueOf(et_pkgamount.getText().toString());

                    if(installation_discount_amount >= pkg_amount){
                        Toast.makeText(CAF_Activity.this,"Installation discount must be less than your package amount.",Toast.LENGTH_LONG).show();
                    }
                }else {
                    installation_discount_amount = 0.0;
                }
                calculate_amount();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                add_total_amount(total_amount);
            }
        });


        et_pkgamount_discount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    pkg_discount_amount = Double.valueOf(s.toString());
                    Double pkg_amount = Double.valueOf(et_pkgamount.getText().toString());

                    if(pkg_discount_amount >= pkg_amount){
                        Toast.makeText(CAF_Activity.this,"Package discount must be less than your package amount.",Toast.LENGTH_LONG).show();
                    }
                } else {
                    pkg_discount_amount = 0.0;
                }
                calculate_amount();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(s.length() == 0){
                    calculate_amount();
                }
                add_total_amount(total_amount);
            }
        });


        Birth_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(BIRTH_DATE_DISPLAY);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR) - 18;
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Birth_date_Display();

        CAf_filled_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(FORM_FILLED_DATE);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Caf_Filled_date_Display();

        Cheque_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(CHEQUE_DATE);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Cheque_date_Display();


        activation_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(ACTIVATION_DATE_final);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        activation_date_Display();


        payment_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(PAYMENT_DATE_FINAL);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        payment_date_Display();


        RS_cheque_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(RS_CHEQUE_DATE);
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        RS_Cheque_date_Display();

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("Login", 0);
                preferences.edit().remove("UserName").commit();
                preferences.edit().remove("Password").commit();
                preferences.edit().remove("UserId").commit();
                preferences.edit().remove("ClientAccessID").commit();
                preferences.edit().remove(MyUtils.APP_REG).commit();
                //preferences.edit().clear().commit();
                CAF_Activity.this.finish();
                Intent i = new Intent(CAF_Activity.this, DashboardActivity.class);
                startActivity(i);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceAsColor")
    public void init(){
        // Table hardcodeed for Postpaid connection
        ///Dynamic Table row adding
        TableLayout postpaidamttabviwe = (TableLayout) findViewById(R.id.postpaidamttabviwe);
        TableRow titles = new TableRow(this);
        MyTextView select = new MyTextView(this);
        select.setText(" Select ");
        select.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        select.setTextColor(R.color.colorPrimaryDark);
        titles.addView(select);
        MyTextView head = new MyTextView(this);
        head.setText(" Head ");
        head.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        head.setTextColor(R.color.colorPrimaryDark);
        titles.addView(head);
        MyTextView amount = new MyTextView(this);
        amount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        amount.setText(" Amount ");
        amount.setTextColor(R.color.colorPrimaryDark);
        titles.addView(amount);
        MyTextView pay_amount = new MyTextView(this);
        pay_amount.setText(" Pay Amount ");
        pay_amount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        pay_amount.setTextColor(R.color.colorPrimaryDark);
        titles.addView(pay_amount);
        postpaidamttabviwe.addView(titles);

        //TableLayout postpaidamttabviwe = (TableLayout) findViewById(R.id.postpaidamttabviwe);
        TableRow row1 = new TableRow(this);
        CheckBox security_deposit_checkbox = new CheckBox(this);
        //security_deposit_checkbox.setText();
        security_deposit_checkbox.setChecked(true);
        security_deposit_checkbox.setGravity(Gravity.CENTER);
        //security_deposit_checkbox.setBackgroundColor(R.color.colorPrimaryDark);
        row1.addView(security_deposit_checkbox);
        MyTextView security_deposit_textview = new MyTextView(this);
        security_deposit_textview.setText("Security Deposit");
        security_deposit_textview.setGravity(Gravity.CENTER);
        security_deposit_textview.setTextColor(R.color.colorPrimaryDark);
        row1.addView(security_deposit_textview);
        MyTextView security_deposit_amt = new MyTextView(this);
        security_deposit_amt.setText("0.00");
        security_deposit_amt.setGravity(Gravity.CENTER);
        security_deposit_amt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        security_deposit_amt.setTextColor(R.color.colorPrimaryDark);
        row1.addView(security_deposit_amt);
        MyEditTextView security_deposit_edit = new MyEditTextView(this);
        security_deposit_edit.setText(security_deposit_amt.getText());
        security_deposit_edit.setTextColor(R.color.colorPrimaryDark);
        security_deposit_edit.setGravity(Gravity.CENTER);
        security_deposit_edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row1.addView(security_deposit_edit);
        postpaidamttabviwe.addView(row1);


        TableRow row2 = new TableRow(this);
        CheckBox recurring_charges_checkbox = new CheckBox(this);
        //security_deposit_checkbox.setText();
        recurring_charges_checkbox.setChecked(true);
        recurring_charges_checkbox.setGravity(Gravity.CENTER);
        //security_deposit_checkbox.setBackgroundColor(R.color.colorPrimaryDark);
        row2.addView(recurring_charges_checkbox);
        MyTextView recurring_charges_textview = new MyTextView(this);
        recurring_charges_textview.setText("Recurring Charges");
        recurring_charges_textview.setGravity(Gravity.CENTER);
        recurring_charges_textview.setTextColor(R.color.colorPrimaryDark);
        row2.addView(recurring_charges_textview);
        MyTextView recurring_charges_amt = new MyTextView(this);
        recurring_charges_amt.setText("100.00");
        recurring_charges_amt.setGravity(Gravity.CENTER);
        recurring_charges_amt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        recurring_charges_amt.setTextColor(R.color.colorPrimaryDark);
        row2.addView(recurring_charges_amt);
        MyEditTextView recurring_charges_edit = new MyEditTextView(this);
        recurring_charges_edit.setText(recurring_charges_amt.getText());
        recurring_charges_edit.setTextColor(R.color.colorPrimaryDark);
        recurring_charges_edit.setGravity(Gravity.CENTER);
        recurring_charges_edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row2.addView(recurring_charges_edit);
        postpaidamttabviwe.addView(row2);


        TableRow row3 = new TableRow(this);
        CheckBox installation_charges_checkbox = new CheckBox(this);
        //security_deposit_checkbox.setText();
        installation_charges_checkbox.setChecked(true);
        installation_charges_checkbox.setGravity(Gravity.CENTER);
        //security_deposit_checkbox.setBackgroundColor(R.color.colorPrimaryDark);
        row3.addView(installation_charges_checkbox);
        MyTextView installation_charges_textview = new MyTextView(this);
        installation_charges_textview.setText("Installation Charges");
        installation_charges_textview.setGravity(Gravity.CENTER);
        installation_charges_textview.setTextColor(R.color.colorPrimaryDark);
        row3.addView(installation_charges_textview);
        MyTextView installation_charges_amt = new MyTextView(this);
        installation_charges_amt.setText("0.00");
        installation_charges_amt.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            installation_charges_amt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        installation_charges_amt.setTextColor(R.color.colorPrimaryDark);
        row3.addView(installation_charges_amt);
        MyEditTextView installation_charges_edit = new MyEditTextView(this);
        installation_charges_edit.setText(installation_charges_amt.getText());
        installation_charges_edit.setTextColor(R.color.colorPrimaryDark);
        installation_charges_edit.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            installation_charges_edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        row3.addView(installation_charges_edit);
        postpaidamttabviwe.addView(row3);


        TableRow row4 = new TableRow(this);
        CheckBox registration_charges_checkbox = new CheckBox(this);
        //security_deposit_checkbox.setText();
        registration_charges_checkbox.setChecked(true);
        registration_charges_checkbox.setGravity(Gravity.CENTER);
        //security_deposit_checkbox.setBackgroundColor(R.color.colorPrimaryDark);
        row4.addView(registration_charges_checkbox);
        MyTextView registration_charges_textview = new MyTextView(this);
        registration_charges_textview.setText("Registration Charges");
        registration_charges_textview.setGravity(Gravity.CENTER);
        registration_charges_textview.setTextColor(R.color.colorPrimaryDark);
        row4.addView(registration_charges_textview);
        MyTextView registration_charges_amt = new MyTextView(this);
        registration_charges_amt.setText("0.00");
        registration_charges_amt.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            registration_charges_amt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        registration_charges_amt.setTextColor(R.color.colorPrimaryDark);
        row4.addView(registration_charges_amt);
        MyEditTextView registration_charges_edit = new MyEditTextView(this);
        registration_charges_edit.setText(registration_charges_amt.getText());
        registration_charges_edit.setTextColor(R.color.colorPrimaryDark);
        registration_charges_edit.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            registration_charges_edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        row4.addView(registration_charges_edit);
        postpaidamttabviwe.addView(row4);


/////////////////////////for dynamic table view
//        for (int i = 0; i < 25; i++) {
//            TableRow tbrow = new TableRow(this);
//            TextView t1v = new TextView(this);
//            t1v.setText("" + i);
//            t1v.setTextColor(Color.WHITE);
//            t1v.setGravity(Gravity.CENTER);
//            tbrow.addView(t1v);
//            TextView t2v = new TextView(this);
//            t2v.setText("Product " + i);
//            t2v.setTextColor(Color.WHITE);
//            t2v.setGravity(Gravity.CENTER);
//            tbrow.addView(t2v);
//            TextView t3v = new TextView(this);
//            t3v.setText("Rs." + i);
//            t3v.setTextColor(Color.WHITE);
//            t3v.setGravity(Gravity.CENTER);
//            tbrow.addView(t3v);
//            TextView t4v = new TextView(this);
//            t4v.setText("" + i * 15 / 32 * 10);
//            t4v.setTextColor(Color.WHITE);
//            t4v.setGravity(Gravity.CENTER);
//            tbrow.addView(t4v);
//            stk.addView(tbrow);
//        }


    }

    public void setAsterisk(){

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String connection = "Connection Type*";
        SpannableString redSpannable= new SpannableString(connection);
        redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 15, connection.length(), 0);
        builder.append(redSpannable);
        tv_connection.setText(builder, TextView.BufferType.SPANNABLE);

        SpannableStringBuilder builder1 = new SpannableStringBuilder();
        String isp = "Select ISP*";
        SpannableString redSpannable1= new SpannableString(isp);
        redSpannable1.setSpan(new ForegroundColorSpan(Color.RED), 10, isp.length(), 0);
        builder1.append(redSpannable1);
        tv_isp.setText(builder1, TextView.BufferType.SPANNABLE);


    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void add_total_amount(double total_amount) {

        rs_total_amount = total_amount +router_amount + security_amount + gpon_amount +post_amount+natt_amount;
        et_total_amount.setText(""+rs_total_amount);
        et_rs_amount.setText(""+rs_total_amount);
    }

    public void RS_totalAmount() {
        if (cb_router.isChecked() || cb_security.isChecked() || cb_gpon.isChecked() ||cb_post_forward.isChecked()|| cb_natting_charges.isChecked()) {
            ll_total_charges_selection.setVisibility(View.VISIBLE);
            ll_separate_payment.setVisibility(View.GONE);
            if (rb_separate.isChecked()) {
                ll_separate_payment.setVisibility(View.VISIBLE);
            } else {
                ll_separate_payment.setVisibility(View.GONE);
            }
        } else {
            ll_total_charges_selection.setVisibility(View.GONE);
            ll_separate_payment.setVisibility(View.GONE);
            et_rs_cheque_dd_no.setText("");
            et_rs_bank_Name.setText("");
            et_router_model.setText("");
            et_gpon_model.setText("");
            et_post_desc.setText("");
            et_natt_desc.setText("");
            et_natt_charges.setText("");
            et_security_description.setText("");
            rb_same.setChecked(true);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class CallWebservice extends AsyncTask<String, Void, Void> {
        String rslt = "";
        String response = "";

        SharedPreferences sharedPreferences;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progress_wheel.setVisibility(View.VISIBLE);
            linear_cafform.setVisibility(View.GONE);
        }

        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub

            MiniCafDataSOAP sampleSOAP = new MiniCafDataSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.SOAP_URL), getString(R.string.METHOD_MINIDATA));

            try {
                // pref=getApplicationContext().getSharedPreferences("Login", 0);
                sharedPreferences_name = getString(R.string.shared_preferences_name);
                sharedPreferences = getApplicationContext().getSharedPreferences(sharedPreferences_name, 0);
                System.out.println("client Access" + pref.getString("ClientAccessID", ""));
                System.out.println();

                Login_UserId = pref.getString("USER_ID", "");
                Username = pref.getString("USER_NAME", "");
                password = pref.getString("USER_PASSWORD","");
                clientaccess_id = pref.getString("CliectAccessId", "");

                MyUtils.l("Clientaccess_id", ":" + clientaccess_id);

                rslt=sampleSOAP.FirstInstanceMINICAFData(/*MyUtils.CLIENTACCESSID*/String.valueOf("CM000196UT"), 6);
                response = sampleSOAP.getJsonResponse();
            } catch (SocketTimeoutException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            progress_wheel.setVisibility(View.GONE);
            linear_cafform.setVisibility(View.VISIBLE);
            if (rslt.equalsIgnoreCase("ok")) {
                if (response.length() > 0) {
                    parse_response(response);
                } else {
                    DialogUtils.show_dialog(CAF_Activity.this, "Confirmation", "We are unable fetch data from server" + "\n" + "Please try again !", false);
                }
            } else {
                DialogUtils.show_dialog(CAF_Activity.this, "Confirmation", "We are unable fetch data from server" + "\n" + "Please try again !", false);
            }
            super.onPostExecute(result);
        }
    }

    public void parse_response(String json) {
        try {
            JSONObject main_json = new JSONObject(json);
            if (main_json.has("NewDataSet")) {
                JSONObject new_datset_json = main_json.getJSONObject("NewDataSet");

                if (new_datset_json.has("ConnectionTypeTable")) {
                    if (new_datset_json.get("ConnectionTypeTable") instanceof JSONObject) {
                        JSONObject conn_type_obj = new_datset_json.getJSONObject("ConnectionTypeTable");
                        conn_type_id = conn_type_obj.optString("ConnectionTypeId");
                        String conn_type_name = conn_type_obj.optString("ConnectionTypeName");
                        Common_Item common_Item = new Common_Item(conn_type_id, conn_type_name);
                        al_connection_type.add(common_Item);
                    }

                    if (new_datset_json.get("ConnectionTypeTable") instanceof JSONArray) {
                        JSONArray connection_type_array = new_datset_json.getJSONArray("ConnectionTypeTable");
                        for (int i = 0; i < connection_type_array.length(); i++) {
                            JSONObject connection_obj = connection_type_array.getJSONObject(i);
                            String conn_type_id = connection_obj.optString("ConnectionTypeId");
                            String conn_type_name = connection_obj.optString("ConnectionTypeName");
                            Common_Item common_Item = new Common_Item(conn_type_id, conn_type_name);
                            al_connection_type.add(common_Item);
                        }
                    }

                    connection_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_connection_type);

                    sp_connection.setAdapter(connection_Adapter);
                }


                if (new_datset_json.has("ISPTable")) {
                    if (new_datset_json.get("ISPTable") instanceof JSONObject) {
                        JSONObject isp_obj = new_datset_json.getJSONObject("ISPTable");
                        String isp_id = isp_obj.optString("ISPID");
                        String isp_name = isp_obj.optString("Column1");
                        Common_Item isp = new Common_Item(isp_id, isp_name);
                        al_isp_type.add(isp);
                    }

                    if (new_datset_json.get("ISPTable") instanceof JSONArray) {
                        JSONArray city_array = new_datset_json.getJSONArray("ISPTable");
                        for (int i = 0; i < city_array.length(); i++) {
                            JSONObject isp_obj = city_array.getJSONObject(i);
                            String isp_id = isp_obj.optString("ISPID");
                            String isp_name = isp_obj.optString("Column1");
                            Common_Item isp = new Common_Item(isp_id, isp_name);
                            al_isp_type.add(isp);
                        }
                    }

                    isp_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_isp_type);
                    sp_isp.setAdapter(isp_Adapter);
                }


                if (new_datset_json.has("CityTable")) {
                    if (new_datset_json.get("CityTable") instanceof JSONObject) {
                        JSONObject city_obj = new_datset_json.getJSONObject("CityTable");
                        String city_id = city_obj.optString("CityId");
                        String city_name = city_obj.optString("Column1");
                        Common_Item city = new Common_Item(city_id, city_name);
                        al_city_type.add(city);
                    }

                    if (new_datset_json.get("CityTable") instanceof JSONArray) {
                        JSONArray city_array = new_datset_json.getJSONArray("CityTable");
                        for (int i = 0; i < city_array.length(); i++) {
                            JSONObject city_obj = city_array.getJSONObject(i);
                            String city_id = city_obj.optString("CityId");
                            String city_name = city_obj.optString("Column1");
                            Common_Item city = new Common_Item(city_id, city_name);
                            al_city_type.add(city);
                        }
                    }

                    city_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_city_type);
                    sp_city.setAdapter(city_Adapter);
                    sp_loc_city.setAdapter(city_Adapter);
                    sp_bill_city.setAdapter(city_Adapter);
                    sp_per_city.setAdapter(city_Adapter);

                    city_id= al_city_type.get(0).getItem_id();
                    loc_city_id= al_city_type.get(0).getItem_id();
                    bill_city_id = al_city_type.get(0).getItem_id();
                    per_city_id = al_city_type.get(0).getItem_id();



                }

                if (new_datset_json.has("RouterType")) {
                    if (new_datset_json.get("RouterType") instanceof JSONObject) {
                        JSONObject router_obj = new_datset_json.getJSONObject("RouterType");
                        String router_id = router_obj.optString("RouterId");
                        String router_name = router_obj.optString("Column1");
                        Common_Item router = new Common_Item(router_id, router_name);
                        al_router_type.add(router);
                    }

                    if (new_datset_json.get("RouterType") instanceof JSONArray) {
                        JSONArray city_array = new_datset_json.getJSONArray("RouterType");
                        for (int i = 0; i < city_array.length(); i++) {
                            JSONObject city_obj = city_array.getJSONObject(i);
                            String router_id = city_obj.optString("RouterId");
                            String router_name = city_obj.optString("Column1");
                            Common_Item router = new Common_Item(router_id, router_name);
                            al_router_type.add(router);
                            MyUtils.l("router Arrylist", ":" + al_router_type);
                        }
                    }
                    router_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_router_type);
                    sp_router_type.setAdapter(router_Adapter);
                    selected_router_id = al_router_type.get(0).getItem_id();
                }


                if (new_datset_json.has("ServiceType")) {
                    if (new_datset_json.get("ServiceType") instanceof JSONObject) {
                        JSONObject router_obj = new_datset_json.getJSONObject("ServiceType");
                        String router_id = router_obj.optString("SID");
                        String router_name = router_obj.optString("ServiceType");
                        Common_Item service = new Common_Item(router_id, router_name);
                        al_service_type.add(service);
                    }

                    if (new_datset_json.get("ServiceType") instanceof JSONArray) {
                        JSONArray city_array = new_datset_json.getJSONArray("ServiceType");
                        for (int i = 0; i < city_array.length(); i++) {
                            JSONObject city_obj = city_array.getJSONObject(i);
                            String router_id = city_obj.optString("SID");
                            String router_name = city_obj.optString("ServiceType");
                            Common_Item service = new Common_Item(router_id, router_name);
                            al_service_type.add(service);
                            MyUtils.l("ServiceType Arrylist", ":" + al_service_type.size());
                        }
                    }
                    service_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_service_type);
                    service_type.setAdapter(service_Adapter);
                }



                if (new_datset_json.has("GponType")) {
                    if (new_datset_json.get("GponType") instanceof JSONObject) {
                        JSONObject gpon_obj = new_datset_json.getJSONObject("GponType");
                        String gpon_id = gpon_obj.optString("RouterId");
                        String gpon_name = gpon_obj.optString("Column1");
                        Common_Item gpon = new Common_Item(gpon_id, gpon_name);
                        al_gpon_type.add(gpon);
                    }

                    if (new_datset_json.get("GponType") instanceof JSONArray) {
                        JSONArray gpon_array = new_datset_json.getJSONArray("GponType");
                        for (int i = 0; i < gpon_array.length(); i++) {
                            JSONObject gpon_obj = gpon_array.getJSONObject(i);
                            String gpon_id = gpon_obj.optString("RouterId");
                            String gpon_name = gpon_obj.optString("Column1");
                            Common_Item gpon = new Common_Item(gpon_id, gpon_name);
                            al_gpon_type.add(gpon);
                        }
                    }
                    MyUtils.l("Gpon Arrylist", ":" + al_gpon_type.size());
                    gpon_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_gpon_type);
                    sp_gpon_type.setAdapter(gpon_Adapter);
                    selected_gpon_id = al_gpon_type.get(0).getItem_id();

                }


                if (new_datset_json.get("UserRole") instanceof JSONObject) {
                    JSONObject user_role_obj = new_datset_json.getJSONObject("UserRole");
                    String user = user_role_obj.optString("Act");
                    login_user_role = user;
                    if (user.equals("Head")) {
                        tv_displayUser.setText("Head");
                        role_name = tv_displayUser.getText().toString();
                        ll_channelmanager.setVisibility(View.VISIBLE);
                        //ll_user.setVisibility(View.VISIBLE);
                    }

                    if (user.equals("Channel Manager")) {
                        tv_displayUser.setText("Channel Manager");
                        ll_channelmanager.setVisibility(View.GONE);
                        //linear_User.setVisibility(View.VISIBLE);
                    }

                    if (user.equals("Direct")) {
                        tv_displayUser.setText("Direct");
                        ll_channelmanager.setVisibility(View.GONE);
                        //linear_User.setVisibility(View.VISIBLE);
                    }
                }

                if (new_datset_json.has("ChannelManager")) {
                    JSONArray manager_array = new_datset_json.getJSONArray("ChannelManager");
                    for (int i = 0; i < manager_array.length(); i++) {
                        JSONObject manager_obj = manager_array.getJSONObject(i);
                        String manager_id = manager_obj.optString("ChannelManager");
                        MyUtils.l("Manager Id", ":" + manager_id);
                        al_channnel_manager.add(manager_id);
                        hashMap_cm.put(manager_id, manager_id);
                    }
                }

                if (new_datset_json.has("ShowOtherCharges")) {
                    if (new_datset_json.get("ShowOtherCharges") instanceof JSONObject) {
                        JSONObject isp_obj = new_datset_json.getJSONObject("ShowOtherCharges");
                        show_other_charges = isp_obj.optString("ShowOtherCharges");
                        if (show_other_charges.equalsIgnoreCase("1")) {
                            ll_router_security.setVisibility(View.VISIBLE);
                        }
                        MyUtils.l("show_other_charges", ":" + show_other_charges);
                    }
                }

                if (new_datset_json.has("UsersList")) {
                    if (new_datset_json.get("UsersList") instanceof JSONObject) {
                        JSONObject user_list_obj = new_datset_json.getJSONObject("UsersList");
                        String UserId = user_list_obj.optString("UserId");
                        String user_name = user_list_obj.optString("UserLoginName");
                        al_user_id.add(UserId);

                        Log.d("RoleName", ":" + user_list_obj.optString("RoleName"));
                        if (user_list_obj.optString("RoleName").equalsIgnoreCase("DSA")) {

                            RoleId = 4;
                        }
                        if (user_list_obj.optString("RoleName").equalsIgnoreCase("DST")) {
                            RoleId = 1;
                        }

                        Common_Item userlist = new Common_Item(UserId, user_name);
                        userlist.setRole_Name(role_name);
                        al_user_list.add(userlist);
                        user_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_user_list);

                        if (!this.Login_UserId.equalsIgnoreCase(UserId)) {
                            Common_Item userlist1 = new Common_Item(this.Login_UserId, Username);

                            userlist1.setRole_Name("DSA");
                            al_user_list.add(userlist1);
                            user_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_user_list);
                            source_id = al_user_list.get(0).getItem_id();
                            source_name = al_user_list.get(0).getItem_name();
                        }
                        sp_select_user.setAdapter(user_Adapter);
                    }


                    if (new_datset_json.get("UsersList") instanceof JSONArray) {
                        JSONArray user_list_array = new_datset_json.getJSONArray("UsersList");
                        //boolean is_add=false;

                        for (int i = 0; i < user_list_array.length(); i++) {
                            JSONObject user_list_obj = user_list_array.getJSONObject(i);
                            String UserId = user_list_obj.optString("UserId");
                            String user_name = user_list_obj.optString("UserLoginName");
                            String role_name = user_list_obj.optString("RoleName");
                            String channel_user_id = user_list_obj.optString("ChannelManager");
                            //MyUtils.l("From"+i,"array:"+UserId);
                            Common_Item userlist = new Common_Item(UserId, user_name);
                            String last_userid = "";
                            userlist.setRole_Name(role_name);

                            if (al_channnel_manager != null && al_channnel_manager.size() > 0) {
                                for (int j = 0; j < al_channnel_manager.size(); j++) {
                                    if (al_channnel_manager.get(j).equalsIgnoreCase(UserId)) {
                                        //MyUtils.l("Channel Manager Id"+j, ":"+al_channnel_manager.get(j));
                                        //MyUtils.l("Channel User Id"+j,":"+UserId);
                                        // MyUtils.l("Channel Manager Name", ":"+manager_name);
                                        al_manager_list.add(userlist);
                                        channel_mgr_adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_manager_list);
                                        sp_channel_manager.setAdapter(channel_mgr_adapter);
                                    } else {
                                        if ((!last_userid.equalsIgnoreCase(UserId))) {
                                            //Log.d(" Manager Id"+j, ":"+al_channnel_manager.get(j));
                                            //MyUtils.l(" User Id"+j,":"+UserId);
                                            //MyUtils.l("User Name", ":"+manager_name);
                                            if (!al_channnel_manager.contains(UserId)) {
                                                if (hashMap_cm != null) {
                                                    if (hashMap_cm.size() > 0) {
                                                        if (hashMap_cm.containsKey(channel_user_id)) {
                                                            userlist.setChannel_mgr(channel_user_id);
                                                        }
                                                    }
                                                }
                                                al_user_list.add(userlist);
                                                user_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_user_list);
                                                sp_select_user.setAdapter(user_Adapter);

                                            }
                                        }
                                    }
                                    last_userid = UserId;
                                }
                            } else {
                                if (!Login_UserId.equalsIgnoreCase(userlist.getItem_id())) {
                                    al_user_list.add(userlist);
                                    user_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_user_list);
                                    sp_select_user.setAdapter(user_Adapter);

                                }
                            }
                        }

                        Common_Item userlist1 = new Common_Item(this.Login_UserId, Username);

                        userlist1.setRole_Name("DSA");
                        source_id = al_user_list.get(0).getItem_id();
                        source_name = al_user_list.get(0).getItem_name();
                        al_user_list.add(userlist1);
                        user_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_user_list);
                        user_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_user_list);
                    }
                }




                if (new_datset_json.has("CablingType")) {
                    if (new_datset_json.get("CablingType") instanceof JSONObject) {
                        JSONObject city_obj = new_datset_json.getJSONObject("CablingType");
                        String cable_id = city_obj.optString("CablingTypeID");
                        String cable_name = city_obj.optString("CablingType");
                        Common_Item cable_data = new Common_Item(cable_id, cable_name);
                        al_city_type.add(cable_data);
                    }

                    if (new_datset_json.get("CablingType") instanceof JSONArray) {
                        JSONArray city_array = new_datset_json.getJSONArray("CablingType");
                        for (int i = 0; i < city_array.length(); i++) {
                            JSONObject city_obj = city_array.getJSONObject(i);
                            String cable_id = city_obj.optString("CablingTypeID");
                            String cable_name = city_obj.optString("CablingType");
                            Common_Item cable_data = new Common_Item(cable_id, cable_name);
                            al_cabling_type.add(cable_data);
                        }
                    }
                    cabling_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_cabling_type);
                   // sp_cabling_type.setAdapter(cabling_Adapter);
                }




                if (new_datset_json.has("PPConnectionTypeTable")) {
                    if (new_datset_json.get("PPConnectionTypeTable") instanceof JSONObject) {
                        JSONObject cust_obj = new_datset_json.getJSONObject("PPConnectionTypeTable");
                        String cust_id = cust_obj.optString("ConnectionTypeId");
                        String cust_name = cust_obj.optString("ConnectionTypeName");
                        Common_Item cust_data = new Common_Item(cust_name, cust_id);
                        al_cust_type.add(cust_data);
                    }

                    if (new_datset_json.get("PPConnectionTypeTable") instanceof JSONArray) {
                        JSONArray cust_array = new_datset_json.getJSONArray("PPConnectionTypeTable");
                        for (int i = 0; i < cust_array.length(); i++) {
                            JSONObject cust_obj = cust_array.getJSONObject(i);
                            String cust_id = cust_obj.optString("ConnectionTypeId");
                            String cust_name = cust_obj.optString("ConnectionTypeName");
                            Common_Item cust_data = new Common_Item(cust_id, cust_name);
                            al_cust_type.add(cust_data);
                        }
                    }
                    customer_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_cust_type);
                    sp_customertype.setAdapter(customer_Adapter);
                }




                if (new_datset_json.has("IDProof")) {
                    if (new_datset_json.get("IDProof") instanceof JSONObject) {
                        JSONObject idProof_obj = new_datset_json.getJSONObject("IDProof");
                        String idProof_id = idProof_obj.optString("IdProof_ID");
                        String idProof_name = idProof_obj.optString("IdProof_Type");
                        Common_Item idProof_data = new Common_Item(idProof_name, idProof_id);
                        al_id_proof_type.add(idProof_data);
                    }

                    if (new_datset_json.get("IDProof") instanceof JSONArray) {
                        JSONArray idProof_array = new_datset_json.getJSONArray("IDProof");
                        for (int i = 0; i < idProof_array.length(); i++) {
                            JSONObject idProof_obj = idProof_array.getJSONObject(i);
                            String idProof_id = idProof_obj.optString("IdProof_ID");
                            String idProof_name = idProof_obj.optString("IdProof_Type");
                            Common_Item idProof_data = new Common_Item(idProof_id, idProof_name);
                            al_id_proof_type.add(idProof_data);
                        }
                    }
                    idproof_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_id_proof_type);
                    id_list.setAdapter(idproof_Adapter);
                    id_proof_id = al_id_proof_type.get(0).getItem_id();
                }



                if (new_datset_json.has("Nationality")) {
                    if (new_datset_json.get("Nationality") instanceof JSONObject) {
                        JSONObject nationality_obj = new_datset_json.getJSONObject("Nationality");
                        String nationality_id = nationality_obj.optString("IdProof_ID");
                        String nationality_name = nationality_obj.optString("IdProof_Type");
                        Common_Item nationality_data = new Common_Item(nationality_name, nationality_id);
                        al_nationality_type.add(nationality_data);
                    }

                    if (new_datset_json.get("Nationality") instanceof JSONArray) {
                        JSONArray nationality_array = new_datset_json.getJSONArray("Nationality");
                        for (int i = 0; i < nationality_array.length(); i++) {
                            JSONObject nationality_obj = nationality_array.getJSONObject(i);
                            String nationality_id = nationality_obj.optString("NationalityId");
                            String nationality_name = nationality_obj.optString("Nationality");
                            Common_Item nationality_data = new Common_Item(nationality_id, nationality_name);
                            al_nationality_type.add(nationality_data);
                        }
                    }
                    nationality_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_nationality_type);
                    nationality.setAdapter(nationality_Adapter);
                    natinality_id = al_nationality_type.get(0).getItem_id();
                }




                if (new_datset_json.has("Entity")) {
                    if (new_datset_json.get("Entity") instanceof JSONObject) {
                        JSONObject entity_obj = new_datset_json.getJSONObject("Entity");
                        String entity_id = entity_obj.optString("EntityID");
                        String entity_name = entity_obj.optString("EntityName");
                        Common_Item entity_data = new Common_Item(entity_name, entity_id);
                        al_entity_type.add(entity_data);
                    }

                    if (new_datset_json.get("Entity") instanceof JSONArray) {
                        JSONArray entity_array = new_datset_json.getJSONArray("Entity");
                        for (int i = 0; i < entity_array.length(); i++) {
                            JSONObject entity_obj = entity_array.getJSONObject(i);
                            String entity_id = entity_obj.optString("EntityID");
                            String entity_name = entity_obj.optString("EntityName");
                            Common_Item entity_data = new Common_Item(entity_id, entity_name);
                            al_entity_type.add(entity_data);
                        }
                    }
                    entity_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_entity_type);
                    entity.setAdapter(entity_Adapter);
                }




                if (new_datset_json.has("Type")) {
                    if (new_datset_json.get("Type") instanceof JSONObject) {
                        JSONObject type_obj = new_datset_json.getJSONObject("Type");
                        String type_id = type_obj.optString("ID");
                        String type_name = type_obj.optString("SMEAccType");
                        Common_Item type_data = new Common_Item(type_name, type_id);
                        al_type_type.add(type_data);
                    }

                    if (new_datset_json.get("Type") instanceof JSONArray) {
                        JSONArray type_array = new_datset_json.getJSONArray("Type");
                        for (int i = 0; i < type_array.length(); i++) {
                            JSONObject type_obj = type_array.getJSONObject(i);
                            String type_id = type_obj.optString("ID");
                            String type_name = type_obj.optString("SMEAccType");
                            Common_Item type_data = new Common_Item(type_id, type_name);
                            al_type_type.add(type_data);
                        }
                    }
                    type_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_type_type);
                    type.setAdapter(type_Adapter);
                }




                if (new_datset_json.has("KeyAccountMgr")) {
                    if (new_datset_json.get("KeyAccountMgr") instanceof JSONObject) {
                        JSONObject KeyAccountMgr_obj = new_datset_json.getJSONObject("KeyAccountMgr");
                        String KeyAccountMgr_id = KeyAccountMgr_obj.optString("UserId");
                        String KeyAccountMgr_name = KeyAccountMgr_obj.optString("FullName");
                        Common_Item KeyAccountMgr_data = new Common_Item(KeyAccountMgr_id, KeyAccountMgr_name);
                        al_key_account_mng_type.add(KeyAccountMgr_data);
                    }

                    if (new_datset_json.get("KeyAccountMgr") instanceof JSONArray) {
                        JSONArray KeyAccountMgr_array = new_datset_json.getJSONArray("KeyAccountMgr");
                        for (int i = 0; i < KeyAccountMgr_array.length(); i++) {
                            JSONObject KeyAccountMgr_obj = KeyAccountMgr_array.getJSONObject(i);
                            String KeyAccountMgr_id = KeyAccountMgr_obj.optString("UserId");
                            String KeyAccountMgr_name = KeyAccountMgr_obj.optString("FullName");
                            Common_Item KeyAccountMgr_data = new Common_Item(KeyAccountMgr_id, KeyAccountMgr_name);
                            al_key_account_mng_type.add(KeyAccountMgr_data);
                        }
                    }
                    key_account_mng_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_key_account_mng_type);
                    key_account_mng.setAdapter(key_account_mng_Adapter);
                }



                if (new_datset_json.has("RelationShipMgr")) {
                    if (new_datset_json.get("RelationShipMgr") instanceof JSONObject) {
                        JSONObject RelationShipMgr_obj = new_datset_json.getJSONObject("RelationShipMgr");
                        String RelationShipMgr_id = RelationShipMgr_obj.optString("UserId");
                        String RelationShipMgr_name = RelationShipMgr_obj.optString("FullName");
                        Common_Item RelationShipMgr_data = new Common_Item(RelationShipMgr_id, RelationShipMgr_name);
                        al_relationShip_mng_type.add(RelationShipMgr_data);
                    }

                    if (new_datset_json.get("RelationShipMgr") instanceof JSONArray) {
                        JSONArray RelationShipMgr_array = new_datset_json.getJSONArray("RelationShipMgr");
                        for (int i = 0; i < RelationShipMgr_array.length(); i++) {
                            JSONObject RelationShipMgr_obj = RelationShipMgr_array.getJSONObject(i);
                            String RelationShipMgr_id = RelationShipMgr_obj.optString("UserId");
                            String RelationShipMgr_name = RelationShipMgr_obj.optString("FullName");
                            Common_Item RelationShipMgr_data = new Common_Item(RelationShipMgr_id, RelationShipMgr_name);
                            al_relationShip_mng_type.add(RelationShipMgr_data);
                        }
                    }
                    relationShip_mng_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_relationShip_mng_type);
                    relationShip_mng.setAdapter(relationShip_mng_Adapter);
                }



                if (new_datset_json.has("BillingCycle")) {
                    if (new_datset_json.get("BillingCycle") instanceof JSONObject) {
                        JSONObject BillingCycle_obj = new_datset_json.getJSONObject("BillingCycle");
                        String BillingCycle_id = BillingCycle_obj.optString("BillCycleValue");
                        String BillingCycle_name = BillingCycle_obj.optString("BillCycleType");
                        Common_Item BillingCycle_data = new Common_Item(BillingCycle_name, BillingCycle_id);
                        al_billing_cycle_type.add(BillingCycle_data);
                    }

                    if (new_datset_json.get("BillingCycle") instanceof JSONArray) {
                        JSONArray BillingCycle_array = new_datset_json.getJSONArray("BillingCycle");
                        for (int i = 0; i < BillingCycle_array.length(); i++) {
                            JSONObject BillingCycle_obj = BillingCycle_array.getJSONObject(i);
                            String BillingCycle_id = BillingCycle_obj.optString("BillCycleValue");
                            String BillingCycle_name = BillingCycle_obj.optString("BillCycleType");
                            Common_Item BillingCycle_data = new Common_Item(BillingCycle_id, BillingCycle_name);
                            al_billing_cycle_type.add(BillingCycle_data);
                        }
                    }
                    billing_cycle_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_billing_cycle_type);
                    billing_cycle.setAdapter(billing_cycle_Adapter);
                }



                if (new_datset_json.has("InstallBy")) {
                    if (new_datset_json.get("InstallBy") instanceof JSONObject) {
                        JSONObject InstallBy_obj = new_datset_json.getJSONObject("InstallBy");
                        String InstallBy_id = InstallBy_obj.optString("UserId");
                        String InstallBy_name = InstallBy_obj.optString("FullName");
                        Common_Item InstallBy_data = new Common_Item(InstallBy_name, InstallBy_id);
                        al_sp_install_by_type.add(InstallBy_data);
                    }

                    if (new_datset_json.get("InstallBy") instanceof JSONArray) {
                        JSONArray InstallBy_array = new_datset_json.getJSONArray("InstallBy");
                        for (int i = 0; i < InstallBy_array.length(); i++) {
                            JSONObject InstallBy_obj = InstallBy_array.getJSONObject(i);
                            String InstallBy_id = InstallBy_obj.optString("UserId");
                            String InstallBy_name = InstallBy_obj.optString("FullName");
                            Common_Item InstallBy_data = new Common_Item(InstallBy_id, InstallBy_name);
                            al_sp_install_by_type.add(InstallBy_data);
                        }
                    }
                    sp_install_by_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_sp_install_by_type);
                    sp_install_by.setAdapter(sp_install_by_Adapter);
                    sp_installation_discount_by.setAdapter(sp_install_by_Adapter);
                    sp_pkdicountby.setAdapter(sp_install_by_Adapter);
                    packdicbyid= al_sp_install_by_type.get(0).getItem_id();
                    installtiondicbyid = al_sp_install_by_type.get(0).getItem_id();


                }



                if (new_datset_json.has("Source")) {
                    if (new_datset_json.get("Source") instanceof JSONObject) {
                        JSONObject InstallBy_obj = new_datset_json.getJSONObject("Source");
                        String InstallBy_id = InstallBy_obj.optString("SourceId");
                        String InstallBy_name = InstallBy_obj.optString("SourceName");
                        Common_Item InstallBy_data = new Common_Item(InstallBy_name, InstallBy_id);
                        al_user_list.add(InstallBy_data);
                    }

                    if (new_datset_json.get("Source") instanceof JSONArray) {
                        JSONArray InstallBy_array = new_datset_json.getJSONArray("Source");
                        for (int i = 0; i < InstallBy_array.length(); i++) {
                            JSONObject InstallBy_obj = InstallBy_array.getJSONObject(i);
                            String InstallBy_id = InstallBy_obj.optString("SourceId");
                            String InstallBy_name = InstallBy_obj.optString("SourceName");
                            Common_Item InstallBy_data = new Common_Item(InstallBy_id, InstallBy_name);
                            al_user_list.add(InstallBy_data);
                        }
                    }
                    user_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_user_list);
                    sp_select_user.setAdapter(user_Adapter);
                }

//
//                if (new_datset_json.has("InstallBy")) {
//                    if (new_datset_json.get("InstallBy") instanceof JSONObject) {
//                        JSONObject InstallBy_obj = new_datset_json.getJSONObject("InstallBy");
//                        String InstallBy_id = InstallBy_obj.optString("SourceId");
//                        String InstallBy_name = InstallBy_obj.optString("SourceName");
//                        Common_Item InstallBy_data = new Common_Item(InstallBy_name, InstallBy_id);
//                        al_installdiscount_type.add(InstallBy_data);
//                    }
//
//                    if (new_datset_json.get("InstallBy") instanceof JSONArray) {
//                        JSONArray InstallBy_array = new_datset_json.getJSONArray("InstallBy");
//                        for (int i = 0; i < InstallBy_array.length(); i++) {
//                            JSONObject InstallBy_obj = InstallBy_array.getJSONObject(i);
//                            String InstallBy_id = InstallBy_obj.optString("SourceId");
//                            String InstallBy_name = InstallBy_obj.optString("SourceName");
//                            Common_Item InstallBy_data = new Common_Item(InstallBy_id, InstallBy_name);
//                            al_installdiscount_type.add(InstallBy_data);
//                        }
//                    }
//                    sp_installetiondiscount_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_installdiscount_type);
//                    sp_pkdicountby.setAdapter(sp_installetiondiscount_Adapter);
//                }



//                if (new_datset_json.has("InstallBy")) {
//                    if (new_datset_json.get("InstallBy") instanceof JSONObject) {
//                        JSONObject InstallBy_obj = new_datset_json.getJSONObject("InstallBy");
//                        String InstallBy_id = InstallBy_obj.optString("SourceId");
//                        String InstallBy_name = InstallBy_obj.optString("SourceName");
//                        Common_Item InstallBy_data = new Common_Item(InstallBy_name, InstallBy_id);
//                        al_pkdiscount_type.add(InstallBy_data);
//                    }
//
//                    if (new_datset_json.get("InstallBy") instanceof JSONArray) {
//                        JSONArray InstallBy_array = new_datset_json.getJSONArray("InstallBy");
//                        for (int i = 0; i < InstallBy_array.length(); i++) {
//                            JSONObject InstallBy_obj = InstallBy_array.getJSONObject(i);
//                            String InstallBy_id = InstallBy_obj.optString("SourceId");
//                            String InstallBy_name = InstallBy_obj.optString("SourceName");
//                            Common_Item InstallBy_data = new Common_Item(InstallBy_id, InstallBy_name);
//                            al_pkdiscount_type.add(InstallBy_data);
//                        }
//                    }
//                    sp_pckdisc_Adapter= new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_pkdiscount_type);
//                    sp_installation_discount_by.setAdapter(sp_pckdisc_Adapter);
//                }



            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //showFinishDialog("Problem in Parsing Data"+"\n"+" Please try again!");
            DialogUtils.show_dialog(CAF_Activity.this, "Confirmation", "Problem in Parsing Data" + "\n" + " Please try again!", true);
        }
    }

    public class CallAddressdetails extends AsyncTask<String, Void, Void> {
        String rslt = "";
        String address_response = "";
        String ParamType, ID, CliectAccessId;
        ProgressDialog prgDialog;

        public CallAddressdetails(String ParamType, String ID) {
            this.ParamType = ParamType;
            this.ID = ID;
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            prgDialog = new ProgressDialog(CAF_Activity.this);
            prgDialog.setMessage("Please wait...");
            prgDialog.show();
            prgDialog.setCancelable(false);
        }

        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            AddressDetailsSOAP address_soap = new AddressDetailsSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.SOAP_URL), getString(R.string.METHOD_ADDRESSData));
            try {
                //rslt=address_soap.GetMiniCAFAddressDetails(clientaccess_id,ParamType,ID);
                rslt = address_soap.GetMiniCAFAddressDetails(client_accesss_id, ParamType, ID);
                address_response = address_soap.getJsonResponse();
            } catch (SocketTimeoutException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            if (rslt.length() > 0) {
                if (rslt.equalsIgnoreCase("OK")) {
                    if (address_response.length() > 0) {
                        if (ParamType.equalsIgnoreCase("Location")) {
                            location_parse_response(address_response);
                        }
                        if (ParamType.equalsIgnoreCase("Area")) {
                            area_parse_response(address_response);
                        }
                        if (ParamType.equalsIgnoreCase("SubArea")) {
                            sub_area_parse_response(address_response);
                            Common_Item common_Item = connection_Adapter.getItem(sp_connection.getSelectedItemPosition());
                            new PackageNameDetails().execute(MemberId, common_Item.getItem_id(), AreaCode, ClientAccessId);
                        }
                        if (ParamType.equalsIgnoreCase("Building")) {
                            building_parse_response(address_response);
                        }
                        if (ParamType.equalsIgnoreCase("Tower")) {
                            tower_parse_response(address_response);
                        }
                        if (ParamType.equalsIgnoreCase("Floor")) {
                            floor_parse_response(address_response);
                        }
                    }
                } else {
                    //AlertsBoxFactory.showAlert("Please Select "+ParamType+" again !!", CAF_Activity.this);
                    DialogUtils.show_dialog(CAF_Activity.this, "Confirmation", "Please Select " + ParamType + " again !!", false);
                }
            } else {
                // AlertsBoxFactory.showAlert("Please Select "+ParamType+" again !!", CAF_Activity.this);
                DialogUtils.show_dialog(CAF_Activity.this, "Confirmation", "Please Select " + ParamType + " again !!", false);
            }
            super.onPostExecute(result);
            prgDialog.dismiss();
        }
    }

    public void getAddressformgooglemap(){
        try {

            //Double latitude, Double longitude

            Location mLocation = new Location("");

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("gps_location", 0); // 0 - for private mode
            Log.e("CAF address",":"+sharedPreferences.getString("address",""));
            Log.e("CAF latitude",":"+sharedPreferences.getString("latitude",""));
            Log.e("CAF longitude",":"+sharedPreferences.getString("longitude",""));

            if (!sharedPreferences.getString("latitude","").equals("") && !sharedPreferences.getString("longitude","").equals("")){

                mLocation.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","")));
                mLocation.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","")));

                latitude=sharedPreferences.getString("latitude","");
                longitude=sharedPreferences.getString("longitude","");
                address=sharedPreferences.getString("address","");

                et_latitude.setText(latitude);
                et_longitude.setText(longitude);
                et_addressline.setText(address);

                if(!city_id.equalsIgnoreCase("-1")){
                    sp_city.setSelection(al_city_type.indexOf(city_id));
                }
                if(!location_id.equalsIgnoreCase("-1")){
                    sp_location.setSelection(al_location_type.indexOf(location_id));
                }
                if(!area_id.equalsIgnoreCase("-1")){
                    sp_area.setSelection(al_area_type.indexOf(area_id));
                }
                if(!sub_area_id.equalsIgnoreCase("-1")){
                    sp_sub_area.setSelection(al_sub_area_type.indexOf(sub_area_id));
                }
                if(!building_id.equalsIgnoreCase("-1")){
                    sp_building.setSelection(al_building_type.indexOf(building_id));
                }
                if(!tower_id.equalsIgnoreCase("-1")){
                    sp_tower.setSelection(al_tower_type.indexOf(tower_id));
                }
                if(!floor_no.equalsIgnoreCase("-1")){
                    sp_floor.setSelection(al_floor_type.indexOf(floor_no));
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void location_parse_response(String location_json) {
        try {
            JSONObject location_obj = new JSONObject(location_json);
            if (location_obj.has("NewDataSet")) {
                JSONObject new_location_obj = location_obj.getJSONObject("NewDataSet");

                if (new_location_obj.has("Table")) {
                    if (new_location_obj.get("Table") instanceof JSONObject) {
                        JSONObject location_type_obj = new_location_obj.getJSONObject("Table");
                        String location_id = location_type_obj.optString("LocationId");
                        String location_name = location_type_obj.optString("LocationName");
                        Common_Item location_Item = new Common_Item(location_id, location_name);
                        al_location_type.add(location_Item);
                    }

                    if (new_location_obj.get("Table") instanceof JSONArray) {
                        JSONArray location_type_array = new_location_obj.getJSONArray("Table");
                        for (int i = 0; i < location_type_array.length(); i++) {
                            JSONObject location_array_obj = location_type_array.getJSONObject(i);
                            String city_id = location_array_obj.optString("CityId");
                            String location_id = location_array_obj.optString("LocationId");
                            String location_name = location_array_obj.optString("LocationName");
                            Common_Item location_Item = new Common_Item(location_id, location_name);
                            al_location_type.add(location_Item);
                        }
                    }

                    location_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_location_type);
                    sp_location.setAdapter(location_Adapter);
                    location_id = al_location_type.get(0).getItem_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void area_parse_response(String area_json) {
        try {
            JSONObject area_obj = new JSONObject(area_json);
            if (area_obj.has("NewDataSet")) {
                JSONObject new_location_obj = area_obj.getJSONObject("NewDataSet");

                if (new_location_obj.has("Table")) {
                    if (new_location_obj.get("Table") instanceof JSONObject) {
                        JSONObject area_type_obj = new_location_obj.getJSONObject("Table");
                        String area_id = area_type_obj.optString("AreaID");
                        String area_name = area_type_obj.optString("AreaName");
                        Common_Item area_Item = new Common_Item(area_id, area_name);
                        al_area_type.add(area_Item);
                        Common_Item area_Item1 = new Common_Item("0", "-:: No Area Found ::-");
                        al_area_type.add(area_Item1);
                    }

                    if (new_location_obj.get("Table") instanceof JSONArray) {
                        JSONArray area_type_array = new_location_obj.getJSONArray("Table");
                        for (int i = 0; i < area_type_array.length(); i++) {
                            JSONObject area_array_obj = area_type_array.getJSONObject(i);
                            String area_location_id = area_array_obj.optString("LocationId");
                            String area_id = area_array_obj.optString("AreaID");
                            String area_name = area_array_obj.optString("AreaName");
                            Common_Item area_Item = new Common_Item(area_id, area_name);
                            al_area_type.add(area_Item);
                        }
                        MyUtils.l("Size", ":" + al_area_type.size());
                    }

                    area_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_area_type);
                    sp_area.setAdapter(area_Adapter);
                    area_id = al_area_type.get(0).getItem_id();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sub_area_parse_response(String sub_area_json) {
        try {
            JSONObject sub_area_obj = new JSONObject(sub_area_json);
            if (sub_area_obj.has("NewDataSet")) {
                JSONObject new_sub_area_obj = sub_area_obj.getJSONObject("NewDataSet");

                if (new_sub_area_obj.has("Table")) {
                    if (new_sub_area_obj.get("Table") instanceof JSONObject) {
                        JSONObject subarea_type_obj = new_sub_area_obj.getJSONObject("Table");
                        String subarea_id = subarea_type_obj.optString("SubAreaID");
                        String subarea_name = subarea_type_obj.optString("SubAreaName");

                        Common_Item subarea_Item = new Common_Item(subarea_id, subarea_name);
                        al_sub_area_type.add(subarea_Item);
                        Common_Item subarea_Item1 = new Common_Item("0", "-:: No SubArea Found ::-");
                        al_sub_area_type.add(subarea_Item1);
                    }

                    if (new_sub_area_obj.get("Table") instanceof JSONArray) {
                        JSONArray area_type_array = new_sub_area_obj.getJSONArray("Table");
                        for (int i = 0; i < area_type_array.length(); i++) {
                            JSONObject area_array_obj = area_type_array.getJSONObject(i);
                            String area_id = area_array_obj.optString("AreaID");
                            String subarea_id = area_array_obj.optString("SubAreaID");
                            String subarea_name = area_array_obj.optString("SubAreaName");
                            Common_Item subarea_Item = new Common_Item(subarea_id, subarea_name);
                            al_sub_area_type.add(subarea_Item);
                        }
                        MyUtils.l("Size", ":" + al_area_type.size());
                    }
                    sub_area_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_sub_area_type);
                    sp_sub_area.setAdapter(sub_area_Adapter);
                    sub_area_id = al_sub_area_type.get(0).getItem_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void building_parse_response(String building_json) {
        try {
            JSONObject building_obj = new JSONObject(building_json);
            if (building_obj.has("NewDataSet")) {
                JSONObject new_building_obj = building_obj.getJSONObject("NewDataSet");

                if (new_building_obj.has("Table")) {
                    if (new_building_obj.get("Table") instanceof JSONObject) {
                        JSONObject building_type_obj = new_building_obj.getJSONObject("Table");
                        String building_id = building_type_obj.optString("BuildingID");
                        String building_name = building_type_obj.optString("BuildingName");
                        Common_Item building_Item = new Common_Item(building_id, building_name);
                        al_building_type.add(building_Item);
                        Common_Item building_Item1 = new Common_Item("0", "-:: No Building Found ::-");
                        al_building_type.add(building_Item1);
                    }

                    if (new_building_obj.get("Table") instanceof JSONArray) {
                        JSONArray area_type_array = new_building_obj.getJSONArray("Table");
                        for (int i = 0; i < area_type_array.length(); i++) {
                            JSONObject building_type_obj = area_type_array.getJSONObject(i);
                            String subarea_id = building_type_obj.optString("SubAreaID");
                            String building_id = building_type_obj.optString("BuildingID");
                            String building_name = building_type_obj.optString("BuildingName");
                            Common_Item building_Item = new Common_Item(building_id, building_name);
                            al_building_type.add(building_Item);
                        }
                    }
                    building_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_building_type);
                    sp_building.setAdapter(building_Adapter);
                    building_id = al_building_type.get(0).getItem_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tower_parse_response(String tower_json) {
        try {
            JSONObject tower_obj = new JSONObject(tower_json);
            if (tower_obj.has("NewDataSet")) {
                JSONObject new_tower_obj = tower_obj.getJSONObject("NewDataSet");

                if (new_tower_obj.has("Table")) {
                    if (new_tower_obj.get("Table") instanceof JSONObject) {
                        JSONObject tower_type_obj = new_tower_obj.getJSONObject("Table");
                        String tower_id = tower_type_obj.optString("TowerId");
                        String tower_name = tower_type_obj.optString("TowerName");
                        Common_Item tower_Item = new Common_Item(tower_id, tower_name);
                        al_tower_type.add(tower_Item);
                        Common_Item tower_Item1 = new Common_Item("0", "-:: No Tower Found ::-");
                        al_tower_type.add(tower_Item1);
                    }

                    if (new_tower_obj.get("Table") instanceof JSONArray) {
                        JSONArray area_type_array = new_tower_obj.getJSONArray("Table");
                        for (int i = 0; i < area_type_array.length(); i++) {
                            JSONObject tower_type_obj = area_type_array.getJSONObject(i);
                            String subarea_id = tower_type_obj.optString("BuildingID");
                            String tower_id = tower_type_obj.optString("TowerId");
                            String tower_name = tower_type_obj.optString("TowerName");
                            Common_Item tower_Item = new Common_Item(tower_id, tower_name);
                            al_tower_type.add(tower_Item);
                        }
                    }
                    tower_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_tower_type);
                    sp_tower.setAdapter(tower_Adapter);
                    tower_id = al_tower_type.get(0).getItem_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void floor_parse_response(String floor_json) {
        try {
            JSONObject floor_obj = new JSONObject(floor_json);
            if (floor_obj.has("NewDataSet")) {
                JSONObject new_floor_obj = floor_obj.getJSONObject("NewDataSet");

                if (new_floor_obj.has("Table")) {
                    if (new_floor_obj.get("Table") instanceof JSONObject) {
                        JSONObject floor_type_obj = new_floor_obj.getJSONObject("Table");
                        String floor_no = floor_type_obj.optString("FloorNo");
                        String floor_name = floor_type_obj.optString("FloorName");
                        Common_Item floor_Item = new Common_Item(floor_no, floor_name);
                        al_floor_type.add(floor_Item);
                        Common_Item floor_Item1 = new Common_Item("0", "-:: No Floor Found ::-");
                        al_floor_type.add(floor_Item1);
                    }

                    if (new_floor_obj.get("Table") instanceof JSONArray) {
                        JSONArray area_type_array = new_floor_obj.getJSONArray("Table");
                        for (int i = 0; i < area_type_array.length(); i++) {
                            JSONObject tower_type_obj = area_type_array.getJSONObject(i);
                            String tower_id = tower_type_obj.optString("TowerId");
                            String floor_no = tower_type_obj.optString("FloorNo");
                            String floor_name = tower_type_obj.optString("FloorName");
                            Common_Item floor_Item = new Common_Item(floor_no, floor_name);
                            al_floor_type.add(floor_Item);
                        }
                    }
                    floor_Adapter = new Common_Item_Adapter(CAF_Activity.this, R.layout.row_item, al_floor_type);
                    sp_floor.setAdapter(floor_Adapter);
                    floor_no = al_floor_type.get(0).getItem_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class PackageNameDetails extends AsyncTask<String, String, String> {
        ProgressDialog prgDialog;
        PackageNameSOAP packageSOAP;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            prgDialog = new ProgressDialog(CAF_Activity.this);
            prgDialog.setMessage("Please wait...");
            prgDialog.show();
            prgDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String result = " ";
            try {
                packageSOAP = new PackageNameSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.GLOBAL_SOAP_URL),
                        getString(R.string.METHOD_PACKAGE_LIST));
                //result = packageSOAP.GetPackageDetails("0",conn_type_id,area_id,/*MyUtils.CLIENTACCESSID*/clientaccess_id);
               // result = packageSOAP.GetPackageDetails("0", conn_type_id, area_id,/*MyUtils.CLIENTACCESSID*/client_accesss_id);

                result = packageSOAP.GetPackageDetails(userid, isp_id, area_id,conn_type_id,false,0,clientaccess_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String theResult) {
            prgDialog.dismiss();
            //super.onPostExecute(theResult);

            allPackage = packageSOAP.getPackgeDetails();
            package_Adapter = new Common_Item_Adapter_Package(CAF_Activity.this, R.layout.row_item1, allPackage);
            sp_pkg_name.setAdapter(package_Adapter);

            super.onPostExecute(theResult);

        }
    }

    private DatePickerDialog.OnDateSetListener BIRTH_dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
            year = Year;
            month = monthOfYear;
            day = dayOfMonth;

            Birth_date_Display();
        }
    };

    private void Birth_date_Display() {

            tv_displaydob.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));
            // Month is 0 based so add 1
            String day_str = "", month_str = "";
            if (day > 9) {
                day_str = "" + day;
            } else {
                day_str = "0" + day;
            }

            if ((month + 1) > 9) {
                month_str = "" + (month + 1);
            } else {
                month_str = "0" + (month + 1);
            }

            dob_to_send = day_str + "" + month_str + "" + year + "000000";


    }

    private DatePickerDialog.OnDateSetListener CAFilled_dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
            year = Year;
            month = monthOfYear;
            day = dayOfMonth;
            Caf_Filled_date_Display();
        }
    };

    private void Caf_Filled_date_Display() {
        tv_displyfilleddate.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));

        String day_str = "", month_str = "";
        if (day > 9) {
            day_str = "" + day;
        } else {
            day_str = "0" + day;
        }

        if ((month + 1) > 9) {
            month_str = "" + (month + 1);
        } else {
            month_str = "0" + (month + 1);
        }
        caf_filled_to_send = day_str + "" + month_str + "" + year + "000000";
    }

    private DatePickerDialog.OnDateSetListener Cheque_dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
            year = Year;
            month = monthOfYear;
            day = dayOfMonth;
            Cheque_date_Display();
        }
    };

    private void Cheque_date_Display() {
        tv_displaychequeDate.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));
        cheque_date = tv_displaychequeDate.getText().toString();

        String day_str = "", month_str = "";
        if (day > 9) {
            day_str = "" + day;
        } else {
            day_str = "0" + day;
        }

        if ((month + 1) > 9) {
            month_str = "" + (month + 1);
        } else {
            month_str = "0" + (month + 1);
        }
        cheque_date_to_send = day_str + "" + month_str + "" + year + "000000";

    }

    private DatePickerDialog.OnDateSetListener RS_Cheque_dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
            year = Year;
            month = monthOfYear;
            day = dayOfMonth;
            RS_Cheque_date_Display();
        }
    };

    private DatePickerDialog.OnDateSetListener actication_dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
            year = Year;
            month = monthOfYear;
            day = dayOfMonth;
            activation_date_Display();
        }
    };

    private DatePickerDialog.OnDateSetListener payment_dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int Year, int monthOfYear, int dayOfMonth) {
            year = Year;
            month = monthOfYear;
            day = dayOfMonth;
            payment_date_Display();
        }
    };

    private void RS_Cheque_date_Display() {
        tv_disp_rs_cheque_date.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));
        rs_cheque_date = tv_disp_rs_cheque_date.getText().toString();

        String day_str = "", month_str = "";
        if (day > 9) {
            day_str = "" + day;
        } else {
            day_str = "0" + day;
        }

        if ((month + 1) > 9) {
            month_str = "" + (month + 1);
        } else {
            month_str = "0" + (month + 1);
        }
        RS_Cheque_date_send = day_str + "" + month_str + "" + year + "000000";
    }


    private void activation_date_Display() {
        activation_date_iv.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));
        activation_date_str = activation_date_iv.getText().toString();

        String day_str = "", month_str = "";
        if (day > 9) {
            day_str = "" + day;
        } else {
            day_str = "0" + day;
        }

        if ((month + 1) > 9) {
            month_str = "" + (month + 1);
        } else {
            month_str = "0" + (month + 1);
        }
       actication_date_send = day_str + "" + month_str + "" + year + "000000";


    }

    private void payment_date_Display() {
        payment_date_iv.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year).append(" "));
        payment_date_str = activation_date_iv.getText().toString();

        String day_str = "", month_str = "";
        if (day > 9) {
            day_str = "" + day;
        } else {
            day_str = "0" + day;
        }

        if ((month + 1) > 9) {
            month_str = "" + (month + 1);
        } else {
            month_str = "0" + (month + 1);
        }



        payment_date_send = day_str + "" + month_str + "" + year + "000000";
    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case BIRTH_DATE_DISPLAY:

                //calendar = Calendar.getInstance();

                DatePickerDialog dp_birth_date = new DatePickerDialog(this, BIRTH_dateListener, year, month, day);
                DatePicker datePicker = dp_birth_date.getDatePicker();
                Calendar c = Calendar.getInstance();
                c.set(year - 18, month, day);
                datePicker.setMaxDate(c.getTimeInMillis());
                return dp_birth_date;


            case FORM_FILLED_DATE:
                return new DatePickerDialog(this, CAFilled_dateListener, year, month, day);

	        /*case  PAYMENT_DATE:
	         	 return new DatePickerDialog(this,third_dateListener,year, month,day);*/

            case CHEQUE_DATE:
                return new DatePickerDialog(this, Cheque_dateListener, year, month, day);

            case RS_CHEQUE_DATE:
                return new DatePickerDialog(this, RS_Cheque_dateListener, year, month, day);

            case ACTIVATION_DATE_final:
                return new DatePickerDialog(this, actication_dateListener, year, month, day);

            case PAYMENT_DATE_FINAL:
                return new DatePickerDialog(this, payment_dateListener, year, month, day);
        }
        return null;
    }


    public boolean form_validation(boolean is_ezetap_checked) {
        if (connection_validation()) {
            if (contact_Validation()) {
                if (location_Validation()) {
                    folder_name = et_firstname.getText().toString() + "_" + et_lastname.getText().toString() + "(" + et_caf_no.getText().toString() + ")";
                        /*if (connection_validation()) {
                            if (check_router(is_ezetap_checked)) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }*/
                    if (check_router(is_ezetap_checked)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    public boolean connection_validation() {

       // String pattern = "~`!@#$%^&*()-+={}|:;',/?[]<>\\";

        Pattern letter = Pattern.compile("[A-Z]");
        Pattern digit = Pattern.compile("[0-9]");
        //Pattern special = Pattern.compile ("[[!@#$%&*()+=|<>?{}\\\\[\\\\]~-]]");
        Pattern special = Pattern.compile ("[[~`!@#$%&*()+=|<>?{}\\\\[\\\\]~-]]");

        Matcher hasLetter = letter.matcher(et_caf_no.getText().toString());
        Matcher hasDigit = digit.matcher(et_caf_no.getText().toString());
        Matcher hasSpecial = special.matcher(et_caf_no.getText().toString());

        if (et_caf_no.getText().toString().equals("") && !hasLetter.find() && !hasDigit.find()) {

           Log.d("Comditon", et_caf_no.getText().toString().equals("")+" "+ !hasLetter.find() +" "+ !hasDigit.find());
            //Toast.makeText(getApplicationContext(), "Please Enter CAF NO", Toast.LENGTH_LONG).show();
            et_caf_no.setError("Please Enter CAF NO");
            return false;
        } else {
            hasLetter = letter.matcher(member_loginId.getText().toString());
            hasDigit = digit.matcher(member_loginId.getText().toString());
            hasSpecial = special.matcher(member_loginId.getText().toString());

//        if (member_loginId.getText().toString().equals("") || !hasLetter.find() || !hasDigit.find() || hasSpecial.find()) {
//            //Toast.makeText(getApplicationContext(), "Please Enter CAF NO", Toast.LENGTH_LONG).show();
//            member_loginId.setError("Please Enter Member Login ID");
//            return false;
//        } else {
            if (conn_type_id.equalsIgnoreCase("-1")) {
                Toast.makeText(getApplicationContext(), "Please Select Connection Type", Toast.LENGTH_LONG).show();
                return false;
            } else {
                if (isp_id.equalsIgnoreCase("-1")) {
                    Toast.makeText(getApplicationContext(), "Please Select ISP", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    //return true;
                    if(member_loginId.getText().toString().equals("") || member_loginId.getText().toString().length()!=6){
                        member_loginId.setError("Please Enter 6 Characters MemberLoginId");
                        return false;
                    }else{
                        if(tv_displyfilleddate.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Please Select CAF Filled Date", Toast.LENGTH_LONG).show();
                            return false;
                        }else{
                            return true;
                        }
                    }
               }
            }
            //}
        }
    }

    public boolean contact_Validation() {
            if (et_firstname.getText().toString().equalsIgnoreCase("")) {
                //Toast.makeText(getApplicationContext(), "Please Enter First Name", Toast.LENGTH_LONG).show();
                et_firstname.setError("Please Enter First Name");
                return false;
            } else {
                if (et_middlename.getText().toString().equalsIgnoreCase("")) {
                    //Toast.makeText(getApplicationContext(), "Please Enter Middle Name", Toast.LENGTH_LONG).show();
                    et_middlename.setError("Please Enter Middle Name");
                    return false;
                } else {
                    if (et_lastname.getText().toString().equalsIgnoreCase("")) {
                        //Toast.makeText(getApplicationContext(), "Please Enter Last Name", Toast.LENGTH_LONG).show();
                        et_lastname.setError("Please Enter Last Name");
                        return false;
                    } else {
                        if (et_mbnumber.getText().toString().length() < 10) {
                            ///Toast.makeText(getApplicationContext(), "Please Enter Valid Contact Number", Toast.LENGTH_LONG).show();
                            et_mbnumber.setError("Please Enter Valid Contact Number");
                            return false;
                        } else {
                            if (et_mbnumber.getText().toString().length() > 10 && Patterns.PHONE.matcher(et_mbnumber.getText()).matches()) {
                                //Toast.makeText(getApplicationContext(), "Please Enter Valid Contact Number", Toast.LENGTH_LONG).show();
                                et_mbnumber.setError("Please Enter Valid Contact Number");
                                return false;
                            } else {
							        if (id_proof_id.equalsIgnoreCase("-1")) {
                                         Toast.makeText(getApplicationContext(), "Please Select ID Proof Type Type", Toast.LENGTH_LONG).show();
                                         return false;
                                     } else {

                                        if(et_pannumber.getText().toString().equalsIgnoreCase(""))
                                        {
                                            //ll_contact_details.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), "Please Enter ID proof Number", Toast.LENGTH_LONG).show();
                                            return false;
                                        }
                                        else {

                                         if (natinality_id.equalsIgnoreCase("-1")) {
                                             Toast.makeText(getApplicationContext(), "Please Select Nationality Type", Toast.LENGTH_LONG).show();
                                             return false;
                                         } else {

                                             if (et_emailid.getText().toString().equalsIgnoreCase("")) {
                                                 //Toast.makeText(getApplicationContext(), "Please Enter Email Id", Toast.LENGTH_LONG).show();
                                                 et_emailid.setError("Please Enter Email Id");
                                                 return false;
                                             } else {
                                                 if (!isValidEmail(et_emailid.getText().toString())) {
                                                     //Toast.makeText(getApplicationContext(), "Please Enter Valid Email Id", Toast.LENGTH_LONG).show();
                                                     et_emailid.setError("Please Enter Valid Email Id");
                                                     return false;
                                                 } else {
                                                     return true;
                                                 }
                                             }
                                         }
                                     }
                                 }
                            }
                        }
                    }
                }
            }

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean location_Validation() {
        if (city_id.equalsIgnoreCase("-1")) {
            Toast.makeText(getApplicationContext(), "Please Select City", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (location_id.equalsIgnoreCase("-1")) {
                Toast.makeText(getApplicationContext(), "Please Select Location", Toast.LENGTH_LONG).show();
                return false;
            } else {
                if (area_id.equalsIgnoreCase("-1")) {
                    Toast.makeText(getApplicationContext(), "Please Select Area", Toast.LENGTH_LONG).show();
                    return false;
                } else {
					  /* if(sub_area_id.equalsIgnoreCase("-1"))
						{
						   ll_location_details.setVisibility(View.VISIBLE);
						   Toast.makeText(getApplicationContext(), "Please Select Sub Area", Toast.LENGTH_LONG).show();
						   return false;
					    }
					   else
					   {
						 if(building_id.equalsIgnoreCase("-1"))
						 {
							ll_location_details.setVisibility(View.VISIBLE);
							Toast.makeText(getApplicationContext(), "Please Select Building", Toast.LENGTH_LONG).show();
							return false;
						 }
						 else
						 {
							if(tower_id.equalsIgnoreCase("-1"))
							{
							  ll_location_details.setVisibility(View.VISIBLE);
							  Toast.makeText(getApplicationContext(), "Please Select Tower", Toast.LENGTH_LONG).show();
							  return false;
							}
							else
							{*/
                   /* if(floor_no.equalsIgnoreCase("-1"))
                    {
                        Toast.makeText(getApplicationContext(), "Please Select Floor", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    else
                    {*/
                    if (et_flatno.getText().toString().equalsIgnoreCase("")) {
                        //Toast.makeText(getApplicationContext(), "Please Enter Flat Number", Toast.LENGTH_LONG).show();
                        et_flatno.setError("Please Enter Flat Number");
                        return false;
                    } else {
                        if (et_pincode.getText().toString().equalsIgnoreCase("")) {
                            String pincode = et_pincode.getText().toString();

                            boolean isValidpincode = pincodePattern.matcher(pincode).matches();
                            if(!isValidpincode){
                                et_pincode.setError("Please Enter Valid Pincode");
                            }
                            return false;
                        } else {
                            if (et_addressline.getText().toString().equalsIgnoreCase("")) {
                                //Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_LONG).show();
                                et_addressline.setError("Please Enter Installation Address");
                                return false;
                            } else {
                                if (bill_addressline.getText().toString().equalsIgnoreCase("")) {
                                    //Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_LONG).show();
                                    bill_addressline.setError("Please Enter Billing Address");
                                    return false;
                                } else {
//                                    if (bill_city.getText().toString().equalsIgnoreCase("")) {
//                                        //Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_LONG).show();
//                                        bill_city.setError("Please Enter Billing City");
//                                        return false;
//                                    } else {
                                        if (bill_pincode.getText().toString().equalsIgnoreCase("")) {
                                            String pincode = bill_pincode.getText().toString();
                                            Pattern pincodePattern = Pattern.compile("[0-9]{6}");
                                            boolean isValidpincode = pincodePattern.matcher(pincode).matches();
                                            if(!isValidpincode){
                                                bill_pincode.setError("Please Enter Valid Pincode");
                                            }
                                            return false;
                                        } else {

                                            if (per_addressline.getText().toString().equalsIgnoreCase("")) {
                                                //Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_LONG).show();
                                                per_addressline.setError("Please Enter Permanent Address");
                                                return false;
                                            } else {
//                                                if (per_city.getText().toString().equalsIgnoreCase("")) {
//                                                    //Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_LONG).show();
//                                                    per_city.setError("Please Enter Permanent Address City");
//                                                    return false;
//                                                } else {
                                                    if (per_pincode.getText().toString().equalsIgnoreCase("")) {
                                                        String pincode = per_pincode.getText().toString();
                                                        Pattern pincodePattern = Pattern.compile("[0-9]{6}");
                                                        boolean isValidpincode = pincodePattern.matcher(pincode).matches();
                                                        if(!isValidpincode){
                                                            per_pincode.setError("Please Enter Valid Pincode");
                                                        }
                                                        return false;
                                                    } else {
                                                        return true;
                                                    }
                                                //}
                                            }
                                         }
                                   // }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean check_router(boolean ezetap_cheked) {

        if (cb_router.isChecked() && cb_security.isChecked()&&(cb_gpon.isChecked())) {
            if (router_validation() && security_validation()&& gpon_validation()) {
                if(router_security_validation()){
                    //ll_separate_payment.setVisibility(View.VISIBLE);
                    MyUtils.l("total amount",":"+total_amount);
                    MyUtils.l("rs_router_amount", ":"+rs_router_amount);
                    MyUtils.l("rs_security_amount", ":"+rs_security_amount);
                    rs_total_amount =    rs_router_amount+rs_security_amount;
                    MyUtils.l("rs_total_amount", ":"+rs_total_amount);
                    //et_rs_amount.setText(String.valueOf(rs_total_amount));
                    if (package_Validation()) {
                        return after_package_validation(ezetap_cheked);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            if(cb_router.isChecked()&&cb_security.isChecked()){
                if (router_validation() && security_validation()){
                    if(router_security_validation()){
                        if (package_Validation()) {
                            return after_package_validation(ezetap_cheked);
                        } else {
                            return false;
                        }
                    }else{
                        return  false;
                    }
                }else{
                    return  false;
                }
            }else{
                if(cb_security.isChecked()&& cb_gpon.isChecked()){
                    if(security_validation() && gpon_validation()){
                        if(router_security_validation()){
                            if (package_Validation()) {
                                return after_package_validation(ezetap_cheked);
                            } else {
                                return false;
                            }
                        }else{
                            return  false;
                        }
                    }else{
                        return false;
                    }
                }else{
                    if(cb_router.isChecked()&&cb_gpon.isChecked()){
                        if(router_validation()&& gpon_validation()){
                            if(router_security_validation()){
                                if (package_Validation()) {
                                    return after_package_validation(ezetap_cheked);
                                } else {
                                    return false;
                                }
                            }else{
                                return  false;
                            }
                        }else{
                            return  false;
                        }
                    }else{
                        if (cb_router.isChecked()) {
                            if (router_validation()) {
                                if (router_security_validation()) {
                                    if (package_Validation()) {
                                        return after_package_validation(ezetap_cheked);
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            router_id = "0";
                            router_charges = Double.parseDouble("0.0");
                            router_refund = Double.parseDouble("0.0");

                            if (cb_security.isChecked()) {
                                if (security_validation()) {
                                    if (router_security_validation()) {
                                        if (package_Validation()) {
                                            return after_package_validation(ezetap_cheked);
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } else {
                                router_id = "0";
                                router_charges = Double.parseDouble("0.0");
                                router_refund = Double.parseDouble("0.0");
                                security_deposit = Double.parseDouble("0.0");
                                security_refund = Double.parseDouble("0.0");

                                if (cb_gpon.isChecked()) {
                                    if (gpon_validation()) {
                                        if (router_security_validation()) {
                                            if (package_Validation()) {
                                                return after_package_validation(ezetap_cheked);
                                            } else {
                                                return false;
                                            }
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                }else{
                                    router_id = "0";
                                    router_charges = Double.parseDouble("0.0");
                                    router_refund = Double.parseDouble("0.0");
                                    security_deposit = Double.parseDouble("0.0");
                                    security_refund = Double.parseDouble("0.0");
                                    gpon_id="0";
                                    gpon_charges= Double.parseDouble("0.0");
                                    gpon_refund= Double.parseDouble("0.0");

                                    if (package_Validation()) {
                                        return after_package_validation(ezetap_cheked);
                                    } else {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

   /* public boolean check_router(boolean ezetap_cheked) {

        if (cb_router.isChecked() && cb_security.isChecked() && (cb_gpon.isChecked()) && cb_post_forward.isChecked() && cb_natting_charges.isChecked() ) {
            if (router_validation() && security_validation() && gpon_validation() && post_forward_validation() && natt_validation()) {
                if (router_security_validation()) {
                    MyUtils.l("total amount", ":" + total_amount);
                    MyUtils.l("rs_router_amount", ":" + rs_router_amount);
                    MyUtils.l("rs_security_amount", ":" + rs_security_amount);
                    rs_total_amount = rs_router_amount + rs_security_amount;
                    MyUtils.l("rs_total_amount", ":" + rs_total_amount);
                    if (package_Validation()) {
                        return after_package_validation(ezetap_cheked);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            if (cb_router.isChecked() && cb_security.isChecked()) {
                if (router_validation() && security_validation()) {
                    if (router_security_validation()) {
                        if (package_Validation()) {
                            return after_package_validation(ezetap_cheked);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (cb_security.isChecked() && cb_gpon.isChecked()) {
                    if (security_validation() && gpon_validation()) {
                        if (router_security_validation()) {
                            if (package_Validation()) {
                                return after_package_validation(ezetap_cheked);
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    if (cb_router.isChecked() && cb_gpon.isChecked()) {
                        if (router_validation() && gpon_validation()) {
                            if (router_security_validation()) {
                                if (package_Validation()) {
                                    return after_package_validation(ezetap_cheked);
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        if (cb_router.isChecked() && cb_post_forward.isChecked()) {
                            if (router_validation() && post_forward_validation()) {
                                if (router_security_validation()) {
                                    if (package_Validation()) {
                                        return after_package_validation(ezetap_cheked);
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }else{
                            if (cb_security.isChecked() && cb_post_forward.isChecked()) {
                                if (security_validation() && post_forward_validation()) {
                                    if (router_security_validation()) {
                                        if (package_Validation()) {
                                            return after_package_validation(ezetap_cheked);
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            }else{
                                if (cb_gpon.isChecked() && cb_post_forward.isChecked()) {
                                    if (gpon_validation() && post_forward_validation()) {
                                        if (router_security_validation()) {
                                            if (package_Validation()) {
                                                return after_package_validation(ezetap_cheked);
                                            } else {
                                                return false;
                                            }
                                        } else {
                                            return false;
                                        }
                                    } else {
                                        return false;
                                    }
                                }else{
                                    if (cb_router.isChecked() && cb_security.isChecked() && cb_post_forward.isChecked()) {
                                        if (router_validation() && security_validation() && post_forward_validation()) {
                                            if (router_security_validation()) {
                                                if (package_Validation()) {
                                                    return after_package_validation(ezetap_cheked);
                                                } else {
                                                    return false;
                                                }
                                            } else {
                                                return false;
                                            }
                                        } else {
                                            return false;
                                        }
                                    }else{
                                        if (cb_security.isChecked() && cb_gpon.isChecked() && cb_post_forward.isChecked()) {
                                            if (security_validation() && gpon_validation() && post_forward_validation()) {
                                                if (router_security_validation()) {
                                                    if (package_Validation()) {
                                                        return after_package_validation(ezetap_cheked);
                                                    } else {
                                                        return false;
                                                    }
                                                } else {
                                                    return false;
                                                }
                                            } else {
                                                return false;
                                            }
                                        }else{
                                            if (cb_router.isChecked() && cb_gpon.isChecked() && cb_post_forward.isChecked()) {
                                                if (router_validation() && gpon_validation() && post_forward_validation()) {
                                                    if (router_security_validation()) {
                                                        if (package_Validation()) {
                                                            return after_package_validation(ezetap_cheked);
                                                        } else {
                                                            return false;
                                                        }
                                                    } else {
                                                        return false;
                                                    }
                                                } else {
                                                    return false;
                                                }
                                            }else{
                                                if (cb_router.isChecked() && cb_security.isChecked() && cb_gpon.isChecked() && cb_post_forward.isChecked()) {
                                                    if (router_validation() && security_validation() && gpon_validation() && post_forward_validation()) {
                                                        if (router_security_validation()) {
                                                            if (package_Validation()) {
                                                                return after_package_validation(ezetap_cheked);
                                                            } else {
                                                                return false;
                                                            }
                                                        } else {
                                                            return false;
                                                        }
                                                    } else {
                                                        return false;
                                                    }
                                                }else{
                                                    if (cb_router.isChecked() && cb_natting_charges.isChecked()) {
                                                        if (router_validation() && natt_validation() ) {
                                                            if (router_security_validation()) {
                                                                if (package_Validation()) {
                                                                    return after_package_validation(ezetap_cheked);
                                                                } else {
                                                                    return false;
                                                                }
                                                            } else {
                                                                return false;
                                                            }
                                                        } else {
                                                            return false;
                                                        }
                                                    }else{
                                                        if (cb_security.isChecked() && cb_natting_charges.isChecked()) {
                                                            if (security_validation() && natt_validation() ) {
                                                                if (router_security_validation()) {
                                                                    if (package_Validation()) {
                                                                        return after_package_validation(ezetap_cheked);
                                                                    } else {
                                                                        return false;
                                                                    }
                                                                } else {
                                                                    return false;
                                                                }
                                                            } else {
                                                                return false;
                                                            }
                                                        }else{
                                                            if (cb_gpon.isChecked() && cb_natting_charges.isChecked()) {
                                                                if (gpon_validation() && natt_validation() ) {
                                                                    if (router_security_validation()) {
                                                                        if (package_Validation()) {
                                                                            return after_package_validation(ezetap_cheked);
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    } else {
                                                                        return false;
                                                                    }
                                                                } else {
                                                                    return false;
                                                                }
                                                            }else{
                                                                if (cb_post_forward.isChecked() && cb_natting_charges.isChecked()) {
                                                                    if (post_forward_validation() && natt_validation() ) {
                                                                        if (router_security_validation()) {
                                                                            if (package_Validation()) {
                                                                                return after_package_validation(ezetap_cheked);
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    } else {
                                                                        return false;
                                                                    }
                                                                }else{
                                                                    if (cb_router.isChecked() && cb_security.isChecked() &&cb_natting_charges.isChecked()) {
                                                                        if (router_validation() && security_validation() &&natt_validation() ) {
                                                                            if (router_security_validation()) {
                                                                                if (package_Validation()) {
                                                                                    return after_package_validation(ezetap_cheked);
                                                                                } else {
                                                                                    return false;
                                                                                }
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        } else {
                                                                            return false;
                                                                        }
                                                                    }else{
                                                                        if (cb_security.isChecked() && cb_gpon.isChecked() &&cb_natting_charges.isChecked()) {
                                                                            if (security_validation() && gpon_validation() &&natt_validation() ) {
                                                                                if (router_security_validation()) {
                                                                                    if (package_Validation()) {
                                                                                        return after_package_validation(ezetap_cheked);
                                                                                    } else {
                                                                                        return false;
                                                                                    }
                                                                                } else {
                                                                                    return false;
                                                                                }
                                                                            } else {
                                                                                return false;
                                                                            }
                                                                        }else{
                                                                            if (cb_gpon.isChecked() && cb_post_forward.isChecked() &&cb_natting_charges.isChecked()) {
                                                                                if (gpon_validation() && post_forward_validation() &&natt_validation() ) {
                                                                                    if (router_security_validation()) {
                                                                                        if (package_Validation()) {
                                                                                            return after_package_validation(ezetap_cheked);
                                                                                        } else {
                                                                                            return false;
                                                                                        }
                                                                                    } else {
                                                                                        return false;
                                                                                    }
                                                                                } else {
                                                                                    return false;
                                                                                }
                                                                            }else{
                                                                                if (cb_router.isChecked() && cb_security.isChecked() &&cb_gpon.isChecked() &&cb_natting_charges.isChecked()) {
                                                                                    if (router_validation() && security_validation() && gpon_validation() &&natt_validation() ) {
                                                                                        if (router_security_validation()) {
                                                                                            if (package_Validation()) {
                                                                                                return after_package_validation(ezetap_cheked);
                                                                                            } else {
                                                                                                return false;
                                                                                            }
                                                                                        } else {
                                                                                            return false;
                                                                                        }
                                                                                    } else {
                                                                                        return false;
                                                                                    }
                                                                                }else{
                                                                                    if (cb_security.isChecked() && cb_gpon.isChecked() &&cb_post_forward.isChecked() &&cb_natting_charges.isChecked()) {
                                                                                        if (security_validation() && gpon_validation() && gpon_validation() && post_forward_validation() && natt_validation() ) {
                                                                                            if (router_security_validation()) {
                                                                                                if (package_Validation()) {
                                                                                                    return after_package_validation(ezetap_cheked);
                                                                                                } else {
                                                                                                    return false;
                                                                                                }
                                                                                            } else {
                                                                                                return false;
                                                                                            }
                                                                                        } else {
                                                                                            return false;
                                                                                        }
                                                                                    }else{
                                                                                        if (cb_router.isChecked() && cb_gpon.isChecked() &&cb_post_forward.isChecked() &&cb_natting_charges.isChecked()) {
                                                                                            if (router_validation() && gpon_validation() && gpon_validation() && post_forward_validation() && natt_validation() ) {
                                                                                                if (router_security_validation()) {
                                                                                                    if (package_Validation()) {
                                                                                                        return after_package_validation(ezetap_cheked);
                                                                                                    } else {
                                                                                                        return false;
                                                                                                    }
                                                                                                } else {
                                                                                                    return false;
                                                                                                }
                                                                                            } else {
                                                                                                return false;
                                                                                            }
                                                                                        }else{
                                                                                            if (cb_router.isChecked()) {
                                                                                                if (router_validation()) {
                                                                                                    if (router_security_validation()) {
                                                                                                        if (package_Validation()) {
                                                                                                            return after_package_validation(ezetap_cheked);
                                                                                                        } else {
                                                                                                            return false;
                                                                                                        }
                                                                                                    } else {
                                                                                                        return false;
                                                                                                    }
                                                                                                } else {
                                                                                                    return false;
                                                                                                }
                                                                                            } else {
                                                                                                router_id = "0";
                                                                                                router_charges = "0.0";
                                                                                                router_refund = "0.0";

                                                                                                if (cb_security.isChecked()) {
                                                                                                    if (security_validation()) {
                                                                                                        if (router_security_validation()) {
                                                                                                            if (router_security_validation()) {
                                                                                                                return after_package_validation(ezetap_cheked);
                                                                                                            } else {
                                                                                                                return false;
                                                                                                            }
                                                                                                        } else {
                                                                                                            return false;
                                                                                                        }
                                                                                                    } else {
                                                                                                        return false;
                                                                                                    }
                                                                                                } else {
                                                                                                    router_id = "0";
                                                                                                    router_charges = "0.0";
                                                                                                    router_refund = "0.0";
                                                                                                    security_deposit = "0.0";
                                                                                                    security_refund = "0.0";

                                                                                                    if (cb_gpon.isChecked()) {
                                                                                                        if (gpon_validation()) {
                                                                                                            if (router_security_validation()) {
                                                                                                                if (package_Validation()) {
                                                                                                                    return after_package_validation(ezetap_cheked);
                                                                                                                } else {
                                                                                                                    return false;
                                                                                                                }
                                                                                                            } else {
                                                                                                                return false;
                                                                                                            }
                                                                                                        } else {
                                                                                                            return false;
                                                                                                        }
                                                                                                    } else {
                                                                                                        router_id = "0";
                                                                                                        router_charges = "0.0";
                                                                                                        router_refund = "0.0";
                                                                                                        security_deposit = "0.0";
                                                                                                        security_refund = "0.0";
                                                                                                        gpon_id = "0";
                                                                                                        gpon_charges = "0.0";
                                                                                                        gpon_refund = "0.0";

                                                                                                        if (cb_post_forward.isChecked()) {
                                                                                                            if (post_forward_validation()) {
                                                                                                                if (router_security_validation()) {
                                                                                                                    if (package_Validation()) {
                                                                                                                        return after_package_validation(ezetap_cheked);
                                                                                                                    } else {
                                                                                                                        return false;
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    return false;
                                                                                                                }
                                                                                                            } else {
                                                                                                                return false;
                                                                                                            }
                                                                                                        } else {
                                                                                                            router_id = "0";
                                                                                                            router_charges = "0.0";
                                                                                                            router_refund = "0.0";
                                                                                                            security_deposit = "0.0";
                                                                                                            security_refund = "0.0";
                                                                                                            gpon_id = "0";
                                                                                                            gpon_charges = "0.0";
                                                                                                            gpon_refund = "0.0";
                                                                                                            post_charges ="0.0";
                                                                                                            post_refund ="0.0";

                                                                                                            if (cb_natting_charges.isChecked()) {
                                                                                                                if (natt_validation()) {
                                                                                                                    if (router_security_validation()) {
                                                                                                                        if (package_Validation()) {
                                                                                                                            return after_package_validation(ezetap_cheked);
                                                                                                                        } else {
                                                                                                                            return false;
                                                                                                                        }
                                                                                                                    } else {
                                                                                                                        return false;
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    return false;
                                                                                                                }
                                                                                                            } else {
                                                                                                                router_id = "0";
                                                                                                                router_charges = "0.0";
                                                                                                                router_refund = "0.0";
                                                                                                                security_deposit = "0.0";
                                                                                                                security_refund = "0.0";
                                                                                                                gpon_id = "0";
                                                                                                                gpon_charges = "0.0";
                                                                                                                gpon_refund = "0.0";
                                                                                                                post_charges ="0.0";
                                                                                                                post_refund ="0.0";

                                                                                                                if (package_Validation()) {
                                                                                                                    return after_package_validation(ezetap_cheked);
                                                                                                                } else {
                                                                                                                    return false;
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/


    public boolean router_validation() {
        if (selected_router_id.equalsIgnoreCase("-1")) {

            ll_router_details.setVisibility(View.VISIBLE);
            //ll_cb_router_security.setVisibility(View.VISIBLE);
            ll_total_charges_selection.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Please Select Router Type", Toast.LENGTH_LONG).show();
            return false;
        } else {
           // router_id = selected_router_id;
            if (rb_chargable.isChecked()) {
                if (et_router_charges.getText().toString().equalsIgnoreCase("")) {
                    ll_router_details.setVisibility(View.VISIBLE);
                    //ll_cb_router_security.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    //Toast.makeText(getApplicationContext(), "Please Enter Router Charges", Toast.LENGTH_LONG).show();
                    et_router_charges.setError("Please Enter Router Charges");
                    return false;
                } else {
                    router_charges = Double.parseDouble(et_router_charges.getText().toString());
                    rs_router_amount = Double.valueOf(et_router_charges.getText().toString());
                }
            } else {
                if (rb_refund.isChecked()) {
                    if (et_router_charges.getText().toString().equalsIgnoreCase("")) {
                        ll_router_details.setVisibility(View.VISIBLE);
                        //ll_cb_router_security.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.VISIBLE);
                        if (rb_separate.isChecked()) {
                            ll_separate_payment.setVisibility(View.VISIBLE);
                        }
                        // Toast.makeText(getApplicationContext(), "Please Enter Router Charges", Toast.LENGTH_LONG).show();
                        et_router_charges.setError("Please Enter Router Charges");
                        return false;
                    } else {
                        router_charges = Double.parseDouble(et_router_charges.getText().toString());
                        rs_router_amount = Double.valueOf(et_router_charges.getText().toString());
                        if (et_router_refund.getText().toString().equalsIgnoreCase("")) {
                            ll_router_details.setVisibility(View.VISIBLE);
                            //ll_cb_router_security.setVisibility(View.VISIBLE);
                            ll_total_charges_selection.setVisibility(View.VISIBLE);
                            if (rb_separate.isChecked()) {
                                ll_separate_payment.setVisibility(View.VISIBLE);
                            }
                            //Toast.makeText(getApplicationContext(), "Please Enter Router Refund Charges", Toast.LENGTH_LONG).show();
                            et_router_refund.setError("Please Enter Router Refund Charges");
                            return false;
                        } else {
                            router_refund = Double.parseDouble(et_router_refund.getText().toString());

                            if ((Double.parseDouble(et_router_charges.getText().toString())) < (Double.parseDouble(et_router_refund.getText().toString()))) {
                                ll_router_details.setVisibility(View.VISIBLE);
                                //ll_cb_router_security.setVisibility(View.VISIBLE);
                                ll_total_charges_selection.setVisibility(View.VISIBLE);
                                if (rb_separate.isChecked()) {
                                    ll_separate_payment.setVisibility(View.VISIBLE);
                                }
                                Toast.makeText(getApplicationContext(), "Please Enter Refund Amount Less than Deposit Amount", Toast.LENGTH_LONG).show();
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }


    public boolean security_validation() {
        if (rb_security_refund.isChecked()) {
            if (et_security_charges.getText().toString().equalsIgnoreCase("")) {
                security_deposit = 0.0;
                ll_security_details.setVisibility(View.VISIBLE);
                // ll_cb_router_security.setVisibility(View.VISIBLE);
                ll_total_charges_selection.setVisibility(View.VISIBLE);
                if (rb_separate.isChecked()) {
                    ll_separate_payment.setVisibility(View.VISIBLE);
                }
                // Toast.makeText(getApplicationContext(), "Please Enter Security Deposit charges", Toast.LENGTH_LONG).show();
                et_security_charges.setError("Please Enter Security Deposit charges");
                return false;
            } else {
                security_deposit = Double.parseDouble(et_security_charges.getText().toString());
                rs_security_amount = Double.valueOf(et_security_charges.getText().toString());
                if (et_security_refund.getText().toString().equalsIgnoreCase("")) {
                    security_refund = 0.0;
                    ll_security_details.setVisibility(View.VISIBLE);
                    // ll_cb_router_security.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    // Toast.makeText(getApplicationContext(), "Please Enter Security Deposit Refund charges", Toast.LENGTH_LONG).show();
                    et_security_refund.setError("Please Enter Security Deposit charges");
                    return false;
                } else {
                    security_refund = Double.parseDouble(et_security_refund.getText().toString());
                    if ((Double.parseDouble(et_security_charges.getText().toString())) < (Double.parseDouble(et_security_refund.getText().toString()))) {
                        ll_security_details.setVisibility(View.VISIBLE);
                        //ll_cb_router_security.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.VISIBLE);
                        if (rb_separate.isChecked()) {
                            ll_separate_payment.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getApplicationContext(), "Please Enter Refund Amount Less than Deposit Amount", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
        } else {
            if (rb_nonrefund.isChecked()) {
                if (et_security_charges.getText().toString().equalsIgnoreCase("")) {
                    security_deposit = 0.0;
                    ll_security_details.setVisibility(View.VISIBLE);
                    //ll_cb_router_security.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getApplicationContext(), "Please Enter Security Deposit charges", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    security_deposit = Double.parseDouble(et_security_charges.getText().toString());
                    rs_security_amount = Double.valueOf(et_security_charges.getText().toString());
                }
            }
        }
        ll_separate_payment.setVisibility(View.GONE);
        return true;
    }

    public boolean gpon_validation() {
        if (selected_gpon_id.equalsIgnoreCase("-1")) {
            selected_gpon_id = gpon_id = "0";
            ll_gpon_details.setVisibility(View.VISIBLE);
            //ll_cb_router_security.setVisibility(View.VISIBLE);
            ll_total_charges_selection.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(), "Please Select Gpon Type", Toast.LENGTH_LONG).show();
            return false;
        } else {
            gpon_id = selected_gpon_id;
            if (rb_chargable_gpon.isChecked()) {
                if (et_gpon_charges.getText().toString().equalsIgnoreCase("")) {
                    ll_gpon_details.setVisibility(View.VISIBLE);
                    // ll_cb_router_security.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    //Toast.makeText(getApplicationContext(), "Please Enter Gpon Charges", Toast.LENGTH_LONG).show();
                    et_gpon_charges.setError("Please Enter Gpon Charges");
                    return false;
                } else {
                    gpon_charges = Double.parseDouble(et_gpon_charges.getText().toString());
                    rs_gpon_amount = Double.valueOf(et_gpon_charges.getText().toString());
                }
            } else {
                if (rb_refund_gpon.isChecked()) {
                    if (et_gpon_charges.getText().toString().equalsIgnoreCase("")) {
                        ll_gpon_details.setVisibility(View.VISIBLE);
                        //ll_cb_router_security.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.VISIBLE);
                        if (rb_separate.isChecked()) {
                            ll_separate_payment.setVisibility(View.VISIBLE);
                        }
                        // Toast.makeText(getApplicationContext(), "Please Enter Gpon Charges", Toast.LENGTH_LONG).show();
                        et_gpon_charges.setError("Please Enter Gpon Charges");
                        return false;
                    } else {
                        gpon_charges = Double.parseDouble(et_gpon_charges.getText().toString());
                        rs_gpon_amount = Double.valueOf(et_gpon_charges.getText().toString());
                        if (et_gpon_refund.getText().toString().equalsIgnoreCase("")) {
                            ll_gpon_details.setVisibility(View.VISIBLE);
                            // ll_cb_router_security.setVisibility(View.VISIBLE);
                            ll_total_charges_selection.setVisibility(View.VISIBLE);
                            if (rb_separate.isChecked()) {
                                ll_separate_payment.setVisibility(View.VISIBLE);
                            }
                            //Toast.makeText(getApplicationContext(), "Please Enter Gpon Refund Charges", Toast.LENGTH_LONG).show();
                            et_gpon_refund.setError("Please Enter Gpon Refund Charges");
                            return false;
                        } else {
                            gpon_refund = Double.parseDouble(et_gpon_refund.getText().toString());

                            if ((Double.parseDouble(et_gpon_charges.getText().toString())) < (Double.parseDouble(et_router_refund.getText().toString()))) {
                                ll_gpon_details.setVisibility(View.VISIBLE);
                                //ll_cb_router_security.setVisibility(View.VISIBLE);
                                ll_total_charges_selection.setVisibility(View.VISIBLE);
                                if (rb_separate.isChecked()) {
                                    ll_separate_payment.setVisibility(View.VISIBLE);
                                }
                                Toast.makeText(getApplicationContext(), "Please Enter Refund Amount Less than Deposit Amount", Toast.LENGTH_LONG).show();
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }


    public boolean post_forward_validation() {
        if (rb_post_refund.isChecked()) {
            if (et_post_charges.getText().toString().equalsIgnoreCase("")) {
                post_charges = Double.parseDouble("0");
                ll_post_details.setVisibility(View.VISIBLE);
                // ll_cb_router_security.setVisibility(View.VISIBLE);
                ll_total_charges_selection.setVisibility(View.VISIBLE);
                if (rb_separate.isChecked()) {
                    ll_separate_payment.setVisibility(View.VISIBLE);
                }
                // Toast.makeText(getApplicationContext(), "Please Enter Security Deposit charges", Toast.LENGTH_LONG).show();
                et_post_charges.setError("Please Enter Post Forwarding Deposit charges");
                return false;
            } else {
                post_charges = Double.parseDouble(et_post_charges.getText().toString());
                rs_post_amount = Double.valueOf(et_post_charges.getText().toString());
                if (et_post_refund.getText().toString().equalsIgnoreCase("")) {
                    post_refund = Double.parseDouble("0");
                    ll_post_details.setVisibility(View.VISIBLE);
                    // ll_cb_router_security.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    // Toast.makeText(getApplicationContext(), "Please Enter Security Deposit Refund charges", Toast.LENGTH_LONG).show();
                    et_post_refund.setError("Please Enter Post Forwarding Refund charges");
                    return false;
                } else {
                    post_refund = Double.parseDouble(et_post_refund.getText().toString());
                    if ((Double.parseDouble(et_post_charges.getText().toString())) < (Double.parseDouble(et_post_refund.getText().toString()))) {
                        ll_post_details.setVisibility(View.VISIBLE);
                        //ll_cb_router_security.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.VISIBLE);
                        if (rb_separate.isChecked()) {
                            ll_separate_payment.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getApplicationContext(), "Please Enter Refund Amount Less than Deposit Amount", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
        } else {
            if (rb_post_nonrefund.isChecked()) {
                if (et_post_charges.getText().toString().equalsIgnoreCase("")) {
                    post_charges = Double.parseDouble("0");
                    ll_post_details.setVisibility(View.VISIBLE);

                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getApplicationContext(), "Please Enter Post Forwarding Deposit charges", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    post_charges = Double.parseDouble(et_post_charges.getText().toString());
                    rs_post_amount = Double.valueOf(et_post_charges.getText().toString());
                }
            }
        }
        ll_separate_payment.setVisibility(View.GONE);
        return true;
    }


    public boolean natt_validation() {
        if (rb_natt_refund.isChecked()) {
            if (et_natt_charges.getText().toString().equalsIgnoreCase("")) {
                natt_charges = Double.parseDouble("0");
                ll_natting_details.setVisibility(View.VISIBLE);
                // ll_cb_router_security.setVisibility(View.VISIBLE);
                ll_total_charges_selection.setVisibility(View.VISIBLE);
                if (rb_separate.isChecked()) {
                    ll_separate_payment.setVisibility(View.VISIBLE);
                }
                // Toast.makeText(getApplicationContext(), "Please Enter Security Deposit charges", Toast.LENGTH_LONG).show();
                et_natt_charges.setError("Please Enter Natting Deposit charges");
                return false;
            } else {
                natt_charges = Double.parseDouble(et_natt_charges.getText().toString());
                rs_natt_amount = Double.valueOf(et_natt_charges.getText().toString());
                if (et_natt_refund.getText().toString().equalsIgnoreCase("")) {
                    natt_refund = Double.parseDouble("0");
                    ll_post_details.setVisibility(View.VISIBLE);
                    // ll_cb_router_security.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    // Toast.makeText(getApplicationContext(), "Please Enter Security Deposit Refund charges", Toast.LENGTH_LONG).show();
                    et_natt_refund.setError("Please Enter Natting Refund charges");
                    return false;
                } else {
                    natt_refund = Double.parseDouble(et_natt_refund.getText().toString());
                    if ((Double.parseDouble(et_natt_charges.getText().toString())) < (Double.parseDouble(et_natt_refund.getText().toString()))) {
                        ll_post_details.setVisibility(View.VISIBLE);
                        //ll_cb_router_security.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.VISIBLE);
                        if (rb_separate.isChecked()) {
                            ll_separate_payment.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getApplicationContext(), "Please Enter Refund Amount Less than Deposit Amount", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
        } else {
            if (rb_natt_nonrefund.isChecked()) {
                if (et_natt_charges.getText().toString().equalsIgnoreCase("")) {
                    natt_charges = Double.parseDouble("0");
                    ll_post_details.setVisibility(View.VISIBLE);

                    ll_total_charges_selection.setVisibility(View.VISIBLE);
                    if (rb_separate.isChecked()) {
                        ll_separate_payment.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getApplicationContext(), "Please Enter Natting Deposit charges", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    natt_charges = Double.parseDouble(et_natt_charges.getText().toString());
                    rs_natt_amount = Double.valueOf(et_natt_charges.getText().toString());
                }
            }
        }
        ll_separate_payment.setVisibility(View.GONE);
        return true;
    }


    public boolean router_security_validation() {
        if (rb_same.isChecked()) {
            ll_separate_payment.setVisibility(View.GONE);
            return true;
        } else {
            if (rb_separate.isChecked()) {
                ll_separate_payment.setVisibility(View.VISIBLE);
                if (rb_rs_cash.isChecked()) {
                    ll_separate_payment.setVisibility(View.GONE);
                    return true;
                } else if (rb_rs_cheque.isChecked()) {
                    if (et_rs_cheque_dd_no.getText().toString().equalsIgnoreCase("")) {

                        //ll_cb_router_security.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.VISIBLE);
                        ll_separate_payment.setVisibility(View.GONE);

                        if (cb_router.isChecked()) {
                            ll_router_details.setVisibility(View.VISIBLE);
                        }
                        if (cb_security.isChecked()) {
                            ll_security_details.setVisibility(View.VISIBLE);
                        }
                        if (cb_gpon.isChecked()) {
                            ll_gpon_details.setVisibility(View.VISIBLE);
                        }
                        if (cb_post_forward.isChecked()) {
                            ll_post_details.setVisibility(View.VISIBLE);
                        }
                        if (rb_separate.isChecked()) {
                            ll_separate_payment.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(this, "Please Enter Cheque Number", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        if (et_rs_bank_Name.getText().toString().equalsIgnoreCase("")) {

                            //ll_cb_router_security.setVisibility(View.VISIBLE);
                            ll_total_charges_selection.setVisibility(View.VISIBLE);
                            ll_separate_payment.setVisibility(View.GONE);
                            if (cb_router.isChecked()) {
                                ll_router_details.setVisibility(View.VISIBLE);
                            }
                            if (cb_security.isChecked()) {
                                ll_security_details.setVisibility(View.VISIBLE);
                            }
                            if (cb_gpon.isChecked()) {
                                ll_gpon_details.setVisibility(View.VISIBLE);
                            }
                            if (rb_separate.isChecked()) {
                                ll_separate_payment.setVisibility(View.VISIBLE);
                            }

                            Toast.makeText(this, "Please Enter Bank Name", Toast.LENGTH_LONG).show();
                            return false;
                        } else {

                        }
                        ll_separate_payment.setVisibility(View.GONE);
                    }
                    return true;
                } else if (rb_rs_dd.isChecked()) {

                    if (et_rs_cheque_dd_no.getText().toString().equalsIgnoreCase("")) {
                        //ll_cb_router_security.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.VISIBLE);
                        if (cb_router.isChecked()) {
                            ll_router_details.setVisibility(View.VISIBLE);
                        }
                        if (cb_security.isChecked()) {
                            ll_security_details.setVisibility(View.VISIBLE);
                        }
                        if (cb_gpon.isChecked()) {
                            ll_gpon_details.setVisibility(View.VISIBLE);
                        }
                        if (cb_post_forward.isChecked()) {
                            ll_post_details.setVisibility(View.VISIBLE);
                        }
                        if (rb_separate.isChecked()) {
                            ll_separate_payment.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(this, "Please Enter DD Number", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        if (et_rs_bank_Name.getText().toString().equalsIgnoreCase("")) {
                            //ll_cb_router_security.setVisibility(View.VISIBLE);
                            ll_total_charges_selection.setVisibility(View.VISIBLE);
                            if (cb_router.isChecked()) {
                                ll_router_details.setVisibility(View.VISIBLE);
                            }
                            if (cb_security.isChecked()) {
                                ll_security_details.setVisibility(View.VISIBLE);
                            }
                            if (cb_gpon.isChecked()) {
                                ll_gpon_details.setVisibility(View.VISIBLE);
                            }
                            if (cb_post_forward.isChecked()) {
                                ll_post_details.setVisibility(View.VISIBLE);
                            }
                            if (rb_separate.isChecked()) {
                                ll_separate_payment.setVisibility(View.VISIBLE);
                            }
                            Toast.makeText(this, "Please Enter Bank Name", Toast.LENGTH_LONG).show();
                            return false;
                        } else {

                        }
                        ll_separate_payment.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(this, "Please select Payment Option", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            } else {
                return true;
            }
        }
    }


    public boolean package_Validation() {
        if (alPackage.size() > 0) {
            if (package_id.equalsIgnoreCase("-1")) {
                // ll_package_details.setVisibility(View.VISIBLE);
                ll_total_charges_selection.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please Select Package Name", Toast.LENGTH_LONG).show();
                return false;
            } else {
                if (manager_id.equalsIgnoreCase("-1")) {
                    //ll_package_details.setVisibility(View.VISIBLE);
                    ll_total_charges_selection.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Please Select Channel Manager", Toast.LENGTH_LONG).show();
                    return false;
                } else {
                    if (source_id.equalsIgnoreCase("-1")) {
                        //ll_package_details.setVisibility(View.VISIBLE);
                        ll_total_charges_selection.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Please Select Source User", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        if (et_installation.getText().toString().equals("")) {
                            router_security_hide();
                            //ll_cb_router_security.setVisibility(View.GONE);
                            //ll_package_details.setVisibility(View.VISIBLE);
                            ll_total_charges_selection.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Please Enter Installation Charges", Toast.LENGTH_LONG).show();
                            return false;
                        } else {
                            //ll_package_details.setVisibility(View.GONE);
                            if (cabling_id.equalsIgnoreCase("-1")) {
                                cabling_id = "0";
                                Toast.makeText(getApplicationContext(), "Please Select Cabling Type", Toast.LENGTH_LONG).show();
                                return false;
                            } else {
                                //ll_package_details.setVisibility(View.GONE);
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            //AlertsBoxFactory.showAlert("Packge List is empty"+"\n"+"Please select Area again!", CAF_Activity.this);
            //DialogUtils.show_dialog(CAF_Activity.this, "Confirmation", "Packge List is empty" + "\n" + "Please select Area again!", false);
            return false;
        }

    }


    public boolean after_package_validation(boolean is_ezetap_checked) {


        if (selected_mode.equalsIgnoreCase(value)) {
            if (payment_Validation()) {
                MyUtils.l("from", "validation complete");

                if (!is_ezetap_checked) {

                    hm_data_value = validation_to_createDirectory();
                    MyUtils.l("Hashmap Data", ":" + hm_data.size());
                    folder_name = et_firstname.getText().toString() + "_" + et_lastname.getText().toString() + "(" + et_caf_no.getText().toString() + ")";
                    hm_upload_image.put(folder_name, hm_data);
                    MyUtils.l("Hashmap upload data", ":" + hm_upload_image.size());

                    if (hm_data_value.size() > 0) {
                        UploadPictureTask createDirectoryTask = new UploadPictureTask(hm_upload_image);
                        createDirectoryTask.execute();
                    } else {

                    }

                }

                return true;

            } else {
                return false;
            }
        } else {
            MyUtils.l("from", "validation complete");

            if (!is_ezetap_checked) {

                hm_data_value = validation_to_createDirectory();
                MyUtils.l("Hashmap Data", ":" + hm_data.size());
                folder_name = et_firstname.getText().toString() + "_" + et_lastname.getText().toString() + "(" + et_caf_no.getText().toString() + ")";
                hm_upload_image.put(folder_name, hm_data);
                MyUtils.l("Hashmap upload data", ":" + hm_upload_image.size());

                if (hm_data_value.size() > 0) {
                    UploadPictureTask createDirectoryTask = new UploadPictureTask(hm_upload_image);
                    createDirectoryTask.execute();
                } else {

                }

            }

            return true;
        }

    }

    public boolean router_security_hide() {
        if (cb_router.isChecked()) {
            if (router_validation()) {
                //ll_cb_router_security.setVisibility(View.GONE);
                ll_router_details.setVisibility(View.GONE);
                ll_security_details.setVisibility(View.GONE);
            }
        } else {
            if (cb_security.isChecked()) {
                if (security_validation()) {
                    //ll_cb_router_security.setVisibility(View.GONE);
                    ll_router_details.setVisibility(View.GONE);
                    ll_security_details.setVisibility(View.GONE);
                }
            } else {
                if (cb_gpon.isChecked()) {
                    if (gpon_validation()) {
                        // ll_cb_router_security.setVisibility(View.GONE);
                        ll_router_details.setVisibility(View.GONE);
                        ll_security_details.setVisibility(View.GONE);
                        ll_gpon_details.setVisibility(View.GONE);
                    }
                } else {
                    if ((cb_router.isChecked()) && (cb_security.isChecked())) {
                        if ((router_validation()) && (security_validation())) {
                            //ll_cb_router_security.setVisibility(View.GONE);
                            ll_router_details.setVisibility(View.GONE);
                            ll_security_details.setVisibility(View.GONE);
                        }
                    } else {
                        if (cb_router.isChecked() && cb_gpon.isChecked()) {
                            if (security_validation() && gpon_validation()) {
                                //ll_cb_router_security.setVisibility(View.GONE);
                                ll_gpon_details.setVisibility(View.GONE);
                                ll_security_details.setVisibility(View.GONE);
                            }
                        } else {
                            if (cb_router.isChecked() && cb_gpon.isChecked()) {
                                if (router_validation() && gpon_validation()) {
                                    //ll_cb_router_security.setVisibility(View.GONE);
                                    ll_gpon_details.setVisibility(View.GONE);
                                    ll_router_details.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    public double calculate_amount() {

        try {
            pkg_amount = Double.valueOf(et_pkgamount.getText().toString());

            if(et_installation.getText().toString().equals("")){
                installation_amount = 0.0;
            }else {
                installation_amount = Double.valueOf((et_installation.getText().toString()));
            }
            if(et_installation_discount.getText().toString().equals("")){
                installation_discount_amount = 0.0;
            }else {
                installation_discount_amount = Double.valueOf(et_installation_discount.getText().toString());
            }
            if(et_pkgamount_discount.getText().toString().equals("")){
                pkg_discount_amount = 0.0;
            }else {
                pkg_discount_amount = Double.valueOf(et_pkgamount_discount.getText().toString());
            }
            if(installation_discount_amount == 0.0 || pkg_discount_amount==0.0){
                if(pkg_discount_amount==0.0){
                    if((pkg_discount_amount ==0.0) &&(installation_discount_amount==0.0)){
                        total_amount = pkg_amount + installation_amount;
                        add_total_amount(total_amount);
                    }else {
                        total_amount = pkg_amount + installation_amount - installation_discount_amount;
                        add_total_amount(total_amount);
                    }
                }else {
                    total_amount = pkg_amount + installation_amount - pkg_discount_amount;
                    add_total_amount(total_amount);
                }
            }else {
                total_amount = pkg_amount + installation_amount - pkg_discount_amount - installation_discount_amount;
                add_total_amount(total_amount);
            }

        } catch(NumberFormatException ex){
                // handle exception
            ex.printStackTrace();
            }
        return total_amount;
    }

    public boolean payment_Validation() {
        if (et_chequeno.getText().toString().equals("")) {
            //ll_package_details.setVisibility(View.VISIBLE);
            linear_cheque_details.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Please Enter Cheque No", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (et_bankName.getText().toString().equals("")) {
                //ll_package_details.setVisibility(View.VISIBLE);
                linear_cheque_details.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Please Enter Bank Name", Toast.LENGTH_LONG).show();
                return false;
            } else {
                linear_cheque_details.setVisibility(View.GONE);
                return true;
            }
        }
    }


    public void setMiniCAF_details() {
        if(cb_router.isChecked()){
            r_key=1;
            cb_value= "Router";
            if(!selected_router_id.equalsIgnoreCase("-1")){
                if(!et_router_model.getText().toString().equalsIgnoreCase("")) {
                    if(!et_router_charges.getText().toString().equalsIgnoreCase("")) {
                        r_key = 0;

                    }else{ }
                }else{ }
            }else{ }

        }else{
            r_key=0;
        }
        if(cb_security.isChecked()){
            s_key=1;
            cb_value = "Security";
                if(!et_security_charges.getText().toString().equalsIgnoreCase("")) {
                    if(!et_security_refund.getText().toString().equalsIgnoreCase("")) {
                        if (!et_security_description.getText().toString().equalsIgnoreCase("")) {
                            s_key=0;
                        }else{ }
                    }else{ }
                }else{ }
        }else{
            s_key=0;
        }
        if(cb_gpon.isChecked()){
            g_key=1;
            cb_value = "Gpon";
                if(!et_gpon_model.getText().toString().equalsIgnoreCase("")) {
                    if(!et_gpon_charges.getText().toString().equalsIgnoreCase("")) {
                        g_key=0;
                    }else{ }
                }else{ }
        }else{
            g_key=0;
        }


        dsacaf = new DSACAF();
        dsacaf.setCliectAccessId(client_accesss_id);
        dsacaf.setMobLoginId(Username);
        dsacaf.setMemberLoginID(member_loginId.getText().toString());
        dsacaf.setLoginPassword(password);
        dsacaf.setIPAddress("AllowedFromAllNode");
        if (login_user_role.equalsIgnoreCase("Head")) {
            Common_Item common_Item = (Common_Item) sp_channel_manager.getSelectedItem();
            dsacaf.setCreatedBy(Integer.parseInt(common_Item.getItem_id()));
        } else {
            dsacaf.setCreatedBy(Integer.parseInt(Login_UserId));
        }
        dsacaf.setMobileNumber(et_mbnumber.getText().toString());
        dsacaf.setCAFNo(et_caf_no.getText().toString());
        dsacaf.setCAFDate(caf_filled_to_send);
        dsacaf.setRoleId(RoleId);
        dsacaf.setBindToMac(false);
        dsacaf.setIPPoolId(0);
        dsacaf.setIPNodeType(0);
        dsacaf.setCustTypeId(0);
        dsacaf.setCustSubTypeId(0);
        dsacaf.setIsPostPaid(false);
        dsacaf.setPPParentMemberId(0);
        dsacaf.setBillingCycleTypeId(-1);
        dsacaf.setNoOfMac(0);
        dsacaf.setNoOfSimUse(0);
        dsacaf.setIsIPPOE(false);
        dsacaf.setVLANID("");
        dsacaf.setSid(0);
        dsacaf.setMobileNumberSecondary("");
        dsacaf.setEmailIdSecondary("");
        dsacaf.setCablingType(0);
        if(!select_gender.isSelected()) {
            gender_value = "M";
            dsacaf.setGender(gender_value);
        }else{
            dsacaf.setGender(gender_value);
        }
        dsacaf.setDOB(dob_to_send);
        dsacaf.setCityId(Integer.parseInt(city_id));
        dsacaf.setLocationId(Integer.parseInt(location_id));
        dsacaf.setAreaId(Integer.parseInt(area_id));
        dsacaf.setSubAreaId(Integer.parseInt(sub_area_id));
        dsacaf.setBuildingId(Integer.parseInt(building_id));
        dsacaf.setTowerId(Integer.parseInt(tower_id));
        dsacaf.setFloorNo(Integer.parseInt(floor_no));
        dsacaf.setFlatNo(et_flatno.getText().toString());
        dsacaf.setPincode(Integer.parseInt(et_pincode.getText().toString()));
        dsacaf.setISPId(Integer.parseInt(isp_id));
        dsacaf.setAddress(et_addressline.getText().toString());
        dsacaf.setFirstName(et_firstname.getText().toString());
        dsacaf.setMiddleName(et_middlename.getText().toString());
        dsacaf.setLastName(et_lastname.getText().toString());
        dsacaf.setEmailId(et_emailid.getText().toString());
        dsacaf.setPanNo(et_pannumber.getText().toString());
        dsacaf.setConnectionTypeId(Integer.parseInt(conn_type_id));
        dsacaf.setPayMode(selected_mode);
        dsacaf.setPckgId(Integer.parseInt(package_id));
        dsacaf.setPckgCharge(pkg_amount);

        if(et_installation.getText().toString().equalsIgnoreCase("")){
            dsacaf.setInstCharge(0.0);
        }else {
            dsacaf.setInstCharge(Double.parseDouble(((et_installation.getText().toString()))));
        }
        dsacaf.setInstDiscountGivenBy(installtiondicbyid);
        dsacaf.setPackageDiscountGivenBy(packdicbyid);

        if(et_installation_discount.getText().toString().equalsIgnoreCase("")){
            dsacaf.setInstDiscount(0);
        }else {
            dsacaf.setInstDiscount(Integer.parseInt(((et_installation_discount.getText().toString()))));
        }

        if(et_pkgamount_discount.getText().toString().equalsIgnoreCase("")){
            dsacaf.setPackageDiscount(0);
        }else {
            dsacaf.setPackageDiscount(Integer.parseInt(((et_pkgamount_discount.getText().toString()))));
        }
        dsacaf.setStrPaymentDate(payment_date_send);
        dsacaf.setStrPackageStartDate(actication_date_send);
       // dsacaf.setActivationDate(activation_date_iv.getText().toString());
      //  dsacaf.setOpeningBalance(Double.parseDouble(et_opening_balance.getText().toString()));

        if (select_cash.isChecked()) {
            dsacaf.setPaymodeDate("");
        } else {
            dsacaf.setPaymodeDate(cheque_date_to_send);
        }

        dsacaf.setPaymodeNo(et_chequeno.getText().toString());
        dsacaf.setRoleName("A");
        dsacaf.setSourceId(0);
        if(al_user_list.size()==0){
            dsacaf.setSourceName("");
        }else{
        dsacaf.setSourceName(al_user_list.get(0).getItem_name());}
        dsacaf.setAssignTo(0);
        dsacaf.setReceiptNo("0");
        dsacaf.setBankName(et_bankName.getText().toString());
        dsacaf.setStatusId(0);

        dsacaf.setIsDSAProspect(false);
        dsacaf.setPrimisesType(false);
        dsacaf.setRMUserID(0);
        dsacaf.setKAMUserID(0);
        dsacaf.setIPNodeType(101);
        dsacaf.setNoofMACAllowed(0);
        dsacaf.setNoOfSimUse(1);
        dsacaf.setProspectId("0");
        dsacaf.setAmount(rs_total_amount);
        dsacaf.setPackageName(package_name);
        dsacaf.setActualCreatedBy(Integer.parseInt(Login_UserId));
        dsacaf.setRouterId(Integer.parseInt(selected_router_id));
        dsacaf.setGponId(Integer.parseInt(selected_gpon_id));
        if(et_router_charges.getText().toString().equals("")){
            dsacaf.setRouterCharges(0);
        }else {
            dsacaf.setRouterCharges(Double.parseDouble(et_router_charges.getText().toString()));
        }
       if(et_router_refund.getText().toString().equals("")){
            dsacaf.setRouterRefundAmt(0);
        }else{
        dsacaf.setRouterRefundAmt((router_refund));}

        if(et_security_charges.getText().toString().equals("")){
            dsacaf.setSecurityDepositAmt(0);
        }else{
        dsacaf.setSecurityDepositAmt(Double.parseDouble((et_security_charges.getText().toString())));}

        if(et_security_refund.getText().toString().equals("")){
            dsacaf.setSecurityDepositRefundAmt(0);
        }else{
        dsacaf.setSecurityDepositRefundAmt(Double.parseDouble((et_security_refund.getText().toString())));}


        dsacaf.setSecurityDepositType(security_type);
        dsacaf.setRouterOfferType(router_type);
        dsacaf.setGponOfferType(gpon_type);
        dsacaf.setCorporationName("");
        dsacaf.setIdProof(id_proof_id);
        dsacaf.setIdProofNumber(et_pannumber.getText().toString());
        dsacaf.setNationality(Integer.parseInt(natinality_id));
        dsacaf.setGSTNumber(gst_no.getText().toString());
        dsacaf.setInstAddress(et_installation.getText().toString());
        dsacaf.setInstCityId(loc_city_id);
        dsacaf.setInstPinCode(install_pincode.getText().toString());
        dsacaf.setBillingAddress(bill_addressline.getText().toString());
        dsacaf.setBillingCityId(bill_city_id);
        dsacaf.setBillingPinCode(bill_pincode.getText().toString());
        dsacaf.setPermanentAddress(per_addressline.getText().toString());
        dsacaf.setPermanentCityId(per_city_id);
        dsacaf.setPermanentPinCode(per_pincode.getText().toString());
        dsacaf.setMACAddress(mac_add.getText().toString());
        dsacaf.setSMEEntityID("0");
        dsacaf.setSMETypeID("0");

        dsacaf.setLatitude(sharedPreferences.getString("latitude",""));
        dsacaf.setLongitude(sharedPreferences.getString("longitude",""));

       if(et_gpon_charges.getText().toString().equals("")){
           dsacaf.setGponCharges(0);
       }else{
        dsacaf.setGponCharges((gpon_charges));
       }

       if(et_gpon_refund.getText().toString().equals("")){
           dsacaf.setGponRefundAmt(0);
       }else {
           dsacaf.setGponRefundAmt(Double.parseDouble((et_gpon_refund.getText().toString())));
       }
        dsacaf.setComment(et_comment.getText().toString());

       if (et_router_model.getText().toString().equalsIgnoreCase("")) {
            dsacaf.setRouterModel("");
        } else {
            dsacaf.setRouterModel(et_router_model.getText().toString().trim());
        }

        if (et_gpon_model.getText().toString().equalsIgnoreCase("")) {
            dsacaf.setGponModel("");
        } else {
            dsacaf.setGponModel(et_gpon_model.getText().toString().trim());
        }

        if (et_security_description.getText().toString().equalsIgnoreCase("")) {
            dsacaf.setSecurityDepositDescription("");
        } else {
            dsacaf.setSecurityDepositDescription(et_security_description.getText().toString().trim());
        }

        dsacaf.setRouterSecurityPayMode(rs_pay_mode);
        dsacaf.setRouterSecurityTotalAmount(rs_total_amount);
        if (rb_same.isChecked()) {
            dsacaf.setRouterSecurityPayModeType(selected_mode);
            dsacaf.setRouterSecurityChequeNumber(et_rs_cheque_dd_no.getText().toString());
            dsacaf.setRouterSecurityChequeDate(cheque_date_to_send);
            dsacaf.setRouterSecurityBankName(et_rs_bank_Name.getText().toString());
        } else {
            if (rb_separate.isChecked()) {
                dsacaf.setRouterSecurityPayModeType(selected_rs_pay_type);

                if (rb_rs_cash.isChecked()) {
                    dsacaf.setRouterSecurityChequeNumber("");
                    dsacaf.setRouterSecurityChequeDate("");
                    dsacaf.setRouterSecurityBankName("");
                } else {
                    dsacaf.setRouterSecurityChequeNumber(et_rs_cheque_dd_no.getText().toString());
                    dsacaf.setRouterSecurityChequeDate(RS_Cheque_date_send);
                    dsacaf.setRouterSecurityBankName(et_rs_bank_Name.getText().toString());
                }
            }
        }

        SEND_DATA();

    }

    public void SEND_DATA() {
        if(r_key == 1 || s_key == 1 || g_key == 1){

            show_dialog_routerchk(CAF_Activity.this, "ALERT", "Please Enter "+cb_value+" Details.", false);

        }else {
            show_dialog(CAF_Activity.this, "CONFIRMATION", "Are you sure you want to submit?", false);
        }
    }


    public class InsertDetails extends AsyncTask<String, Void, Void> {
        String request_ = " ";
        String response = "";
        ProgressDialog prgDialog;

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            prgDialog = new ProgressDialog(CAF_Activity.this);
            prgDialog.setMessage("Please wait...");
            prgDialog.show();
            prgDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                InsertDetailsCaller insertDetailsCaller= new InsertDetailsCaller(
                        getApplicationContext().getResources().getString(
                                R.string.WSDL_TARGET_NAMESPACE),
                        getString(R.string.SOAP_URL), getApplicationContext()
                        .getResources().getString(
                                R.string.METHOD_INSERT_DETAILS),
                        dsacaf);

                insertDetailsCaller.join();
                insertDetailsCaller.start();
                rslt="START";
                while (rslt == "START") {
                    try {
                        Thread.sleep(10);
                    } catch (Exception ex) {
                    }
                }
               // InsertDetailsSOAP insert_details = new InsertDetailsSOAP(getString(R.string.WSDL_TARGET_NAMESPACE), getString(R.string.SOAP_URL), getString(R.string.METHOD_INSERT_DETAILS));

               // request_ = insert_details.InsertMiniCAFDetailsNew(dsacaf);

               // response = insert_details.getJsonResponse();
                MyUtils.l("myrequest",request_);
                MyUtils.l("myresponse",response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void request) {
            if (request_.length() > 0) {

                if (request_.equalsIgnoreCase("OK")) {

                    if (response.length() > 0) {
                        if (response.contains("#")) {
                            str = response.split("#");
                            MyUtils.l("String 1", ":" + str[0]);
                            MyUtils.l("String 2", ":" + str[1]);
                            if (str[1].equalsIgnoreCase("success")) {
                                //Toast.makeText(getApplicationContext(), "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                                show_finish_dialog(CAF_Activity.this, "Confirmation", "Data Inserted Successfully", true);
                                finish = true;
                            }
                            if (str[1].equalsIgnoreCase("error")) {
                                //Toast.makeText(getApplicationContext(), "Error:"+str[0], Toast.LENGTH_LONG).show();
                                //Display_msg(str[0]);
                                show_dialog1(CAF_Activity.this, "Confirmation", str[0], false);
                                //show_finish_dialog(CAF_Activity.this,"Confirmation","Data Inserted Successfully",true);
                                finish = false;
                            }
                        } else {
                            //Display_msg("Response from server is blank "+"\n"+"Error:"+response);
                            show_dialog1(CAF_Activity.this, "Confirmation", "Response from server is blank " + "\n" + "Error:", false);
                        }
                    } else {
                        //Display_msg("Response from server is empty");
                        show_dialog1(CAF_Activity.this, "Confirmation", "Response from server is empty", false);
                    }
                } else {
                    // Display_msg("Request from server contains blank !"+"\n"+"Error:"+request_);
                    show_dialog1(CAF_Activity.this, "Confirmation", "Request from server contains blank !" + "\n" + "Error:", false);
                }
            } else {
                // Display_msg("Request from server is empty "+"\n"+"Error:"+request_);
                show_dialog1(CAF_Activity.this, "Confirmation", "Request from server is empty " + "\n" + "Error:", false);
            }
            super.onPostExecute(request);
            prgDialog.dismiss();
        }
    }


    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(CAF_Activity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);*/
                    Intent startCustomCameraIntent = new Intent(CAF_Activity.this, CameraActivity.class);
                    startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                    is_caf_select=false;
                    is_address_scan=false;
                    is_identity_scan=false;
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
        if(requestCode == 2)
        {
            getAddressformgooglemap();
        }




    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        img_store_path = cursor.getString(column_index);

        MyUtils.l("Gallery image path",":"+img_store_path);

        String array[] = img_store_path.split("/");
        MyUtils.l("Array Length",":"+array.length);
        if (array.length > 0) {
            MyUtils.l("Image Name", ":" + array[array.length - 1]);
            image_name = array[array.length - 1];
        } else {
            image_name = "";
        }

        if(is_caf_select) {
            is_caf_select=false;
            tv_disp_caf.setText(image_name);
            caf_img_path=img_store_path;
        }else{
            if(is_address_scan){
                is_address_scan=false;
                tv_disp_address.setText(image_name);
                address_img_path=img_store_path;
            }else{
                if(is_identity_scan){
                    is_identity_scan=false;
                    tv_disp_identity.setText(image_name);
                    identity_img_path=img_store_path;
                }
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Uri photoUri = data.getData();
        // Get the bitmap in according to the width of the device
        Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(photoUri.getPath(), mSize.x, mSize.x);
        //((ImageView) findViewById(R.id.scannedImage)).setImageBitmap(bitmap);

        img_store_path=photoUri.getPath();
        MyUtils.l("Image Path",":"+photoUri.getPath());

        String array[] = img_store_path.split("/");
        MyUtils.l("Array Length",":"+array.length);
        if (array.length > 0) {
            MyUtils.l("Image Name", ":" + array[array.length - 1]);
            image_name = array[array.length - 1];
        } else {
            image_name = "";
        }

        if(is_caf_select) {
            is_caf_select=false;
            tv_disp_caf.setText(image_name);
            caf_img_path=img_store_path;
        }else{
            if(is_address_scan){
                is_address_scan=false;
                tv_disp_address.setText(image_name);
                address_img_path=img_store_path;
            }else{
                if(is_identity_scan){
                    is_identity_scan=false;
                    tv_disp_identity.setText(image_name);
                    identity_img_path=img_store_path;
                }
            }
        }
    }


    public HashMap<String, String> validation_to_createDirectory() {
        folder_name = et_firstname.getText().toString() + "_" + et_lastname.getText().toString() + "(" + et_caf_no.getText().toString() + ")";
        if (caf_img_path.contains("/") && address_img_path.contains("/") && identity_img_path.contains("/")) {
            hm_data.put("CAF_IMAGE", caf_img_path);
            hm_data.put("ADDRESS_IMAGE", address_img_path);
            hm_data.put("IDENTITY_IMAGE", identity_img_path);
            MyUtils.l("Hashmap", ":" + hm_data.size());
            //return true;
        } else {
            if (caf_img_path.contains("/") && address_img_path.contains("/")) {
                hm_data.put("CAF_IMAGE", caf_img_path);
                hm_data.put("ADDRESS_IMAGE", address_img_path);
                MyUtils.l("Hashmap", ":" + hm_data.size());
                //return true;
            } else {
                if (address_img_path.contains("/") && identity_img_path.contains("/")) {
                    hm_data.put("ADDRESS_IMAGE", address_img_path);
                    hm_data.put("IDENTITY_IMAGE", identity_img_path);
                    MyUtils.l("Hashmap", ":" + hm_data.size());
                    // return true;
                } else {
                    if (caf_img_path.contains("/") && identity_img_path.contains("/")) {
                        hm_data.put("CAF_IMAGE", caf_img_path);
                        hm_data.put("IDENTITY_IMAGE", identity_img_path);
                        MyUtils.l("Hashmap", ":" + hm_data.size());
                        // return true;
                    } else {
                        if (caf_img_path.contains("/")) {
                            hm_data.put("CAF_IMAGE", caf_img_path);
                            MyUtils.l("Hashmap", ":" + hm_data.size());
                            // return true;
                        } else {
                            if (identity_img_path.contains("/")) {
                                hm_data.put("IDENTITY_IMAGE", identity_img_path);
                                MyUtils.l("Hashmap", ":" + hm_data.size());
                                //return true;
                            } else {
                                if (address_img_path.contains("/")) {
                                    hm_data.put("ADDRESS_IMAGE", address_img_path);
                                    MyUtils.l("Hashmap", ":" + hm_data.size());
                                    // return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        MyUtils.l("Hashmap Data", ":" + hm_data.size());
        hm_upload_image.put(folder_name, hm_data);
        MyUtils.l("Hashmap upload data", ":" + hm_upload_image.size());
        return hm_data;
    }

    /*class CreateDirectoryTask extends AsyncTask<Void, Void, Boolean> {
        String hm_key, directory_name, file_name, file_path;
        HashMap<String, HashMap<String, String>> hm_data_upload;
        HashMap<String, String> hm_value;


        public CreateDirectoryTask(HashMap<String, HashMap<String, String>> imagePath) {
            hm_data_upload = imagePath;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean isCreated = false;

            Set keys = hm_data_upload.keySet();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                hm_key = (String) i.next();
                hm_value = (HashMap<String, String>) hm_data_upload.get(hm_key);
                MyUtils.l("HAsh key", ":" + hm_key);
                MyUtils.l("HAsh value", ":" + hm_value.toString());
            }

            directory_name = "/ScanDocument/" + hm_key + "/";

            try {
                ftpClient.connect("103.54.183.89", 21);
                boolean success = ftpClient.login("crmadmin", "crm@dmin123");
                showServerReply(ftpClient);
                if (!success) {
                    System.out.println("Could not login to the server");
                }

                String dirToCreate = directory_name;
                boolean success_create = ftpClient.makeDirectory(dirToCreate);
                showServerReply(ftpClient);

                if (success_create) {
                    System.out.println("Successfully created directory: " + dirToCreate);
                    isCreated = true;
                } else {
                    System.out.println("Something went wrong while creating directory: " + dirToCreate);

                    CAF_Activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            show_dialog1(CAF_Activity.this, "CONFIRMATION", "Something went wrong while creating directory !!", false);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isCreated;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            MyUtils.l("Success", ":" + success);

            if (success) {
                UploadImageTask uploadfile = new UploadImageTask(hm_value, directory_name);
                uploadfile.execute();

               *//* Set key = hm_value.keySet();
                for (Iterator i = key.iterator(); i.hasNext(); ) {
                  file_name = (String) i.next();
                  file_path = (String) hm_value.get(file_name);

                  fileName = new File(file_path);
                    try {
                        ftpClient.changeWorkingDirectory(directory_name);
                        ftpClient.enterLocalPassiveMode(); // important!
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        inputStream = new FileInputStream(fileName);
                        isUploaded=ftpClient.storeFile(file_name,inputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(isUploaded){
                    System.out.println("Successfully  Uploaded File: " );
                }else{
                    System.out.println("Successfully Not Uploaded File: ");
                }*//*
            }
        }
    }


    class UploadImageTask extends AsyncTask<Void, Void, Boolean> {

        HashMap<String, String> hm_data_upload;

        String directory_name, file_name, file_path;
        File fileName;
        InputStream inputStream;

        public UploadImageTask(HashMap<String, String> hm_data_upload, String directory_name) {
            this.hm_data_upload = hm_data_upload;
            this.directory_name = directory_name;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            boolean isUploaded = false;
            try {
                boolean success = ftpClient.login("crmadmin", "crm@dmin123");
                if (success) {
                    Set key = hm_data_upload.keySet();
                    for (Iterator i = key.iterator(); i.hasNext(); ) {
                        file_name = (String) i.next();
                        file_path = (String) hm_data_upload.get(file_name);

                        fileName = new File(file_path);
                        MyUtils.l("Directory Name", ":" + directory_name);

                        try {
                            ftpClient.changeWorkingDirectory(directory_name);
                            ftpClient.enterLocalPassiveMode(); // important!
                            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                            inputStream = new FileInputStream(fileName);
                            isUploaded = ftpClient.storeFile(file_name, inputStream);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (isUploaded) {
                        System.out.println("Successfully  Uploaded File: ");
                        isUploaded = true;
                    } else {
                        //System.out.println("Successfully Not Uploaded File: ");
                        //show_dialog1(CAF_Activity.this,"CONFIRMATION","Something went wrong while uploading file !!",false);
                        CAF_Activity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                show_dialog1(CAF_Activity.this, "CONFIRMATION", "Something went wrong while uploading file !!", false);
                            }
                        });
                    }
                } else {
                    System.out.println("Ftp disconnected: ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return isUploaded;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if (success) {
                SEND_DATA();
            }
        }
    }*/

    class UploadPictureTask extends AsyncTask<Void, Void, Boolean> {

        String hm_key, hm_key2, hm_value_names;
        FileInputStream inputStream;
        HashMap<String, HashMap<String, String>> hm_data_upload;

        HashMap<String, String> hm_value;
        ArrayList<String> al_up_data;


       /* public UploadPictureTask(String imagePath) {
            imgPath = imagePath;
        }*/

        public UploadPictureTask(HashMap<String, HashMap<String, String>> imagePath) {
            hm_data_upload = imagePath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean isUploaded = false;

            Set keys = hm_data_upload.keySet();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                hm_key = (String) i.next();
                hm_value = (HashMap<String, String>) hm_data_upload.get(hm_key);
                MyUtils.l("HAsh key", ":" + hm_key);
                MyUtils.l("HAsh value", ":" + hm_value.toString());
            }

            String directory_name = "/ScanDocument/" + hm_key + "/";



            try {
                //ftpClient.connect("103.54.183.89", 21);
                //boolean success = ftpClient.login("crmadmin", "crm@dmin123");

                ftpClient.connect("103.54.183.98", 21);
                boolean success = ftpClient.login("administrator", "H0rr0rTe@ser$432");

                showServerReply(ftpClient);
                if (!success) {
                    System.out.println("Could not login to the server");
                }

                String dirToCreate = directory_name;

                boolean success_create = ftpClient.makeDirectory(dirToCreate);


                if (success_create) {
                    System.out.println("Successfully created directory: " + dirToCreate);

                    Set key = hm_value.keySet();
                    for (Iterator i = key.iterator(); i.hasNext(); ) {
                        hm_key2 = (String) i.next();
                        hm_value_names = (String) hm_value.get(hm_key2);
                        MyUtils.l("HAsh key", ":" + hm_key2);
                        MyUtils.l("HAsh key", ":" + hm_value_names);

                        try {
                            File fileName = new File(hm_value_names);
                            ftpClient.changeWorkingDirectory(directory_name);
                            ftpClient.enterLocalPassiveMode(); // important!
                            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                            InputStream inputStream = new FileInputStream(fileName);
                            isUploaded = ftpClient.storeFile(hm_key2, inputStream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (isUploaded) {
                            System.out.println("Successfully  Uploaded File: ");
                        } else {
                            System.out.println("Successfully Not Uploaded File: ");
                        }
                    }
                }
                showServerReply(ftpClient);
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  if(MyUtils.isOnline(CAF_Activity.this)){
                File fileName = new File(imgPath);
                //FTPClient client = new FTPClient();
                String directory_name="/ScanDocument/Jyoti/";
                MyUtils.l("Image Store Path",":"+imgPath);

                try {

                    try {
                        ftpClient.connect("103.54.183.89", 21);
                        showServerReply(ftpClient);
                        int replyCode = ftpClient.getReplyCode();
                        if (!FTPReply.isPositiveCompletion(replyCode)) {
                            System.out.println("Operation failed. Server reply code: " + replyCode);
                            // return;
                        }

                        boolean success = ftpClient.login("crmadmin", "crm@dmin123");
                        showServerReply(ftpClient);
                        if (!success) {
                            System.out.println("Could not login to the server");
                            // return;
                        }

                        // Creates a directory
                        String dirToCreate = directory_name;
                        success = ftpClient.makeDirectory(dirToCreate);
                        showServerReply(ftpClient);
                        if (success) {
                            System.out.println("Successfully created directory: " + dirToCreate);
                            ftpClient.changeWorkingDirectory(directory_name);
                            ftpClient.enterLocalPassiveMode(); // important!
                            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                            InputStream inputStream = new FileInputStream(fileName);

                            //isUploaded=ftpClient.storeUniqueFile(inputStream);
                            isUploaded=ftpClient.storeFile("Jyoti",inputStream);

                            if(isUploaded){
                                System.out.println("Successfully  Uploaded File: " );
                            }else{
                                System.out.println("Successfully Not Uploaded File: ");
                            }
                        }else{
                            System.out.println("Successfully Not created directory: " + dirToCreate);
                    }


                    }catch (IOException ex) {
                        System.out.println("Oops! Something wrong happened");
                        ex.printStackTrace();
                    }
                    isUploaded = true;

                } catch (Exception e) {
                    // MyUtils.l(TAG, "err1 :" + e);
                    e.printStackTrace();
                    isUploaded = false;
                }
            }else{
                isUploaded = false;
            }*/
            return isUploaded;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

        /*
            if(MyUtils.isOnline(CAF_Activity.this)){
                if(success){

                }else{
                    DialogUtils.show_dialog(CAF_Activity.this,"Oops...","error occurred while uploading an Image",false);
                }
            }else{
                DialogUtils.show_dialog(CAF_Activity.this,"Confirmation","Please Connect to internet !",false);
            }*/
        }
    }

    private void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);

            }
        }

    }


    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {
            System.out.println(" Upload Started ...");
        }

        public void transferred(int length) {
            System.out.println(" transferred ..." + length);
        }

        public void completed() {
            System.out.println(" completed ...");
        }

        public void aborted() {
            System.out.println(" aborted ...");
        }

        public void failed() {
            System.out.println(" failed ...");
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                show_dialog_lost(CAF_Activity.this, "CONFIRMATION", "Your selection will be lost " + "\n" + " Are you sure?", false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void show_dialog(final Context ctx, String title,
                            String message, final boolean finish) {
        Effectstype effect = Effectstype.Slidetop;


        try{
        dialog_box = NiftyDialogBuilder.getInstance(ctx);
        dialog_box

                .withTitle(null)
                .withDividerColor("#eeeeee")
                .withMessage(null)
                .withMessageColor("#eeeeee")
                .withDialogColor("#eeeeee")
                .withIcon(ctx.getResources().getDrawable(R.drawable.ic_launcher))
                .isCancelableOnTouchOutside(true) // def | isCancelable(true)
                .withDuration(700)
                .withEffect(effect).setCustomView(R.layout.dialog_final_submit, ctx)// def
                .show();
        dialog_box.setCancelable(false);
        MyTextView tv_title = (MyTextView) dialog_box
                .findViewById(R.id.tv_title);
        MyTextView tv_meesage = (MyTextView) dialog_box
                .findViewById(R.id.tv_message);
        Button btn_yes = (Button) dialog_box
                .findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialog_box
                .findViewById(R.id.btn_no);
            tv_title.setText(title);
            tv_meesage.setText(message);


        btn_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new InsertDetails().execute();

                if (dialog_box != null) {
                    dialog_box.dismiss();
                }
                if (finish) {
                    ((Activity) ctx).finish();
                }
            }
        });


        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_box != null) {
                    dialog_box.dismiss();
                }
            }
        });

        }catch (Exception e){
            System.out.println(e);
        }
    }



    public void show_dialog_routerchk(final Context ctx, String title,
                            String message, final boolean finish) {
        Effectstype effect = Effectstype.Slidetop;


        try{
            dialog_box = NiftyDialogBuilder.getInstance(ctx);
            dialog_box

                    .withTitle(null)
                    .withDividerColor("#eeeeee")
                    .withMessage(null)
                    .withMessageColor("#eeeeee")
                    .withDialogColor("#eeeeee")
                    .withIcon(ctx.getResources().getDrawable(R.drawable.ic_launcher))
                    .isCancelableOnTouchOutside(true) // def | isCancelable(true)
                    .withDuration(700)
                    .withEffect(effect).setCustomView(R.layout.dialog_final_submit, ctx)// def
                    .show();
            dialog_box.setCancelable(false);
            MyTextView tv_title = (MyTextView) dialog_box
                    .findViewById(R.id.tv_title);
            MyTextView tv_meesage = (MyTextView) dialog_box
                    .findViewById(R.id.tv_message);
            Button btn_yes = (Button) dialog_box
                    .findViewById(R.id.btn_yes);
            Button btn_no = (Button) dialog_box
                    .findViewById(R.id.btn_no);
            tv_title.setText(title);
            tv_meesage.setText(message);

            btn_yes.setText("OK");
            btn_no.setVisibility(View.GONE);


            btn_yes.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog_box.dismiss();
                    if(cb_value.equalsIgnoreCase("Router")){
                        router_check();
                    }
                    if(cb_value.equalsIgnoreCase("Security")){
                        Security_check();
                    }
                    if(cb_value.equalsIgnoreCase("Gpon")){
                        Gpon_check();
                    }


                }
            });
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void Gpon_check() {

        if(!et_gpon_model.getText().toString().equalsIgnoreCase("")) {
            if(!et_gpon_charges.getText().toString().equalsIgnoreCase("")) {
                g_key=0;
            }else{
                et_gpon_charges.setError("Please Enter Gpon Charges.");
            }
        }else{
            et_gpon_model.setError("Please Enter Gpon Model.");
        }
    }

    private void Security_check() {
        if(!et_security_charges.getText().toString().equalsIgnoreCase("")) {
            if(!et_security_refund.getText().toString().equalsIgnoreCase("")) {
                if (!et_security_description.getText().toString().equalsIgnoreCase("")) {
                    s_key=0;
                }else{
                    et_security_description.setError("Please Enter Description.");
                }
            }else{
                et_security_refund.setError("Please Enter Security Refund Charges.");
            }
        }else{
            et_security_charges.setError("Please Enter Security Model.");
        }
    }

    private void router_check() {

        if(!selected_router_id.equalsIgnoreCase("-1")){
            if(!et_router_model.getText().toString().equalsIgnoreCase("")) {
                if(!et_router_charges.getText().toString().equalsIgnoreCase("")) {
                    r_key = 0;
                }else{
                    et_router_charges.setError("Please Enter Router Charges.");
                }
            }else{
                et_router_model.setError("Please Enter Router Model.");
            }
        }else{
            Toast.makeText(CAF_Activity.this,"Please select Router Type",Toast.LENGTH_LONG).show();
        }
    }

    public void showBackDialog(String Message) {
        new AlertDialog.Builder(CAF_Activity.this)
                .setMessage(Html.fromHtml("<font color='#20B2AA'>" + Message + "</font>"))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(Html.fromHtml("<font color='#20B2AA'><b>Confirmation</b></font>"))
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                            public void onClick(DialogInterface dialog, int id) {
                                CAF_Activity.this.finish();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        }).show();
    }


    public void show_dialog_lost(final Context ctx, String title, String message, final boolean finish) {
        Effectstype effect = Effectstype.Slidetop;
        dialog_box = NiftyDialogBuilder.getInstance(ctx);
        dialog_box
                /*
                 * .withTitle("Confirmation") .withTitle(null) no title
                 * .withTitleColor(ctx.getString(R.color.theme_color))
                 */
                // def
                .withTitle(null)
                .withDividerColor("#eeeeee")
                // def
                // .withMessage(Message) //.withMessage(null) no Msg
                .withMessage(null)
                .withMessageColor("#eeeeee")
                // def | withMessageColor(int resid)
                .withDialogColor("#eeeeee")
                // def | withDialogColor(int resid) //def
                .withIcon(
                        ctx.getResources().getDrawable(R.drawable.ic_launcher))
                .isCancelableOnTouchOutside(true) // def | isCancelable(true)
                .withDuration(700)
                // def
                .withEffect(effect).setCustomView(R.layout.dialog_final_submit, ctx)// def
                // Effectstype.Slidetop
                .show();
        dialog_box.setCancelable(false);
        MyTextView tv_title = (MyTextView) dialog_box.findViewById(R.id.tv_title);
        MyTextView tv_meesage = (MyTextView) dialog_box.findViewById(R.id.tv_message);
        Button btn_yes = (Button) dialog_box.findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialog_box.findViewById(R.id.btn_no);
        btn_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                CAF_Activity.this.finish();
                if (dialog_box != null) {
                    dialog_box.dismiss();
                }
                if (finish) {
                    ((Activity) ctx).finish();
                }
            }
        });
        tv_title.setText(title);
        tv_meesage.setText(message);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog_box != null) {
                    dialog_box.dismiss();
                }
            }
        });
    }

    public void show_finish_dialog(final Context ctx, String title, String message, final boolean finish) {
        Effectstype effect = Effectstype.Slidetop;
        dialog_box = NiftyDialogBuilder.getInstance(ctx);
        dialog_box
                /*
                 * .withTitle("Confirmation") .withTitle(null) no title
                 * .withTitleColor(ctx.getString(R.color.theme_color))
                 */
                // def
                .withTitle(null)
                .withDividerColor("#eeeeee")
                // def
                // .withMessage(Message) //.withMessage(null) no Msg
                .withMessage(null)
                .withMessageColor("#eeeeee")
                // def | withMessageColor(int resid)
                .withDialogColor("#eeeeee")
                // def | withDialogColor(int resid) //def
                .withIcon(ctx.getResources().getDrawable(R.drawable.ic_launcher))
                .isCancelableOnTouchOutside(true) // def | isCancelable(true)
                .withDuration(700)
                // def
                .withEffect(effect).setCustomView(R.layout.dialog_box, ctx)// def
                // Effectstype.Slidetop
                .show();
        dialog_box.setCancelable(false);
        MyTextView tv_title = (MyTextView) dialog_box.findViewById(R.id.tv_title);
        MyTextView tv_meesage = (MyTextView) dialog_box.findViewById(R.id.tv_message);
        Button btn_ok = (Button) dialog_box.findViewById(R.id.btn_next);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dialog_box != null) {
                    dialog_box.dismiss();
                    Intent intent = new Intent(CAF_Activity.this, CAF_Activity.class);
                    startActivity(intent);
                }

                if (finish) {
                    ((Activity) ctx).finish();
                }
            }
        });
        tv_title.setText(title);
        tv_meesage.setText(message);
    }

    public void show_dialog1(final Context ctx, String title, String message, final boolean finish) {
        Effectstype effect = Effectstype.Slidetop;
        dialog_box = NiftyDialogBuilder.getInstance(ctx);
        dialog_box
                /*
                 * .withTitle("Confirmation") .withTitle(null) no title
                 * .withTitleColor(ctx.getString(R.color.theme_color))
                 */
                // def
                .withTitle(null)
                .withDividerColor("#eeeeee")
                // def
                // .withMessage(Message) //.withMessage(null) no Msg
                .withMessage(null)
                .withMessageColor("#eeeeee")
                // def | withMessageColor(int resid)
                .withDialogColor("#eeeeee")
                // def | withDialogColor(int resid) //def
                .withIcon(ctx.getResources().getDrawable(R.drawable.ic_launcher))
                .isCancelableOnTouchOutside(true) // def | isCancelable(true)
                .withDuration(700)
                // def
                .withEffect(effect).setCustomView(R.layout.dialog_box, ctx)// def
                // Effectstype.Slidetop
                .show();
        dialog_box.setCancelable(false);
        MyTextView tv_title = (MyTextView) dialog_box
                .findViewById(R.id.tv_title);
        MyTextView tv_meesage = (MyTextView) dialog_box
                .findViewById(R.id.tv_message);
        Button btn_ok = (Button) dialog_box
                .findViewById(R.id.btn_next);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (dialog_box != null) {
                    dialog_box.dismiss();
                }
                if (finish) {
                    ((Activity) ctx).finish();
                }
            }
        });
        tv_title.setText(title);
        tv_meesage.setText(message);
    }

}
