package org.poo.Gofind.models.driver;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicule")
public class Car {
    @Id
    @Column(name = "immatriculation")
    private String matricule;

    private String marque;

    @Column(name = "nombre de places")
    private int places = 1;

    @OneToOne
    @JoinColumn(name = "id_chauffeur")
    private Driver chauffeur;
}
