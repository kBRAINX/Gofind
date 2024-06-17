package org.poo.Gofind.repositories.immobilier;

import org.poo.Gofind.models.immobilier.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ProprietaireRepository extends JpaRepository<Proprietaire, Long> {
}
