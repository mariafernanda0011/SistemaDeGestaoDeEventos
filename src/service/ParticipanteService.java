package service;

import dao.*;
import exception.*;
import model.*;

/*
 * @author Maria Fernanda S. :)
 */

public class ParticipanteService {

    private ParticipanteDao participanteDao;

    public ParticipanteService() {
        this.participanteDao = new ParticipanteDaoSQLite();
    }

    // Validando autenticação
    public Participante autenticar(String nomeUsuario, String senha) {
        if (!participanteDao.autenticar(nomeUsuario, senha)) {
            throw new AutenticarException("Usuário ou senha inválidos.");
        }

        Participante participante = participanteDao.buscarUsuario(nomeUsuario);
        return participante;
    }

    // Validando CPFs
    public void cadastrarParticipante(String nome, String cpf, String nomeDeUsuario, String senha,
            TipoDeParticipante tipo) {
        if (cpf == null || cpf.length() < 11) {
            throw new CpfInvalidoException("CPF inválido.");
        }
        // Verificando se o usuário não existe ainda no sistema
        Participante existente = participanteDao.buscarUsuario(nomeDeUsuario);

        if (existente != null) { // Se existir lança uma exceção
            throw new IllegalArgumentException("Nome de usuário inválido.");
        }
        // Criando um novo participante se tudo tiver ok
        Participante novo = new Participante(nome, cpf, nomeDeUsuario, senha, tipo);
        participanteDao.cadastrarParticipante(novo);
    }

    public Participante buscarUsuario(String nomeDeUsuario){
        return participanteDao.buscarUsuario(nomeDeUsuario);
    }
}
