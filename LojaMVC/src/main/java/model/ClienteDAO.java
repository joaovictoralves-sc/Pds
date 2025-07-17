package model;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteDAO extends GenericDAO {

    public void salvar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes(nome, telefone, endereco, data_nascimento) VALUES (?, ?, ?, ?)";
        save(sql, cliente.getNome(), cliente.getTelefone(), cliente.getEndereco(), cliente.getDataNascimento());
    }

    public void alterar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, endereco = ?, data_nascimento = ? WHERE id = ?";
        update(sql, cliente.getNome(), cliente.getTelefone(), cliente.getEndereco(), cliente.getDataNascimento(), cliente.getId());
    }

    public void excluir(long id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        delete(sql, id);
    }

    public ObservableList<Cliente> selecionarClientes() throws SQLException {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM clientes";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Cliente c = new Cliente();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setTelefone(rs.getString("telefone"));
            c.setEndereco(rs.getString("endereco"));
            c.setDataNascimento(rs.getDate("data_nascimento"));
            lista.add(c);
        }

        rs.close();
        ps.close();
        conectarDAO().close();
        return lista;
    }

    public Cliente selecionarPorId(int id) throws SQLException {
        Cliente c = new Cliente();
        String sql = "SELECT * FROM clientes WHERE id = ?";
        PreparedStatement ps = conectarDAO().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setTelefone(rs.getString("telefone"));
            c.setEndereco(rs.getString("endereco"));
            c.setDataNascimento(rs.getDate("data_nascimento"));
        }

        rs.close();
        ps.close();
        conectarDAO().close();
        return c;
    }
}
