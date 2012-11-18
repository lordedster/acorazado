/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package battleships.game;

import battleships.effects.Slideable;
import battleships.game.maps.Grid;
import battleships.game.maps.Map;
import battleships.game.ships.BattleShip;
import battleships.game.ships.TypeBattleShips;
import battleships.records.UserData;
import java.util.Random;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Administrador
 */
public class FriendlyBoard extends Map implements Slideable {
    
    int dificultad;
    int direccionDisparo;//0 up, 1, down, 2 left, 3 rigth
    int XTocado;
    int YTocado;
    boolean tocado;
    boolean ud;
    boolean dd;
    boolean ld;
    boolean rd;
    private Random rnd;
        private final int IN_X;
    private final int OUT_X;    
    private int x;
    private int y;
    private int cornerX;
    private int cornerY;
    private int displayWidth;
    private int displayHeight;
    private Resources r;
    //private Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);   
    private double scaling;


    public FriendlyBoard(int cornerX, int cornerY, int width, int height, Resources r, Listener l, double scaling, int dificultad) {
                super(10,10,5,l);
        this.displayWidth = width;
        this.displayHeight = height;        
        this.r = r;
        this.scaling = scaling;
        this.dificultad = dificultad;
        rnd = new Random();
        
//        buttonGirar = new Sprite(r.girar);
//        buttonBarcos = new Sprite(r.buttonBarcos);
        IN_X = cornerX;
        OUT_X = cornerX + displayWidth;    
        
        x = OUT_X;
        y = cornerY + (int) (height * 0.8);

        this.cornerX = OUT_X;
        this.cornerX = cornerY;
         
    }
    
    public void singlePlayerGame()
    {
        rnd = new Random();
      // dificultad =  ;
        
    
    

    }

    public void generarMapa(){
        createMap(dificultad);        
        positionGrid();
    }
    
    public void vaciarMapa(){
        Grid[][] x = ObtenerMatriz();
        ReemplazarBarcos(new BattleShip[obtenerBarcos().length]);
        ReemplazarMapa(new Grid[x.length][x[0].length]);
    } 

    public boolean slideOut() {
        int distance = x - OUT_X;
        distance *= 0.8;
        x = OUT_X + distance;
        positionGrid();
        return distance != 0;
    }

    public boolean slideIn() {
        int distance = x - IN_X;
        distance *= 0.8;
        x = IN_X + distance;
        positionGrid();
        return distance != 0;
    }
    
    public final void positionGrid()
    {
        Grid item;
        int newx;
        int newy = 0;
        int value = 0;
        for (int i = 0; i < getHeight(); i++) {
            newx = x;
            for(int j = 0; j < getWidth(); j++){                
                item = getGrid(i,j);
                item.setPosition(newx, newy);
                value = item.getWidth();
                newx += value;
            }            
            newy += value;
        }
//        buttonGirar.setPosition(x + displayWidth - buttonGirar.getWidth(), displayHeight - buttonGirar.getHeight());
//        buttonBarcos.setPosition(x, displayHeight - buttonBarcos.getHeight());
    }
    
    public void paint(Graphics g){
        super.paint(g);
    }
    
    private void createMap(int dificultad){
        for(int i = 0; i < getHeight(); i++ )
        {
            for(int j = 0; j < getWidth(); j++)
            {
                setGrid(i, j, new Grid(TypeBattleShips.AGUA, TypeBattleShips.EMPTY, 
                                        loadSprite(r.water, 1, scaling), 
                                        loadSprite(r.mira, 2, scaling),TypeBattleShips.EMPTY));
            }
        }
        cargarBarcos(dificultad);
    }    
    
    private void cargarBarcos(int dificultad){
        switch(dificultad){
            case TypeBattleShips.FACIL:
                CargarBarcosFacil();
                break;
            case TypeBattleShips.MODERADO:
                break;
            case TypeBattleShips.DIFICIL:
                break;
        }        
    }
    
    private void CargarBarcosFacil(){    
//        AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 0);
//        AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 1);
//        AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2);
//        AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 3);
//        AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 4);        
        AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 0);        
        AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 1);        
        AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2);
        AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 3);
        AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 4);
        r = null;
   }
    
    private void AlgoritmoFacil(int ship, int size, Grid[][] map, int posicion){        
        int direccion = rnd.nextInt(2); 
        int x_primero = 0; 
        int y_primero = 0;
        int x_fin; 
        int y_fin;
        boolean done = false;
        switch(direccion){
            case TypeBattleShips.HORIZONTAL:
                x_primero = rnd.nextInt(map.length - size);
                int p = map.length - size;
                x_fin = x_primero + size - 1;
                y_primero = rnd.nextInt(map[0].length); 
                int p1 = map.length;
                y_fin = y_primero;
                
                break;
            case TypeBattleShips.VERTICAL:
                y_primero = rnd.nextInt(map[0].length - size);
                int p2 = map[0].length - size;
                y_fin = y_primero + size - 1;
                x_primero = rnd.nextInt(map.length); 
                int p3 = map.length;
                x_fin = y_primero;
                
        }
        if(estaOcupado(direccion, x_primero, y_primero, size)){
            AlgoritmoFacil(ship, size, map, posicion);
        } else {
            BattleShip b = new BattleShip(TypeBattleShips.SHIPS[ship], ship, size, direccion, calcMatrizX(x_primero), calcMatrizY(y_primero), r);
            super.AddShip(b, scaling, true, r, posicion);
        }
    }
    
    public void leftButtonPressed(){
        
    }
    
    public void rightButtonPressed(){
        Listener(TypeBattleShips.STATE_MENU);
    }
        
    
    /*
     *******************************INTELIGENCIA ARTIFICIAL*******************************************
     * Disparar con disparar(nextShot());
     */
    
    public void pcshoot()
    {
        disparar(nextShot());
        Listener(TypeBattleShips.SP_TURNO);
    }
    
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
                    if (ud == true)
                    {
                        direccionDisparo = 0;
                    }
                    break;
                }
                case 1:
                {
                    if (dd == true)
                    {
                        direccionDisparo = 1;
                    }
                    break;
                }
                case 2:
                {
                    if (ld == true)
                    {
                        direccionDisparo = 2;
                    }
                    break;
                }
                case 3:
                {
                    if (rd == true)
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
                ud=false;
                break;
            }
            case 1:
            {
                dd=false;
                break;
            }
            case 2:
            {
                ld=false;
                break;
            }
            case 3:
            {
                rd=false;
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
        ud = false;
        dd = false;
        ld = false;
        rd = false;
        tocado = false;
        buscarAcertado();
    }
    /*
     *******************************INTELIGENCIA ARTIFICIAL*******************************************
     * Disparar con disparar(nextShot());
     */
}
