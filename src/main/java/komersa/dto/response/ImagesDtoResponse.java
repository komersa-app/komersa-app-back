package komersa.dto.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImagesDtoResponse {

    private Long id;

    private String url;

    private CarDtoResponse car;
}
