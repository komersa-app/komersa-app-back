package komersa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car")
@Entity
public class Car{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "color")
    private String color;
    @Column(name = "motor_type")
    private String motorType;
    @Column(name = "power")
    private String power;
    @Column(name = "status")
    private String status;
    @Column(name = "type")
    private String type;
    @Column(name = "model")
    private String model;

    @OneToMany(mappedBy = "car")
    private List<Images> images;

    @OneToMany(mappedBy = "car")
    @ToString.Exclude
    private List<Appointment> appointments;

    @ManyToOne
    private Brand brand;

    @OneToOne
    private Prices price;

    public  Car(String name, String description, String color, String motorType, String power, String status, String type, String brand, String model) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.motorType = motorType;
        this.power = power;
        this.status = status;
        this.type = type;
        this.model = model;
        this.brand = new Brand(brand);
    }
}
