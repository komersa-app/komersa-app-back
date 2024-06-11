package komersa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price")
@Entity
public class Prices {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "change_datetime")
    private LocalDateTime changeDatetime;

    @OneToOne(mappedBy = "price")
    @ToString.Exclude
    private Car car;
}
