package ro.msg.learning.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.CreateOrderDTO;
import ro.msg.learning.shop.dto.OrderResponseDTO;
import ro.msg.learning.shop.dto.ProductQuantityDTO;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.entity.OrderDetail;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.keys.OrderDetailId;
import ro.msg.learning.shop.mapper.DeliveryAddressMapper;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.repository.ProductRepository;
import ro.msg.learning.shop.service.impl.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceImpl orderService;
    private final ProductRepository productRepository;

    public OrderController(OrderServiceImpl orderService, ProductRepository productRepository) {
        this.orderService = orderService;
        this.productRepository = productRepository;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderDTO dto){
        Order order=Order.builder()
                .id(UUID.randomUUID())
                .createdAt(dto.getOrderDate())
                .country(dto.getDeliveryAddress().getCountry())
                .city(dto.getDeliveryAddress().getCity())
                .county(dto.getDeliveryAddress().getCounty())
                .street(dto.getDeliveryAddress().getStreet())
                .build();

        DeliveryAddressMapper.mapDtoToOrder(dto.getDeliveryAddress(),order);

        List<OrderDetail> orderDetailList=new ArrayList<>();
        for(ProductQuantityDTO productQuantityDTO:dto.getProducts()){
            Product product=productRepository.findById(productQuantityDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            OrderDetail detail= OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(productQuantityDTO.getQuantity())
                    .orderDetailId(new OrderDetailId(order.getId(), product.getId()))
                    .build();
            orderDetailList.add(detail);
        }

        order.setOrderDetailList(orderDetailList);

        Order savedOrder=orderService.createOrder(order);

        OrderResponseDTO responseDTO= OrderMapper.toDTO(savedOrder);
        return ResponseEntity.ok(responseDTO);
    }
}
