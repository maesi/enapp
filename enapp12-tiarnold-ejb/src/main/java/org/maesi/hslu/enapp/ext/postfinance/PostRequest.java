package org.maesi.hslu.enapp.ext.postfinance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class PostRequest {
    
    public static final String SHA1PWDIN = "hslu!comp@ny.websh0p";
    public static final String PSPID = "HSLUiCompany";
    public static final String USERID = "enappstudents";
    public static final String PSWD = "Mb%*3Kt9BU";
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String PSPURL_REQUEST = "https://e-payment.postfinance.ch/ncol/test/orderdirect.asp";
    public static final String PSPURL_QUERY = "https://e-payment.postfinance.ch/ncol/test/querydirect.asp";
    public static final String PSPURL_MAINTENANCE = "https://e-payment.postfinance.ch/ncol/test/maintenancedirect.asp";
    

    public HttpPost composeRequest(PaymentRequest payload) {
        HttpPost postRequest = new HttpPost();
        SortedMap<String, String> parameters = new TreeMap<String, String>();
        
        addParameters(parameters, payload);
        
        Collection<NameValuePair> nameValuePairs = getParametersAsNameValuePairs(parameters);
        postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        return postRequest;
    }

    protected void addParameters(SortedMap<String, String> parameters, PaymentRequest payload) {
        parameters.put("CURRENCY", payload.getCurrency());
        parameters.put("ORDERID", payload.getPurchaseId());
        parameters.put("AMOUNT", payload.getAmount());
        parameters.put("CARDNO", payload.getCardNumber());
        parameters.put("ED", payload.getCardExpiryDate());
        parameters.put("CVC", payload.getCardCvc());
        
        parameters.put("PSPID", PSPID);
        parameters.put("USERID", USERID);
        parameters.put("PSWD", PSWD);
        
        String _hash = DigestUtils.shaHex(getMessageToHash(parameters));
        parameters.put("SHASIGN", _hash);
    }
    
    private String getMessageToHash(SortedMap<String, String> parameters) {
        String signature = "";
        String hashPassphrase = SHA1PWDIN;
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            signature += String.format("%s=%s%s", parameter.getKey(), parameter.getValue(), hashPassphrase);
        }
        return signature;
    }

    private Collection<NameValuePair> getParametersAsNameValuePairs(Map<String, String> parameters) {
        Collection<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(parameter.getKey(), parameter.getValue()));
        }
        return nameValuePairs;
    }
}
