package org.poo.Gofind.models.driver;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chauffeurs")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String email;
    private String city;
    private String permis;

    @OneToMany(mappedBy = "chauffeur")
    private List<Trajet> trajetlist;
}
