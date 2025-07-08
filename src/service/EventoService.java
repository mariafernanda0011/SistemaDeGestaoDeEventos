package service;

import dao.*;
import exception.EventoNaoEncontradoException;
import model.*;
import java.util.List;

/*
 * @author Maria Fernanda S. :)
 */

public class EventoService {

    private EventoDao eventoDao;
    private InscricaoDao inscricaoDao;

    public EventoService() {
        this.eventoDao = new EventoDaoSQLite();
        this.inscricaoDao = new InscricaoDaoSQLite(); // Para consultar participantes
    }

    // RN para criar eventos //
    public void criarEvento(String nomeEvento, String duracao, String data, int hora, int limiteDeVagas) {
        if (nomeEvento == null || nomeEvento.isEmpty()) { // Todo evento deve ter um nome
            throw new IllegalArgumentException("Nome do evento não pode ser vazio."); // Exceção genérica
        }
        Evento evento = new Evento(nomeEvento, duracao, data, hora, limiteDeVagas); // Criando o evento com os dados
        eventoDao.criarEvento(evento); // Chama o método DaoSQLite para fazer as alterações no banco de dados 
    }

    // RN para editar eventos //
    public boolean editarEvento(String nomeEvento, String novaDuracao, String data, int hora, int novoLimiteVagas) {

        Evento eventoParaEditar = null; // Variável do tipo evento guarda valor nulo inicialmente
        // o método busca e se o evento existir a variavel será atualizada guardando o
        // evento(referência) encontrado...
        eventoParaEditar = eventoDao.buscarEvento(nomeEvento);

        if (eventoParaEditar != null) { // Agora verifica se o evento foi encontrado
            eventoDao.editarEvento(eventoParaEditar, nomeEvento, novaDuracao, data, hora, novoLimiteVagas); 
            return true; // Se sim, edição é feita com sucesso
        } 
        return false; // Se não, retorna falso(isso será util para tratamento de erros no menu)
    }

    // Regra de negócios para exluir eventos (Mesma lógica para edição) //
    public void excluirEvento(String nomeEvento) {
        Evento evento = eventoDao.buscarEvento(nomeEvento);

        if (evento != null) {
            eventoDao.excluirEvento(evento);
        } else {
            throw new EventoNaoEncontradoException("O evento não foi encontrado.");
        }
    }

    // RN para consultar a lista de participantes inscritos em cada evento e
    // atividade. //
    public List<Participante> consultarParticipantesEvento(String nomeEvento) {
        Evento evento = eventoDao.buscarEvento(nomeEvento);
        if (evento != null) {
            return inscricaoDao.listarPorEvento(evento.getId());
        }
        throw new EventoNaoEncontradoException("Evento não encontrado.");
    }

    public Evento buscarEvento(String nomeEvento){
        return eventoDao.buscarEvento(nomeEvento);
    }

    // RN para visualizar todos os eventos cadastrados. //
    public List<Evento> listarEventos() {
        return eventoDao.visualizarEventos();
    }
}
