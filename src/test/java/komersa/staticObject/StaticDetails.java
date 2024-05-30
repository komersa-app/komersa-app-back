package komersa.staticObject;

import komersa.dto.request.DetailsDtoRequest;
import komersa.dto.response.DetailsDtoResponse;
import komersa.model.Details;


public class StaticDetails {

    public static final Long ID = 1L;

    public static Details details1() {
        Details model = new Details();
        model.setId(ID);
        model.setBrand("brand");
        model.setModel("model");
        return model;
    }

    public static Details details2() {
        Details model = new Details();
        model.setId(ID);
        model.setBrand("brand");
        model.setModel("model");
        return model;
    }

    public static DetailsDtoRequest detailsDtoRequest1() {
        DetailsDtoRequest dtoRequest = new DetailsDtoRequest();
        dtoRequest.setBrand("brand");
        dtoRequest.setModel("model");
        return dtoRequest;
    }

    public static DetailsDtoResponse detailsDtoResponse1() {
        DetailsDtoResponse dtoResponse = new DetailsDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setBrand("brand");
        dtoResponse.setModel("model");
        return dtoResponse;
    }

    public static DetailsDtoResponse detailsDtoResponse2() {
        DetailsDtoResponse dtoResponse = new DetailsDtoResponse();
        dtoResponse.setId(ID);
        dtoResponse.setBrand("brand");
        dtoResponse.setModel("model");
        return dtoResponse;
    }
}
