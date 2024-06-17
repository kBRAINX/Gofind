package org.poo.Gofind.dto.driver;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class TrajetDTO {
    private long id;
    private String start;
    private String end;
    private String H_Start;
    private String createdAt;
    private long chauffeurId;
    private List<PassagerDTO> passagers;
}
