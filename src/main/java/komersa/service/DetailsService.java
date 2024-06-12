package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Details;
import komersa.repository.DetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DetailsService {
    private final DetailsRepository detailsRepository;

    public DetailsService(DetailsRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    public Details create(Details details) {
        log.info("Details create: {}", details);

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

    public List<String> getAllModels(Pageable pageable) {
        log.info("Models get all: {}", pageable);
        List<String> modelList = new ArrayList<>();

        for (Details details : detailsRepository.findAll(pageable)) {
            String model = details.getModel();

            if (!modelList.contains(model)) {
                modelList.add(model);
            }
        }

        return modelList;
    }

    public List<String> getAllBrands(Pageable pageable) {
        log.info("Brands get all: {}", pageable);
        List<String> brandList = new ArrayList<>();

        for (Details details : detailsRepository.findAll(pageable)) {
            String brand = details.getBrand();

            if (!brandList.contains(brand)) {
                brandList.add(details.getBrand());
            }
        }

        return brandList;
    }

    public Details updateById(Long id, Details details) {
        getById(id);
        details.setId(id);

        log.info("Details update by id: {}", details);
        return detailsRepository.save(details);
    }

    public Boolean deleteById(Long id) {
        log.info("Details delete by id: {}", id);
        detailsRepository.deleteById(id);
        return true;
    }
}
