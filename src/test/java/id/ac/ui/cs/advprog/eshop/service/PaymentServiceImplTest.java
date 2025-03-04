package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    Order order;
    Payment testPayment;

    @BeforeEach
    void setUp() {
        // Setup products and order
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1709560000L, "Safira Sudrajat");

        // Setup payment data maps
        Map<String, String> validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP80235gh021f");

        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ZCZCW08255L18Assignment#08:Trydemos,runscripts,doLFSchapter1-5");

        Map<String, String> validBankData = new HashMap<>();
        validBankData.put("bankName", "Bank Ajarin Dong Bank");
        validBankData.put("referenceCode", "ea5f7b695ec97579a6f5e767ea5f9");

        // Setup test payments
        this.payments = new ArrayList<>();

        Payment payment1 = new Payment("13652556-012a-4c07-b546-54eb1396d79b",
                PaymentMethod.VOUCHER.getDisplayName(),
                PaymentStatus.SUCCESS.getDisplayName(),
                validVoucherData);

        Payment payment2 = new Payment("7f915bb-4b15-42f4-aebc-c3af385fb078",
                PaymentMethod.VOUCHER.getDisplayName(),
                PaymentStatus.REJECTED.getDisplayName(),
                invalidVoucherData);

        Payment payment3 = new Payment("e334ef40-9eff-4da8-9487-8ee697ecbf1e",
                PaymentMethod.CASH_ON_DELIVERY.getDisplayName(),
                PaymentStatus.SUCCESS.getDisplayName(),
                validBankData);

        payments.add(payment1);
        payments.add(payment2);
        payments.add(payment3);

        this.testPayment = payment1;
    }

    @Test
    void testAddPaymentCreatesNewPayment() {
        // Test: Add a new payment for an order
        // Fix: Mock findById to return null (no existing payment) and use any(Payment.class)
        when(paymentRepository.findById(order.getId())).thenReturn(null);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));

        // Expected: Payment should be created and returned with correct ID
        Payment result = paymentService.addPayment(this.order, testPayment.getMethod(), testPayment.getPaymentData());

        assertEquals(order.getId(), result.getId());
        assertEquals(testPayment.getMethod(), result.getMethod());
        assertEquals(PaymentStatus.PENDING.getDisplayName(), result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentReturnsNullForExistingPayment() {
        // Test: Try to add payment for an order that already has a payment
        // Fix: Create a payment with SUCCESS status and the order's ID
        Payment successPayment = new Payment(
            order.getId(),
            PaymentMethod.VOUCHER.getDisplayName(),
            PaymentStatus.SUCCESS.getDisplayName(),
            new HashMap<>()
        );

        when(paymentRepository.findById(order.getId())).thenReturn(successPayment);

        Payment result = paymentService.addPayment(this.order, testPayment.getMethod(), testPayment.getPaymentData());

        assertNull(result);
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testSetStatusToRejectedUpdatesPaymentAndOrder() {
        // Test: Update a payment status to rejected
        Payment payment = payments.get(2);

        // Fix: Add missing mock for findById and use any(Payment.class) for save()
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        when(orderRepository.findById(payment.getId())).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> {
            Order savedOrder = (Order)i.getArgument(0);
            order.setStatus(savedOrder.getStatus());
            return savedOrder;
        });

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getDisplayName());

        assertAll("Payment and order should be updated correctly",
            () -> assertEquals(payment.getId(), result.getId()),
            () -> assertEquals(PaymentStatus.REJECTED.getDisplayName(), result.getStatus()),
            () -> assertEquals("FAILED", order.getStatus())
        );

        // Verify repository interactions
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusWithInvalidStatusThrowsException() {
        // Test: Try to set an invalid payment status
        Payment payment = testPayment;
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "awokaowk");
        });

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testSetStatusWithNonexistentPaymentThrowsException() {
        // Test: Try to update status for a non-existent payment
        Payment payment = new Payment("woh",
                PaymentMethod.CASH_ON_DELIVERY.getDisplayName(),
                PaymentStatus.REJECTED.getDisplayName(),
                new HashMap<>());
        when(paymentRepository.findById("woh")).thenReturn(null);

        // Expected: Should throw NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> {
            paymentService.setStatus(payment, "woh");
        });

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentByIdReturnsCorrectPayment() {

        Payment payment = testPayment;
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        Payment result = paymentService.getPayment(payment.getId());

        assertAll("Payment properties should match",
            () -> assertEquals(payment.getId(), result.getId()),
            () -> assertEquals(payment.getMethod(), result.getMethod()),
            () -> assertEquals(payment.getStatus(), result.getStatus()),
            () -> assertEquals(payment.getPaymentData(), result.getPaymentData())
        );
    }

    @Test
    void testGetPaymentByNonexistentIdReturnsNull() {

        when(paymentRepository.findById("gemoy")).thenReturn(null);

        Payment result = paymentService.getPayment("gemoy");
        assertNull(result);
    }

    @Test
    void testGetAllPaymentsReturnsAllPayments() {

        when(paymentRepository.findAll()).thenReturn(payments);


        List<Payment> results = paymentService.getAllPayments();
        assertEquals(payments.size(), results.size());
    }
}
