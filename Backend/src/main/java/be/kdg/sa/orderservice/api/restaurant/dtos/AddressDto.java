package be.kdg.sa.orderservice.api.restaurant.dtos;


import be.kdg.sa.orderservice.domain.restaurant.Address;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record AddressDto(String street, int number, int postalCode, String country) {
    public static AddressDto from(Address address){
        return new AddressDto(address.street(), address.number(), address.postalCode(), address.country());
    }
}
