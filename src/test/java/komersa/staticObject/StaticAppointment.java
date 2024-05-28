package komersa.staticObject;

import komersa.dto.request.AppointmentDtoRequest;
import komersa.dto.response.AppointmentDtoResponse;
import komersa.model.Appointment;

import java.time.LocalDateTime;

public class StaticAppointment {

    public static final Long ID = 1L;

    public static Appointment appointment1() {
        Appointment model = new Appointment();
        model.setId(ID);
        model.setName("name");
        model.setDatetime(LocalDateTime.MIN);
        model.setStatus("status");
        model.setCar(StaticCar.car1());
        model.setAdmin(StaticUser.user1());
        model.setVisitor(StaticUser.user1());
        model.setAppointment(StaticAppointment.appointment1());
        return model;
    }

    public static Appointment appointment2() {
        Appointment model = new Appointment();
        model.setId(ID);
        model.setName("name");
        model.setDatetime(LocalDateTime.MIN);
        model.setStatus("status");
        model.setCar(StaticCar.car2());
        model.setAdmin(StaticUser.user2());
        model.setVisitor(StaticUser.user2());
        model.setAppointment(StaticAppointment.appointment2());
        return model;
    }

    public static AppointmentDtoRequest appointmentDtoRequest1() {
        AppointmentDtoRequest dtoRequest = new AppointmentDtoRequest();
        dtoRequest.setName("name");
        dtoRequest.setStatus("status");
        dtoRequest.setCarId(1L);
        dtoRequest.setAdminId(1L);
        dtoRequest.setVisitorId(1L);
        dtoRequest.setAppointmentId(1L);
        return dtoRequest;
    }

    public static AppointmentDtoResponse appointmentDtoResponse1() {
        AppointmentDtoResponse dtoResponse = new AppointmentDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setDatetime(LocalDateTime.MIN);
        dtoResponse.setStatus("status");
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        dtoResponse.setAdmin(StaticUser.userDtoResponse1());
        dtoResponse.setVisitor(StaticUser.userDtoResponse1());
        dtoResponse.setAppointment(StaticAppointment.appointmentDtoResponse1());
        return dtoResponse;
    }

    public static AppointmentDtoResponse appointmentDtoResponse2() {
        AppointmentDtoResponse dtoResponse = new AppointmentDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setDatetime(LocalDateTime.MIN);
        dtoResponse.setStatus("status");
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        dtoResponse.setAdmin(StaticUser.userDtoResponse1());
        dtoResponse.setVisitor(StaticUser.userDtoResponse1());
        dtoResponse.setAppointment(StaticAppointment.appointmentDtoResponse1());
        return dtoResponse;
    }
}
