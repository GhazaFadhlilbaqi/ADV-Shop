package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Unlimited Games");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateProductPageDisplaysForm() throws Exception {
        // Test: Access the create product page
        // Expected: Should display create product form
        mockMvc.perform(get("/product/create"))
               .andExpect(status().isOk())
               .andExpect(view().name("CreateProduct"))
               .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductSuccessfullyRedirectsList() throws Exception {
        // Test: Submit new product creation
        // Expected: Should create product and redirect to list
        when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/create")
               .param("productName", "Unlimited Games")
               .param("productQuantity", "100"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("list"));

        verify(productService).create(any(Product.class));
    }

    @Test
    void testListProductShowsAllProducts() throws Exception {
        // Test: Access product list page
        // Expected: Should display all products
        when(productService.findAll()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/product/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("ProductList"))
               .andExpect(model().attributeExists("products"));

        verify(productService).findAll();
    }

    @Test
    void testEditProductPageDisplaysForm() throws Exception {
        // Test: Access edit product page
        // Expected: Should display edit form with product data
        when(productService.findById(product.getProductId())).thenReturn(product);

        mockMvc.perform(get("/product/edit/" + product.getProductId()))
               .andExpect(status().isOk())
               .andExpect(view().name("EditProduct"))
               .andExpect(model().attributeExists("product"));

        verify(productService).findById(product.getProductId());
    }

    @Test
    void testEditProductSuccessfullyRedirectsList() throws Exception {
        // Test: Submit product edit
        // Expected: Should update product and redirect to list
        when(productService.edit(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/edit")
               .param("productId", product.getProductId())
               .param("productName", "No More Games")
               .param("productQuantity", "200"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("list"));

        verify(productService).edit(any(Product.class));
    }

    @Test
    void testDeleteProductSuccessfullyRedirectsList() throws Exception {
        // Test: Delete existing product
        // Expected: Should delete product and redirect to list
        when(productService.delete(product.getProductId())).thenReturn(true);

        mockMvc.perform(get("/product/delete/" + product.getProductId()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/product/list"));

        verify(productService).delete(product.getProductId());
    }
}
