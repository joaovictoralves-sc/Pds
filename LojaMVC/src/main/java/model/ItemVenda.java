package model;

import java.io.Serializable;
import javafx.beans.property.*;

public class ItemVenda implements Serializable {

    private int id;
    private int vendaId;
    private int produtoId;
    private int quantidade;
    private double precoUnitario;

    public ItemVenda() {}

    public ItemVenda(int id, int vendaId, int produtoId, int quantidade, double precoUnitario) {
        this.id = id;
        this.vendaId = vendaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemVenda(int vendaId, int produtoId, int quantidade, double precoUnitario) {
        this.vendaId = vendaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVendaId() { return vendaId; }
    public void setVendaId(int vendaId) { this.vendaId = vendaId; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }

    // Properties
    private transient IntegerProperty idProperty;
    public IntegerProperty idProperty() {
        if (idProperty == null) idProperty = new SimpleIntegerProperty(id);
        return idProperty;
    }

    private transient IntegerProperty vendaIdProperty;
    public IntegerProperty vendaIdProperty() {
        if (vendaIdProperty == null) vendaIdProperty = new SimpleIntegerProperty(vendaId);
        return vendaIdProperty;
    }

    private transient IntegerProperty produtoIdProperty;
    public IntegerProperty produtoIdProperty() {
        if (produtoIdProperty == null) produtoIdProperty = new SimpleIntegerProperty(produtoId);
        return produtoIdProperty;
    }

    private transient IntegerProperty quantidadeProperty;
    public IntegerProperty quantidadeProperty() {
        if (quantidadeProperty == null) quantidadeProperty = new SimpleIntegerProperty(quantidade);
        return quantidadeProperty;
    }

    private transient DoubleProperty precoUnitarioProperty;
    public DoubleProperty precoUnitarioProperty() {
        if (precoUnitarioProperty == null) precoUnitarioProperty = new SimpleDoubleProperty(precoUnitario);
        return precoUnitarioProperty;
    }
}
