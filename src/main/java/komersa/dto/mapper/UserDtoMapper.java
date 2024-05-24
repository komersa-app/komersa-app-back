package komersa.dto.mapper;

import komersa.model.User;
import komersa.dto.request.UserDtoRequest;
import komersa.dto.response.UserDtoResponse;

public class UserDtoMapper {

    public static User toModel(UserDtoRequest request) {
        User model = new User();

        model.setUsrId(request.getUsrId());
        model.setUsrFname(request.getUsrFname());
        model.setUsrEmail(request.getUsrEmail());
        model.setUsrPswd(request.getUsrPswd());

        return model;
    }

    public static UserDtoResponse toResponse(User model) {
        UserDtoResponse response = new UserDtoResponse();

        response.setUsrId(model.getUsrId());
        response.setUsrFname(model.getUsrFname());
        response.setUsrEmail(model.getUsrEmail());
        response.setUsrPswd(model.getUsrPswd());

        return response;
    }

    private UserDtoMapper() {}

}
