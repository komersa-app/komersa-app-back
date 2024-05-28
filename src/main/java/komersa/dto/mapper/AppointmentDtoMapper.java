package komersa.dto.mapper;

import komersa.model.Appointment;
import komersa.model.User;
import komersa.model.User;
import komersa.model.Car;
import komersa.model.Appointment;
import komersa.dto.request.AppointmentDtoRequest;
import komersa.dto.response.AppointmentDtoResponse;

public class AppointmentDtoMapper {

    public static Appointment toModel(AppointmentDtoRequest request) {
        Appointment model = new Appointment();

        model.setName(request.getName());
        model.setStatus(request.getStatus());
        Car car = new Car();
        car.setId(request.getCarId());
        model.setCar(car);
        User admin = new User();
        admin.setId(request.getAdminId());
        model.setAdmin(admin);
        User visitor = new User();
        visitor.setId(request.getVisitorId());
        model.setVisitor(visitor);
        Appointment appointment = new Appointment();
        appointment.setId(request.getAppointmentId());
        model.setAppointment(appointment);

        return model;
    }

    public static AppointmentDtoResponse toResponse(Appointment model) {
        AppointmentDtoResponse response = new AppointmentDtoResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setDatetime(model.getDatetime());
        response.setStatus(model.getStatus());
        response.setCar(CarDtoMapper.toResponse(model.getCar()));
        response.setAdmin
visitor(UserDtoMapper.toResponse(model.getAdmin()));
        response.setAdmin
visitor(UserDtoMapper.toResponse(model.getVisitor()));
        response.setAppointment(AppointmentDtoMapper.toResponse(model.getAppointment()));

        return response;
    }

    private AppointmentDtoMapper() {}

}
