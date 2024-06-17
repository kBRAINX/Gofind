package org.poo.Gofind.dto.immobilier;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LocataireDTO {
    private long id;
    private String nom;
    private String email;
    private String city;
    private String number;
    private List<CommandeDTO> commandes;
}
