package controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Produto;
import model.ProdutoDAO;
import util.AlertaUtil;

public class TelaCadastroProdutosController {
    
    Stage stageCadastroProdutos;
    Produto produtoSelecionado;
    private Runnable onProdutoSalvo;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnIncluirAlterar;

    @FXML
    private Label lblCadastroProdutos;

    @FXML
    private Label lblDescricao;

    @FXML
    private Label lblQuantidade;

    @FXML
    private Label lblValor;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtValor;

    @FXML
    void btnExcluirClick(ActionEvent event) throws SQLException {
        Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Atenção",
                "Tem certeza que quer excluir o produto?");
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            excluir(produtoSelecionado.getId());
        }
    }
    
    @FXML
    void btnFecharClick(ActionEvent event) {
        stageCadastroProdutos.close();
    }

    @FXML
    void btnIncluirAlterarClick(ActionEvent event) throws SQLException {
        String descricao = txtDescricao.getText();
        String valorTexto = txtValor.getText();
        String estoqueTexto = txtQuantidade.getText();

        if (descricao.isEmpty() || valorTexto.isEmpty() || estoqueTexto.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        BigDecimal valor;
        int estoque;

        try {
            valor = new BigDecimal(valorTexto.replace(",", "."));
            estoque = Integer.parseInt(estoqueTexto);
        } catch (NumberFormatException ex) {
            AlertaUtil.mostrarErro("Erro", "Valor ou estoque inválido.");
            return;
        }

        if (produtoSelecionado == null) {
            incluir(descricao, valor, estoque);
        } else {
            alterar(produtoSelecionado.getId(), descricao, valor, estoque);
        }
    }

    public void setStage(Stage stage) {
        this.stageCadastroProdutos = stage;
    }

    public void ajustarElementosJanela(Produto produto) {
        this.produtoSelecionado = produto;
        if (produto == null) {
            txtDescricao.requestFocus();
            btnExcluir.setVisible(false);
            btnIncluirAlterar.setText("Salvar");
        } else {
            btnIncluirAlterar.setText("Editar");
            txtDescricao.setText(produto.getDescricao());
            txtValor.setText(produto.getValor().toString());
            txtQuantidade.setText(String.valueOf(produto.getQuantidadeEstoque()));
        }
    }

    void incluir(String descricao, BigDecimal valor, int estoque) throws SQLException {
        Produto produto = new Produto(descricao, valor, estoque);
        new ProdutoDAO().salvar(produto);
        if (onProdutoSalvo != null) onProdutoSalvo.run();
        AlertaUtil.mostrarInformacao("Sucesso", "Produto cadastrado!");
        stageCadastroProdutos.close();
    }

    void alterar(int id, String descricao, BigDecimal valor, int estoque) throws SQLException {
        Produto produto = new Produto(id, descricao, valor, estoque);
        new ProdutoDAO().alterar(produto);
        if (onProdutoSalvo != null) onProdutoSalvo.run();
        AlertaUtil.mostrarInformacao("Sucesso", "Produto alterado!");
        stageCadastroProdutos.close();
    }

    public void setOnProdutoSalvo(Runnable callback) {
        this.onProdutoSalvo = callback;
    }

    void excluir(int id) throws SQLException {
        new ProdutoDAO().excluir(id);
        if (onProdutoSalvo != null) onProdutoSalvo.run();
        AlertaUtil.mostrarInformacao("Sucesso", "Produto excluído!");
        stageCadastroProdutos.close();
    }
}
