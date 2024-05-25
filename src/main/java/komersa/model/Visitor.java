package komersa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("VISITOR")
@Data
@Entity
public class Visitor extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "message")
    private String message;

    @OneToMany(mappedBy = "VISITOR")
    private List<Appointment> appointments;
}
