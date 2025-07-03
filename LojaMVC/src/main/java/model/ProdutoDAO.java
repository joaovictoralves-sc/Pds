package model;

import dal.ConexaoBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    public void inserirProduto(Produto produto) {
        String sql = "INSERT INTO produto (descricao, valor, quantidade_estoque) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setLong(2, produto.getValor());
            stmt.setInt(3, produto.getQuantidade_estoque());

            stmt.executeUpdate();
            System.out.println("Produto inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
        }
    }

    public void listarProdutos() {
        String sql = "SELECT * FROM produto";

        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String descricao = rs.getString("descricao");
                long valor = rs.getLong("valor");
                int quantidadeEstoque = rs.getInt("quantidade_estoque");

                System.out.println("ID: " + id + " | Descrição: " + descricao +
                        " | Valor: " + valor + " | Quantidade em Estoque: " + quantidadeEstoque);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
    }
}
