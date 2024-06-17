package org.poo.Gofind.repositories.immobilier;

import org.poo.Gofind.models.immobilier.Locataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LocataireRepository extends JpaRepository<Locataire, Long> {
}
