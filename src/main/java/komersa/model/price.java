package komersa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "price")
@Entity
public class price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "change_datetime")
    private LocalDateTime changeDatetime;
    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "carId")
    private Car car;
}
