package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CashPaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<String, String>();
    }

    @Test
    void testEmptyPaymentData() {
        CashPayment payment = new CashPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), this.paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setPaymentData(this.paymentData));
    }

    @Test
    void testSetValidPaymentData() {
        this.paymentData.put("address", "Mordor");
        this.paymentData.put("deliveryFee", "10000");
        CashPayment payment = new CashPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.SUCCESS.getDisplayName(), payment.getStatus());
    }

    @Test
    void testMissingAddressPaymentData() {
        this.paymentData.put("deliveryFee", "1984");
        CashPayment payment = new CashPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testMissingDeliveryFeePaymentData() {
        this.paymentData.put("address", "Cocytus");
        CashPayment payment = new CashPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testEmptyAddressPaymentData() {
        this.paymentData.put("address", "");
        this.paymentData.put("deliveryFee", "1738");
        CashPayment payment = new CashPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testEmptyDeliveryFeePaymentData() {
        this.paymentData.put("address", "Canada");
        this.paymentData.put("deliveryFee", "");
        CashPayment payment = new CashPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
}
