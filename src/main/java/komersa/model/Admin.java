package komersa.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "adm_id")
    private long admId;
    @Column(name = "adm_pswd")
    private String admPswd;
}
