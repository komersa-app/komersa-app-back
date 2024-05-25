package komersa.staticObject;

import komersa.dto.request.PriceDtoRequest;
import komersa.dto.response.PriceDtoResponse;
import komersa.model.price;

import java.time.LocalDateTime;

public class StaticPrice {

    public static final Long ID = 1L;

    public static price price1() {
        price model = new price();
        model.setId(ID);
        model.setChangeDatetime(LocalDateTime.MIN);
        model.setCar(StaticCar.car1());
        return model;
    }

    public static price price2() {
        price model = new price();
        model.setId(ID);
        model.setChangeDatetime(LocalDateTime.MIN);
        model.setCar(StaticCar.car2());
        return model;
    }

    public static PriceDtoRequest priceDtoRequest1() {
        PriceDtoRequest dtoRequest = new PriceDtoRequest();
        dtoRequest.setCarId(1L);
        return dtoRequest;
    }

    public static PriceDtoResponse priceDtoResponse1() {
        PriceDtoResponse dtoResponse = new PriceDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setChangeDatetime(LocalDateTime.MIN);
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }

    public static PriceDtoResponse priceDtoResponse2() {
        PriceDtoResponse dtoResponse = new PriceDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setChangeDatetime(LocalDateTime.MIN);
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }
}
