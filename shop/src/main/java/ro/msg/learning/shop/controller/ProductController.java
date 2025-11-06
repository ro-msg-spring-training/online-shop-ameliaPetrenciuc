package ro.msg.learning.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.msg.learning.shop.dto.ProductRequestDTO;
import ro.msg.learning.shop.dto.ProductResponseDTO;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.ProductCategory;
import ro.msg.learning.shop.mapper.ProductMapper;
import ro.msg.learning.shop.service.ProductCategoryService;
import ro.msg.learning.shop.service.ProductService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    public ProductController(ProductService productService, ProductCategoryService productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductCategory productCategory = productCategoryService.findById(productRequestDTO.getCategoryId());
        Product product = ProductMapper.toEntity(productRequestDTO,productCategory);
        Product savedProduct = productService.create(product);
        return new ResponseEntity<>(ProductMapper.toDTO(savedProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductRequestDTO productRequestDTO,@PathVariable UUID id) {
        ProductCategory productCategory = productCategoryService.findById(productRequestDTO.getCategoryId());
        Product product = ProductMapper.toEntity(productRequestDTO,productCategory);
        Product updatedProduct = productService.update(id,product);
        return ResponseEntity.ok(ProductMapper.toDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable UUID id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(ProductMapper.toDTO(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        List<ProductResponseDTO> products = productService.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
}
