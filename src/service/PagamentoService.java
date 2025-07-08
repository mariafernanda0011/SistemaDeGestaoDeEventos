package service;

import dao.PagamentoDao;
import dao.PagamentoDaoSQLite;
import java.util.List;
import model.Pagamento;

/*
 * @author Maria Fernanda S. :) 
 */

public class PagamentoService {

    private PagamentoDao pagamentoDao;

    public PagamentoService() {
        this.pagamentoDao = new PagamentoDaoSQLite();
    }

    public void realizarPagamento(int participanteId, int eventoId){
        pagamentoDao.realizarPagamento(participanteId, eventoId);
    }

    public void confirmarPagamento(int pagamentoId){
        pagamentoDao.confirmarPagamento(pagamentoId);
    }

    public List<Pagamento> listarPagamentosPendentes() {
        return pagamentoDao.listarPagamentosPendentes();
    }
}
