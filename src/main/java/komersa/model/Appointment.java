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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "datetime")
    private LocalDateTime datetime;
    @Column(name = "status")
    private String status;

    @ManyToOne
    private Car car;
    @ManyToOne
    private User admin;
    @ManyToOne
    private User visitor;
    @ManyToOne
    private Appointment appointment;
}
