package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class VoucherPayment extends Payment {
    private static final String VOUCHER_CODE_KEY = "voucherCode";
    private static final String REQUIRED_PREFIX = "ESHOP";
    private static final int REQUIRED_LENGTH = 16;
    private static final int REQUIRED_DIGIT_COUNT = 8;
    
    public VoucherPayment(String id, String method, String status, Map<String, String> paymentData) {
        super(id, method, status, paymentData);
    }

    public VoucherPayment(String id, String method, Map<String, String> paymentData) {
        super(id, method, PaymentStatus.PENDING.getDisplayName(), paymentData);
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty");
        } else {
            String voucherCode = paymentData.get(VOUCHER_CODE_KEY);
            
            if (isValidVoucherCode(voucherCode)) {
                setStatus(PaymentStatus.SUCCESS.getDisplayName());
            } else {
                setStatus(PaymentStatus.REJECTED.getDisplayName());
            }
        }
    }

    // Ensuring the voucher meets all requirements
    private boolean isValidVoucherCode(String voucherCode) {
        return hasCorrectLength(voucherCode) && 
               hasCorrectPrefix(voucherCode) && 
               hasCorrectDigitCount(voucherCode);
    }

    // Length requirement checker
    private boolean hasCorrectLength(String voucherCode) {
        return voucherCode.length() == REQUIRED_LENGTH;
    }

    // Prefix requirement checker
    private boolean hasCorrectPrefix(String voucherCode) {
        return voucherCode.startsWith(REQUIRED_PREFIX);
    }

    // Digit count requirement checker
    private boolean hasCorrectDigitCount(String voucherCode) {
        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        return digitCount == REQUIRED_DIGIT_COUNT;
    }
}
