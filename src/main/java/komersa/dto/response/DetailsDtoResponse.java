package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class DetailsDtoResponse {

    private Long id;

    private String brand;

    private String model;
}
