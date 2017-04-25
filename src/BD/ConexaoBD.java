
package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    
    public Connection conectarBD(){
        
        Connection con = null; 
        
        try{
            String url = "jdbc:postgresql://localhost:5432/KS Sistema de vendas";  
            String usuario = "user";  
            String senha = "senha";              
            //Class.forName("org.postgresql.Driver");                   
            con = DriverManager.getConnection(url, usuario, senha);                
            return con;
          
        }
        catch(Exception e){
            e.printStackTrace();  
        }    
        return con;
    }
    
    public void desconectarBD(Connection con){
        try{
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }        
    }
    
}
