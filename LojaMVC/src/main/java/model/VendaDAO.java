package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendaDAO {

    public void listarVendas() {
        String sql = "SELECT * FROM venda";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Date dataCompra = rs.getDate("Data_compra");
                long valorTotal = rs.getLong("Valor_total");
                String clienteId = rs.getString("Cliente_id");

                System.out.println("ID: " + id + " | Data da Compra: " + dataCompra + 
                        " | Valor Total: " + valorTotal + " | Cliente ID: " + clienteId);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        }
    }
}