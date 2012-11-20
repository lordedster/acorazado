package battleships.menu;

import battleships.effects.Slideable;
import battleships.game.Resources;
import battleships.records.UserData;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author edster
 */
public class MainMenu
    extends Menu
    implements Slideable{
    public static final int ITEM_COUNT = 3;    
    public static final int SLOT1 = 0;
    public static final int SLOT2 = 1;
    public static final int SLOT3 = 2;
    public static final int ELIMINADO = 3;  
    public static MenuItem leyenda;
    public static MenuItem eliminar;
    public final int OUT_CX;
    public final int IN_CX;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private int codeeliminado = -1;
    
     public MainMenu(int cornerX, int cornerY, int width, int height, Listener l, 
                     double scaling, String user1, String user2, String user3, Resources r){
        super(ITEM_COUNT,l);
        leyenda = new MenuItem(new StringMenuItem("Elija su perfil:", r));
        eliminar = new MenuItem(new StringMenuItem("Eliminar", r));
        if (user1.equals("")){
            setItem(SLOT1, new MenuItem(new StringMenuItem("Vacío",r)));
        }else{
            setItem(SLOT1, new MenuItem(new StringMenuItem(user1,r)));
        }
        if (user2.equals("")){
            setItem(SLOT2, new MenuItem(new StringMenuItem("Vacío",r)));
        }else{
            setItem(SLOT2, new MenuItem(new StringMenuItem(user2,r)));
        }
        if (user3.equals("")){
            setItem(SLOT3, new MenuItem(new StringMenuItem("Vacío",r)));
        }else{
            setItem(SLOT3, new MenuItem(new StringMenuItem(user3,r)));
        }
        
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;
        this.width = width;
        this.height = height;        

        x = OUT_CX;
        y = cornerY + height / 2;
        
        positionItemsHorizontally();
        positionItemsVertically();
        positionBoton();
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        leyenda.paint(g);
        eliminar.paint(g);
    }
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItemsHorizontally();
        positionBoton();
        return distance != 0;
    }

    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionItemsHorizontally();
        positionBoton();
        return distance != 0;
    }
    
    
    public final void positionItemsHorizontally() {
        MenuItem item;
        for (int i = 0; i < ITEM_COUNT; i++) {
            item = getItem(i);
            item.setHorizontalCenter(x);
        }
        leyenda.setHorizontalCenter(x);
    }

    /**
     * Lay out menu items vertically
     */
    public final void positionItemsVertically() {
        int newY = y + (int)(leyenda.getHeight() * 1.5);
        leyenda.setCenter( leyenda.getX(), y);
        for (int i = 0; i < ITEM_COUNT; i++) {
            MenuItem item = getItem(i);
            item.setCenter(item.getX(), newY);
            newY += (int)(item.getHeight()* 1.3);
        }
    }
    
    public final void positionBoton(){        
        eliminar.setPosition(x + (int)(width/2) - eliminar.getWidth() - 3, height - eliminar.getHeight() - 3);
    }
    
    public void eliminarUsuario(Resources r){
        switch(getSelected()){
            case SLOT1:
                setItem(SLOT1, new MenuItem(new StringMenuItem("Vacío",r)));
                codeeliminado = UserData.PERFIL_A;
                clickSelected(ELIMINADO);
                break;
            case SLOT2:
                setItem(SLOT2, new MenuItem(new StringMenuItem("Vacío",r)));
                codeeliminado = UserData.PERFIL_B;
                clickSelected(ELIMINADO);
                break;
            case SLOT3:
                setItem(SLOT3, new MenuItem(new StringMenuItem("Vacío",r)));
                codeeliminado = UserData.PERFIL_C;
                clickSelected(ELIMINADO);
                break;
        }                
        x = IN_CX + (int)(width/4);
        positionItemsHorizontally();
        positionItemsVertically();
    }
    
    public int getCodeEliminado(){
        return codeeliminado;
    }
    
    public void setNombre(int usuario, String nombre){
        MenuItem item;
        item = getItem(usuario);
        item.setString(nombre);
    }
    
    
}