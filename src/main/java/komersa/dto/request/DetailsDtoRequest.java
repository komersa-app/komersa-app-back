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
public class DetailsDtoRequest {

    @NotNull(message = "Brand cannot be null")
    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @NotNull(message = "Model cannot be null")
    @NotBlank(message = "Model cannot be blank")
    private String model;
}
