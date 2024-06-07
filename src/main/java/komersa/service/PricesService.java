package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Prices;
import komersa.repository.PricesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PricesService {
    private final PricesRepository pricesRepository;

    public PricesService(PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    public Prices create(Prices prices) {
        log.info("Prices create: {}", prices);
        return pricesRepository.save(prices);
    }

    public Prices getById(Long id) {
        log.info("Prices get by id: {}", id);
        return pricesRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Prices with id: " + id + " does not exist"));
    }

    public Page<Prices> getAll(Pageable pageable) {
        log.info("Prices get all: {}", pageable);
        return pricesRepository.findAll(pageable);
    }

    public Prices updateById(Long id, Prices prices) {
        getById(id);
        prices.setId(id);
        log.info("Prices update by id: {}", prices);
        return pricesRepository.save(prices);
    }

    public Boolean deleteById(Long id) {
        log.info("Prices delete by id: {}", id);
        pricesRepository.deleteById(id);
        return true;
    }
}
