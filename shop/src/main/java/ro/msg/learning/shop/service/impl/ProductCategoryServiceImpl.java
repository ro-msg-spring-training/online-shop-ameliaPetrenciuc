package ro.msg.learning.shop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.ProductCategory;
import ro.msg.learning.shop.repository.ProductCategoryRepository;
import ro.msg.learning.shop.service.ProductCategoryService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory create(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductCategory update(UUID id, ProductCategory productCategory) {
        ProductCategory existing=productCategoryRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        existing.setName(productCategory.getName());
        existing.setDescription(productCategory.getDescription());
        return productCategoryRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        ProductCategory existing=productCategoryRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        productCategoryRepository.deleteById(existing.getId());
    }

    @Override
    public ProductCategory findById(UUID id) {
        return productCategoryRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }
}
