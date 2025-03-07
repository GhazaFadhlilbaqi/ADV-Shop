package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED"),
    PENDING("PENDING");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public static boolean isValidPaymentStatus(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.displayName.equals(status)) {
                return true;
            }
        }
        return false;
    }
}