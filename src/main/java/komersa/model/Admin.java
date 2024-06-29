package komersa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "admin")
@DiscriminatorValue("admin")
@NoArgsConstructor
@Data
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "admin")
    private List<Appointment> appointments;

    public Admin(Long id, String name, String email, Long id1, String password, List<Appointment> appointments) {
        super(id, name, email);
        this.id = id1;
        this.password = password;
        this.appointments = appointments;
    }
}
