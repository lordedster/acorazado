/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.effects.Slideable;
import battleships.game.Resources;
import battleships.menu.ToggleStringItem;
import javax.microedition.lcdui.Graphics;
/**
 *
 * @author edster
 */
public class OptionScreen  
        extends Menu
        implements Slideable {
    
    public static final int ITEM_COUNT = 5;
    public static final int VIBRATOR = 0;
    public static final int SOUNDS = 1;
    public static final int DIFICULTAD = 2;
    public static final int USUARIO = 3;
    public static final int BACK = 4;
    public final int OUT_CX;
    public final int IN_CX;
    private final ToggleMenuItem sounds;
    private final ToggleMenuItem vibrator; 
    private final ToggleStringItem dificul;
    private int x;
    private int y;
    
    public OptionScreen(int cornerX, int cornerY, int width, int height, Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
//        this.width = width;       
//        this.cornerY = cornerY;
        String[] s = {"Off","On"};
        vibrator = new ToggleMenuItem(r,"Vibración:", s);
        setItem(VIBRATOR, vibrator);
        sounds = new ToggleMenuItem(r,"Sonido:", s);
        setItem(SOUNDS, sounds);        
        dificul = new ToggleStringItem(r, 0);
        setItem(DIFICULTAD, dificul);
        setItem(USUARIO, new MenuItem(new StringMenuItem("Cambiar de Perfil", r)));        
        setItem(BACK, new MenuItem(new StringMenuItem("Atrás", r)));
        
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
    
    public void setDificultad(int dificultad){
        dificul.setFrameSelected(dificultad);
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
    
    public int toogleDificultad(){
        return dificul.toggle();
    }
}
