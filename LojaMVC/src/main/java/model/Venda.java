package model;

import java.io.Serializable;
import java.sql.Date;
import javafx.beans.property.*;

public class Venda implements Serializable {

    private int id;
    private Date dataCompra;
    private double valorTotal;
    private int clienteId;

    public Venda() {}

    public Venda(int id, Date dataCompra, double valorTotal, int clienteId) {
        this.id = id;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.clienteId = clienteId;
    }

    public Venda(Date dataCompra, double valorTotal, int clienteId) {
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.clienteId = clienteId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDataCompra() { return dataCompra; }
    public void setDataCompra(Date dataCompra) { this.dataCompra = dataCompra; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    private transient IntegerProperty idProperty;
    public IntegerProperty idProperty() {
        if (idProperty == null) idProperty = new SimpleIntegerProperty(id);
        return idProperty;
    }

    private transient ObjectProperty<Date> dataCompraProperty;
    public ObjectProperty<Date> dataCompraProperty() {
        if (dataCompraProperty == null) dataCompraProperty = new SimpleObjectProperty<>(dataCompra);
        return dataCompraProperty;
    }

    private transient DoubleProperty valorTotalProperty;
    public DoubleProperty valorTotalProperty() {
        if (valorTotalProperty == null) valorTotalProperty = new SimpleDoubleProperty(valorTotal);
        return valorTotalProperty;
    }

    private transient IntegerProperty clienteIdProperty;
    public IntegerProperty clienteIdProperty() {
        if (clienteIdProperty == null) clienteIdProperty = new SimpleIntegerProperty(clienteId);
        return clienteIdProperty;
    }
}
