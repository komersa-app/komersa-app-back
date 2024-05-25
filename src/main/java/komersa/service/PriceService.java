package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Price;
import komersa.repository.PriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PriceService {
    private final PriceRepository priceRepository;
    private final CarService carService;

    public PriceService(CarService carService, PriceRepository priceRepository) {
        this.carService = carService;
        this.priceRepository = priceRepository;
    }

    public Price create(Price price) {
        log.info("price create: {}", price);
        price.setCar(carService.getById(price.getCar().getId()));
        return priceRepository.save(price);
    }

    public Price getById(Long id) {
        log.info("price get by id: {}", id);
        return priceRepository.findById(id).orElseThrow(()->new EntityNotFoundException("price with id: " + id + " does not exist"));
    }

    public Page<Price> getAll(Pageable pageable) {
        log.info("price get all: {}", pageable);
        return priceRepository.findAll(pageable);
    }

    public Price updateById(Long id, Price price) {
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
