/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.effects;

import battleships.Main;
import javax.microedition.lcdui.Display;

/**
 *
 * @author edster
 */
public class VibratorManager {
    
    
    public static final int TIME = 100;
    private static boolean vibratorEnabled = true;
    private static VibratorManager instance;
    
    public static VibratorManager getInstance() {
        if (instance == null) {
            instance = new VibratorManager();
        }
        return instance;
    }
   
    
    public void SetEnabled(boolean on)
    {
        vibratorEnabled = on;
        vibrate(TIME);
    }
    
    public boolean isOn()
    {
        return vibratorEnabled;
    }
    
    public boolean ToggleEnabled()
    {
        vibratorEnabled = !vibratorEnabled;        
        vibrate(TIME);
        return vibratorEnabled;
    }
    
    public void vibrate(int duration) 
    {
        if (vibratorEnabled)
        {
            Display display = Display.getDisplay(Main.getInstance());
            display.vibrate(duration);
        }
    }
}
