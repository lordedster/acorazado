/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.effects.Slideable;
import javax.microedition.lcdui.Graphics;
import battleships.effects.Slideable;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Administrador
 */

    


public class multiPlayerScreen
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 2;
    public static final int CLIENTE = 0;
    public static final int SERVIDOR = 1;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int cornerY;

    public multiPlayerScreen(int cornerX, int cornerY, int width, int height, Menu.Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        this.width = width;       
        this.cornerY = cornerY;
        setItem(CLIENTE, new MenuItem(new StringMenuItem("Unirse a partida", r)));
        setItem(SERVIDOR, new MenuItem(new StringMenuItem ("Crear partida", r)));   
       
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;
        x = OUT_CX;
        y = cornerY + height / 2;
        positionItemsHorizontally();
        positionItemsVertically();
    }

    /**
     * Render the menu
     */
    public void paint(Graphics g) {
        super.paint(g);
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

    /**
     * Lay out menu items horizontally
     */
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
//        MenuItem resume = getItem(RESUME);
//        if(!resume.isVisible()) {
//            newY -= resume.getHeight() / 2;
//        }
        for (int i = 0; i < ITEM_COUNT; i++) {
            MenuItem item = getItem(i);
            item.setCenter(item.getX(), newY);
            newY += item.getHeight();
        }
    }

    /**
     * Hide resume option
     */
//    public void hideResume() {
//        getItem(RESUME).setVisibile(false);
//        positionItemsVertically();
//    }

    /**
     * Show resume option
     */
//    public void showResume() {
//        getItem(RESUME).setVisibile(true);
//        selectItem(RESUME);
//        positionItemsVertically();
//    }
}