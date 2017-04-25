
package Repositorios;

import BD.ConexaoBD;
import Models.Faturamento;
import Models.Financa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

public class RepositorioFaturamentoC {
    
    private ArrayList<Faturamento> lista;
    private ArrayList<Faturamento> tempLista;
    private static RepositorioFaturamentoC instanciaRep;
    
    private RepositorioFaturamentoC(){
        this.lista = new ArrayList<Faturamento>();
    }
    
    public static RepositorioFaturamentoC obterInstancia(){
        if(instanciaRep == null)
        {
            instanciaRep = new RepositorioFaturamentoC();
        }
        return instanciaRep;   
    }
    
    public ArrayList<Faturamento> listar() throws SQLException{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();
        ArrayList<Faturamento> lista = new ArrayList<>();
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM FAT_COMERCIO";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){
                Faturamento faturamentoTmp = new Faturamento();
                faturamentoTmp.setId(result.getInt("ID"));
                faturamentoTmp.setData(result.getString("DATA"));
                faturamentoTmp.setValor(result.getString("VALOR"));                                                                
                lista.add(faturamentoTmp);
                
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
        String SEQ = "SELECT NEXTVAL('SEQ_FATC')";
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
    
    public void inserir(Faturamento faturamento) throws  Exception{        
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
                    
                    /*
                    String temp = "SELECT * FROM FINANCAS";
                    PreparedStatement prepared = con.prepareStatement(temp);
                    ResultSet result = prepared.executeQuery();
                    result.next();
                    Double soma = Double.parseDouble(result.getString("VALOR"));
                    */                    
                    soma = Double.parseDouble(faturamento.getValor()) + Double.parseDouble(lista.get(x).getValor());
                    
                    String temp = "UPDATE FAT_COMERCIO SET DATA=? ,VALOR=? WHERE ID=?";
                    PreparedStatement prepared2 = con.prepareStatement(temp);                    
                    prepared2.setString(1, faturamento.getData());
                    prepared2.setString(2, soma.toString());         
                    prepared2.setInt(3, lista.get(x).getId());
                    prepared2.execute();
        
                    novo = false;
                                        
                }
            }
                                                
        if(novo){
        String temp = "INSERT INTO FAT_COMERCIO(ID,DATA,VALOR) VALUES(?,?,?)";
        PreparedStatement prepared = con.prepareStatement(temp);
            prepared.setInt(1, getId(con));
            prepared.setString(2, faturamento.getData());
            prepared.setString(3, faturamento.getValor());                                                                                                                            
            prepared.execute();
        }
        c.desconectarBD(con);
        }
        catch(SQLException e){
            e.printStackTrace();
        }                                
           
    }
    
    public void setEstoque(Double saldo,char op){
        try{
        Connection con = null;
        ConexaoBD c = new ConexaoBD();  
        con = c.conectarBD();
        
        if(op == '-'){
            String saldoTmp = this.getEstoque();
            saldo = Double.parseDouble(saldoTmp) - saldo;           
            //System.out.println(saldo);                
        }
        else{
            String saldoTmp = this.getEstoque();
            saldo = Double.parseDouble(saldoTmp) + saldo;           
        }              
        
        String temp = "UPDATE SALDO SET ESTOQUE_COMERCIO=?";
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
    
    public String getEstoque() throws SQLException{
        String saldo = "";
        Connection con = null;
        ConexaoBD c = new ConexaoBD();       
        try{            
            con = c.conectarBD();         
            
            String temp = "SELECT * FROM SALDO";
            PreparedStatement prepared = con.prepareStatement(temp);
            ResultSet result = prepared.executeQuery();
            
            while(result.next()){                
                saldo = (result.getString("ESTOQUE_COMERCIO"));
                
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
