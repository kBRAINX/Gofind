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
@Table(name = "habitations")
public class Habitat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private  long superficie;

    @Column(name = "nombre de pieces")
    private long pieces;

    private String localisation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Operation operation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut status;

    private int etage;

    @ManyToOne
    @JoinColumn(name = "id_proprietaire")
    private Proprietaire proprietaire;

    @Column(name = "montant location")
    private long montant;

    @OneToMany(mappedBy = "habitat")
    private List<Commande> commandes;
}
