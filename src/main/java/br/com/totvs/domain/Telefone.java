package br.com.totvs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Telefone.
 */
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_pais")
    private String codigoPais;

    @Column(name = "ddd")
    private Integer ddd;

    @Column(name = "numero")
    private String numero;

    @Column(name = "tipo")
    private String tipo;

    @ManyToOne
    @JsonIgnoreProperties(value = "telefones", allowSetters = true)
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public Telefone codigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
        return this;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Integer getDdd() {
        return ddd;
    }

    public Telefone ddd(Integer ddd) {
        this.ddd = ddd;
        return this;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public Telefone numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public Telefone tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Telefone pessoa(Pessoa pessoa) {
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
        if (!(o instanceof Telefone)) {
            return false;
        }
        return id != null && id.equals(((Telefone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telefone{" +
            "id=" + getId() +
            ", codigoPais='" + getCodigoPais() + "'" +
            ", ddd=" + getDdd() +
            ", numero='" + getNumero() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
