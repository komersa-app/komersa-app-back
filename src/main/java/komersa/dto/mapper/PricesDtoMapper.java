package komersa.dto.mapper;

import komersa.model.Car;
import komersa.model.Prices;
import komersa.dto.request.PricesDtoRequest;
import komersa.dto.response.PricesDtoResponse;

public class PricesDtoMapper {

    public static Prices toModel(PricesDtoRequest request) {
        Prices model = new Prices();
        model.setAmount(request.getAmount());
        Car car = new Car();
        model.setCar(car);

        return model;
    }

    public static PricesDtoResponse toResponse(Prices model) {
        PricesDtoResponse response = new PricesDtoResponse();
        response.setId(model.getId());
        response.setAmount(model.getAmount());
        response.setChangeDatetime(model.getChangeDatetime());

        return response;
    }

    private PricesDtoMapper() {}

}
