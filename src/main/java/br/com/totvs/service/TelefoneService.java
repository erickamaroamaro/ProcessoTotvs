package br.com.totvs.service;

import br.com.totvs.domain.Telefone;
import br.com.totvs.repository.TelefoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Telefone}.
 */
@Service
@Transactional
public class TelefoneService {

    private final Logger log = LoggerFactory.getLogger(TelefoneService.class);

    private final TelefoneRepository telefoneRepository;

    public TelefoneService(TelefoneRepository telefoneRepository) {
        this.telefoneRepository = telefoneRepository;
    }

    /**
     * Save a telefone.
     *
     * @param telefone the entity to save.
     * @return the persisted entity.
     */
    public Telefone save(Telefone telefone) {
        log.debug("Request to save Telefone : {}", telefone);
        return telefoneRepository.save(telefone);
    }

    /**
     * Get all the telefones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Telefone> findAll(Pageable pageable) {
        log.debug("Request to get all Telefones");
        return telefoneRepository.findAll(pageable);
    }


    /**
     * Get one telefone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Telefone> findOne(Long id) {
        log.debug("Request to get Telefone : {}", id);
        return telefoneRepository.findById(id);
    }

    /**
     * Delete the telefone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Telefone : {}", id);
        telefoneRepository.deleteById(id);
    }
}
