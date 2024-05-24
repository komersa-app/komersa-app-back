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
public class UserDtoRequest {

    @NotNull(message = "Usr Id cannot be null")
    @NotBlank(message = "Usr Id cannot be blank")
    private long usrId;

    @NotNull(message = "Usr Fname cannot be null")
    @NotBlank(message = "Usr Fname cannot be blank")
    private String usrFname;

    @NotNull(message = "Usr Email cannot be null")
    @NotBlank(message = "Usr Email cannot be blank")
    private String usrEmail;

    @NotNull(message = "Usr Pswd cannot be null")
    @NotBlank(message = "Usr Pswd cannot be blank")
    private String usrPswd;
}
