package ro.msg.learning.shop.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.repository.OrderRepository;
import ro.msg.learning.shop.service.OrderService;
import ro.msg.learning.shop.service.StockService;
import ro.msg.learning.shop.service.strategy.LocationProductQuantity;
import ro.msg.learning.shop.service.strategy.LocationStrategy;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final LocationStrategy locationStrategy;
    private final StockService stockService;


    public OrderServiceImpl(OrderRepository orderRepository, LocationStrategy locationStrategy, StockService stockService) {
        this.orderRepository = orderRepository;
        this.locationStrategy = locationStrategy;
        this.stockService = stockService;
    }

    @Transactional
    public Order createOrder(Order order) {

        Map<Product, Integer> products=new HashMap<>();
        order.getOrderDetailList().forEach(detail->products.put(detail.getProduct(), detail.getQuantity()));

        List<LocationProductQuantity> locations=locationStrategy.findLocations(products);

        for(LocationProductQuantity obj : locations){
            stockService.updateStock(obj.getProduct(), obj.getLocation(), obj.getQuantity());

            order.getOrderDetailList().forEach(detail->{
                if(detail.getProduct().equals(obj.getProduct())){
                    detail.setShippedFrom(obj.getLocation());
                }
            });
        }
        return orderRepository.save(order);
    }
}
