package komersa.dto.response;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VisitorDtoResponse extends UserDtoResponse {
    private Long id;
    private String message;
}
