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
public class CreateUserScreen
    extends Menu
    implements Slideable{
    public static final int ITEM_COUNT = 3;    
    public static final int RESUME = 0;
    public static final int NEWGAME = 1;
    public static final int BACK = 2;    
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    
    public CreateUserScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling){
        super(ITEM_COUNT, l);
        
        setItem(x, null);
        
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;

        x = OUT_CX;
        y = cornerY + height / 2;
        
    }
    
    public void paint(Graphics g){
        
    }
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItemsHorizontally();
        return distance != 0;
    }

    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
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
