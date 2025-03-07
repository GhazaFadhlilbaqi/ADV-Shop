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

    @Test
    void testFourArgumentConstructor() {
        this.paymentData.put("voucherCode", "ESHOP6743FHD8521");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b",
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  PaymentStatus.SUCCESS.getDisplayName(),
                                                  this.paymentData);
        assertEquals(PaymentStatus.SUCCESS.getDisplayName(), payment.getStatus());
        assertEquals("ESHOP6743FHD8521", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testNullVoucherCode() {
        this.paymentData.put("voucherCode", null);
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testEmptyVoucherCode() {
        this.paymentData.put("voucherCode", "");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testCorrectLengthButWrongPrefixAndDigits() {
        this.paymentData.put("voucherCode", "REALESHOPABCDEFGHIJ");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testCorrectPrefixButWrongLengthAndDigits() {
        this.paymentData.put("voucherCode", "ESHOPABCD");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testLongVoucherCode() {
        this.paymentData.put("voucherCode", "ESHOP12345678901234567890");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testCorrectLengthAndPrefixButInsufficientDigits() {
        this.paymentData.put("voucherCode", "ESHOPABCDEF12345");  // Only 5 digits
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testCorrectLengthAndDigitsButWrongPrefix() {
        this.paymentData.put("voucherCode", "SHOP123456789012");  // Has 12 digits but wrong prefix
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testExactlyEightDigits() {
        this.paymentData.put("voucherCode", "ESHOP12345678ABC");  // Exactly 8 digits
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.SUCCESS.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testMoreThanEightDigits() {
        this.paymentData.put("voucherCode", "ESHOP123456789AB");  // 9 digits
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }
    
    @Test
    void testGetPaymentData() {
        this.paymentData.put("voucherCode", "ESHOP12345678ABCD");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                   PaymentMethod.VOUCHER.getDisplayName(),
                                                   this.paymentData);
        Map<String, String> retrievedData = payment.getPaymentData();
        assertEquals(this.paymentData, retrievedData);
    }

    @Test
    void testMissingVoucherCodeKey() {
        this.paymentData.put("VirusCode", "ESHOP12345678ABC");
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(), 
                                                  this.paymentData);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testNullVoucherCodeHelperMethods() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b", 
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  this.paymentData);
        
        this.paymentData.put("voucherCode", null);
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
        
        this.paymentData.put("voucherCode", "");
        payment.setPaymentData(this.paymentData);
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), payment.getStatus());
    }

    @Test
    void testNullCheckForHasCorrectLength() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b",
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  new HashMap<>());

        try {
            java.lang.reflect.Method hasCorrectLengthMethod =
                VoucherPayment.class.getDeclaredMethod("hasCorrectLength", String.class);
            hasCorrectLengthMethod.setAccessible(true);
            boolean result = (boolean) hasCorrectLengthMethod.invoke(payment, (String)null);
            assertEquals(false, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNullCheckForHasCorrectPrefix() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b",
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  new HashMap<>());

        try {
            java.lang.reflect.Method hasCorrectPrefixMethod =
                VoucherPayment.class.getDeclaredMethod("hasCorrectPrefix", String.class);
            hasCorrectPrefixMethod.setAccessible(true);
            boolean result = (boolean) hasCorrectPrefixMethod.invoke(payment, (String)null);
            assertEquals(false, result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHasCorrectDigitCountWithNull() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b",
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  new HashMap<>());

        try {
            java.lang.reflect.Method hasCorrectDigitCountMethod =
                VoucherPayment.class.getDeclaredMethod("hasCorrectDigitCount", String.class);
            hasCorrectDigitCountMethod.setAccessible(true);

            boolean nullResult = (boolean) hasCorrectDigitCountMethod.invoke(payment, (String)null);
            assertEquals(false, nullResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHasCorrectDigitCountWithExactDigits() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b",
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  new HashMap<>());

        try {
            java.lang.reflect.Method hasCorrectDigitCountMethod =
                VoucherPayment.class.getDeclaredMethod("hasCorrectDigitCount", String.class);
            hasCorrectDigitCountMethod.setAccessible(true);

            boolean exactDigitsResult = (boolean) hasCorrectDigitCountMethod.invoke(payment, "12345678ESHOPABCDEFG");
            assertEquals(true, exactDigitsResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHasCorrectDigitCountWithScatteredDigits() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b",
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  new HashMap<>());

        try {
            java.lang.reflect.Method hasCorrectDigitCountMethod =
                VoucherPayment.class.getDeclaredMethod("hasCorrectDigitCount", String.class);
            hasCorrectDigitCountMethod.setAccessible(true);

            boolean boundaryResult = (boolean) hasCorrectDigitCountMethod.invoke(payment, "1SHOPABCDEFG2345678");
            assertEquals(true, boundaryResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testHasCorrectDigitCountWithLongString() {
        VoucherPayment payment = new VoucherPayment("13652556-012a-4c07-b546-54eb1396d79b",
                                                  PaymentMethod.VOUCHER.getDisplayName(),
                                                  new HashMap<>());

        try {
            java.lang.reflect.Method hasCorrectDigitCountMethod =
                VoucherPayment.class.getDeclaredMethod("hasCorrectDigitCount", String.class);
            hasCorrectDigitCountMethod.setAccessible(true);

            StringBuilder longString = new StringBuilder("ESHOP");
            for (int i = 0; i < 100; i++) {
                if (i < 8) {
                    longString.append(i % 10);
                } else {
                    longString.append('X');
                }
            }
            boolean longStringResult = (boolean) hasCorrectDigitCountMethod.invoke(payment, longString.toString());
            assertEquals(true, longStringResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}