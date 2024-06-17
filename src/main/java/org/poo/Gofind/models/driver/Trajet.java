package org.poo.Gofind.models.driver;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trajets")
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "depart")
    private  String start;

    @Column(name = "arrivee")
    private String end;

    @Column(name = "heure de depart")
    private Timestamp H_Start;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "id_chauffeur")
    private Driver chauffeur;

    @OneToMany(mappedBy = "trajet")
    private List<Passager> passagers;
}
