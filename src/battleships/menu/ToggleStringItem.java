/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author edster
 */
public class ToggleStringItem extends MenuItem {
    
    private StringMenuItem facil;
    private StringMenuItem intermedio;
    private StringMenuItem dificil;
    private int seleccion;
    private boolean selected;
    
    public ToggleStringItem(Resources r, int dificuldad){
        super(new StringMenuItem("Dificultad:", r));
        this.seleccion = dificuldad;
        facil = new StringMenuItem("Fácil", r);
        intermedio = new StringMenuItem("Medio", r);
        dificil = new StringMenuItem("Dificil", r);
        selected = false;
    }
    
    public void paint(Graphics g){
        super.paint(g);
        switch(seleccion){
            case 0:
                facil.paint(g);
                break;
            case 1:
                intermedio.paint(g);
                break;
            case 2:
                dificil.paint(g);
                break;
        }
    }
    public void setHorizontalCenter(int x) {
        setPosition(x - getWidth()/2, super.getY());
    }

    public void setVerticalCenter(int y) {
        setPosition(super.getX(), y - super.getHeight()/2);
    }
        
    public void setPosition(int x, int y){
        super.setPosition(x, y);
        facil.setPosition(x + super.getWidth(), y);
        intermedio.setPosition(x + super.getWidth(), y);
        dificil.setPosition(x + super.getWidth(), y);
    }

    public void setFrameSelected(int frame) {
        seleccion = frame;
    }    
    
    public void setFrame(int frame){
        switch(frame){
            case 1:
                super.texto.setFrame(1);               
                facil.setFrame(1);
                intermedio.setFrame(1);
                dificil.setFrame(1);  
                break;
            default:
               
                super.texto.setFrame(0);       
                facil.setFrame(0);
                intermedio.setFrame(0);
                dificil.setFrame(0); 
                break;
        }
    }
    public void setSelected(boolean s) {
        selected = s;
        super.setSelected(s);
        setFrame(selected ? 1 : 0);
        setFrame(selected ? 1 : 0);
        
    }

    public int toggle() {
        switch(seleccion){
            case 0:
                seleccion = 1;
                return 1;
            case 1:
                seleccion = 2;
                return 2;
            case 2:
                seleccion = 0;
                return 0;
        }
        return 0;
    }

    protected int getFrameSelected() {
        return seleccion;
    }    
    
    public int getWidth(){
        return (super.getWidth() + intermedio.getWidth());        
    }
    
    public boolean isSelected() {
        return selected;
    }
}
