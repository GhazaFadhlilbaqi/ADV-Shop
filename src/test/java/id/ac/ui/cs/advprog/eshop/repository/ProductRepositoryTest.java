package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditExistingProduct() {
        // Test: Edit an existing product with new details
        Product product = new Product();
        product.setProductName("Shampoo for fragile hair: Lavander Scented");
        product.setProductQuantity(100);
        Product createdProduct = productRepository.create(product);
        
        Product updatedProduct = new Product();
        updatedProduct.setProductId(createdProduct.getProductId());
        updatedProduct.setProductName("13-in-one Shampoo: For Men");
        updatedProduct.setProductQuantity(345);

        // Expected: Product details should be updated successfully
        Product result = productRepository.edit(updatedProduct);
        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testEditProductWithNullId() {
        // Test: Edit an existing product with a null id
        Product product = new Product();
        product.setProductName("A super cool limited edition product");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(null);
        updatedProduct.setProductName("An ultra lame unlimited edition product");
        updatedProduct.setProductQuantity(200);

        // Expected: Returns null when trying to edit the product
        Product result = productRepository.edit(updatedProduct);
        assertNull(result);
    }

    @Test
    void testEditNonExistentProduct() {
        // Test: Attempt to edit a product that doesn't exist
        Product updatedProduct = new Product();
        updatedProduct.setProductId("No-ID-cuz-it-does-not-exist");
        updatedProduct.setProductName("McLovin");
        updatedProduct.setProductQuantity(100);

        // Expected: Should return null for non-existent product
        Product result = productRepository.edit(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteExistingProduct() {
        // Test: Delete an existing product from repository
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Hot stinky pile of garbage");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Expected: Product should be deleted and repository should be empty
        boolean isDeleted = productRepository.delete(product.getProductId());
        assertTrue(isDeleted);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        // Test: Attempt to delete a product with non-existent ID
        // Expected: Should return false for non-existent product
        boolean isDeleted = productRepository.delete("fake-and-illegal-id");
        assertFalse(isDeleted);
    }

    @Test
    void testDeleteFirstProductFromMultipleProducts() {
        // Test: Delete first product from a repository containing multiple products
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Banjo-Kazooie");
        product1.setProductQuantity(123);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Gex: Enter the Gecko");
        product2.setProductQuantity(456);
        productRepository.create(product2);

        // Expected: First product should be deleted while second remains intact
        boolean isDeleted = productRepository.delete("1");
        assertTrue(isDeleted);

        // Check if only one product remains, and verifies if the correct product is remaining
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product remainingProduct = productIterator.next();
        assertEquals("2", remainingProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditFirstProductWhilePreservingSecondProduct() {
        // Test: Edit first product while maintaining second product's data
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Unlimited Bacon");
        product1.setProductQuantity(987);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("No Games");
        product2.setProductQuantity(654);
        productRepository.create(product2);

        // Expected: First product should be updated while second remains unchanged
        Product updatedProduct = new Product();
        updatedProduct.setProductId("1");
        updatedProduct.setProductName("Unlimited Games");
        updatedProduct.setProductQuantity(321);
        
        Product result = productRepository.edit(updatedProduct);
        assertNotNull(result);
        assertEquals("Unlimited Games", result.getProductName());

        // Verify second product remained unchanged
        Product unchangedProduct = productRepository.findById("2");
        assertEquals("No Games", unchangedProduct.getProductName());
    }
}