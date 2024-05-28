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
public class AppointmentDtoRequest {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Positive(message = "Car must be a positive number")
    @NotNull(message = "Car cannot be null")
    private Long carId;

    @Positive(message = "Admin must be a positive number")
    @NotNull(message = "Admin cannot be null")
    private Long adminId;

    @Positive(message = "Visitor must be a positive number")
    @NotNull(message = "Visitor cannot be null")
    private Long visitorId;

    @Positive(message = "Appointment must be a positive number")
    @NotNull(message = "Appointment cannot be null")
    private Long appointmentId;
}
