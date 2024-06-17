package org.poo.Gofind.dto.immobilier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HabitatImageDTO {
    private Long imageId;
    private Long habitatId;
    private byte[] imageData;
}
