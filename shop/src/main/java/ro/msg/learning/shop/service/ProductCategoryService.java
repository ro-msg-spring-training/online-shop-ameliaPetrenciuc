package ro.msg.learning.shop.service;

import ro.msg.learning.shop.entity.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ProductCategoryService {
    ProductCategory create(ProductCategory productCategory);
    ProductCategory update(UUID id, ProductCategory productCategory);
    void delete(UUID id);
    ProductCategory findById(UUID id);
    List<ProductCategory> findAll();
}
