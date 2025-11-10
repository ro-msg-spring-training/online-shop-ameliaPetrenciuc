package ro.msg.learning.shop.service.strategy;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entity.*;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component("singleLocationStrategy")
public class SingleLocationStrategy implements LocationStrategy {
    private final LocationRepository locationRepository;
    private final StockRepository stockRepository;

    public SingleLocationStrategy(LocationRepository locationRepository, StockRepository stockRepository) {
        this.locationRepository = locationRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public List<LocationProductQuantity> findLocations(Map<Product, Integer> products) {
        List<Location> locations = locationRepository.findAll();

        for(Location loc:locations){
            boolean hasAllProducts=true;

            for(Map.Entry<Product,Integer> entry:products.entrySet()){
                Product product = entry.getKey();
                int  quantity = entry.getValue();
                Optional<Stock> stock = stockRepository.findByProductAndLocation(product, loc);
                if(stock.isEmpty() || stock.get().getQuantity()<quantity){
                    hasAllProducts=false;
                    break;
                }
            }
            if(hasAllProducts){
                List<LocationProductQuantity> res=new ArrayList<>();
                for(Map.Entry<Product,Integer> entry:products.entrySet()){
                    res.add(LocationProductQuantity.builder()
                            .location(loc)
                            .product(entry.getKey())
                            .quantity(entry.getValue())
                            .build());
                }
                return res;
            }
        }
        throw new RuntimeException("No location has all products in stock");
    }
}
