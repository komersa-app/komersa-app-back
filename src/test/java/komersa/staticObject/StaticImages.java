package komersa.staticObject;

import komersa.dto.request.ImagesDtoRequest;
import komersa.dto.response.ImagesDtoResponse;
import komersa.model.Images;


public class StaticImages {

    public static final Long ID = 1L;

    public static Images images1() {
        Images model = new Images();
        model.setId(ID);
        model.setUrl("url");
        model.setCar(StaticCar.car1());
        return model;
    }

    public static Images images2() {
        Images model = new Images();
        model.setId(ID);
        model.setUrl("url");
        model.setCar(StaticCar.car2());
        return model;
    }

    public static ImagesDtoRequest imagesDtoRequest1() {
        ImagesDtoRequest dtoRequest = new ImagesDtoRequest();
        dtoRequest.setUrl("url");
        dtoRequest.setCarId(1L);
        return dtoRequest;
    }

    public static ImagesDtoResponse imagesDtoResponse1() {
        ImagesDtoResponse dtoResponse = new ImagesDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setUrl("url");
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }

    public static ImagesDtoResponse imagesDtoResponse2() {
        ImagesDtoResponse dtoResponse = new ImagesDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setUrl("url");
        dtoResponse.setCar(StaticCar.carDtoResponse1());
        return dtoResponse;
    }
}
