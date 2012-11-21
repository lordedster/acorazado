
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

public class SelectorMisionScreen
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 6;
    public static final int CERO = 0;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    public static final int ATRAS = 5;
    public static final int CUATRO = 4;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;

    public SelectorMisionScreen(int cornerX, int cornerY, int width, int height, Menu.Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        setItem(CERO, new MenuItem(new StringMenuItem("Mision 0: El principio",r)));
        setItem(UNO, new MenuItem(new StringMenuItem("Mision 1: Sin Aviones",r)));
        setItem(DOS, new MenuItem(new StringMenuItem("Mision 2: Destrucción",r)));
        setItem(TRES, new MenuItem(new StringMenuItem("Mision 3: Venganza",r)));
        setItem(CUATRO, new MenuItem(new StringMenuItem("Mision 4: Suicidio",r)));
        setItem(ATRAS, new MenuItem(new StringMenuItem("Atrás",r)));
        
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
    public void visivilityItems(int mision){
        for(int i = 0; i < ITEM_COUNT - 1; i++){
            getItem(i).setVisibile(false);
            if( i <= mision){
                getItem(i).setVisibile(true);
            }
        }
    }
}



