package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Brand;
import komersa.repository.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public Brand create(Brand brand) {
        log.info("Brand create: {}", brand);

        return brandRepository.save(brand);
    }

    public Brand getById(Long id) {
        log.info("Brand get by id: {}", id);
        return brandRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Brand with id: " + id + " does not exist"));
    }

    public Page<Brand> getAll(Pageable pageable) {
        log.info("Brand get all: {}", pageable);
        return brandRepository.findAll(pageable);
    }

    public Brand updateById(Long id, Brand brand) {
        getById(id);
        brand.setId(id);

        log.info("Brand update by id: {}", brand);
        return brandRepository.save(brand);
    }

    public Boolean deleteById(Long id) {
        log.info("Brand delete by id: {}", id);
        brandRepository.deleteById(id);
        return true;
    }
}
