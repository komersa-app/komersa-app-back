package komersa.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

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
    private String status; // pending, validated, rejected, archived

    @ManyToOne
    @ToString.Exclude
    private Car car;

    /*
    @ManyToOne
    @ToString.Exclude
    private Admin admin;

     */

    @ManyToOne
    @ToString.Exclude
    private Visitor visitor;
}
