package ro.msg.learning.shop.service.strategy;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.entity.Location;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.repository.LocationRepository;
import ro.msg.learning.shop.repository.StockRepository;

import java.util.*;

@Component("greedyStrategy")
public class GreedyStrategy implements LocationStrategy {

    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;

    public GreedyStrategy(StockRepository stockRepository,LocationRepository locationRepository) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<LocationProductQuantity> findLocations(Map<Product, Integer> products) {
        List<LocationProductQuantity> res=new ArrayList<>();
        Map<Product,Integer> remaining=new HashMap<>(products);

        List<Location> locations=locationRepository.findAll();

        for(Location location:locations){
            for(Map.Entry<Product,Integer> entry:remaining.entrySet()){
                Product product=entry.getKey();
                int quantity=entry.getValue();

                if (quantity<=0){
                    continue;
                }

                Optional<Stock> stockOptional=stockRepository.findByProductAndLocation(product,location);
                Stock stock=stockOptional.orElseThrow(()->new RuntimeException("Stock not found"));

                if(stock.getQuantity()>0){
                    int quantityTaken=Math.min(quantity,stock.getQuantity());
                    res.add(LocationProductQuantity.builder()
                            .location(location)
                            .product(product)
                            .quantity(quantityTaken)
                            .build());
                    remaining.put(product,quantity-quantityTaken);
                }
            }
        }
        for(Integer quantity:remaining.values()){
            if(quantity>0){
                throw  new RuntimeException("Quantity is greater than 0 - Failed");
            }
        }
        return res;
    }
}
