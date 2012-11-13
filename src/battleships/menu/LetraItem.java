/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author edster
 */
public class LetraItem extends MenuItem {
    private int codigo;
    
    public LetraItem(Sprite sprite, int codigo){
        super(sprite);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
}
