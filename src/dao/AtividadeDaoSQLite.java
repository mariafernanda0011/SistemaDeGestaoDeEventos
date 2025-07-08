package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DataBase;
import model.*;

/* 
 * @author Maria Fernanda S. :)
 */
public class AtividadeDaoSQLite implements AtividadeDao {

    @Override
    public void cadastrarAtividade(Atividade atividade, int idEvento) {
        String sql = "INSERT INTO atividades (descricao, ministrante, data_realizacao, limite_vagas, tipo, evento_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, atividade.getDescricao());
            stmt.setString(2, atividade.getMinistrante());
            stmt.setInt(3, atividade.getDataDeRealizacao());
            stmt.setInt(4, atividade.getLimiteDeVagas());
            stmt.setString(5, atividade.getTipo().toString());
            stmt.setInt(6, idEvento);

            stmt.executeUpdate();
            System.out.println("Atividade cadastrada com sucesso. ");

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao cadastrar atividade: " + ex.getMessage());
        }
    }

    @Override
    public List<Atividade> listarPorEvento(int eventoId) {
        String sql = "SELECT * FROM atividades WHERE evento_id = ?";
        List<Atividade> atividades = new ArrayList<>();

        try (Connection conn = DataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Atividade atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("descricao"),
                        rs.getString("ministrante"),
                        rs.getInt("data_realizacao"),
                        rs.getInt("limite_vagas"),
                        TipoDeAtividade.valueOf(rs.getString("tipo"))
                );
                atividades.add(atividade);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar atividades do evento: " + ex.getMessage());
        }
        return atividades;
    }

}
