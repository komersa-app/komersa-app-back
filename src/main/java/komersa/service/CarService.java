package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Brand;
import komersa.model.Car;
import komersa.repository.CarFilterRepository;
import komersa.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CarService {
    private final CarRepository carRepository;
    private final BrandService brandService;
    private final PricesService pricesService;
    private final ImagesService imagesService;
    private final CarFilterRepository carFilterRepository;

    public CarService(PricesService pricesService, BrandService brandService, CarRepository carRepository, ImagesService imagesService, CarFilterRepository carFilterRepository) {
        this.pricesService = pricesService;
        this.brandService = brandService;
        this.carRepository = carRepository;
        this.imagesService = imagesService;
        this.carFilterRepository = carFilterRepository;
    }

    public Car create(Car car) {
        log.info("Car create: {}", car);
        car.setBrand(brandService.getById(car.getBrand().getId()));
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
        car.setBrand(brandService.getById(car.getBrand().getId()));
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
        return carFilterRepository.findByCriteria(criteriaCar, pageable);
    }

    public List<String> getAllMotorType(Pageable pageable) {
        log.info("Motor type get all: {}", pageable);
        List<String> motorTypeList = new ArrayList<>();
        for (Car car: carRepository.findAll(pageable)) {
            String motorType = car.getMotorType();
            if (!motorTypeList.contains(motorType)) {
                motorTypeList.add(motorType);
            }
        }

        return motorTypeList;
    }

    public List<String> getAllType(Pageable pageable) {
        log.info("Type get all: {}", pageable);
        List<String> typeList = new ArrayList<>();
        for (Car car: carRepository.findAll(pageable)) {
            String type = car.getType();
            if (!typeList.contains(type)) {
                typeList.add(type);
            }
        }

        return typeList;
    }

    public List<String> getAllColor(Pageable pageable) {
        log.info("Color get all: {}", pageable);
        List<String> colorList = new ArrayList<>();
        for (Car car: carRepository.findAll(pageable)) {
            String color = car.getColor();
            if (!colorList.contains(color)) {
                colorList.add(color);
            }
        }

        return colorList;
    }

    public List<String> getAllModels(Pageable pageable) {
        log.info("Models get all: {}", pageable);
        List<String> modelList = new ArrayList<>();

        for (Car car : carRepository.findAll(pageable)) {
            String model = car.getModel();

            if (!modelList.contains(model)) {
                modelList.add(model);
            }
        }

        return modelList;
    }
}
