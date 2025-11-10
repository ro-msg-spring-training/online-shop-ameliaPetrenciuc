package ro.msg.learning.shop.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.entity.keys.StockId;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.strategy.LocationProductQuantity;
import ro.msg.learning.shop.service.strategy.SingleLocationStrategy;

import java.util.UUID;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SingleLocationStrategyTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private SingleLocationStrategy strategy;

    private Product product1;
    private Product product2;
    private Location location1;
    private Location location2;

    @BeforeEach
    void setUp() {
        product1 = Product.builder().id(UUID.randomUUID()).name("Prod1").build();
        product2 = Product.builder().id(UUID.randomUUID()).name("Prod2").build();

        location1 = Location.builder().id(UUID.randomUUID()).name("Loc1").build();
        location2 = Location.builder().id(UUID.randomUUID()).name("Loc2").build();
    }

    @Test
    void findLocations_shouldReturnLocationWithAllProducts() {
        Map<Product, Integer> products = Map.of(product1, 5, product2, 3);

        when(locationRepository.findAll()).thenReturn(List.of(location1, location2));
        when(stockRepository.findByProductAndLocation(product1, location1)).thenReturn(Optional.of(Stock.builder()
                .stockId(new StockId(product1.getId(), location1.getId()))
                .product(product1)
                .location(location1)
                .quantity(10)
                .build()));

        when(stockRepository.findByProductAndLocation(product2, location1)).thenReturn(Optional.of(Stock.builder()
                .stockId(new StockId(product2.getId(), location1.getId()))
                .product(product2)
                .location(location1)
                .quantity(5)
                .build()));

        List<LocationProductQuantity> result = strategy.findLocations(products);

        assertThat(result).hasSize(2);
        assertThat(result).allMatch(lpq -> lpq.getLocation().equals(location1));
    }

    @Test
    void findLocations_shouldThrowWhenNoLocationHasAllProducts() {
        Map<Product, Integer> products = Map.of(product1, 5, product2, 3);

        when(locationRepository.findAll()).thenReturn(List.of(location1));
        when(stockRepository.findByProductAndLocation(product1, location1)).thenReturn(Optional.of(Stock.builder()
                .stockId(new StockId(product1.getId(), location1.getId()))
                .product(product1)
                .location(location1)
                .quantity(10)
                .build()));
        when(stockRepository.findByProductAndLocation(product2, location1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> strategy.findLocations(products));
    }
}
