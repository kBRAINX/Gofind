package org.poo.Gofind.dto.immobilier;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HabitatDTO {
    private long id;
    private long superficie;
    private long pieces;
    private String localisation;
    private String operation;
    private String status;
    private int etage;
    private long proprietaireId;
    private long montant;
    private List<CommandeDTO> commandes;
}
