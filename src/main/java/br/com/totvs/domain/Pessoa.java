package br.com.totvs.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "pessoa")
    private Set<Endereco> enderecos = new HashSet<>();

    @OneToMany(mappedBy = "pessoa")
    private Set<Dependente> dependentes = new HashSet<>();

    @OneToMany(mappedBy = "pessoa")
    private Set<Telefone> telefones = new HashSet<>();

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

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public Pessoa enderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
        return this;
    }

    public Pessoa addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setPessoa(this);
        return this;
    }

    public Pessoa removeEndereco(Endereco endereco) {
        this.enderecos.remove(endereco);
        endereco.setPessoa(null);
        return this;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<Dependente> getDependentes() {
        return dependentes;
    }

    public Pessoa dependentes(Set<Dependente> dependentes) {
        this.dependentes = dependentes;
        return this;
    }

    public Pessoa addDependente(Dependente dependente) {
        this.dependentes.add(dependente);
        dependente.setPessoa(this);
        return this;
    }

    public Pessoa removeDependente(Dependente dependente) {
        this.dependentes.remove(dependente);
        dependente.setPessoa(null);
        return this;
    }

    public void setDependentes(Set<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public Pessoa telefones(Set<Telefone> telefones) {
        this.telefones = telefones;
        return this;
    }

    public Pessoa addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setPessoa(this);
        return this;
    }

    public Pessoa removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setPessoa(null);
        return this;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
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
