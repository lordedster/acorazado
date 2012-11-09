/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author edster
 */
public class StringMenuItem {
    protected final String texto;
    private boolean visible;
    private int X;
    private int Y;
    private Font FONT;
    private int R;
    private int G;
    private int B;
    
    public StringMenuItem (String texto){
        this.texto = texto;
        visible = true;
        X = 0;
        Y = 0;
        setFrame(0);
    }
    
    
    public void paint(Graphics g){
        g.setFont (FONT); 
        g.setColor (R, G, B); 
        g.drawString(texto, X, Y, Graphics.LEFT | Graphics.TOP);
    }
    
    public void setFrame(int frame){
        switch(frame){
            case 1:
                R = 255;
                G = 255;
                B = 255;
                this.FONT = Font.getFont (Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);   
                break;
            default:
                R = 0;
                G = 0;
                B = 0;
                this.FONT = Font.getFont (Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);   
                break;
        }
    }
    
    public boolean isVisible(){
        return visible;
    }
     
    public void setVisible(boolean bln){
        visible = bln;
    }
    
    public int getWidth(){
        return (texto.length()* 5);
    }
    
    public int getHeight(){
        return FONT.getHeight();
    }
    
    public int getX(){
        return X;
    }
    
    public int getY(){
        return Y;
    }
    
    public void setPosition(int x, int y){
        X = x;
        Y = y;
    }
}
