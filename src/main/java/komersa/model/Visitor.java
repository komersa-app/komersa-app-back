package komersa.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("visitor")
@Data
@Entity(name = "visitor")
public class Visitor extends User {
    @OneToMany(mappedBy = "visitor")
    private List<Appointment> appointments;

    public Visitor(Long id, String name, String email, List<Appointment> appointments) {
        super(id, name, email);
        this.appointments = appointments;
    }

    public Visitor(String name, String email) {
        super(null, name, email);
    }
}
