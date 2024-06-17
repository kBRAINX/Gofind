package org.poo.Gofind.dto.appareils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private String imei;
    private String marque;
    private String model;
    private long userId;
    private long categorieId;
}
