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
public class priceDtoRequest {

    @Positive(message = "Amount must be a positive number")
    @NotNull(message = "Amount cannot be null")
    private Double amount;

    @Positive(message = "Car must be a positive number")
    @NotNull(message = "Car cannot be null")
    private Long carId;
}
