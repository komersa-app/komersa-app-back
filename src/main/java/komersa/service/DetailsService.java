package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Details;
import komersa.repository.DetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DetailsService {
    private final DetailsRepository detailsRepository;
    private final CarService carService;

    public DetailsService(CarService carService, DetailsRepository detailsRepository) {
        this.carService = carService;
        this.detailsRepository = detailsRepository;
    }

    public Details create(Details details) {
        log.info("Details create: {}", details);
        details.setCar(carService.getById(details.getCar().getId()));
        return detailsRepository.save(details);
    }

    public Details getById(Long id) {
        log.info("Details get by id: {}", id);
        return detailsRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Details with id: " + id + " does not exist"));
    }

    public Page<Details> getAll(Pageable pageable) {
        log.info("Details get all: {}", pageable);
        return detailsRepository.findAll(pageable);
    }

    public Details updateById(Long id, Details details) {
        getById(id);
        details.setId(id);
        details.setCar(carService.getById(details.getCar().getId()));
        log.info("Details update by id: {}", details);
        return detailsRepository.save(details);
    }

    public Boolean deleteById(Long id) {
        log.info("Details delete by id: {}", id);
        detailsRepository.deleteById(id);
        return true;
    }
}
