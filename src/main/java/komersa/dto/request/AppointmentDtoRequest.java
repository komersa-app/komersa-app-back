package komersa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import komersa.dto.response.CarDtoResponse;
import komersa.dto.response.VisitorDtoResponse;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDtoRequest {
    //@NotNull(message = "Name cannot be null")
    //@NotBlank(message = "Name cannot be blank")
    private String name;
    
    //@JsonFormat(pattern = "YYYY-MM-DD'T'HH-MM-SS'Z'")
    @NotNull(message = "Datetime cannot be null")
    //@NotBlank(message = "Datetime cannot be blank")
	//@JsonFormat(pattern = "YYYY-MM-DD'T'HH-MM-SS'Z'")
    private LocalDateTime datetime;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private String status;

	@NotBlank(message = "Message cannot be blank")
    @NotNull(message = "Message cannot be null")
    private String message;

	@Positive(message = "CarId must be a positive number")
    @NotNull(message = "CarId cannot be null")
    private Long carId;

    /*
    @Positive(message = "Admin must be a positive number")
    @NotNull(message = "Admin cannot be null")
    private Long adminId;

     */


    @Positive(message = "VisitorId must be a positive number")
    @NotNull(message = "VisitorId cannot be null")
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
