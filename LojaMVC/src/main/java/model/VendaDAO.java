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
}
