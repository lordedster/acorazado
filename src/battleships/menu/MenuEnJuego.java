/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;


import battleships.effects.Slideable;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;

public class MenuEnJuego
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 2;
    public static final int GUARDAR = 0;
    public static final int CONTINUAR = 1;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int cornerY;

    public MenuEnJuego(int cornerX, int cornerY, int width, int height, Menu.Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        this.width = width;       
        this.cornerY = cornerY;
        setItem(GUARDAR, new MenuItem(new StringMenuItem("Menu Principal", r)));
        setItem(CONTINUAR, new MenuItem(new StringMenuItem("Continuar", r)));
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;
        x = OUT_CX;
        y = cornerY + height / 2;
        positionItemsHorizontally();
        positionItemsVertically();
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
   
    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionItemsHorizontally();
        return distance != 0;
    }

    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItemsHorizontally();
        return distance != 0;
    }

    public final void positionItemsHorizontally() {
        MenuItem item;
        for (int i = 0; i < ITEM_COUNT; i++) {
            item = getItem(i);
            item.setHorizontalCenter(x);
        }
    }

    public final void positionItemsVertically() {
        int newY = y;
        for (int i = 0; i < ITEM_COUNT; i++) {
            MenuItem item = getItem(i);
            item.setCenter(item.getX(), newY);
            newY += item.getHeight();
        }
    }
}




