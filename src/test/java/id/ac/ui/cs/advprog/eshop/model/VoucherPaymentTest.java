package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VoucherPaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<String, String>();
    }

    @Test
    void testEmptyPaymentData() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER.getDisplayName(), this.paymentData);
        assertThrows(IllegalArgumentException.class, () -> payment.setPaymentData(this.paymentData));
    }

    @Test
    void testSetValidPaymentData() {
        this.paymentData.put("voucherCode", "ESHOP6743FHD8521");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.SUCCESS.getDisplayName(), payment.getStatus());
    }

    @Test
    void testSetShortInvalidPaymentData() {
        this.paymentData.put("voucherCode", "ESHOP12357234FB");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testNoPrefixInvalidPaymentData() {
        this.paymentData.put("voucherCode", "173817381738");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testNoEightNumberPaymentData() {
        this.paymentData.put("voucherCode", "ESHOPIMLIKEHEYWHATSUPHELLO");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", PaymentMethod.VOUCHER.getDisplayName(), this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
}