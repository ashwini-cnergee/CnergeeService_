package com.cnergee.service;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cnergee.broadbandservice.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ecature extends AppCompatActivity {
    SignaturePad signaturePad;
    Button saveButton, clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecature);

        signaturePad = (SignaturePad)findViewById(R.id.signaturePad);
        saveButton = (Button)findViewById(R.id.saveButton);
        clearButton = (Button)findViewById(R.id.clearButton);

        //disable both buttons at start
        saveButton.setEnabled(false);
        clearButton.setEnabled(false);

        //change screen orientation to landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write code for saving the signature here
                //signaturePad.getSignatureBitmap();
                saveToInternalStorage(signaturePad.getTransparentSignatureBitmap());
                /*
                * getSignatureBitmap() - A signature bitmap with a white background.
                  getTransparentSignatureBitmap() - A signature bitmap with a transparent background.
                  getSignatureSvg() - A signature Scalable Vector Graphics document.
                */
                Toast.makeText(Ecature.this, "Signature Saved", Toast.LENGTH_SHORT).show();
                signaturePad.clear();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });
    }


    public boolean saveImageToInternalStorage(Bitmap image) {

        try {
// Use the compress method on the Bitmap object to write image to
// the OutputStream
            Context context = this;
            String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            FileOutputStream fos = context.openFileOutput(fileName+".png", Context.MODE_PRIVATE);

// Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            Log.e("saveToInternalStorage()","save");
            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }


    @Nullable
    private String saveToInternalStorage(Bitmap bitmapImage){

        Log.e("saveToInternalStorage()","save");
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        try {
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/PDFfiles");
            if (!dir.exists()) {
                dir.mkdirs();
            }

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
            String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        File mypath=new File(dir,fileName+".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }catch (Exception e){
        }
        return null;
    }

}
