package komersa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import komersa.dto.response.BrandDtoResponse;
import komersa.dto.response.PricesDtoResponse;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDtoRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Color cannot be null")
    @NotBlank(message = "Color cannot be blank")
    private String color;

    @NotNull(message = "Motor Type cannot be null")
    @NotBlank(message = "Motor Type cannot be blank")
    private String motorType;

    @NotNull(message = "Power cannot be null")
    @NotBlank(message = "Power cannot be blank")
    private String power;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Type cannot be null")
    @NotBlank(message = "Type cannot be blank")
    private String type;

    @NotNull(message = "Model cannot be null")
    @NotBlank(message = "Model cannot be blank")
    private String model;

    @Positive(message = "Brand must be a positive number")
    @NotNull(message = "Brand cannot be null")
    private Long brandId;

    @Positive(message = "Price must be a positive number")
    @NotNull(message = "Price cannot be null")
    private Long priceId;

    public CarDtoRequest(String name, String description, String color, String motorType, String power, String status, String type, String model, BrandDtoResponse brand, PricesDtoResponse price) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.motorType = motorType;
        this.power = power;
        this.status = status;
        this.type = type;
        this.model = model;
        this.brandId = brand.getId();
        this.priceId = price.getId();
    }
}
