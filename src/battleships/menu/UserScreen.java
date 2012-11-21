
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

/**
 *
 * @author edster
 */
import battleships.effects.Slideable;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;

public class UserScreen
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 6;
    public static final int ACTION = 0;
    public static final int CAMPAIGN = 1;
    public static final int MULTIJUGADOR = 2;
    public static final int OPTIONS = 3;
    public static final int EXIT = 5;
    public static final int INFO = 4;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int cornerY;

    public UserScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        this.width = width;       
        this.cornerY = cornerY;
        setItem(ACTION, new MenuItem(new StringMenuItem("Misión Única",r)));
        setItem(CAMPAIGN, new MenuItem(new StringMenuItem("Campaña",r)));
        setItem(MULTIJUGADOR, new MenuItem(new StringMenuItem("Multijugador",r)));
        setItem(OPTIONS, new MenuItem(new StringMenuItem("Opciones",r)));
        setItem(INFO, new MenuItem(new StringMenuItem("Acerca De",r)));
        setItem(EXIT, new MenuItem(new StringMenuItem("Salir",r)));
        
        getItem(CAMPAIGN).setVisibile(false);
        
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
        for (int i = 0; i < ITEM_COUNT; i++) {
            MenuItem item = getItem(i);
            item.setCenter(item.getX(), newY);
            newY += item.getHeight();
        }
    }
}



