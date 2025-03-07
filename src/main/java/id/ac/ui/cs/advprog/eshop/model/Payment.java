package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        if (!PaymentMethod.isValidPaymentMethod(method)) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        
        if (!PaymentStatus.isValidPaymentStatus(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (!PaymentStatus.isValidPaymentStatus(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }
}