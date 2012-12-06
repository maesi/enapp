package org.maesi.hslu.enapp.ext.postfinance;

public class PaymentRequest {

    private String purchaseId;
    private String amount;
    private String currency;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardCvc;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String orderId) {
        this.purchaseId = orderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }
}
