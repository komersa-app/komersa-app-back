package komersa.use.service;

import komersa.model.Car;
import komersa.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class CarServiceTest {
    @Autowired
    CarService carService;

    @Test
    void findAllCarsOk() {
        Page<Car> carPage = carService.getAll(Pageable.unpaged());
        Assertions.assertNotNull(carPage);
        System.err.println(carPage.get().toList().get(1));
    }
}
