package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Car;
import komersa.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car create(Car car) {
        log.info("Car create: {}", car);

        return carRepository.save(car);
    }

    public Car getById(Long id) {
        log.info("Car get by id: {}", id);
        return carRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Car with id: " + id + " does not exist"));
    }

    public Page<Car> getAll(Pageable pageable) {
        log.info("Car get all: {}", pageable);
        return carRepository.findAll(pageable);
    }

    public Car updateById(Long id, Car car) {
        getById(id);
        car.setId(id);

        log.info("Car update by id: {}", car);
        return carRepository.save(car);
    }

    public Boolean deleteById(Long id) {
        log.info("Car delete by id: {}", id);
        carRepository.deleteById(id);
        return true;
    }
}
