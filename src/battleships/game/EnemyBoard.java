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
import battleships.menu.StringImageItem;
import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.LayerManager;
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
    private StringImageItem menu;
    //private Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);   
    private double scaling;
    private Random rnd;
    private int dificultad;
    
    private Sprite misil;
    private boolean ataqueEnCurso;
    private boolean hasShoot;
    private boolean acertoBarco;
    
    private int misil_x;
    private int misil_y;
    private int target_x;
    private int target_y;
    private int map_x;
    private int map_y;
    
    public EnemyBoard(int cornerX, int cornerY, int width, int height, Resources r, Listener l, double scaling, int dificultad) {
        super(10,10,5,l);
        this.displayWidth = width;
        this.displayHeight = height;        
        this.r = r;
        this.scaling = scaling;
        this.dificultad = dificultad;
        rnd = new Random();
        this.misil = loadSprite(r.misil,1,scaling);
        setearMisil();        
        menu = new StringImageItem("Menu");
        menu.setRGB(255, 255, 255);
        acertoBarco = false;
        //setPositionMenu();
        
        ataqueEnCurso = false;
        hasShoot = false;
//        buttonGirar = new Sprite(r.girar);
//        buttonBarcos = new Sprite(r.buttonBarcos);
        IN_X = cornerX;
        OUT_X = cornerX + displayWidth;    
        
        x = OUT_X;
        y = cornerY + (int) (height * 0.8);

        this.cornerX = OUT_X;
        this.cornerX = cornerY;
        
        menu.setPosition(OUT_X, cornerY + displayHeight);
    }
    
    public void generarMapa(Resources r){     
        this.r = r;
        createMap(false);        
        positionGrid();
        selectGrid(0,0);
    }
    
    public void vaciarMapa(){
        Grid[][] x = ObtenerMatriz();
        ReemplazarMapa(new Grid[x.length][x[0].length]);
        ReemplazarBarcos(new BattleShip[obtenerBarcos().length]);
    } 

    public boolean slideOut() {
        int distance = x - OUT_X;
        distance *= 0.5;
        x = OUT_X + distance;
        positionGrid();
        setPositionMenu();
        return distance != 0;
    }

    public boolean slideIn() {
        int distance = x - IN_X;
        distance *= 0.5;
        x = IN_X + distance;
        positionGrid();
        setPositionMenu();
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
        misil.paint(g);
        menu.paint(g);
    }
    
    private void createMap(boolean visibilidad){
        for(int i = 0; i < getHeight(); i++ )
        {
            for(int j = 0; j < getWidth(); j++)
            {
                setGrid(i, j, new Grid(TypeBattleShips.AGUA, TypeBattleShips.EMPTY, 
                                        loadSprite(r.water, 3, scaling), 
                                        loadSprite(r.mira, 2, scaling),TypeBattleShips.EMPTY));
            }
        }
        CargarBarcosFacil(visibilidad);
    }    
    

    
    private void CargarBarcosFacil(boolean v){           
        AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 0,v);        
        AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 1, v);        
        AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2, v );
        AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 3, v);
        AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 4, v);
   }
    
    private void AlgoritmoFacil(int ship, int size, Grid[][] map, int posicion, boolean v){        
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
            AlgoritmoFacil(ship, size, map, posicion, v);
        } else {
            BattleShip b = new BattleShip(TypeBattleShips.SHIPS[ship], ship, size, direccion, calcMatrizX(x_primero), calcMatrizY(y_primero));
            super.AddShip(b, scaling, v, r, posicion);
        }
    }
    
    public void leftButtonPressed(){
        
    }
    
    public void rightButtonPressed(){
        Listener(TypeBattleShips.STATE_MENU_EN_JUEGO);
    }
    
    public void PlayerShoot(int x, int y)
    {
        if(!isAtaqueEnCurso() && !hasShoot())
        {
            if(board[x][y].getEstado()== TypeBattleShips.AGUA ||board[x][y].getEstado()== TypeBattleShips.INTACTO)
            {
                posicionMisil(x,y,board[x][y].getX(),board[x][y].getY());
                if(board[x][y].getBarco()==TypeBattleShips.EMPTY)
                {
                    board[x][y].setEstado(TypeBattleShips.SHOT);
                }
                else
                {
                    board[x][y].setEstado(TypeBattleShips.ACERTADO);
                    acertarBaraco(board[x][y].getBarco());
                    acertoBarco = true;
                }
                hasShoot = true;                
            }
        }
             
    }
    
    public void acertarBaraco(int barco)
    {
        super.ships[barco].Hit();
        if(super.ships[barco].isSunked())
        {
            for(int i = 0; i < 10; i++)
            {
                for (int j = 0; j < 10; j++)
                {
                    if(super.board[i][j].getBarco()==barco)
                    {
                        super.board[i][j].setEstado(TypeBattleShips.HUNDIDO); 
                        Sprite s = loadSprite(SeccionBarco(board[i][j].getSeccion_barco(), barco, r), 2, scaling);
                        if(super.ships[barco].getOrientacion() == TypeBattleShips.VERTICAL){
                            s.setTransform(Sprite.TRANS_MIRROR_ROT270);
                        }
                        s.setFrame(1);
                        s.setPosition(board[i][j].getX(), board[i][j].getY());
                        super.board[i][j].setSprite(s);
                    }
                }
            } 
        } 
    }     
    public void setAcertarBarcoFalse(){
        this.acertoBarco = false;
    }
    
    public boolean getAcertarBarco(){
       return this.acertoBarco;
    }
        
        
    public boolean animarAtaque(){
        misil_y += 60;
        if(target_y <= misil_y){            
            return false;
        }else{
            posicionarMisil();
            return true;
        }
    }
    
    private void posicionMisil(int x, int y, int target_x, int target_y ){
        ataqueEnCurso = true;
        this.target_x = target_x;
        this.target_y = target_y;
        this.map_x = x;
        this.map_y = y;
        misil_y = cornerY - 24;
        misil_x = target_x;
        posicionarMisil();
        
    }
    
    private void posicionarMisil(){
        misil.setPosition(misil_x, misil_y);
    }
    
    public void setearMisil(){
        misil.setPosition(-24, -24);
    }
    
    public void Atacar(boolean b){
        this.ataqueEnCurso = b;        
        pintar();
        setearMisil();
    }
    
    public boolean isAtaqueEnCurso(){
        return ataqueEnCurso;
    }
    
    public void pintar(){          
        if(board[map_x][map_y].getBarco() == TypeBattleShips.EMPTY){
            board[map_x][map_y].setFrameBarco(2);                
        }else{
            board[map_x][map_y].setFrameBarco(1);
        }            
    }
    
    public boolean hasShoot(){
        return hasShoot;
    }
    
    public void setHasShoot(boolean n){
        hasShoot = n;
    }
    
    

    private void setPositionMenu() {       
       menu.setPosition(x + displayWidth - menu.getWidth(), cornerY + displayHeight - menu.getHeight());
    }

    
    
     
     public void pointerEvent(int type, int x, int y) {
        if ( menu.hits(x,y)) {
            if (type == POINTER_RELEASED) {
                rightButtonPressed();
            }
            return;
        }
    } 
     
    public int[][] getMapaParaGuardar(){
        int[][] m = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                m[i][j] = super.board[i][j].getEstado();
            }
        }
        return m;
    } 
}
