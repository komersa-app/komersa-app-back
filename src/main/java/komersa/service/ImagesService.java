package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Images;
import komersa.repository.ImagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImagesService {
    private final ImagesRepository imagesRepository;
    private final CarService carService;

    public ImagesService(CarService carService, ImagesRepository imagesRepository) {
        this.carService = carService;
        this.imagesRepository = imagesRepository;
    }

    public Images create(Images images) {
        log.info("Images create: {}", images);
        images.setCar(carService.getById(images.getCar().getId()));
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
        images.setCar(carService.getById(images.getCar().getId()));
        log.info("Images update by id: {}", images);
        return imagesRepository.save(images);
    }

    public Boolean deleteById(Long id) {
        log.info("Images delete by id: {}", id);
        imagesRepository.deleteById(id);
        return true;
    }
}
