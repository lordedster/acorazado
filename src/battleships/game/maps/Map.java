/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game.maps;

import battleships.game.ships.BattleShip;
import battleships.game.ships.TypeBattleShips;
import battleships.ImageHelper;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
//import javax.microedition.lcdui.Image;
/**
 *
 * @author edster
 */
public class Map {    
    
    protected BattleShip[] ships;
    protected Grid[][] board;
    public static final int POINTER_PRESSED = 0;
    public static final int POINTER_DRAGGED = 1;
    public static final int POINTER_RELEASED = 2;
    private final Listener listener;
    
   
    /**
     * Constructor
     * Genera un mapa de dimenciones fijas estableciendo la cantidad de barcos que tendrá el mapa
     * @param width
     * @param height
     * @param MaxShips
     * @param r
     */
    public Map(int width, int height, int MaxShips, Listener listener) {
        ships = new BattleShip[MaxShips];
        board = new Grid[height][width];
        this.listener = listener;
       
    }
    
    /**
     * Establece el objeto Grid en una posicion determinada
     * @param x posicion fila
     * @param y posicion columna
     * @param item objeto contenido en la posicion 
     */
    protected final void setGrid(int x, int y, Grid item) {
        board[x][y] = item;
    }
    
    /**
     *Debuelve un Grid 
     * @param x posicion vertical del mapa
     * @param y posicion horizontal del mapa
     * @return un objeto Grid
     */
    protected final Grid getGrid(int x, int y){
        return board[x][y];
    }
    
     /**
     * Render the menu items
     */
    protected void paint(Graphics g) {
        // Posición vertical(x)
        for (int i = 0; i < board.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < board[i].length; j++){
                board[i][j].paint(g);
            }
        }
    }
    
    /**
     * establece el siguiente item que está seleccionado
     * @param x posición vertical
     * @param y posicion horizontal
     */
    public void selectGrid(int x, int y){
        // Posición vertical(x)
        for (int i = 0; i < board.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < board[i].length; j++){
                boolean selected = false;
                if(x == i && y == j) {
                    selected  = true;
                }
                board[i][j].setSelected(selected);
            }
        }
    }
    
    /**
     *  devuelve el valor vertical de la selección
     * @return int
     */
    protected int getSelectedX() {
        
        // Posición vertical(x)
        for (int i = 0; i < board.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < board[i].length; j++){
               if(board[i][j].isSelected()){
                   return i;
               } 
            }
        }
        return -1;
    }
    
    /**
     * Devuelve el valor horizontal de la selección
     * @return int
     */
     protected int getSelectedY() {
        
        // Posición vertical(x)
        for (int i = 0; i < board.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < board[i].length; j++){
               if(board[i][j].isSelected()){
                   return j;
               } 
            }
        }
        return -1;
    }
     
     public void selectUp(){
         int sely = getSelectedY();
         if (sely == -1){
             sely = 0;
         }
         selectGrid((Math.max(getSelectedX(), 0) - 1 + board.length) % board.length, sely);
     }
     
     public void selectDown(){
         int sely = getSelectedY();
         if (sely == -1){
             sely = 0;
         }
         selectGrid((getSelectedX() + 1) % board.length, sely);
     }
     
     public void selectLeft(){
         int selx = getSelectedX();
         if (selx == -1){
             selx = 0;
         }
         selectGrid(selx,(Math.max(getSelectedY(), 0) - 1 + board[selx].length)% board.length);
     }
     
     public void selectRight(){
         int selx = getSelectedX();
         if (selx == -1){
             selx = 0;
         }
         selectGrid( selx, (getSelectedY() + 1) % board[selx].length);
     }
     
     /**
     * Manually cause clicked event on the highlighted item
     */
     public void clickSelected(){
         listener.itemClicked(getSelectedX(),getSelectedY());
     }
     
     /**
     * Handle pointer events
     * @param type POINTER_PRESSED, POINTER_DRAGGED or POINTER_RELEASED
     * @param x
     * @param y
     */
    public void pointerEvent(int type, int x, int y) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j <board[i].length; j++)
            {
                if (board[i][j].hits(x, y)) {
                    if (type == POINTER_RELEASED) {
                        listener.itemClicked(i,j);
                        board[i][j].setSelected(false);
                    }
                    else {
                        selectGrid(i,j);
                    }
                    return;
                }
            }
        }
        selectGrid(0,0);
    }
    
    /**
     * Loads a sprite
     * @param fileName
     * @param lines Amount of vertical frames
     * @param scaling Scaling factor
     * @return Loaded sprite
     */
        protected Sprite loadSprite(String fileName, int lines, double scaling) {
            Image i = loadImage(fileName, lines, scaling);
            return new Sprite(i, i.getWidth(), i.getHeight() / lines);
        }
    
        protected Sprite loadSprite(Image file, int lines, double scaling) {
            return new Sprite(file, file.getWidth(), file.getHeight() / lines);
        }
//    protected Sprite loadSprite(String fileName, int lines, double scaling, int orientation) {
//        switch(orientation){
//            case TypeBattleShips.HORIZONTAL:
//                Image i = loadImage(fileName, lines, scaling);
//                return new Sprite(i, i.getWidth(), i.getHeight() / lines);
//            case TypeBattleShips.VERTICAL:                
//                Image x = loadImage(fileName, lines, scaling);
//                return new Sprite(x, x.getWidth()/2, x.getHeight() / lines);
//                
//       }
//        return null;
//    }
//    
//       protected Sprite loadSprite(Image file, int lines, double scaling, int orientation) {
//                switch(orientation){
//            case TypeBattleShips.HORIZONTAL:
//                return new Sprite(file, file.getWidth(), file.getHeight() / lines);
//            case TypeBattleShips.VERTICAL:                
//                return new Sprite(file, file.getWidth(), file.getHeight()/2 / lines);
//                
//       }
//        return null;        
//    }

    /**
     * Load and scale image
     * @param fileName
     * @param lines Amount of vertical frames
     * @param scaling Scaling factor
     * @return Loaded and scaled image
     */
    protected Image loadImage(String fileName, int lines, double scaling) {
        ImageHelper ih = ImageHelper.getInstance();
        Image i = ih.loadImage(fileName);
        return ImageHelper.scaleImage(i, (int) (scaling * i.getWidth() + 0.5),
                                      (int) (scaling * i.getHeight() / lines + 0.5) * lines);
    }
    
    public int Estado(int x, int y)
    {
        return board[x][y].getEstado();
    }
    
    public int Barco(int x, int y)
    {
        return board[x][y].getBarco();
    }
    
    /**
     * agrega el barco al mapa, mostrando el barco dependiendo que si el mapa
     * es de los buenos o de los malos
     * @param ship barco a agregar
     * @param scaling escala que debe tener
     * @param visibility si lo ve el que lo agrega o si se trata del enemigo
     */
    protected void AddShip(BattleShip ship, double scaling, boolean visibility, Resources r, int posicion)
    {
        ships[posicion] = ship;
        int x = obtMatrizX(ship.getX());
        int y = obtMatrizY(ship.getY());
        
        Sprite agua;
        
        for (int i = 0; i < ship.getLargo(); i ++ )
        {
            if (visibility){
                
                agua = loadSprite(SeccionBarco(i, ship.getType(), r),1, scaling);
                if (ship.getOrientacion() == TypeBattleShips.VERTICAL)
                {
                    agua.setTransform(Sprite.TRANS_MIRROR_ROT270);
                }      
               
            } else {
                agua = loadSprite(r.water, 1, scaling);
            }
            board[y][x] = new Grid(TypeBattleShips.INTACTO, ship.getType(), agua, loadSprite(r.mira, 2, scaling), i);
            switch(ship.getOrientacion())
            {
                case TypeBattleShips.HORIZONTAL:
                    x += 1;
                    break;
                case TypeBattleShips.VERTICAL:
                    y += 1;
                    break;                    
            }            
        }        
        
    }
    
    public void setEstado(int estado, int x, int y)
    {
        board[x][y].setEstado(estado);
    }
   
    public int getWidth()
    {
        return board[0].length;
    }
    public int getHeight()
    {
        return board.length;
    }
    
    public void Listener(int state)
    {
        listener.changeState(state);
    }
    
     /**
     * Listener interface for menu events
     */
    public interface Listener {

        void itemClicked(int x, int y);
        
        void changeState(int state);
    }
    
    protected Grid[][] ObtenerMatriz(){
        return board;
    }
    protected void ReemplazarMapa(Grid[][] mapa)
    {
        board = mapa;
    }
    
    protected BattleShip[] obtenerBarcos(){
        return ships;
    }
    
    protected void ReemplazarBarcos(BattleShip[] barcos){
        ships = barcos;
    }   

    public BattleShip[] getShips() {
        return ships;
    }

    public void setShips(BattleShip[] ships) {
        this.ships = ships;
    }

    public Grid[][] getBoard() {
        return board;
    }

    public void setBoard(Grid[][] board) {
        this.board = board;
    }
    
    
    
    
    
    
    protected int calcMatrizX(int x){
        int d = board[0][0].getWidth();
        x = d * x;
        return x;
    }
     protected int calcMatrizY(int y){
        int d = board[0][0].getHeight();
        y = d * y;
        return y;
    }
     
    protected int obtMatrizX(int x){
        int d = board[0][0].getWidth();
        x = (int)(x / d);
        return x;
    }
    protected int obtMatrizY(int y){
        int d = board[0][0].getHeight();
        y = (int)(y / d);
        return y;
    }
    
    private Image SeccionBarco(int seccion, int Type, Resources r){
        switch(Type){
            case TypeBattleShips.PORTAAVIONES:
                switch(seccion){
                    case 0:                        
                        return r.s_portaaviones_0; 
                    case 1:                               
                        return r.s_portaaviones_1;
                    case 2:    
                        return r.s_portaaviones_2;
                    case 3:                                                 
                        return r.s_portaaviones_3;
                    case 4:                               
                        return r.s_portaaviones_4;
                }
                break;
            case TypeBattleShips.ACORAZADO:
                switch(seccion){
                    case 0:                                
                        return r.s_acorazado_0;
                    case 1:              
                        return r.s_acorazado_1;
                    case 2:                               
                        return r.s_acorazado_2;
                    case 3:               
                        return r.s_acorazado_3;
                }
                break;
            case TypeBattleShips.SUBMARINO:
                switch(seccion){
                    case 0:                            
                        return r.s_submarino_0;
                    case 1:                              
                        return r.s_submarino_1;
                    case 2:      
                        return r.s_submarino_2;
                    }
                    break;
                case TypeBattleShips.DESTRUCTOR:
                    switch(seccion){
                    case 0:                     
                        return r.s_destructor_0;
                    case 1:           
                        return r.s_destructor_1;
                    case 2:                     
                        return r.s_destructor_2;
                    }
                    break;
                case TypeBattleShips.ESPIA:
                    switch(seccion){
                    case 0:                              
                        return r.s_espia_0;
                    case 1:                   
                        return r.s_espia_1;
                    }
                    break;
        }
        return null;
    }
    
    protected boolean estaOcupado(int direccion, int x, int y,int largo){
        boolean done = false;
        Grid[][] map = ObtenerMatriz();
        for(int i = 0; i < largo; i++){
            if(map[y][x].getBarco() != TypeBattleShips.EMPTY){
                done = true;
            }
            switch(direccion){
                case TypeBattleShips.HORIZONTAL:
                    x += 1;
                    break;
                case TypeBattleShips.VERTICAL:
                    y += 1;
                    break;
            }
        }
        return done;
    }
    
    public boolean sinBarcos()
    {
        boolean r = true;
        
            for (int i = 0; i < ships.length -1; i++)
            {
                if(!ships[i].isSunked())
                {
                    r=false;
                }
            }
        return r;
    }
    
}
