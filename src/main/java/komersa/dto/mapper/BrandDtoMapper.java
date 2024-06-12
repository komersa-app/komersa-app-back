package komersa.dto.mapper;

import komersa.dto.request.BrandDtoRequest;
import komersa.dto.response.BrandDtoResponse;
import komersa.model.Brand;

public class BrandDtoMapper {

    public static Brand toModel(BrandDtoRequest request) {
        Brand model = new Brand();

        model.setName(request.getName());
        model.setLogo(request.getLogo());

        return model;
    }

    public static BrandDtoResponse toResponse(Brand model) {
        BrandDtoResponse response = new BrandDtoResponse();

        response.setId(model.getId());
        response.setName(model.getName());
        response.setLogo(model.getLogo());

        return response;
    }

    private BrandDtoMapper() {}

}
