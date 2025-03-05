package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class CashPayment extends Payment {
    
    public CashPayment(String id, String method, String status, Map<String, String> paymentData) {
        super(id, method, status, paymentData);
    }

    public CashPayment(String id, String method, Map<String, String> paymentData) {
        super(id, method, PaymentStatus.PENDING.getDisplayName(), paymentData);
    }

    public void setPaymentData(Map<String, String> paymentData) {
    }

}
