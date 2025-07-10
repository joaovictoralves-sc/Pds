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

public class ListagemProdutosController {

    Stage stageListagem;
    Produto produto;
    ObservableList<Produto> lista;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnFechar;

    @FXML
    private TableView<Produto> tabelaUsuarios;

    @FXML
    private TextField txtPesquisar;

    @FXML
    void btnCadastrarClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/CadastroProdutos.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaCadastro = new Stage();
        CadastroProdutosController cadc = loader.getController();
        cadc.setStage(telaCadastro);

        telaCadastro.setOnShown(e -> cadc.ajustarElementosJanela(null));
        cadc.setOnProdutoSalvo(() -> {
            try { carregarTabela(); } catch (SQLException ex) {}
        });

        telaCadastro.setTitle("Cadastro de Produtos");
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
        lista = FXCollections.observableArrayList(new ProdutoDAO().selecionarProdutos());
        if (!lista.isEmpty()) {
            tabelaUsuarios.getColumns().clear();

            TableColumn<Produto, Number> colId = new TableColumn<>("ID");
            colId.setCellValueFactory(p -> p.getValue().idProperty());

            TableColumn<Produto, String> colDescricao = new TableColumn<>("Descrição");
            colDescricao.setCellValueFactory(p -> p.getValue().descricaoProperty());

            TableColumn<Produto, Number> colValor = new TableColumn<>("Valor");
            colValor.setCellValueFactory(p -> p.getValue().valorProperty());

            TableColumn<Produto, Number> colQtd = new TableColumn<>("Estoque");
            colQtd.setCellValueFactory(p -> p.getValue().quantidadeEstoqueProperty());

            tabelaUsuarios.getColumns().addAll(colId, colDescricao, colValor, colQtd);

            FilteredList<Produto> filtrada = new FilteredList<>(lista, p -> true);
            txtPesquisar.textProperty().addListener((obs, oldVal, newVal) -> {
                filtrada.setPredicate(prod -> {
                    if (newVal == null || newVal.isEmpty()) return true;
                    String filtro = newVal.toLowerCase();
                    return prod.getDescricao().toLowerCase().contains(filtro)
                        || String.valueOf(prod.getValor()).contains(filtro)
                        || String.valueOf(prod.getQuantidadeEstoque()).contains(filtro);
                });
            });

            SortedList<Produto> ordenada = new SortedList<>(filtrada);
            ordenada.comparatorProperty().bind(tabelaUsuarios.comparatorProperty());
            tabelaUsuarios.setItems(ordenada);
        } else {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar produtos");
        }
    }

    @FXML
    void TableViewClick(MouseEvent event) throws IOException {
        if (event.getClickCount() == 1) {
            this.produto = tabelaUsuarios.getSelectionModel().getSelectedItem();
            if (produto != null) {
                URL url = new File("src/main/java/view/CadastroProdutos.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load();

                Stage telaCadastro = new Stage();
                CadastroProdutosController cadc = loader.getController();
                cadc.setStage(telaCadastro);
                telaCadastro.setOnShown(e -> cadc.ajustarElementosJanela(produto));
                cadc.setOnProdutoSalvo(() -> {
                    try { carregarTabela(); } catch (SQLException ex) {}
                });

                telaCadastro.setTitle("Cadastro de Produtos");
                telaCadastro.setScene(new Scene(root));
                telaCadastro.show();
            }
        }
    }
}
