/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game.ships;

import battleships.game.Resources;
/**
 *
 * @author edster
 */
public class BattleShip {
    
    private Resources r;
    private String name; 
    private int type;
    private int lengh;
    private int orientacion; 
    private int x;
    private int y;
    private int hits;
    
    public BattleShip(String name, int type, int lengh, int orientacion, int x, int y, Resources r)
    {
        this.r = r;
        this.name = name;
        this.lengh = lengh;
        this.x = x;
        this.y = y;
        this.orientacion = orientacion;
        hits = 0;
        this.type = type;
    }
    
    public int getOrientacion()
    {
        return orientacion;
    }
    
    public int getLargo()
    {
        return lengh;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public boolean isSunked()
    {
        if (lengh > hits) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public void Hit()
    {
        hits += 1;
    }
    
    public String Name()
    {
        return name;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void setOrientacion(int orientacion){
        this.orientacion = orientacion;
    }
}
