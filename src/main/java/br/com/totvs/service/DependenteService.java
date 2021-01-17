package br.com.totvs.service;

import br.com.totvs.domain.Dependente;
import br.com.totvs.repository.DependenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Dependente}.
 */
@Service
@Transactional
public class DependenteService {

    private final Logger log = LoggerFactory.getLogger(DependenteService.class);

    private final DependenteRepository dependenteRepository;

    public DependenteService(DependenteRepository dependenteRepository) {
        this.dependenteRepository = dependenteRepository;
    }

    /**
     * Save a dependente.
     *
     * @param dependente the entity to save.
     * @return the persisted entity.
     */
    public Dependente save(Dependente dependente) {
        log.debug("Request to save Dependente : {}", dependente);
        return dependenteRepository.save(dependente);
    }

    /**
     * Get all the dependentes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Dependente> findAll(Pageable pageable) {
        log.debug("Request to get all Dependentes");
        return dependenteRepository.findAll(pageable);
    }


    /**
     * Get one dependente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Dependente> findOne(Long id) {
        log.debug("Request to get Dependente : {}", id);
        return dependenteRepository.findById(id);
    }

    /**
     * Delete the dependente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dependente : {}", id);
        dependenteRepository.deleteById(id);
    }
}
