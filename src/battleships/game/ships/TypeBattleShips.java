/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game.ships;

/**
 *
 * @author edster
 */
public class TypeBattleShips {
    
    // partes del barco
//    public static final int PROA = 0; //proa
//    public static final int CENTRO = 1; //centro del barco 
//    public static final int POPA = 2; // popa
    
    // Estados del barco y mapa
    public static final int INTACTO = 0;
    public static final int ACERTADO = 1;
    public static final int HUNDIDO = 2;    
    public static final int SHOT = 3;
    public static final int AGUA = 4;
    
    //orientación del barco
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    
    // identificación de los barcos
    public static final int EMPTY = -1; // identificación del barco fantasma - no existe    
    public static final int PORTAAVIONES = 0;
    public static final int PORTAAVIONES_SIZE = 5;
    public static final int ACORAZADO = 1;
     public static final int ACORAZADO_SIZE = 4;
    public static final int DESTRUCTOR = 2;
     public static final int DESTRUCTOR_SIZE = 3;
    public static final int SUBMARINO = 3;
     public static final int SUBMARINO_SIZE = 3;
    public static final int ESPIA = 4;
     public static final int ESPIA_SIZE = 2;
    /**
     * Nombre, largo
     */
    public static final String[]SHIPS = {
        "PortaAviones",
        "Acorazado",
        "Destructor",
        "Submarino",
        "Espia"
    };
    
    
    // Estados del juego
    public static final int STATE_MENU = 15;
    public static final int STATE_INFO = 1;
    public static final int STATE_TRANSITION = 2;
    public static final int STATE_LEVEL = 3;
    public static final int STATE_GAME_OVER = 4;
    public static final int STATE_HIGH_SCORES = 5;
    public static final int STATE_OPTIONS = 6;
    public static final int STATE_SELECTOR = 7;
    public static final int STATE_DEPLOYSHIPS = 8;
    public static final int STATE_BARCOS = 9;
    public static final int STATE_MISION = 10;
    public static final int STATE_MISION_LEVEL = 11;
    public static final int STATE_CAMPAIGN = 12;
    public static final int STATE_MULTI = 13;
    public static final int STATE_PERFIL = 14;
    public static final int STATE_USER = 0;
    public static final int GIRAR = 15;
    
    //Dificultades
    public static final int FACIL = 0;
    public static final int MODERADO = 1;
    public static final int DIFICIL = 2;
}
