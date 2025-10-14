package be.kdg.sa.orderservice.api.restaurant.dtos;


import be.kdg.sa.orderservice.domain.restaurant.Address;

public record AddressDto(String street, int number, int postalCode, String country) {
    public static AddressDto from(Address address){
        return new AddressDto(address.getStreet(), address.getNumber(), address.getPostalCode(), address.getCountry());
    }
}
