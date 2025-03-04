package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.HashMap;
import java.util.Map;

public class PaymentTest {
    private Map<String, String> paymentData;
    
    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<String, String>();
    }
    
    @Test
    void testCreatePaymentInvalidMethod() {
        paymentData.put("giftCard", "$19 Fornite Card");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("b94d0613-a74b-427e-8967-dc40abda13e7", "Fortnite V-Bucks", "SUCCESS", paymentData);
        });
    }

    @Test
    void testCreatePaymentValidStatus() {
        paymentData.put("voucherCode", "ESHOP1738");
        Payment payment = new Payment("b94d0613-a74b-427e-8967-dc40abda13e7", 
                                     PaymentMethod.VOUCHER.getDisplayName(), 
                                     PaymentStatus.SUCCESS.getDisplayName(), 
                                     paymentData);

        assertEquals(PaymentStatus.SUCCESS.getDisplayName(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        paymentData.put("voucherCode", "ESHOP1738");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("b94d0613-a74b-427e-8967-dc40abda13e7", 
                                         PaymentMethod.VOUCHER.getDisplayName(), 
                                         "I LOVE ADPRO", 
                                         paymentData);
        });
    }

    @Test
    void testCreatePaymentValidMethod() {
        paymentData.put("voucherCode", "ESHOP1738");
        Payment payment = new Payment("b94d0613-a74b-427e-8967-dc40abda13e7", 
                                     PaymentMethod.VOUCHER.getDisplayName(), 
                                     PaymentStatus.SUCCESS.getDisplayName(), 
                                     paymentData);

        assertEquals(PaymentMethod.VOUCHER.getDisplayName(), payment.getMethod());
    }

    @Test
    void testSetStatusInvalid() {
        paymentData.put("voucherCode", "ESHOP1738");
        Payment payment = new Payment("b94d0613-a74b-427e-8967-dc40abda13e7", 
                                     PaymentMethod.VOUCHER.getDisplayName(), 
                                     PaymentStatus.SUCCESS.getDisplayName(), 
                                     paymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("I LOVE DAA"));
    }

    @Test
    void testSetStatusValid() {
        paymentData.put("voucherCode", "ESHOP1738");
        Payment payment = new Payment("b94d0613-a74b-427e-8967-dc40abda13e7", 
                                     PaymentMethod.VOUCHER.getDisplayName(), 
                                     PaymentStatus.SUCCESS.getDisplayName(), 
                                     paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getDisplayName());

        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
}