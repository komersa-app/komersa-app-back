package komersa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import komersa.dto.response.CarDtoResponse;
import komersa.dto.response.VisitorDtoResponse;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDtoRequest {
    // TODO: visitor can get admin list without sensitive info
    // TODO: update api & dto
    
    @JsonFormat(pattern = "YYYY-MM-DD'T'HH-MM-SS'Z'")
    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private String name;
    private LocalDateTime datetime;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Positive(message = "Car must be a positive number")
    @NotNull(message = "Car cannot be null")
    private String message;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private Long carId;

    /*
    @Positive(message = "Admin must be a positive number")
    @NotNull(message = "Admin cannot be null")
    private Long adminId;

     */


    @Positive(message = "Visitor must be a positive number")
    @NotNull(message = "Visitor cannot be null")
    private Long visitorId;

    public AppointmentDtoRequest(String name, LocalDateTime datetime, String status, String message, CarDtoResponse car, VisitorDtoResponse visitor) {
	    this.name = name;
        this.datetime = datetime;
        this.status = status;
        this.message = message;
        this.carId = car.getId();
        this.visitorId = visitor.getId();
    }

}
