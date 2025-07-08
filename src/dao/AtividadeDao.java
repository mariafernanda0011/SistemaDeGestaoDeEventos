package dao;

import java.util.List;
import model.*;

/* 
 * @author Maria Fernanda S. :)
 */

public interface AtividadeDao {

    void cadastrarAtividade(Atividade atividade, int idEvento);

    List<Atividade> listarPorEvento(int eventoId);
    
}
