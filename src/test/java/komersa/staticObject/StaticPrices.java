package komersa.staticObject;

import komersa.dto.request.PricesDtoRequest;
import komersa.dto.response.PricesDtoResponse;
import komersa.model.Prices;

import java.time.LocalDateTime;

public class StaticPrices {

    public static final Long ID = 1L;

    public static Prices prices1() {
        Prices model = new Prices();
        model.setId(ID);
        model.setAmount(10D);
        model.setChangeDatetime(LocalDateTime.MIN);
        model.setCar(StaticCar.car1());
        return model;
    }

    public static Prices prices2() {
        Prices model = new Prices();
        model.setId(ID);
        model.setAmount(20D);
        model.setChangeDatetime(LocalDateTime.MIN);
        model.setCar(StaticCar.car2());
        return model;
    }

    public static PricesDtoRequest pricesDtoRequest1() {
        PricesDtoRequest dtoRequest = new PricesDtoRequest();
        dtoRequest.setAmount(10D);
        return dtoRequest;
    }

    public static PricesDtoResponse pricesDtoResponse1() {
        PricesDtoResponse dtoResponse = new PricesDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setAmount(10D);
        dtoResponse.setChangeDatetime(LocalDateTime.MIN);
        return dtoResponse;
    }

    public static PricesDtoResponse pricesDtoResponse2() {
        PricesDtoResponse dtoResponse = new PricesDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setAmount(20D);
        dtoResponse.setChangeDatetime(LocalDateTime.MIN);
        return dtoResponse;
    }
}
