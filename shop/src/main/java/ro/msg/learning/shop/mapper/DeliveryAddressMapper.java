package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.DeliveryAddressDTO;
import ro.msg.learning.shop.entity.Order;

public class DeliveryAddressMapper {

    public static void mapDtoToOrder(DeliveryAddressDTO dto, Order order) {
        order.setCountry(dto.getCountry());
        order.setCity(dto.getCity());
        order.setCounty(dto.getCounty());
        order.setStreet(dto.getStreet());
    }

    public static DeliveryAddressDTO mapOrderToDto(Order order) {
        return DeliveryAddressDTO.builder()
                .country(order.getCountry())
                .city(order.getCity())
                .county(order.getCounty())
                .street(order.getStreet())
                .build();
    }
}
