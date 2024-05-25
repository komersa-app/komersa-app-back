package komersa.dto.mapper;

import komersa.model.Car;
import komersa.model.Price;
import komersa.dto.request.PriceDtoRequest;
import komersa.dto.response.PriceDtoResponse;

public class PriceDtoMapper {

    public static Price toModel(PriceDtoRequest request) {
        Price model = new Price();

        Car car = new Car();
        car.setId(request.getCarId());
        model.setCar(car);

        return model;
    }

    public static PriceDtoResponse toResponse(Price model) {
        PriceDtoResponse response = new PriceDtoResponse();

        response.setId(model.getId());
        response.setChangeDatetime(model.getChangeDatetime());
        response.setCar(CarDtoMapper.toResponse(model.getCar()));

        return response;
    }

    private PriceDtoMapper() {}

}
