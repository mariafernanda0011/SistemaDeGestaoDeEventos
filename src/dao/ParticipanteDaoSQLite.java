package dao;

import java.sql.*;
import model.*;
import util.DataBase;

/*
 * @author Maria Fernanda S. :) 
 */
public class ParticipanteDaoSQLite implements ParticipanteDao {

    @Override
    public boolean autenticar(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM participantes WHERE nome_usuario = ? AND senha = ?";

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao autenticar : " + ex.getMessage());
        }
    }

    @Override
    public void cadastrarParticipante(Participante participante) {
        String sql = "INSERT INTO participantes (nome_completo, cpf, nome_usuario, senha, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, participante.getNomeCompleto());
            stmt.setString(2, participante.getCpf());
            stmt.setString(3, participante.getNomeDeUsuario());
            stmt.setString(4, participante.getSenha());
            stmt.setString(5, participante.getTipo().toString());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao cadastrar participante: " + ex.getMessage());
        }
    }

    @Override
    public Participante buscarUsuario(String nomeDeUsuario) {
        String sql = "SELECT * FROM participantes WHERE nome_usuario = ?";

        try (Connection conn = DataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeDeUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeCompleto = rs.getString("nome_completo");
                String cpf = rs.getString("cpf");
                String senha = rs.getString("senha");
                TipoDeParticipante tipo = TipoDeParticipante.valueOf(rs.getString("tipo"));

                return new Participante(nomeCompleto, cpf, nomeDeUsuario, senha, tipo);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro: " + ex.getMessage());
        }
        return null;
    }

}
