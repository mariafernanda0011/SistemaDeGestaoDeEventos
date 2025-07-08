package view;

import java.util.List;
import model.*;
import service.*;
import util.Entrada;
import static view.MenuAdministrador.atividadeService;

public class MenuParticipante {

    private static final EventoService eventoService = new EventoService();
    private static final InscricaoService inscricaoService = new InscricaoService();
    private static final PagamentoService pagamentoService = new PagamentoService();

    private static final Entrada entrada = Entrada.getInstance();

    public void menuParticipante(Participante participante) {
        int opcao = 0;
        do {
            System.out.println("\n| MENU PARTICIPANTE - " + participante.getTipo() + " |");
            System.out.println(" -------------------------------------------------- ");
            System.out.println("1 - Ver eventos disponíveis");
            System.out.println("2 - Inscrever-se em evento");
            System.out.println("3 - Inscrever-se em atividade");
            System.out.println("4 - Efetuar pagamento");
            System.out.println("5 - Visualizar minhas inscrições");
            System.out.println("6 - Cancelar inscrição pendente");
            System.out.println("0 - Sair");
            System.out.println(" --------------------------------------------------- ");
            opcao = entrada.lerInt();

            switch (opcao) {
                case 1:
                    listarEventos();
                    break;
                case 2:
                    inscreverEmEvento(participante);
                    break;
                case 3:
                    inscreverEmAtividade(participante);
                    break;
                case 4:
                    efetuarPagamento(participante);
                    break;
                case 5:
                    visualizarInscricoes(participante);
                    break;
                case 6:
                    cancelarInscricao(participante);
                    break;
                case 0:
                    System.out.println("Saindo da área do participante...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);
    }

    private void listarEventos() {
        try {
            List<Evento> eventos = eventoService.listarEventos();

            if (eventos.isEmpty()) {
                System.out.println("Nenhum evento cadastrado no momento.");
                return;
            }

            System.out.println("\n| Lista de Eventos Disponíveis |");
            for (Evento evento : eventos) {
                System.out.println("ID: " + evento.getId());
                System.out.println("Nome: " + evento.getNomeEvento());
                System.out.println("Duração: " + evento.getDuracao());
                System.out.println("Data: " + evento.getData());
                System.out.println("Hora de Início: " + evento.getHora());
                System.out.println("Limite de Vagas: " + evento.getLimiteDeVagas());
            }

        } catch (RuntimeException ex) {
            System.out.println("Erro ao listar eventos: " + ex.getMessage());
        }
    }

    private void inscreverEmEvento(Participante participante) {
        System.out.println("Digite o nome do evento que deseja se inscrever:");
        String nomeEvento = entrada.lerString();

        Evento evento = eventoService.buscarEvento(nomeEvento);

        if (evento == null) {
            System.out.println("Evento não encontrado. Verifique o nome digitado.");
            return;
        }

        try {
            inscricaoService.realizarInscricaoEmEvento(participante.getId(), evento.getId());
            System.out.println("\nInscrição realizada com sucesso!");
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void inscreverEmAtividade(Participante participante) {
        System.out.println("Digite o nome do evento que contém a atividade:");
        String nomeEvento = entrada.lerString();

        Evento evento = eventoService.buscarEvento(nomeEvento);

        if (evento == null) {
            System.out.println("Evento não encontrado. Verifique o nome digitado.");
            return;
        }

        List<Atividade> atividades = atividadeService.listarAtividadesPorEvento(evento.getId());

        System.out.println("\nAtividades disponíveis para o evento " + nomeEvento + ":");
        for (Atividade atv : atividades) {
            System.out.println("ID: " + atv.getId() + " - " + atv.getDescricao());
        }

        System.out.print("Digite o ID da atividade para inscrição: ");
        int atividadeId = entrada.lerInt();

        try {
            inscricaoService.realizarInscricaoEmAtividade(participante.getId(), evento.getId(), atividadeId);
            System.out.println("\nInscrição na atividade realizada com sucesso!");
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void efetuarPagamento(Participante participante) {
        try {
            List<Evento> eventos = eventoService.listarEventos();

            if (eventos.isEmpty()) {
                System.out.println("Nenhum evento disponível para pagamento.");
                return;
            }

            System.out.println("\n| Eventos Disponíveis para Pagamento |");
            for (Evento evento : eventos) {
                System.out.println("ID: " + evento.getId());
                System.out.println("Nome: " + evento.getNomeEvento());
                System.out.println("Data: " + evento.getData());
                System.out.println("Hora: " + evento.getHora());
                System.out.println("-----------------------------------");
            }
            
            System.out.print("Digite o ID do evento que deseja pagar: ");
            int idEvento = entrada.lerInt();

            pagamentoService.realizarPagamento(participante.getId(), idEvento);

            System.out.println("\nPagamento efetuado com sucesso!");
        } catch (RuntimeException ex) {
            System.out.println("Erro ao realizar pagamento: " + ex.getMessage());
        }
    }

    private void visualizarInscricoes(Participante participante) {
        try {
            inscricaoService.exibirInscricoesComStatus(participante.getId());
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void cancelarInscricao(Participante participante) {

        System.out.println("Digite o nome do evento que deseja cancelar a inscrição:");
        String nomeEvento = entrada.lerString();

        Evento evento = eventoService.buscarEvento(nomeEvento);

        if (evento == null) {
            System.out.println("Evento não encontrado. Verifique o nome digitado.");
            return;
        }

        try {
            inscricaoService.cancelarInscricao(participante.getId(), evento.getId());
            System.out.println("\nInscrição cancelada com sucesso!");
        } catch (RuntimeException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

}
