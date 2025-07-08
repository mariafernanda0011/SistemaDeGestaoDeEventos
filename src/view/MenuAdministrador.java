package view;

import exception.*;
import java.util.List;
import model.*;
import service.*;
import util.Entrada;
import model.TipoDeParticipante;

/* 
 * @author Maria Fernanda S. :)
 */
public class MenuAdministrador {

    public static final AdministradorService administradorService = new AdministradorService();
    public static final AtividadeService atividadeService = new AtividadeService();
    public static final PagamentoService pagamentoService = new PagamentoService();
    public static final ParticipanteService participanteService = new ParticipanteService();
    public static final InscricaoService inscricaoService = new InscricaoService();
    public static final EventoService eventoService = new EventoService();

    public static Entrada entrada = Entrada.getInstance();

    // Menu administrativo acessado por administradores //
    public void menuAdministrativo(Administrador administrador) {
        int opcao = 0;
        do {
            System.out.println();
            System.out.println("|        MENU ADMINISTRATIVO      |");
            System.out.println(" --------------------------------- ");
            System.out.println(" 1 - Criar Evento");
            System.out.println(" 2 - Editar Eventos");
            System.out.println(" 3 - Excluir Evento");
            System.out.println(" 4 - Cadastrar Atividade");
            System.out.println(" 5 - Confirmar pagamentos");
            System.out.println(" 6 - Acessar Painel Administrativo");
            System.out.println(" 0 - Sair");
            System.out.println(" ---------------------------------- ");
            opcao = Integer.parseInt(entrada.lerString());

            switch (opcao) {
                case 1:
                    criarEvento();
                    break;
                case 2:
                    editarEvento();
                    break;
                case 3:
                    excluirEvento();
                    break;
                case 4:
                    cadastrarAtividade();
                    break;
                case 5:
                    confirmarPagamento();
                    break;
                case 6:
                    painelAdministrativo(administrador);
                    break;
                case 0:
                    System.out.println("Saindo do menu.");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);
    }

    private void criarEvento() {

        System.out.print("Descrição/Nome do evento: ");
        String nomeEvento = entrada.lerString();

        System.out.print("Duração: ");
        String duracao = entrada.lerString();

        System.out.print("Data: ");
        String data = entrada.lerString();

        System.out.print("Hora de início: ");
        int hora = entrada.lerInt();

        System.out.print("Limite de vagas: ");
        int limite = Integer.parseInt(entrada.lerString());

        try {
            eventoService.criarEvento(nomeEvento, duracao, data, hora, limite);
            System.out.println("\nEvento criado com sucesso!");
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    public void excluirEvento() {

        System.out.print("Digite o nome do evento que deseja excluir: ");
        String nome = entrada.lerString();

        Evento evento = eventoService.buscarEvento(nome);

        if (evento != null) {
            eventoService.excluirEvento(nome);
            System.out.println("\nEvento excluído com sucesso!");
        } else {
            System.out.println("Evento não encontrado.");
        }
    }

    public void editarEvento() {

        System.out.print("Qual é o nome do evento você deseja editar? ");
        String nomeEvento = entrada.lerString();

        System.out.print("Informe a nova duração do evento: ");
        String novaDuracao = entrada.lerString();

        System.out.print("Informe uma nova data para o evento: ");
        String novaData = entrada.lerString();

        System.out.print("Informe um novo Horário para início do evento: ");
        int novaHora = entrada.lerInt();

        System.out.print("Informe um novo limite de vagas: ");
        int novoLimiteVagas = entrada.lerInt();

        try {
            Evento evento = eventoService.buscarEvento(nomeEvento);

            if (evento != null) {
                eventoService.editarEvento(nomeEvento, novaDuracao, novaData, novaHora, novoLimiteVagas);
                System.out.println("\nEdições feitas com sucesso.");
            } else {
                throw new EventoNaoEncontradoException("Evento não encontrado.");
            }
        } catch (EventoNaoEncontradoException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void cadastrarAtividade() {

        System.out.print("Digite o nome do evento para adicionar a atividade:");
        String nomeEvento = entrada.lerString();

        System.out.print("Descrição da Atividade:");
        String descricao = entrada.lerString();

        System.out.print("Ministrante:");
        String palestrante = entrada.lerString();

        System.out.print("Data de Realização: ");
        int dataDeRealizacao = entrada.lerInt();

        System.out.print("Limite de Inscritos: ");
        int limiteDeInscritos = entrada.lerInt();

        System.out.println("Escolha o tipo da atividade:");
        System.out.println("1 - PALESTRA");
        System.out.println("2 - SIMPÓSIO");
        System.out.println("3 - CURSO");

        int opcaoTipo = entrada.lerInt();
        TipoDeAtividade tipo = null;

        switch (opcaoTipo) {
            case 1:
                tipo = TipoDeAtividade.PALESTRA;
                break;
            case 2:
                tipo = TipoDeAtividade.SIMPOSIO;
                break;
            case 3:
                tipo = TipoDeAtividade.CURSO;
                break;
            default:
                System.out.println("Tipo inválido. Operação cancelada.");
                return;
        }

        try {
            Evento evento = eventoService.buscarEvento(nomeEvento);

            if (evento == null) {
                throw new EventoNaoEncontradoException("Evento não encontrado.");
            }

            atividadeService.cadastrarAtividade(descricao, palestrante, dataDeRealizacao, limiteDeInscritos, tipo, evento.getId());
            System.out.println("\nAtividade cadastrada com sucesso.");
        } catch (EventoNaoEncontradoException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void confirmarPagamento() {
        try {
            List<Pagamento> pagamentosPendentes = pagamentoService.listarPagamentosPendentes();

            if (pagamentosPendentes.isEmpty()) {
                System.out.println("Não há pagamentos pendentes.");
                return;
            }

            System.out.println("\n[ Pagamentos Pendentes ]");
            for (Pagamento pagamento : pagamentosPendentes) {
                System.out.println("ID: " + pagamento.getId());
                System.out.println("Participante: " + pagamento.getParticipante().getNomeCompleto());
                System.out.println("Evento: " + pagamento.getEvento().getNomeEvento());
                System.out.println("Confirmado: " + pagamento.getStatus());
                System.out.println();
            }
            System.out.print("Digite o ID do pagamento que deseja confirmar: ");
            int idPagamento = entrada.lerInt();

            pagamentoService.confirmarPagamento(idPagamento);

        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void painelAdministrativo(Administrador administrador) {
        int opcao = 0;
        do {
            System.out.println("|            PAINEL ADMINISTRATIVO        |");
            System.out.println(" ----------------------------------------- ");
            System.out.println("1 - Visualizar Eventos ");
            System.out.println("2 - Consultar Participantes por Evento");
            System.out.println("3 - Visualizar Status de Pagamentos");
            System.out.println("4 - Gerenciar Valores ");
            System.out.println("0 - Sair");
            System.out.println(" ----------------------------------------- ");
            opcao = Integer.parseInt(entrada.lerString());

            switch (opcao) {
                case 1:
                    listarEventos();
                    break;
                case 2:
                    consultarParticipante();
                    break;
                case 3:
                    statusDePagamentos();
                    break;
                case 4:
                    gerenciarValores();
                    break;
                case 0:
                    System.out.println("Saindo do painel.");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);
    }

    // Visualizando todos os eventos cadastrados. //
    private void listarEventos() {
        try {
            List<Evento> eventos = eventoService.listarEventos();

            if (eventos.isEmpty()) {
                System.out.println("Nenhum evento cadastrado.");
                return;
            }

            System.out.println("\n[ Lista de Eventos Cadastrados ]");
            for (Evento evento : eventos) {
                System.out.println("ID: " + evento.getId());
                System.out.println("Nome: " + evento.getNomeEvento());
                System.out.println("Duração: " + evento.getDuracao());
                System.out.println("Data: " + evento.getData());
                System.out.println("Hora de Início: " + evento.getHora());
                System.out.println("Limite de Vagas: " + evento.getLimiteDeVagas());
                System.out.println("------------");
            }
        } catch (RuntimeException ex) {
            System.out.println("Erro ao listar eventos: " + ex.getMessage());
        }
    }

    // Visualizando o status de pagamento de cada participante. //
    private void statusDePagamentos() {
        try {
            administradorService.exibirValores();
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    // Gerenciando os valores de inscrição para cada tipo de participante. //
    private void gerenciarValores() {
        System.out.println("Escolha o tipo de participante para alterar o valor de inscrição: ");
        System.out.println("1 - ALUNO");
        System.out.println("2 - PROFESSOR");
        System.out.println("3 - PROFISSIONAL");
        int opcaoTipo = entrada.lerInt();

        TipoDeParticipante tipo = null;

        switch (opcaoTipo) {
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
                System.out.println("Tipo inválido. Operação cancelada.");
                return;
        }

        System.out.print("Digite o novo valor para o tipo - " + tipo + ": R$ ");
        double novoValor = entrada.lerDouble();

        try {
            administradorService.gerenciarValores(novoValor, tipo);
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void consultarParticipante() {
        try {
            System.out.print("Digite o nome do evento que deseja consultar: ");
            String nomeEvento = entrada.lerString();

            eventoService.consultarParticipantesEvento(nomeEvento);
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

}
