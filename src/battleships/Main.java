/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

/**
 * @author edster
 */
public class Main extends MIDlet {

    private BattleshipCanvas gameView;
    private static Main self;

    public static Main getInstance() {
        return self;
    }

    public void startApp() {
         self = this;
        if (gameView == null) {
            this.gameView = new BattleshipCanvas(this);
        }
        Display.getDisplay(this).setCurrent(gameView);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void exit() {
        destroyApp(true);
        notifyDestroyed();
    }
}
