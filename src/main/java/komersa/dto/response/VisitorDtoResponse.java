package komersa.dto.response;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Data
public class VisitorDtoResponse extends UserDtoResponse {
    public VisitorDtoResponse(Long id, String name, String email) {
        super(id, name, email);
    }
}
