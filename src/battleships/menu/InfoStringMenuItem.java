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
public class InfoStringMenuItem extends StringMenuItem {
    protected String[] texto;
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
    
    public InfoStringMenuItem (String texto, Resources r){
        super ("hola",r);
        setearTexto(texto);
        visible = true;
        X = 0;
        Y = 0;
        setFrame(0);
        left = r.fondo_letra_izq;
        center = r.fondo_letra_cent;
        right = r.fondo_letra_der;
    }
    
     private void setearTexto(String texto){
        char[] array = texto.toCharArray();
        int start = 1;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == '/' && array[i + 1] == 'n'){
                start++;
            } 
        }
        this.texto = new String[start];
        
        if (start > 1){
            start = 0;
            int contador = 0;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] == '/' && array[i + 1] == 'n'){
                    this.texto[contador] = texto.substring(start, i - 1);
                    start = i + 2;
                    contador++;
                } 
            }
            this.texto[contador] = texto.substring(start, texto.length());
            
        }else{
            this.texto[0] = texto;
        }
    }
    
    
    public void paint(Graphics g){  
        if(visible){            
            g.setFont (FONT); 
            g.setColor (R, G, B); 
            int newy = Y;
            for(int i = 0; i < texto.length; i++){
                paintBackground(g);
                g.drawString(texto[i], X, newy, anchor);
                newy  += FONT.getHeight();
            }
        }
    }
    
    private void paintBackground(Graphics g){
        if (left.getWidth() + right.getWidth() < getWidth()){
            g.drawImage(left, X - 4, Y - 3, anchor);
            paintCenterBackground(g);
            g.drawImage(right,X + getWidth() - 8, Y - 3,anchor);
        }else{            
            g.drawImage(left, X - 4, Y - 3, anchor);
            g.drawImage(right,X - 4 + left.getWidth(), Y - 3,anchor);
        }
        
    }
    
    private void paintCenterBackground(Graphics g){
        int x = getWidth() - 14;
        int x_start = X + 6;
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
                break;
            default:
                R = 0;
                G = 0;
                B = 0;  
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
        int p;
        int width = 0; 
        for(int i = 0; i < texto.length; i++ ){
            p = FONT.stringWidth(texto[i]);
            if (p > width){
                width = p;
            }
        }
        return width;
    }
    
    public int getHeight(){
        return (FONT.getHeight() * texto.length)+ 3;
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
    
    public void setString(String string){
        setearTexto(string);
    }
}
