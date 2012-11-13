/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.ImageHelper;
import battleships.effects.Slideable;
import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author edster
 */
public class SelectorNombre 
    implements Slideable{
    public static final int POINTER_PRESSED = 0;
    public static final int POINTER_DRAGGED = 1;
    public static final int POINTER_RELEASED = 2;
    public static final int BUTTON_OK = 0;
    public static final int BUTTON_CANCEL = 1;
    public static final int VOLVER = -1;
    private final LetraItem[][] items;
    private LetraItem[] nombre;
    private MenuItem ok;
    private MenuItem cancel;
         
    private final Listener listener;
    public final int OUT_CX;
    public final int IN_CX;
    private final int middle;
    private final double scaling;
    private final int width;
    private final int height;
    private int x;
    private int y;

    /**
     * Constructor
     * @param capacity Amount of menu items
     * @param listener Listener for menu events
     */
    public SelectorNombre(int cornerX, int cornerY, int width, int height, Listener listener, double scaling,Resources r) {
        items = new LetraItem[3][9];
        nombre = new LetraItem[5];
        this.listener = listener;
        this.scaling = scaling;
        ok = new MenuItem(loadSprite("/button_ok.png", 2, scaling));
        cancel = new MenuItem(loadSprite("/back.png",2, scaling));
        
        setNombreItem(0, new LetraItem(loadSprite(r.letra_guion, 2, scaling), Letra.guion));
        setNombreItem(1, new LetraItem(loadSprite(r.letra_guion, 2, scaling), Letra.guion));
        setNombreItem(2, new LetraItem(loadSprite(r.letra_guion, 2, scaling), Letra.guion));
        setNombreItem(3, new LetraItem(loadSprite(r.letra_guion, 2, scaling), Letra.guion));
        setNombreItem(4, new LetraItem(loadSprite(r.letra_guion, 2, scaling), Letra.guion));
        nombre[0].setSelected(true);
        
        setItem(0,0, new LetraItem(loadSprite(r.letra_A, 2, scaling), Letra.A));
        setItem(0,1, new LetraItem(loadSprite(r.letra_B, 2, scaling), Letra.B));
        setItem(0,2, new LetraItem(loadSprite(r.letra_C, 2, scaling), Letra.C));
        setItem(0,3, new LetraItem(loadSprite(r.letra_D, 2, scaling), Letra.D));
        setItem(0,4, new LetraItem(loadSprite(r.letra_E, 2, scaling), Letra.E));
        setItem(0,5, new LetraItem(loadSprite(r.letra_F, 2, scaling), Letra.F));
        setItem(0,6, new LetraItem(loadSprite(r.letra_G, 2, scaling), Letra.G));
        setItem(0,7, new LetraItem(loadSprite(r.letra_H, 2, scaling), Letra.H));
        setItem(0,8, new LetraItem(loadSprite(r.letra_I, 2, scaling), Letra.I));
        setItem(1,0, new LetraItem(loadSprite(r.letra_J, 2, scaling), Letra.J));
        setItem(1,1, new LetraItem(loadSprite(r.letra_K, 2, scaling), Letra.K));
        setItem(1,2, new LetraItem(loadSprite(r.letra_L, 2, scaling), Letra.L));
        setItem(1,3, new LetraItem(loadSprite(r.letra_M, 2, scaling), Letra.M));
        setItem(1,4, new LetraItem(loadSprite(r.letra_N, 2, scaling), Letra.N));
        setItem(1,5, new LetraItem(loadSprite(r.letra_Enie, 2, scaling), Letra.Enie));
        setItem(1,6, new LetraItem(loadSprite(r.letra_O, 2, scaling), Letra.O));
        setItem(1,7, new LetraItem(loadSprite(r.letra_P, 2, scaling), Letra.P));
        setItem(1,8, new LetraItem(loadSprite(r.letra_Q, 2, scaling), Letra.Q));
        setItem(2,0, new LetraItem(loadSprite(r.letra_R, 2, scaling), Letra.R));
        setItem(2,1, new LetraItem(loadSprite(r.letra_S, 2, scaling), Letra.S));
        setItem(2,2, new LetraItem(loadSprite(r.letra_T, 2, scaling), Letra.T));
        setItem(2,3, new LetraItem(loadSprite(r.letra_U, 2, scaling), Letra.U));
        setItem(2,4, new LetraItem(loadSprite(r.letra_V, 2, scaling), Letra.V));
        setItem(2,5, new LetraItem(loadSprite(r.letra_W, 2, scaling), Letra.W));
        setItem(2,6, new LetraItem(loadSprite(r.letra_X, 2, scaling), Letra.X));
        setItem(2,7, new LetraItem(loadSprite(r.letra_Y, 2, scaling), Letra.Y));
        setItem(2,8, new LetraItem(loadSprite(r.letra_Z, 2, scaling), Letra.Z));
        
      
        IN_CX = cornerX;
        OUT_CX = IN_CX - width;
        middle = calculateMiddle(width);
        this.height = cornerY + height;
        this.width = width;
        

        x = OUT_CX;
        y = cornerY + height / 2;
        positionItem();
        positionNombre();
        positionBotones();
    }
    
    private int calculateMiddle(int width){
        int num = width / 2;
        int largo = 0;
        for (int i = 0; i< nombre.length; i++){
            largo += nombre[i].getWidth() - 2;
        }
        largo = largo /2;
        return num - largo;
    }
    
   
    /**
     * Establece el objeto Grid en una posicion determinada
     * @param x posicion fila
     * @param y posicion columna
     * @param item objeto contenido en la posicion 
     */
    protected final void setItem(int x, int y, LetraItem item) {
        items[x][y] = item;
    }
    protected final void setNombreItem(int posicion, LetraItem item) {
        nombre[posicion] = item;
    }
    
    /**
     *Debuelve un Grid 
     * @param x posicion vertical del mapa
     * @param y posicion horizontal del mapa
     * @return un objeto Grid
     */
    protected final MenuItem getItem(int x, int y){
        return items[x][y];
    }
    
     /**
     * Render the menu items
     */
    public void paint(Graphics g) {
        // Posición vertical(x)
        for (int i = 0; i < items.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < items[i].length; j++){
                items[i][j].paint(g);
            }
        }
        for (int i = 0; i < nombre.length; i++){
            nombre[i].paint(g);
        }
        ok.paint(g);
        cancel.paint(g);
    }
    
    public final void positionItem(){
        MenuItem item;
        int newx;
        int newy = y + getItem(0,0).getHeight() - 10;
        int value = 0;
        int valuey = 0; 
        for (int i = 0; i < getHeight(); i++) {
            newx = x;
            for(int j = 0; j < getWidth(); j++){                
                item = getItem(i,j);
                item.setPosition(newx, newy);
                value = item.getWidth();
                newx += value - 2;
                valuey = item.getHeight();
            }            
            newy += valuey - 3;
        }
    }
    public final void positionNombre(){
        LetraItem item;
        int newx = x + middle;    
        for (int i = 0; i < nombre.length; i++) {
            item = nombre[i];
            item.setPosition(newx, y - 10);  
            newx += item.getWidth() - 2;
        }
    }
    public final void positionBotones(){
        
        ok.setPosition(x, height - ok.getHeight());
        cancel.setPosition(x + width - cancel.getWidth(), height - cancel.getHeight());
    }
    
    /**
     * Move view inwards
     */
    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionItem();
        positionNombre();
        positionBotones();
        return distance != 0;
    }

    /**
     * Move view outwards
     */
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItem();
        positionNombre();
        positionBotones();
        return distance != 0;
    }
    
    /**
     * establece el siguiente item que está seleccionado
     * @param x posición vertical
     * @param y posicion horizontal
     */
    public void selectItem(int x, int y){
        // Posición vertical(x)
        for (int i = 0; i < items.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < items[i].length; j++){
                boolean selected = false;
                if(x == i && y == j) {
                    selected  = true;
                }
                items[i][j].setSelected(selected);
            }
        }
    }
    
    /**
     *  devuelve el valor vertical de la selección
     * @return int
     */
    protected int getSelectedX() {
        
        // Posición vertical(x)
        for (int i = 0; i < items.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < items[i].length; j++){
               if(items[i][j].isSelected()){
                   return i;
               } 
            }
        }
        return -1;
    }
    
    /**
     * Devuelve el valor horizontal de la selección
     * @return int
     */
     protected int getSelectedY() {
        
        // Posición vertical(x)
        for (int i = 0; i < items.length; i++){
            
            // Posición horizontal(y)
            for (int j = 0; j < items[i].length; j++){
               if(items[i][j].isSelected()){
                   return j;
               } 
            }
        }
        return -1;
    }
     
     public void selectUp(){
         int sely = getSelectedY();
         if (sely == -1){
             sely = 0;
         }
         selectItem((Math.max(getSelectedX(), 0) - 1 + items.length) % items.length, sely);
     }
     
     public void selectDown(){
         int sely = getSelectedY();
         if (sely == -1){
             sely = 0;
         }
         selectItem((getSelectedX() + 1) % items.length, sely);
     }
     
     public void selectLeft(){
         int selx = getSelectedX();
         if (selx == -1){
             selx = 0;
         }
         selectItem(selx,(Math.max(getSelectedY(), 0) - 1 + items[selx].length)% items[selx].length);
     }
     
     public void selectRight(){
         int selx = getSelectedX();
         if (selx == -1){
             selx = 0;
         }
         selectItem( selx, (getSelectedY() + 1) % items[selx].length);
     }
     
     /**
     * Manually cause clicked event on the highlighted item
     */
     public void clickSelected(){
         listener.itemClicked(getSelectedX(),getSelectedY());
     }
     
     /**
     * Handle pointer events
     * @param type POINTER_PRESSED, POINTER_DRAGGED or POINTER_RELEASED
     * @param x
     * @param y
     */
    public void pointerEvent(int type, int x, int y) {
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j <items[i].length; j++)
            {
                if (items[i][j].hits(x, y)) {
                    if (type == POINTER_RELEASED) {
                        listener.itemClicked(i,j);
                        items[i][j].setSelected(false);
                    }
                    else {
                        selectItem(i,j);
                    }
                    return;
                }
            }
        }
        selectItem(0,0);
    }
    
    /**
     * Loads a sprite
     * @param fileName
     * @param lines Amount of vertical frames
     * @param scaling Scaling factor
     * @return Loaded sprite
     */
    protected Sprite loadSprite(String fileName, int lines, double scaling) {
        Image i = loadImage(fileName, lines, scaling);
        return new Sprite(i, i.getWidth(), i.getHeight() / lines);
    }
    
       protected Sprite loadSprite(Image file, int lines, double scaling) {
        return new Sprite(file, file.getWidth(), file.getHeight() / lines);
    }

    /**
     * Load and scale image
     * @param fileName
     * @param lines Amount of vertical frames
     * @param scaling Scaling factor
     * @return Loaded and scaled image
     */
    protected Image loadImage(String fileName, int lines, double scaling) {
        ImageHelper ih = ImageHelper.getInstance();
        Image i = ih.loadImage(fileName);
        return ImageHelper.scaleImage(i, (int) (scaling * i.getWidth() + 0.5),
                                      (int) (scaling * i.getHeight() / lines + 0.5) * lines);
    }        
   
    public int getWidth()
    {
        return items[0].length;
    }
    public int getHeight()
    {
        return items.length;
    }
     /**
     * Listener interface for menu events
     */
    public interface Listener {

        void itemClicked(int x, int y); 
        
        void changeState(int item);
    }
    
    public void AgregarLetra(int x, int y, Resources r){
        if (x > -1 && y >-1){
            int codigo = items[x][y].getCodigo();
            for(int i = 0; i < nombre.length; i++){
                if (nombre[i].isSelected())
                {
                    nombre[i] = new LetraItem(loadSprite(r.getLetra(codigo), 2, scaling),codigo);
                    if (i < nombre.length-1){
                        nombre[i+1].setSelected(true);
                    }
                    nombre[i].setSelected(false);
                    break;
                }
            }
            positionNombre();
        }
    }
    
    public void borrarLetra(Resources r){
        boolean completo = true;
         for(int i = nombre.length - 1; i > -1; i--){
            if (nombre[i].isSelected())
            {                
                if(i > 0){
                    nombre[i-1] = new LetraItem(loadSprite(r.getLetra(Letra.guion), 2, scaling), Letra.guion);
                    nombre[i-1].setSelected(true);                
                    nombre[i].setSelected(false);
                }
                break;
            }
        }
        for(int i = 0; i < nombre.length; i++){
            if(nombre[i].getCodigo()!= Letra.guion){
                completo = false;
                break;
            }
        }
        if (completo){
            listener.changeState(VOLVER);
        }
        positionNombre();
    }
    
    public int[] obtenerNombre(){
        int[] n = new int[5];
        for(int i = 0; i < nombre.length; i++){
            n[i] = nombre[i].getCodigo();
        }
        return n;
    }
    
    public boolean isNombreListo(){
        boolean done = true;
        if (nombre[0].getCodigo() == Letra.guion){
            done = false;
        }
        return done;
    }
    
//    public void Confirmar
}
