package br.com.totvs.repository;

import br.com.totvs.domain.Dependente;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dependente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Long> {
}
