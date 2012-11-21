
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

/**
 *
 * @author edster
 */
import battleships.effects.Slideable;
import battleships.game.Resources;
import battleships.menu.StringImageItem;
import battleships.game.ships.TypeBattleShips;
import battleships.game.CampaignHistory;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.wireless.messaging.SizeExceededException;

public class HistoryScreen
        extends Menu
        implements Slideable {

    public static final int ITEM_COUNT = 0;
    public StringImageItem historia;
    public StringImageItem menu;
    public final int OUT_CX;
    public final int IN_CX;
    private int x;
    private int y;
    private int width;
    private int height;

    public HistoryScreen(int cornerX, int cornerY, int width, int height, Menu.Listener l, double scaling, Resources r) {
        super(ITEM_COUNT, l);
        this.width = width;    
        this.height = height;
        this.historia = new StringImageItem("primera historia");
        historia.setRGB(255, 255, 255);
        historia.setFontStyleSize(Font.STYLE_PLAIN, Font.SIZE_SMALL);
        
        menu = new StringImageItem("Jugar");
        menu.setRGB(255, 255, 255);
        
        IN_CX = cornerX + width / 2;
        OUT_CX = IN_CX - width;
        x = OUT_CX;
        y = cornerY;
        positionItems();
        
        historia.setPosition(OUT_CX + width, y);
        menu.setPosition(OUT_CX, y + height);
    }

    /**
     * Render the menu
     */
    public void paint(Graphics g) {
        historia.paint(g);
        menu.paint(g);
    }
    /**
     * Move view inwards
     */
    public boolean slideIn() {
        int distance = x - IN_CX;
        distance *= 0.8;
        x = IN_CX + distance;
        positionItems();
        return distance != 0;
    }

    /**
     * Move view outwards
     */
    public boolean slideOut() {
        int distance = x - OUT_CX;
        distance *= 0.8;
        x = OUT_CX + distance;
        positionItems();
        return distance != 0;
    }
    
    public void setHistoria(int h){
        historia.setTexto(CampaignHistory.HISTORY[h]);
    }
    
    public final void positionItems() {
        historia.setPosition(x - menu.getWidth()/2, y);
        menu.setPosition(x + width - menu.getWidth(), y + height - menu.getHeight());
    }
    
    public void jugar(){
        clickSelected(TypeBattleShips.SP_TURNO);
    }
}



