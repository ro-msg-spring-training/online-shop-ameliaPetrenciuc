package ro.msg.learning.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryAddressDTO {
    private String country;
    private String city;
    private String street;
    private String county;
}
