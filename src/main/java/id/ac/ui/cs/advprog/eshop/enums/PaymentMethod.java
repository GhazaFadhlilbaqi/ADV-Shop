package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CASH_ON_DELIVERY("Cash On Delivery"),
    VOUCHER("Voucher Payment");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public static boolean isValidPaymentMethod(String method) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.displayName.equals(method)) {
                return true;
            }
        }
        return false;
    }
}