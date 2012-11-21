/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.game.Resources;
import battleships.effects.Slideable;
import javax.microedition.lcdui.Graphics;
import battleships.game.ships.TypeBattleShips;

/**
 *
 * @author edster
 */
public class VictoryLoserScreen  
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 0;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int height;
    private int cornerY;
    private int cornerX;
    private boolean seleccionado;
    private StringImageItem menu;
    private StringImageItem ganado;
    private StringImageItem perdido;
    private StringImageItem puntaje;
    private int puntos;
    

    public VictoryLoserScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        this.width = width;   
        this.height = height;
        this.cornerY = cornerY;
        this.cornerX = cornerX;
        seleccionado = true;
        menu = new StringImageItem("Menu");        
        menu.setRGB(255, 255, 255);
        ganado = new StringImageItem("GANASTE!!!!!!");
        ganado.setRGB(255, 255, 255);
        perdido = new StringImageItem("Perdiste");        
        perdido.setRGB(205, 0, 0);
        puntaje = new StringImageItem("Puntaje: " + puntos);
        ganado.setRGB(255, 255, 255);
        
        IN_CX = cornerX + width/2;
        OUT_CX = IN_CX - width;
        x = OUT_CX;
        y = cornerY + height / 2;
        positionItems();
    }

    /**
     * Render the menu
     */
    public void paint(Graphics g) {
        menu.paint(g);
        if(seleccionado){
            ganado.paint(g);
        }else{
            perdido.paint(g);
        }
        puntaje.paint(g);
    }
    /**
     * Move view inwards
     */
    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionItems();
        return distance != 0;
    }

    /**
     * Move view outwards
     */
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItems();
        return distance != 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        puntaje.setTexto("Puntaje: "+ puntos);
        this.puntos = puntos;
    }
    
     /**
     * Lay out menu items horizontally
     */
    public final void positionItems() {        
        menu.setPosition(x + width / 2 - menu.getWidth(),cornerY + height - menu.getHeight());
        perdido.setPosition(x - perdido.getWidth() / 2, y);
        ganado.setPosition(x - ganado.getWidth() / 2, y);
    }
    
    public void setGanadoPerdido(boolean resultado){
        seleccionado = resultado;
    }
    
    public void rightButtonPressed(){        
        clickSelected(TypeBattleShips.STATE_USER);
    }

}



