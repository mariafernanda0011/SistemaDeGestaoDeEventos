package service;

import dao.*;
import model.*;
import exception.*;

/*
 * @author Maria Fernanda S. :)
 */
public class AdministradorService {

    private AdministradorDao administradorDao;

    public AdministradorService() {
        this.administradorDao = new AdministradorDaoSQLite();
    }

    // Validando autenticação
    public Administrador autenticar(String nomeUsuario, String senha) {
        if (!administradorDao.autenticar(nomeUsuario, senha)) {
            throw new AutenticarException("Usuário ou senha inválidos.");
        }

        Administrador administrador = administradorDao.buscarUsuario(nomeUsuario);
        return administrador;
    }

    public Administrador buscarUsuario(String nomeDeUsuario) {
        return administradorDao.buscarUsuario(nomeDeUsuario);
    }

    // Método para exibir os valores //
    public void exibirValores() {
        double valorAluno = administradorDao.buscarValorPorTipo(TipoDeParticipante.ALUNO);
        double valorProfessor = administradorDao.buscarValorPorTipo(TipoDeParticipante.PROFESSOR);
        double valorProfissional = administradorDao.buscarValorPorTipo(TipoDeParticipante.PROFISSIONAL);

        System.out.println("[ Valores de inscrição ]");
        System.out.println("Aluno: R$ " + valorAluno);
        System.out.println("Professor: R$ " + valorProfessor);
        System.out.println("Profissional: R$ " + valorProfissional);
    }

    // Regra de negócio para gerenciar valores de incrição para cada tipo de
    // participante //
    public void gerenciarValores(double novoValor, TipoDeParticipante tipo) {

        if (novoValor <= 0) {
            throw new ValorInvalidoException("O valor deve ser maior que zero.");
        }

        administradorDao.gerenciarValores(novoValor, tipo);
        System.out.println("\nValor atualizado com sucesso para " + tipo);
    }

    // Validando CPFs
    public void cadastrarAdministrador(String nome, String cpf, String nomeDeUsuario, String senha) {
        if (cpf == null || cpf.length() < 11) {
            throw new CpfInvalidoException("CPF inválido.");
        }
        // Verificando se o usuário não existe ainda no sistema
        Administrador existente = administradorDao.buscarUsuario(nomeDeUsuario);

        if (existente != null) { // Se existir lança uma exceção
            throw new IllegalArgumentException("Nome de usuário inválido.");
        }
        // Criando um novo participante se tudo tiver ok
        Administrador novo = new Administrador(nome, cpf, nomeDeUsuario, senha);
        administradorDao.cadastrarAdministrador(novo);
    }

}
