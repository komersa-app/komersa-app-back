package komersa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Data
public class VisitorDtoRequest extends UserDtoRequest {
    @NotNull(message = "Message cannot be null")
    @NotBlank(message = "Message cannot be blank")
    private String message;

    public VisitorDtoRequest(String name, String email, String message) {
        super(name, email);
        this.message = message;
    }
}
