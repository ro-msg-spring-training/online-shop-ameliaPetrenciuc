package ro.msg.learning.shop.service;

import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Product;

public interface StockService {
    void updateStock(Product product, Location location, int quantity);
}
