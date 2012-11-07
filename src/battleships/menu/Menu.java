/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.ImageHelper;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Menu {

    public static final int POINTER_PRESSED = 0;
    public static final int POINTER_DRAGGED = 1;
    public static final int POINTER_RELEASED = 2;
    private final int anchor;
    private final MenuItem[] items;
    private final Listener listener;

    /**
     * Constructor
     * @param capacity Amount of menu items
     * @param listener Listener for menu events
     */
    protected Menu(int capacity, Listener listener, int anchor) {
        items = new MenuItem[capacity];
        this.listener = listener;
        this.anchor = anchor;
    }

    /**
     * Sets menu item to given index
     * @param i Index for the item
     * @param item Menu item
     */
    protected final void setItem(int i, MenuItem item) {
        items[i] = item;
    }

    /**
     * Get a menu item
     * @param i Index of the wanted menu item
     * @return Menu item as a MenuItem object
     */
    protected final MenuItem getItem(int i) {
        return items[i];
    }

    /**
     * Render the menu items
     */
    protected void paint(Graphics g) {
        for (int i = 0; i < items.length; i++) {
            items[i].paint(g, anchor);
        }
    }

    /**
     * Sets the a menu item in selected state
     * @param selected Index of the item to be set selected
     */
    public void selectItem(int selected) {
        for (int i = 0; i < items.length; i++) {
            items[i].setSelected(i == selected);
        }
    }

    /**
     * Get the index of currently selected menu item.
     * @return Currently selected item or -1, if none is selected
     */
    private int getSelected() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isSelected()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Select next menu item. Wraps to first item, if current item is the last one.
     */
    public void selectNext() {
        selectItem((getSelected() + 1) % items.length);
        // Skip, if the item is hidden
        if(!getItem(getSelected()).isVisible()) {
            selectNext();
        }
    }

    /**
     * Select previous menu item. Wraps to last item, if current item is the first one.
     */
    public void selectPrev() {
        selectItem((Math.max(getSelected(), 0) - 1 + items.length) % items.length);
        // Skip, if the item is hidden
        if(!getItem(getSelected()).isVisible()) {
            selectPrev();
        }
    }

    /**
     * Manually cause clicked event on the highlighted item
     */
    public void clickSelected() {
        listener.itemClicked(getSelected());
    }
    
    public void clickSelected(int item) {
        listener.itemClicked(item);
    }

    /**
     * Handle pointer events
     * @param type POINTER_PRESSED, POINTER_DRAGGED or POINTER_RELEASED
     * @param x
     * @param y
     */
    public void pointerEvent(int type, int x, int y) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].hits(x, y)) {
                if (type == POINTER_RELEASED) {
                    listener.itemClicked(i);
                    items[i].setSelected(false);
                }
                else {
                    selectItem(i);
                }
                return;
            }
        }
        selectItem(-1);
    }

    /**
     * Loads a sprite
     * @param fileName
     * @param lines Amount of vertical frames
     * @param scaling Scaling factor
     * @return Loaded sprite
     */
    protected Sprite loadSprite(String fileName, int lines, double scaling) {
        Image i = loadImage(fileName, lines, scaling);
        return new Sprite(i, i.getWidth(), i.getHeight() / lines);
    }

    /**
     * Load and scale image
     * @param fileName
     * @param lines Amount of vertical frames
     * @param scaling Scaling factor
     * @return Loaded and scaled image
     */
    protected Image loadImage(String fileName, int lines, double scaling) {
        ImageHelper ih = ImageHelper.getInstance();
        Image i = ih.loadImage(fileName);
        return ImageHelper.scaleImage(i, (int) (scaling * i.getWidth() + 0.5),
                                      (int) (scaling * i.getHeight() / lines + 0.5) * lines);
    }

    /**
     * Listener interface for menu events
     */
    public interface Listener {

        void itemClicked(int item);
    }
    
    protected void VisibilidadItems(boolean on){
        for (int i = 0; i < items.length; i++){
            items[i].setVisibile(on);
        }
    }
}
