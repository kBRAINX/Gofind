package org.poo.Gofind.dto.driver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PassagerDTO {
    private long id;
    private String nom;
    private String email;
    private String city;
    private long trajetId;
}
