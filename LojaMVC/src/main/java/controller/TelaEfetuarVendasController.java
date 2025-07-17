package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import model.Produto;
import model.Venda;
import model.VendaDAO;
import util.AlertaUtil;

public class TelaEfetuarVendasController {

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnIncluirAlterar;

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private DatePicker dtDataCompra;

    @FXML
    private Label lblCliente;

    @FXML
    private Label lblDataCompra;

    @FXML
    private Label lblEfetuarVendas;

    @FXML
    private Label lblValor;

    @FXML
    private TextField txtValor;

    private Venda vendaSelecionada;
    private Runnable onVendaSalva; 
    private Stage stageVenda;
    private Produto produtoSelecionado;

    @FXML
    public void initialize() {
        carregarClientes();
        dtDataCompra.setValue(LocalDate.now()); 
    }

    private void carregarClientes() {
        try {
            List<Cliente> clientes = new ClienteDAO().selecionarClientes();
            ObservableList<Cliente> lista = FXCollections.observableArrayList(clientes);
            cmbCliente.setItems(lista);
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro", "Erro ao carregar clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnFecharClick(ActionEvent event) {
        if (stageVenda != null) {
            stageVenda.close();
        }
    }

    @FXML
    void btnIncluirAlterarClick(ActionEvent event) {
        Cliente clienteSelecionado = cmbCliente.getValue();
        LocalDate dataLocal = dtDataCompra.getValue();
        String valorTexto = txtValor.getText();

        if (clienteSelecionado == null || dataLocal == null || valorTexto.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            double valorTotal = Double.parseDouble(valorTexto.replace(",", "."));
            Date dataCompra = Date.valueOf(dataLocal);

            Venda novaVenda = new Venda(dataCompra, valorTotal, clienteSelecionado.getId());
            int vendaId = new VendaDAO().salvarRetornandoId(novaVenda);

            // Salva o item da venda se produto selecionado
            if (produtoSelecionado != null) {
                model.ItemVenda item = new model.ItemVenda(
                    vendaId,
                    produtoSelecionado.getId(),
                    1,
                    produtoSelecionado.getValor().doubleValue()
                );
                new model.ItemVendaDAO().salvar(item);
            }

            AlertaUtil.mostrarInformacao("Sucesso", "Venda registrada com sucesso!");

            if (onVendaSalva != null) onVendaSalva.run();

            if (stageVenda != null) stageVenda.close();

        } catch (NumberFormatException e) {
            AlertaUtil.mostrarErro("Erro", "Valor inválido.");
        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro", "Erro ao registrar venda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnExcluirClick(ActionEvent event) {
        if (vendaSelecionada == null) {
            AlertaUtil.mostrarErro("Erro", "Nenhuma venda selecionada.");
            return;
        }

        try {
            new model.ItemVendaDAO().excluirPorVenda(vendaSelecionada.getId());
            new VendaDAO().excluir(vendaSelecionada.getId());

            AlertaUtil.mostrarInformacao("Sucesso", "Venda excluída com sucesso!");

            if (onVendaSalva != null) onVendaSalva.run();

            if (stageVenda != null) stageVenda.close();

        } catch (SQLException e) {
            AlertaUtil.mostrarErro("Erro", "Erro ao excluir venda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setVenda(Venda venda) {
        this.vendaSelecionada = venda;

        if (venda != null) {
            dtDataCompra.setValue(venda.getDataCompra().toLocalDate());
            txtValor.setText(String.valueOf(venda.getValorTotal()));

            try {
                List<Cliente> clientes = new ClienteDAO().selecionarClientes();
                for (Cliente c : clientes) {
                    if (c.getId() == venda.getClienteId()) {
                        cmbCliente.setValue(c);
                        break;
                    }
                }
                cmbCliente.setItems(FXCollections.observableArrayList(clientes));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            btnIncluirAlterar.setText("Editar");
            btnExcluir.setVisible(true);
        } else {
            btnExcluir.setVisible(false);
            btnIncluirAlterar.setText("Salvar");
        }
    }

    public void setStage(Stage stage) {
        this.stageVenda = stage;
    }

    public void setOnVendaSalva(Runnable callback) {
        this.onVendaSalva = callback;
    }

    public void setProduto(Produto produto) {
        this.produtoSelecionado = produto;
        if (produto != null) {
            txtValor.setText(String.valueOf(produto.getValor()));
        }
    }
}
