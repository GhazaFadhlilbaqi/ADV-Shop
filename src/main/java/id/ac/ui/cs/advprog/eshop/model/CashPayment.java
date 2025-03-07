package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class CashPayment extends Payment {
    private static final String ADDRESS_KEY = "address";
    private static final String DELIVERY_FEE_KEY = "deliveryFee";
    
    public CashPayment(String id, String method, String status, Map<String, String> paymentData) {
        super(id, method, status, paymentData);
    }

    public CashPayment(String id, String method, Map<String, String> paymentData) {
        super(id, method, PaymentStatus.PENDING.getDisplayName(), paymentData);
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        } else {
            String address = paymentData.get(ADDRESS_KEY);
            String deliveryFee = paymentData.get(DELIVERY_FEE_KEY);
            
            if (isValidPaymentData(address, deliveryFee)) {
                setStatus(PaymentStatus.SUCCESS.getDisplayName());
            } else {
                setStatus(PaymentStatus.REJECTED.getDisplayName());
            }
        }
    }

    private boolean isValidPaymentData(String address, String deliveryFee) {
        return isNotNullOrEmpty(address) && isNotNullOrEmpty(deliveryFee);
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
