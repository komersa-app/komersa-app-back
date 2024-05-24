package komersa.dto.mapper;

import komersa.model.Car;
import komersa.model.Appointment;
import komersa.dto.request.AppointmentDtoRequest;
import komersa.dto.response.AppointmentDtoResponse;

public class AppointmentDtoMapper {

    public static Appointment toModel(AppointmentDtoRequest request) {
        Appointment model = new Appointment();

        model.setName(request.getName());
        model.setStatus(Appointment.Status.valueOf(request.getStatus()));
        Car car = new Car();
        car.setId(request.getCarId());
        model.setCar(car);

        return model;
    }

    public static AppointmentDtoResponse toResponse(Appointment model) {
        AppointmentDtoResponse response = new AppointmentDtoResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setDatetime(model.getDatetime());
        response.setStatus(model.getStatus());
        response.setCar(CarDtoMapper.toResponse(model.getCar()));

        return response;
    }

    private AppointmentDtoMapper() {}

}
