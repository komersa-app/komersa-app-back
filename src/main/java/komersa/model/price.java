package komersa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price")
@Entity
public class price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private LocalDateTime changeDateTime;
    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "carId")
    private Car car;
}
