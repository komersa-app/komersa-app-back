package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminDtoResponse {

    private long admId;

    private String admPswd;
}
