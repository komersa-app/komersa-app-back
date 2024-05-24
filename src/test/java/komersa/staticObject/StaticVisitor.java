package komersa.staticObject;

import komersa.dto.request.VisitorDtoRequest;
import komersa.dto.response.VisitorDtoResponse;
import komersa.model.Visitor;


public class StaticVisitor {

    public static final Long ID = 1L;

    public static Visitor visitor1() {
        Visitor model = new Visitor();
        model.setId(ID);
        model.setFirstname("firstname");
        model.setEmail("email");
        model.setTel("tel");
        model.setMessage("message");
        model.setAppointment(StaticAppointment.appointment1());
        return model;
    }

    public static Visitor visitor2() {
        Visitor model = new Visitor();
        model.setId(ID);
        model.setFirstname("firstname");
        model.setEmail("email");
        model.setTel("tel");
        model.setMessage("message");
        model.setAppointment(StaticAppointment.appointment2());
        return model;
    }

    public static VisitorDtoRequest visitorDtoRequest1() {
        VisitorDtoRequest dtoRequest = new VisitorDtoRequest();
        dtoRequest.setFirstname("firstname");
        dtoRequest.setEmail("email");
        dtoRequest.setTel("tel");
        dtoRequest.setMessage("message");
        dtoRequest.setAppointmentId(1L);
        return dtoRequest;
    }

    public static VisitorDtoResponse visitorDtoResponse1() {
        VisitorDtoResponse dtoResponse = new VisitorDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setFirstname("firstname");
        dtoResponse.setEmail("email");
        dtoResponse.setTel("tel");
        dtoResponse.setMessage("message");
        dtoResponse.setAppointment(StaticAppointment.appointmentDtoResponse1());
        return dtoResponse;
    }

    public static VisitorDtoResponse visitorDtoResponse2() {
        VisitorDtoResponse dtoResponse = new VisitorDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setFirstname("firstname");
        dtoResponse.setEmail("email");
        dtoResponse.setTel("tel");
        dtoResponse.setMessage("message");
        dtoResponse.setAppointment(StaticAppointment.appointmentDtoResponse1());
        return dtoResponse;
    }
}
