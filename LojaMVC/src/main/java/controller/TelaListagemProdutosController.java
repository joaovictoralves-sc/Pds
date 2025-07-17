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

public class TelaListagemProdutosController {

    private Stage stageListagem;
    private Produto produto;
    private ObservableList<Produto> lista;

    @FXML
    private Button btnCadastrar;

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
        if (event.getClickCount() == 1) {
            this.produto = tabelaProdutos.getSelectionModel().getSelectedItem();
            if (produto != null) {
                abrirCadastroProduto(produto);
            }
        }
    }

    @FXML
    void btnCadastrarClick(ActionEvent event) throws IOException {
        abrirCadastroProduto(null);
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

    private void abrirCadastroProduto(Produto produtoSelecionado) throws IOException {
        URL url = new File("src/main/java/view/TelaCadastroProdutos.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaCadastro = new Stage();
        TelaCadastroProdutosController cadc = loader.getController();
        cadc.setStage(telaCadastro);
        telaCadastro.setOnShown(e -> cadc.ajustarElementosJanela(produtoSelecionado));
        cadc.setOnProdutoSalvo(() -> {
            try { carregarTabela(); } catch (SQLException ex) {}
        });

        telaCadastro.setTitle("Cadastro de Produtos");
        telaCadastro.setScene(new Scene(root));
        telaCadastro.show();
    }
}
