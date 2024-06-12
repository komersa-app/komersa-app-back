package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandDtoResponse {

    private Long id;

    private String name;

    private String logo;
}
