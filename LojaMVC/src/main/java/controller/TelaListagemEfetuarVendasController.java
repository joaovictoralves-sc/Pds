package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Produto;
import model.ProdutoDAO;
import util.AlertaUtil;

public class TelaListagemEfetuarVendasController {

    private Stage stageListagem;
    private Produto produto;
    private ObservableList<Produto> lista;

    @FXML
    private Button btnEfetuar;

    @FXML
    private Button btnFechar;

    @FXML
    private Label lblListagemProdutos;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TextField txtPesquisa;

    @FXML
    void TableViewClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 1) {  // clique simples para abrir a tela
            Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                abrirTelaEfetuarVenda(selecionado);
            }
        }
    }

    @FXML
    void btnEfetuarClick(ActionEvent event) throws IOException {
        Produto selecionado = tabelaProdutos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            abrirTelaEfetuarVenda(selecionado);
        } else {
            AlertaUtil.mostrarErro("Erro", "Selecione um produto para efetuar a venda.");
        }
    }

    @FXML
    void btnFecharClick(ActionEvent event) {
        stageListagem.close();
    }

    public void setStage(Stage stage) {
        this.stageListagem = stage;
    }

    public void ajustarElementosJanela() throws SQLException {
        carregarTabela();
    }

    private void carregarTabela() throws SQLException {
        lista = FXCollections.observableArrayList(new ProdutoDAO().selecionarProdutos());
        if (!lista.isEmpty()) {
            tabelaProdutos.getColumns().clear();

            TableColumn<Produto, Number> colId = new TableColumn<>("ID");
            colId.setCellValueFactory(p -> p.getValue().idProperty());

            TableColumn<Produto, String> colDescricao = new TableColumn<>("Descrição");
            colDescricao.setCellValueFactory(p -> p.getValue().descricaoProperty());

            TableColumn<Produto, Number> colValor = new TableColumn<>("Valor");
            colValor.setCellValueFactory(p -> p.getValue().valorProperty());

            TableColumn<Produto, Number> colQtd = new TableColumn<>("Estoque");
            colQtd.setCellValueFactory(p -> p.getValue().quantidadeEstoqueProperty());

            tabelaProdutos.getColumns().addAll(colId, colDescricao, colValor, colQtd);

            FilteredList<Produto> filtrada = new FilteredList<>(lista, p -> true);
            txtPesquisa.textProperty().addListener((obs, oldVal, newVal) -> {
                filtrada.setPredicate(prod -> {
                    if (newVal == null || newVal.isEmpty()) return true;
                    String filtro = newVal.toLowerCase();
                    return prod.getDescricao().toLowerCase().contains(filtro)
                            || String.valueOf(prod.getValor()).contains(filtro)
                            || String.valueOf(prod.getQuantidadeEstoque()).contains(filtro);
                });
            });

            SortedList<Produto> ordenada = new SortedList<>(filtrada);
            ordenada.comparatorProperty().bind(tabelaProdutos.comparatorProperty());
            tabelaProdutos.setItems(ordenada);
        } else {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar produtos");
        }
    }

    private void abrirTelaEfetuarVenda(Produto produtoSelecionado) throws IOException {
        URL url = new File("src/main/java/view/TelaEfetuarVendas.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaVenda = new Stage();
        TelaEfetuarVendasController controller = loader.getController();
        controller.setStage(telaVenda);
        controller.setProduto(produtoSelecionado);

        // Callback para atualizar a tabela após salvar a venda (se implementar no controller)
        controller.setOnVendaSalva(() -> {
            try {
                ajustarElementosJanela(); // Recarrega a tabela após venda
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        telaVenda.setTitle("Efetuar Venda");
        telaVenda.setScene(new Scene(root));
        telaVenda.show();
    }
}
