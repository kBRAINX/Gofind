package org.poo.Gofind.dto.appareils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceImageDTO {
    private String imageId;
    private String deviceImei;
    private byte[] imageData;
}
