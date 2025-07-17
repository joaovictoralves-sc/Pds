package model;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VendaDAO extends GenericDAO {

    public ObservableList<Venda> selecionarVendas() throws SQLException {
        ObservableList<Venda> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM venda";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Venda v = new Venda();
            v.setId(rs.getInt("id"));
            v.setDataCompra(rs.getDate("data_compra"));
            v.setValorTotal(rs.getDouble("valor_total"));
            v.setClienteId(rs.getInt("cliente_id"));
            lista.add(v);
        }

        rs.close();
        ps.close();
        conectarDAO().close();
        return lista;
    }

    public void salvar(Venda venda) throws SQLException {
        String sql = "INSERT INTO venda (data_compra, valor_total, cliente_id) VALUES (?, ?, ?)";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ps.setDate(1, venda.getDataCompra());
        ps.setDouble(2, venda.getValorTotal());
        ps.setInt(3, venda.getClienteId());
        ps.executeUpdate();
        ps.close();
        conectarDAO().close();
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM venda WHERE id = ?";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        conectarDAO().close();
    }
    
    public int salvarRetornandoId(Venda venda) throws SQLException {
    String sql = "INSERT INTO venda (data_compra, valor_total, cliente_id) VALUES (?, ?, ?)";
    try (Connection conn = conectarDAO();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setDate(1, venda.getDataCompra());
        stmt.setDouble(2, venda.getValorTotal());
        stmt.setInt(3, venda.getClienteId());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("Falha ao obter o ID da nova venda.");
        }
    }
  }
}
