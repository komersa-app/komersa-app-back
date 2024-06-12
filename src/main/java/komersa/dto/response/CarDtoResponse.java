package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDtoResponse {

    private Long id;

    private String name;

    private String description;

    private String color;

    private String motorType;

    private String power;

    private String status;

    private String type;
    private String model;

    private BrandDtoResponse brand;

    private PricesDtoResponse price;

    private List<ImagesDtoResponse> images;
}
