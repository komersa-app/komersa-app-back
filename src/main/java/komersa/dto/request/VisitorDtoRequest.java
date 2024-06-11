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
        public VisitorDtoRequest(String name, String email) {
        super(name, email);
    }
}
