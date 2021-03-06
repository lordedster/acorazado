/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game.maps;

import battleships.ImageHelper;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;
/**
 *
 * @author edster
 */
public class Grid {
    private int estado;
    private int barco;
    private int seccion_barco;
    protected Sprite sprite;
    protected final Sprite mira;
    private volatile boolean selected = false;
    
    public Grid (int estado, int barco, Sprite sprite, Sprite mira, int seccion_barco)
    {
        this.estado = estado;
        this.barco = barco;
        this.sprite = sprite;
        this.mira = mira;
        this.seccion_barco = seccion_barco;
        
        
    }
    
    public void setSelected(boolean s) {
        selected = s;
        mira.setFrame(getFrame());
    }
    
    
    protected int getFrame() {
        return selected ? 1 : 0;
    }
    
    public void setFrameBarco(int frame){
        sprite.setFrame(frame);
    }
    
    public int getFrameBarco(){
        return sprite.getFrame();
    }
    
    

    public boolean isSelected() {
        return selected;
    }

    public boolean isVisible() {
        return sprite.isVisible();
    }

    public void setVisibile(boolean bln) {
        sprite.setVisible(bln);
    }

    public int getWidth() {
        return sprite.getWidth();
    }

    public int getHeight() {
        return sprite.getHeight();
    }

    public int getX() {
        return sprite.getX();
    }

    public int getY() {
        return sprite.getY();
    }
    
    /**
     * Check whether a point is inside the bounding rectangle of the menu item
     */
    public boolean hits(int x, int y) {
        int left = sprite.getX();
        int right = left + sprite.getWidth();
        int top = sprite.getY();
        int bottom = top + sprite.getHeight();
        return x > left && x < right && y > top && y < bottom;
    }

    /**
     * Render menu item
     */
    public void paint(Graphics g) {
        sprite.paint(g);
        mira.paint(g);
    }

    public void setPosition(int x, int y) {
        sprite.setPosition(x, y);
        mira.setPosition(x, y);
    }

    
    public int getEstado()
    {
        return estado;
    }
    
    public void setEstado(int estado)
    {
        this.estado = estado;
    }
    public int getBarco()
    {
        return barco;
    }   

    public void setBarco(int barco) {
        this.barco = barco;
    }

    public int getSeccion_barco() {
        return seccion_barco;
    }

    public void setSeccion_barco(int seccion_barco) {
        this.seccion_barco = seccion_barco;
    }
    
    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }
    
}
