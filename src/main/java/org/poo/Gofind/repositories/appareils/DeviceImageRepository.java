package org.poo.Gofind.repositories.appareils;

import org.poo.Gofind.models.appareils.DeviceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceImageRepository extends JpaRepository<DeviceImage, String> {
    List<DeviceImage> findByDevice_Imei(String imei);
}
