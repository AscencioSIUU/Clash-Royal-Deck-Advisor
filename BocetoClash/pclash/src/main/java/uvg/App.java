package uvg;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{

   
    @SuppressWarnings("deprecation")


    public static void agregarUsuario(String name, Session session){ 
        Scanner sc = new Scanner(System.in);
        System.out.println("Gracias por su colaboración: " + name);
        session.writeTransaction(tx -> {
            tx.run("CREATE (user:Person {name:'" + name + "'})");
            return null;
        });
        System.out.println("Ahora por favor ingrese las 8 cartas que desee recomendar");
        for (int x = 0; x < 8; x++) {
            String card = sc.nextLine();
            System.out.println("Ingresando la carta: " + card);



            if(analizar(card, session)){
                Result result = session.run("MATCH (n) RETURN n.name AS name");
                    result = session.run("MATCH (p:Person {name:'"+name+"'}), (s:Card {name:'"+card+"'}) " +
                    "CREATE (p)-[:recommend]->(s)" +
                    "RETURN p, s");

                while (result.hasNext()){
                    Record record = result.next();
                    System.out.println(record);
                }

                System.out.println("carta encontrada");





            }else{
                session.writeTransaction(tx -> {
                    tx.run("CREATE (user:Card {name: '" + card + "'})"); 
                    return null;
                 });

                Result result = session.run("MATCH (n) RETURN n.name AS name");
                
                result = session.run("MATCH (p:Person {name:'"+name+"'}), (s:Card {name:'"+card+"'}) " +
                "CREATE (p)-[:recommend]->(s)" +
                "RETURN p, s");

                while (result.hasNext()){
                    Record record = result.next();
                    System.out.println(record);
                }
                
            }

            
        System.out.println("Se han ingresado de forma exitosa");
        
        
            
        }
    }

    public static boolean analizar(String card, Session session){
        String cartaBuscada = card;
        String query = "MATCH (n:Card {name: '"+ card +"'}) RETURN n";
        Result result = session.run(query, Values.parameters("name",cartaBuscada));
        return result.hasNext();
    }
    


    public static void getID(){
        System.out.println("MATCH (n {name: \"Arqueras\"})\r\n" + //
                        "RETURN ID(n)");         
    }

    public static void relaciones(Session session, boolean bandera) {
        String query = "MATCH (n)<-[r]-(m)\n" +
        "RETURN n, n.name AS nombre_del_nodo, collect(m.name) AS nodos_relacionados, count(r) AS numero_de_relaciones_que_llegan\n" +
        "ORDER BY numero_de_relaciones_que_llegan DESC";
        Result result = session.run(query);
        int count = 0;
        while (result.hasNext()) {
            if (bandera){
                if (count < 8) {
                    Record record = result.next();
                    Node nodo = record.get("n").asNode();
                    String nombreDelNodo = record.get("nombre_del_nodo").asString();
                    List<Object> nodosRelacionados = record.get("nodos_relacionados").asList();
                    long numeroDeRelaciones = record.get("numero_de_relaciones_que_llegan").asLong();
                    
                    StringBuilder nodosRelacionadosStr = new StringBuilder();
                    for (Object nombreNodoRelacionado : nodosRelacionados) {
                        nodosRelacionadosStr.append(nombreNodoRelacionado.toString()).append(", ");
                    }
                    
                    System.out.println("Nodo: " + nodo + ", Nombre: " + nombreDelNodo + ", Nodos relacionados: " + nodosRelacionadosStr + ", Numero de relaciones que llegan: " + numeroDeRelaciones);
                    count++;
                }
                
            }else{
                
                Record record = result.next();
                Node nodo = record.get("n").asNode();
                String nombreDelNodo = record.get("nombre_del_nodo").asString();
                List<Object> nodosRelacionados = record.get("nodos_relacionados").asList();
                long numeroDeRelaciones = record.get("numero_de_relaciones_que_llegan").asLong();
                    
                StringBuilder nodosRelacionadosStr = new StringBuilder();
                for (Object nombreNodoRelacionado : nodosRelacionados) {
                    nodosRelacionadosStr.append(nombreNodoRelacionado.toString()).append(", ");
                }
                    
                System.out.println("Nodo: " + nodo + ", Nombre: " + nombreDelNodo + ", Nodos relacionados: " + nodosRelacionadosStr + ", Numero de relaciones que llegan: " + numeroDeRelaciones);
                    
            
            }
        }
    }

    // para saber los nodos que tiene cada uno de las personas 
            //*MATCH (n)-[r]->(m)
        //*WHERE ID(n) = 2
        //*RETURN n, type(r), m//

    // Para saber que personas recomendaron la carta
   //MATCH (n)<-[r]-(m)
    //WHERE ID(n) = 3
    //RETURN m, type(r), n

//SABER LA CANTIDAD DE PERSONAS QUE RELACIONARON CON UNA CARTA 
   // MATCH (n)<-[r]-(m)
    //WHERE ID(n) = 3
    //RETURN count(r) AS numero_de_relaciones_que_llegan

    
    
    public static void borrarDatos(Session session){ 
        Result result = session.run("MATCH (n) RETURN n.name AS name");
            while (result.hasNext()){
                Record record = result.next();
                String name = record.get("name").asString();
                System.out.println("Name: " + name);
            }
            result = session.run("""
                MATCH (n)
                DETACH DELETE n
                
                    """)    ;   

            while (result.hasNext()){
                Record record = result.next();
                System.out.println(record);
            }
            

    }

    @SuppressWarnings("deprecation")
    public static void main( String[] args )
    {

        //------------------------------Especificaciones sobre el driver de neo4j----------------------------------------------------------------
        final String dbUri = "neo4j+s://63283284.databases.neo4j.io";
        final String dbUSer = "neo4j";
        final String dbPassword = "rHDX0yIO-6CjWHVnzYOfWiXms-w3ncxYhwRgX8yenDI";
        var driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUSer, dbPassword));
        Session session = driver.session();
            //driver.verifyConnectivity();

        //------------------------------------------------------------------menu del programa--------------------------------------------------
        boolean bandera = true;
        boolean confirmar = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenido al recomendador de cartas de Clash Royale");
        while (bandera){
            System.out.println("Por favor selecciones una de las siguientes opciones a realizar"); 
            System.out.println("1. Recomendar cartas");
            System.out.println("2. Top recomendados");
            System.out.println("3. Borrar base de datos");
            System.out.println("4. Relaciones de las cartas");
            System.out.println("10. Salir");
            
            int opcion = sc.nextInt();
            sc.nextLine(); // Consumir el carácter de nueva línea
            
            switch (opcion){
                case 1:
                    System.out.println("Por favor ingrese su nombre: ");
                    String nombre = sc.nextLine();
                    agregarUsuario(nombre, session);
                    //System.out.println("Por favor ingrese la carta que quiere recomendar: ");
                    //String carta = sc.nextLine();
                    break;

                case 2: 
                    System.out.println("Top cartas recomendadas por el usuario");
                    confirmar = true;
                    relaciones(session, confirmar);
                    break;
                
                case 3:
                    System.out.println("Borrando base de datos...");
                    borrarDatos(session);
                    break;
                case 4:
                    System.out.println("Relaciones de las cartas");
                    relaciones(session,confirmar);
                    break;
                case 10:
                    System.out.println("Finalizacion del programa....");
                    bandera = false; // Sale del bucle
                    
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione nuevamente.");
                    break;
            }
            
        }  
        session.close();
        driver.close();      

    }
}
