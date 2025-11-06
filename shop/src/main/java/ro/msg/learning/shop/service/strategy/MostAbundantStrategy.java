package ro.msg.learning.shop.service.strategy;

import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("mostAbundantStrategy")
public class MostAbundantStrategy implements LocationStrategy {

    private final StockRepository stockRepository;

    public MostAbundantStrategy(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<LocationProductQuantity> findLocations(Map<Product, Integer> products) {
        List<LocationProductQuantity> res=new ArrayList<>();

        for(Map.Entry<Product,Integer> entry:products.entrySet()){
            Product product=entry.getKey();
            int quantity=entry.getValue();

            Optional<Stock> stockOptional=stockRepository.findTopByProductOrderByQuantityDesc(product);
            Stock stock=stockOptional.orElseThrow(()->new RuntimeException("Stock not found"));

            if(stock.getQuantity()<quantity){
                throw new RuntimeException("Stock Quantity Exceeded");
            }

            res.add(LocationProductQuantity.builder()
                    .location(stock.getLocation())
                    .product(product)
                    .quantity(quantity)
                    .build());
        }
        return res;
    }
}
