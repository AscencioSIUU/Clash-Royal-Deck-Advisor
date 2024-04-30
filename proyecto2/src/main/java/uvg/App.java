package uvg;

import java.util.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner teclado = new Scanner(System.in);
        System.out.println( "Bienvenido a Clash Royale Deck Advisor" );
        while(true){
        System.out.println( "¿Qué arquetipo de mazo utilizará?" );
        String myarchetype = teclado.nextLine();
        System.out.println( "¿Qué cartas debe contener?" );
        String archetypeCards = teclado.nextLine();
        System.out.println( "¿El mazo es efectivo en partidas reales?" );
        String effectiveness = teclado.nextLine();
        System.out.println( "Cual es su carta favorita en Clash Royale" );
        String favoriteCard = teclado.nextLine();

            if ( myarchetype.toLowerCase().equals("beatdown")){
                System.out.println("Cartas: Golem, Mega Minion, Bruja Nocturna, Bebé Dragón, Choza de duendes, Rayo, Tronco, Descarga.");
            }
            if ( myarchetype.toLowerCase().equals("ciclado rapido")){
                System.out.println("Cartas: Mosquetera, Montapuercos, Golem de hielo, Esqueletos, Espíritu de hielo, Torre Tesla, Bola de fuego, El Tronco.");           
            }
            if ( myarchetype.toLowerCase().equals("de control")){
                System.out.println("Trío de Mosqueteras, Ariete de batalla, Minero, Horda de esbirros, Golem de hielo, Pandilla de Duendes, Recolector de elixir, El Tronco");
            }
            if ( myarchetype.toLowerCase().equals("spell bait")){
                System.out.println("Cartas: Barril de duendes, Caballero, Princesa, Pandilla de Duendes, Espíritu de hielo, Cohete, Tornado, El Tronco.");
            }
            if ( myarchetype.toLowerCase().equals("spawner")){
                System.out.println("Cartas: Montacarneros, Minero, Mosquetera, Esbirros, Choza de duendes, Choza de bárbaros, Bola de fuego, Flechas.");
            }
            if ( myarchetype.toLowerCase().equals("siege")){
                System.out.println("Cartas: Ballesta, Golem de hielo, Arqueras, Esqueletos, Espíritu de hielo, Torre Infernal, Bola de fuego, El Tronco.");
            }
        

        }
    }
}
