/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import javax.microedition.lcdui.game.Sprite;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;

public class ToggleMenuItem extends MenuItem {
    private volatile boolean on = false;
    private StringMenuItem[] opciones;
    private int seleccion;

    public ToggleMenuItem(Resources r, String texto, String[] opciones) {
        super(new StringMenuItem(texto, r));
        this.opciones = new StringMenuItem[opciones.length];
        for (int i = 0; i < opciones.length; i++) {
            this.opciones[i] = new StringMenuItem(opciones[i], r);
        }
        seleccion = 0;
    }
    public void paint(Graphics g){
        super.paint(g); 
        opciones[seleccion].paint(g);
    }

    public void set(boolean on) {
        this.on = on;
        seleccion = on ? 1 : 0;
        
    }
    public void setSelected(boolean s) {
        super.setSelected(s); 
        int q = s ? 1 : 0;
        for (int i = 0; i < opciones.length; i++) {
            opciones[i].setFrame(q);
        }
        
    }

    public boolean toggle() {
        this.on = !this.on;        
        seleccion = on ? 1 : 0;
        return on;
    }
    
    public boolean isOn() {
        return on;
    }
    
    
    public void setHorizontalCenter(int x) {
        setPosition(x - getWidth()/2, super.getY());
    }

    public void setVerticalCenter(int y) {
        setPosition(super.getX(), y - super.getHeight()/2);
    }
        
    public void setPosition(int x, int y){
        super.setPosition(x, y);
        for (int i = 0; i < opciones.length; i++) {
            opciones[i].setPosition(x + super.getWidth(), y);
        }
    }
    
    public int getWidth(){
        int a = 0;
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].getWidth()> a){
                a = opciones[i].getWidth();
            }
        }
        return (super.getWidth() + a);        
    }
    
}
