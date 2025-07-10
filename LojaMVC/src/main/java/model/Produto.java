package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javafx.beans.property.*;

public class Produto implements Serializable {

    private int id;
    private String descricao;
    private BigDecimal valor;
    private int quantidadeEstoque;

    public Produto() {}

    public Produto(String descricao, BigDecimal valor, int quantidadeEstoque) {
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto(int id, String descricao, BigDecimal valor, int quantidadeEstoque) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    private transient IntegerProperty idProperty;
    public IntegerProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleIntegerProperty(id);
        }
        return idProperty;
    }

    private transient StringProperty descricaoProperty;
    public StringProperty descricaoProperty() {
        if (descricaoProperty == null) {
            descricaoProperty = new SimpleStringProperty(descricao);
        }
        return descricaoProperty;
    }

    private transient DoubleProperty valorProperty;
    public DoubleProperty valorProperty() {
        if (valorProperty == null && valor != null) {
            valorProperty = new SimpleDoubleProperty(valor.doubleValue());
        }
        return valorProperty;
    }

    private transient IntegerProperty quantidadeEstoqueProperty;
    public IntegerProperty quantidadeEstoqueProperty() {
        if (quantidadeEstoqueProperty == null) {
            quantidadeEstoqueProperty = new SimpleIntegerProperty(quantidadeEstoque);
        }
        return quantidadeEstoqueProperty;
    }
}