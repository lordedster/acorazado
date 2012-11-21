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
import battleships.menu.StringImageItem;
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
    
    boolean mpg;
    
    int puntaje = 0;
    
    
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
    private boolean acertoBarco;
    private StringImageItem menu;
    
    private int misil_x;
    private int misil_y;
    private int target_x;
    private int target_y;
    private int map_x;
    private int map_y;


    public FriendlyBoard(int cornerX, int cornerY, int width, int height, Resources r, Listener l, double scaling, int dificultad, boolean multiplayer) {
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
        acertoBarco = false;
        
        mpg = multiplayer;
        
        IN_CX = cornerX;
        OUT_CX = IN_CX - width;    

        x = OUT_CX;
        y = cornerY + height / 2;

        this.cornerX = OUT_CX;
        this.cornerY = cornerY;
        ready = false;
        disparado = false;
        menu = new StringImageItem("Menu");
        menu.setRGB(255, 255, 255);   
        menu.setPosition(OUT_CX, cornerY + displayHeight);
        menu.setVisible(false);
        
    }
    
    public void verMenu(){        
        menu.setVisible(true);
    }
      
    public void ocultarMenu(){        
        menu.setVisible(false);
    }
    private void setPositionMenu() {       
       menu.setPosition(x + displayWidth - menu.getWidth(), cornerY + displayHeight - menu.getHeight());
    }    

    public boolean isMpg() {
        return mpg;
    }

    public void setMpg(boolean mpg) {
        this.mpg = mpg;
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
    
    public void generarMapaCampaign(Resources r, int mision){             
        this.r = r;
        RellenarMapa(r, scaling);
        cargarBarcosCampaign(mision, true);
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
        setPositionMenu();
        return distance != 0;
    }

    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.5;
        x = IN_CX + distance;
        positionGrid();
        setPositionMenu();
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
        RellenarMapa(r,scaling);
        CargarBarcosFacil(visibilidad);
    }    
    
 private void cargarBarcosCampaign(int mision, boolean v){
     for (int i = 0; i <= mision; i++) {
         AlgoritmoDificil(i, TypeBattleShips.getLargoBarco(i), i, false);
     }
     switch (mision){
         case 0:
            AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 0,v);
         case 1:
            AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 1, v); 
         case 2:
            AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2, v );
         case 3:
            AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 3, v);
         case 4:
            AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 4, v);
         
     }
     
   }
    
    private void CargarBarcosFacil(boolean v){       
        AlgoritmoFacil(TypeBattleShips.PORTAAVIONES, TypeBattleShips.PORTAAVIONES_SIZE, ObtenerMatriz(), 0,v);        
        AlgoritmoFacil(TypeBattleShips.ACORAZADO, TypeBattleShips.ACORAZADO_SIZE, ObtenerMatriz(), 1, v);        
        AlgoritmoFacil(TypeBattleShips.DESTRUCTOR, TypeBattleShips.DESTRUCTOR_SIZE, ObtenerMatriz(), 2, v );
        AlgoritmoFacil(TypeBattleShips.SUBMARINO, TypeBattleShips.SUBMARINO_SIZE, ObtenerMatriz(), 3, v);
        AlgoritmoFacil(TypeBattleShips.ESPIA, TypeBattleShips.ESPIA_SIZE, ObtenerMatriz(), 4, v);
   }
    
    
    private void AlgoritmoDificil(int ship, int size, int posicion, boolean v){     
            BattleShip b = new BattleShip(ship, size, 0, 0, 0);
            b.hundir();
            super.AddShipVolador(b, scaling, v, posicion);
     
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
            BattleShip b = new BattleShip(ship, size, direccion, calcMatrizX(x_primero), calcMatrizY(y_primero));
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
            puntaje = puntaje - 40;
            super.board[s.getX()][s.getY()].setEstado(TypeBattleShips.ACERTADO);
            if(!tocado)
            {
                tocado = true;
                XTocado = s.getX();
                YTocado = s.getY();                       
            }
            acertarBaraco(super.board[s.getX()][s.getY()].getBarco(), super.board[s.getX()][s.getY()].getSeccion_barco());
            acertoBarco = true;
        }
        else
        {
           super.board[s.getX()][s.getY()].setEstado(TypeBattleShips.SHOT);
           puntaje = puntaje +5;
        }
        return true;
    }
    
    
    public void acertarBaraco(int barco, int seccion)
    {
        super.ships[barco].Hit();
        if(super.ships[barco].isSunked())
        {
            puntaje = puntaje - 100;
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
            ships[i] = new BattleShip(g[i].getType(), g[i].getLargo(), g[i].getOrientacion(), g[i].getX(), g[i].getY());
        }
    }
    /*
     *******************************INTELIGENCIA ARTIFICIAL******************************************
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
        misil_y += 60;
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
 ;
        pintar();
        setearMisil();
    }
    
    public void AtacarMP(boolean b){
        this.ataqueEnCurso = b;   
       
        //pintar();
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
      
        disparado = disparar(s);
       
        ss = ss + "2"; // 0
        ss = ss + "0";//sync  // 1
        if(board[s.getX()][s.getY()].getBarco() == TypeBattleShips.EMPTY) //no hay barcos = agua
        {
           
            ss = ss + "" + s.getX();  //2
            ss = ss + "" + s.getY(); //3
             ss = ss + "0"; // 4
             ss = ss + "0"; // 5
             ss = ss + "0"; // 6
             
             
        }
        else
        {
            if(  ships[board[s.getX()][s.getY()].getBarco()].isSunked())
            {
                ss = ss + "" + obtMatrizX(ships[board[s.getX()][s.getY()].getBarco()].getX());  //2
                ss = ss +  "" + obtMatrizY(ships[board[s.getX()][s.getY()].getBarco()].getY());  //3
                ss = ss + "2";  //4
                ss = ss + "" + board[s.getX()][s.getY()].getBarco();//5
                ss = ss + "" + ships[board[s.getX()][s.getY()].getBarco()].getOrientacion();//6
                
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
    
    public void startAsServer()
    {
        Listener(TypeBattleShips.BT_SERVIDOR);
    }
    
    public void startAsCliente()
    {
        Listener(TypeBattleShips.BT_CLIENTE);
    }
    
    public void setAcertarBarcoFalse(){
        this.acertoBarco = false;
    }
    
    public boolean getAcertarBarco(){
       return this.acertoBarco;
    }
    
    
     
     public int[][] getMapaParaGuardar(){
        int[][] m = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                m[i][j] = super.board[i][j].getEstado();
            }
        }
        return m;
    } 
    
    public int[][] getBarcosParaGuardar(){
        int[][] s = new int[5][5];
        for (int i = 0; i < 5; i++) {
            s[i][0] = super.ships[i].getX();
            s[i][1] = super.ships[i].getY();
            s[i][2] = super.ships[i].getType();
            s[i][3] = super.ships[i].getOrientacion();
            s[i][4] = super.ships[i].gethits();
        }
        return s;
    }
    
    public void loadMapa(int[][] map){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch(map[i][j]){
                    case TypeBattleShips.INTACTO:
                        super.board[i][j].setEstado(TypeBattleShips.INTACTO);
                        super.board[i][j].setFrameBarco(0);
                        break;
                    case TypeBattleShips.ACERTADO:
                        super.board[i][j].setEstado(TypeBattleShips.ACERTADO);
                        super.board[i][j].setFrameBarco(1);
                        break;                        
                    case TypeBattleShips.HUNDIDO:           
                        super.board[i][j].setEstado(TypeBattleShips.HUNDIDO);             
                        super.board[i][j].setFrameBarco(1);
                        break;
                    case TypeBattleShips.SHOT:
                        super.board[i][j].setEstado(TypeBattleShips.SHOT);
                        super.board[i][j].setFrameBarco(2);
                        break;
                    default:                        
                        super.board[i][j].setEstado(TypeBattleShips.AGUA);
                        super.board[i][j].setFrameBarco(0);
                        break;
                }
            }
        }
        positionGrid();
    }
    
    public void loadBarcos(int[][] barcos){
        RellenarMapa(r,scaling);
        for (int i = 0; i < barcos.length; i++) {            
            BattleShip b = new BattleShip(barcos[i][2], 
                                            TypeBattleShips.getLargoBarco( barcos[i][2]),
                                            barcos[i][3], 
                                            barcos[i][0],
                                            barcos[i][1]);
            b.setHits(barcos[i][4]);     
            super.AddShip(b, scaling, true, r, barcos[i][2]);
        }
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    
}
