package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Car;
import komersa.model.Images;
import komersa.repository.ImagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ImagesService {
    private final ImagesRepository imagesRepository;

    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public Images create(Images images) {
        log.info("Images create: {}", images);
        Car car = new Car();
        car.setId(images.getCar().getId());
        images.setCar(car);
        return imagesRepository.save(images);
    }

    public Images getById(Long id) {
        log.info("Images get by id: {}", id);
        return imagesRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Images with id: " + id + " does not exist"));
    }

    public Page<Images> getAll(Pageable pageable) {
        log.info("Images get all: {}", pageable);
        return imagesRepository.findAll(pageable);
    }

    public Images updateById(Long id, Images images) {
        getById(id);
        images.setId(id);
        Car car = new Car();
        car.setId(images.getCar().getId());
        images.setCar(car);
        log.info("Images update by id: {}", images);
        return imagesRepository.save(images);
    }

    public Boolean deleteById(Long id) {
        log.info("Images delete by id: {}", id);
        imagesRepository.deleteById(id);
        return true;
    }

    public List<Images> findByCarId(Long carId) {
        log.info("Images gets by carId: {}", carId);
        return imagesRepository.findByCarId(carId);
    }
}
