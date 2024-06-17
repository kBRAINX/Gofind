package org.poo.Gofind.models;

import jakarta.persistence.*;
import lombok.Data;
import org.poo.Gofind.models.appareils.Device;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(
        nullable = false,
        unique = true
    )
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "users")
    private List<Device> deviceList;
}
