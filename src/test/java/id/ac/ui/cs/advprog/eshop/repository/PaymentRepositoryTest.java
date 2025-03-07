package id.ac.ui.cs.advprog.eshop.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment testPayment;
    private Map<String, String> paymentData;
    
    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();
        this.paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP80235gh021f");
        
        this.testPayment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", 
                PaymentMethod.VOUCHER.getDisplayName(), 
                PaymentStatus.SUCCESS.getDisplayName(), 
                paymentData);
    }
    
    @Test
    void testSaveReturnsCorrectPaymentId() {
        Payment result = paymentRepository.save(testPayment);
        assertEquals(testPayment.getId(), result.getId());
    }

    @Test
    void testSavePersistsPaymentCorrectly() {
        paymentRepository.save(testPayment);
        Payment savedPayment = paymentRepository.findById(testPayment.getId());
        assertEquals(testPayment.getId(), savedPayment.getId());
    }

    @Test
    void testUpdateChangesPaymentStatus() {
        paymentRepository.save(testPayment);
        
        Payment modifiedPayment = new Payment(testPayment.getId(), 
                testPayment.getMethod(), 
                PaymentStatus.REJECTED.getDisplayName(), 
                testPayment.getPaymentData());
                
        paymentRepository.save(modifiedPayment);
        
        Payment updatedPayment = paymentRepository.findById(testPayment.getId());
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), updatedPayment.getStatus());
    }

    @Test
    void testUpdatePreservesOriginalMethod() {
        paymentRepository.save(testPayment);
        
        Payment modifiedPayment = new Payment(testPayment.getId(), 
                testPayment.getMethod(), 
                PaymentStatus.REJECTED.getDisplayName(), 
                testPayment.getPaymentData());
                
        paymentRepository.save(modifiedPayment);
        
        Payment updatedPayment = paymentRepository.findById(testPayment.getId());
        assertEquals(testPayment.getMethod(), updatedPayment.getMethod());
    }

    @Test
    void testUpdatePreservesPaymentData() {
        paymentRepository.save(testPayment);
        
        Payment modifiedPayment = new Payment(testPayment.getId(), 
                testPayment.getMethod(), 
                PaymentStatus.REJECTED.getDisplayName(), 
                testPayment.getPaymentData());
                
        paymentRepository.save(modifiedPayment);
        
        Payment updatedPayment = paymentRepository.findById(testPayment.getId());
        assertEquals(testPayment.getPaymentData(), updatedPayment.getPaymentData());
    }

    @Test
    void testFindByIdReturnsCorrectPayment() {
        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "Bank Ajarin Dong Bank");
        bankData.put("referenceCode", "ea5f7b695ec97579a6f5e767ea5f9");
        
        Payment payment = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e", 
                PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), 
                PaymentStatus.SUCCESS.getDisplayName(), 
                bankData);
        
        paymentRepository.save(payment);
        
        Payment foundPayment = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), foundPayment.getId());
    }

    @Test
    void testFindByNonexistentIdReturnsNull() {
        Payment foundPayment = paymentRepository.findById("non-existent-id");
        assertNull(foundPayment);
    }

    @Test
    void testFindAllReturnsCorrectCount() {
        // Save first payment
        paymentRepository.save(testPayment);
        
        // Save second payment
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ZCZCW08255L18");
        Payment payment2 = new Payment("7f915bb-4b15-42f4-aebc-c3af385fb078", 
                PaymentMethod.VOUCHER.getDisplayName(), 
                PaymentStatus.REJECTED.getDisplayName(), 
                voucherData);
        paymentRepository.save(payment2);
        
        // Save third payment
        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "Bank Ajarin Dong Bank");
        bankData.put("referenceCode", "ea5f7b695ec97579a6f5e767ea5f9");
        Payment payment3 = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e", 
                PaymentMethod.CASH_ON_DELIVERY.getDisplayName(), 
                PaymentStatus.SUCCESS.getDisplayName(), 
                bankData);
        paymentRepository.save(payment3);
        
        List<Payment> paymentList = paymentRepository.findAll();
        assertEquals(3, paymentList.size());
    }

    @Test
    void testFindAllReturnsEmptyListWhenNoPayments() {
        // Create a fresh repository with no payments
        PaymentRepository emptyRepo = new PaymentRepository();
        List<Payment> paymentList = emptyRepo.findAll();
        
        assertTrue(paymentList.isEmpty());
    }
    
    @Test
    void testSaveUpdatesCorrectPaymentWhenMultipleExist() {
        paymentRepository.save(testPayment);
        
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ZCZCW08255L18");
        Payment payment2 = new Payment("7f915bb-4b15-42f4-aebc-c3af385fb078", 
                PaymentMethod.VOUCHER.getDisplayName(), 
                PaymentStatus.REJECTED.getDisplayName(), 
                voucherData);
        paymentRepository.save(payment2);
        
        Payment updatedPayment = new Payment(testPayment.getId(), 
                testPayment.getMethod(), 
                PaymentStatus.REJECTED.getDisplayName(), 
                testPayment.getPaymentData());
        paymentRepository.save(updatedPayment);
        
        List<Payment> allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size());
        
        Payment retrievedPayment = paymentRepository.findById(testPayment.getId());
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), retrievedPayment.getStatus());
        
        Payment retrievedPayment2 = paymentRepository.findById(payment2.getId());
        assertEquals(PaymentStatus.REJECTED.getDisplayName(), retrievedPayment2.getStatus());
    }
}