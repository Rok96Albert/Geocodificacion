/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package geocodeelotes;

/**
 * La clase elotes tiene como función principal modelar un punto de venta de elotes
 * y también para mantener consistencia en los datos. 
 * @author mario
 */
public class Elotes {
    
    private String nombre;
    private double x_longitud;
    private double y_latitud;
    private String tipo_puesto;
    private String txt_direccion;
    Elotes(String nombre, String direccion, String tipo, double longitud, double latitud){
        this.nombre = nombre;
        this.tipo_puesto = tipo;
        this.txt_direccion = direccion;
        
        this.x_longitud = longitud;
        this.y_latitud = latitud;
    }
    
    public String getDireccion(){
        return txt_direccion;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getTipo(){
        return tipo_puesto;
    }
    
    public double getLongitud(){
        
        if(x_longitud >= -180 && x_longitud <= 0)
        {
            System.out.println(x_longitud+" O");
            return x_longitud;//Coordenada de longitud en direccion Oeste
        }
        else if(x_longitud >= 0 && x_longitud <= 180){
            
            
            System.out.println(x_longitud+" E");
            return x_longitud;// Coordenada de latitud en direccion Este. 
        }
        
        return x_longitud;
    }
    
    public double getLatitud(){
        
        if(y_latitud > 0 && y_latitud <= 90)
        {
            System.out.println(y_latitud+" N");
            return y_latitud;
            
        }else if(y_latitud < 0 && y_latitud >= -90){
            
            System.out.println(y_latitud+" S");
            return y_latitud;
        }
        
        return y_latitud;
    }
    
    
}
