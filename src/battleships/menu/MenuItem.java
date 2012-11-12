/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

public class MenuItem {
    protected final Sprite sprite;    
    protected final StringMenuItem texto;
    protected final boolean tipo;
    private volatile boolean selected = false;

    public MenuItem(Sprite sprite) {
        this.sprite = sprite;
        this.texto = null;
        this.tipo = true;
    }
    
    public MenuItem(StringMenuItem texto) {
        this.sprite = null;
        this.texto = texto;
        this.tipo = false;
    }
    

    public void setSelected(boolean s) {
        selected = s;
        if (tipo){
            sprite.setFrame(getFrame());
        } else {
            texto.setFrame(getFrame());
        }
        
    }

    protected int getFrame() {
        return selected ? 1 : 0;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isVisible() {
        if(tipo){
            return sprite.isVisible();
        }else{
            return texto.isVisible();
        }
        
    }

    public void setVisibile(boolean bln) {
        if(tipo){
            sprite.setVisible(bln);
        }else{
            texto.setVisible(bln);
        }
    }

    public int getWidth() {
        if(tipo){
            return sprite.getWidth();
        }else{
            return texto.getWidth();
        }
    }

    public int getHeight() {
        if(tipo){
            return sprite.getHeight();
        }else{
            return texto.getHeight();
        }
    }

    public int getX() {
        if(tipo){
            return sprite.getX();
        }else{
            return texto.getX();
        }
        
    }

    public int getY() {
        if(tipo){
            return sprite.getY();
        }else{
            return texto.getY();
        }
    }

    public void setCenter(int x, int y) {
        if(tipo){
            sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
        }else{
             texto.setPosition(x - texto.getWidth()/2, y - texto.getHeight()/2);
        }
    }

    public void setHorizontalCenter(int x) {
        if(tipo){
            sprite.setPosition(x - sprite.getWidth()/2, sprite.getY());
        }else{
            texto.setPosition(x - texto.getWidth()/2, texto.getY());
        }
    }

    public void setVerticalCenter(int y) {
        if(tipo){
            sprite.setPosition(sprite.getX(), y - sprite.getHeight()/2);
        }else{
            texto.setPosition(texto.getX(), y - texto.getHeight()/2);
        }
    }

    public void setPosition(int x, int y) {
        if(tipo){
            sprite.setPosition(x, y);
        }else{
            texto.setPosition(x, y);
        }
    }

    /**
     * Check whether a point is inside the bounding rectangle of the menu item
     */
    public boolean hits(int x, int y) {
        int left, right,top,bottom = 0;
        if(tipo){
            left = sprite.getX();
            right = left + sprite.getWidth();
            top = sprite.getY();
            bottom = top + sprite.getHeight();
        }else{
            left = texto.getX();
            right = left + texto.getWidth();
            top = texto.getY();
            bottom = top + texto.getHeight();
        }
        return x > left && x < right && y > top && y < bottom;
    }

    /**
     * Render menu item
     */
    public void paint(Graphics g) {
        if (tipo){
            sprite.paint(g);            
        } else {
            texto.paint(g);
        }
    }
}
