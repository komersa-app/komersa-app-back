package komersa.dto.mapper;

import komersa.model.Car;
import komersa.model.price;
import komersa.dto.request.priceDtoRequest;
import komersa.dto.response.priceDtoResponse;

public class priceDtoMapper {

    public static price toModel(priceDtoRequest request) {
        price model = new price();

        model.setAmount(request.getAmount());
        Car car = new Car();
        car.setId(request.getCarId());
        model.setCar(car);

        return model;
    }

    public static priceDtoResponse toResponse(price model) {
        priceDtoResponse response = new priceDtoResponse();

        response.setId(model.getId());
        response.setAmount(model.getAmount());
        response.setChangeDatetime(model.getChangeDatetime());
        response.setCar(CarDtoMapper.toResponse(model.getCar()));

        return response;
    }

    private priceDtoMapper() {}

}
