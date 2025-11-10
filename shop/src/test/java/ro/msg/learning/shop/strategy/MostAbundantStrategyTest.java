package ro.msg.learning.shop.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.entity.keys.StockId;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.strategy.LocationProductQuantity;
import ro.msg.learning.shop.service.strategy.MostAbundantStrategy;

import java.util.UUID;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MostAbundantStrategyTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private MostAbundantStrategy strategy;

    private Product product1;

    @BeforeEach
    void setUp() {
        product1 = Product.builder()
                    .id(UUID.randomUUID())
                    .name("Prod1")
                    .build();
    }

    @Test
    void findLocations_shouldReturnStockWithHighestQuantity() {
        Map<Product, Integer> products = Map.of(product1, 5);
        Stock stock = Stock.builder()
                .stockId(new StockId(product1.getId(), null))
                .product(product1)
                .location(null)
                .quantity(10)
                .build();


        when(stockRepository.findTopByProductOrderByQuantityDesc(product1)).thenReturn(Optional.of(stock));

        List<LocationProductQuantity> result = strategy.findLocations(products);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getProduct()).isEqualTo(product1);
        assertThat(result.getFirst().getQuantity()).isEqualTo(5);
    }

    @Test
    void findLocations_shouldThrowWhenQuantityExceedsStock() {
        Map<Product, Integer> products = Map.of(product1, 15);
        Stock stock = Stock.builder()
                .stockId(new StockId(product1.getId(), null))
                .product(product1)
                .location(null)
                .quantity(10)
                .build();

        when(stockRepository.findTopByProductOrderByQuantityDesc(product1)).thenReturn(Optional.of(stock));

        assertThrows(RuntimeException.class, () -> strategy.findLocations(products));
    }
}
