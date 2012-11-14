/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.game.Resources;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author edster
 */
public class LetraItem extends MenuItem {
    private int codigo;
    
    public LetraItem(String letra, int codigo, Resources r){
        super(new StringMenuItem(letra, r));
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
}
