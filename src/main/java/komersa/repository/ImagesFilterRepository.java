package komersa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import komersa.model.Images;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ImagesFilterRepository {
    private EntityManager entityManager;

    public Page<Images> findByCriteria(Images criteria, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Images> criteriaQuery = criteriaBuilder.createQuery(Images.class);
        Root<Images> root = criteriaQuery.from(Images.class);

        //Predicate predicates = criteriaBuilder.and(criteriaBuilder.equal(root.get("image"), criteria));

        Predicate predicate = criteriaBuilder.conjunction();
        return null;
    }
}
