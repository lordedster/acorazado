/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game;

import battleships.effects.Slideable;
import battleships.game.maps.Grid;
import battleships.game.maps.Map;
import battleships.game.ships.BattleShip;
import battleships.game.ships.TypeBattleShips;
import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author edster
 */
public class EnemyBoard
        extends Map
        implements Slideable {
    
    private final int IN_X;
    private final int OUT_X;    
    private int x;
    private int y;
    private int cornerX;
    private int cornerY;
    private int displayWidth;
    private int displayHeight;
    private Resources r;
    //private Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
    private Map mapa;    
    private double scaling;
    private Random rnd;
    private int dificultad;
    
    public EnemyBoard(int cornerX, int cornerY, int width, int height, Resources r, Listener l, double scaling, int dificultad) {
        super(10,10,5,l);
        this.displayWidth = width;
        this.displayHeight = height;        
        this.r = r;
        this.scaling = scaling;
        this.dificultad = dificultad;
        rnd = new Random();
        
//        buttonGirar = new Sprite(r.girar);
//        buttonBarcos = new Sprite(r.buttonBarcos);
        IN_X = cornerX;
        OUT_X = cornerX + displayWidth;    
        
        x = OUT_X;
        y = cornerY + (int) (height * 0.8);

        this.cornerX = OUT_X;
        this.cornerX = cornerY;
    }
    
    public void generarMapa(){
        createMap(dificultad);        
        positionGrid();
    }
    
    public void vaciarMapa(){
        Grid[][] x = ObtenerMatriz();
        ReemplazarBarcos(new BattleShip[obtenerBarcos().length]);
        ReemplazarMapa(new Grid[x.length][x[0].length]);
    } 

    public boolean slideOut() {
        int distance = x - OUT_X;
        distance *= 0.8;
        x = OUT_X + distance;
        positionGrid();
        return distance != 0;
    }

    public boolean slideIn() {
        int distance = x - IN_X;
        distance *= 0.8;
        x = IN_X + distance;
        positionGrid();
        return distance != 0;
    }
    
    public final void positionGrid()
    {
        Grid item;
        int newx;
        int newy = 0;
        int value = 0;
        for (int i = 0; i < getHeight(); i++) {
            newx = x;
            for(int j = 0; j < getWidth(); j++){                
                item = getGrid(i,j);
                item.setPosition(newx, newy);
                value = item.getWidth();
                newx += value;
            }            
            newy += value;
        }
//        buttonGirar.setPosition(x + displayWidth - buttonGirar.getWidth(), displayHeight - buttonGirar.getHeight());
//        buttonBarcos.setPosition(x, displayHeight - buttonBarcos.getHeight());
    }
    
    public void paint(Graphics g){
        super.paint(g);
    }
    
    private void createMap(int dificultad){
        for(int i = 0; i < getHeight(); i++ )
        {
            for(int j = 0; j < getWidth(); j++)
            {
                setGrid(i, j, new Grid(TypeBattleShips.AGUA, TypeBattleShips.EMPTY, 
                                        loadSprite(r.water, 1, scaling), 
                                        loadSprite(r.mira, 2, scaling),TypeBattleShips.EMPTY));
            }
        }
        cargarBarcos(dificultad);
    }    
    
    private void cargarBarcos(int dificultad){
        switch(dificultad){
            case TypeBattleShips.FACIL:
                CargarBarcosFacil();
                break;
            case TypeBattleShips.MODERADO:
                break;
            case TypeBattleShips.DIFICIL:
                break;
        }        
    }
    
    private void CargarBarcosFacil(){    
        AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 0);
        AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 1);
        AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2);
        AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 3);
        AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 4);        
//        AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 0);        
//        AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 1);        
//        AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2);
//        AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 3);
//        AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 4);
   }
    
    private void AlgoritmoFacil(int ship, int size, Grid[][] map, int posicion){        
        int direccion = rnd.nextInt(2); 
        int x_primero = 0; 
        int y_primero = 0;
        int x_fin; 
        int y_fin;
        boolean done = false;
        switch(direccion){
            case TypeBattleShips.HORIZONTAL:
                x_primero = rnd.nextInt(map.length - size);
                int p = map.length - size;
                x_fin = x_primero + size - 1;
                y_primero = rnd.nextInt(map[0].length); 
                int p1 = map.length;
                y_fin = y_primero;
                
                break;
            case TypeBattleShips.VERTICAL:
                y_primero = rnd.nextInt(map[0].length - size);
                int p2 = map[0].length - size;
                y_fin = y_primero + size - 1;
                x_primero = rnd.nextInt(map.length); 
                int p3 = map.length;
                x_fin = y_primero;
                
        }
        if(estaOcupado(direccion, x_primero, y_primero, size)){
            AlgoritmoFacil(ship, size, map, posicion);
        } else {
            BattleShip b = new BattleShip(TypeBattleShips.SHIPS[ship], ship, size, direccion, calcMatrizX(x_primero), calcMatrizY(y_primero), r);
            super.AddShip(b, scaling, true, r, posicion);
        }
    }
    
    public void leftButtonPressed(){
        
    }
    
    public void rightButtonPressed(){
        Listener(TypeBattleShips.STATE_MENU);
    }
    
//    private boolean ComprobarOcupacion(int exis, int i, int direccion, int size, Grid[][] map){
//        boolean done = false;
//        switch(direccion){
//            case TypeBattleShips.VERTICAL:
//                for(int z = 0; z < size; z++){
//                    if (map[exis][i].getBarco() != TypeBattleShips.EMPTY){
//                        return true;
//                    }
//                    exis = CalcularDireccionX(direccion, exis);
//                }
//                break;
//            case TypeBattleShips.HORIZONTAL:
//                for(int z = 0; z < size; z++){
//                    if (map[exis][i].getBarco() != TypeBattleShips.EMPTY){
//                        return true;
//                    }
//                    i = CalcularDireccionY(direccion, i);
//                }
//                break;
//        }        
//        return done;
//    }
    
//    private boolean DentroLimite(int numero, int maximo, int minimo){
//        if (numero <= maximo && numero >= minimo){
//            return true;
//        }else {
//            return false;
//        }
//    }
//    
//    private int CalcularDireccionX(int direccion, int exis){        
//        switch(direccion){
//            case TypeBattleShips.VERTICAL:
//                exis += 1;
//                break;
//            case TypeBattleShips.HORIZONTAL:
//                break;
//        }
//        return exis;
//    }
//    
//    private int CalcularDireccionY(int direccion, int i) {
//        switch(direccion){
//            case TypeBattleShips.VERTICAL:                
//                break;
//            case TypeBattleShips.HORIZONTAL:
//                i += 1;
//                break;
//        }
//        return i;
//    }
    
}
