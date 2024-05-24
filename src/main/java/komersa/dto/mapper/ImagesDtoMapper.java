package komersa.dto.mapper;

import komersa.model.Car;
import komersa.model.Images;
import komersa.dto.request.ImagesDtoRequest;
import komersa.dto.response.ImagesDtoResponse;

public class ImagesDtoMapper {

    public static Images toModel(ImagesDtoRequest request) {
        Images model = new Images();

        model.setUrl(request.getUrl());
        Car car = new Car();
        car.setId(request.getCarId());
        model.setCar(car);

        return model;
    }

    public static ImagesDtoResponse toResponse(Images model) {
        ImagesDtoResponse response = new ImagesDtoResponse();

        response.setId(model.getId());
        response.setUrl(model.getUrl());
        response.setCar(CarDtoMapper.toResponse(model.getCar()));

        return response;
    }

    private ImagesDtoMapper() {}

}
