package komersa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMIN")
@Data
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "ADMIN")
    List<Appointment> appointments;
}
