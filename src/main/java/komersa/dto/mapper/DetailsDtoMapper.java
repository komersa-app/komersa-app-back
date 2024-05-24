package komersa.dto.mapper;

import komersa.model.Car;
import komersa.model.Details;
import komersa.dto.request.DetailsDtoRequest;
import komersa.dto.response.DetailsDtoResponse;

public class DetailsDtoMapper {

    public static Details toModel(DetailsDtoRequest request) {
        Details model = new Details();

        model.setBrand(request.getBrand());
        model.setModel(request.getModel());
        Car car = new Car();
        car.setId(request.getCarId());
        model.setCar(car);

        return model;
    }

    public static DetailsDtoResponse toResponse(Details model) {
        DetailsDtoResponse response = new DetailsDtoResponse();

        response.setId(model.getId());
        response.setBrand(model.getBrand());
        response.setModel(model.getModel());
        response.setCar(CarDtoMapper.toResponse(model.getCar()));

        return response;
    }

    private DetailsDtoMapper() {}

}
