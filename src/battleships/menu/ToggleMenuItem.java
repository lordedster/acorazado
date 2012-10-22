/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import javax.microedition.lcdui.game.Sprite;

public class ToggleMenuItem extends MenuItem {
    private volatile boolean on = false;

    public ToggleMenuItem(Sprite sprite) {
        super(sprite);
    }

    public void set(boolean on) {
        this.on = on;
        sprite.setFrame(getFrame());
    }

    public boolean toggle() {
        this.on = !this.on;
        sprite.setFrame(getFrame());
        return on;
    }

    protected int getFrame() {
        return super.getFrame() + (on ? 0 : 2);
    }

    public boolean isOn() {
        return on;
    }
}
