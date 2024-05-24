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
public class AdminDtoRequest {

    @NotNull(message = "Adm Id cannot be null")
    @NotBlank(message = "Adm Id cannot be blank")
    private long admId;

    @NotNull(message = "Adm Pswd cannot be null")
    @NotBlank(message = "Adm Pswd cannot be blank")
    private String admPswd;
}
