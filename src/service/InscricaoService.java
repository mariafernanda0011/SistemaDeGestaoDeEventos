package service;

import dao.*;
import model.*;
import java.util.List;

/*
 * @author Maria Fernanda S. :) 
 */

public class InscricaoService {

    private InscricaoDao inscricaoDao;

    public InscricaoService() {
        this.inscricaoDao = new InscricaoDaoSQLite();
    }

    public void realizarInscricaoEmEvento(int participanteId, int eventoId){
        inscricaoDao.realizarInscricaoEmEvento(participanteId, eventoId);
    }

    public void realizarInscricaoEmAtividade(int participanteId, int eventoId, int atividadeId){
        inscricaoDao.realizarInscricaoEmAtividade(participanteId, eventoId, atividadeId);
    }

    public void cancelarInscricao(int participanteId, int eventoId){
        inscricaoDao.excluirInscricao(participanteId, eventoId);
    }

    public List<Participante> listarParticipantesPorEvento(int eventoId) {
        return inscricaoDao.listarPorEvento(eventoId);
    }

    public List<Participante> listarParticipantesPorAtividade(int atividadeId) {
        return inscricaoDao.listarPorAtividade(atividadeId);
    }

    public void exibirInscricoesComStatus(int participanteId){
        inscricaoDao.exibirInscricoesComStatus(participanteId);
    }
}
