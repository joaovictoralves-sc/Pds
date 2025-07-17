package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.AlertaUtil;

public class TelaPrincipalController {

    private Stage stagePrincipal;

    @FXML
    private Label lblUsuario;

    @FXML
    private Menu menuAjuda;

    @FXML
    private Menu menuCadastro;

    @FXML
    private MenuItem menuCadastroCliente;

    @FXML
    private MenuItem menuCadastroProduto;

    @FXML
    private MenuItem menuCadastroUsuario;

    @FXML
    private MenuItem menuFechar;

    @FXML
    private Menu menuRelatorio;

    @FXML
    private MenuItem menuRelatorioVendas;

    @FXML
    private MenuItem menuSobre;

    @FXML
    void menuCadastroClienteClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/TelaListagemClientes.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage tela = new Stage();
        TelaListagemClientesController controller = loader.getController();
        controller.setStage(tela);
        tela.setOnShown(e -> {
            try {
                controller.ajustarElementosJanela();
            } catch (SQLException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tela.setTitle("Listagem de Clientes");
        tela.setScene(new Scene(root));
        tela.show();
    }

    @FXML
    void menuCadastroProdutoClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/TelaListagemProdutos.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage tela = new Stage();
        TelaListagemProdutosController controller = loader.getController();
        controller.setStage(tela);
        tela.setOnShown(e -> {
            try {
                controller.ajustarElementosJanela();
            } catch (SQLException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tela.setTitle("Listagem de Produtos");
        tela.setScene(new Scene(root));
        tela.show();
    }

    @FXML
    void menuCadastroUsuarioClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/TelaListagemUsuarios.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage tela = new Stage();
        TelaListagemUsuariosController controller = loader.getController();
        controller.setStage(tela);
        tela.setOnShown(e -> {
            try {
                controller.ajustarElementosJanela();
            } catch (SQLException ex) {
                Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        tela.setTitle("Listagem de Usuários");
        tela.setScene(new Scene(root));
        tela.show();
    }

    @FXML
    void menuFecharClick(ActionEvent event) {
        Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Atenção", "Tem certeza que deseja fechar a aplicação?");
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void menuRelatorioVendasClick(ActionEvent event) {
        try {
            URL url = new File("src/main/java/view/TelaListagemEfetuarVendas.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            TelaListagemEfetuarVendasController controller = loader.getController();
            Stage telaRelatorio = new Stage();
            controller.setStage(telaRelatorio);

            telaRelatorio.setOnShown(e -> {
                try {
                    controller.ajustarElementosJanela();
                } catch (SQLException ex) {
                    Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                    AlertaUtil.mostrarErro("Erro", "Erro ao carregar produtos: " + ex.getMessage());
                }
            });

            telaRelatorio.setTitle("Relatório de Produtos / Efetuar Vendas");
            telaRelatorio.setScene(new Scene(root));
            telaRelatorio.show();

        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            AlertaUtil.mostrarErro("Erro", "Erro ao abrir tela de relatório: " + ex.getMessage());
        }
    }

    @FXML
    void menuSobreClick(ActionEvent event) {
        AlertaUtil.mostrarInformacao("Sobre o Sistema",
                "Sistema simples para vendas online.\n" +
                "Aplicação de controle com cadastro de usuários, clientes e produtos.");
    }

    public void setStage(Stage telaPrincipal) {
        this.stagePrincipal = telaPrincipal;
    }

    public void ajustarElementosJanela(ArrayList<String> dados) {
        lblUsuario.setText(dados.get(0));
        if (!"admin".equals(dados.get(1))) {
            menuRelatorio.setDisable(true);
        }
    }
}
