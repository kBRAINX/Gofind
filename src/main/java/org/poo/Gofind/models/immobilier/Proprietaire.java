package org.poo.Gofind.models.immobilier;

import jakarta.persistence.*;
import lombok.*;
import org.poo.Gofind.models.Man;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proprietaires")
public class Proprietaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nom;
    private String email;
    private String city;

    @Column(name = "telephone")
    private String number;

    private String address;

    @OneToMany(mappedBy = "proprietaire")
    private List<Habitat> habitatList;
}
