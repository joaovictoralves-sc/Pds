package model;

import java.io.Serializable;
import java.sql.Date;
import javafx.beans.property.*;

public class Cliente implements Serializable {

    private int id;
    private String nome;
    private String telefone;
    private String endereco;
    private Date dataNascimento;

    public Cliente() {}

    public Cliente(String nome, String telefone, String endereco, Date dataNascimento) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    public Cliente(int id, String nome, String telefone, String endereco, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    // JavaFX Properties
    private transient IntegerProperty idProperty;
    public IntegerProperty idProperty() {
        if (idProperty == null) idProperty = new SimpleIntegerProperty(id);
        return idProperty;
    }

    private transient StringProperty nomeProperty;
    public StringProperty nomeProperty() {
        if (nomeProperty == null) nomeProperty = new SimpleStringProperty(nome);
        return nomeProperty;
    }

    private transient StringProperty telefoneProperty;
    public StringProperty telefoneProperty() {
        if (telefoneProperty == null) telefoneProperty = new SimpleStringProperty(telefone);
        return telefoneProperty;
    }

    private transient StringProperty enderecoProperty;
    public StringProperty enderecoProperty() {
        if (enderecoProperty == null) enderecoProperty = new SimpleStringProperty(endereco);
        return enderecoProperty;
    }

    private transient ObjectProperty<java.time.LocalDate> dataNascimentoProperty;
    public ObjectProperty<java.time.LocalDate> dataNascimentoProperty() {
        if (dataNascimentoProperty == null && dataNascimento != null) {
            dataNascimentoProperty = new SimpleObjectProperty<>(dataNascimento.toLocalDate());
        }
        return dataNascimentoProperty;
    }
    
    @Override
    public String toString() {
        return nome;
    }
}
