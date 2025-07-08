package dao;

import model.*;

/*
 * @author Maria Fernanda S. :) 
 */

public interface ParticipanteDao {

    boolean autenticar(String nomeUsuario, String senha);

    void cadastrarParticipante(Participante participante);

    Participante buscarUsuario(String nomeDeUsuario);

}
