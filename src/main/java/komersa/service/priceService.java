package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.price;
import komersa.repository.priceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class priceService {
    private final priceRepository priceRepository;
    private final CarService carService;

    public priceService(CarService carService, priceRepository priceRepository) {
        this.carService = carService;
        this.priceRepository = priceRepository;
    }

    public price create(price price) {
        log.info("price create: {}", price);
        price.setCar(carService.getById(price.getCar().getId()));
        return priceRepository.save(price);
    }

    public price getById(Long id) {
        log.info("price get by id: {}", id);
        return priceRepository.findById(id).orElseThrow(()->new EntityNotFoundException("price with id: " + id + " does not exist"));
    }

    public Page<price> getAll(Pageable pageable) {
        log.info("price get all: {}", pageable);
        return priceRepository.findAll(pageable);
    }

    public price updateById(Long id, price price) {
        getById(id);
        price.setId(id);
        price.setCar(carService.getById(price.getCar().getId()));
        log.info("price update by id: {}", price);
        return priceRepository.save(price);
    }

    public Boolean deleteById(Long id) {
        log.info("price delete by id: {}", id);
        priceRepository.deleteById(id);
        return true;
    }
}
