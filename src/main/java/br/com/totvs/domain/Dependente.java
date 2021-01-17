package br.com.totvs.domain;


import javax.persistence.*;

import java.io.Serializable;

import br.com.totvs.domain.enumeration.dependenteEnum;

/**
 * A Dependente.
 */
@Entity
@Table(name = "dependente")
public class Dependente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo")
    private String nomeCompleto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dependente")
    private dependenteEnum tipoDependente;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public Dependente nomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
        return this;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public dependenteEnum getTipoDependente() {
        return tipoDependente;
    }

    public Dependente tipoDependente(dependenteEnum tipoDependente) {
        this.tipoDependente = tipoDependente;
        return this;
    }

    public void setTipoDependente(dependenteEnum tipoDependente) {
        this.tipoDependente = tipoDependente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dependente)) {
            return false;
        }
        return id != null && id.equals(((Dependente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dependente{" +
            "id=" + getId() +
            ", nomeCompleto='" + getNomeCompleto() + "'" +
            ", tipoDependente='" + getTipoDependente() + "'" +
            "}";
    }
}
