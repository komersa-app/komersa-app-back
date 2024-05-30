package komersa.staticObject;

import komersa.dto.request.UserDtoRequest;
import komersa.dto.response.UserDtoResponse;
import komersa.model.User;


public class StaticUser {

    public static final Long ID = 1L;

    public static User user1() {
        User model = new User();
        model.setId(ID);
        model.setName("name");
        model.setEmail("email");
        return model;
    }

    public static User user2() {
        User model = new User();
        model.setId(ID);
        model.setName("name");
        model.setEmail("email");
        return model;
    }

    public static UserDtoRequest userDtoRequest1() {
        UserDtoRequest dtoRequest = new UserDtoRequest();
        dtoRequest.setName("name");
        dtoRequest.setEmail("email");
        return dtoRequest;
    }

    public static UserDtoResponse userDtoResponse1() {
        UserDtoResponse dtoResponse = new UserDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setEmail("email");
        return dtoResponse;
    }

    public static UserDtoResponse userDtoResponse2() {
        UserDtoResponse dtoResponse = new UserDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setName("name");
        dtoResponse.setEmail("email");
        return dtoResponse;
    }
}
