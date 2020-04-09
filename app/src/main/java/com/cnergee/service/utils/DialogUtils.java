package com.cnergee.service.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


import com.cnergee.broadbandservice.R;
import com.cnergee.service.customview.MyButtonView;
import com.cnergee.service.customview.MyTextView;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;


public class DialogUtils {
    public static NiftyDialogBuilder dialog_box;

    public static void show_dialog(final Context ctx, String title, String message, final boolean finish) {
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
                .withEffect(effect).setCustomView(R.layout.dialog_box, ctx)// def
                // Effectstype.Slidetop

                .show();
        dialog_box.setCancelable(false);
        MyTextView tv_title = (MyTextView) dialog_box
                .findViewById(R.id.tv_title);
        MyTextView tv_meesage = (MyTextView) dialog_box
                .findViewById(R.id.tv_message);
        MyButtonView btn_ok = (MyButtonView) dialog_box
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
                    ((Activity) ctx).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
        tv_title.setText(title);
        tv_meesage.setText(message);
    }



    public  void show_scan_option(final Context ctx, String title,
                                  String message, final boolean finish) {
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
                .withEffect(effect).setCustomView(R.layout.dialog_scan, ctx)// def
                // Effectstype.Slidetop

                .show();
        dialog_box.setCancelable(false);
        MyTextView tv_title = (MyTextView) dialog_box.findViewById(R.id.tv_title);
        //MyTextView tv_meesage = (MyTextView) dialog_box.findViewById(R.id.tv_message);
        final RadioButton rb_media=(RadioButton)dialog_box.findViewById(R.id.rb_media);
        final RadioButton rb_camera=(RadioButton)dialog_box.findViewById(R.id.rb_camera);
        Button btn_cancel = (Button) dialog_box.findViewById(R.id.btn_cancel);
        Button btn_ok = (Button) dialog_box.findViewById(R.id.btn_ok);

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (dialog_box != null) {
                    dialog_box.dismiss();
                }

                if (finish) {
                    ((Activity) ctx).finish();
                }
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

              /*  if (dialog_box != null) {
                    dialog_box.dismiss();
                }

                if (finish) {
                    ((Activity) ctx).finish();
                }

                if(rb_media.isChecked()){
                    startScan(ScanConstants.OPEN_MEDIA);
                }else{
                    if(rb_camera.isChecked()){
                        startScan(ScanConstants.OPEN_CAMERA);
                    }
                }*/
            }
        });
        tv_title.setText(title);
        // tv_meesage.setText(message);
    }

}
