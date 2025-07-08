package dao;

import java.util.List;
import model.*;

/*
 * @author Maria Fernanda S. :) 
 */

public interface PagamentoDao {

    void realizarPagamento(int participanteId, int eventoId); //Para os Participantes

    void confirmarPagamento(int pagamentoId); // Para os Administradores

    List<Pagamento> listarPagamentosPendentes(); // Verificar os pagamentos

}
