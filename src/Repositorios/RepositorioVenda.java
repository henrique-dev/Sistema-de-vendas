
package Repositorios;

import BD.ConexaoBD;
import Models.Cliente;
import Models.Produto;
import Models.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class RepositorioVenda {
    
    private ArrayList<Produto> lista;
    private static RepositorioVenda instanciaRep;
    
    private RepositorioVenda(){
        this.lista = new ArrayList<Produto>();
    }
    
    public static RepositorioVenda obterInstancia(){
        if(instanciaRep == null)
        {
            instanciaRep = new RepositorioVenda();
        }
        return instanciaRep;   
    }
    
    public ArrayList<Venda> listar() throws SQLException{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();
        ArrayList<Venda> lista = new ArrayList<>();
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM VENDAS";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){
                Venda vendaTmp = new Venda();
                
                vendaTmp.setId_venda(result.getInt("ID_VENDA"));
                
                int id = result.getInt("ID_CLIENTE");                
                ArrayList<Cliente> tmpArray = RepositorioCliente.obterInstancia().listar();
                for(int x=0;x<tmpArray.size();x++){
                    if(tmpArray.get(x).getId_cli() == id){
                        vendaTmp.setCliente(tmpArray.get(x));
                        break;
                    }
                }                
                
                vendaTmp.setDataVenda(result.getString("DATA_VENDA"));
                vendaTmp.setDataVencimento(result.getString("DATA_VENC"));
                vendaTmp.setTotalVenda(result.getString("TOTAL_VENDA"));                                                                                                                                    
                lista.add(vendaTmp);
                
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            c.desconectarBD(con);
        }
        return lista;
    }
    
    public int getId(Connection con) throws SQLException {         
        String SEQ = "SELECT NEXTVAL('SEQ_VEND')";
        int id = 0; 
        try {             
            PreparedStatement prepared = con .prepareStatement(SEQ); 
            ResultSet resultSet = prepared.executeQuery(); 
            while (resultSet.next()) id = resultSet.getInt("nextval"); 
        } 
        catch (SQLException e) { 
            e.printStackTrace(); 
        }         
        return id; 
    }
    
    public void inserir(Venda venda) throws  Exception{
                
        if(venda.getCliente() == null){
            throw new Exception("Selecione um cliente");
        }
        if(venda.getCliente().getNome().trim().equals("")){
            throw new Exception("Selecione um cliente");
        }
        if(venda.getTotalVenda() == null){
            throw new Exception("Insira o valor total");
        }
        if(venda.getTotalVenda().trim().equals("")){
            throw new Exception("Insira o valor total");
        }
        
        try{
            ConexaoBD c = new ConexaoBD();
            Connection con = null;        
            con = c.conectarBD();
             
        String temp = "INSERT INTO VENDAS(ID_VENDA,ID_CLIENTE,DATA_VENDA,DATA_VENC,TOTAL_VENDA) VALUES(?,?,?,?,?)";
        PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setInt(1, getId(con));
            prepared.setInt(2, venda.getCliente().getId_cli());      
            prepared.setString(3, venda.getDataVenda());      
            prepared.setString(4, venda.getDataVencimento());      
            prepared.setString(5, venda.getTotalVenda());                                                                   
            prepared.execute();
        
        c.desconectarBD(con);
        }
        catch(SQLException e){
            e.printStackTrace();
        }                                
           
    }
    
    public void remover(int id) throws Exception{
        
        ConexaoBD c = new ConexaoBD();
        Connection con = null;
        con = c.conectarBD();
        con.prepareStatement("DELETE FROM VENDAS WHERE ID_VENDA="+id).execute();        
        c.desconectarBD(con);                
        
    }
    
}
