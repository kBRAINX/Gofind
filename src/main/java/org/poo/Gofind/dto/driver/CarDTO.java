package org.poo.Gofind.dto.driver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDTO {
    private String matricule;
    private String marque;
    private int places;
    private long chauffeurId;
}
