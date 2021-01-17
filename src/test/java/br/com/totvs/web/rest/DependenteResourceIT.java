package br.com.totvs.web.rest;

import br.com.totvs.CadastroPessoasTotvsApp;
import br.com.totvs.domain.Dependente;
import br.com.totvs.repository.DependenteRepository;
import br.com.totvs.service.DependenteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.totvs.domain.enumeration.dependenteEnum;
/**
 * Integration tests for the {@link DependenteResource} REST controller.
 */
@SpringBootTest(classes = CadastroPessoasTotvsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DependenteResourceIT {

    private static final String DEFAULT_NOME_COMPLETO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_COMPLETO = "BBBBBBBBBB";

    private static final dependenteEnum DEFAULT_TIPO_DEPENDENTE = dependenteEnum.ESPOSA;
    private static final dependenteEnum UPDATED_TIPO_DEPENDENTE = dependenteEnum.FILHO;

    @Autowired
    private DependenteRepository dependenteRepository;

    @Autowired
    private DependenteService dependenteService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDependenteMockMvc;

    private Dependente dependente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependente createEntity(EntityManager em) {
        Dependente dependente = new Dependente()
            .nomeCompleto(DEFAULT_NOME_COMPLETO)
            .tipoDependente(DEFAULT_TIPO_DEPENDENTE);
        return dependente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependente createUpdatedEntity(EntityManager em) {
        Dependente dependente = new Dependente()
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .tipoDependente(UPDATED_TIPO_DEPENDENTE);
        return dependente;
    }

    @BeforeEach
    public void initTest() {
        dependente = createEntity(em);
    }

    @Test
    @Transactional
    public void createDependente() throws Exception {
        int databaseSizeBeforeCreate = dependenteRepository.findAll().size();
        // Create the Dependente
        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isCreated());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeCreate + 1);
        Dependente testDependente = dependenteList.get(dependenteList.size() - 1);
        assertThat(testDependente.getNomeCompleto()).isEqualTo(DEFAULT_NOME_COMPLETO);
        assertThat(testDependente.getTipoDependente()).isEqualTo(DEFAULT_TIPO_DEPENDENTE);
    }

    @Test
    @Transactional
    public void createDependenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dependenteRepository.findAll().size();

        // Create the Dependente with an existing ID
        dependente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependenteMockMvc.perform(post("/api/dependentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDependentes() throws Exception {
        // Initialize the database
        dependenteRepository.saveAndFlush(dependente);

        // Get all the dependenteList
        restDependenteMockMvc.perform(get("/api/dependentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCompleto").value(hasItem(DEFAULT_NOME_COMPLETO)))
            .andExpect(jsonPath("$.[*].tipoDependente").value(hasItem(DEFAULT_TIPO_DEPENDENTE.toString())));
    }
    
    @Test
    @Transactional
    public void getDependente() throws Exception {
        // Initialize the database
        dependenteRepository.saveAndFlush(dependente);

        // Get the dependente
        restDependenteMockMvc.perform(get("/api/dependentes/{id}", dependente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dependente.getId().intValue()))
            .andExpect(jsonPath("$.nomeCompleto").value(DEFAULT_NOME_COMPLETO))
            .andExpect(jsonPath("$.tipoDependente").value(DEFAULT_TIPO_DEPENDENTE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDependente() throws Exception {
        // Get the dependente
        restDependenteMockMvc.perform(get("/api/dependentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDependente() throws Exception {
        // Initialize the database
        dependenteService.save(dependente);

        int databaseSizeBeforeUpdate = dependenteRepository.findAll().size();

        // Update the dependente
        Dependente updatedDependente = dependenteRepository.findById(dependente.getId()).get();
        // Disconnect from session so that the updates on updatedDependente are not directly saved in db
        em.detach(updatedDependente);
        updatedDependente
            .nomeCompleto(UPDATED_NOME_COMPLETO)
            .tipoDependente(UPDATED_TIPO_DEPENDENTE);

        restDependenteMockMvc.perform(put("/api/dependentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDependente)))
            .andExpect(status().isOk());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeUpdate);
        Dependente testDependente = dependenteList.get(dependenteList.size() - 1);
        assertThat(testDependente.getNomeCompleto()).isEqualTo(UPDATED_NOME_COMPLETO);
        assertThat(testDependente.getTipoDependente()).isEqualTo(UPDATED_TIPO_DEPENDENTE);
    }

    @Test
    @Transactional
    public void updateNonExistingDependente() throws Exception {
        int databaseSizeBeforeUpdate = dependenteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependenteMockMvc.perform(put("/api/dependentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dependente)))
            .andExpect(status().isBadRequest());

        // Validate the Dependente in the database
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDependente() throws Exception {
        // Initialize the database
        dependenteService.save(dependente);

        int databaseSizeBeforeDelete = dependenteRepository.findAll().size();

        // Delete the dependente
        restDependenteMockMvc.perform(delete("/api/dependentes/{id}", dependente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dependente> dependenteList = dependenteRepository.findAll();
        assertThat(dependenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
