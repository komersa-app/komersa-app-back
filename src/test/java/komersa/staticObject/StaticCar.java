package komersa.staticObject;

import komersa.dto.request.CarDtoRequest;
import komersa.dto.response.CarDtoResponse;
import komersa.model.Car;


public class StaticCar {

    public static final Long ID = 1L;

    public static Car car1() {
        Car model = new Car();
        model.setId(ID);
        model.setName("name");
        model.setDescription("description");
        model.setColor("color");
        model.setMotorType("motorType");
        model.setPower("power");
        model.setStatus("status");
        model.setType("type");
        return model;
    }

    public static Car car2() {
        Car model = new Car();
        model.setId(ID);
        model.setName("name");
        model.setDescription("description");
        model.setColor("color");
        model.setMotorType("motorType");
        model.setPower("power");
        model.setStatus("status");
        model.setType("type");
        return model;
    }

    public static CarDtoRequest carDtoRequest1() {
        CarDtoRequest dtoRequest = new CarDtoRequest();
        dtoRequest.setName("name");
        dtoRequest.setDescription("description");
        dtoRequest.setColor("color");
        dtoRequest.setMotorType("motorType");
        dtoRequest.setPower("power");
        dtoRequest.setStatus("status");
        dtoRequest.setType("type");
        return dtoRequest;
    }

    public static CarDtoResponse carDtoResponse1() {
        CarDtoResponse dtoResponse = new CarDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setDescription("description");
        dtoResponse.setColor("color");
        dtoResponse.setMotorType("motorType");
        dtoResponse.setPower("power");
        dtoResponse.setStatus("status");
        dtoResponse.setType("type");
        return dtoResponse;
    }

    public static CarDtoResponse carDtoResponse2() {
        CarDtoResponse dtoResponse = new CarDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setDescription("description");
        dtoResponse.setColor("color");
        dtoResponse.setMotorType("motorType");
        dtoResponse.setPower("power");
        dtoResponse.setStatus("status");
        dtoResponse.setType("type");
        return dtoResponse;
    }
}
