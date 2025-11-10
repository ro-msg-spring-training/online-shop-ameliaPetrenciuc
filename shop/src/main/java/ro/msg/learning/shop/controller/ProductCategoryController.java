package ro.msg.learning.shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import ro.msg.learning.shop.dto.ProductCategoryDTO;
import ro.msg.learning.shop.entity.ProductCategory;
import ro.msg.learning.shop.mapper.ProductCategoryMapper;
import ro.msg.learning.shop.service.ProductCategoryService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping
    public ResponseEntity<ProductCategoryDTO> create(@RequestBody ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = ProductCategoryMapper.toEntity(productCategoryDTO);
        ProductCategory savedCategory= productCategoryService.create(productCategory);
        return new ResponseEntity<>(ProductCategoryMapper.toDTO(savedCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> update(@RequestBody ProductCategoryDTO productCategoryDTO,@PathVariable UUID id) {
        ProductCategory productCategory = ProductCategoryMapper.toEntity(productCategoryDTO);
        ProductCategory updatedProduct = productCategoryService.update(id,productCategory);
        return ResponseEntity.ok(ProductCategoryMapper.toDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> getAll() {
        List<ProductCategoryDTO> productsCategory = productCategoryService.findAll()
                .stream()
                .map(ProductCategoryMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsCategory);
    }
}
