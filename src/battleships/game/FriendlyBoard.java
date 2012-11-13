/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleships.game;

import battleships.game.maps.Grid;
import battleships.game.maps.Map;
import battleships.game.ships.TypeBattleShips;
import battleships.records.UserData;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public class FriendlyBoard extends Map {
    
    int dificultad;
    int direccionDisparo;//0 up, 1, down, 2 left, 3 rigth
    int XTocado;
    int YTocado;
    boolean tocado;
    boolean u;
    boolean d;
    boolean l;
    boolean r;
    private Random rnd;

    public FriendlyBoard(int width, int height, int MaxShips, Map.Listener listener) {
        super(width, height, MaxShips, listener);
         
    }
    
    public void singlePlayerGame()
    {
        rnd = new Random();
      // dificultad =  ;
        
    
    

    }

        
    
    /*
     *******************************INTELIGENCIA ARTIFICIAL*******************************************
     * Disparar con disparar(nextShot());
     */
    public Shoot nextAcertadoShoot(Shoot s)
    {
        
        switch (direccionDisparo)
        {
            case 0:
            {
                s.setY(s.getY()+1);
                break;
            }
            case 1:
            {
                s.setY(s.getY()-1);
                break;
            }
            case 2:
            {
                s.setX(s.getX()-1);
                break;
            }
            case 3:
            {
                s.setX(s.getX()-1);
                break;
            }
        }
        if(s.getX() > 9 || s.getY() > 9 || s.getX()<0 || s.getY()<0) //el siguiente disparo esta en los limites del tablero?
        {
            getNewDireccion(); // buscamos otra direccion
        }
        else
        {
            if( super.board[s.getX()][s.getY()].getEstado()== TypeBattleShips.SHOT || super.board[s.getX()][s.getY()].getEstado()== TypeBattleShips.HUNDIDO)
            {
                getNewDireccion(); // buscamos otra direccion
            }
            else
            {
                if(super.board[s.getX()][s.getY()].getEstado()== TypeBattleShips.ACERTADO)
                {   
                    s = nextAcertadoShoot(s);
                }
                else
                {
                   //realizar el disparo
                }
            }
        }
        return s;
    }
    
    public Shoot nextShot()
    {
        Shoot s = new Shoot();
        int x;
        int y;
        switch(dificultad){
    
            case TypeBattleShips.FACIL:
            {
                while(true)
                {
                    x = rnd.nextInt(9);
                    y = rnd.nextInt(9);
                    
                    if( super.board[x][y].getEstado()== TypeBattleShips.INTACTO || super.board[x][y].getEstado()== TypeBattleShips.EMPTY )
                    {   
                        break;
                    }
                }
                s.setX(x);
                s.setY(y);
            break;
            }
            case TypeBattleShips.MODERADO:
            {
                if(tocado)
                {
                    s = nextAcertadoShoot(s);
                }
                else
                {
                    while(true)
                     {
                        x = rnd.nextInt(9);
                        y = rnd.nextInt(9);
                    
                        if( super.board[x][y].getEstado()== TypeBattleShips.INTACTO || super.board[x][y].getEstado()== TypeBattleShips.EMPTY  )
                        {   
                            break;
                        }
                        s.setX(x);
                        s.setY(y);
                }
                
               }
            break;
            }
            case TypeBattleShips.DIFICIL:
            {
                 if(tocado)
                {
                    s = nextAcertadoShoot(s);
                }
                else
                {
                 while(true)
                {
                    x = rnd.nextInt(9);
                    y = rnd.nextInt(4);
                    if(x%2 == 0)
                    {
                        y = y + y;
                    }
                    else
                    {
                        y = y + y + 1;
                    }    
                    if( super.board[x][y].getEstado()== TypeBattleShips.INTACTO || super.board[x][y].getEstado()== TypeBattleShips.EMPTY )
                    {   
                        break;
                    }
                }
                s.setX(x);
                s.setY(y);
                }
            break;
            }
                
         
    
           }
         
    
        return s;
    }
    
    public void buscarAcertado ()
    {
       
       for(int i = 0; i < 10; i++)
       {
           for (int j = 0; j < 10; j++)
           {
               if(super.board[i][j].getEstado()==TypeBattleShips.ACERTADO)
               {
                   tocado = true;
                   XTocado = i;
                   YTocado = j;
               }
           }
       }
    }
    
    public void newDirection()
    {
        int a = -1;
        while(a == -1)
        {
            a = rnd.nextInt(4);
            switch (a)
            {
                case 0:
                {
                    if (u == true)
                    {
                        direccionDisparo = 0;
                    }
                    break;
                }
                case 1:
                {
                    if (d == true)
                    {
                        direccionDisparo = 1;
                    }
                    break;
                }
                case 2:
                {
                    if (l == true)
                    {
                        direccionDisparo = 2;
                    }
                    break;
                }
                case 3:
                {
                    if (r == true)
                    {
                        direccionDisparo = 3;
                    }
                    break;
                }
            }
        }
    }
    
    public void getNewDireccion()
    {
        switch (direccionDisparo)
        {
             case 0:
            {
                u=false;
                break;
            }
            case 1:
            {
                d=false;
                break;
            }
            case 2:
            {
                l=false;
                break;
            }
            case 3:
            {
                r=false;
                break;
            }
        }
        direccionDisparo = -1;
        newDirection();
    }
       
    
    public void disparar(Shoot s)
    { 
        if(super.board[s.getX()][s.getY()].getEstado()== TypeBattleShips.INTACTO)
        {
           super.board[s.getX()][s.getY()].setEstado(TypeBattleShips.ACERTADO);
           if(!tocado)
           {
               tocado = true;
               XTocado = s.getX();
               YTocado = s.getY();                       
           }
           acertarBaraco(super.board[s.getX()][s.getY()].getBarco(), super.board[s.getX()][s.getY()].getSeccion_barco());
        }
        else
        {
           super.board[s.getX()][s.getY()].setEstado(TypeBattleShips.SHOT);
        }
    }
    
    
    public void acertarBaraco(int barco, int seccion)
    {
        super.ships[barco].Hit();
        if(super.ships[barco].isSunked())
        {
              for(int i = 0; i < 10; i++)
              {
                   for (int j = 0; j < 10; j++)
                    {
                            if(super.board[i][j].getBarco()==barco)
                                    {
                                        super.board[i][j].setEstado(TypeBattleShips.HUNDIDO); 
                                    }
                    }
               }
            reset();  
        } 
    }
    
    public void reset()
    {
        direccionDisparo = -1;
        u = false;
        d = false;
        l = false;
        r = false;
        tocado = false;
        buscarAcertado();
    }
    /*
     *******************************INTELIGENCIA ARTIFICIAL*******************************************
     * Disparar con disparar(nextShot());
     */
}
