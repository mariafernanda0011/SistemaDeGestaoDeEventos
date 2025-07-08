package model;

import java.util.List;

/*
 * @author Maria Fernanda S. :) 
 */
public class Evento {

    private int id;
    private String nomeEvento;
    private String duracao;
    private String data;
    private int hora;
    private int limiteDeVagas;

    // Constructor //
    public Evento(int id, String nome, String duracao, String data, int hora, int limiteDeVagas) {
        this.id = id;
        this.nomeEvento = nome;
        this.duracao = duracao;
        this.data = data;
        this.hora = hora;
        this.limiteDeVagas = limiteDeVagas;
    }

    public Evento(String nome, String duracao, String data, int hora, int limiteDeVagas) {
        this.nomeEvento = nome;
        this.duracao = duracao;
        this.data = data;
        this.hora = hora;
        this.limiteDeVagas = limiteDeVagas;
    }

    public int getId() {
        return this.id;
    }

    public String getNomeEvento() {
        return this.nomeEvento;
    }

    public String getDuracao() {
        return this.duracao;
    }

    public String getData() {
        return this.data;
    }

    public int getHora() {
        return this.hora;
    }

    public int getLimiteDeVagas() {
        return this.limiteDeVagas;
    }

    public void setNomeDoEvento(String nomeDoEvento) {
        this.nomeEvento = nomeDoEvento;
    }

    public void setDuracao(String novaDuracao) {
        this.duracao = novaDuracao;
    }

    public void setData(String novaData) {
        this.data = novaData;
    }

    public void setHora(int novaHora) {
        this.hora = novaHora;
    }

    public void setLimiteDeVagas(int novoLimite) {
        this.limiteDeVagas = novoLimite;
    }

}
