package model;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemVendaDAO extends GenericDAO {

    public void salvar(ItemVenda item) throws SQLException {
        String sql = "INSERT INTO item_venda(venda_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        save(sql, item.getVendaId(), item.getProdutoId(), item.getQuantidade(), item.getPrecoUnitario());
    }

    public void excluirPorVenda(int vendaId) throws SQLException {
        String sql = "DELETE FROM item_venda WHERE venda_id = ?";
        delete(sql, vendaId);
    }

    public ObservableList<ItemVenda> selecionarPorVenda(int vendaId) throws SQLException {
        ObservableList<ItemVenda> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item_venda WHERE venda_id = ?";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ps.setInt(1, vendaId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ItemVenda item = new ItemVenda();
            item.setId(rs.getInt("id"));
            item.setVendaId(rs.getInt("venda_id"));
            item.setProdutoId(rs.getInt("produto_id"));
            item.setQuantidade(rs.getInt("quantidade"));
            item.setPrecoUnitario(rs.getDouble("preco_unitario"));
            lista.add(item);
        }

        rs.close();
        ps.close();
        conectarDAO().close();
        return lista;
    }
}
