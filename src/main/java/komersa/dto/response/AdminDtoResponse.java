package komersa.dto.response;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Data
public class AdminDtoResponse extends UserDtoResponse {
    private Long id;
    private String password;

    public AdminDtoResponse(Long id, String name, String email, Long id1, String password) {
        super(id, name, email);
        this.id = id1;
        this.password = password;
    }
}
