package ro.msg.learning.shop.service.impl;

import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void updateStock(Product product, Location location, int quantity) {
        Stock stock = stockRepository.findByProductAndLocation(product,location)
                .orElseThrow(() -> new RuntimeException("No stock for product " + product.getName()
                                                        + " at location " + location.getName()));

        if (stock.getQuantity() < quantity) {
            throw new RuntimeException("Quantity exceeded for product " + product.getName());
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }
}
