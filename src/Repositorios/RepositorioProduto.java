
package Repositorios;

import BD.ConexaoBD;
import Models.Cliente;
import Models.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class RepositorioProduto {
    
    private ArrayList<Produto> lista;
    private static RepositorioProduto instanciaRep;
    
    private RepositorioProduto(){
        this.lista = new ArrayList<Produto>();
    }
    
    public static RepositorioProduto obterInstancia(){
        if(instanciaRep == null)
        {
            instanciaRep = new RepositorioProduto();
        }
        return instanciaRep;   
    }
    
    public ArrayList<Produto> listar() throws SQLException{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();
        ArrayList<Produto> lista = new ArrayList<>();
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM PRODUTOS";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){
                Produto produtoTmp = new Produto();
                produtoTmp.setId_prod(result.getInt("ID_PRO"));
                produtoTmp.setNome(result.getString("NOME"));
                produtoTmp.setCod_barra(result.getString("COD_BARRA"));
                produtoTmp.setDescricao(result.getString("DESCRICAO"));
                produtoTmp.setPreco(result.getString("PRECO"));
                produtoTmp.setQuantidade(result.getString("QUANTIDADE"));
                produtoTmp.setTipo(result.getString("TIPO"));                                                                                                
                lista.add(produtoTmp);
                
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
        String SEQ = "SELECT NEXTVAL('SEQ_PROD')";
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
    
    public int verificarExistencia(Produto produto) throws SQLException{
        int retorno = -1;
        lista = listar();
        for(int a=0;a< this.lista.size();a++){
            if(produto.getCod_barra().trim().equals(this.lista.get(a).getCod_barra().trim())){
                retorno = a;
                break;
            }            
        }
        return retorno;
    }
    
    public void inserir(Produto produto) throws  Exception{
        if(produto == null){
            throw new Exception("O cliente não foi instanciado");
        }
        if(produto.getCod_barra() == null){
            produto.setCod_barra(" ");
        }
        if(produto.getCod_barra().trim().equals("")){
            produto.setCod_barra(" ");
        }
        if(produto.getDescricao() == null){
            produto.setDescricao(" ");
        }
        if(produto.getDescricao().trim().equals("")){
            produto.setDescricao(" ");
        }
        if(produto.getNome()== null){
            throw new Exception("Insira o nome do produto");
        }
        if(produto.getNome().trim().equals("")){
            throw new Exception("Insira o nome do produto");
        }
        if(produto.getPreco()== null){
            throw new Exception("Insira o valor do produto");
        }
        if(produto.getPreco().trim().equals("")){
            throw new Exception("Insira o valor do produto");
        }
        if(produto.getQuantidade()== null){
            produto.setQuantidade(" ");
        }
        if(produto.getQuantidade().trim().equals("")){
            produto.setQuantidade(" ");
        }
        if(produto.getTipo()== null){
            produto.setTipo(" ");
        }
        if(produto.getTipo().trim().equals("")){
            produto.setTipo(" ");
        }                                        
        if(this.verificarExistencia(produto) >=0){
            throw new Exception("O referido cliente ja se encontra cadastrado");
        }
        try{
            ConexaoBD c = new ConexaoBD();
            Connection con = null;        
            con = c.conectarBD();
             
        String temp = "INSERT INTO PRODUTOS(ID_PRO,NOME,TIPO,QUANTIDADE,DESCRICAO,PRECO,COD_BARRA) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setInt(1, getId(con));
            prepared.setString(2, produto.getNome());      
            prepared.setString(3, produto.getTipo());      
            prepared.setString(4, produto.getQuantidade());      
            prepared.setString(5, produto.getDescricao());      
            prepared.setString(6, produto.getPreco());      
            prepared.setString(7, produto.getCod_barra());                                                      
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
        con.prepareStatement("DELETE FROM PRODUTOS WHERE ID_PRO="+id).execute();        
        c.desconectarBD(con);                
        
    }
    
    public void atualizar(Produto produto) throws Exception{
        if(produto == null){
            throw new Exception("O cliente não foi instanciado");
        }
        if(produto.getCod_barra() == null){
            produto.setCod_barra(" ");
        }
        if(produto.getCod_barra().trim().equals("")){
            produto.setCod_barra(" ");
        }
        if(produto.getDescricao() == null){
            produto.setDescricao(" ");
        }
        if(produto.getDescricao().trim().equals("")){
            produto.setDescricao(" ");
        }
        if(produto.getNome()== null){
            throw new Exception("Insira o nome do produto");
        }
        if(produto.getNome().trim().equals("")){
            throw new Exception("Insira o nome do produto");
        }
        if(produto.getPreco()== null){
            throw new Exception("Insira o valor do produto");
        }
        if(produto.getPreco().trim().equals("")){
            throw new Exception("Insira o valor do produto");
        }
        if(produto.getQuantidade()== null){
            produto.setQuantidade(" ");
        }
        if(produto.getQuantidade().trim().equals("")){
            produto.setQuantidade(" ");
        }
        if(produto.getTipo()== null){
            produto.setTipo(" ");
        }
        if(produto.getTipo().trim().equals("")){
            produto.setTipo(" ");
        }                                                
                
        
        try{
            Connection con = null;
            ConexaoBD c = new ConexaoBD();
            con = c.conectarBD();
            
            String temp = "UPDATE PRODUTOS SET NOME=?,TIPO=?,QUANTIDADE=?,"
                    + "DESCRICAO=?,PRECO=?,COD_BARRA=? WHERE ID_PRO=?";
            PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setString(1, produto.getNome());      
            prepared.setString(2, produto.getTipo());      
            prepared.setString(3, produto.getQuantidade());      
            prepared.setString(4, produto.getDescricao());      
            prepared.setString(5, produto.getPreco());      
            prepared.setString(6, produto.getCod_barra());       
            prepared.setInt(7, produto.getId_prod());
            prepared.execute();
            
            c.desconectarBD(con);
        }
        catch(SQLException e){
            e.printStackTrace();
        }            
                                                
    }
    
}
