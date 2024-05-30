package komersa.dto.mapper;

import komersa.model.Prices;
import komersa.model.Details;
import komersa.model.Car;
import komersa.dto.request.CarDtoRequest;
import komersa.dto.response.CarDtoResponse;

public class CarDtoMapper {

    public static Car toModel(CarDtoRequest request) {
        Car model = new Car();

        model.setName(request.getName());
        model.setDescription(request.getDescription());
        model.setColor(request.getColor());
        model.setMotorType(request.getMotorType());
        model.setPower(request.getPower());
        model.setStatus(request.getStatus());
        model.setType(request.getType());
        Details details = new Details();
        details.setId(request.getDetailsId());
        model.setDetails(details);
        Prices price = new Prices();
        price.setId(request.getPriceId());
        model.setPrice(price);

        return model;
    }

    public static CarDtoResponse toResponse(Car model) {
        CarDtoResponse response = new CarDtoResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setDescription(model.getDescription());
        response.setColor(model.getColor());
        response.setMotorType(model.getMotorType());
        response.setPower(model.getPower());
