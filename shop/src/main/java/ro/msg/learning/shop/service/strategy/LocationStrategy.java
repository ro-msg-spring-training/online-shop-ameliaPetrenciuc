package ro.msg.learning.shop.service.strategy;

import ro.msg.learning.shop.entity.Product;

import java.util.List;
import java.util.Map;

public interface LocationStrategy {
    List<LocationProductQuantity> findLocations(Map<Product,Integer> products);
}
