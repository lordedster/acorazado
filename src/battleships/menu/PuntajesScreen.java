
package battleships.menu;

/**
 *
 * @author edster
 */
import battleships.effects.Slideable;
import battleships.game.Resources;
import battleships.game.ships.TypeBattleShips;
import javax.microedition.lcdui.Graphics;
/**
 *
 * @author Eric
 */
public class PuntajesScreen extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 0;
    public static final int ACTION = 0;
    public static final int CAMPAIGN = 1;
    public static final int MULTIJUGADOR = 2;
    public static final int OPTIONS = 3;
    public static final int EXIT = 5;
    public static final int INFO = 4;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int height;
    private int cornerY;
    private StringImageItem menu;
    private StringImageItem puntajeSP;
    private StringImageItem puntajeCP;
    private int puntosCP;
    private int puntosSP;

    public PuntajesScreen(int cornerX, int cornerY, int width, int height, Menu.Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        this.width = width;  
        this.height = height;
        this.cornerY = cornerY;
        menu = new StringImageItem("Menu");        
        menu.setRGB(255, 255, 255);
        puntajeSP = new StringImageItem("Juagor unico: " + puntosSP);
        puntajeSP.setRGB(255, 255, 255);
        puntajeCP = new StringImageItem("Modo campaña: " + puntosCP);
        puntajeCP.setRGB(255, 255, 255);
        
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;
        x = OUT_CX;
        y = cornerY + height / 2;
        positionItemsHorizontally();
        positionItemsVertically();
         positionItems();
    }

    /**
     * Render the menu
     */
    public void paint(Graphics g) {
        menu.paint(g);
        puntajeCP.paint(g);
        puntajeSP.paint(g);
    }
    /**
     * Move view inwards
     */
    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionItemsHorizontally();
        return distance != 0;
    }

    /**
     * Move view outwards
     */
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItemsHorizontally();
        return distance != 0;
    }

    
    /**
     * Lay out menu items horizontally
     */
    public final void positionItemsHorizontally() {
        MenuItem item;
        for (int i = 0; i < ITEM_COUNT; i++) {
            item = getItem(i);
            item.setHorizontalCenter(x);
        }
    }
    
    public void atrasPuntaje()
    {
        clickSelected(1);
    }

    /**
     * Lay out menu items vertically
     */
    public final void positionItemsVertically() {
        int newY = y;
        for (int i = 0; i < ITEM_COUNT; i++) {
            MenuItem item = getItem(i);
            item.setCenter(item.getX(), newY);
            newY += item.getHeight();
        }
        
        
        
    }
    
    public final void positionItems() {        
        menu.setPosition(x + width / 2 - menu.getWidth(),cornerY + height - menu.getHeight());
        puntajeSP.setPosition(x - puntajeCP.getWidth() / 2, y);
        puntajeCP.setPosition(x - puntajeCP.getWidth() / 2, y);
    }
}

