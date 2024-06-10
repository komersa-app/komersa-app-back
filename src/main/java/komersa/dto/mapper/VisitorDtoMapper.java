package komersa.dto.mapper;

import komersa.model.Visitor;
import komersa.dto.request.VisitorDtoRequest;
import komersa.dto.response.VisitorDtoResponse;

public class VisitorDtoMapper {

    public static Visitor toModel(VisitorDtoRequest request) {
        Visitor model = new Visitor();

        model.setEmail(request.getEmail());
        model.setName(request.getName());
        return model;
    }

    public static VisitorDtoResponse toResponse(Visitor model) {
        VisitorDtoResponse response = new VisitorDtoResponse();

        response.setId(model.getId());
        response.setEmail(model.getEmail());
        response.setName(model.getName());
        return response;
    }

    private VisitorDtoMapper() {}

}
