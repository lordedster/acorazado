/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.effects.Slideable;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
/**
 *
 * @author edster
 */
public class SelectorScreen 
        extends Menu
        implements Slideable {
    public static final int ITEM_COUNT = 2;
    public static final int BUTTON_OK = 0;
    public static final int BUTTON_CANCEL = 1;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int height;
    private int cornerY;
    private Image mensaje;
    
    public SelectorScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        this.width = width;  
        this.height = height;
        this.cornerY = cornerY;        
        setItem(BUTTON_OK, new MenuItem(new StringMenuItem("Si", r)));
        setItem(BUTTON_CANCEL,new MenuItem(new StringMenuItem("No", r)));
        
        mensaje = loadImage("/automatico.png", 1, scaling);
        
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;

        x = OUT_CX;
        y = cornerY + height / 2;
        positionItemsHorizontally();
        positionItemsVertically();
    }
     public final void positionItemsHorizontally() {
        MenuItem item;
        item = getItem(ITEM_COUNT - 1);
        item.setPosition(x - (width / 2) + (width / 8), item.getY());
        item = getItem(ITEM_COUNT - 2);
        item.setPosition(x + (width / 2) - item.getWidth() - (width / 8), item.getY());
    }

    /**
     * Lay out menu items vertically
     */
    public final void positionItemsVertically() {
        int newY = (int)(y + mensaje.getHeight()  / 1.2);
        for (int i = 0; i < ITEM_COUNT; i++) {
            MenuItem item = getItem(i);
            item.setCenter(item.getX(), newY);
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
         g.drawImage(mensaje, x, cornerY + height / 2, Graphics.VCENTER | Graphics.HCENTER);
    }
    
}
