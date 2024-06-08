package komersa.staticObject;

import komersa.dto.request.AdminDtoRequest;
import komersa.dto.response.AdminDtoResponse;
import komersa.model.Admin;


public class StaticAdmin {

    public static final Long ID = 1L;

    public static Admin admin1() {
        Admin model = new Admin();
        model.setId(ID);
        model.setPassword("password");
        return model;
    }

    public static Admin admin2() {
        Admin model = new Admin();
        model.setId(ID);
        model.setPassword("password");
        return model;
    }

    public static AdminDtoRequest adminDtoRequest1() {
        AdminDtoRequest dtoRequest = new AdminDtoRequest();
        dtoRequest.setPassword("password");
        return dtoRequest;
    }

    public static AdminDtoResponse adminDtoResponse1() {
        AdminDtoResponse dtoResponse = new AdminDtoResponse();
        dtoResponse.setId(ID);
        return dtoResponse;
    }

    public static AdminDtoResponse adminDtoResponse2() {
        AdminDtoResponse dtoResponse = new AdminDtoResponse();
        dtoResponse.setId(ID);
        return dtoResponse;
    }
}
