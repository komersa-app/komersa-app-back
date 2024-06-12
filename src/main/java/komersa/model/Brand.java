package komersa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "logo")
    private String logo;

    @OneToMany(mappedBy = "brand")
    @ToString.Exclude
    private List<Car> cars;

    public Brand(String name) {
        this.name = name;
    }
}
