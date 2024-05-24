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
        model.setStatus(Appointment.Status.PENDING);
        model.setCar(StaticCar.car1());
        return model;
    }

    public static Appointment appointment2() {
        Appointment model = new Appointment();
        model.setId(ID);
        model.setName("name");
        model.setDatetime(LocalDateTime.MIN);
        model.setStatus(Appointment.Status.VALIDATED);
        model.setCar(StaticCar.car2());
        return model;
    }

    public static AppointmentDtoRequest appointmentDtoRequest1() {
        AppointmentDtoRequest dtoRequest = new AppointmentDtoRequest();
        dtoRequest.setName("name");
        dtoRequest.setStatus("status");
        dtoRequest.setCarId(1L);
        return dtoRequest;
    }

    public static AppointmentDtoResponse appointmentDtoResponse1() {
        AppointmentDtoResponse dtoResponse = new AppointmentDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setDatetime(LocalDateTime.MIN);
        dtoResponse.setStatus(Appointment.Status.ARCHIVED);
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }

    public static AppointmentDtoResponse appointmentDtoResponse2() {
        AppointmentDtoResponse dtoResponse = new AppointmentDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setDatetime(LocalDateTime.MIN);
        dtoResponse.setStatus(Appointment.Status.ARCHIVED);
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }
}
