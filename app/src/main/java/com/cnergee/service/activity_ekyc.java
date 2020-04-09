package com.cnergee.service;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.cnergee.broadbandservice.R;
import com.example.ekycsdkdemo.Config;
import com.example.ekycsdkdemo.WebViewActivity;
import com.example.ekycsdkdemo.mantra.model.EkycData;
import com.example.ekycsdkdemo.mantra.model.ResponseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class activity_ekyc extends Activity {

    TextView info, genderAdhanr, dobadhanr, genderAdhanr_v, dobadhar_v, nameAdhar_v;
    String xmlstring;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println("EKYC");
        setContentView(R.layout.activity_ekyc);

        info = (TextView) findViewById(R.id.nameAdhar);
        image =  (ImageView)findViewById(R.id.image);
        nameAdhar_v= (TextView)findViewById(R.id.nameAdhar_V);
        genderAdhanr= (TextView)findViewById(R.id.genderAdhar);
        genderAdhanr_v= (TextView)findViewById(R.id.genderAdhar_v);
        dobadhanr= (TextView)findViewById(R.id.dobAdhanr);
        dobadhar_v= (TextView)findViewById(R.id.dobAdhanr_V);

        info.setText("Adhan Name");
        EkycData ekycData = new EkycData();

        ekycData.setTransactionType("E");
        ekycData.setTransactionID("121");
        ekycData.setAadharNumber("");
        ekycData.setDeviceType( "Mantra"+"("+"MFS100"+")" );
        ekycData.setKey("bfa98e9b873a26975e84eeb89150046b");
        ekycData.setLicKey("R0ML0VwlsEipdov1UoCgt1+BlswdMW9fN60xK+4vc7w3vBOToRyO8Po5ZaK8Uoh6FkSJTOdivtHuYsWToge8+YwaaX7tTmlHhKvEnNRq9kwG1XN/L2Fc3rGYS5lRGyC6eoEEdxBcxoeWl2ynSBuyfHRjjZGXMBBIGiJRbLuR/Uw=");

        // Use your Activity name instead of “YourActivity.this”
        Intent intent = new Intent(activity_ekyc.this, WebViewActivity.class);

//Put all the data into the Intent and Pass it to the SDK.
            intent.putExtra(Config.ARGUMENT_DATA, ekycData);

        startActivityForResult(intent, Config.REQUEST_CODE);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Config.REQUEST_CODE) {

            try {
                if (resultCode == Activity.RESULT_OK) {

                    if (data != null) {
                        try {


                            //Response model object holds the data which you get after the process completion.
                            ResponseModel responseModel = (ResponseModel) data.getSerializableExtra(Config.RESULT);
                            responseModel.getAadhaarTs();

                            //info.setText(String.valueOf(responseModel.getErrorCode()));
                            //info.setText(String.valueOf(responseModel.getErrorMessage()));
                            //info.setText(String.valueOf(responseModel.getKycInfo()));

                            xmlstring = String.valueOf(responseModel.getKycInfo());


                            JSONObject jsonObj = null;
                            try {
                                jsonObj = XML.toJSONObject(xmlstring);

                                JSONObject KycRes = jsonObj.getJSONObject("KycRes");

                                String code =KycRes.getString("code");

                                //JSONObject c = KycRes.getJSONObject();
                                JSONObject UidData = KycRes.getJSONObject("UidData");
                                JSONObject Poi = UidData.getJSONObject("Poi");
                                String name = Poi.getString("name");
                                String gender = Poi.getString("gender");
                                String dob = Poi.getString("dob");
                                Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                                Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
                                Toast.makeText(this, dob, Toast.LENGTH_SHORT).show();
                                nameAdhar_v.setText(name);
                                if (gender.equals("F")){
                                    genderAdhanr_v.setText("Female");
                                }else{
                                 genderAdhanr_v.setText("Male");
                                }
                                dobadhar_v.setText(dob);

                                Log.d("photo", UidData.getString("Pht"));


                                byte[] imageBytes;
                                imageBytes = imageBytes = Base64.decode(UidData.getString("Pht"), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                image.setImageBitmap(decodedImage);


                              /*  for (int i = 0; i < KycRes.length(); i++){
                                   // JSONArray KycRes = jsonObj.getJSONArray("KycRes");

                                    JSONObject c = KycRes.getJSONObject(i);
                                    JSONObject UidData = c.getJSONObject("UidData");
                                    JSONObject Poi = UidData.getJSONObject("Poi");
                                    String name = Poi.getString("name");
                                    String gender = Poi.getString("gender");
                                    String dob = Poi.getString("dob");
                                    Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();

                                    Toast.makeText(this, dob, Toast.LENGTH_SHORT).show();




                                }*/
                            } catch (JSONException e) {
                                Log.e("JSON exception", e.getMessage());
                                e.printStackTrace();
                            }

                            Log.d("XML", xmlstring);

                            Log.d("JSON", jsonObj.toString());

                            //Toast.makeText(this, String.valueOf(responseModel.getErrorCode()), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(this, String.valueOf(responseModel.getErrorMessage()), Toast.LENGTH_SHORT).show();
                            Toast.makeText(this, String.valueOf(responseModel.getKycInfo()), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
