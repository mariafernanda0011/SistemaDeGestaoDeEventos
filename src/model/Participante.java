package model;

/*
 * @author Maria Fernanda S. :) 
 */

public class Participante extends Usuario {
    
    private TipoDeParticipante tipo;

    public Participante(String nomeCompleto, String cpf, String nomeDeUsuario, String senha, TipoDeParticipante tipo) {
        super(nomeCompleto, cpf, nomeDeUsuario, senha);
        this.tipo = tipo;
    }

    public Participante(int id, String nomeCompleto, String cpf, TipoDeParticipante tipo) {
        super(id, nomeCompleto, cpf);
        this.tipo = tipo;
    }

    public TipoDeParticipante getTipo() {
        return this.tipo;
    }

}
