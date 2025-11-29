package aom.credito.challenge.datasource.repository;

import aom.credito.challenge.datasource.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Long> {

    Optional<Credito> findByNumeroNfse(String numeroNfse);
}
