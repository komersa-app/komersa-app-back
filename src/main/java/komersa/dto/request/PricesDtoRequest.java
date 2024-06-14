package komersa.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PricesDtoRequest {

    @Positive(message = "Amount must be a positive number")
    @NotNull(message = "Amount cannot be null")
    private Double amount;

    @JsonFormat(pattern = "YYYY-MM-DD'T'HH-MM-SS'Z'")
    @NotNull(message = "Datetime cannot be null")
    //@NotBlank(message = "Datetime cannot be blank")
    private LocalDateTime changeDatetime;
}
