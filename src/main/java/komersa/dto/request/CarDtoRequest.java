package komersa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "Details must be a positive number")
    @NotNull(message = "Details cannot be null")
    private Long detailsId;

    @Positive(message = "Price must be a positive number")
    @NotNull(message = "Price cannot be null")
    private Long priceId;
}
