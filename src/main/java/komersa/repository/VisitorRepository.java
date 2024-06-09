package komersa.repository;

import komersa.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Visitor findByEmail(String email);
}
