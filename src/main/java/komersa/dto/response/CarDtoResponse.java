package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


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

    private DetailsDtoResponse details;

    private PriceDtoResponse price;
}
