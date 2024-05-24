package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitorDtoResponse {

    private Long id;

    private String firstname;

    private String email;

    private String tel;

    private String message;

    private AppointmentDtoResponse appointment;
}
