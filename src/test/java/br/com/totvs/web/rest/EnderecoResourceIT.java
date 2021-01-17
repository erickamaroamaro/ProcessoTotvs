package br.com.totvs.web.rest;

import br.com.totvs.CadastroPessoasTotvsApp;
import br.com.totvs.domain.Endereco;
import br.com.totvs.repository.EnderecoRepository;
import br.com.totvs.service.EnderecoService;

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

import br.com.totvs.domain.enumeration.estadosEnum;
/**
 * Integration tests for the {@link EnderecoResource} REST controller.
 */
@SpringBootTest(classes = CadastroPessoasTotvsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnderecoResourceIT {

    private static final String DEFAULT_TIPO_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final estadosEnum DEFAULT_ESTADO = estadosEnum.SP;
    private static final estadosEnum UPDATED_ESTADO = estadosEnum.RJ;

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnderecoMockMvc;

    private Endereco endereco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endereco createEntity(EntityManager em) {
        Endereco endereco = new Endereco()
            .tipoEndereco(DEFAULT_TIPO_ENDERECO)
            .tipo(DEFAULT_TIPO)
            .nome(DEFAULT_NOME)
            .numero(DEFAULT_NUMERO)
            .complemento(DEFAULT_COMPLEMENTO)
            .cep(DEFAULT_CEP)
            .bairro(DEFAULT_BAIRRO)
            .cidade(DEFAULT_CIDADE)
            .estado(DEFAULT_ESTADO)
            .pais(DEFAULT_PAIS);
        return endereco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Endereco createUpdatedEntity(EntityManager em) {
        Endereco endereco = new Endereco()
            .tipoEndereco(UPDATED_TIPO_ENDERECO)
            .tipo(UPDATED_TIPO)
            .nome(UPDATED_NOME)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .cep(UPDATED_CEP)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS);
        return endereco;
    }

    @BeforeEach
    public void initTest() {
        endereco = createEntity(em);
    }

    @Test
    @Transactional
    public void createEndereco() throws Exception {
        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();
        // Create the Endereco
        restEnderecoMockMvc.perform(post("/api/enderecos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endereco)))
            .andExpect(status().isCreated());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate + 1);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getTipoEndereco()).isEqualTo(DEFAULT_TIPO_ENDERECO);
        assertThat(testEndereco.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testEndereco.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEndereco.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEndereco.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testEndereco.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testEndereco.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testEndereco.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEndereco.getPais()).isEqualTo(DEFAULT_PAIS);
    }

    @Test
    @Transactional
    public void createEnderecoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enderecoRepository.findAll().size();

        // Create the Endereco with an existing ID
        endereco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnderecoMockMvc.perform(post("/api/enderecos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endereco)))
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnderecos() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get all the enderecoList
        restEnderecoMockMvc.perform(get("/api/enderecos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(endereco.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoEndereco").value(hasItem(DEFAULT_TIPO_ENDERECO)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)));
    }
    
    @Test
    @Transactional
    public void getEndereco() throws Exception {
        // Initialize the database
        enderecoRepository.saveAndFlush(endereco);

        // Get the endereco
        restEnderecoMockMvc.perform(get("/api/enderecos/{id}", endereco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(endereco.getId().intValue()))
            .andExpect(jsonPath("$.tipoEndereco").value(DEFAULT_TIPO_ENDERECO))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS));
    }
    @Test
    @Transactional
    public void getNonExistingEndereco() throws Exception {
        // Get the endereco
        restEnderecoMockMvc.perform(get("/api/enderecos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndereco() throws Exception {
        // Initialize the database
        enderecoService.save(endereco);

        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // Update the endereco
        Endereco updatedEndereco = enderecoRepository.findById(endereco.getId()).get();
        // Disconnect from session so that the updates on updatedEndereco are not directly saved in db
        em.detach(updatedEndereco);
        updatedEndereco
            .tipoEndereco(UPDATED_TIPO_ENDERECO)
            .tipo(UPDATED_TIPO)
            .nome(UPDATED_NOME)
            .numero(UPDATED_NUMERO)
            .complemento(UPDATED_COMPLEMENTO)
            .cep(UPDATED_CEP)
            .bairro(UPDATED_BAIRRO)
            .cidade(UPDATED_CIDADE)
            .estado(UPDATED_ESTADO)
            .pais(UPDATED_PAIS);

        restEnderecoMockMvc.perform(put("/api/enderecos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEndereco)))
            .andExpect(status().isOk());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
        Endereco testEndereco = enderecoList.get(enderecoList.size() - 1);
        assertThat(testEndereco.getTipoEndereco()).isEqualTo(UPDATED_TIPO_ENDERECO);
        assertThat(testEndereco.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testEndereco.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEndereco.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEndereco.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testEndereco.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testEndereco.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testEndereco.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testEndereco.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEndereco.getPais()).isEqualTo(UPDATED_PAIS);
    }

    @Test
    @Transactional
    public void updateNonExistingEndereco() throws Exception {
        int databaseSizeBeforeUpdate = enderecoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnderecoMockMvc.perform(put("/api/enderecos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(endereco)))
            .andExpect(status().isBadRequest());

        // Validate the Endereco in the database
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEndereco() throws Exception {
        // Initialize the database
        enderecoService.save(endereco);

        int databaseSizeBeforeDelete = enderecoRepository.findAll().size();

        // Delete the endereco
        restEnderecoMockMvc.perform(delete("/api/enderecos/{id}", endereco.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Endereco> enderecoList = enderecoRepository.findAll();
        assertThat(enderecoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
