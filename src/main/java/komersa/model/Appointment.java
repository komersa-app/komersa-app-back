package komersa.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointment")
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "datetime")
    private LocalDateTime datetime;
    @Column(name = "status")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    //@OneToMany(mappedBy = "appointment_id", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Visitor> visitors;

    //@OneToMany(mappedBy = "appointment_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public enum Status {
        PENDING,
        VALIDATED,
        REJECTED,
        ARCHIVED
    }

}