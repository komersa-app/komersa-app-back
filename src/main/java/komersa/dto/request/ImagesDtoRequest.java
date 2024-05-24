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
public class ImagesDtoRequest {

    @NotNull(message = "Url cannot be null")
    @NotBlank(message = "Url cannot be blank")
    private String url;

    @Positive(message = "Car must be a positive number")
    @NotNull(message = "Car cannot be null")
    private Long carId;
}
