package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;
import util.DataBase;

/*
 * @author Maria Fernanda S. :)
 */

public class EventoDaoSQLite implements EventoDao {

    // Criando eventos no Banco de Dados. //
    @Override
    public void criarEvento(Evento evento) {
        String sql = "INSERT INTO eventos (nome, duracao, data, hora_inicio, limite_vagas) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evento.getNomeEvento());
            stmt.setString(2, evento.getDuracao());
            stmt.setString(3, evento.getData());
            stmt.setInt(4, evento.getHora());
            stmt.setInt(5, evento.getLimiteDeVagas());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao criar evento: " + ex.getMessage());
        }
    }

    // Excluindo eventos do Banco de Dados. //
    @Override
    public void excluirEvento(Evento evento) {

        String sql = "DELETE FROM eventos WHERE id = ?";

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, evento.getId());

            int linhasEncontradas = stmt.executeUpdate();

            if (linhasEncontradas == 0) {
                throw new RuntimeException("Evento não encontrado.");
            } 

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao excluir evento: " + ex.getMessage());
        }
    }

    // Método para editar eventos. //
    @Override
    public void editarEvento(Evento evento, String nomeEvento, String novaDuracao, String novaData, int novaHora,
            int novoLimite) {

        String sql = "UPDATE eventos SET nome = ?, duracao = ?, data = ?, hora_inicio = ?, limite_vagas = ? WHERE id = ?";

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeEvento);
            stmt.setString(2, novaDuracao);
            stmt.setString(3, novaData);
            stmt.setInt(4, novaHora);
            stmt.setInt(5, novoLimite);

            stmt.setInt(6, evento.getId());

            int linhasEncontradas = stmt.executeUpdate();

            if (linhasEncontradas == 0) {
                throw new RuntimeException("Evento não encontrado para edição.");
            }
          
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao editar evento: " + ex.getMessage());
        }
    }

    // Método para buscar eventos. //
    @Override
    public Evento buscarEvento(String nomeEvento) {

        String sql = "SELECT * FROM eventos WHERE nome = ?";

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeEvento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String duracao = rs.getString("duracao");
                String data = rs.getString("data");
                int hora = rs.getInt("hora_inicio");
                int limiteVagas = rs.getInt("limite_vagas");

                return new Evento(id, nomeEvento, duracao, data, hora, limiteVagas);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar evento: " + ex.getMessage());
        }
        return null;
    }

    // Método para listar eventos. //
    @Override
    public List<Evento> visualizarEventos() {
        String sql = "SELECT * FROM eventos";
        List<Evento> eventos = new ArrayList<>();

        try (Connection conn = DataBase.getConnection(); // Abrindo a conexão com o Banco de Dados //
                PreparedStatement stmt = conn.prepareStatement(sql); // Preparando o comando //
                ResultSet rs = stmt.executeQuery()) { // Executa o sql e traz os resultados //

            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("duracao"),
                        rs.getString("data"),
                        rs.getInt("hora_inicio"),
                        rs.getInt("limite_vagas"));
                eventos.add(evento);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar eventos: " + ex.getMessage());
        }
        return eventos;
    }

}
