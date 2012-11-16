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
public class StringScreenItem {
    
    private String[] texto;
    private int x;
    private int y;
    private int width;
    private int height;

    private Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD, Font.SIZE_LARGE);
    private int anchor = Graphics.LEFT | Graphics.TOP;
    
    public StringScreenItem(String texto, int x, int y){
        
        setearTexto(texto);
        this.x = x;
        this.y = y;
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
        start = 0;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] == '/' && array[i + 1] == 'n'){
                this.texto[this.texto.length] = texto.substring(start, i - 1);
                start = i + 2;
            } 
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    
}
