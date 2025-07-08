
import model.*;
import exception.*;
import service.*;
import util.*;
import view.*;

/*
 * @author Maria Fernanda S. :) 
 */
public class Principal {

    public static final AdministradorService administradorService = new AdministradorService();
    public static final ParticipanteService participanteService = new ParticipanteService();
    public static Entrada entrada = Entrada.getInstance();

    public static void main(String[] args) {
        DataBase.initializeDatabase();
        Principal principal = new Principal();
        principal.menu();
    }

    // Menu inicial do sistema //
    public void menu() {
        int opcao = 0;
        do {
            System.out.println("\n | SISTEMA DE GERENCIAMENTO DE EVENTOS ACADÊMICOS |");
            System.out.println();
            System.out.println("1 - Fazer login como Administrador");
            System.out.println("2 - Fazer login como Participante");
            System.out.println("3 - Criar nova conta de Participante");
            System.out.println("4 - Criar nova conta de Administrador");
            System.out.println("0 - Sair");
            
            try {
                opcao = Integer.parseInt(entrada.lerString());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }
            
// Switch case aqui...
            switch (opcao) {
                case 1:
                    loginAdministrador();
                    break;
                case 2:
                    loginParticipante();
                    break;
                case 3:
                    cadastrarParticipante();
                    break;
                case 4:
                    cadastrarAdministrador();
                    break;
                case 0:
                    System.out.println("Sistema Encerrado.");
                    break;
            }

        } while (opcao != 0);
    }

    // Login do Adiministrador //
    public void loginAdministrador() {

        System.out.print("Nome de usuário: ");
        String nomeDeUsuario = entrada.lerString();

        System.out.print("Senha: ");
        String senha = entrada.lerString();

        try {
            Administrador admin = administradorService.autenticar(nomeDeUsuario, senha);
            if (admin != null) {

                System.out.println("\nLogin realizado com sucesso!");
                System.out.println("Bem-vindo(a), " + admin.getNomeCompleto() + "!");
                System.out.println();
                MenuAdministrador menuAdm = new MenuAdministrador();
                menuAdm.menuAdministrativo(admin);
            } else {
                System.out.println("Usuário ou senha inválidos.");
            }
        } catch (AutenticarException ex) {
            System.out.println("Erro de autenticação: " + ex.getMessage());
        }
    }

    // Login do Participante //
    public void loginParticipante() {

        System.out.print("Nome de usuário: ");
        String nomeDeUsuario = entrada.lerString();

        System.out.print("Senha: ");
        String senha = entrada.lerString();

        try {
            Participante part = participanteService.autenticar(nomeDeUsuario, senha);
            if (part != null) {

                System.out.println("\n Login realizado com sucesso!");
                System.out.println("Bem-vindo(a), " + part.getNomeCompleto() + "!");

                MenuParticipante menuPart = new MenuParticipante();
                menuPart.menuParticipante(part);
            } else {
                System.out.println("Usuário ou senha inválidos.");
            }
        } catch (AutenticarException ex) {
            System.out.println("Erro de autenticação: " + ex.getMessage());
        }
    }

    private void cadastrarParticipante() {
        System.out.println("\n[ Cadastro de Participante ]");

        System.out.print("Nome completo: ");
        String nome = entrada.lerString();

        System.out.print("CPF: ");
        String cpf = entrada.lerString();

        System.out.print("Nome de usuário: ");
        String nomeUsuario = entrada.lerString();

        System.out.print("Senha: ");
        String senha = entrada.lerString();

        System.out.println("Tipo de participante:");
        System.out.println("1 - Aluno");
        System.out.println("2 - Professor");
        System.out.println("3 - Profissional");
        System.out.print("Escolha: ");
        int tipoOpcao = entrada.lerInt();

        TipoDeParticipante tipo;
        switch (tipoOpcao) {
            case 1:
                tipo = TipoDeParticipante.ALUNO;
                break;
            case 2:
                tipo = TipoDeParticipante.PROFESSOR;
                break;
            case 3:
                tipo = TipoDeParticipante.PROFISSIONAL;
                break;
            default:
                System.out.println("Tipo inválido. Cadastro cancelado.");
                return;
        }
        try {
            participanteService.cadastrarParticipante(nome, cpf, nomeUsuario, senha, tipo);
            System.out.println("Cadastro realizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar participante: " + e.getMessage());
        }
    }

    private void cadastrarAdministrador() {
        System.out.println("\n[ Cadastro de Administrador ]");

        System.out.print("Nome completo: ");
        String nome = entrada.lerString();

        System.out.print("CPF: ");
        String cpf = entrada.lerString();

        System.out.print("Nome de usuário: ");
        String nomeUsuario = entrada.lerString();

        System.out.print("Senha: ");
        String senha = entrada.lerString();

        try {
            administradorService.cadastrarAdministrador(nome, cpf, nomeUsuario, senha);
            System.out.println("Cadastro realizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao cadastrar administrador: " + e.getMessage());
        }
    }

}
