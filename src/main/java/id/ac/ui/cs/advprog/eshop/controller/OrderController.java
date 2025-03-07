package id.ac.ui.cs.advprog.eshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        List<Product> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "CreateOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam("selectedProducts") List<String> selectedProductIds,
                                 @RequestParam("author") String author,
                                 Model model) {
        // Return to form if no products selected
        if (selectedProductIds == null || selectedProductIds.isEmpty()) {
            model.addAttribute("errorMessage", "Please select at least one product");
            model.addAttribute("products", productService.findAll());
            return "CreateOrder";
        }

        List<Product> selectedProducts = new ArrayList<>();
        for (String productId : selectedProductIds) {
            Product product = productService.findById(productId);
            if (product != null) {
                selectedProducts.add(product);
            }
        }

        // Create and save the order
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, selectedProducts, System.currentTimeMillis(), author);
        orderService.createOrder(order);

        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String orderHistoryPage(Model model) {
        model.addAttribute("authorName", "");
        return "OrderHistory";
    }

    @PostMapping("/history")
    public String showOrderHistory(@RequestParam("authorName") String authorName, Model model) {
        List<Order> orders = orderService.findAllByAuthor(authorName);
        model.addAttribute("orders", orders);
        model.addAttribute("authorName", authorName);
        return "OrderHistory";
    }

    @GetMapping("/pay/{orderId}")
    public String paymentPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return "redirect:/order/history";
        }
        model.addAttribute("order", order);
        return "PayOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String processPayment(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        if (order == null) {
            return "redirect:/order/history";
        }
        
        // Update order status to PAID
        order = orderService.updateStatus(orderId, "PAID");
        
        // Generate a payment ID
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8);
        model.addAttribute("order", order);
        model.addAttribute("paymentId", paymentId);
        
        return "PaymentConfirmation";
    }
}
