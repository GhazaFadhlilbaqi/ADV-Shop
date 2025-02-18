package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Unlimited Bacon");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateAndVerifyProduct() {
        // Test: Create a new product and verify its details
        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        // Expected: Product should be created with correct details
        assertNotNull(createdProduct);
        assertEquals("Unlimited Bacon", createdProduct.getProductName());
        assertEquals(100, createdProduct.getProductQuantity());
        verify(productRepository).create(product);
    }

    @Test
    void testFindAllWithMultipleProducts() {
        // Test: Retrieve multiple products from service
        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Limited Games");
        product2.setProductQuantity(50);

        when(productRepository.findAll()).thenReturn(List.of(product, product2).iterator());

        // Expected: Should return all products in the repository
        List<Product> products = productService.findAll();
        assertEquals(2, products.size());
        assertEquals("Unlimited Bacon", products.get(0).getProductName());
        assertEquals("Limited Games", products.get(1).getProductName());
        verify(productRepository).findAll();
    }

    @Test
    void testEditExistingProduct() {
        // Test: Edit an existing product's details
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sammich");
        updatedProduct.setProductQuantity(200);

        when(productRepository.edit(updatedProduct)).thenReturn(updatedProduct);

        // Expected: Product details should be updated successfully
        Product result = productService.edit(updatedProduct);
        assertNotNull(result);
        assertEquals("Sammich", result.getProductName());
        assertEquals(200, result.getProductQuantity());
        verify(productRepository).edit(updatedProduct);
    }

    @Test
    void testFindProductById() {
        // Test: Find a product using its ID
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        // Expected: Should return the correct product
        Product foundProduct = productService.findById(product.getProductId());
        assertNotNull(foundProduct);
        assertEquals("Unlimited Bacon", foundProduct.getProductName());
        assertEquals(100, foundProduct.getProductQuantity());
        verify(productRepository).findById(product.getProductId());
    }

    @Test
    void testDeleteExistingProduct() {
        // Test: Delete an existing product
        when(productRepository.delete(product.getProductId())).thenReturn(true);

        // Expected: Product should be deleted successfully
        boolean isDeleted = productService.delete(product.getProductId());
        assertTrue(isDeleted);
        verify(productRepository).delete(product.getProductId());
    }
}
