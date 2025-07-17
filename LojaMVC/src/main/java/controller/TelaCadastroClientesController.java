package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Cliente;
import model.ClienteDAO;
import util.AlertaUtil;
import util.LimitarCaracter;

public class TelaCadastroClientesController {
    
    Stage stageCadastro;
    Cliente clienteSelecionado;
    private Runnable onClienteSalvo;

    LimitarCaracter limitarCaracter = new LimitarCaracter();

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnIncluirAlterar;

    @FXML
    private DatePicker dtDataNascimento;

    @FXML
    private Label lblCadastroClientes;

    @FXML
    private Label lblDataNascimento;

    @FXML
    private Label lblEndereco;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblTelefone;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    void btnExcluirClick(ActionEvent event) throws SQLException {
        Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Atenção", "Tem certeza que deseja excluir este cliente?");
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            excluir(clienteSelecionado.getId());
        }
    }

    @FXML
    void btnFecharClick(ActionEvent event) {
        stageCadastro.close();
    }

    @FXML
    void btnIncluirAlterarClick(ActionEvent event) throws SQLException {
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String endereco = txtEndereco.getText();
        LocalDate nascimento = dtDataNascimento.getValue();

        if (nome.isEmpty() || telefone.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Os campos Nome e Telefone são obrigatórios.");
            return;
        }

        if (limitarCaracter.LimitaFormatoTelefone(telefone)) {
            return;
        }

        Date dataNascimento = (nascimento != null) ? Date.valueOf(nascimento) : null;

        if (clienteSelecionado == null) {
            incluir(nome, telefone, endereco, dataNascimento);
        } else {
            alterar(clienteSelecionado.getId(), nome, telefone, endereco, dataNascimento);
        }
    }

    void setStage(Stage stageCadastro) {
        this.stageCadastro = stageCadastro;
    }

    void ajustarElementosJanela(Cliente cliente) {
        this.clienteSelecionado = cliente;
        if (cliente == null) {
            txtNome.requestFocus();
            btnExcluir.setVisible(false);
            btnIncluirAlterar.setText("Salvar");
        } else {
            btnIncluirAlterar.setText("Editar");
            txtNome.setText(cliente.getNome());
            txtTelefone.setText(cliente.getTelefone());
            txtEndereco.setText(cliente.getEndereco());
            if (cliente.getDataNascimento() != null) {
                dtDataNascimento.setValue(cliente.getDataNascimento().toLocalDate());
            }
        }
    }

    void incluir(String nome, String telefone, String endereco, Date nascimento) throws SQLException {
        Cliente cliente = new Cliente(nome, telefone, endereco, nascimento);
        new ClienteDAO().salvar(cliente);
        if (onClienteSalvo != null) onClienteSalvo.run();
        AlertaUtil.mostrarInformacao("Informação", "Cliente cadastrado com sucesso!");
        stageCadastro.close();
    }

    void alterar(int id, String nome, String telefone, String endereco, Date nascimento) throws SQLException {
        Cliente cliente = new Cliente(id, nome, telefone, endereco, nascimento);
        new ClienteDAO().alterar(cliente);
        if (onClienteSalvo != null) onClienteSalvo.run();
        AlertaUtil.mostrarInformacao("Informação", "Cliente alterado com sucesso!");
        stageCadastro.close();
    }

    public void setOnClienteSalvo(Runnable callback) {
        this.onClienteSalvo = callback;
    }

    public void excluir(int id) throws SQLException {
        new ClienteDAO().excluir(id);
        if (onClienteSalvo != null) onClienteSalvo.run();
        AlertaUtil.mostrarInformacao("Informação", "Cliente excluído com sucesso!");
        stageCadastro.close();
    }
}
