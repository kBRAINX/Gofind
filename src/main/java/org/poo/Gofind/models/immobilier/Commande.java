package org.poo.Gofind.models.immobilier;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_locataire")
    private Locataire locataire;

    @ManyToOne
    @JoinColumn(name = "id_habitat")
    private Habitat habitat;

    private Date createdAt;
}
