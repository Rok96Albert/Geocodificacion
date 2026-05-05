/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geocodeelotes;

/**
 * Esta clase permite crear una conexión a la base de datos postgresql.
 * La conexion a la base de datos, permitira manejar SQL dentro del programa.
 * @author mario
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ElotesBD {
    
    private String uri ="jdbc:postgresql://localhost:5432/PosElotes";
    private String user="postgres";
    private String cv ="postgres";
    
    PreparedStatement p_state = null;
    ResultSet rs = null;
    Statement st = null;
    
    public ElotesBD()
    {
       
    }
    
    public void Registrar_BD(Elotes maiz)
    {
        try{
            
            Connection conn = DriverManager.getConnection(uri, user, cv);
            System.out.println("La conexión a la base de datos gis-elotes ha sido exitosa.");
            
            p_state = conn.prepareStatement("INSERT INTO Puesto_elotes VALUES(?,?,?,?,?)");
            
            p_state.setString(1,maiz.getNombre());
            p_state.setDouble(2,maiz.getLongitud());
            p_state.setDouble(3,maiz.getLatitud());
            p_state.setString(4,maiz.getTipo());
            p_state.setString(5,maiz.getDireccion());
            
            p_state.executeUpdate();
            
            System.out.println("Datos registrados.");
            p_state.close();
            conn.close();
            
        }catch(SQLException ex){
            System.out.println("Hubo un fallo en la conexión o pudo haber sido un error de actualización. ");
            ex.printStackTrace();
        }
        
    }
    
}
