package Repositorios;

import Models.Cliente;
import BD.ConexaoBD;
import Models.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jdk.nashorn.internal.objects.NativeString;


public class RepositorioCliente {
    
    private ArrayList<Cliente> lista;
    private static RepositorioCliente instanciaRep;
    
    private RepositorioCliente(){
        this.lista = new ArrayList<Cliente>();
    }
    
    public static RepositorioCliente obterInstancia(){
        if(instanciaRep == null)
        {
            instanciaRep = new RepositorioCliente();
        }
        return instanciaRep;   
    }
    
    public ArrayList<Cliente> listar() throws SQLException{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();
        ArrayList<Cliente> lista = new ArrayList<>();
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM CLIENTES";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){
                Cliente pessoaTmp = new Cliente();
                pessoaTmp.setId_cli(result.getInt("ID_CLI"));
                pessoaTmp.setNome(result.getString("NOME"));
                pessoaTmp.setCpf(result.getString("CPF"));
                pessoaTmp.setEndereco(result.getString("ENDERECO"));
                pessoaTmp.setBairro(result.getString("BAIRRO"));
                lista.add(pessoaTmp);
                
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
        String SEQ = "SELECT NEXTVAL('ID_SEQUENCE')";
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
    
    public int verificarExistencia(Cliente cliente) throws SQLException{
        int retorno = -1;
        lista = listar();
        for(int a=0;a< this.lista.size();a++){
            if(cliente.getCpf().trim().equals(this.lista.get(a).getCpf().trim())){
                retorno = a;
                break;
            }            
        }
        return retorno;
    }
    
    public void inserir(Cliente cliente) throws  Exception{
        if(cliente == null){
            throw new Exception("O cliente não foi instanciado");
        }
        if(cliente.getCpf() == null){
            cliente.setCpf(" ");
        }
        if(cliente.getCpf().trim().equals("")){
            cliente.setCpf(" ");
        }
        if(cliente.getNome() == null){
            throw new Exception("Informe o nome do cliente");
        }
        if(cliente.getNome().trim().equals("")){
            throw new Exception("Informe o nome do cliente");
        }
        if(cliente.getEndereco()== null){
            throw new Exception("Informe o endereço do cliente");
        }
        if(cliente.getEndereco().trim().equals("")){
            throw new Exception("Informe o endereço do cliente");
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
             
        String temp = "INSERT INTO CLIENTES(ID_CLI,NOME,CPF,ENDERECO,BAIRRO) VALUES(?,?,?,?,?)";
        PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setInt(1, getId(con));
            prepared.setString(2, cliente.getNome());           
            prepared.setString(3, cliente.getCpf());
            prepared.setString(4, cliente.getEndereco());
            prepared.setString(5, cliente.getBairro());
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
        con.prepareStatement("DELETE FROM CLIENTES WHERE ID_CLI="+id).execute();        
        c.desconectarBD(con);                
        
    }
    
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
    
}
