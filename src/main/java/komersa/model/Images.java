package komersa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
@Entity
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID imageId;
    private String url;
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
