package komersa.staticObject;

import komersa.dto.request.AdminDtoRequest;
import komersa.dto.response.AdminDtoResponse;
import komersa.model.Admin;


public class StaticAdmin {


    public static Admin admin1() {
        Admin model = new Admin();
        model.setAdmId(Staticlong.long1());
        model.setAdmPswd("admPswd");
        return model;
    }

    public static Admin admin2() {
        Admin model = new Admin();
        model.setAdmId(Staticlong.long2());
        model.setAdmPswd("admPswd");
        return model;
    }

    public static AdminDtoRequest adminDtoRequest1() {
        AdminDtoRequest dtoRequest = new AdminDtoRequest();
        dtoRequest.setAdmId("admId");
        dtoRequest.setAdmPswd("admPswd");
        return dtoRequest;
    }

    public static AdminDtoResponse adminDtoResponse1() {
        AdminDtoResponse dtoResponse = new AdminDtoResponse();
        dtoResponse.setAdmId(Staticlong.longDtoResponse1());
        dtoResponse.setAdmPswd("admPswd");
        return dtoResponse;
    }

    public static AdminDtoResponse adminDtoResponse2() {
        AdminDtoResponse dtoResponse = new AdminDtoResponse();
        dtoResponse.setAdmId(Staticlong.longDtoResponse1());
        dtoResponse.setAdmPswd("admPswd");
        return dtoResponse;
    }
}
