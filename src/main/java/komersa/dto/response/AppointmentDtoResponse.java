package komersa.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import komersa.model.Appointment;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDtoResponse {

    private Long id;

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")

    private LocalDateTime datetime;

    private Appointment.Status status;

    private CarDtoResponse car;
}
