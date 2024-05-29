package komersa.staticObject;

import komersa.dto.request.priceDtoRequest;
import komersa.dto.response.priceDtoResponse;
import komersa.model.price;

import java.time.LocalDateTime;

public class Staticprice {

    public static final Long ID = 1L;

    public static price price1() {
        price model = new price();
        model.setId(ID);
        model.setAmount(10D);
        model.setChangeDatetime(LocalDateTime.MIN);
        model.setCar(StaticCar.car1());
        return model;
    }

    public static price price2() {
        price model = new price();
        model.setId(ID);
        model.setAmount(20D);
        model.setChangeDatetime(LocalDateTime.MIN);
        model.setCar(StaticCar.car2());
        return model;
    }

    public static priceDtoRequest priceDtoRequest1() {
        priceDtoRequest dtoRequest = new priceDtoRequest();
        dtoRequest.setAmount(10D);
        dtoRequest.setCarId(1L);
        return dtoRequest;
    }

    public static priceDtoResponse priceDtoResponse1() {
        priceDtoResponse dtoResponse = new priceDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setAmount(10D);
        dtoResponse.setChangeDatetime(LocalDateTime.MIN);
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }

    public static priceDtoResponse priceDtoResponse2() {
        priceDtoResponse dtoResponse = new priceDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setAmount(20D);
        dtoResponse.setChangeDatetime(LocalDateTime.MIN);
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }
}
