/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.game.Sprite;

public class MenuItem {
    protected final Sprite sprite;    
    protected final StringItem texto;
    protected final boolean tipo;
    private volatile boolean selected = false;

    public MenuItem(Sprite sprite) {
        this.sprite = sprite;
        this.texto = new StringItem(null, null);
        this.tipo = true;
    }
    
    public MenuItem(StringItem texto) {
        this.sprite = null;
        this.texto = texto;
        this.tipo = false;
    }
    

    public void setSelected(boolean s) {
        selected = s;
        if (tipo)
        {
            sprite.setFrame(getFrame());
        }
    }

    protected int getFrame() {
        return selected ? 1 : 0;
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

    public void setCenter(int x, int y) {
        sprite.setPosition(x - sprite.getWidth()/2, y - sprite.getHeight()/2);
    }

    public void setHorizontalCenter(int x) {
        sprite.setPosition(x - sprite.getWidth()/2, sprite.getY());
    }

    public void setVerticalCenter(int y) {
        sprite.setPosition(sprite.getX(), y - sprite.getHeight()/2);
    }

    public void setPosition(int x, int y) {
        sprite.setPosition(x, y);
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
    public void paint(Graphics g, int anchor) {
        if (tipo){
            sprite.paint(g);            
        } else {
            g.drawString(texto.getText(), texto.getPreferredHeight(), texto.getPreferredWidth(), anchor);
        }
    }
}
