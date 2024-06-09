package komersa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import komersa.model.Car;
import komersa.model.Visitor;
import komersa.service.VisitorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;

@AllArgsConstructor
@Repository
public class VisitorFilterRepository {
    private EntityManager entityManager;

    public Page<Visitor> findByCriteria(Visitor criteria, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Visitor> criteriaQuery = criteriaBuilder.createQuery(Visitor.class);
        Root<Visitor> root = criteriaQuery.from(Visitor.class);

        Predicate predicate = criteriaBuilder.conjunction();

        if (criteria.getName() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), criteria.getName()));
        }
        if (criteria.getEmail() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("email"), criteria.getEmail()));
        }

        criteriaQuery.where(predicate);

        // Apply pagination
        int startPosition = (int) pageable.getOffset();
        int maxResults = pageable.getPageSize();

        TypedQuery<Visitor> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(startPosition);
        typedQuery.setMaxResults(maxResults);

        List<Visitor> resultList = typedQuery.getResultList();

        // Create a custom Page object
        return new PageImpl<>(resultList, pageable, resultList.size());
    }
}
