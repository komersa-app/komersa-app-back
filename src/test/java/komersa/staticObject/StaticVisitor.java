package komersa.staticObject;

import komersa.dto.request.VisitorDtoRequest;
import komersa.dto.response.VisitorDtoResponse;
import komersa.model.Visitor;


public class StaticVisitor {

    public static final Long ID = 1L;

    public static Visitor visitor1() {
        Visitor model = new Visitor();
        model.setId(ID);
        return model;
    }

    public static Visitor visitor2() {
        Visitor model = new Visitor();
        model.setId(ID);
        return model;
    }

    public static VisitorDtoRequest visitorDtoRequest1() {
        VisitorDtoRequest dtoRequest = new VisitorDtoRequest();
        return dtoRequest;
    }

    public static VisitorDtoResponse visitorDtoResponse1() {
        VisitorDtoResponse dtoResponse = new VisitorDtoResponse();
        dtoResponse.setId(ID);
        return dtoResponse;
    }

    public static VisitorDtoResponse visitorDtoResponse2() {
        VisitorDtoResponse dtoResponse = new VisitorDtoResponse();
        dtoResponse.setId(ID);
        return dtoResponse;
    }
}
