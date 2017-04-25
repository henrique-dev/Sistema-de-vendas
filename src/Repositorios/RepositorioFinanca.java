
package Repositorios;

import BD.ConexaoBD;
import Models.Cliente;
import Models.Financa;
import Models.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class RepositorioFinanca {
    
    private ArrayList<Financa> lista;
    private ArrayList<Financa> tempLista;
    private static RepositorioFinanca instanciaRep;
    
    private RepositorioFinanca(){
        this.lista = new ArrayList<Financa>();
    }
    
    public static RepositorioFinanca obterInstancia(){
        if(instanciaRep == null)
        {
            instanciaRep = new RepositorioFinanca();
        }
        return instanciaRep;   
    }
    /*
    public ArrayList<Financa> listar() throws SQLException{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();
        ArrayList<Financa> lista = new ArrayList<>();
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM FINANCAS";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){
                Financa financaTmp = new Financa();
                financaTmp.setId(result.getInt("ID"));
                financaTmp.setData(result.getString("DATA"));
                financaTmp.setValor(result.getString("VALOR"));                                                                
                lista.add(financaTmp);
                
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
    /*
    public int getId(Connection con) throws SQLException {         
        String SEQ = "SELECT NEXTVAL('SEQ_FIN')";
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
    /*
    public void inserir(Financa financa) throws  Exception{        
        try{
            Boolean novo = true;
            ConexaoBD c = new ConexaoBD();
            Connection con = null;  
            Double soma = 0.0;
            con = c.conectarBD();
            
            Date d = new Date();
            Calendar ca = Calendar.getInstance();
            ca.setTime(d); 
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);                        
            String data = (df.format(ca.getTime())); 
            
            lista = this.listar();
            for(int x=0;x<this.lista.size();x++){
                //System.out.println(lista.get(x).getData());
                //System.out.println(data);
                
                if(data.equals(lista.get(x).getData())){
                    
                    //System.out.println(lista.get(x).getId());
                    
                   
                    soma = Double.parseDouble(financa.getValor()) + Double.parseDouble(lista.get(x).getValor());
                    
                    String temp = "UPDATE FINANCAS SET DATA=? ,VALOR=? WHERE ID=?";
                    PreparedStatement prepared2 = con.prepareStatement(temp);                    
                    prepared2.setString(1, financa.getData());
                    prepared2.setString(2, soma.toString());         
                    prepared2.setInt(3, lista.get(x).getId());
                    prepared2.execute();
        
                    novo = false;
                                        
                }
            }
                                                
        if(novo){
        String temp = "INSERT INTO FINANCAS(ID,DATA,VALOR) VALUES(?,?,?)";
        PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setInt(1, getId(con));
            prepared.setString(2, financa.getData());
            prepared.setString(3, financa.getValor());                                                                                                                            
            prepared.execute();
        }
        c.desconectarBD(con);
        }
        catch(SQLException e){
            e.printStackTrace();
        }                                
           
    }
    */
    public void setSaldo(Double saldo){
        try{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();  
        con = c.conectarBD();
        
        String saldoTmp = this.getSaldo();
        saldo = saldo + Double.parseDouble(saldoTmp);                
        //System.out.println(saldo);                
        
        String temp = "UPDATE SALDO SET TOTAL=?";
        PreparedStatement prepared = con.prepareStatement(temp);
        prepared.setString(1, saldo.toString());      
        prepared.execute();
        
        //JOptionPane.showInputDialog(null, "TESTE");
        
        c.desconectarBD(con);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public String getSaldo() throws SQLException{
        String saldo = "";
        Connection con = null;
        ConexaoBD c = new ConexaoBD();       
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM SALDO";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){                
                saldo = (result.getString("TOTAL"));
                
            }            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            c.desconectarBD(con);
        }
        return saldo;
    }
    
    
    
}
