package komersa.service;

import komersa.exception.EntityNotFoundException;
import komersa.model.Visitor;
import komersa.repository.VisitorFilterRepository;
import komersa.repository.VisitorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VisitorService {
    private final VisitorRepository visitorRepository;
    private final VisitorFilterRepository visitorFilterRepository;

    public VisitorService(VisitorRepository visitorRepository, VisitorFilterRepository visitorFilterRepository) {
        this.visitorRepository = visitorRepository;
        this.visitorFilterRepository = visitorFilterRepository;
    }

    public Visitor create(Visitor visitor) {
        log.info("Visitor create: {}", visitor);

        return visitorRepository.save(visitor);
    }

    public Visitor getById(Long id) {
        log.info("Visitor get by id: {}", id);
        return visitorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Visitor with id: " + id + " does not exist"));
    }

    public Page<Visitor> getAll(Pageable pageable) {
        log.info("Visitor get all: {}", pageable);
        return visitorRepository.findAll(pageable);
    }

    public Visitor updateById(Long id, Visitor visitor) {
        getById(id);
        visitor.setId(id);

        log.info("Visitor update by id: {}", visitor);
        return visitorRepository.save(visitor);
    }

    public Boolean deleteById(Long id) {
        log.info("Visitor delete by id: {}", id);
        visitorRepository.deleteById(id);
        return true;
    }

    public Page<Visitor> findByCriteria(Visitor criteria, Pageable pageable) {
        return visitorFilterRepository.findByCriteria(criteria, pageable);
    }
}
