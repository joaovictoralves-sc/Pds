package controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Venda;
import model.VendaDAO;
import util.AlertaUtil;

public class TelaListagemVendasController {

    private Stage stageListagem;
    private ObservableList<Venda> lista;

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnInfo;

    @FXML
    private Label lblListagemVendas;

    @FXML
    private TableView<Venda> tabelaVendas;

    @FXML
    private TextField txtPesquisa;

    @FXML
    void TableViewClick(MouseEvent event) {

    }

    @FXML
    void btnFecharClick(ActionEvent event) {
        stageListagem.close();
    }

    @FXML
    void btnInfoClick(ActionEvent event) {

    }

    public void setStage(Stage stage) {
        this.stageListagem = stage;
    }

    public void ajustarElementosJanela() throws SQLException {
        carregarTabela();
    }

    private void carregarTabela() throws SQLException {
        lista = FXCollections.observableArrayList(new VendaDAO().selecionarVendas());

        if (!lista.isEmpty()) {
            tabelaVendas.getColumns().clear();

            TableColumn<Venda, Number> colId = new TableColumn<>("ID");
            colId.setCellValueFactory(v -> v.getValue().idProperty());

            TableColumn<Venda, java.sql.Date> colData = new TableColumn<>("Data Compra");
            colData.setCellValueFactory(v -> v.getValue().dataCompraProperty());

            TableColumn<Venda, Number> colValor = new TableColumn<>("Valor Total");
            colValor.setCellValueFactory(v -> v.getValue().valorTotalProperty());

            TableColumn<Venda, Number> colCliente = new TableColumn<>("Cliente ID");
            colCliente.setCellValueFactory(v -> v.getValue().clienteIdProperty());

            tabelaVendas.getColumns().addAll(colId, colData, colValor, colCliente);

            FilteredList<Venda> filtrada = new FilteredList<>(lista, p -> true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            txtPesquisa.textProperty().addListener((obs, oldVal, newVal) -> {
                filtrada.setPredicate(venda -> {
                    if (newVal == null || newVal.isEmpty()) return true;
                    String filtro = newVal.toLowerCase();
                    return String.valueOf(venda.getId()).contains(filtro)
                        || (venda.getDataCompra() != null && venda.getDataCompra().toLocalDate().format(dtf).contains(filtro))
                        || String.valueOf(venda.getClienteId()).contains(filtro);
                });
            });

            SortedList<Venda> ordenada = new SortedList<>(filtrada);
            ordenada.comparatorProperty().bind(tabelaVendas.comparatorProperty());
            tabelaVendas.setItems(ordenada);
        } else {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar vendas");
        }
    }
}
