/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 * See LICENSE.TXT for license information.
 */

package battleships;

import battleships.effects.Slideable;
import battleships.game.Game;
import battleships.game.DeployShips;
import battleships.game.Resources;
import battleships.effects.ElectricArc;
import battleships.effects.VibratorManager;
import battleships.audio.AudioManager;
import battleships.effects.LightManager;
import battleships.game.EnemyBoard;
import battleships.game.maps.Map;
import battleships.game.ships.BattleShip;
import battleships.menu.BarcosScreen;
import battleships.menu.SelectorScreen;
import battleships.menu.OptionScreen;
import battleships.menu.InfoScreen;
import battleships.menu.MainMenu;
import battleships.menu.Menu;
import battleships.game.ships.TypeBattleShips;
import battleships.menu.MisionScreen;
import battleships.menu.UserScreen;
import java.util.Random;
import javax.microedition.amms.control.tuner.TunerControl;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.Player;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class BattleshipCanvas
        extends GameCanvas {

    private static final int LEFT_SOFTKEY = -6;
    private static final int RIGHT_SOFTKEY = -7;
    private static final int INTERVAL = 30;
    private static final double DEFAULT_WIDTH = 240;
    private static final double DEFAULT_HEIGHT = 320;
    private boolean pressed = false;
    private float scaling;
    private int gameState;
    private int nextState;
    private int cornerX;
    private int cornerY;
    private int displayWidth;
    private int displayHeight;
    private int gameWidth;
    private int gameHeight;
    private int x = 0;
    private int y = 0;
    private BattleshipCanvas.GameThread gameThread;
    private Main main;
    private MainMenu menu; 
    private OptionScreen options;
    private InfoScreen info;
    private SelectorScreen selector;
    private DeployShips deployShips;
    private BarcosScreen barcos;
    private MisionScreen misionScreen;
    private UserScreen userScreen;
    private EnemyBoard tableroEnemigo;
    private Game game;
    private Slideable currentView;
    private Slideable targetView;
    private Player electricity;
    private Resources r;
    private Random rnd = new Random();
    private AudioManager audioManager;
    private VibratorManager vibratorManager;
    private boolean audioEnabled;
    
    /**
     * Inserta un título en la clase descripción.
     * Al ser el título obligatorio, si es nulo o vacío se lanzará
     * una excepción.
     *
     * @param main El nuevo título de la descripción.
     * @throws IllegalArgumentException Si titulo es null, está vacío o contiene solo espacios.
     */
    public BattleshipCanvas(Main main) {
        super(false);
        this.setFullScreenMode(true);
        this.main = main;

        // Figure out the scaling factor
        Graphics g = getGraphics();
        displayWidth = g.getClipWidth();
        displayHeight = g.getClipHeight();

        if ((float) displayWidth / displayHeight < DEFAULT_WIDTH / DEFAULT_HEIGHT) {
            scaling = (float) (displayWidth / DEFAULT_WIDTH);
        }
        else {
            scaling = (float) (displayHeight / DEFAULT_HEIGHT);
        }
        this.r = new Resources(scaling);
        gameWidth = (int) (scaling * DEFAULT_WIDTH + 0.5);
        gameHeight = (int) (scaling * DEFAULT_HEIGHT + 0.5);

        cornerX = (int) (displayWidth / 2 - gameWidth / 2 + 0.5);
        cornerY = displayHeight - gameHeight;

        audioManager = AudioManager.getInstance();
        vibratorManager = VibratorManager.getInstance();
        gameState = TypeBattleShips.STATE_USER;
    }

    /**
     * Creates a new game and loads the previous status of the game, if it exists
     */
    private void loadOrCreateGame() {
        game = new Game(cornerX, cornerY, gameWidth, gameHeight, r, new Game.Listener() {

            public void changeState(int state) {
                switch (state) {
                    case TypeBattleShips.STATE_MENU:
                        showMenu();
                        break;
                    case TypeBattleShips.STATE_LEVEL:
                        nextLevel();
                        break;
                }
            }

            public void handleEvent(int event) {
                switch (event) {
//                    case Game.EVENT_BALL_OUT:
//                        //audioManager.playSample(r.SAMPLE_DEATH);
//                        break;
//                    case Game.EVENT_BONUS:
////                        audioManager.playSample(r.SAMPLE_BONUS);
//                        break;
//                    case Game.EVENT_BRICK_EXPLOSION:
////                        audioManager.playSample(r.SAMPLE_EXPLOSION);
//                        break;
//                    case Game.EVENT_BRICK_COLLISION:
////                        audioManager.playSample(rndCollisionSample());
//                        break;
//                    case Game.EVENT_BUTTON_PRESSED:
//                        audioManager.playSample(r.SAMPLE_BUTTON);
//                        break;
//                    case Game.EVENT_GAME_OVER:
//                        // No sound at the moment
//                        break;
//                    case Game.EVENT_LEVEL_CHANGE:
////                        audioManager.playSample(r.SAMPLE_TELEPORT);
//                        break;
//                    case Game.EVENT_LEVEL_STARTED:
//                        //menu.showResume();
//                        break;
//                    case Game.EVENT_PLATE_COLLISION:
////                        audioManager.playSample(rndCollisionSample());
//                        break;
//                    case Game.EVENT_WALL_COLLISION:
////                        audioManager.playSample(r.SAMPLE_WALL);
//                        break;
                }
            }
        });
        try {
            //RecordStore.deleteRecordStore("GameSnapshot"); // Clear state data for testing purposes
            RecordStore snapshot = RecordStore.openRecordStore("GameSnapshot", true);
            if (snapshot.getNumRecords() == 0 || !game.load(snapshot.getRecord(getRecordId(snapshot)))) {
                game.newGame();
            } else {
               // menu.showResume();
            }
            snapshot.closeRecordStore();
        }
        catch (RecordStoreException e) {
            game.newGame();
        }
        catch (NumberFormatException nfe) { 
            nfe.printStackTrace();
        }
        audioEnabled = true;      
        showMenu();
    }

    /**
     * Returns the name of a randomly selected collision sample
     * @return The sample name in a String
     */
    public String rndCollisionSample() {
//        switch (rnd.nextInt(2)) {
//            case 0:
//                return r.SAMPLE_COLLISION;
//            case 1:
//                return r.SAMPLE_COLLISION2;
//            case 2:
//                return r.SAMPLE_COLLISION3;
//            default:
//                return r.SAMPLE_COLLISION;
//        }
        return "";
    }

    /**
     * Save the state of the game to RecordStore
     */
    public void saveGame() {
        if (game == null) {
            return;
        }
        try {
            RecordStore snapshot = RecordStore.openRecordStore("GameSnapshot", true);
            if (snapshot.getNumRecords() == 0) {
                snapshot.addRecord(null, 0, 0);
            }
            byte[] data = game.getSnapshot();
            snapshot.setRecord(getRecordId(snapshot), data, 0, data.length);
            snapshot.closeRecordStore();
            System.out.println("state saved succesfully");
        }
        catch (RecordStoreException e) {
            System.out.println("exception occured while saving");
        }
    }

    /**
     * Get the first Record ID for a given record store
     */
    private int getRecordId(RecordStore store) throws RecordStoreException {
        RecordEnumeration e = store.enumerateRecords(null, null, false);
        try {
            return e.nextRecordId();
        }
        finally {
            e.destroy();
        }
    }

    /**
     * Check the state of keys
     */
    private void checkKeys() {
//        int keyState = getKeyStates();
//        if (gameState == TypeBattleShips.STATE_LEVEL) {
//            if ((keyState & LEFT_PRESSED) != 0) {
//                game.movePlateLeft();
//            }
//            else if ((keyState & RIGHT_PRESSED) != 0) {
//                game.movePlateRight();
//            }
//        }
    }

    /**
     * Handles the key pressed events
     * @param key
     */
    protected void keyPressed(int key) {
        switch (gameState) {
            case TypeBattleShips.STATE_MENU:
                switch (getGameAction(key)) {
                    case UP:
                        menu.selectPrev();
                        break;
                    case DOWN:
                        menu.selectNext();
                        break;
                    case FIRE:
                        menu.clickSelected();
                }
                break;
            case TypeBattleShips.STATE_INFO:
                switch (getGameAction(key)) {
                    case UP:
                        info.selectPrev();
                        break;
                    case DOWN:
                        info.selectNext();
                        break;
                    case FIRE:
                        info.clickSelected();
                }
                break;
            case TypeBattleShips.STATE_LEVEL:
                switch (key) {
                    case LEFT_SOFTKEY:
                        game.leftButtonPressed();
                        break;
                    case RIGHT_SOFTKEY:
                        game.rightButtonPressed();
                        break;
                }
                if (getGameAction(key) == FIRE) {
                    game.fire();
                }
                break;
            case TypeBattleShips.STATE_OPTIONS:
                switch (getGameAction(key)) {
                    case UP:
                        options.selectPrev();
                        break;
                    case DOWN:
                        options.selectNext();
                        break;
                    case FIRE:
                        options.clickSelected();
                }
                break;
            case TypeBattleShips.STATE_SELECTOR:
                switch (key) {
                    case LEFT_SOFTKEY:
                        selector.clickSelected(1);
                        break;
                    case RIGHT_SOFTKEY:
                        selector.clickSelected(0);
                        break;
                    default: 
                        break;
                }
                switch (getGameAction(key)) {
                    case LEFT:
                        selector.selectPrev();
                        break;
                    case RIGHT:
                        selector.selectNext();
                        break;
                    case FIRE:
                        selector.clickSelected();
                        break;
                    default:
                        break;
                }
                break;
            case TypeBattleShips.STATE_DEPLOYSHIPS:
                 switch (key) {
                    case LEFT_SOFTKEY:
                        deployShips.leftButtonPressed();
                        break;
                    case RIGHT_SOFTKEY:
                        deployShips.rightButtonPressed();
                        break;
                    default: 
                        break;
                }
                switch (getGameAction(key)) {
                    case UP:
                        deployShips.moveUp();
                        break;
                    case DOWN:
                        deployShips.moveDown();
                        break;
                    case LEFT:
                        deployShips.moveLeft();
                        break;
                    case RIGHT:
                        deployShips.moveRight();
                        break;
                    case FIRE:
                        deployShips.clickSelected();
                        break;
                    default: 
                        break;
                }
                break;
            case TypeBattleShips.STATE_BARCOS:
            {
                switch (getGameAction(key)) {
                    case UP:
                        barcos.selectPrev();
                        break;
                    case DOWN:
                        barcos.selectNext();
                        break;
                    case FIRE:
                        barcos.clickSelected();
                        break;
                }
                break;
            }
            case TypeBattleShips.STATE_MISION:
            {
                switch(getGameAction(key)){
                    case UP:
                        misionScreen.selectPrev();
                        break;
                    case DOWN:
                        misionScreen.selectNext();
                        break;
                    case FIRE:
                        misionScreen.clickSelected();
                        break; 
                }
                break;
            }
            case TypeBattleShips.STATE_MISION_LEVEL:
                 switch (key) {
                    case LEFT_SOFTKEY:
                        tableroEnemigo.leftButtonPressed();
                        break;
                    case RIGHT_SOFTKEY:
                        tableroEnemigo.rightButtonPressed();
                        break;
                    default: 
                        break;
                }
                switch (getGameAction(key)) {
                    case UP:
                        tableroEnemigo.selectUp();
                        break;
                    case DOWN:
                        tableroEnemigo.selectDown();
                        break;
                    case LEFT:
                        tableroEnemigo.selectLeft();
                        break;
                    case RIGHT:
                        tableroEnemigo.selectRight();
                        break;
                    case FIRE:
                        tableroEnemigo.clickSelected();
                        break;
                    default: 
                        break;
                }
                break;
            case TypeBattleShips.STATE_USER:
                 switch(getGameAction(key)){
                    case UP:
                        userScreen.selectPrev();
                        break;
                    case DOWN:
                        userScreen.selectNext();
                        break;
                    case FIRE:
                        userScreen.clickSelected();
                        break; 
                }
                break;
        }
    }

    /**
     * Updates the game logic and transitions
     */
    public void update() {
        switch (gameState) {
            case TypeBattleShips.STATE_TRANSITION:
                if (!(currentView != null && currentView.slideOut())) {
                    currentView = null;
                    if (!(targetView != null && targetView.slideIn())) {
                        currentView = targetView;
                        targetView = null;
                        gameState = nextState;
                    }
                }
                break;
            case TypeBattleShips.STATE_LEVEL:
                game.update();
                break;
        }
    }

    /**
     * Render a frame
     */
    public void render() {
        final Graphics g = getGraphics();
        paint(g);
        flushGraphics();
    }

    /**
     * Renders the view
     * @param g
     */
    public void paint(Graphics g) {
        int anchor = Graphics.LEFT | Graphics.TOP;
        g.setColor(0xFF000000);
        g.fillRect(0, 0, displayWidth, displayHeight);
        
        g.drawImage(r.background, cornerX, cornerY, anchor);
        if (gameState == TypeBattleShips.STATE_DEPLOYSHIPS || gameState == TypeBattleShips.STATE_MISION_LEVEL){
            g.drawImage(r.fondo, cornerX, cornerY, anchor);
        }
        g.drawImage((gameState == TypeBattleShips.STATE_MENU || gameState == TypeBattleShips.STATE_INFO) ? r.signOn : r.signOff,
                    cornerX, cornerY + (int) (scaling * 86 + 0.5), anchor);
        if(gameState == TypeBattleShips.STATE_TRANSITION) {
            g.setClip(cornerX, cornerY, gameWidth, gameHeight);
        } else {
            g.setClip(0, 0, displayWidth, displayHeight);
        }
        try {
            if (currentView != null) {
                currentView.paint(g);
            }
            if (targetView != null) {
                targetView.paint(g);
            }
        }
        catch (NullPointerException npe) {
            // just no painting then
        }
        if ((gameState == TypeBattleShips.STATE_MENU || gameState == TypeBattleShips.STATE_INFO) && pressed) {
            ElectricArc arc = new ElectricArc();
            for (int a = 2 + rnd.nextInt(4); a > 0; a--) {
                g.setColor(rnd.nextInt(255), 255, 255);
                arc.paint(g, x, y, (int) (scaling * 10), (int) (scaling * 10));
            }
        }
    }

    protected void showNotify() {
        r.loadResources(); 
        if (menu == null) {
            createMenu();
           // menu.hideResume();
        }
        if (info == null) {
            createInfoScreen();
        }
        if (game == null) {
            loadOrCreateGame();
        }
        if (options == null) {
            createOptionScreen();
            options.setSounds(audioEnabled);
            options.setVibrator(vibratorManager.isOn());
        }
        if (selector == null){
            createSelectorScreen();
        }
        if (deployShips == null){
            createDeployShips();
        }
        if (barcos == null){
            createBarcosScreen();
        }
        if (misionScreen == null){
            createMisionScreen();
        }
        if (tableroEnemigo == null){
            createTableroEnemigo();
        }
        if (userScreen == null){
            createUserScreen();
        }
        startApp();
    }

    protected void hideNotify() {
        pauseApp();
        r.freeResources();
        audioManager.stopAll();
    }

    /**
     * Stop the game loop
     */
    public void stopApp() {
        gameState = TypeBattleShips.STATE_MENU;
        this.gameThread.requestStop();
        LightManager.allowDimming();
    }

    /**
     * Start the game loop
     */
    public void startApp() {
        if (gameThread == null) {
            gameThread = new BattleshipCanvas.GameThread();
        }
        gameThread.requestStart();
        LightManager.avoidDimming();
    }

    /**
     * Pause the game loop
     */
    public void pauseApp() {
        gameThread.requestPause();
        LightManager.allowDimming();
    }

    /**
     * Game Thread
     */
    class GameThread
            extends Thread {

        private boolean pause = true;
        private boolean stop = false;
        private boolean started = false;

        public void requestStart() {
            this.pause = false;
            if (!started) {
                this.start();
                this.started = true;
            }
            else {
                synchronized (this) {
                    notify();
                }
            }
        }

        public void requestPause() {
            this.pause = true;
        }

        public void requestStop() {
            this.stop = true;
        }

        /**
         * The game loop. This example uses only one thread for updating
         * game logic and for rendering with constant frequency.
         */
        public void run() {
            long time = 0;
            while (!stop) {
                try {
                    if (pause) {
                        synchronized (this) {
                            wait();
                        }
                    }
                    else {
                        time = System.currentTimeMillis();
                        checkKeys();
                        update();
                        render();

                        // Sleep the rest of the time
                        time = INTERVAL - (System.currentTimeMillis() - time);
                        Thread.sleep((time < 0 ? 0 : time));
                    }
                }
                catch (Exception e) {
                }
            }
        }
    }

    /**
     * Handle pointer pressed
     */
    public void pointerPressed(int x, int y) {
        this.x = x;
        this.y = y;
        pressed = true;
        if (gameState == TypeBattleShips.STATE_MENU) {
            electricity = audioManager.loopSample(r.SAMPLE_ELECTRICITY);
        }

        switch (gameState) {
            case TypeBattleShips.STATE_LEVEL:
                game.pointerPressed(x - cornerX, y - cornerY);
                break;
            case TypeBattleShips.STATE_MENU:
                menu.pointerEvent(Menu.POINTER_PRESSED, x, y);
                audioManager.playSample(r.SAMPLE_BUTTON);
                break;
            case TypeBattleShips.STATE_USER:
                userScreen.pointerEvent(Menu.POINTER_PRESSED, x, y);
                audioManager.playSample(r.SAMPLE_BUTTON);
                break;
            case TypeBattleShips.STATE_INFO:
                info.pointerEvent(Menu.POINTER_PRESSED, x, y);
                audioManager.playSample(r.SAMPLE_BUTTON);
                break;
            case TypeBattleShips.STATE_OPTIONS:
                options.pointerEvent(Menu.POINTER_PRESSED, x, y);
                audioManager.playSample(r.SAMPLE_BUTTON);
                break;
            case TypeBattleShips.STATE_SELECTOR:
                selector.pointerEvent(Menu.POINTER_PRESSED, x, y);
                audioManager.playSample(r.SAMPLE_BUTTON);
                break;
            case TypeBattleShips.STATE_BARCOS:
                barcos.pointerEvent(Menu.POINTER_PRESSED, x, y);
                audioManager.playSample(r.SAMPLE_BUTTON);
                break;
            case TypeBattleShips.STATE_MISION:
                misionScreen.pointerEvent(Menu.POINTER_PRESSED, x, y);
                audioManager.playSample(r.SAMPLE_BUTTON);
                break;
//            case TypeBattleShips.STATE_MISION_LEVEL:
//                misionScreen.pointerEvent(Menu.POINTER_PRESSED, x, y);
//                audioManager.playSample(r.SAMPLE_BUTTON);
//                break;
        }
    }
    /**
     * Handle pointer released
     */
    public void pointerReleased(int x, int y) {
        pressed = false;
        if (electricity != null) {
            audioManager.stopPlayer(electricity);
            electricity = null;
        }
        // STOP loop
        switch (gameState) {
            case TypeBattleShips.STATE_LEVEL:
                game.pointerReleased(x - cornerX, y - cornerY);
                break;
            case TypeBattleShips.STATE_MENU:
                menu.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_USER:
                userScreen.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_INFO:
                info.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_OPTIONS:
                options.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_SELECTOR:
                selector.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_BARCOS:
                barcos.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_MISION:
                misionScreen.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_MISION_LEVEL:
                tableroEnemigo.pointerEvent(Map.POINTER_RELEASED, x, y);
                break;
                
        }
    }

    /**
     * Handle pointer dragged
     */
    public void pointerDragged(int x, int y) {
        this.x = x;
        this.y = y;

        switch (gameState) {
            case TypeBattleShips.STATE_LEVEL:
                game.pointerDragged(x - cornerX, y - cornerY);
                break;
            case TypeBattleShips.STATE_MENU:
                menu.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_USER:
                userScreen.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_INFO:
                info.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_OPTIONS:
                options.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_SELECTOR:
                selector.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_DEPLOYSHIPS:
                deployShips.pointerEvent(Map.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_BARCOS:
                barcos.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_MISION:
                misionScreen.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_MISION_LEVEL:
                tableroEnemigo.pointerEvent(Map.POINTER_PRESSED, x, y);
                break;
        }
    }

    /**
     * Create and initialize game menu
     */
    private void createMenu() {
        menu = new MainMenu(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int item) {
                switch(item){
                     case MainMenu.SLOT1:
                         showUserScreen();
                         break;
                     case MainMenu.SLOT2:
                         showUserScreen();
                         break;
                     case MainMenu.SLOT3:
                         showUserScreen();
                         break;
                 }
            }
         
        }, scaling);
    }
    
    private void createOptionScreen() {
        options = new OptionScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {
            
             public void itemClicked(int item) {
                switch (item) {
                    case OptionScreen.VIBRATOR:
                        vibratorManager.SetEnabled(options.toggleVibrator());
                        break;
                    case OptionScreen.SOUNDS:     
                        boolean enabled = options.toggleSounds();
                        AudioManager.audioEnabled = enabled;
                        audioEnabled = enabled;
                        audioManager.playSample(r.SAMPLE_BONUS);
                        break;
                    case OptionScreen.BACK:
                        showUserScreen();
                        break;
                }
            }
        }, scaling);        
    }
    
    private void createUserScreen(){
        userScreen = new UserScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

               public void itemClicked(int item) {
                switch (item) {
                    case UserScreen.CAMPAIGN:
                        newGame();        
                        break;
                    case UserScreen.ACTION:
                        showMisionScreen();                        
                        break;
                    case UserScreen.MULTIJUGADOR:
                        showSelector();                      
                        break;
                    case UserScreen.OPTIONS:
                        showOptions();
                        break;
                    case UserScreen.EXIT:
                        main.exit();
                        break;
                    case UserScreen.INFO:
                        showInfo();
                        break;
                }
            }
        },scaling);
    }
    
    private void createDeployShips(){
        deployShips = new DeployShips(cornerX, cornerY, gameWidth, gameHeight, r, new Map.Listener() {

            public void itemClicked(int x, int y) {
                deployShips.colocarBarco();
                //deployShips.girarBarco();
            }
            
            public void changeState(int state){
                switch(state)
                {
                    case TypeBattleShips.STATE_BARCOS:
                        showBarcos();
                        break;
                    case TypeBattleShips.GIRAR:
                        deployShips.girarBarco();
                        break;
                    case TypeBattleShips.STATE_MENU:
                        tableroEnemigo.generarMapa();
                        showTableroEnemigo();
                        break;
                }
            }
        }, scaling);
    }
    
    private void createTableroEnemigo(){
        tableroEnemigo = new EnemyBoard(cornerX, cornerY, gameWidth, gameHeight, r, new Map.Listener() {

            public void itemClicked(int x, int y) {
//                deployShips.colocarBarco();
            }
            
            public void changeState(int state){
                switch(state)
                {
//                    case TypeBattleShips.STATE_BARCOS:
//                        showBarcos();
//                        break;
//                    case TypeBattleShips.GIRAR:
////                        deployShips.ObtenerBarcos();
////                        deployShips.ObtenerMapa();
////                        showUserScreen();
//                        deployShips.girarBarco();
//                        break;
                    case TypeBattleShips.STATE_MENU:
                        showUserScreen();
                        break;
                }
            }
        }, scaling, TypeBattleShips.FACIL);
    }
    
    private void createMisionScreen() {
         misionScreen = new MisionScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {
             public void itemClicked(int item){
                 switch(item){
                     case MisionScreen.BACK:
                         showUserScreen();
                         break;
                     case MisionScreen.NEWGAME:
                         showSelector();
                         break;
                     case MisionScreen.RESUME:
                         break;
                 }
             }
         }, scaling);
    }

    /**
     * Create and initialize information screen
     */
    private void createInfoScreen() {
        info = new InfoScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int item) {
                switch (item) {
                    case InfoScreen.LINK:
                        Main midlet = Main.getInstance();
                        try {
                            if (midlet.platformRequest("http://projects.developer.nokia.com/JMEExplonoid")) {
                                midlet.exit();
                            }
                        }
                        catch (ConnectionNotFoundException cnfe) {
                        }
                        break;
                    case InfoScreen.BACK:
                        showUserScreen();
                        break;
                }
            }
        }, scaling);
    }
    
    private void createBarcosScreen(){
        barcos = new BarcosScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int item) {
                switch (item) {
                    case BarcosScreen.PORTAAVIONES:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.SHIPS[TypeBattleShips.PORTAAVIONES], 
                                                                TypeBattleShips.PORTAAVIONES,
                                                                TypeBattleShips.PORTAAVIONES_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0,
                                                                r));
                        barcos.hideItem(BarcosScreen.PORTAAVIONES);
                        showDeployShips();
                        break;  
                    case BarcosScreen.ACORAZADO:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.SHIPS[TypeBattleShips.ACORAZADO], 
                                                                TypeBattleShips.ACORAZADO,
                                                                TypeBattleShips.ACORAZADO_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0,
                                                                r));                        
                        barcos.hideItem(BarcosScreen.ACORAZADO);
                        showDeployShips();
                        break;                
                    case BarcosScreen.SUBMARINO:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.SHIPS[TypeBattleShips.SUBMARINO], 
                                                                TypeBattleShips.SUBMARINO,
                                                                TypeBattleShips.SUBMARINO_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0,
                                                                r));
                        barcos.hideItem(BarcosScreen.SUBMARINO);
                        showDeployShips();
                        break;
                    case BarcosScreen.DESTRUCTOR:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.SHIPS[TypeBattleShips.DESTRUCTOR], 
                                                                TypeBattleShips.DESTRUCTOR,
                                                                TypeBattleShips.DESTRUCTOR_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0,
                                                                r));
                        barcos.hideItem(BarcosScreen.DESTRUCTOR);
                        showDeployShips();
                        break;
                    case BarcosScreen.ESPIA:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.SHIPS[TypeBattleShips.ESPIA], 
                                                                TypeBattleShips.ESPIA,
                                                                TypeBattleShips.ESPIA_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0,
                                                                r));
                        barcos.hideItem(BarcosScreen.ESPIA);
                        showDeployShips();
                        break;
                    case BarcosScreen.BACK:
                        showDeployShips();
                        break;
                }
            }
        }, scaling);
    }
    
    private void  createSelectorScreen() {
       selector = new SelectorScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int item) {
                switch (item) {
                    case SelectorScreen.BUTTON_OK:
                        tableroEnemigo.generarMapa();
                        showTableroEnemigo();
                        break;
                    case SelectorScreen.BUTTON_CANCEL:
                        deployShips.generarMapa();
                        barcos.verItems();
                        showBarcos();
                        break;                        
                }
            }          
       }, scaling);
   }

    /**
     * Change view
     * @param targetView The view to change to
     */
    public void changeView(Slideable targetView) {
        this.targetView = targetView;
        gameState = TypeBattleShips.STATE_TRANSITION;
        audioManager.playSample(r.SAMPLE_TRANSITION);
    }

    /**
     * Resume to game preserving the game state
     */
    private void resumeGame() {
        nextState = TypeBattleShips.STATE_LEVEL;
        changeView(game);
    }

    /**
     * Start new game
     */
    private void newGame() {
        game.newGame();
        nextState = TypeBattleShips.STATE_LEVEL;
        changeView(game);
    }

    /**
     * Show menu screen
     */
    public void showMenu() {
        nextState = TypeBattleShips.STATE_MENU;
        changeView(menu);
    }
    
    public void showUserScreen() {
        
        nextState = TypeBattleShips.STATE_USER;
        changeView(userScreen);
    }

    /**
     * Show info screen
     */
    public void showInfo() {
        nextState = TypeBattleShips.STATE_INFO;
        changeView(info);
    }
        
    public void showOptions(){
        nextState = TypeBattleShips.STATE_OPTIONS;
        changeView(options);
    }
    
    public void showSelector(){
        nextState = TypeBattleShips.STATE_SELECTOR;
        changeView(selector);
    }
    
    private void showDeployShips() {
         nextState = TypeBattleShips.STATE_DEPLOYSHIPS;
         changeView(deployShips);
    }
    
    private void showBarcos(){
        nextState = TypeBattleShips.STATE_BARCOS;
        changeView(barcos);
    }
    
    private void showMisionScreen(){
        nextState = TypeBattleShips.STATE_MISION;
        changeView(misionScreen);
    }
    
    private void showTableroEnemigo(){
        nextState = TypeBattleShips.STATE_MISION_LEVEL;
        changeView(tableroEnemigo);
    }

    /**
     * Change level
     */
    public void nextLevel() {
        nextState = TypeBattleShips.STATE_LEVEL;
        changeView(game);
    }
}
