package ro.msg.learning.shop.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.service.strategy.FallbackLocationStrategy;
import ro.msg.learning.shop.service.strategy.LocationProductQuantity;
import ro.msg.learning.shop.service.strategy.LocationStrategy;

import java.util.UUID;
import java.util.Map;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FallbackLocationStrategyTest {

    private LocationStrategy failingStrategy;
    private LocationStrategy successfulStrategy;
    private FallbackLocationStrategy fallbackStrategy;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder().id(UUID.randomUUID()).name("Prod1").build();
        failingStrategy = mock(LocationStrategy.class);
        successfulStrategy = mock(LocationStrategy.class);
        fallbackStrategy = new FallbackLocationStrategy(List.of(failingStrategy, successfulStrategy));
    }

    @Test
    void findLocations_shouldUseSecondStrategyIfFirstFails() {
        Map<Product, Integer> products = Map.of(product, 5);
        when(failingStrategy.findLocations(products)).thenThrow(new RuntimeException("Fail"));
        LocationProductQuantity lpq = LocationProductQuantity.builder().product(product).quantity(5).location(null).build();
        when(successfulStrategy.findLocations(products)).thenReturn(List.of(lpq));

        List<LocationProductQuantity> result = fallbackStrategy.findLocations(products);

        assertThat(result).hasSize(1);
        verify(failingStrategy).findLocations(products);
        verify(successfulStrategy).findLocations(products);
    }

    @Test
    void findLocations_shouldThrowIfAllStrategiesFail() {
        Map<Product, Integer> products = Map.of(product, 5);
        when(failingStrategy.findLocations(products)).thenThrow(new RuntimeException("Fail"));
        when(successfulStrategy.findLocations(products)).thenThrow(new RuntimeException("Fail too"));

        assertThrows(RuntimeException.class, () -> fallbackStrategy.findLocations(products));
    }
}
