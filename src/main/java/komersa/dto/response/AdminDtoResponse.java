package komersa.dto.response;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Data
public class AdminDtoResponse extends UserDtoResponse {
       public AdminDtoResponse(Long id, String name, String email) {
        super(id, name, email);
    }
}
