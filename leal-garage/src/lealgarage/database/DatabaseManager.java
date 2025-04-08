// DatabaseManager.java
package lealgarage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:lealgarage.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC"); // força o carregamento do driver
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do SQLite não encontrado!");
            e.printStackTrace();
        }
        return DriverManager.getConnection(DATABASE_URL);
    }

    public static void inicializarBancoDeDados() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String createClientesTable = "CREATE TABLE IF NOT EXISTS clientes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "telefone TEXT NOT NULL," +
                    "veiculo TEXT NOT NULL," +
                    "placa TEXT NOT NULL)";
            stmt.execute(createClientesTable);

            String createServicosTable = "CREATE TABLE IF NOT EXISTS servicos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "descricao TEXT NOT NULL," +
                    "valor REAL NOT NULL)";
            stmt.execute(createServicosTable);

            String createOrcamentosTable = "CREATE TABLE IF NOT EXISTS orcamentos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "cliente_id INTEGER NOT NULL," +
                    "data TEXT NOT NULL," +
                    "valor_total REAL NOT NULL," +
                    "FOREIGN KEY (cliente_id) REFERENCES clientes (id))";
            stmt.execute(createOrcamentosTable);

            String createOrcamentoServicosTable = "CREATE TABLE IF NOT EXISTS orcamento_servicos (" +
                    "orcamento_id INTEGER NOT NULL," +
                    "servico_id INTEGER NOT NULL," +
                    "PRIMARY KEY (orcamento_id, servico_id)," +
                    "FOREIGN KEY (orcamento_id) REFERENCES orcamentos (id)," +
                    "FOREIGN KEY (servico_id) REFERENCES servicos (id))";
            stmt.execute(createOrcamentoServicosTable);

            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
