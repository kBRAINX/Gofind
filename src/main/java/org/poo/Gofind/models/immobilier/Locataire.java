package org.poo.Gofind.models.immobilier;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "locataires")
public class Locataire{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String email;
    private String city;

    @Column(name = "telephone")
    private String number;

    @OneToMany(mappedBy = "locataire")
    private List<Commande> commandes;
}
