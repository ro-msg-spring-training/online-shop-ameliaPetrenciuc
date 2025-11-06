package ro.msg.learning.shop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.entity.Stock;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.repository.StockRepository;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.strategy.LocationProductQuantity;
import ro.msg.learning.shop.service.strategy.LocationStrategy;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final LocationStrategy locationStrategy;
    private final StockRepository stockRepository;


    public OrderServiceImpl(OrderRepository orderRepository, LocationStrategy locationStrategy, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.locationStrategy = locationStrategy;
        this.stockRepository = stockRepository;
    }

    @Transactional
    public Order createOrder(Order order) {

        Map<Product, Integer> products=new HashMap<>();
        order.getOrderDetailList().forEach(detail->products.put(detail.getProduct(), detail.getQuantity()));

        List<LocationProductQuantity> locations=locationStrategy.findLocations(products);

        for(LocationProductQuantity obj :locations){
            Stock stock = stockRepository.findByProductAndLocation(obj.getProduct(), obj.getLocation())
                    .orElseThrow(() -> new RuntimeException("No stock for product " + obj.getProduct()));

            if (stock.getQuantity()<obj.getQuantity()) {//finally here i verify and deduct stock
                throw new RuntimeException("Quantity exceeded for product " + obj.getProduct().getName());
            }

            stock.setQuantity(stock.getQuantity()-obj.getQuantity());
            stockRepository.save(stock);

            order.getOrderDetailList().forEach(detail->{
                if(detail.getProduct().equals(obj.getProduct())){
                    detail.setShippedFrom(obj.getLocation());
                }
            });
        }

        return orderRepository.save(order);
    }
}
