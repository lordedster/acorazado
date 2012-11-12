/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.effects.Slideable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class InfoScreen
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 2;
    public static final int LINK = 0;
    public static final int BACK = 1;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int height;
    private int cornerY;
    private Image info;

    public InfoScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling) {
        super(ITEM_COUNT, l);
        this.height = height;
        this.cornerY = cornerY;
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX + width;

        info = loadImage("/info_text.png", 1, scaling);

        x = OUT_CX;
        y = cornerY + (int) (height * 0.8);

        MenuItem item = new MenuItem(loadSprite("/link.png", 2, scaling));
        setItem(LINK, item);
        item.setCenter(x, y);

        y += item.getHeight();
        item = new MenuItem(loadSprite("/back.png", 2, scaling));
        setItem(BACK, item);
        item.setCenter(x, y);
        selectItem(BACK);
    }

    /**
     * Render the information and menu items
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(info, x, cornerY + height / 2, Graphics.VCENTER | Graphics.HCENTER);
    }

    /**
     * Move view inwards
     */
    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionMenuItems();
        return distance != 0;
    }

    /**
     * Move view outwards
     */
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionMenuItems();
        return distance != 0;
    }

    /**
     * Lay out menu items
     */
    public void positionMenuItems() {
        MenuItem item = getItem(0);
        item.setHorizontalCenter(x);

        item = getItem(1);
        item.setHorizontalCenter(x);
    }
}
