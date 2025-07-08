package service;

import dao.*;
import model.*;
import java.util.List;

/* 
 * @author Maria Fernanda S. :)
 */

public class AtividadeService {

    private AtividadeDao atividadeDao;

    public AtividadeService() {
        this.atividadeDao = new AtividadeDaoSQLite();
    }

    public void cadastrarAtividade(String descricao, String ministrante, int dataDeRealizacao, int limiteIncritos, TipoDeAtividade tipo, int idEvento) {
        
        if (descricao == null || descricao.isEmpty() || limiteIncritos <= 0) {
            throw new IllegalArgumentException("Erro ao inserir dados de descrição e limite de vagas.");
        }

        Atividade novaAtv = new Atividade(descricao, ministrante, dataDeRealizacao, limiteIncritos, tipo);
        atividadeDao.cadastrarAtividade(novaAtv, idEvento);
    }

    public List<Atividade> listarAtividadesPorEvento(int idEvento) {
        return atividadeDao.listarPorEvento(idEvento);
    }
}
