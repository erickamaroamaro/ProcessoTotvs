package br.com.totvs.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Pessoa.
 */
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "apelido")
    private String apelido;

    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "salario")
    private Float salario;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public Pessoa apelido(String apelido) {
        this.apelido = apelido;
        return this;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public Pessoa cpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getProfissao() {
        return profissao;
    }

    public Pessoa profissao(String profissao) {
        this.profissao = profissao;
        return this;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public Float getSalario() {
        return salario;
    }

    public Pessoa salario(Float salario) {
        this.salario = salario;
        return this;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Pessoa dataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pessoa)) {
            return false;
        }
        return id != null && id.equals(((Pessoa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pessoa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", apelido='" + getApelido() + "'" +
            ", cpfCnpj='" + getCpfCnpj() + "'" +
            ", profissao='" + getProfissao() + "'" +
            ", salario=" + getSalario() +
            ", dataNascimento='" + getDataNascimento() + "'" +
            "}";
    }
}
