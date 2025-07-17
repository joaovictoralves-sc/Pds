package model;

import java.math.BigDecimal;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoDAO extends GenericDAO {

    public void salvar(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto(descricao, valor, quantidade_estoque) VALUES (?, ?, ?)";
        save(sql, produto.getDescricao(), produto.getValor(), produto.getQuantidadeEstoque());
    }

    public void alterar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET descricao = ?, valor = ?, quantidade_estoque = ? WHERE id = ?";
        update(sql, produto.getId(), produto.getDescricao(), produto.getValor(), produto.getQuantidadeEstoque());
    }

    public void excluir(long id) throws SQLException {
        String sql = "DELETE FROM produto WHERE id = ?";
        delete(sql, id);
    }

    public ObservableList<Produto> selecionarProdutos() throws SQLException {
        ObservableList<Produto> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM produto";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Produto p = new Produto(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getBigDecimal("valor"),
                rs.getInt("quantidade_estoque")
            );
            lista.add(p);
        }

        rs.close();
        ps.close();
        conectarDAO().close();
        return lista;
    }

    public Produto selecionarPorId(int id) throws SQLException {
        Produto p = new Produto();
        String sql = "SELECT * FROM produto WHERE id = ?";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            p.setId(rs.getInt("id"));
            p.setDescricao(rs.getString("descricao"));
            p.setValor(rs.getBigDecimal("valor"));
            p.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
        }

        rs.close();
        ps.close();
        conectarDAO().close();
        return p;
    }
}
