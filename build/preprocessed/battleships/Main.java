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

    /**
     * Debuelve la instancia de la clase Main
     * @return self
     */
    public static Main getInstance() {
        return self;
    }

    /**
     * Cuando comienza la aplicación, crea levanta el juego.
     */
    public void startApp() {
        self = this;
        if (gameView == null) {
            this.gameView = new BattleshipCanvas(this);
        }
        Display.getDisplay(this).setCurrent(gameView);
    }
    
    /**
     * Código para la pausa de la aplicación,
     * No implementado.
     */
    public void pauseApp() {
    }
    
    /**
     * NO implementado.
     * @param unconditional 
     */
    public void destroyApp(boolean unconditional) {
    }
    
    /**
     * se asegura de liberar la memoria cuando se cierra.
     */
    public void exit() {
        destroyApp(true);
        notifyDestroyed();
    }
}
