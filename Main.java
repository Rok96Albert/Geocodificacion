/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package geocodeelotes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author mario
 */
public class Main {
    
    //Atributos de la clase principal
    public static String nombre;
    public static double [] coordenadas;
    public static String direcc;
    
    public static String tipo;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Instanciación de los objetos de la clase ElotesBD.
        //Inicializando arreglo de coordenadas.
        coordenadas = new double[2];
       
        ElotesBD pos_elotes = new ElotesBD();
        
        int num_registros = 0;
        
        System.out.println("Numero de puntos de venta a registrar: ");
        
        Scanner scan = new Scanner(System.in);
        
        num_registros = scan.nextInt();
        
        int c_registro = 0;
        
        while(c_registro < num_registros)
        {
            System.out.println("Nombre del punto de venta: ");
            nombre = scan.next();
            
            
            scan.nextLine();
            
            System.out.println("Coordenadas: ");
            
            System.out.println("Longitud: ");
            
            coordenadas[0] = scan.nextDouble();
            
            
            
            scan.nextLine();
            
            System.out.println("Latitud: ");
            
            coordenadas[1] = scan.nextDouble();
            
            
            
            scan.nextLine();
            
            direcc = Direccion(coordenadas);
            
            //Especificar si es ambulante o local
            System.out.println("Tipo de negocio: ");
            tipo = scan.next();
           
            
            
            System.out.println("Dirección del punto de venta es: "+direcc);
            
            Elotes elote = new Elotes(nombre,direcc,tipo,coordenadas[0],coordenadas[1]);
            
            pos_elotes.Registrar_BD(elote);
            
            c_registro++;
        }
        
        scan.close();
    }
    
    public static String Direccion(double [] coords)
    {
        String direccion = "";
        try{
            //Creo una instancia de la clase Socket 
            Socket s_cliente = new Socket("localhost",5412);
            
            //Creo 2 objetos de tipo entrada y salida de datos para manejar un flujo de datos
            DataOutputStream out = new DataOutputStream(s_cliente.getOutputStream());
            DataInputStream input = new DataInputStream(s_cliente.getInputStream());
            
            System.out.println("Coordenadas geográficas: "+coords[0]+","+coords[1]);
            
            String coordenada = "";
            //Se concatenan las coordenadas y se guardan en un objeto de tipo String. 
            coordenada = coords[0]+","+coords[1];
            /*
                Las coordenadas concatenadas se pasan al objeto out de la clase DataOutputStream, usando el método write(String)
                El tipo de dato que recibe el método write(), se convierte en un paquete de bits, usando el métdo getBytes()
            */ 
            
            out.write(coordenada.getBytes());
            //Guardar resultado.
            //Se crea un arreglo de bytes
            byte [] buffer = new byte[1024];
            
            // El paquete de bytes que el cliente recibe se guarda en el arreglo de bytes y después se lee el número de bytes que contiene
            int lee_buffer = input.read(buffer);
            //La instancia del objeto de tipo String, recibe el arreglo de bytes (datos crudos), la posicion inicial del primer byte y
            //el numero total de bytes que contiene ese paquete de bytes, los espacios no usados en el arreglo se eliminan. 
            direccion = new String(buffer,0,lee_buffer,StandardCharsets.UTF_8);
            
            //Al finalizar el proceso del flujo de datos, se liberan los recursos de memoria que el socket usó 
            s_cliente.close();
        } catch (IOException ex) {
            System.out.println("La conexión al servidor no se realizo correctamente.");
            
            ex.printStackTrace();
        }
        
        return direccion;
    }
    
}
