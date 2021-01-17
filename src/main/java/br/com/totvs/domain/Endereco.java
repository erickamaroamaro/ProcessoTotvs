package br.com.totvs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

import br.com.totvs.domain.enumeration.estadosEnum;

/**
 * A Endereco.
 */
@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_endereco")
    private String tipoEndereco;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "cep")
    private String cep;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private estadosEnum estado;

    @Column(name = "pais")
    private String pais;

    @ManyToOne
    @JsonIgnoreProperties(value = "enderecos", allowSetters = true)
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public Endereco tipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
        return this;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public String getTipo() {
        return tipo;
    }

    public Endereco tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public Endereco nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return numero;
    }

    public Endereco numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public Endereco complemento(String complemento) {
        this.complemento = complemento;
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public Endereco cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public Endereco bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public Endereco cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public estadosEnum getEstado() {
        return estado;
    }

    public Endereco estado(estadosEnum estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(estadosEnum estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public Endereco pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Endereco pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Endereco)) {
            return false;
        }
        return id != null && id.equals(((Endereco) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Endereco{" +
            "id=" + getId() +
            ", tipoEndereco='" + getTipoEndereco() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", nome='" + getNome() + "'" +
            ", numero=" + getNumero() +
            ", complemento='" + getComplemento() + "'" +
            ", cep='" + getCep() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
