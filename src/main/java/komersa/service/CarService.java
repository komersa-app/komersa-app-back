package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Car;
import komersa.repository.CarRepo;
import komersa.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CarService {
    private final CarRepository carRepository;
    private final DetailsService detailsService;
    private final PricesService pricesService;
    private final ImagesService imagesService;
    private final CarRepo carRepo;

    public CarService(PricesService pricesService, DetailsService detailsService, CarRepository carRepository, ImagesService imagesService, CarRepo carRepo) {
        this.pricesService = pricesService;
        this.detailsService = detailsService;
        this.carRepository = carRepository;
        this.imagesService = imagesService;
        this.carRepo = carRepo;
    }

    public Car create(Car car) {
        log.info("Car create: {}", car);
        car.setDetails(detailsService.getById(car.getDetails().getId()));
        car.setPrice(pricesService.getById(car.getPrice().getId()));
        return carRepository.save(car);
    }

    public Car getById(Long id) {
        log.info("Car get by id: {}", id);
        return carRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Car with id: " + id + " does not exist"));
    }

    public Page<Car> getAll(Pageable pageable) {
        log.info("Car get all: {}", pageable);
        Page<Car> cars = carRepository.findAll(pageable);
        cars.forEach(e -> e.setImages(imagesService.findByCarId(e.getId())));
        return cars;
    }

    public Car updateById(Long id, Car car) {
        getById(id);
        car.setId(id);
        car.setDetails(detailsService.getById(car.getDetails().getId()));
        car.setPrice(pricesService.getById(car.getPrice().getId()));
        log.info("Car update by id: {}", car);
        return carRepository.save(car);
    }

    public Boolean deleteById(Long id) {
        log.info("Car delete by id: {}", id);
        carRepository.deleteById(id);
        return true;
    }

    public Page<Car> findByCriteria(Car criteriaCar, Pageable pageable) {
        return carRepo.findByCriteria(criteriaCar, pageable);
    }
}
