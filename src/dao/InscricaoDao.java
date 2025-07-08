package dao;

import java.util.List;
import model.*;

/*
 * @author Maria Fernanda S. :) 
 */

public interface InscricaoDao {

    void realizarInscricaoEmEvento(int participanteId, int eventoId);

    void realizarInscricaoEmAtividade(int participanteId, int eventoId, int atividadeId);

    void excluirInscricao(int participanteId, int eventoId);

    List<Participante> listarPorEvento(int eventoId);

    List<Participante> listarPorAtividade(int atividadeId);

    void exibirInscricoesComStatus(int participanteId);
}
