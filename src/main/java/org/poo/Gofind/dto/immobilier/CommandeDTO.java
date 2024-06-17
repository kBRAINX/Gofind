package org.poo.Gofind.dto.immobilier;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommandeDTO {
    private long id;
    private long locataireId;
    private long habitatId;
    private Date createdAt;
}
