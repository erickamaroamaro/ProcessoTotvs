package br.com.totvs.web.rest;

import br.com.totvs.CadastroPessoasTotvsApp;
import br.com.totvs.domain.Telefone;
import br.com.totvs.repository.TelefoneRepository;
import br.com.totvs.service.TelefoneService;

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

/**
 * Integration tests for the {@link TelefoneResource} REST controller.
 */
@SpringBootTest(classes = CadastroPessoasTotvsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TelefoneResourceIT {

    private static final String DEFAULT_CODIGO_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PAIS = "BBBBBBBBBB";

    private static final Integer DEFAULT_DDD = 1;
    private static final Integer UPDATED_DDD = 2;

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private TelefoneService telefoneService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelefoneMockMvc;

    private Telefone telefone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .codigoPais(DEFAULT_CODIGO_PAIS)
            .ddd(DEFAULT_DDD)
            .numero(DEFAULT_NUMERO)
            .tipo(DEFAULT_TIPO);
        return telefone;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telefone createUpdatedEntity(EntityManager em) {
        Telefone telefone = new Telefone()
            .codigoPais(UPDATED_CODIGO_PAIS)
            .ddd(UPDATED_DDD)
            .numero(UPDATED_NUMERO)
            .tipo(UPDATED_TIPO);
        return telefone;
    }

    @BeforeEach
    public void initTest() {
        telefone = createEntity(em);
    }

    @Test
    @Transactional
    public void createTelefone() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();
        // Create the Telefone
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(telefone)))
            .andExpect(status().isCreated());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate + 1);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getCodigoPais()).isEqualTo(DEFAULT_CODIGO_PAIS);
        assertThat(testTelefone.getDdd()).isEqualTo(DEFAULT_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testTelefone.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createTelefoneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = telefoneRepository.findAll().size();

        // Create the Telefone with an existing ID
        telefone.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelefoneMockMvc.perform(post("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(telefone)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTelefones() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get all the telefoneList
        restTelefoneMockMvc.perform(get("/api/telefones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telefone.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoPais").value(hasItem(DEFAULT_CODIGO_PAIS)))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)));
    }
    
    @Test
    @Transactional
    public void getTelefone() throws Exception {
        // Initialize the database
        telefoneRepository.saveAndFlush(telefone);

        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", telefone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telefone.getId().intValue()))
            .andExpect(jsonPath("$.codigoPais").value(DEFAULT_CODIGO_PAIS))
            .andExpect(jsonPath("$.ddd").value(DEFAULT_DDD))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO));
    }
    @Test
    @Transactional
    public void getNonExistingTelefone() throws Exception {
        // Get the telefone
        restTelefoneMockMvc.perform(get("/api/telefones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTelefone() throws Exception {
        // Initialize the database
        telefoneService.save(telefone);

        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // Update the telefone
        Telefone updatedTelefone = telefoneRepository.findById(telefone.getId()).get();
        // Disconnect from session so that the updates on updatedTelefone are not directly saved in db
        em.detach(updatedTelefone);
        updatedTelefone
            .codigoPais(UPDATED_CODIGO_PAIS)
            .ddd(UPDATED_DDD)
            .numero(UPDATED_NUMERO)
            .tipo(UPDATED_TIPO);

        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTelefone)))
            .andExpect(status().isOk());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
        Telefone testTelefone = telefoneList.get(telefoneList.size() - 1);
        assertThat(testTelefone.getCodigoPais()).isEqualTo(UPDATED_CODIGO_PAIS);
        assertThat(testTelefone.getDdd()).isEqualTo(UPDATED_DDD);
        assertThat(testTelefone.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testTelefone.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingTelefone() throws Exception {
        int databaseSizeBeforeUpdate = telefoneRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelefoneMockMvc.perform(put("/api/telefones")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(telefone)))
            .andExpect(status().isBadRequest());

        // Validate the Telefone in the database
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTelefone() throws Exception {
        // Initialize the database
        telefoneService.save(telefone);

        int databaseSizeBeforeDelete = telefoneRepository.findAll().size();

        // Delete the telefone
        restTelefoneMockMvc.perform(delete("/api/telefones/{id}", telefone.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Telefone> telefoneList = telefoneRepository.findAll();
        assertThat(telefoneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
