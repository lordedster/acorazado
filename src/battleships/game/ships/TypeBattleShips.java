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
    
    // identificación de la parte del barco
    public static final int SECCION_A = 0;
    public static final int SECCION_B = 1;
    public static final int SECCION_C = 2;
    public static final int SECCION_D = 3;
    public static final int SECCION_E = 4;
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
    public static final int STATE_MENU = 0;
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
    public static final int STATE_USER = 15;
    public static final int STATE_CREATE_NOMBRE = 16;
    public static final int GIRAR = 16;
    public static final int STATE_SINGLEPLAYER = 17;
    public static final int SP_TURNO = 18;
    public static final int SP_TURNO_IA = 21;
    public static final int TABLERO_AMIGO = 19;
    public static final int TABLERO_ENEMIGO = 20;
    public static final int BT_SERVIDOR = 22;
    public static final int BT_CLIENTE = 23;
    public static final int MP_SYNC = 24;
    public static final int MP_DISPARAR = 25;
    public static final int STATE_MP = 26;
    public static final int STATE_WIN_OR_LOSSER = 27;
    public static final int SACUDIR = 28;
    public static final int STATE_MENU_EN_JUEGO = 29; 
    public static final int STATE_HISTORIA = 30;
    public static final int STATE_SELECTOR_CAMPAIGN = 31;
    public static final int PUNTAJES_MENU = 40;
    public static final int STATE_MISION_SELECTOR = 32;
    
    //Dificultades
    public static final int FACIL = 0;
    public static final int MODERADO = 1;
    public static final int DIFICIL = 2;
    
    public static final int getLargoBarco(int type){
        switch(type){
            case PORTAAVIONES:
                return PORTAAVIONES_SIZE;
            case ACORAZADO:
                return ACORAZADO_SIZE;
            case DESTRUCTOR:
                return DESTRUCTOR_SIZE;
            case SUBMARINO:
                return SUBMARINO_SIZE;
            case ESPIA:
                return ESPIA_SIZE;
        }
        return -1;
            
    }
}
