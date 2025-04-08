package lealgarage.services;

import lealgarage.models.Servico;
import lealgarage.database.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoService {

    public boolean cadastrarServico(Servico servico) {
        String sql = "INSERT INTO servicos (nome, descricao, valor) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, servico.getNome());
            pstmt.setString(2, servico.getDescricao());
            pstmt.setDouble(3, servico.getValor());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar serviço: " + e.getMessage());
            return false;
        }
    }

    public List<Servico> listarTodosServicos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Servico s = new Servico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("valor")
                );
                servicos.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar serviços: " + e.getMessage());
        }

        return servicos;
    }
}
