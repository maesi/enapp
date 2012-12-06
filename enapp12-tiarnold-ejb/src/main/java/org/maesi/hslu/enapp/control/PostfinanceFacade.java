package org.maesi.hslu.enapp.control;

import java.math.BigDecimal;
import java.net.ProxySelector;
import java.net.URI;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.maesi.hslu.enapp.dto.Payment;
import org.maesi.hslu.enapp.ext.postfinance.NcResponse;
import org.maesi.hslu.enapp.ext.postfinance.PaymentRequest;
import org.maesi.hslu.enapp.ext.postfinance.PostRequest;

@Stateless
public class PostfinanceFacade {


    public int doPayment(Payment payment) throws Exception {
    	PostRequest postRequest = new PostRequest();
    	HttpPost httpPostRequest = postRequest.composeRequest(createPaymentRequest(payment));
    	httpPostRequest.setURI(URI.create("https://e-payment.postfinance.ch/ncol/test/orderdirect.asp"));
        DefaultHttpClient client = new DefaultHttpClient();
        client.setRoutePlanner(new ProxySelectorRoutePlanner(
                client.getConnectionManager().getSchemeRegistry(),
                ProxySelector.getDefault()));
        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpPostRequest);
        } catch (Exception e) {
            throw new Exception("Payment-Request-Fehler: " + e.getMessage());
        }
        checkHttpState(httpResponse);
        NcResponse response = unmarshalResponse(httpResponse);
        checkResponse(response);
        return response.getPaymentId();
    }

    private PaymentRequest createPaymentRequest(Payment payment) {
        PaymentRequest _return = new PaymentRequest();
        _return.setPurchaseId("TIARNOLD" + payment.getPurchaseId());
        _return.setCurrency( "CHF");
        _return.setAmount(payment.getAmount().multiply(new BigDecimal(100)).toString());
        _return.setCardNumber(String.valueOf(payment.getCreditCardNumber()));
        _return.setCardCvc(String.valueOf(payment.getCreditCardCvc()));
        _return.setCardExpiryDate(payment.getCreditCardExpire());
        return _return;
    }

    private void checkResponse(NcResponse aResponse) throws Exception {
        if (aResponse.getErrorState() != 0) {
            throw new Exception("Postfinance-Service-Fehler: " + aResponse.getErrorMessage());
        }
    }

    private NcResponse unmarshalResponse(HttpResponse aHttpResponse) throws Exception {
        try {
            Unmarshaller unmarshaller = JAXBContext.newInstance(NcResponse.class).createUnmarshaller();
            return (NcResponse) unmarshaller.unmarshal(aHttpResponse.getEntity().getContent());
        } catch (Exception e) {
            throw new Exception("Response-Unmarshell-Fehler: " + e.getMessage());
        }
    }

    private void checkHttpState(HttpResponse aHttpResponse) throws Exception {
        int state = aHttpResponse.getStatusLine().getStatusCode();
        if (state < 200 || state >= 300) {
            throw new Exception("Payment-Request-Fehler: " + aHttpResponse.getStatusLine().getReasonPhrase());
        }
    }
}
