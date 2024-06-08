package komersa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import komersa.model.Car;
import komersa.service.ImagesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
@AllArgsConstructor
@Repository
public class CarRepo {
    private EntityManager entityManager;
    private ImagesService imagesService;

    // Spring Data's Pageable parameter
    // JPA Criteria API

    public Page<Car> findByCriteria(Car criteriaCar, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
        Root<Car> root = criteriaQuery.from(Car.class);

        Predicate predicate = criteriaBuilder.conjunction();

        if (criteriaCar.getName() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), criteriaCar.getName()));
        }
        if (criteriaCar.getDescription() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("description"), criteriaCar.getDescription()));
        }
        if (criteriaCar.getColor() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("color"), criteriaCar.getColor()));
        }
        if (criteriaCar.getMotorType() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("motorType"), criteriaCar.getMotorType()));
        }
        if (criteriaCar.getPower() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("power"), criteriaCar.getPower()));
        }
        if (criteriaCar.getStatus() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), criteriaCar.getStatus()));
        }
        if (criteriaCar.getType() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("type"), criteriaCar.getType()));
        }

        criteriaQuery.where(predicate);

            // Apply pagination
        int startPosition = (int) pageable.getOffset();
        int maxResults = pageable.getPageSize();

        TypedQuery<Car> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(startPosition);
        typedQuery.setMaxResults(maxResults);

        List<Car> resultList = typedQuery.getResultList();
        resultList.forEach(car -> car.setImages(imagesService.findByCarId(car.getId())));

        // Create a custom Page object
        return new PageImpl<>(resultList, pageable, resultList.size());
    }
}
