package org.maesi.hslu.enapp.ext.postfinance;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "ncresponse")
public class NcResponse {

    @XmlAttribute(name = "orderID")
    private String purchaseId;
    @XmlAttribute(name = "PAYID")
    private int paymentId;
    @XmlAttribute(name = "NCSTATUS")
    private int errorState;
    @XmlAttribute(name = "NCERROR")
    private String errorCode;
    @XmlAttribute(name = "NCERRORPLUS")
    private String errorMessage;
    @XmlAttribute(name = "ACCEPTANCE")
    private String acceptanceCode;
    @XmlAttribute(name = "STATUS")
    private String transactionState;
    @XmlAttribute(name = "ECI")
    private String eci;
    @XmlAttribute(name = "amount")
    private String amount;
    @XmlAttribute(name = "currency")
    private String currency;
    @XmlAttribute(name = "PM")
    private String paymentMethod;
    @XmlAttribute(name = "BRAND")
    private String paymentMethodBrand;

    public String getPurchaseId() {
        return purchaseId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getErrorState() {
        return errorState;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getAcceptanceCode() {
        return acceptanceCode;
    }

    public String getTransactionState() {
        return transactionState;
    }

    public String getEci() {
        return eci;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentMethodBrand() {
        return paymentMethodBrand;
    }
}
