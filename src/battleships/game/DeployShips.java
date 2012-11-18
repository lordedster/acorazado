/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game;

import battleships.ImageHelper;
import battleships.effects.Slideable;
import battleships.game.maps.Map;
import battleships.game.maps.Grid;
import battleships.game.ships.BattleShip;
import battleships.game.ships.TypeBattleShips;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
//import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author edster
 */
public class DeployShips 
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
    private Sprite buttonGirar;
    private Sprite buttonBarcos;
    private Sprite imageShip;
    private BattleShip ship;
    private double scaling;
    private int barcosPuestos;
    
    public DeployShips(int cornerX, int cornerY, int width, int height, Resources r, Listener l, double scaling) {
        super(10,10,5,l);
        this.displayWidth = width;
        this.displayHeight = height;        
        this.r = r;
        this.scaling = scaling;
        this.barcosPuestos = 0;
        ship = null;
        buttonGirar = new Sprite(r.girar);
        buttonBarcos = new Sprite(r.buttonBarcos);
        imageShip = null;
        IN_X = cornerX;
        OUT_X = cornerX + displayWidth;    
        
        x = OUT_X;
        y = cornerY + (int) (height * 0.8);

        this.cornerX = OUT_X;
        this.cornerX = cornerY;    
    }
    
    public void generarMapa(){
        createMap();        
        positionGrid();
    }

    public void paint(Graphics g) {     
        super.paint(g);
        buttonGirar.paint(g);
        buttonBarcos.paint(g);
        pintarBarco(g);       
        
    }

     public boolean slideIn() {
        int distance = x - IN_X;
        distance *= 0.8;
        x = IN_X + distance;
        positionGrid();
        return distance != 0;
    }

    /**
     * Move view outwards
     */
    public boolean slideOut() {
        int distance = x - OUT_X;
        distance *= 0.8;
        x = OUT_X + distance;
        positionGrid();
        return distance != 0;
    }
    
    private void createMap(){
        for(int i = 0; i < getHeight(); i++ )
        {
            for(int j = 0; j < getWidth(); j++)
            {
                setGrid(i, j, new Grid(TypeBattleShips.AGUA, TypeBattleShips.EMPTY, 
                                        loadSprite(r.water, 1, scaling), 
                                        loadSprite(r.mira, 2, scaling),
                                        TypeBattleShips.EMPTY));
            }
        }           
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
        buttonGirar.setPosition(x + displayWidth - buttonGirar.getWidth(), displayHeight - buttonGirar.getHeight());
        buttonBarcos.setPosition(x, displayHeight - buttonBarcos.getHeight());
        posicionBarco();
    }
    
    public void leftButtonPressed() {
        if(buttonBarcos.isVisible()){
            Listener(TypeBattleShips.STATE_BARCOS);
        }
    }
    
    public void rightButtonPressed(){
        Listener(TypeBattleShips.GIRAR);
    }
    
    public void AddShip(BattleShip ship)
    {
        this.ship = ship;
    }
    
    public Grid[][] ObtenerMapa(){
        Grid[][] f = ObtenerMatriz();
        ReemplazarMapa(new Grid[f.length][f[0].length]);
        return f;
    }
    
    public BattleShip[] ObtenerBarcos(){
        BattleShip[] x = obtenerBarcos();
        ReemplazarBarcos(new BattleShip[x.length]);
        return x;
    }
    
    
    public void agregarBarco(BattleShip ship){
        this.ship = ship;
        imageShip = loadSprite("/"+ TypeBattleShips.SHIPS[ship.getType()] + ".png",2, scaling);
        imageShip.setFrame(1);
        posicionBarco();
        buttonBarcos.setVisible(false);
    }
    
    public void pintarBarco(Graphics g){
        if (imageShip != null){
            imageShip.paint(g);
        }
    }
    
    public void colocarBarco(){ 
        if(!estaOcupado(ship.getOrientacion(), obtMatrizX(ship.getX()), obtMatrizY(ship.getY()), ship.getLargo())){
            super.AddShip(ship, scaling, true, r, barcosPuestos);
            positionGrid();
            buttonBarcos.setVisible(true);
            barcosPuestos += 1;
            imageShip = null;
            ship = null;
            if((obtenerBarcos().length) == barcosPuestos){
                barcosPuestos = 0;                
                Listener(TypeBattleShips.STATE_MISION);
            }
        }        
    }
    

    
    private void posicionBarco(){
        if(imageShip != null){
            imageShip.setPosition(ship.getX() + x, ship.getY());
        }
    }
    
    
    
    public void moveUp(){
        if (ship != null){
        Grid[][] map = ObtenerMatriz();
        int shipy = obtMatrizY(ship.getY());
        shipy -= 1;
        shipy = calcMatrizY(shipy);
        if (shipy >= 0){
            ship.setY(shipy);   
        }
        posicionBarco();
        }
    }
    
    public void moveDown(){
        if (ship != null){
            Grid[][] map = ObtenerMatriz();
            int shipy = obtMatrizY(ship.getY());
            shipy += 1;
            int larg = shipy;
            switch(ship.getOrientacion()){
                case TypeBattleShips.VERTICAL:
                    larg = shipy + ship.getLargo() - 1;
                    break;
                case TypeBattleShips.HORIZONTAL:
                    break;
            }
            larg = calcMatrizY(larg);
            if (larg < calcMatrizY(map.length)){
                ship.setY(calcMatrizY(shipy));   
            }
            posicionBarco();
        }
    }
    
    public void moveLeft(){
        if (ship != null){
            Grid[][] map = ObtenerMatriz();
            int shipx = obtMatrizX(ship.getX());
            shipx -= 1;
            shipx = calcMatrizX(shipx);
            if (shipx >= 0){
                ship.setX(shipx);   
            }
            posicionBarco();
        }
    }
    
    public void moveRight(){
        if (ship != null){
            Grid[][] map = ObtenerMatriz();
            int shipx = obtMatrizX(ship.getX());        
            shipx += 1;
            int larg = shipx;
            switch(ship.getOrientacion()){
                case TypeBattleShips.HORIZONTAL:
                    larg = shipx + ship.getLargo() - 1;
                    break;
                case TypeBattleShips.VERTICAL:
                    break;
            }
            larg = calcMatrizX(larg);
            if (larg < calcMatrizX(map[0].length)){            
                ship.setX(calcMatrizX(shipx));   
            }
            posicionBarco();
        }
    }
    
    public void girarBarco(){
        if (ship != null){
            Grid[][] map = ObtenerMatriz();
            if (ship.getOrientacion() == TypeBattleShips.HORIZONTAL){
                ship.setOrientacion(TypeBattleShips.VERTICAL);
            } else {
                ship.setOrientacion(TypeBattleShips.HORIZONTAL);
            }

            switch(ship.getOrientacion()){
                case TypeBattleShips.VERTICAL:
                    int i = obtMatrizY(ship.getY()); // 6
                    if ((i + ship.getLargo()) >= map[i].length) // 11 10
                    {
                        int ix = (i + ship.getLargo()) -  map[i].length;
                        i -= ix;
                        ship.setY(calcMatrizY(i));
                    }
                    imageShip = loadSprite(obtenerImagen(ship.getType()), 2, scaling);  
                    imageShip.setTransform(Sprite.TRANS_MIRROR_ROT270);              
                    imageShip.setFrame(1);
                    break;
                case TypeBattleShips.HORIZONTAL:
                    int b = obtMatrizX(ship.getX());
                    if ((b + ship.getLargo()) >= map.length){
                        int ix = (b + ship.getLargo()) -  map.length;
                        b -= ix;
                        ship.setX(calcMatrizX(b));
                    }                
                    imageShip = loadSprite(obtenerImagen(ship.getType()), 2, scaling);
                    imageShip.setFrame(1);
                    break;
            }
            posicionBarco();
        }
    }
    
    
//    private Image obtenerImagenC(int Type, int orientacion){
//        switch(orientacion){
//            case TypeBattleShips.VERTICAL:
//                switch(Type){
//                case TypeBattleShips.ACORAZADO:
//                    return r.rotate90(r.acorazado);
//                case TypeBattleShips.PORTAAVIONES:
//                    return r.rotate90(r.portaaviones);
//                case TypeBattleShips.SUBMARINO:
//                    return r.rotate90(r.submarino);
//                case TypeBattleShips.DESTRUCTOR:
//                    return r.rotate90(r.destructor);
//                case TypeBattleShips.ESPIA:
//                    return r.rotate90(r.espia);
//                }
//                break;
//            case TypeBattleShips.HORIZONTAL:
//                switch(Type){
//                case TypeBattleShips.ACORAZADO:
//                    return r.acorazado;
//                case TypeBattleShips.PORTAAVIONES:
//                    return r.portaaviones;
//                case TypeBattleShips.SUBMARINO:
//                    return r.submarino;
//                case TypeBattleShips.DESTRUCTOR:
//                    return r.destructor;
//                case TypeBattleShips.ESPIA:
//                    return r.espia;
//                }
//                break;
//        }
//        return null;
//    }
     private Image obtenerImagen(int Type){
                switch(Type){
                case TypeBattleShips.ACORAZADO:
                    return r.acorazado;
                case TypeBattleShips.PORTAAVIONES:
                    return r.portaaviones;
                case TypeBattleShips.SUBMARINO:
                    return r.submarino;
                case TypeBattleShips.DESTRUCTOR:
                    return r.destructor;
                case TypeBattleShips.ESPIA:
                    return r.espia;
                }       
        return null;
    }
    
}
