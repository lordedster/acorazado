/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.game.Resources;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author edster
 */
public class StringMenuItem {
    protected final String texto;
    private boolean visible;
    private int X;
    private int Y;
    private Font FONT = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);
    private int anchor = Graphics.LEFT | Graphics.TOP;
    private int R;
    private int G;
    private int B;
    private Image left;
    private Image center;
    private Image right;
    
    public StringMenuItem (String texto, Resources r){
        this.texto = texto;
        visible = true;
        X = 0;
        Y = 0;
        setFrame(0);
        left = r.fondo_letra_izq;
        center = r.fondo_letra_cent;
        right = r.fondo_letra_der;
    }
    
    
    public void paint(Graphics g){        
        paintBackground(g);
        g.setFont (FONT); 
        g.setColor (R, G, B); 
        g.drawString(texto, X, Y, anchor);
    }
    
    private void paintBackground(Graphics g){
        g.drawImage(left, X - 2, Y - 3, anchor);
        paintCenterBackground(g);
        g.drawImage(right,X + getWidth() - 8,Y - 3,anchor);
    }
    
    private void paintCenterBackground(Graphics g){
        int x = getWidth() - 16;
        int x_start = X + 8;
        for(int i = 0; i < x; i++){
            g.drawImage(center, x_start, Y - 3, anchor);
            x_start++;
        }
    }
    
    public void setFrame(int frame){
        switch(frame){
            case 1:
                R = 255;
                G = 255;
                B = 255;
                //this.FONT = Font.getFont (Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);   
                break;
            default:
                R = 0;
                G = 0;
                B = 0;
                //this.FONT = Font.getFont (Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);   
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
        return (texto.length() * 13);
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
