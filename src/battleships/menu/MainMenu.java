package battleships.menu;

import battleships.effects.Slideable;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author edster
 */
public class MainMenu
    extends Menu
    implements Slideable{
    public static final int ITEM_COUNT = 3;    
    public static final int SLOT1 = 0;
    public static final int SLOT2 = 1;
    public static final int SLOT3 = 2;    
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    
     public MainMenu(int cornerX, int cornerY, int width, int height, Listener l, double scaling,String user1,String user2, String user3, Resources r){
        super(ITEM_COUNT,l);
        
        if (user1.equals("")){
            setItem(SLOT1, new MenuItem(loadSprite("/vacio.png", 2, scaling)));
        }else{
            setItem(SLOT1, new MenuItem(new StringMenuItem(user1,r)));
        }
        if (user2.equals("")){
            setItem(SLOT2, new MenuItem(loadSprite("/vacio.png", 2, scaling)));
        }else{
            setItem(SLOT2, new MenuItem(new StringMenuItem(user2,r)));
        }
        if (user3.equals("")){
            setItem(SLOT3, new MenuItem(loadSprite("/vacio.png", 2, scaling)));
        }else{
            setItem(SLOT3, new MenuItem(new StringMenuItem(user3,r)));
        }
        
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