package komersa.staticObject;

import komersa.dto.request.UserDtoRequest;
import komersa.dto.response.UserDtoResponse;
import komersa.model.User;


public class StaticUser {


    public static User user1() {
        User model = new User();
        model.setUsrId(Staticlong.long1());
        model.setUsrFname("usrFname");
        model.setUsrEmail("usrEmail");
        model.setUsrPswd("usrPswd");
        return model;
    }

    public static User user2() {
        User model = new User();
        model.setUsrId(Staticlong.long2());
        model.setUsrFname("usrFname");
        model.setUsrEmail("usrEmail");
        model.setUsrPswd("usrPswd");
        return model;
    }

    public static UserDtoRequest userDtoRequest1() {
        UserDtoRequest dtoRequest = new UserDtoRequest();
        dtoRequest.setUsrId("usrId");
        dtoRequest.setUsrFname("usrFname");
        dtoRequest.setUsrEmail("usrEmail");
        dtoRequest.setUsrPswd("usrPswd");
        return dtoRequest;
    }

    public static UserDtoResponse userDtoResponse1() {
        UserDtoResponse dtoResponse = new UserDtoResponse();
        dtoResponse.setUsrId(Staticlong.longDtoResponse1());
        dtoResponse.setUsrFname("usrFname");
        dtoResponse.setUsrEmail("usrEmail");
        dtoResponse.setUsrPswd("usrPswd");
        return dtoResponse;
    }

    public static UserDtoResponse userDtoResponse2() {
        UserDtoResponse dtoResponse = new UserDtoResponse();
        dtoResponse.setUsrId(Staticlong.longDtoResponse1());
        dtoResponse.setUsrFname("usrFname");
        dtoResponse.setUsrEmail("usrEmail");
        dtoResponse.setUsrPswd("usrPswd");
        return dtoResponse;
    }
}
