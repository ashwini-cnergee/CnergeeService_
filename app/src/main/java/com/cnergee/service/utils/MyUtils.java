package com.cnergee.service.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class MyUtils
   {
       //public static String CLIENTACCESSID="CM00013DVN";  //CM000001DB Live Server  //CM000017DV Development Server
       public static String CLIENTACCESSNAME="CliectAccessId";
       public static String EZETAP_ALLOWED="ezetap_check";

       public static String DSA_OBJECT="dsa";
       public static boolean SHOW_LOG=true;
       public static String APP_REG = "app_reg";

       public static boolean isOnline(Context context) {

               boolean haveConnectedWifi = false;
               boolean haveConnectedMobile = false;

               ConnectivityManager cm =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

               // or if function is out side of your Activity then you need context of your Activity
               // and code will be as following
               // (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

               NetworkInfo[] netInfo = cm.getAllNetworkInfo();
               for (NetworkInfo ni : netInfo)
               {
                   if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                   {
                       if (ni.isConnected())
                       {
                           haveConnectedWifi = true;
                           System.out.println("WIFI CONNECTION AVAILABLE");
                       } else
                       {
                           System.out.println("WIFI CONNECTION NOT AVAILABLE");
                       }
                   }
                   if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                   {
                       if (ni.isConnected())
                       {
                           haveConnectedMobile = true;
                           System.out.println("MOBILE INTERNET CONNECTION AVAILABLE");
                       }
                       else
                       {
                           System.out.println("MOBILE INTERNET CONNECTION NOT AVAILABLE");
                       }
                   }
               }
               return haveConnectedWifi || haveConnectedMobile;
       }

       public static void l(String name,String value){
           if(SHOW_LOG){
               Log.d(name,value);
           }
       }
}
