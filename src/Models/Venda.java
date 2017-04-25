
package Models;

import java.util.ArrayList;

public class Venda {
    
    int id_venda;
    Cliente cliente;
    ArrayList<Produto> produtos[];    
    String totalVenda;
    String dataVenda;
    String dataVencimento;

    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Produto>[] getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto>[] produtos) {
        this.produtos = produtos;
    }

    public String getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(String totalVenda) {
        this.totalVenda = totalVenda;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
    
    
    
}
