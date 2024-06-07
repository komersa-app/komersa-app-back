package komersa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import komersa.model.Car;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CarRepo {
    private final EntityManager entityManager;

    public CarRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Car> findByCriteria(Car criteriaCar) {
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

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
