
package Repositorios;

import BD.ConexaoBD;
import Models.Cliente;
import Models.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RepositorioLog {
    
    private ArrayList<Log> lista;
    private static RepositorioLog instanciaRep;
    
    private RepositorioLog(){
        this.lista = new ArrayList<Log>();
    }
    
    public static RepositorioLog obterInstancia(){
        if(instanciaRep == null)
        {
            instanciaRep = new RepositorioLog();
        }
        return instanciaRep;   
    }
    
    public ArrayList<Log> listar() throws SQLException{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();
        ArrayList<Log> lista = new ArrayList<>();
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM LOG";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){
                Log logTmp = new Log();
                logTmp.setId(result.getInt("ID"));
                logTmp.setData(result.getString("DATALOG"));
                logTmp.setDescricao(result.getString("DESCRICAO"));                
                lista.add(logTmp);
                
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
        String SEQ = "SELECT NEXTVAL('SEQ_LOG')";
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
    
    public void inserir(Log log) throws  Exception{
        
        try{
            ConexaoBD c = new ConexaoBD();
            Connection con = null;        
            con = c.conectarBD();
             
        String temp = "INSERT INTO LOG(ID,DESCRICAO,DATALOG) VALUES(?,?,?)";
        PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setInt(1, getId(con));
            prepared.setString(2, log.getDescricao());
            prepared.setString(3, log.getData());
            prepared.execute();
        
        c.desconectarBD(con);
        }
        catch(SQLException e){
            e.printStackTrace();
        }                                
           
    }            
    
}
