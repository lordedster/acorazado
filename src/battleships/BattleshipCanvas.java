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
import battleships.game.DeployShips;
import battleships.game.Resources;
import battleships.effects.ElectricArc;
import battleships.effects.VibratorManager;
import battleships.audio.AudioManager;
import battleships.effects.LightManager;
//import battleships.game.BTCliente;
import battleships.game.BtCommunication;
import battleships.game.EnemyBoard;
import battleships.game.FriendlyBoard;
import battleships.game.Shoot;
import battleships.game.maps.Grid;
import battleships.game.maps.Map;
import battleships.game.mpComunication;
import battleships.game.ships.BattleShip;
import battleships.menu.BarcosScreen;
import battleships.menu.SelectorScreen;
import battleships.menu.OptionScreen;
import battleships.menu.InfoScreen;
import battleships.menu.VictoryLoserScreen;
import battleships.menu.MainMenu;
import battleships.menu.Menu;
import battleships.menu.MenuEnJuego;
import battleships.menu.CampaignScreen;
import battleships.game.ships.TypeBattleShips;
import battleships.menu.MisionScreen;
import battleships.menu.MultiScreen;
import battleships.menu.SelectorNombre;
import battleships.menu.UserScreen;
import battleships.menu.multiPlayerScreen;
import battleships.menu.HistoryScreen;
import battleships.records.UserData;
import java.util.Random;
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
    private static final int INTERVAL_MP = 100;
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
    private BattleshipCanvas.MultiThread multiThread;
    private Main main;
    private MainMenu menu; 
    private OptionScreen options;
    private InfoScreen info;
    private SelectorScreen selector;
    private DeployShips deployShips;
    private BarcosScreen barcos;
    private MisionScreen misionScreen;
    private UserScreen userScreen;
    private SelectorNombre selectorNombre;
    private EnemyBoard tableroEnemigo;
    private FriendlyBoard tableroAmigo;
    private HistoryScreen historia;
    private mpComunication mpc;
    private MenuEnJuego menuEnJuego;
    private VictoryLoserScreen victoryLosser;
    private CampaignScreen campaignScreen;

    private Slideable currentView;
    private Slideable targetView;
    private Resources r;
    private Random rnd = new Random();
    private AudioManager audioManager;
    private VibratorManager vibratorManager;
    private multiPlayerScreen multiMenu;
    private boolean audioEnabled;
    
    // estructura para el guardado de memoria
    private UserData DATA;
    //private int modeSelected;
    private int intervalo = 0;
    
    //private BtCommunication btc;
    private  boolean isServer;
    private boolean mpg = false;
    private boolean connected = false;
    boolean mpturno = false;
    boolean readyToread = false;
    

   
    
    
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

        mpc = new mpComunication();
        
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
        
        
        
    }

    /**
     * Creates a new game and loads the previous status of the game, if it exists
     */
    private void loadOrCreateGame() {
        DATA = new UserData();
        try {
            //RecordStore.deleteRecordStore("GameSnapshot"); // Clear state data for testing purposes
            RecordStore snapshot = RecordStore.openRecordStore("GameSnapshot", true);
            if (snapshot.getNumRecords() == 0 || !DATA.load(snapshot.getRecord(getRecordId(snapshot)))) {
                DATA.reset();
            } else {
               // menu.showResume();
            }
            snapshot.closeRecordStore();
        }
        catch (RecordStoreException e) {
            DATA.reset();
        }
        catch (NumberFormatException nfe) { 
            nfe.printStackTrace();
        }
        audioEnabled = true;  
    }

    /**
     * Save the state of the game to RecordStore
     */
    public void saveGame() {
        if (DATA == null) {
            return;
        }
        try {
            RecordStore snapshot = RecordStore.openRecordStore("GameSnapshot", true);
            if (snapshot.getNumRecords() == 0) {
                snapshot.addRecord(null, 0, 0);
            }
            byte[] data = DATA.getSnapshot();
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
//    private void checkKeys() {
//        int keyState = getKeyStates();
//        if (gameState == TypeBattleShips.STATE_LEVEL) {
//            if ((keyState & LEFT_PRESSED) != 0) {
//                game.movePlateLeft();
//            }
//            else if ((keyState & RIGHT_PRESSED) != 0) {
//                game.movePlateRight();
//            }
//        }
//    }
    
    /**
     * Handles the key pressed events
     * @param key
     */
    protected void keyPressed(int key) {
        switch (gameState) {
            case TypeBattleShips.STATE_MENU_EN_JUEGO:                
                switch (getGameAction(key)) {
                    case UP:
                        menuEnJuego.selectPrev();
                        break;
                    case DOWN:
                        menuEnJuego.selectNext();
                        break;
                    case FIRE:
                        menuEnJuego.clickSelected();
                        break;
                }
                break; case TypeBattleShips.STATE_CAMPAIGN:                
                switch (getGameAction(key)) {
                    case UP:
                        campaignScreen.selectPrev();
                        break;
                    case DOWN:
                        campaignScreen.selectNext();
                        break;
                    case FIRE:
                        campaignScreen.clickSelected();
                        break;
                }
                break;
            case TypeBattleShips.STATE_WIN_OR_LOSSER:
                switch (key) {
                    case RIGHT_SOFTKEY:
                        victoryLosser.rightButtonPressed();
                        break;
                    default: 
                        break;
                }
                break;
            case TypeBattleShips.STATE_MENU:
                switch (key) {
                    case RIGHT_SOFTKEY:
                        menu.eliminarUsuario(r);
                        break;
                    default: 
                        break;
                }
                switch (getGameAction(key)) {
                    case UP:
                        menu.selectPrev();
                        break;
                    case DOWN:
                        menu.selectNext();
                        break;
                    case FIRE:
                        menu.clickSelected();
                        break;
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
                        break;
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
            case TypeBattleShips.STATE_MP:
            switch (getGameAction(key)) {
                    case UP:
                        multiMenu.selectPrev();
                        break;
                    case DOWN:
                        multiMenu.selectNext();
                        break;
                    case FIRE:
                        multiMenu.clickSelected();
                        break;
                    default:
                        break;
                }
                switch (key) {
                    case RIGHT_SOFTKEY:
                        killMPsession();
                        showUserScreen();
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
                 case TypeBattleShips.SP_TURNO_IA:
            {
                switch (key) {
                    case RIGHT_SOFTKEY:
                        if(mpg){
                            tableroAmigo.rightButtonPressed();
                        }
                        break;
                    default: 
                        break;
                }
                break;
            }
            case TypeBattleShips.SP_TURNO:
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
            case TypeBattleShips.STATE_CREATE_NOMBRE:
                switch (key) {
                    case LEFT_SOFTKEY:                        
                        if (selectorNombre.isNombreListo()){
                           selectorNombre.GuardarNombre();
                        }
                        break;
                    case RIGHT_SOFTKEY:
                        selectorNombre.borrarLetra(r);
                        break;
                    default: 
                        break;
                }
                switch(getGameAction(key)){                
                    case UP:
                        selectorNombre.selectUp();
                        break;
                    case DOWN:
                        selectorNombre.selectDown();
                        break;
                    case LEFT:
                        selectorNombre.selectLeft();
                        break;
                    case RIGHT:
                        selectorNombre.selectRight();
                        break;
                    case FIRE:
                        selectorNombre.clickSelected();
                        break;
                    default: 
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
            case TypeBattleShips.SP_TURNO_IA:
                if(tableroAmigo.isReady()){          
                    tableroAmigo.readyToShoot(false);
                    tableroAmigo.pcshoot();
                }
                if(tableroAmigo.isAtaqueEnCurso()){
                    if(!tableroAmigo.animarAtaque()){
                   
                            tableroAmigo.Atacar(false);
                       
                        resetIntervalo();                          
                        if(tableroAmigo.getAcertarBarco()){
                            animacionSacudidaSonido();
                            tableroAmigo.setAcertarBarcoFalse();
                        }                 
                    }
                }
                sumarIntervalo(1);
                if(getIntervalo()>= 7 && !tableroAmigo.isAtaqueEnCurso() && tableroAmigo.hasShot()){
                    tableroAmigo.setHasShot(false);
                    resetIntervalo();
                    tableroAmigo.Listener(TypeBattleShips.SP_TURNO);   
                }
                break;
            case TypeBattleShips.SP_TURNO:
                if(tableroEnemigo.isAtaqueEnCurso()){
                    if(!tableroEnemigo.animarAtaque()){
                        if(mpg)
                        {
                            tableroEnemigo.AtacarMP(false);
                        }
                        else
                        {
                            tableroEnemigo.Atacar(false);
                        }
                        
                        resetIntervalo();
                        if(tableroEnemigo.getAcertarBarco()){
                            animacionSacudidaSonido();
                            tableroEnemigo.setAcertarBarcoFalse();
                        }                      
                    }
                }                
                sumarIntervalo(1);
                if(getIntervalo()>= 7 && !tableroEnemigo.isAtaqueEnCurso() && tableroEnemigo.hasShoot()){
                    
                    resetIntervalo();
                    tableroEnemigo.setHasShoot(false);
                    tableroEnemigo.Listener(TypeBattleShips.SP_TURNO_IA);    
                }
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
        if (gameState == TypeBattleShips.STATE_DEPLOYSHIPS || gameState == TypeBattleShips.SP_TURNO || gameState == TypeBattleShips.SP_TURNO_IA){
            int c = cornerX;
            for(int i = 0; i < 240; i++){
                g.drawImage(r.fondo, c, cornerY, anchor);
                c++;
            }
        }
        g.drawImage(r.signOn, cornerX, cornerY + (int) (scaling * 86 + 0.5), anchor);
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
            g.drawString("Lo que sea", 0, 0, anchor);
                   

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
        if (DATA == null) {
            loadOrCreateGame();
        }
        if (misionScreen == null){
            createMisionScreen();            
            //showMisionScreen();
        }
        if (menu == null) {
            createMenu();            
            showMenu();
           // menu.hideResume();
        }
        if (tableroAmigo == null)
        {
            createTableroAmigo();
        }
        if (selectorNombre == null){
            createSelectorNombre();
        }
        if (info == null) {
            createInfoScreen();
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
        if (tableroEnemigo == null){
            createTableroEnemigo();
        }
        if (userScreen == null){
            createUserScreen();
        }

        if (multiMenu == null){
            createMultiMenu();
            //showMultiplayer();
        }

        if (victoryLosser == null){
            createWinOrLosserScreen();
            victoryLosser.setGanadoPerdido(false);
            //showWinOrLosser();
        }
        if (menuEnJuego == null){
            createMenuEnJuego();
        }
        
        if (campaignScreen == null){
            createCampaignScreen();
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
        gameState = TypeBattleShips.STATE_USER;
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
        
        if (multiThread == null) {
            multiThread = new BattleshipCanvas.MultiThread();
        }
        multiThread.requestStart();
        
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
                        //checkKeys();
                        update();
                        render();
                        //mpgameon();

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

    
        
    class MultiThread
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
                        mpgameon();
        }
    }
    
    
    
    public void mpgameon()
    {

        while(true)
        {
    if (mpg)
        {
           
            if( mpc.isLive())
                 {
                   
                    //if(readyToread)
                        {
                                  
                                  if(!mpc.isDatosListos())
                                  {
                                    mpc.leer();
                                  }
                                  
                            while(mpc.isDatosListos())
                            {

                                mpc.setDatosListos(false);
                                try
                                {
                                switch(mpc.getInt0())
                                {
                                        case 1:
                                        {
                                        
                                            String response;
                                            response = tableroAmigo.mp_disparo(mpc.recuperarDatosDisparo());
                                                                     
                                            mpc.responderDatos(response);
                                            mpc.setDatosListos(false);

                                            break;
                                        }
                                        case 2:
                                        {
                        
                                            tableroEnemigo.updateTableroEnemigo(mpc.getDatosString());
                                            mpc.clear();
                                            mpc.setDatosListos(false);
                                            break;
                                        }
                                }
                                }
                                catch (Exception e)
                                {
                                    killMPsession();
                                    showUserScreen();
                                    
                                }
                              
                            }
                        
                        }
                 
                 }
                 else
                {

                    if(!connected)
                    {
                     killMPsession();
                     showUserScreen();
                    }
                }
             }
    else
    {
        
    }
        }
    
    }
    /**
     * Handle pointer pressed
     */
    
    public void killMPsession()
    {
        mpg = false;
        mpc.kill();
        isServer = false;
        boolean connected = false;
        mpturno = false;
        readyToread = false;
        /*
         * 
         * 
         * 
         * 
         *    EVITAR QUE APAREZCA EL MENU PARA EL TABLERO AMIGO
         * 
         * 
         */
        tableroAmigo.setMpg(false);
        tableroEnemigo.setMpg(false);
    }
    public void pointerPressed(int x, int y) {
        this.x = x;
        this.y = y;
        pressed = true;

        switch (gameState) {
            case TypeBattleShips.STATE_MENU_EN_JUEGO:                
                menuEnJuego.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_MENU:
                menu.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_USER:
                userScreen.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_DEPLOYSHIPS:
                deployShips.pointerEvent(Map.POINTER_PRESSED, x, y);
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
            case TypeBattleShips.STATE_BARCOS:
                barcos.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_MISION:
                misionScreen.pointerEvent(Menu.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_CREATE_NOMBRE:
                selectorNombre.pointerEvent(SelectorNombre.POINTER_PRESSED, x, y);
                break;
            case TypeBattleShips.STATE_CAMPAIGN:
                campaignScreen.pointerEvent(CampaignScreen.POINTER_PRESSED, x, y);
                break;
           case TypeBattleShips.STATE_MP:
                multiMenu.pointerEvent(SelectorNombre.POINTER_PRESSED, x, y);
                break;
        }
    }
    /**
     * Handle pointer released
     */
    public void pointerReleased(int x, int y) {
        pressed = false;
        switch (gameState) {            
            case TypeBattleShips.STATE_MENU_EN_JUEGO:
                menuEnJuego.pointerEvent(Menu.POINTER_RELEASED, x, y);
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
            case TypeBattleShips.STATE_DEPLOYSHIPS:
                deployShips.pointerEvent(Map.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_MISION:
                misionScreen.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_CREATE_NOMBRE:
                selectorNombre.pointerEvent(SelectorNombre.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.SP_TURNO:
                tableroEnemigo.pointerEvent(Map.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_MP:
                multiMenu.pointerEvent(Menu.POINTER_RELEASED, x, y);
                break;
            case TypeBattleShips.STATE_CAMPAIGN:
                campaignScreen.pointerEvent(CampaignScreen.POINTER_RELEASED, x, y);
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
            case TypeBattleShips.STATE_MENU_EN_JUEGO:
                menuEnJuego.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_MENU:
                menu.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_USER:
                userScreen.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_MP:
                multiMenu.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_INFO:
                info.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_OPTIONS:
                options.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_SELECTOR:
                selector.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_DEPLOYSHIPS:
                deployShips.pointerEvent(Map.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_BARCOS:
                barcos.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_MISION:
                misionScreen.pointerEvent(Menu.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_CREATE_NOMBRE:
                selectorNombre.pointerEvent(SelectorNombre.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.SP_TURNO:
                tableroEnemigo.pointerEvent(Map.POINTER_DRAGGED, x, y);
                break;
            case TypeBattleShips.STATE_CAMPAIGN:
                campaignScreen.pointerEvent(CampaignScreen.POINTER_DRAGGED, x, y);
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
                         DATA.setUsuarioActual(UserData.PERFIL_A);
                         if (!DATA.existeNameUser1()){
                             showSelectorNombre();
                         }else{
                             tableroAmigo.setDificultad(DATA.getDificultadUsuarioActual());
                             options.setDificultad(DATA.getDificultadUsuarioActual());
                             boolean d = DATA.getGuardadoUsuarioActual();
                             if(DATA.getGuardadoUsuarioActual()){
                                misionScreen.showResume();
                             }else{
                                misionScreen.hideResume();
                             }
                             showUserScreen();
                         }
                         break;
                     case MainMenu.SLOT2:                         
                         DATA.setUsuarioActual(UserData.PERFIL_B);
                         if (!DATA.existeNameUser2()){
                             showSelectorNombre();
                         }else{                             
                             tableroAmigo.setDificultad(DATA.getDificultadUsuarioActual());
                             options.setDificultad(DATA.getDificultadUsuarioActual());
                             if(DATA.getGuardadoUsuarioActual()){
                                misionScreen.showResume();
                             }else{
                                misionScreen.hideResume();
                             }
                             showUserScreen();
                         }
                         break;
                     case MainMenu.SLOT3:                         
                         DATA.setUsuarioActual(UserData.PERFIL_C);
                         if (!DATA.existeNameUser3()){
                             showSelectorNombre();
                         }else{
                             tableroAmigo.setDificultad(DATA.getDificultadUsuarioActual());
                             options.setDificultad(DATA.getDificultadUsuarioActual());
                             if(DATA.getGuardadoUsuarioActual()){
                                misionScreen.showResume();
                             }else{
                                misionScreen.hideResume();
                             }
                             showUserScreen();
                         }
                         break;
                     case MainMenu.ELIMINADO:
                         DATA.eliminarUsuario(menu.getCodeEliminado());
                         saveGame();
                         break;
                         
                 }
            }
         
        }, scaling, 
           DATA.getNombreUsuario(UserData.PERFIL_A), 
           DATA.getNombreUsuario(UserData.PERFIL_B), 
           DATA.getNombreUsuario(UserData.PERFIL_C), r);
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
                    case OptionScreen.DIFICULTAD:
                        DATA.setDificultadUsuarioActual(options.toogleDificultad());
                        tableroAmigo.setDificultad(DATA.getDificultadUsuarioActual());
                        saveGame();
                        break;                        
                    case OptionScreen.USUARIO:
                        showMenu();
                        break;
                    case OptionScreen.BACK:
                        showUserScreen();
                        break;
                }
            }
        }, scaling, r);        
    }
    
    private void createUserScreen(){
        userScreen = new UserScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

               public void itemClicked(int item) {
                switch (item) {
                    case UserScreen.CAMPAIGN:
                        showCampaignScreen();        
                        break;
                    case UserScreen.ACTION:
                        showMisionScreen();                        
                        break;
                    case UserScreen.MULTIJUGADOR:
                        showMultiplayer();                     
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
        },scaling, r);
    }
    
    private void createDeployShips(){
        deployShips = new DeployShips(cornerX, cornerY, gameWidth, gameHeight, new Map.Listener() {

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
                    case TypeBattleShips.STATE_MISION: 
                        tableroAmigo.crearMapaManualmente(deployShips.ObtenerMapa());
                        tableroAmigo.crearBarcosManualmente(deployShips.ObtenerBarcos());
                        tableroAmigo.positionGrid();                        
                        
                        if(mpg) // si estoy en MP cargo el mapa dependiendo de si soy servidor o cliente para ver quien arrancua
                        {    
                            tableroEnemigo.RellenarMapa(r, scaling);
                            tableroEnemigo.CargarBarcosVoladores();
                            tableroEnemigo.positionGrid();                            
                            tableroAmigo.verMenu();
                            if(isServer)                            
                            {
                                readyToread = false;
                                showTableroEnemigo();
                            }
                            else
                            {
                                    readyToread = true;
                                showTableroAmigo();                            
                            }
                            
                        }   
                        else
                        {
                            tableroEnemigo.generarMapa(r);
                            readyToread = true;
                            showTableroEnemigo();
                        }
                        break;
                    case TypeBattleShips.SACUDIR:
                        animacionSacudidaSonido();
                        break;
                }
            }
        }, scaling, r.girar);
    }
    
    private void createTableroEnemigo(){
        tableroEnemigo = new EnemyBoard(cornerX, cornerY, gameWidth, gameHeight, r, new Map.Listener() {

            public void itemClicked(int x, int y) {

                if(mpg)
                {
                    tableroEnemigo.playerShootMP(x, y);
                }
                else
                {
                    tableroEnemigo.PlayerShoot(x, y);
                }
            }
            
            public void changeState(int state){
                switch(state)
                {
                    case TypeBattleShips.STATE_MENU_EN_JUEGO:
                        DATA.setMapaEnemySigle(tableroEnemigo.getMapaParaGuardar());
                        DATA.setShipsEnemySigle(tableroEnemigo.getBarcosParaGuardar());
                        DATA.setMapaFriendSigle(tableroAmigo.getMapaParaGuardar());
                        DATA.setShipsFriendSigle(tableroAmigo.getBarcosParaGuardar());
                        DATA.setGuardadoSingle(true);
                        saveGame();
                        showMenuEnJuego();
                        misionScreen.showResume();
                        break;
                    case TypeBattleShips.SP_TURNO_IA:
                        if(tableroEnemigo.sinBarcos()){
                            int points = tableroEnemigo.getPuntaje() + tableroAmigo.getPuntaje();
                           victoryLosser.setPuntos(points); 
                           tableroEnemigo.setPuntaje(0);
                           tableroAmigo.setPuntaje(0);
                           victoryLosser.setGanadoPerdido(true);
                            killMPsession();
                            
                            if(!mpg)
                            {
                                if(points > getRecordSingle())
                                {
                                    setRecordSingle(points);
                                }
                            }
                            
                            showWinOrLosser();
                        }else{
                            if(!mpg)
                            {
                               tableroAmigo.readyToShoot(true);
                            }
                            showTableroAmigo();
                        }
                        break;  
                    case TypeBattleShips.SACUDIR:
                        animacionSacudidaSonido();
                        break;
                    case TypeBattleShips.BT_CLIENTE:
                    {
                            mpturno=false;
                            if(!mpc.startClient())
                                    {
                                       
                                        killMPsession();
                                        showUserScreen();
                                    }
                            else
                            {
                                connected = true;
                                isServer = false;
                                showSelector();
                            }
                   
      
                       
                        
                        break;
                    }
                    case TypeBattleShips.BT_SERVIDOR:
                    {
                         try
                        {      
                        mpturno=true;        
                  
                        mpc.startServer();
                                           
                        connected = true;
                        isServer = true;
                        showSelector();
                        
                          }
                        catch (Exception e)
                        {
                            killMPsession();
                            showUserScreen();
                        }
                        break;
                    }

                    case TypeBattleShips.MP_DISPARAR:
                            {
                                if(tableroEnemigo.getMP_shoot() != null)
                                {
                                    readyToread = true;
                                    mpc.enviarDatosDisparo(tableroEnemigo.getMP_shoot());
                                }
                            break;
                            }
                }
            }
        }, scaling, TypeBattleShips.FACIL, mpg);
    }
    
    private void animacionSacudidaSonido(){        
        vibratorManager.vibrate(500);
        audioManager.playSample(r.SAMPLE_EXPLOSION);
    }
    
    private void createTableroAmigo(){
        tableroAmigo = new FriendlyBoard(cornerX, cornerY, gameWidth, gameHeight, r, new Map.Listener() {

            public void itemClicked(int x, int y) {
                
            }
            
            public void changeState(int state){
                switch(state)
                {
                    case TypeBattleShips.STATE_MENU:
                        showUserScreen();
                        break;                        
                    case TypeBattleShips.SP_TURNO:
                       if(tableroAmigo.sinBarcos()){
                           int points = tableroEnemigo.getPuntaje() + tableroAmigo.getPuntaje();
                           victoryLosser.setPuntos(points);  
                           tableroEnemigo.setPuntaje(0);
                           tableroAmigo.setPuntaje(0);
                            victoryLosser.setGanadoPerdido(false);
                            if(!mpg)
                            {
                                if(points > getRecordSingle())
                                {
                                    setRecordSingle(points);
                                }
                            }
                            
                            killMPsession();
                            showWinOrLosser();
                        }else{
                            showTableroEnemigo();                                
                        }
                        break; 
                    case TypeBattleShips.SACUDIR:
                        animacionSacudidaSonido();
                        break;   
                    case TypeBattleShips.BT_CLIENTE:
                    {
                        mpc.startClient();
                        mpg = true;
                        showSelector();
                        break;
                    }
                    case TypeBattleShips.BT_SERVIDOR:
                    {
                        //mpc = new mpComunication();
                        mpc.startServer();
                        mpg = true;
                        showSelector();
                        break;
                    }    
                }
            }
        }, scaling, TypeBattleShips.FACIL, mpg);
    }
        
        
    private void createMultiMenu(){
        multiMenu = new multiPlayerScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int x) {

                switch(x)
                {
                    case multiPlayerScreen.ATRAS:
                            showUserScreen();                            
                            break;
                        
                    case multiPlayerScreen.CLIENTE:
                            isServer = false;
                            tableroAmigo.startAsCliente();
                            break;
                        
                    case multiPlayerScreen.SERVIDOR:
                            isServer = true;
                            tableroAmigo.startAsServer();
                            break;
                }
                        
            }
            
           
            
        }, scaling,  r);
    }
    
    private void createCampaignScreen(){
        campaignScreen = new CampaignScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int x) {

                switch(x)
                {
                    case CampaignScreen.ATRAS:
                            showUserScreen();                            
                            break;                        
                    case CampaignScreen.CONTINUAR:
                            break;                        
                    case CampaignScreen.MISION:
                            break;                        
                    case CampaignScreen.NUEVO:
                        
                            break;
                    case CampaignScreen.RECORDS:
                            break;
                        
                }                        
            }
        }, scaling,  r);
    }
    
        private void createHistoriaScreen(){
        campaignScreen = new CampaignScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int x) {

                              
            }
        }, scaling,  r);
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
                         tableroEnemigo.loadBarcos(DATA.getBarcosEnemySingle());
                         tableroEnemigo.loadMapa(DATA.getMapaEnemySingle());
                         tableroAmigo.loadBarcos(DATA.getBarcosFriendSingle());
                         tableroAmigo.loadMapa(DATA.getMapaFriendSingle());
                         tableroAmigo.buscarAcertado();
                         showTableroEnemigo();
                         break;
                 }
             }
         }, scaling, r);
    }
    
    private void createMenuEnJuego() {
         menuEnJuego = new MenuEnJuego(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {
             public void itemClicked(int item){
                 switch(item){
                     case MenuEnJuego.CONTINUAR:
                         showTableroEnemigo();
                         break;
                     case MenuEnJuego.GUARDAR:
                         showUserScreen();
                         break;
                 }
             }
         }, scaling, r);
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
                            if (midlet.platformRequest("http://github.com/lordedster/acorazado")) {
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
        }, scaling, r);
    }
    
    private void createBarcosScreen(){
        barcos = new BarcosScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int item) {
                switch (item) {
                    case BarcosScreen.PORTAAVIONES:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.PORTAAVIONES,
                                                                TypeBattleShips.PORTAAVIONES_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0));
                        barcos.hideItem(BarcosScreen.PORTAAVIONES);
                        barcos.selectNext();
                        showDeployShips();
                        break;  
                    case BarcosScreen.ACORAZADO:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.ACORAZADO,
                                                                TypeBattleShips.ACORAZADO_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0));                        
                        barcos.hideItem(BarcosScreen.ACORAZADO);
                        barcos.selectNext();
                        showDeployShips();
                        break;                
                    case BarcosScreen.SUBMARINO:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.SUBMARINO,
                                                                TypeBattleShips.SUBMARINO_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0));
                        barcos.hideItem(BarcosScreen.SUBMARINO);
                        barcos.selectNext();
                        showDeployShips();
                        break;
                    case BarcosScreen.DESTRUCTOR:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.DESTRUCTOR,
                                                                TypeBattleShips.DESTRUCTOR_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0));
                        barcos.hideItem(BarcosScreen.DESTRUCTOR);
                        barcos.selectNext();
                        showDeployShips();
                        break;
                    case BarcosScreen.ESPIA:
                        deployShips.agregarBarco(new BattleShip(TypeBattleShips.ESPIA,
                                                                TypeBattleShips.ESPIA_SIZE,
                                                                TypeBattleShips.HORIZONTAL,
                                                                0,0));
                        barcos.hideItem(BarcosScreen.ESPIA);
                        barcos.selectNext();
                        showDeployShips();
                        break;
                    case BarcosScreen.BACK:
                        showDeployShips();
                        break;
                }
            }
        }, scaling, r);
    }
    
    private void  createSelectorScreen() {
       selector = new SelectorScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int item) {
                switch (item) {
                    case SelectorScreen.BUTTON_OK:
                        
                        tableroAmigo.generarMapa(r);
                        
                        
                        if(mpg) // sy estoy en MP cargo el mapa dependiendo de si soy servidor o cliente para ver quien arrancua
                        {      
                            tableroEnemigo.RellenarMapa(r, scaling); 
                            tableroEnemigo.CargarBarcosVoladores();
                            tableroEnemigo.positionGrid();
                            tableroAmigo.verMenu();
                            if(isServer)                            
                            {
                               
                                readyToread = false;
                                showTableroEnemigo();
                            }
                            else
                            {
                                readyToread = true;
                                showTableroAmigo();
                            }
                            
                        }   
                        else
                        {
                            tableroEnemigo.generarMapa(r);
                            readyToread = true;
                           showTableroEnemigo();
                        }                      
                        
                        //asdasd
                        
                        
                        break;
                    case SelectorScreen.BUTTON_CANCEL:  
                        deployShips.seteoMemoria();
                        deployShips.generarMapa(r);
                        barcos.verItems();
  
                        showBarcos();
                        break;                        
                }
            }          
       }, scaling, r);
    }
    
    private void createSelectorNombre(){
        selectorNombre = new SelectorNombre(cornerX, cornerY, gameWidth, gameHeight, new SelectorNombre.Listener() {

            public void itemClicked(int x, int y) {
                selectorNombre.AgregarLetra(x,y,r);                
            }    

            public void changeState(int item) {
                switch (item) {       
                    case SelectorNombre.VOLVER:
                        showMenu();
                        break;
                    case SelectorNombre.ACEPTAR:
                        DATA.setNombreUsuarioActual(selectorNombre.obtenerNombre());
                        menu.setNombre(DATA.getUsuarioActual(),DATA.getNombreUsuarioActual());
                        saveGame();
                        misionScreen.hideResume();
                        showUserScreen();
                        break;
                }
            }
       }, scaling, r);
    }
     private void createWinOrLosserScreen(){
        victoryLosser = new VictoryLoserScreen(cornerX, cornerY, gameWidth, gameHeight, new Menu.Listener() {

            public void itemClicked(int x) {
                switch (x){
                    case TypeBattleShips.STATE_USER:
                        DATA.setGuardadoSingle(false);
                        misionScreen.hideResume();
                        saveGame();
                        showUserScreen();
                    break;
                }
            }  
       }, scaling, r);
    }

    /**
     * Change view
     * @param targetView The view to change to
     */
    public void changeView(Slideable targetView) {
        this.targetView = targetView;
        gameState = TypeBattleShips.STATE_TRANSITION;
        if(nextState == TypeBattleShips.SP_TURNO || nextState == TypeBattleShips.SP_TURNO_IA ){  
        }else{
            audioManager.playSample(r.SAMPLE_TRANSITION);
        }
    }

    /**
     * Resume to game preserving the game state
     */
//    private void resumeGame() {
//        nextState = TypeBattleShips.STATE_LEVEL;
//        changeView(game);
//    }

    /**
     * Start new game
     */
//    private void newGame() {
//        game.newGame();
//        nextState = TypeBattleShips.STATE_LEVEL;
//        changeView(game);
//    }

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
        nextState = TypeBattleShips.SP_TURNO;
        changeView(tableroEnemigo);
    }
    
    private void showMultiplayer(){
        nextState = TypeBattleShips.STATE_MP;
        changeView(multiMenu);
    }
    
     private void showTableroAmigo(){
        nextState = TypeBattleShips.SP_TURNO_IA;
        changeView(tableroAmigo);
    }
    
    private void showSelectorNombre(){
        nextState = TypeBattleShips.STATE_CREATE_NOMBRE;
        changeView(selectorNombre);
    }

    
    private void showCampaignScreen(){
        nextState = TypeBattleShips.STATE_CAMPAIGN;
        changeView(campaignScreen);
    }
    
    private void showWinOrLosser(){
        nextState = TypeBattleShips.STATE_WIN_OR_LOSSER;
        changeView(victoryLosser);
    }
    
    private void showMenuEnJuego(){
        nextState = TypeBattleShips.STATE_MENU_EN_JUEGO;
        changeView(menuEnJuego);
    }
    
    private void showHistoriaScreen(){        
        nextState = TypeBattleShips.STATE_MENU_EN_JUEGO;
        changeView(historia);
    }
    
    private void sumarIntervalo(int suma){
        this.intervalo += suma; 
    }
    
    private int getIntervalo(){
        return this.intervalo;
    }
    
    private void resetIntervalo(){
        this.intervalo = 0;
    }   
    

}


