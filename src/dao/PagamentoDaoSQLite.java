package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Pagamento;
import util.DataBase;

/*
 * @author Maria Fernanda S. :) 
 */

public class PagamentoDaoSQLite implements PagamentoDao {

    @Override
    public void realizarPagamento(int participanteId, int eventoId) {
        String sql = "INSERT INTO pagamentos (participante_id, evento_id, status) VALUES (?, ?, 0)";

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);

            int linhas = stmt.executeUpdate();

            if (linhas < 0) {
                System.out.println("Falha ao registrar o pagamento.");
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao registrar pagamento: " + ex.getMessage());
        }
    }

    @Override
    public void confirmarPagamento(int idPagamento) {

        String sql = "UPDATE pagamentos SET status = 1 WHERE id = ?";

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPagamento);
            int linhasEncontradas = stmt.executeUpdate();

            if (linhasEncontradas < 0) {
                System.out.println("Pagamento com ID " + idPagamento + " não encontrado.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao confirmar pagamento: " + ex.getMessage());
        }
    }

    @Override
    public List<Pagamento> listarPagamentosPendentes() {
        String sql = """
                             SELECT pg.id, pg.status, ev.nome AS nome_evento, part.nome AS nome_participante
                FROM pagamentos pg
                JOIN eventos ev ON pg.evento_id = ev.id
                JOIN participantes part ON pg.participante_id = part.id
                WHERE pg.status = 0
                """;
        List<Pagamento> pagamentosPendentes = new ArrayList<>();

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getInt("id"));
                pagamento.setStatus(false);
                pagamentosPendentes.add(pagamento);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro: " + ex.getMessage());
        }
        return pagamentosPendentes;
    }

}
