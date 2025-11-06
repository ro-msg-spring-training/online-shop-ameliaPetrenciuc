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
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.strategy.GreedyStrategy;
import ro.msg.learning.shop.service.strategy.LocationProductQuantity;

import java.util.UUID;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GreedyStrategyTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private GreedyStrategy strategy;

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
    void findLocations_shouldDistributeProductsAcrossLocations() {
        Map<Product, Integer> products = Map.of(product1, 7, product2, 5);

        when(locationRepository.findAll()).thenReturn(List.of(location1, location2));
        when(stockRepository.findByProductAndLocation(product1, location1)).thenReturn(Optional.of(Stock.builder()
                .product(product1)
                .location(location1)
                .quantity(5)
                .build()));

        when(stockRepository.findByProductAndLocation(product1, location2)).thenReturn(Optional.of(Stock.builder()
                .product(product1)
                .location(location2)
                .quantity(5)
                .build()));

        when(stockRepository.findByProductAndLocation(product2, location1)).thenReturn(Optional.of(Stock.builder()
                .product(product2)
                .location(location1)
                .quantity(5)
                .build()));

//        when(stockRepository.findByProductAndLocation(product2, location2)).thenReturn(Optional.of(Stock.builder()
//                .product(product2)
//                .location(location2)
//                .quantity(5)
//                .build()));

        List<LocationProductQuantity> result = strategy.findLocations(products);

        assertThat(result).hasSize(3);
        int totalProd1 = result.stream().filter(lpq -> lpq.getProduct().equals(product1)).mapToInt(LocationProductQuantity::getQuantity).sum();
        int totalProd2 = result.stream().filter(lpq -> lpq.getProduct().equals(product2)).mapToInt(LocationProductQuantity::getQuantity).sum();

        assertThat(totalProd1).isEqualTo(7);
        assertThat(totalProd2).isEqualTo(5);
    }

    @Test
    void findLocations_shouldThrowWhenNotEnoughStock() {
        Map<Product, Integer> products = Map.of(product1, 10);
        when(locationRepository.findAll()).thenReturn(List.of(location1));
        when(stockRepository.findByProductAndLocation(product1, location1)).thenReturn(Optional.of(Stock.builder()
                .product(product1)
                .location(location1)
                .quantity(5)
                .build()));
        assertThrows(RuntimeException.class, () -> strategy.findLocations(products));
    }
}
