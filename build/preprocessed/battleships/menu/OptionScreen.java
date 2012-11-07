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
public class OptionScreen  
        extends Menu
        implements Slideable {
    
    public static final int ITEM_COUNT = 3;
    public static final int VIBRATOR = 0;
    public static final int SOUNDS = 1;
    public static final int BACK = 2;
    public final int OUT_CX;
    public final int IN_CX;
    private final ToggleMenuItem sounds;
    private final ToggleMenuItem vibrator;
    private int x;
    private int y;
    
    public OptionScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling) {
        super(ITEM_COUNT, l, Graphics.LEFT | Graphics.TOP);
//        this.width = width;       
//        this.cornerY = cornerY;
        vibrator = new ToggleMenuItem(loadSprite("/sensors.png", 4, scaling));
        setItem(VIBRATOR, vibrator);
        sounds = new ToggleMenuItem(loadSprite("/sounds.png", 4, scaling));
        setItem(SOUNDS, sounds);
        setItem(BACK, new MenuItem(loadSprite("/back.png", 2, scaling)));
        
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;

        x = OUT_CX;
        y = cornerY + height / 2;
        positionItemsHorizontally();
        positionItemsVertically();
    }
    
    
     public final void positionItemsHorizontally() {
        MenuItem item;
        for (int i = 0; i < ITEM_COUNT; i++) {
            item = getItem(i);
            item.setHorizontalCenter(x);
        }
//        item = getItem(ITEM_COUNT - 1);
//        item.setPosition(x - width / 2, cornerY);
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
    }
    
    public void setVibrator(boolean on) {
        vibrator.set(on);
    }
    
    public void setSounds(boolean on) {
        sounds.set(on);
    }
    
    /**
     * Toggle sounds on and off     
     */
    public boolean toggleSounds() {
        return sounds.toggle();
    }
    
    public boolean toggleVibrator() {
        return vibrator.toggle();
    }
}
