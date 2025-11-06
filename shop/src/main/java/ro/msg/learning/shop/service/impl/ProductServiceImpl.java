package ro.msg.learning.shop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.ProductService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(UUID id, Product product) {
        Product existing=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setWeight(product.getWeight());
        existing.setImageUrl(product.getImageUrl());
        existing.setCategory(product.getCategory());
        return productRepository.save(product);
    }

    @Override
    public void delete(UUID id) {
        Product existing=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        productRepository.delete(existing);
    }

    @Override
    public Product findById(UUID id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAllWithCategory();
    }
}
