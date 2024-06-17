package org.poo.Gofind.repositories.immobilier;

import org.poo.Gofind.models.immobilier.HabitatImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitatImageRepository extends CrudRepository<HabitatImage, Long> {
    Iterable<HabitatImage> findByHabitatId(Long habitatId);
}
