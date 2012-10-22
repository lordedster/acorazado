/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.effects;

import java.util.Timer;
import java.util.TimerTask;

public class Shaker {

    public int magnitudeX = 0;
    public int magnitudeY = 0;
    private Timer shaker;

    public void shake(int quakeX, int quakeY) {
        // Interference
        magnitudeX += quakeX;
        magnitudeY += quakeY;
        shaker = new Timer();
        TimerTask shake = new TimerTask() {

            public void run() {
                magnitudeX *= -0.9;
                magnitudeY *= -0.9;
                if (magnitudeX == 0 && magnitudeY == 0) {
                    this.cancel();
                }
            }
        };
        shaker.schedule(shake, 0, 50);
    }
}
