package id.ac.ui.cs.advprog.eshop.repository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        for (int i=0; i<paymentData.size(); i++) {
            Payment p = paymentData.get(i);
            if (p.getId().equals(payment.getId())){
                paymentData.remove(i);
                paymentData.add(i, payment);
                return payment;
            }
        }
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String id) {
        for (Payment p : paymentData) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return paymentData;
    }
}