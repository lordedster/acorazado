/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.effects;
import com.nokia.mid.ui.DeviceControl;
import java.util.Timer;
import java.util.TimerTask;

public class LightManager {

    private static final int NORMAL_LIGHT = 60;
    private static final int BRIGHT_LIGHT = 100;
    private static final int PULSE_DURATION = 60;
    private static Timer flasher = new Timer();
    private static Timer avoider;

    public static void avoidDimming() {
        if (avoider == null) {
            try {
                avoider = new Timer();
                avoider.schedule(new TimerTask() {

                    public void run() {
                        DeviceControl.setLights(0, NORMAL_LIGHT);
                    }
                }, 0, 5000);
            }
            catch (Exception e) {
            }
        }
    }

    public static void allowDimming() {
        if (avoider != null) {
            avoider.cancel();
            avoider = null;
        }
    }

    public static void pulse(int repeats) {
        try {
            flasher = new Timer();
            for (int i = 0; i < repeats; i++) {
                flasher.schedule(new TimerTask() {

                    public void run() {
                        DeviceControl.setLights(0, BRIGHT_LIGHT);
                    }
                }, 2 * i * PULSE_DURATION);
                flasher.schedule(new TimerTask() {

                    public void run() {
                        DeviceControl.setLights(0, NORMAL_LIGHT);
                    }
                }, PULSE_DURATION + 2 * i * PULSE_DURATION);
            }
        }
        catch (Exception e) {
        }
    }
}
