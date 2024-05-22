package komersa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "details")
@Entity
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID detailsId;
    private String brand;
    private String model;
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

}
