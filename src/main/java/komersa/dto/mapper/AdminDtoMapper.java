package komersa.dto.mapper;

import komersa.model.Admin;
import komersa.dto.request.AdminDtoRequest;
import komersa.dto.response.AdminDtoResponse;
import komersa.model.Appointment;

public class AdminDtoMapper {

    public static Admin toModel(AdminDtoRequest request) {
        Admin model = new Admin();

        model.setPassword(request.getPassword());
        model.setName(request.getName());
        model.setEmail(request.getEmail());

        return model;
    }

    public static AdminDtoResponse toResponse(Admin model) {
        AdminDtoResponse response = new AdminDtoResponse();
        response.setId(model.getId());
        response.setEmail(model.getEmail());
        response.setName(model.getName());

        return response;
    }

    private AdminDtoMapper() {}

}
