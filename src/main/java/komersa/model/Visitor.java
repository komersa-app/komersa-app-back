package komersa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("visitor")
@Data
@Entity(name = "visitor")
public class Visitor extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "message")
    private String message;

    @OneToMany(mappedBy = "visitor")
    private List<Appointment> appointments;
}
