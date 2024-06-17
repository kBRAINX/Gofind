package org.poo.Gofind.dto.driver;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DriverDTO {
    private long id;
    private String nom;
    private String email;
    private String city;
    private String permis;
}
