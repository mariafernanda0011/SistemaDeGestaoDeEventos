package model;

import contracts.Autenticavel;

/*
 * @author Maria Fernanda S. :) 
 */
public abstract class Usuario implements Autenticavel {

    private int id;
    private String nomeCompleto;
    private String cpf;
    private String nomeUsuario;
    private String senha;

    public Usuario(String nomeCompleto, String cpf, String nomeDeUsuario, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeDeUsuario;
        this.senha = senha;
        this.cpf = cpf;
    }

    public Usuario(int id, String nomeCompleto, String cpf) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
    }

    @Override
    public boolean autenticar(String nomeUsuario, String senha) {
        return this.nomeUsuario.equals(nomeUsuario) && this.senha.equals(senha);
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getNomeCompleto() {
        return this.nomeCompleto;
    }

    public String getNomeDeUsuario() {
        return this.nomeUsuario;
    }

    public String getSenha() {
        return this.senha;
    }

    public int getId() {
        return this.id;
    }

}
