package komersa.dto.mapper;

import komersa.model.Appointment;
import komersa.model.Visitor;
import komersa.dto.request.VisitorDtoRequest;
import komersa.dto.response.VisitorDtoResponse;

public class VisitorDtoMapper {

    public static Visitor toModel(VisitorDtoRequest request) {
        Visitor model = new Visitor();

        model.setFirstname(request.getFirstname());
        model.setEmail(request.getEmail());
        model.setTel(request.getTel());
        model.setMessage(request.getMessage());
        Appointment appointment = new Appointment();
        appointment.setId(request.getAppointmentId());
        model.setAppointment(appointment);

        return model;
    }

    public static VisitorDtoResponse toResponse(Visitor model) {
        VisitorDtoResponse response = new VisitorDtoResponse();

        response.setId(model.getId());
        response.setFirstname(model.getFirstname());
        response.setEmail(model.getEmail());
        response.setTel(model.getTel());
        response.setMessage(model.getMessage());
        response.setAppointment(AppointmentDtoMapper.toResponse(model.getAppointment()));

        return response;
    }

    private VisitorDtoMapper() {}

}
