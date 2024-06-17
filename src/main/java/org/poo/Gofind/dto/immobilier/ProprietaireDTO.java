package org.poo.Gofind.dto.immobilier;

import lombok.Builder;
import lombok.Data;
import org.poo.Gofind.models.Man;

import javax.crypto.Mac;
import java.util.List;

@Data
@Builder
public class ProprietaireDTO {
    private long id;
    private String nom;
    private String email;
    private String city;
    private String number;
    private String address;
    private List<HabitatDTO> habitats;
}
