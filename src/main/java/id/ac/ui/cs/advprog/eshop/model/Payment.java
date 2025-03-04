package id.ac.ui.cs.advprog.eshop.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Payment {
    private static final List<String> VALID_METHODS = Arrays.asList("Cash On Delivery", "Voucher Payment");
    private static final List<String> VALID_STATUSES = Arrays.asList("SUCCESS", "REJECTED");
    
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        if (!VALID_METHODS.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }
}