package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Usuario;
import model.UsuarioDAO;
import util.AlertaUtil;
import util.LimitarCaracter;

public class TelaCadastroUsuariosController {
    
    Stage stageCadastroUsuarios;
    Usuario usuarioSelecionado;
    
    private Runnable onUsuarioSalvo; 

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnFechar;

    @FXML
    private Button btnIncluirAlterar;

    @FXML
    private ComboBox<String> cmbPerfil;

    @FXML
    private DatePicker dtDataNascimento;

    @FXML
    private Label lblCadastroUsuarios;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPerfil;

    @FXML
    private Label lblSenha;

    @FXML
    private Label lblTelefone;

    @FXML
    private Label lbldataNascimento;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtNome;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtTelefone;
    
    LimitarCaracter limitarcaracter = new LimitarCaracter();

    @FXML
    void btnExcluirClick(ActionEvent event) throws SQLException {
        Optional<ButtonType> resultado = AlertaUtil.mostrarConfirmacao("Atenção",
                "Tem certeza que quer excluir o registro?");
        if(resultado.isPresent()){
            ButtonType botaoPressionado = resultado.get();
            if(botaoPressionado == ButtonType.OK){
                excluir(usuarioSelecionado.getId());
            }
        }
    }
    
    @FXML
    void btnFecharClick(ActionEvent event) {
        stageCadastroUsuarios.close();
    }

    @FXML
    void btnIncluirAlterarClick(ActionEvent event) throws SQLException {
         
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String login = txtLogin.getText();
        String senha = txtSenha.getText();
        String perfil = cmbPerfil.getValue();
        String email = txtEmail.getText();
        LocalDate aniversario = dtDataNascimento.getValue();
        
        java.sql.Date dataAniversario = null;

        if (aniversario != null) {
            dataAniversario = java.sql.Date.valueOf(aniversario);
        }

        if (nome.isEmpty() || telefone.isEmpty() || login.isEmpty() ||
            senha.isEmpty() || email.isEmpty()) {
            AlertaUtil.mostrarErro("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }
        
        if(limitarcaracter.LimitaFormatoTelefone(txtTelefone.getText())){
            return;
        }
        
        if(limitarcaracter.LimitaFormatEmail(txtEmail.getText())){
            return;
        }
        
        if (usuarioSelecionado == null) {
            incluir(txtNome.getText(),
                    txtTelefone.getText(),
                    txtLogin.getText(),
                    txtSenha.getText(),
                    cmbPerfil.getValue(),
                    txtEmail.getText(),
                    dataAniversario); 
        } else {
            alterar(usuarioSelecionado.getId(),
                    txtNome.getText(),
                    txtTelefone.getText(),
                    txtLogin.getText(),
                    txtSenha.getText(),
                    cmbPerfil.getValue(),
                    txtEmail.getText(),
                    dataAniversario);  
        }
    }

    void setStage(Stage telaCadastroUsuarios){
        this.stageCadastroUsuarios = telaCadastroUsuarios;
    }
    
    void ajustarElementosJanela(Usuario user){
        this.usuarioSelecionado = user;
        if(user == null){ 
            txtNome.requestFocus();
            btnExcluir.setVisible(false);
            btnIncluirAlterar.setText("Salvar");
            cmbPerfil.getItems().addAll("admin", "user");
        } else {
            btnIncluirAlterar.setText("Editar");
            txtNome.setText(user.getNome());
            txtTelefone.setText(user.getFone());
            txtLogin.setText(user.getLogin());
            txtSenha.setText(user.getSenha());
            txtEmail.setText(user.getEmail());
            if(user.getAniversario() != null){
                dtDataNascimento.setValue(user.getAniversario().toLocalDate());
            } else {
                dtDataNascimento.setValue(null);
            }       
            cmbPerfil.getItems().addAll("admin", "user");
            cmbPerfil.setValue(user.getPerfil());
        }
    }

    void incluir(String nome, String fone, 
        String login, String senha, String perfil, String email, Date aniversario) throws SQLException {
        Usuario usuario = new Usuario(nome, fone, login,
        senha, perfil, email, aniversario);
        new UsuarioDAO().salvar(usuario);
        if(onUsuarioSalvo != null){
            onUsuarioSalvo.run();
        }
        AlertaUtil.mostrarInformacao("Informação",
                "Registro inserido com sucesso!");
        stageCadastroUsuarios.close();
    }
    
    void alterar(int id, String nome, String fone, String login,
            String senha, String perfil, String email, Date aniversario) throws SQLException{
        Usuario usuarioAlterado = new Usuario(id, nome, fone, login,
        senha, perfil, email, aniversario);
        new UsuarioDAO().alterar(usuarioAlterado);
        if(onUsuarioSalvo != null){
            onUsuarioSalvo.run();
        }
        AlertaUtil.mostrarInformacao("Informação",
                "Registro alterado com sucesso!");
        stageCadastroUsuarios.close();
    }
    
    public void setOnUsuarioSalvo(Runnable callback){
        this.onUsuarioSalvo = callback;
    }
    
    public void excluir(int id) throws SQLException{
        new UsuarioDAO().excluir(id);
        if(onUsuarioSalvo != null){
            onUsuarioSalvo.run();
        }
        AlertaUtil.mostrarInformacao("Informação", 
                "Registro excluído com sucesso!");
        stageCadastroUsuarios.close();
    }
}
