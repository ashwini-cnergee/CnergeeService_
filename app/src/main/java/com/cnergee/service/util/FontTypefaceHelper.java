/*
 *
 * Java code developed by Ashok Parmar (parmar.ashok@gmail.com)
 * Date of code generation:  28 Jan. 2013
 *
 * Version 1.0
 *
 */
package com.cnergee.service.util;

import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.cnergee.broadbandservice.R;

public class FontTypefaceHelper {

	private String strFont;
	private Resources res;
	private float fontSize;
	private float btnFontSize;
	private float dialogFontSize;
	Typeface typeface;
	public static String selLanguage;
	
	public void createTypeFace(Context context,View view,String language){
		this.selLanguage=language;
		if(language.equalsIgnoreCase("hi")){
			Resources res = getResourcesByLocale(context, "hi");
			setAppResources(res);
			strFont = "fonts/shusha.ttf";
			fontSize = 20;
			dialogFontSize = 20;
			btnFontSize = 25;

		}else if(language.equalsIgnoreCase("gu")){
			Resources res = getResourcesByLocale(context, "gu");
			setAppResources(res);
			strFont = "fonts/Saumil_guj2.ttf";
			fontSize = 20;
			dialogFontSize = 20;
			btnFontSize = 15;
		}else if(language.equalsIgnoreCase("mh")){
			Resources res = getResourcesByLocale(context, "mh");
			setAppResources(res);
			strFont = "fonts/Shivaji01.ttf";
			dialogFontSize = 20;
			fontSize = 20;
			btnFontSize = 25;

		}else if(language.equalsIgnoreCase("en")){
			Resources res = getResourcesByLocale(context, "en");
			setAppResources(res);
			strFont="";
			fontSize = 18;
			btnFontSize = 15; 
			dialogFontSize = 15;
		}
		else{
			Resources res = getResourcesByLocale(context, "en");
			setAppResources(res);
			strFont="";
			fontSize = 18;
			btnFontSize = 15;
			dialogFontSize = 15;
		}
	}
	
	/**
	 * @return the typeface
	 */
	public Typeface getTypeface() {
		return typeface;
	}

	/**
	 * @param typeface the typeface to set
	 */
	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
	}

	/**
	 * @return the res
	 */
	public Resources getAppResources() {
		return res;
	}

	/**
	 * @param res the res to set
	 */
	public void setAppResources(Resources res) {
		this.res = res;
	}

	public Resources getResourcesByLocale( Context context, String localeName ) {
		/*Log.i(" ****** ",""+localeName);*/
	Resources res = context.getResources();
	    Configuration conf = new Configuration(res.getConfiguration());
	    if(localeName.equals("en"))
	    	conf.locale = Locale.getDefault();
	    else
	    	conf.locale = new Locale(localeName);
	    //Locale.getAvailableLocales();
	    return new Resources(res.getAssets(), res.getDisplayMetrics(), conf);
	}
	
	public void overrideLoginFonts(final Context context, final View view) {
	    try {
	    	String str_textVal = "";
	    	Typeface font;
	    	if(!strFont.equals("")){
	    		font = Typeface.createFromAsset(context.getAssets(), strFont);
	    		this.typeface = font;
	    	}else{
	    		font = Typeface.DEFAULT;
	    		this.typeface = font;
	    	}
	    	Utils.log("Fonts","called");
	    	TextView textView = (TextView)view.findViewById(R.id.userLabel);
	    	
	    	if(selLanguage.equalsIgnoreCase("hi"))
	    	 str_textVal = getAppResources().getString(R.string.login_user_hindi);
	    	else if (selLanguage.equalsIgnoreCase("mh")) 		  	
	    	 str_textVal = getAppResources().getString(R.string.login_user_marathi);
	    	else
	    	 str_textVal = getAppResources().getString(R.string.login_user);
	    	
	    	textView.setText(str_textVal);
	    	textView.setTypeface(font);
	    	textView.setTextSize(fontSize);
	    	
	    	textView = (TextView)view.findViewById(R.id.passwordLabel); 
	    	
	    	if(selLanguage.equalsIgnoreCase("hi"))
	    	 str_textVal = getAppResources().getString(R.string.login_pass_hindi);
	    	else if (selLanguage.equalsIgnoreCase("mh")) 	
	    	 str_textVal = getAppResources().getString(R.string.login_pass_marathi);
	    	else
	    	 str_textVal = getAppResources().getString(R.string.login_pass);
	    	
	    	textView.setText(str_textVal);
	    	textView.setTypeface(font);
	    	textView.setTextSize(fontSize);

	    	textView = (TextView)view.findViewById(R.id.loginBtn);
	    	
	    	if(selLanguage.equalsIgnoreCase("hi"))
	    	str_textVal = getAppResources().getString(R.string.login_btn_login_hindi);
	    	else if (selLanguage.equalsIgnoreCase("mh")) 	
	    	str_textVal = getAppResources().getString(R.string.login_btn_login_marathi);
	    	else
	    	str_textVal = getAppResources().getString(R.string.login_btn_login);	
	    	
	    	textView.setText(str_textVal);
	    	textView.setTypeface(font);
	    	textView.setTextSize(btnFontSize);
	    	
	    	textView = (TextView)view.findViewById(R.id.setting);
	    	
	    	if(selLanguage.equalsIgnoreCase("hi"))
	    	str_textVal = getAppResources().getString(R.string.login_btn_setting_hindi);
	    	else if (selLanguage.equalsIgnoreCase("mh")) 
	    	str_textVal = getAppResources().getString(R.string.login_btn_setting_marathi);
	    	else
	    	str_textVal = getAppResources().getString(R.string.login_btn_setting);
	    	
	    	textView.setText(str_textVal);
	    	textView.setTypeface(font);
	    	textView.setTextSize(btnFontSize);
	    } catch (Exception e) {}
	}
		
	public void dialogFontOverride(Context context, ProgressDialog Dialog) throws NullPointerException {
    	Typeface font;
    	if(!strFont.equals("")){
    		font = Typeface.createFromAsset(context.getAssets(), strFont);
    		this.typeface = font;
    	}else{
    		font = Typeface.DEFAULT;
    		this.typeface = font;
    	}
		TextView textView = (TextView) Dialog.findViewById(android.R.id.message);
		textView.setTypeface(font);
    	textView.setTextSize(dialogFontSize);
	}
	
}
