package komersa.dto.mapper;

import komersa.model.Admin;
import komersa.dto.request.AdminDtoRequest;
import komersa.dto.response.AdminDtoResponse;

public class AdminDtoMapper {

    public static Admin toModel(AdminDtoRequest request) {
        Admin model = new Admin();

        model.setPassword(request.getPassword());

        return model;
    }

    public static AdminDtoResponse toResponse(Admin model) {
        AdminDtoResponse response = new AdminDtoResponse();

        response.setId(model.getId());
        response.setPassword(model.getPassword());

        return response;
    }

    private AdminDtoMapper() {}

}
