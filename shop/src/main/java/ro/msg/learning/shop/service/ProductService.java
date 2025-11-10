package ro.msg.learning.shop.service;

import ro.msg.learning.shop.entity.Product;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product create(Product product);
    Product update(UUID id, Product product);
    void delete(UUID id);
    Product findById(UUID id);
    List<Product> findAll();
}
