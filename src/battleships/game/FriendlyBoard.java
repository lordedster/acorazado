/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleships.game;

import battleships.game.maps.Map;
import battleships.records.UserData;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public class FriendlyBoard extends Map {
    
    int dificultad;
    int direccionDisparo;
    int XTocado;
    int YTocado;
    boolean tocado;
    private Random rnd;

    public FriendlyBoard(int width, int height, int MaxShips, Listener listener) {
        super(width, height, MaxShips, listener);
         
    }
    
    public void singlePlayerGame()
    {
        rnd = new Random();
       // dificultad = ;
    
    
    
    
    }

}
