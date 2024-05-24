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
public class VisitorDtoRequest {

    @NotNull(message = "Firstname cannot be null")
    @NotBlank(message = "Firstname cannot be blank")
    private String firstname;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "Tel cannot be null")
    @NotBlank(message = "Tel cannot be blank")
    private String tel;

    @NotNull(message = "Message cannot be null")
    @NotBlank(message = "Message cannot be blank")
    private String message;

    @Positive(message = "Appointment must be a positive number")
    @NotNull(message = "Appointment cannot be null")
    private Long appointmentId;
}
