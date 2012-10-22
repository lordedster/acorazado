/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.effects.Slideable;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author edster
 */
public class BarcosScreen 
        extends Menu
        implements Slideable {
    
    public static final int ITEM_COUNT = 6;
    public static final int PORTAAVIONES = 0;
    public static final int ACORAZADO = 1;
    public static final int SUBMARINO = 2;    
    public static final int DESTRUCTOR = 3;    
    public static final int ESPIA = 4;
    public static final int BACK = 5;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int cornerY;
    
    public BarcosScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling) {
        super(ITEM_COUNT, l);
        
        setItem(PORTAAVIONES, new MenuItem(loadSprite("/PortaAviones.png", 2, scaling)));
        setItem(ACORAZADO, new MenuItem(loadSprite("/Acorazado.png", 2, scaling)));
        setItem(SUBMARINO,new MenuItem(loadSprite("/Submarino.png", 2, scaling)));
        setItem(DESTRUCTOR, new MenuItem(loadSprite("/Destructor.png", 2, scaling)));
        setItem(ESPIA,new MenuItem(loadSprite("/Espia.png", 2, scaling)));
        setItem(BACK, new MenuItem(loadSprite("/back.png", 2, scaling)));
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;

        x = OUT_CX;
        y = cornerY + height / 4;
        positionItemsHorizontally();
        positionItemsVertically();
        
    }
    
    public final void positionItemsHorizontally() {
        MenuItem item;
        for (int i = 0; i < ITEM_COUNT; i++) {
            item = getItem(i);
            item.setHorizontalCenter(x);
        }
    }

    /**
     * Lay out menu items vertically
     */
    public final void positionItemsVertically() {
        int newY = y;
        for (int i = 0; i < ITEM_COUNT; i++) {
            MenuItem item = getItem(i);
            item.setCenter(item.getX(), newY);
            newY += item.getHeight();
        }
    }
    
    /**
     * Move view inwards
     */
    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionItemsHorizontally();
        return distance != 0;
    }

    /**
     * Move view outwards
     */
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItemsHorizontally();
        return distance != 0;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
    }
    
    

    /**
     * Hide resume option
     */
    public void hideItem(int item) {
        getItem(item).setVisibile(false);
        positionItemsVertically();
    }

    /**
     * Show resume option
     */
    public void showItem(int item) {
        getItem(item).setVisibile(true);
        selectItem(item);
        positionItemsVertically();
    }
    
    public void verItems(){
        super.VisibilidadItems(true);
    }
    
}
