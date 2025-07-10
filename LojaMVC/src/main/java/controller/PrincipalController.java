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
import model.Cliente;
import util.AlertaUtil;

public class PrincipalController {

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

    // Abrir listagem de usuários 
    @FXML
    void menuCadastroUsuarioClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ListagemUsuarios.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaListagemUsuarios = new Stage();
        ListagemUsuariosController luc = loader.getController();
        luc.setStage(telaListagemUsuarios);
        telaListagemUsuarios.setOnShown(e -> {
            try {
                luc.ajustarElementosJanela();
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        telaListagemUsuarios.setTitle("Listagem de Usuários");
        telaListagemUsuarios.setScene(new Scene(root));
        telaListagemUsuarios.show();
    }

    // Abrir listagem de clientes
    @FXML
    void menuCadastroClienteClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ListagemClientes.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaListagemClientes = new Stage();
        ListagemClientesController lcc = loader.getController();
        lcc.setStage(telaListagemClientes);
        telaListagemClientes.setOnShown(e -> {
            try {
                lcc.ajustarElementosJanela();
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        telaListagemClientes.setTitle("Listagem de Clientes");
        telaListagemClientes.setScene(new Scene(root));
        telaListagemClientes.show();
    }

    // Abrir listagem de produtos
    @FXML
    void menuCadastroProdutoClick(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/view/ListagemProdutos.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage telaListagemProdutos = new Stage();
        ListagemProdutosController lpc = loader.getController();
        lpc.setStage(telaListagemProdutos);
        telaListagemProdutos.setOnShown(e -> {
            try {
                lpc.ajustarElementosJanela();
            } catch (SQLException ex) {
                Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        telaListagemProdutos.setTitle("Listagem de Produtos");
        telaListagemProdutos.setScene(new Scene(root));
        telaListagemProdutos.show();
    }

    // Ação do menu Relatório de Vendas
    @FXML
    void menuRelatorioVendasClick(ActionEvent event) {
        AlertaUtil.mostrarInformacao("Relatório de Vendas", "Essa função será desenvolvida.");
    }

    // Ação do menu Sobre
    @FXML
    void menuSobreClick(ActionEvent event) {
        AlertaUtil.mostrarInformacao("Sobre o Sistema",
                "Sistema simples para vendas online.\n" +
                "Aplicação de controle com cadastro de usuários, clientes e produtos.");
    }

    // Fechar aplicação
    @FXML
    void menuFecharClick(ActionEvent event) {
        Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Atenção", "Tem certeza que deseja fechar a aplicação?");
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    // Setter para o stage principal
    public void setStage(Stage telaPrincipal) {
        this.stagePrincipal = telaPrincipal;
    }

    // Ajusta o nome do usuário logado e controla acesso ao menu
    public void ajustarElementosJanela(ArrayList<String> dados) {
        lblUsuario.setText(dados.get(0)); // nome
        if (dados.get(1).equals("admin")) {
            System.out.println("Acesso completo");
        } else {
            System.out.println("Acesso restrito");
            menuRelatorio.setDisable(true);
        }
    }
}
