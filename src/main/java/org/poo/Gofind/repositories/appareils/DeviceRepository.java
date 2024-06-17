package org.poo.Gofind.repositories.appareils;

import org.poo.Gofind.models.appareils.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
