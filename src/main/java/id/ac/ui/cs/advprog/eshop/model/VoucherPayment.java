package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

public class VoucherPayment extends Payment {
    public VoucherPayment(String id, String method, String status, Map<String, String> paymentData) {
        super(id, method, status, paymentData);
    }

    public VoucherPayment(String id, String method, Map<String, String> paymentData) {
        super(id, method, PaymentStatus.PENDING.getDisplayName(), paymentData);
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            int nums = 0;
            for (char c : paymentData.get("voucherCode").toCharArray()) {
                if (Character.isDigit(c)) {
                    nums++;
                }
            }

            if (paymentData.get("voucherCode").length() == 16 && paymentData.get("voucherCode").startsWith("ESHOP") && nums == 8) {
                setStatus(PaymentStatus.SUCCESS.getDisplayName());
            } else {
                setStatus(PaymentStatus.REJECTED.getDisplayName());
            }
        }
    }
}
