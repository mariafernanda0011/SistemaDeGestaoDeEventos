package dao;

import model.*;
import util.*;
import java.sql.*;

/*
 * @author Maria Fernanda S. :) 
 */
public class AdministradorDaoSQLite implements AdministradorDao {

    // Método para autenticação. //
    @Override
    public boolean autenticar(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM administradores WHERE nome_usuario = ? AND senha = ?";

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeUsuario);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) { // Executa o sql //
                if (rs.next()) {
                    return true; // Retorna verdadeiro se "rs" retornar uma linha do banco de dados. //
                } else { // Se não retornar nenhum resultado tem algo errado. //
                    return false; // Retorna false e isso será tratado na camada service //
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao autenticar: " + ex.getMessage());
        }
    }

    // Método para buscar usuários (aux na autenticação). //
    @Override
    public Administrador buscarUsuario(String nomeDeUsuario) {
        String sql = "SELECT * FROM administradores WHERE nome_usuario = ?";

        try (Connection conn = DataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeDeUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nomeCompleto = rs.getString("nome_completo");
                String cpf = rs.getString("cpf");
                String senha = rs.getString("senha");

                return new Administrador(nomeCompleto, cpf, nomeDeUsuario, senha);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro: " + ex.getMessage());
        }
        return null;
    }

    // Buscando valores das inscrições 
    @Override
    public double buscarValorPorTipo(TipoDeParticipante tipo) {
        String sql = "SELECT valor FROM valores_inscricao WHERE tipo = ?";

        try (Connection conn = DataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("valor");
            } else {
                throw new RuntimeException("Tipo de participante não encontrado: " + tipo);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar valor: " + ex.getMessage());
        }
    }

    // Gerenciando os valores de inscrição para cada tipo de participante. //
    @Override
    public void gerenciarValores(double novoValor, TipoDeParticipante tipo) {

        String sql = "UPDATE valores_inscricao SET valor = ? WHERE tipo = ?";

        try (Connection conn = DataBase.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novoValor);
            stmt.setString(2, tipo.toString());

            int linhasEncontradas = stmt.executeUpdate();
            if (linhasEncontradas == 0) {
                throw new RuntimeException("Tipo de participante não encontrado para atualizar.");
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar valor de inscrição: " + ex.getMessage());
        }
    }

    @Override
    public void cadastrarAdministrador(Administrador admin) {
        String sql = """
            INSERT INTO administradores (nome_completo, cpf, nome_usuario, senha)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DataBase.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNomeCompleto());
            stmt.setString(2, admin.getCpf());
            stmt.setString(3, admin.getNomeDeUsuario());
            stmt.setString(4, admin.getSenha());

            stmt.executeUpdate();
            
            System.out.println("Administrador cadastrado com sucesso.");

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao cadastrar administrador: " + ex.getMessage());
        }
    }

}
