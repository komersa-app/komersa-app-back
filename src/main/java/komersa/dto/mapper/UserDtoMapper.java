package komersa.dto.mapper;

import komersa.model.User;
import komersa.dto.request.UserDtoRequest;
import komersa.dto.response.UserDtoResponse;

public class UserDtoMapper {

    public static User toModel(UserDtoRequest request) {
        User model = new User();

        model.setName(request.getName());
        model.setEmail(request.getEmail());

        return model;
    }

    public static UserDtoResponse toResponse(User model) {
        UserDtoResponse response = new UserDtoResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setEmail(model.getEmail());

        return response;
    }

    private UserDtoMapper() {}

}
