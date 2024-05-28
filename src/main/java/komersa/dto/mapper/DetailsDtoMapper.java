package komersa.dto.mapper;

import komersa.model.Details;
import komersa.dto.request.DetailsDtoRequest;
import komersa.dto.response.DetailsDtoResponse;

public class DetailsDtoMapper {

    public static Details toModel(DetailsDtoRequest request) {
        Details model = new Details();

        model.setBrand(request.getBrand());
        model.setModel(request.getModel());

        return model;
    }

    public static DetailsDtoResponse toResponse(Details model) {
        DetailsDtoResponse response = new DetailsDtoResponse();

        response.setId(model.getId());
        response.setBrand(model.getBrand());
        response.setModel(model.getModel());

        return response;
    }

    private DetailsDtoMapper() {}

}
