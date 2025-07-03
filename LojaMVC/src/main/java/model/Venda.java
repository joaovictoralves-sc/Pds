package model;

import java.sql.Date;

public class Venda {
    
    private int id;
    private Date data_compra;
    private long valor_total;
    private int cliente_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData_compra() {
        return data_compra;
    }

    public void setData_compra(Date data_compra) {
        this.data_compra = data_compra;
    }

    public long getValor_total() {
        return valor_total;
    }

    public void setValor_total(long valor_total) {
        this.valor_total = valor_total;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }
    
}
