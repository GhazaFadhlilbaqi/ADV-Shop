package id.ac.ui.cs.advprog.eshop.functional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class OrderFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
               .andExpect(status().isOk())
               .andExpect(view().name("CreateOrder"))
               .andExpect(model().attributeExists("products"));
    }

    @Test
    public void testGetOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
               .andExpect(status().isOk())
               .andExpect(view().name("OrderHistory"))
               .andExpect(model().attributeExists("authorName"));
    }

    @Test
    public void testPostOrderHistory() throws Exception {
        mockMvc.perform(post("/order/history")
               .param("authorName", "testUser"))
               .andExpect(status().isOk())
               .andExpect(view().name("OrderHistory"))
               .andExpect(model().attributeExists("orders"))
               .andExpect(model().attributeExists("authorName"));
    }

    @Test
    public void testGetPaymentPage() throws Exception {

        String testOrderId = "test-order-123";
        
        mockMvc.perform(get("/order/pay/" + testOrderId))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testPayOrder() throws Exception {
        String testOrderId = "test-order-123";
        
        mockMvc.perform(post("/order/pay/" + testOrderId))
               .andExpect(status().is3xxRedirection());
    }
}
