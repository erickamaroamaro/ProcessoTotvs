package br.com.totvs.web.rest;

import br.com.totvs.domain.Dependente;
import br.com.totvs.service.DependenteService;
import br.com.totvs.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.totvs.domain.Dependente}.
 */
@RestController
@RequestMapping("/api")
public class DependenteResource {

    private final Logger log = LoggerFactory.getLogger(DependenteResource.class);

    private static final String ENTITY_NAME = "dependente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DependenteService dependenteService;

    public DependenteResource(DependenteService dependenteService) {
        this.dependenteService = dependenteService;
    }

    /**
     * {@code POST  /dependentes} : Create a new dependente.
     *
     * @param dependente the dependente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dependente, or with status {@code 400 (Bad Request)} if the dependente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dependentes")
    public ResponseEntity<Dependente> createDependente(@RequestBody Dependente dependente) throws URISyntaxException {
        log.debug("REST request to save Dependente : {}", dependente);
        if (dependente.getId() != null) {
            throw new BadRequestAlertException("A new dependente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dependente result = dependenteService.save(dependente);
        return ResponseEntity.created(new URI("/api/dependentes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dependentes} : Updates an existing dependente.
     *
     * @param dependente the dependente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dependente,
     * or with status {@code 400 (Bad Request)} if the dependente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dependente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dependentes")
    public ResponseEntity<Dependente> updateDependente(@RequestBody Dependente dependente) throws URISyntaxException {
        log.debug("REST request to update Dependente : {}", dependente);
        if (dependente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dependente result = dependenteService.save(dependente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dependente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dependentes} : get all the dependentes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dependentes in body.
     */
    @GetMapping("/dependentes")
    public ResponseEntity<List<Dependente>> getAllDependentes(Pageable pageable) {
        log.debug("REST request to get a page of Dependentes");
        Page<Dependente> page = dependenteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dependentes/:id} : get the "id" dependente.
     *
     * @param id the id of the dependente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dependente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dependentes/{id}")
    public ResponseEntity<Dependente> getDependente(@PathVariable Long id) {
        log.debug("REST request to get Dependente : {}", id);
        Optional<Dependente> dependente = dependenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dependente);
    }

    /**
     * {@code DELETE  /dependentes/:id} : delete the "id" dependente.
     *
     * @param id the id of the dependente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dependentes/{id}")
    public ResponseEntity<Void> deleteDependente(@PathVariable Long id) {
        log.debug("REST request to delete Dependente : {}", id);
        dependenteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
