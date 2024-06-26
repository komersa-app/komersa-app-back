package komersa.dto.mapper;

import komersa.model.*;
import komersa.model.User;
import komersa.model.Appointment;
import komersa.dto.request.AppointmentDtoRequest;
import komersa.dto.response.AppointmentDtoResponse;

public class AppointmentDtoMapper {

    public static Appointment toModel(AppointmentDtoRequest request) {
        Appointment model = new Appointment();

        model.setName(request.getName());
        model.setStatus(request.getStatus());
	    model.setDatetime(request.getDatetime());
        model.setMessage(request.getMessage());
        Car car = new Car();
        car.setId(request.getCarId());
        model.setCar(car);
        Visitor visitor = new Visitor();
        visitor.setId(request.getVisitorId());
        model.setVisitor(visitor);

        return model;
    }

    public static AppointmentDtoResponse toResponse(Appointment model) {
        AppointmentDtoResponse response = new AppointmentDtoResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setDatetime(model.getDatetime());
        response.setStatus(model.getStatus());
        response.setMessage(model.getMessage());
        response.setCar(CarDtoMapper.toResponse(model.getCar()));
        response.setVisitor(VisitorDtoMapper.toResponse(model.getVisitor()));

        return response;
    }

    private AppointmentDtoMapper() {}

}
