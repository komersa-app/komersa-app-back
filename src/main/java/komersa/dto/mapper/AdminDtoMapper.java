package komersa.dto.mapper;

import komersa.model.Admin;
import komersa.dto.request.AdminDtoRequest;
import komersa.dto.response.AdminDtoResponse;

public class AdminDtoMapper {

    public static Admin toModel(AdminDtoRequest request) {
        Admin model = new Admin();

        model.setAdmId(request.getAdmId());
        model.setAdmPswd(request.getAdmPswd());

        return model;
    }

    public static AdminDtoResponse toResponse(Admin model) {
        AdminDtoResponse response = new AdminDtoResponse();

        response.setAdmId(model.getAdmId());
        response.setAdmPswd(model.getAdmPswd());

        return response;
    }

    private AdminDtoMapper() {}

}
