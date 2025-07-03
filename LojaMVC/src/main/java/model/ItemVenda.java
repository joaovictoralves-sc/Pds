package model;

import java.sql.Date;

public class ItemVenda {
    
    private int id;
    private int venda_id;
    private int produto_id;
    private int quantidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenda_id() {
        return venda_id;
    }

    public void setVenda_id(int venda_id) {
        this.venda_id = venda_id;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public long getPreco_unitario() {
        return preco_unitario;
    }

    public void setPreco_unitario(long preco_unitario) {
        this.preco_unitario = preco_unitario;
    }
    private long preco_unitario;
    
}
