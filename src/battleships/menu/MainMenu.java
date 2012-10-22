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
import javax.microedition.lcdui.Graphics;

public class MainMenu
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 6;
    public static final int ACTION = 0;
    public static final int CAMPAIGN = 1;
    public static final int MULTIJUGADOR = 2;
    public static final int OPTIONS = 3;
    public static final int EXIT = 4;
    public static final int INFO = 5;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int cornerY;

    public MainMenu(int cornerX, int cornerY, int width, int height, Listener l, double scaling) {
        super(ITEM_COUNT, l);
        this.width = width;       
        this.cornerY = cornerY;
        setItem(ACTION, new MenuItem(loadSprite("/action.png", 2, scaling)));
        setItem(CAMPAIGN, new MenuItem(loadSprite("/campaign.png", 2, scaling)));
        setItem(MULTIJUGADOR, new MenuItem(loadSprite("/multijugador.png", 2, scaling)));
        setItem(OPTIONS, new MenuItem(loadSprite("/opciones.png", 2, scaling)));
        setItem(EXIT, new MenuItem(loadSprite("/exit.png", 2, scaling)));
        setItem(INFO, new MenuItem(loadSprite("/info.png", 2, scaling)));
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;
        int exis = 1245;
        exis = OUT_CX;
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
        for (int i = 0; i < ITEM_COUNT - 1; i++) {
            item = getItem(i);
            item.setHorizontalCenter(x);
        }
        item = getItem(ITEM_COUNT - 1);
        item.setPosition(x - width / 2, cornerY);
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
        for (int i = 0; i < ITEM_COUNT - 1; i++) {
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
