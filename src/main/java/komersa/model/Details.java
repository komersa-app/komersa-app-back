package komersa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "details")
@Entity
public class Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;

    @OneToMany(mappedBy = "details")
    private List<Car> cars;
}
