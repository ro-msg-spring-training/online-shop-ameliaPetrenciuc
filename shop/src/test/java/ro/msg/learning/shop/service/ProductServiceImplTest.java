package ro.msg.learning.shop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.ProductCategory;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.impl.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private UUID id;
    private ProductCategory category;

    @BeforeEach
    void setUp() {
        category = ProductCategory.builder()
                .id(UUID.randomUUID())
                .name("Books")
                .description("All kinds of books")
                .productList(new ArrayList<>())
                .build();

        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Hansel And Gretel")
                .description("Novel")
                .price(BigDecimal.valueOf(50))
                .weight(0.3)
                .imageUrl("img.png")
                .category(category)
                .build();

        category.getProductList().add(product);
    }

    @Test
    void create_shouldSaveProduct(){
        when(productRepository.save(product)).thenReturn(product);
        Product saved=productService.create(product);
        assertThat(saved).isEqualTo(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void findById_shouldReturnProduct(){
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product found = productService.findById(id);
        assertThat(found.getName()).isEqualTo("Hansel And Gretel");
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void findById_shouldThrowWhenNotFound() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,() -> productService.findById(id));
    }

    @Test
    void update_shouldModifyProduct() {
        Product updated = Product.builder()
                .id(id)
                .name("Updated")
                .description("Updated Desc")
                .price(BigDecimal.valueOf(10))
                .weight(0.4)
                .imageUrl("new.png")
                .category(category)
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updated);
        Product result = productService.update(id, updated);
        assertThat(result.getName()).isEqualTo("Updated");
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void delete_shouldRemoveProduct() {
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        productService.delete(id);
        verify(productRepository, times(1)).delete(product);
    }
}
