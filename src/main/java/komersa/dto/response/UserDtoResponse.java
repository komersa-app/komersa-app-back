package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDtoResponse {

    private long usrId;

    private String usrFname;

    private String usrEmail;

    private String usrPswd;
}
