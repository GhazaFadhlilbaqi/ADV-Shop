package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        product.setProductId(String.valueOf(productData.size() + 1));
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Product edit(Product updatedProduct) {
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(updatedProduct.getProductId())) {
                productData.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }
}