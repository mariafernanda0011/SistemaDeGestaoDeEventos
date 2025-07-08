package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Participante;
import model.TipoDeParticipante;
import util.DataBase;
/*
 * @author Maria Fernanda S. :) 
 */

public class InscricaoDaoSQLite implements InscricaoDao {

    @Override
    public void realizarInscricaoEmEvento(int participanteId, int eventoId) {
        String sql = "INSERT INTO inscricoes (participante_id, evento_id) VALUES (?, ?)";

        try (Connection conn = DataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro: " + ex.getMessage());
        }
    }

    @Override
    public void realizarInscricaoEmAtividade(int participanteId, int eventoId, int atividadeId) {

        String sql = "UPDATE inscricoes SET atividade_id = ? WHERE participante_id = ? AND evento_id = ?";

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, atividadeId);
            stmt.setInt(2, participanteId);
            stmt.setInt(3, eventoId);

            int linhas = stmt.executeUpdate();

            if (linhas < 0) {
                System.out.println("Inscrição no evento não encontrada para associar a atividade.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inscrever em atividade: " + ex.getMessage());
        }
    }

    @Override
    public void excluirInscricao(int participanteId, int eventoId) {
        String sql = "DELETE FROM inscricoes WHERE participante_id = ? AND evento_id = ?";

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);
            int linhasEncontradas = stmt.executeUpdate();

            if (linhasEncontradas < 0) {
                System.out.println("Nenhuma inscrição encontrada.");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Participante> listarPorEvento(int eventoId) {
        String sql = """
                    SELECT p.id, p.nome_completo, p.cpf, p.tipo
                    FROM inscricoes i
                    JOIN participantes p ON p.id = i.participante_id
                    WHERE i.evento_id = ?
                """;
        List<Participante> participantes = new ArrayList<>();

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Participante participante = new Participante(
                        rs.getInt("id"),
                        rs.getString("nome_completo"),
                        rs.getString("cpf"),
                        TipoDeParticipante.valueOf(rs.getString("tipo"))
                );
                participantes.add(participante);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar participantes: " + ex.getMessage());
        }
        return participantes;
    }

    @Override
    public List<Participante> listarPorAtividade(int atividadeId) {
        String sql = """
                    SELECT p.id, p.nome_completo, p.cpf, p.tipo
                    FROM inscricoes i
                    JOIN participantes p ON p.id = i.participante_id
                    WHERE i.atividade_id = ?
                """;
        List<Participante> participantes = new ArrayList<>();

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, atividadeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Participante participante = new Participante(
                        rs.getInt("id"),
                        rs.getString("nome_completo"),
                        rs.getString("cpf"),
                        TipoDeParticipante.valueOf(rs.getString("tipo"))
                );                
                participantes.add(participante);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar participantes por atividade: " + ex.getMessage());
        }
        return participantes;
    }

    @Override
    public void exibirInscricoesComStatus(int participanteId) {
        String sql = "SELECT e.nome AS evento_nome, a.descricao AS atividade_desc, p.status " +
                "FROM inscricoes i " +
                "JOIN eventos e ON i.evento_id = e.id " +
                "LEFT JOIN atividades a ON i.atividade_id = a.id " +
                "LEFT JOIN pagamentos p ON p.evento_id = e.id AND p.participante_id = i.participante_id " +
                "WHERE i.participante_id = ?";

        try (Connection conn = DataBase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, participanteId);
            ResultSet rs = stmt.executeQuery();

            boolean encontrou = false;

            System.out.println("[ Minhas Inscrições ]");
            while (rs.next()) {
                encontrou = true;
                String evento = rs.getString("evento_nome");
                String atividade = rs.getString("atividade_desc");
                boolean pagamento = rs.getBoolean("status");
                String status = pagamento ? "Confirmado" : "Pendente";

                System.out.println("Evento: " + evento);
                if (atividade != null) {
                    System.out.println("Atividade: " + atividade);
                }
                System.out.println("Pagamento: " + status);
                System.out.println("----------------------");
            }

            if (!encontrou) {
                System.out.println("Você ainda não possui inscrições.");
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao exibir inscrições: " + ex.getMessage());
        }
    }
}
