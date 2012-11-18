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
public class StringImageItem {
    
    private String[] texto;
    private int x;
    private int y;
    private int R;
    private int G;
    private int B;
    private boolean visible;

    private Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);
    private int anchor = Graphics.LEFT | Graphics.TOP;
    
    public StringImageItem(String texto, int x, int y){
        
        setearTexto(texto);
        this.x = x;
        this.y = y;
        this.R = 0;
        this.G = 0;
        this.B = 0;
        this.visible = true;
    }
    public StringImageItem(String texto){
        
        setearTexto(texto);
        this.x = 0;
        this.y = 0;
        this.R = 0;
        this.G = 0;
        this.B = 0;
        this.visible = true;
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
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] == '/' && array[i + 1] == 'n'){
                    this.texto[i] = texto.substring(start, i - 1);
                    start = i + 2;
                } 
            }
        }else{
            this.texto[0] = texto;
        }
    }
    
    public void paint(Graphics g){
        g.setFont (font); 
        g.setColor (R, G, B); 
        int newx = x;
        for(int i = 0; i < texto.length; i++){
            g.drawString(texto[i], newx, y, anchor);
            newx  += font.getHeight();
        }
    }
    
    public void setTexto(String texto){
        setearTexto(texto);
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setVisible(boolean v){
        this.visible = v;
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    public void setRGB(int R,int G, int B){
        this.R = R;
        this.G = G;
        this.B = B;
    }    

    public int getWidth() {
        int p;
        int width = 0; 
        for(int i = 0; i < texto.length; i++ ){
            p = font.stringWidth(texto[i]);
            if (p > width){
                width = p;
            }
        }
        return width;
    }


    public int getHeight() {
        return (font.getHeight() * texto.length);
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    
}
