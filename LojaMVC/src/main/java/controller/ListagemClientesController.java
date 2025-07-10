package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;

public class ListagemClientesController {

    Stage stageListagem;
    Cliente cliente;
    ObservableList<Cliente> lista;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnFechar;

    @FXML
    private TableView<Cliente> tabelaUsuarios;

    @FXML
    private TextField txtPesquisar;

    @FXML
    void btnCadastrarClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/CadastroClientes.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaCadastro = new Stage();
        CadastroClientesController cadc = loader.getController();
        cadc.setStage(telaCadastro);

        telaCadastro.setOnShown(e -> cadc.ajustarElementosJanela(null));
        cadc.setOnClienteSalvo(() -> {
            try {
                carregarTabela();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        telaCadastro.setTitle("Cadastro de Clientes");
        telaCadastro.setScene(new Scene(root));
        telaCadastro.show();
    }

    @FXML
    void btnFecharClick(ActionEvent event) {
        stageListagem.close();
    }

    void setStage(Stage stage) {
        this.stageListagem = stage;
    }

    void ajustarElementosJanela() throws SQLException {
        carregarTabela();
    }

    private void carregarTabela() throws SQLException {
        lista = FXCollections.observableArrayList(new ClienteDAO().selecionarClientes());
        if (!lista.isEmpty()) {
            tabelaUsuarios.getColumns().clear();

            TableColumn<Cliente, Number> colId = new TableColumn<>("ID");
            colId.setCellValueFactory(c -> c.getValue().idProperty());
            colId.setPrefWidth(50);

            TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
            colNome.setCellValueFactory(c -> c.getValue().nomeProperty());

            TableColumn<Cliente, String> colFone = new TableColumn<>("Telefone");
            colFone.setCellValueFactory(c -> c.getValue().telefoneProperty());

            TableColumn<Cliente, String> colEndereco = new TableColumn<>("Endereço");
            colEndereco.setCellValueFactory(c -> c.getValue().enderecoProperty());

            TableColumn<Cliente, LocalDate> colDataNasc = new TableColumn<>("Nascimento");
            colDataNasc.setCellValueFactory(c -> c.getValue().dataNascimentoProperty());

            // Formatar data para dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            colDataNasc.setCellFactory(column -> new TableCell<Cliente, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.format(formatter));
                    }
                }
            });

            tabelaUsuarios.getColumns().addAll(colId, colNome, colFone, colEndereco, colDataNasc);

            FilteredList<Cliente> filtrada = new FilteredList<>(lista, p -> true);

            txtPesquisar.textProperty().addListener((obs, oldVal, newVal) -> {
                filtrada.setPredicate(cliente -> {
                    if (newVal == null || newVal.isEmpty()) return true;
                    String filtro = newVal.toLowerCase();
                    return cliente.getNome().toLowerCase().contains(filtro)
                        || cliente.getTelefone().toLowerCase().contains(filtro)
                        || cliente.getEndereco().toLowerCase().contains(filtro)
                        || (cliente.getDataNascimento() != null
                            && cliente.getDataNascimento().toLocalDate().format(formatter).contains(filtro));
                });
            });

            SortedList<Cliente> ordenada = new SortedList<>(filtrada);
            ordenada.comparatorProperty().bind(tabelaUsuarios.comparatorProperty());
            tabelaUsuarios.setItems(ordenada);
        } else {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar clientes");
        }
    }

    @FXML
    void TableViewClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 1) {
            this.cliente = tabelaUsuarios.getSelectionModel().getSelectedItem();
            if (cliente != null) {
                URL url = new File("src/main/java/view/CadastroClientes.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();

                Stage telaCadastro = new Stage();
                CadastroClientesController cadc = loader.getController();
                cadc.setStage(telaCadastro);
                telaCadastro.setOnShown(e -> cadc.ajustarElementosJanela(cliente));
                cadc.setOnClienteSalvo(() -> {
                    try {
                        carregarTabela();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });

                telaCadastro.setTitle("Cadastro de Clientes");
                telaCadastro.setScene(new Scene(root));
                telaCadastro.show();
            }
        }
    }
}
