
package Repositorios;

import BD.ConexaoBD;
import Models.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepositorioFornecedor {
    
    private ArrayList<Fornecedor> lista;
    private static RepositorioFornecedor instanciaRep;
    
    private RepositorioFornecedor(){
        this.lista = new ArrayList<Fornecedor>();
    }
    
    public static RepositorioFornecedor obterInstancia(){
        if(instanciaRep == null)
        {
            instanciaRep = new RepositorioFornecedor();
        }
        return instanciaRep;   
    }
    
    public ArrayList<Fornecedor> listar() throws SQLException{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();
        ArrayList<Fornecedor> lista = new ArrayList<>();
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM FORNECEDORES";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){
                Fornecedor fornecedorTmp = new Fornecedor();
                fornecedorTmp.setId(result.getInt("ID"));
                fornecedorTmp.setNome(result.getString("NOME"));
                fornecedorTmp.setVencimento(result.getString("DATA_VENCIMENTO"));
                fornecedorTmp.setValor(result.getString("VALOR"));
                lista.add(fornecedorTmp);                                                                                                
                
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
        String SEQ = "SELECT NEXTVAL('SEQ_FORN')";
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
    
    public void inserir(Fornecedor fornecedor) throws  Exception{
        if(fornecedor == null){
            throw new Exception("O cliente não foi instanciado");
        }
        if(fornecedor.getNome()== null){
            throw new Exception("Informe o nome do fornecedor");
        }
        if(fornecedor.getNome().trim().equals("")){
            throw new Exception("Informe o nome do fornecedor");
        }        
        if(fornecedor.getValor()== null){
            throw new Exception("Informe o valor");
        }
        if(fornecedor.getValor().trim().equals("")){
            throw new Exception("Informe o valor");
        }
        /*
        if(this.verificarExistencia(cliente) >=0){
            throw new Exception("O referido cliente ja se encontra cadastrado");
        }
                */
        try{
            ConexaoBD c = new ConexaoBD();
            Connection con = null;        
            con = c.conectarBD();
             
        String temp = "INSERT INTO FORNECEDORES(ID,NOME,DATA_VENCIMENTO,VALOR) VALUES(?,?,?,?)";
        PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setInt(1, getId(con));
            prepared.setString(2, fornecedor.getNome());
            prepared.setString(3, fornecedor.getVencimento());
            prepared.setString(4, fornecedor.getValor());
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
        con.prepareStatement("DELETE FROM FORNECEDORES WHERE ID="+id).execute();        
        c.desconectarBD(con);                
        
    }
    /*
    public void atualizar(Cliente cliente) throws Exception{
        if(cliente == null){
            throw new Exception("O cliente nao foi instanciado");
        }
        if(cliente.getCpf() == null){
            cliente.setCpf(" ");
        }
        if(cliente.getCpf().trim().equals("")){
            cliente.setCpf(" ");
        }
        if(cliente.getNome() == null){
            throw new Exception("Informar o nome do cliente");
        }
        if(cliente.getNome().trim().equals("")){
            throw new Exception("Informar o nome do cliente");
        }        
                
        
        try{
            Connection con = null;
            ConexaoBD c = new ConexaoBD();
            con = c.conectarBD();
            
            String temp = "UPDATE CLIENTES SET NOME =?, CPF =?,ENDERECO=?,BAIRRO =? WHERE ID_CLI=?";
            PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setString(1, cliente.getNome());
            prepared.setString(2, cliente.getCpf());
            prepared.setString(3, cliente.getEndereco());
            prepared.setString(4, cliente.getBairro());
            prepared.setInt(5, cliente.getId_cli());
            prepared.execute();
            
            c.desconectarBD(con);
        }
        catch(SQLException e){
            e.printStackTrace();
        }            
                                                
    }
    */
    
}
