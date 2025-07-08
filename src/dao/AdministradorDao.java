package dao;

import model.*;

/*
 * @author Maria Fernanda S. :) 
 */
public interface AdministradorDao {

    boolean autenticar(String nomeUsuario, String senha);

    Administrador buscarUsuario(String nomeDeUsuario);

    double buscarValorPorTipo(TipoDeParticipante tipo);

    void gerenciarValores(double valorIncricao, TipoDeParticipante tipo);

    void cadastrarAdministrador(Administrador administrador);
    
}
