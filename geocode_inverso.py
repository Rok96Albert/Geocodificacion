import socket;
import time;
from geopy.geocoders import Nominatim;
from geopy.exc import GeocoderTimedOut, GeocoderServiceError;

class Coordenada:

    def __init__(self,longitud, latitud):

        self.longitud = longitud
        self.latitud = latitud
        
    def Direccion(self):

        long_x = self.longitud
        lat_y = self.latitud
        #Se guardan los valores de los atributos dentro de una tupla. 
        coordenada = (lat_y,long_x)
        
        geolocalizador = Nominatim(user_agent="puesto_elotes", timeout=10)
        try:
            #Devuelve un objeto de tipo location, el objeto que regresa contiene atributos como nombre, direccion y coordenadas
            #Cuando el método reverse() no encuentra nada, regresa un None
            direccion = geolocalizador.reverse(coordenada)
            # Accede al atributo address y se guarda la dirección obtenida. 
            if direccion:
                texto_direccion = direccion.address
            else:
                print("No se encontró la dirección de esa coordenada. ")
            
            print("Direccion: ",direccion.address)
            print("Coordenadas: ","(",direccion.longitude,",",direccion.latitude,")")

        except (GeocoderServiceError, GeocoderTimedOut):

            texto_direccion ="No se pudo encontrar una dirección con esas coordenadas."
            print(texto_direccion)

        return texto_direccion
        
class Server:
    #Método constructor de la clase Server. El constructo recibe como parámetros el puerto de conexión y la dirección IP del Host, que es localhost.
    #Al momento de instanciar la clase, los valores enviados por parametro, son asignados a los atributos de la clase. 
    def __init__(self,port, localhost):
        self.port = port
        self.localhost = localhost
    #Método que permite la creación de un socket de tipo servidor y crear una conexión activa para conectarse con un cliente. 
    def Conexion(self):
        #Se guardan los valores del puerto de conexión, accediendo a los valores de los atributos. 
        puerto = self.port

        host = self.localhost

        #Crear servidor. Se crea una instancia de la clase Socket, para crear un socket de tipo servidor.
        
        print("Conectando al servidor...")
        socket_coords = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        #El método bind, recibe una lista como parámetro, la lista guarda las variables host y puerto. Este método permite establecer un enlace
        #con el puerto que se le asigno y con el host que corresponde a la maquina. 
        socket_coords.bind((host,puerto))

        print("Servidor conectado.")
        #Al mandar a llamar al método listen(integer), le indicamos al socket que se establezca en un estado de escucha activa,
        #cuando el socket entra en ese estado de escucha activa, permite que se reciban conexiones. Al método se le manda un número
        #que indica la cantidad de conexiones que puede recibir el socket, al final unicamente entra una sola conexión de cliente.
        #Cuando un cliente (programa o aplicación diferente), logra conectarse, se bloquea la conexión del servidor para no recibir 
        #nuevas conexiones. 
        socket_coords.listen(5)
        
        while True:
            #Cuando se conecta un cliente al servidor, el servidor procesa la petición del cliente, con el método accept(), el cual 
            #devuelve un nuevo socket que permite manejar el flujo de datos que el cliente manda al servidor. 
            cliente,addr = socket_coords.accept()

            #La variable datos, guarda la información que el cliente envio, usando el método recv(1024) el número que se pasa como parametro
            #es el número de bits total que puede recibir, ya que los datos que el cliente envia, estan codificados en paquetes de bits. 
            #El método decode(), permite decodificar el paquete de bits que envio el cliente, una vez decodificado se guarda en la variable datos.
            datos = cliente.recv(1024).decode()
    
            
            #Se reciben los datos correspondientes a las coordenadas geográficas, 
            lon,lat = map(float,datos.split(","))
            
            coord = Coordenada(lon,lat)
    
            direccion = coord.Direccion()
    
            cliente.send(direccion.encode())
        
            cliente.close()

socket_server = Server(5412,'localhost')

socket_server.Conexion()


        
