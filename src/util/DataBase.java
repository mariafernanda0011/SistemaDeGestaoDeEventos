package util;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* 
 * @author Maria Fernanda S. :)
 */
public class DataBase {

    private static final String URL = "jdbc:sqlite:database/bd_eventos.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // Tabela de administradores
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS administradores (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome_completo TEXT NOT NULL,
                    cpf TEXT NOT NULL UNIQUE,
                    nome_usuario TEXT NOT NULL UNIQUE,
                    senha TEXT NOT NULL
                )
                """);

            // Tabela de participantes
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS participantes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome_completo TEXT NOT NULL,
                    cpf TEXT NOT NULL UNIQUE,
                    nome_usuario TEXT NOT NULL UNIQUE,
                    tipo TEXT NOT NULL,
                    senha TEXT NOT NULL
                         
                )
                """);

            // Tabela de eventos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS eventos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL UNIQUE,
                    duracao TEXT,
                    data TEXT,
                    hora_inicio INTEGER,
                    limite_vagas INTEGER
                )
                """);

            // Tabela de atividades
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS atividades (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    descricao TEXT NOT NULL,
                    ministrante TEXT NOT NULL,
                    data_realizacao INTEGER,
                    limite_vagas INTEGER,
                    tipo TEXT NOT NULL,
                    evento_id INTEGER NOT NULL,
                    FOREIGN KEY(evento_id) REFERENCES eventos(id) ON DELETE CASCADE
                )
                """);

            // Tabela de inscrições
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS inscricoes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    participante_id INTEGER NOT NULL,
                    evento_id INTEGER NOT NULL,
                    atividade_id INTEGER,
                    FOREIGN KEY(participante_id) REFERENCES participantes(id) ON DELETE CASCADE,
                    FOREIGN KEY(evento_id) REFERENCES eventos(id) ON DELETE CASCADE,
                    FOREIGN KEY(atividade_id) REFERENCES atividades(id) ON DELETE SET NULL
                )
                """);

            // Tabela de pagamentos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS pagamentos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    participante_id INTEGER NOT NULL,
                    evento_id INTEGER NOT NULL,
                    status INTEGER NOT NULL CHECK(status IN (0,1)),
                    FOREIGN KEY(participante_id) REFERENCES participantes(id) ON DELETE CASCADE,
                    FOREIGN KEY(evento_id) REFERENCES eventos(id) ON DELETE CASCADE
                )
                """);

            // Tabela de valores de inscrição por tipo de participante
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS valores_inscricao (
                    tipo TEXT PRIMARY KEY,
                    valor REAL NOT NULL
                )
                """);

            System.out.println("Banco de dados inicializado com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
        }
    }

}
