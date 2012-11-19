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
//import battleships.records.UserData;
import java.util.Random;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;


/**
 *
 * @author Administrador
 */
public class FriendlyBoard extends Map implements Slideable {
    
    int dificultad;
    int direccionDisparo = -1;//0 up, 1, down, 2 left, 3 rigth
    int XTocado;
    int YTocado;
    boolean tocado = false;
    boolean ud = true;
    boolean dd = true;
    boolean ld = true;
    boolean rd = true;
    int lastdirection = -1;

    
    
    boolean ready;
    boolean disparado;
    private Random rnd;
    private final int IN_CX;
    private final int OUT_CX;    
    private int x;
    private int y;
    private int cornerX;
    private int cornerY;
    private int displayWidth;
    private int displayHeight;
    private Resources r;
    //private Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);   
    private double scaling;
    
    private Sprite misil;
    private boolean ataqueEnCurso;
    
    private int misil_x;
    private int misil_y;
    private int target_x;
    private int target_y;
    private int map_x;
    private int map_y;


    public FriendlyBoard(int cornerX, int cornerY, int width, int height, Resources r, Listener l, double scaling, int dificultad) {
        super(10,10,5,l);
        this.displayWidth = width;
        this.displayHeight = height;        
        this.r = r;
        this.scaling = scaling;
        this.dificultad = dificultad;
        rnd = new Random();
        this.misil = loadSprite(r.misil,1,scaling);
        setearMisil();
        ataqueEnCurso = false;
        
        IN_CX = cornerX;
        OUT_CX = IN_CX - width;    

        x = OUT_CX;
        y = cornerY + height / 2;

        this.cornerX = OUT_CX;
        this.cornerY = cornerY;
        ready = false;
        disparado = false;
        
        
     
        
         
    }
    
    public void singlePlayerGame()
    {
        rnd = new Random();
      // dificultad =  ;
    }
    public void setDificultad(int dificultad){
        this.dificultad = dificultad;
    }

    public void generarMapa(Resources r){        
        this.r = r;
        createMap(true);        
        positionGrid();
    }
        
    public void vaciarMapa(){
        Grid[][] e = ObtenerMatriz();
        ReemplazarMapa(new Grid[e.length][e[0].length]);
        ReemplazarBarcos(new BattleShip[obtenerBarcos().length]);
    } 

    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.5;
        x = OUT_CX + distance;
        positionGrid();
        return distance != 0;
    }

    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.5;
        x = IN_CX + distance;
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
        misil.paint(g);
    }
    
     private void createMap(boolean visibilidad){
        for(int i = 0; i < getHeight(); i++ )
        {
            for(int j = 0; j < getWidth(); j++)
            {
                setGrid(i, j, new Grid(TypeBattleShips.AGUA, TypeBattleShips.EMPTY, 
                                        loadSprite(r.water, 3, scaling), 
                                        loadSprite(r.mira, 2, scaling),TypeBattleShips.EMPTY));
            }
        }
        CargarBarcosFacil(visibilidad);
    }    
    

    
    private void CargarBarcosFacil(boolean v){       
        AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 0,v);        
        AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 1, v);        
        AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2, v );
        AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 3, v);
        AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 4, v);
        r = null;
   }
    
    private void AlgoritmoFacil(int ship, int size, Grid[][] map, int posicion, boolean v){        
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
            AlgoritmoFacil(ship, size, map, posicion, v);
        } else {
            BattleShip b = new BattleShip(TypeBattleShips.SHIPS[ship], ship, size, direccion, calcMatrizX(x_primero), calcMatrizY(y_primero), r);
            super.AddShip(b, scaling, v, r, posicion);
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
        disparado = disparar(nextShot());
        //Listener(TypeBattleShips.SP_TURNO);
    }
    
    public Shoot nextAcertadoShoot(Shoot s)
    {
        if(direccionDisparo == -1)
        {
            getNewDireccion();
        }
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
                s.setX(s.getX()+1);
                break;
            }
        }
        if(s.getX() > 9 || s.getY() > 9 || s.getX()<0 || s.getY()<0) //el siguiente disparo esta en los limites del tablero?
        {
            getNewDireccion(); // buscamos otra direccion
            s.setX(XTocado);
            s.setY(YTocado);
            s = nextAcertadoShoot(s);
        }
        else
        {
            if( super.board[s.getX()][s.getY()].getEstado()== TypeBattleShips.SHOT || super.board[s.getX()][s.getY()].getEstado()== TypeBattleShips.HUNDIDO)
            {
                getNewDireccion(); // buscamos otra direccion
                 s.setX(XTocado);
                s.setY(YTocado);
                s = nextAcertadoShoot(s);
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
        int xn;
        int yn;
        switch(dificultad){
    
            case TypeBattleShips.FACIL:
            {
                while(true)
                {
                    xn = rnd.nextInt(10);
                    yn = rnd.nextInt(10);
                    
                    if( super.board[xn][yn].getEstado()== TypeBattleShips.INTACTO || super.board[xn][yn].getEstado()== TypeBattleShips.AGUA )
                    {   
                        break;
                    }
                }
                s.setX(xn);
                s.setY(yn);
            break;
            }
            case TypeBattleShips.MODERADO:
            {
                 
                 if(tocado)
                {
                    s.setX(XTocado);
                    s.setY(YTocado);
                    s = nextAcertadoShoot(s);
                }
                else
                {
                 while(true)
                {
                    
                    xn = rnd.nextInt(10);
                    yn = rnd.nextInt(10);
                
                    if( super.board[xn][yn].getEstado()== TypeBattleShips.INTACTO || super.board[xn][yn].getEstado()== TypeBattleShips.AGUA )
                    {   
                        break;
                    }
                }
                s.setX(xn);
                s.setY(yn);
                }
            break;
            }
            case TypeBattleShips.DIFICIL:
            {
                 if(tocado)
                {
                    s.setX(XTocado);
                    s.setY(YTocado);
                    s = nextAcertadoShoot(s);
                }
                else
                {
                 while(true)
                {
                    
                    xn = rnd.nextInt(10);
                    yn = rnd.nextInt(5);
                    if(xn%2 == 0)
                    {
                        yn = yn + yn;
                    }
                    else
                    {
                        yn = yn + yn + 1;
                    }    
                    if( super.board[xn][yn].getEstado()== TypeBattleShips.INTACTO || super.board[xn][yn].getEstado()== TypeBattleShips.AGUA )
                    {   
                        break;
                    }
                }
                s.setX(xn);
                s.setY(yn);
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
        direccionopuesta();
        while(direccionDisparo == -1)
        {
            a = rnd.nextInt(4);
            switch (a)
            {
                case 0:
                {
                    if (ud == true)
                    {
                        direccionDisparo = 0;
                        lastdirection = 0;
                    }
                    break;
                }
                case 1:
                {
                    if (dd == true)
                    {
                        direccionDisparo = 1;
                        lastdirection = 1;
                    }
                    break;
                }
                case 2:
                {
                    if (ld == true)
                    {
                        direccionDisparo = 2;
                        lastdirection = 2;
                    }
                    break;
                }
                case 3:
                {
                    if (rd == true)
                    {
                        direccionDisparo = 3;
                        lastdirection = 3;
                    }
                    break;
                }
            }
        }
    }
    
    public void direccionopuesta()
    {

        switch(lastdirection)
        {
                case 0:
                    if(dd)
                    {
                        direccionDisparo = 1;
                        lastdirection = 1;
                        
                    }
                    break;
                    
                case 1:
                    if(ud)
                    {
                        direccionDisparo = 0;
                        lastdirection = 0;
                        
                    }
                    break;
                    
                case 2:
                    if(ld)
                    {
                        direccionDisparo = 3;
                        lastdirection = 3;
                       
                    }
                    break;
                    
                case 3:
                    if(rd)
                    {
                        direccionDisparo = 2;
                        lastdirection = 2;
                     
                    }
                    break;
        }           
    }
    
    public void getNewDireccion()
    {
        switch (direccionDisparo)
        {
            case -1:
                break;
                
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
       
    
    public boolean disparar(Shoot s)
    { 
        posicionMisil(s.getX(),s.getY(), board[s.getX()][s.getY()].getX(), board[s.getX()][s.getY()].getY());
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
        return true;
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
                                        super.board[i][j].setFrameBarco(1);
                                    }
                    }
               }
            reset();  
        } 
    }
    
    public void reset()
    {
        direccionDisparo = -1;
        lastdirection = -1;
        ud = true;
        dd = true;
        ld = true;
        rd = true;
        tocado = false;
        buscarAcertado();
    }
    
    public void crearMapaManualmente(Grid[][] g){
        for (int i = 0; i < g.length; i++){
            for (int f = 0; f < g[0].length; f++){
                if (g[i][f].getBarco() == TypeBattleShips.EMPTY){
                    board[i][f] = new Grid(g[i][f].getEstado(),g[i][f].getBarco(), loadSprite(r.water, 3, scaling), loadSprite(r.mira, 2, scaling), g[i][f].getSeccion_barco());
                }else{
                    board[i][f] = new Grid(g[i][f].getEstado(),g[i][f].getBarco(), loadSprite(SeccionBarco(g[i][f].getSeccion_barco(), g[i][f].getBarco(), r), 2, scaling), loadSprite(r.mira, 2, scaling), g[i][f].getSeccion_barco());
                }
            }
        }
    }
     public void crearBarcosManualmente(BattleShip[] g){
        for (int i = 0; i < g.length; i++){
            ships[i] = new BattleShip(g[i].Name(), g[i].getType(), g[i].getLargo(), g[i].getOrientacion(), g[i].getX(), g[i].getY(), r);
        }
    }
    /*
     *******************************INTELIGENCIA ARTIFICIAL*******************************************
     * Disparar con disparar(nextShot());
     */
     
     public void readyToShoot(boolean ready){
         this.ready = ready;
     }
     
     public boolean isReady(){
         return ready;
     }
     
     public boolean hasShot(){
         return disparado;
     }
     public void setHasShot(boolean d){
         disparado = d;
     }
     
     
        public boolean animarAtaque(){
        misil_y += 72;
        if(target_y <= misil_y){
            return false;
        }else{
            posicionarMisil();
            return true;
        }
    }
    
    private void posicionMisil(int x, int y, int target_x, int target_y ){
        ataqueEnCurso = true;
        this.target_x = target_x;
        this.target_y = target_y;
        this.map_x = x;
        this.map_y = y;
        misil_y = cornerY - 24;
        misil_x = target_x;
        posicionarMisil();
        
    }
    
    private void posicionarMisil(){
        misil.setPosition(misil_x, misil_y);
    }
    
    public void setearMisil(){
        misil.setPosition(-24, -24);
    }
    
    public void Atacar(boolean b){
        this.ataqueEnCurso = b;        
        pintar();
        setearMisil();
    }
    
    public boolean isAtaqueEnCurso(){
        return ataqueEnCurso;
    }
    
    public void pintar(){          
        if(board[map_x][map_y].getBarco() == TypeBattleShips.EMPTY){
            board[map_x][map_y].setFrameBarco(2);                
        }else{
            board[map_x][map_y].setFrameBarco(1);
        }            
    }

    
    public String mp_disparo(Shoot s)
    {
        String ss ="";
       disparar(s);
       
        ss = ss + "1"; // 0
        ss = ss + "0";//sync  // 1
        if(board[s.getX()][s.getY()].getBarco() == TypeBattleShips.EMPTY)
        {
            ss = ss + "" + s.getX();  //2
            ss = ss + "" + s.getY(); //3
             ss = ss + "0"; // 4
        }
        else
        {
            if(  ships[board[s.getX()][s.getY()].getBarco()].isSunked())
            {
                ss = ss + "" + obtMatrizX(ships[board[s.getX()][s.getY()].getBarco()].getX());  //2
                ss = ss +  "" + obtMatrizY(ships[board[s.getX()][s.getY()].getBarco()].getY());  //3
                ss = ss + "2";  //4
                ss = ss + "" + board[s.getX()][s.getY()].getBarco();
                ss = ss + "" + ships[board[s.getX()][s.getY()].getBarco()].getOrientacion();
                
            }
            else
            {
                ss = ss + "" + s.getX();
                ss = ss + "" + s.getY();
                ss = ss + "1";
                ss = ss + "0";
                ss = ss + "0";
            }
            
        }
        
        
        
        
             /*
        * char 0 = 1 ataque, 2 respuesta ataque
        * char 1 = sycn del 0 al 9, ventana
        * char 2 = x
        * char 3 = y 
        * char 4 = resultado, tocado undido / arma especial
        * char 5 = barco
        * char 6 = orientacion
        */
        return ss;
    }
    
}
