/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.effects;

/**
 *
 * @author edster
 */
import javax.microedition.lcdui.Graphics;

public interface Slideable {

    public abstract void paint(Graphics g);

    public abstract boolean slideOut();

    public abstract boolean slideIn();
}
