package com.cnergee.service.caller;

import com.cnergee.service.CAF_Activity;
import com.cnergee.service.SOAP.InsertDetailsSOAP;
import com.cnergee.service.obj.DSACAF;
import com.cnergee.service.util.Utils;

import java.net.SocketException;
import java.net.SocketTimeoutException;

public class InsertDetailsCaller extends Thread {
    InsertDetailsSOAP insertDetailsSOAP;

    private String WSDL_TARGET_NAMESPACE;
    private String SOAP_URL;
    private String METHOD_NAME;

    private DSACAF objDsaCaf;

    public InsertDetailsCaller(String WSDL_TARGET_NAMESPACE, String SOAP_URL,
                            String METHOD_NAME, DSACAF objDsaCaf) {
        this.WSDL_TARGET_NAMESPACE = WSDL_TARGET_NAMESPACE;
        this.SOAP_URL = SOAP_URL;
        this.METHOD_NAME = METHOD_NAME;
        this.objDsaCaf = objDsaCaf;
    }

    public void run() {

        try {
            insertDetailsSOAP= new InsertDetailsSOAP(WSDL_TARGET_NAMESPACE, SOAP_URL, METHOD_NAME);
            CAF_Activity.rslt=insertDetailsSOAP.CallInsertDetailsSOAP(objDsaCaf);



        } catch (Exception e) {
            Utils.log("3",""+e);
            CAF_Activity.rslt = "Invalid web-service response.<br>"+e.toString();
        }
    }
}
