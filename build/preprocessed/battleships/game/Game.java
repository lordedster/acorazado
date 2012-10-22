/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 * See LICENSE.TXT for license information.
 */

package battleships.game;

//import battleships.Main;
//import battleships.effects.LightManager;
import battleships.effects.Slideable;
//import com.nokia.example.explonoid.effects.Shaker;
//import com.nokia.example.explonoid.effects.ShockWaveManager;
//import com.nokia.example.explonoid.effects.SparkManager;
import battleships.game.ships.TypeBattleShips;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
//import java.util.Random;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
//import java.util.Vector;

public class Game
        extends LayerManager
        implements Slideable {

//    public static final int STATE_BARCOS = 9;
//    public static final int EVENT_BRICK_COLLISION = 0;
//    public static final int EVENT_BRICK_EXPLOSION = 1;
//    public static final int EVENT_PLATE_COLLISION = 2;
//    public static final int EVENT_WALL_COLLISION = 3;
//    public static final int EVENT_LEVEL_CHANGE = 4;
//    public static final int EVENT_BUTTON_PRESSED = 5;
//    public static final int EVENT_BALL_OUT = 6;
//    public static final int EVENT_BONUS = 7;
//    public static final int EVENT_LEVEL_STARTED = 8;
//    public static final int EVENT_GAME_OVER = 9;
//    private static final int LIVES = 5;
//    private static final int WALL_PADDING = 4;
    private final int IN_X;
    private final int OUT_X;
//    private int levelNumber;
//    private int plateY;
    private int cornerX;
    private int cornerY;
    private int displayWidth;
    private int displayHeight;
//    private int pointerX;
//    private boolean pointerPressed;
//    private boolean menuPressed = false;
//    private boolean changeLevel;
    private boolean aiming = true;
//    private boolean stretched = false;
    private Resources r;
    //private Plate plate;
    //private Ball ball;
    //private BrickGrid bricks;
//    private Sprite leftWall;
//    private Sprite rightWall;
//    private Sprite topWall;
    private Sprite dashboard;
    private Sprite menuButton;
    private Sprite newGameButton;
    //private Shaker shaker;
    //private ShockWaveManager shockWaveManager;
    //private SparkManager sparkManager;
//    private Vector lives;
//    private Random rnd = new Random();
    private Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
    private final Game.Listener listener;

    public interface Listener {

        void changeState(int state);

        void handleEvent(int event);
    }

    /**
     * Constructor
     * @param cornerX Horizontal position of the top left coordinate of the game area
     * @param cornerY Vertical position of the top left coordinate of the game area
     * @param width Width of the Game area
     * @param height Height of the Game area
     * @param r Game resources
     * @param listener Game event listener
     */
    public Game(int cornerX, int cornerY, int width, int height, Resources r, Game.Listener listener) {
        this.listener = listener;
        this.displayWidth = width;
        this.displayHeight = height;        
        this.r = r;
//
        IN_X = cornerX;
        OUT_X = cornerX + displayWidth;        
//
        this.cornerX = OUT_X;
        this.cornerY = cornerY;
//
        createGame();
        setViewWindow(0, 0, width, height);
    }

    /**
     * Creates and initializes the game assets
     */
    private void createGame() {
//        lives = new Vector();
//
//        // Create Bricks
//        bricks = new BrickGrid(this, displayWidth, displayHeight, r);
//
//        // Create Plate
//        plate = new Plate(displayWidth, r);
//        plateY = displayHeight - plate.getHeight() - r.dashboard.getHeight() / 2;
//        plate.setPosition((displayWidth - plate.getWidth()) / 2, plateY);
//
//        // Create Ball
//        ball = new Ball(r);
//
//        // Create Walls
//        topWall = new Sprite(r.horizontalWall, r.horizontalWall.getWidth(), r.horizontalWall.getHeight() / 2);
//        leftWall = new Sprite(r.verticalWall, r.verticalWall.getWidth() / 2, r.verticalWall.getHeight());
//        rightWall = new Sprite(r.verticalWall, r.verticalWall.getWidth() / 2, r.verticalWall.getHeight());
//
//        int wallPadding = r.scale(WALL_PADDING);
//        topWall.defineCollisionRectangle(0, 0, displayWidth,
//                                         topWall.getHeight() - wallPadding);
//        leftWall.defineCollisionRectangle(0, 0,
//                                          leftWall.getWidth() - wallPadding, leftWall.getHeight() - wallPadding);
//        rightWall.defineCollisionRectangle(wallPadding, 0,
//                                           rightWall.getWidth() - wallPadding, rightWall.getHeight() - wallPadding);
//
//        topWall.setPosition(0, 0);
//        leftWall.setPosition(r.scale(-3), 0);
//        rightWall.setPosition(displayWidth + r.scale(3) - rightWall.getWidth(), 0);
//
        // Create Dashboard
        dashboard = new Sprite(r.dashboard);
        dashboard.setPosition(0, displayHeight - dashboard.getHeight());
//
        // Create Buttons
        menuButton = new Sprite(r.menuButton);
        menuButton.setPosition(r.scale(180), displayHeight - menuButton.getHeight());
//        newGameButton = new Sprite(r.newGameButton);
//        newGameButton.setPosition(r.scale(10), displayHeight - newGameButton.getHeight());
//
//        // Append sprites to LayerManager
//        append(plate);
//        append(topWall);
//        append(leftWall);
//        append(rightWall);
//        append(ball);
//        pointerPressed = false;
//
//        // Create effect managers        
//        shockWaveManager = new ShockWaveManager();
//        sparkManager = new SparkManager(24, r, this);
//        shaker = new Shaker();
    }

    /**
     * Render game graphics
     */
    public void paint(Graphics g) {
//        shockWaveManager.paint(g);
        paint(g,cornerX, cornerY);
//        paint(g, cornerX + shaker.magnitudeX, cornerY + shaker.magnitudeY);
//        if (lives.isEmpty()) {
//            g.drawImage(r.gameOver, cornerX + displayWidth / 2,
//                        cornerY + displayHeight / 2, Graphics.VCENTER | Graphics.HCENTER);
//        }
        g.setColor(0xFFFFFFFF);
        g.setFont(font);
//        g.drawString("" + levelNumber, cornerX + shaker.magnitudeX + displayWidth / 2,
//                     cornerY + shaker.magnitudeY + displayHeight, Graphics.BOTTOM | Graphics.HCENTER);
//        topWall.setFrame(0);
//        leftWall.setFrame(0);
//        rightWall.setFrame(0);
    }

    /**
     * Update game logic
     */
    public void update() {
//        if (!lives.isEmpty()) {
//            if (aiming) {
//                aim();
//            }
//            else {
//                ball.move();
//
//                // Ball out of screen
//                if (ball.getY() > displayHeight) {
//                    sparkManager.burst(ball.getRefPixelX(), displayHeight);
//                    int refX = cornerX + ball.getRefPixelX();
//                    int refY = cornerY + ball.getRefPixelY();
//                    shockWaveManager.createShockWave(refX, refY, r.scale(900), 3);
//                    shockWaveManager.createShockWave(refX, refY, r.scale(500), 4);
//                    shockWaveManager.createShockWave(refX, refY, r.scale(100), 6);
//                    vibrate(1000);
//                    LightManager.pulse(3);
//                    cancelBonuses();
//                    Sprite life = (Sprite) lives.lastElement();
//                    lives.removeElement(life);
//                    remove(life);
//                    if (lives.isEmpty()) {
//                        newGameButton.setVisible(true);
//                        listener.handleEvent(EVENT_GAME_OVER);
//                    }
//                    else {
//                        placeBall();
//                    }
//                    listener.handleEvent(EVENT_BALL_OUT);
//                }
//            }
//            if (pointerPressed) {
//                movePlate();
//            }
//            checkCollisions();
//        }
    }

    /**
     * Keep ball attached to the plate
     */
    private void aim() {
//        ball.setPosition(plate.getRefPixelX() - ball.getWidth() / 2, plate.getTopY() - ball.getHeight());
    }

    public void cancelBonuses() {
//        if (stretched) {
//            plate.shrink();
//        }
    }

    /**
     * Set ball to its starting position and adjust velocity to
     */
    public void placeBall() {
//        ball.setVelocityX(0);
//        ball.setVelocityY(r.scale(-4 - levelNumber / 3) - 1);
//        aiming = true;
//        aim();
    }

    /**
     * Check collisions
     */
    public void checkCollisions() {
//        checkPlateCollision();
//        checkWallCollisions();
//        checkBrickCollisions();
    }

    /**
     * Check and act for collisions between the ball and the plate
     */
    public void checkPlateCollision() {
//        if (ball.collidesWith(plate, false)) {
//            if (ball.getRefPixelY() < plate.getRefPixelY()) {
//                ball.bounceUp();
//                // Calculate horizontal velocity relative to the distance
//                // between plate center and collision point
//                ball.setVelocityX((int) Math.ceil(r.scale(-8.0)
//                        * (plate.getRefPixelX() - ball.getRefPixelX())
//                        / (plate.getPlateWidth())));
//                listener.handleEvent(EVENT_PLATE_COLLISION); // Inform listener
//            }
//        }
    }

    /**
     * Check and act for collisions between the ball and the walls
     */
    public void checkWallCollisions() {
//        boolean collision = false;
//        if (ball.collidesWith(topWall, false) || ball.getY() < 0) {
//            ball.bounceDown();
//            topWall.setFrame(1); // Glow pulse
//            collision = true;
//        }
//        if (ball.collidesWith(leftWall, false) || ball.getX() < 0) {
//            ball.bounceRight();
//            leftWall.setFrame(1); // Glow pulse
//            collision = true;
//        }
//        else if (ball.collidesWith(rightWall, false)
//                || ball.getX() + ball.getWidth() > displayWidth) {
//            ball.bounceLeft();
//            rightWall.setFrame(1); // Glow pulse
//            collision = true;
//        }
//        if (collision) {
//            listener.handleEvent(EVENT_WALL_COLLISION); // Inform listener
//        }
    }

    /**
     * Check and act for collisions between the ball and the bricks
     */
    public void checkBrickCollisions() {
//        // Check and get the brick that the ball collided with
//        Brick brick = bricks.collidesWith(ball);
//        if (brick != null) { // Check the bounce accordign to the hit position
//            if (ball.getRefPixelY() < brick.getRefPixelY()) {
//                ball.bounceUp();
//            }
//            else {
//                ball.bounceDown();
//            }
//            if (ball.getRefPixelX() < brick.getX()) {
//                ball.bounceLeft();
//            }
//            else if (ball.getRefPixelX() > brick.getX() + brick.getWidth()) {
//                ball.bounceRight();
//            }
//
//            // If the brick explodes...
//            if (brick.getFrame() % 2 == 0) {
//                remove(brick);
//                ball.changeVelocityX(rnd.nextInt(2));
//                sparkManager.burst(brick.getRefPixelX(), brick.getRefPixelY());
//                listener.handleEvent(EVENT_BRICK_EXPLOSION);
//                vibrate(40);
//                LightManager.pulse(2);
//                if (bricks.isEmpty()) {
//                    changeLevel = true;
//                    listener.changeState(STATE_LEVEL);
//                    listener.handleEvent(EVENT_LEVEL_CHANGE);
//                }
//                else {
//                    shaker.shake(ball.getVelocityX(), ball.getVelocityY());
//                }
//            }
//            else {
//                listener.handleEvent(EVENT_BRICK_COLLISION);
//            }
//        }
    }

    /**
     * Handle pointer pressed
     */
    public void pointerPressed(int x, int y) {
//        movePlate(x);
//        if (menuButtonHit(x, y)) {
//            menuPressed = true;
//        }
    }

    /**
     * Handle pointer released
     */
    public void pointerReleased(int x, int y) {
//        stopMovement();
//        if (menuPressed && menuButtonHit(x, y)) {
//            rightButtonPressed();
//        }
//        else {
//            menuPressed = false;
//            if (aiming) {
//                aiming = false;
//            }
//        }
//        if (newGameButton.isVisible() && newGameButtonHit(x, y)) {
//            leftButtonPressed();
//        }
    }

    /**
     * Handle pointer dragged
     */
    public void pointerDragged(int x, int y) {
//        movePlate(x);
    }

    /**
     * Handle firing
     */
    public void fire() {
//        if (aiming) {
//            aiming = false;
//        }
    }

    /**
     * Handle left action key
     */
    public void leftButtonPressed() {
//        if (lives.isEmpty()) {
//            listener.changeState(STATE_LEVEL);
//            newGame();
//        }
    }

    /**
     * Handle right action key
     */
    public void rightButtonPressed() {
        listener.changeState(TypeBattleShips.STATE_MENU);
//        listener.handleEvent(EVENT_BUTTON_PRESSED);
    }

    /**
     * Set the plate moving towards given x coordinate
     */
    public void movePlate(int x) {
//        pointerPressed = true;
//        plate.setMoving(x);
//        pointerX = x;
    }

    /**
     * Moves plate towards set destination
     */
    public void movePlate() {
//        int distance = plate.getRefPixelX() - pointerX;
//        if (distance > 0 && plate.getLeftX() > 0
//                || distance < 0 && plate.getRightX() < displayWidth) {
//            distance *= 0.94;
//        }
//        plate.setRefPixelPosition(pointerX + distance, plate.getRefPixelY());
//        if (distance == 0) {
//            stopMovement();
//        }
    }

    /**
     * Moves plate one constant step to the left
     */
    public void movePlateLeft() {
//        if (plate.getLeftX() > 0) {
//            plate.moveLeft();
//        }
    }

    /**
     * Moves plate one constant step to the right
     */
    public void movePlateRight() {
//        if (plate.getRightX() < displayWidth) {
//            plate.moveRight();
//        }
    }

    public void stopMovement() {
//        pointerPressed = false;
//        plate.stop();
    }

    /**
     * Vibrate device
     * @param duration
     */
    public void vibrate(int duration) {
//        Display display = Display.getDisplay(Main.getInstance());
//        display.vibrate(duration);
    }

    private boolean menuButtonHit(int x, int y) {
        return containsPoint(x, y, menuButton);
    }

    private boolean newGameButtonHit(int x, int y) {
        return containsPoint(x, y, newGameButton);
    }

    /**
     * Checks whether a coordinate overlaps the bounding rectangle of a sprite
     */
    private boolean containsPoint(int x, int y, Sprite sprite) {
        return x >= sprite.getX() && x <= sprite.getX() + sprite.getWidth()
                && y >= sprite.getY();
    }

    /**
     * Starts a new game
     */
    public void newGame() {
//        levelNumber = 0;
//        initDashboard(LIVES);
//        nextLevel();
    }

    /**
     * Loads the next level
     */
    public void nextLevel() {
//        placeBall();
//        levelNumber++;
//        loadLevel(levelNumber);
    }

    /**
     * Loads up a level
     * @param number
     */
    public void loadLevel(int number) {
//        bricks.load(this, Levels.getLevel((number - 1) % Levels.LEVEL_COUNT + 1));
    }

    /**
     * Initializes the dashboard
     * @param balls Number of balls left
     */
    public void initDashboard(int balls) {
//        if (balls > 0) {
//            newGameButton.setVisible(false);
//        }
        insert(dashboard, 0);
        insert(menuButton, 0);
//        insert(newGameButton, 0);
//        while (lives.size() > 0) {
//            Sprite life = (Sprite) lives.lastElement();
//            lives.removeElement(life);
//            remove(life);
//        }
//        for (int i = 0; i < balls; i++) {
//            Sprite life = new Sprite(r.life);
//            life.setPosition(r.scale(12) + i * life.getWidth(), displayHeight - r.scale(14));
//            lives.addElement(life);
//            insert(life, 0);
//        }
//        if (lives.size() > 0) {
//            ((Sprite) (lives.elementAt(0))).setVisible(false);
//        }
    }

    /**
     * Serializes the game data
     * @return Game data as a byte array
     */
    public byte[] getSnapshot() {
        ByteArrayOutputStream bout = null;
//        try {
//            bout = new ByteArrayOutputStream();
//            DataOutputStream dout = new DataOutputStream(bout);
//            dout.writeInt(levelNumber);
//            dout.writeInt(lives.size());
//            dout.writeBoolean(aiming);
//            dout.writeInt(plate.getX());
//            dout.writeInt(plate.getY());
//            dout.writeInt(ball.getX());
//            dout.writeInt(ball.getY());
//            dout.writeInt(ball.getVelocityX());
//            dout.writeInt(ball.getVelocityY());
//            bricks.writeTo(dout);
//            return bout.toByteArray();
//        }
//        catch (IOException e) {
//        }
//        finally {
//            try {
//                if (bout != null) {
//                    bout.close();
//                }
//            }
//            catch (IOException e) {
//            }
//        }
        return new byte[0];
    }

    /**
     * Deserializes game data
     * @param record Game data in byte array
     * @return True, if loading was successful - otherwise false
     */
    public boolean load(byte[] record) {
        if (record == null) {
            return false;
        }
        try {
            DataInputStream din = new DataInputStream(new ByteArrayInputStream(record));
//            levelNumber = din.readInt();
//          initDashboard(din.readInt());
            initDashboard(1);
            aiming = din.readBoolean();
//            plate.setPosition(din.readInt(), din.readInt());
//            ball.setPosition(din.readInt(), din.readInt());
//            ball.setVelocityX(din.readInt());
//            ball.setVelocityY(din.readInt());
//            bricks.load(this, bricks.readFrom(din));
            return true;
        }
        catch (IOException e) {
        }
        return false;
    }

    /**
     * Moves the view inwards
     */
    public boolean slideIn() {
        int distance = cornerX - IN_X;
        distance *= 0.8;
        cornerX = IN_X + distance;
        boolean sliding = distance != 0;
        if (!sliding) {
//            listener.handleEvent(EVENT_LEVEL_STARTED);
        }
        return sliding;
    }

    /**
     * Moves the view outwards
     */
    public boolean slideOut() {
        int distance = cornerX - OUT_X;
        distance *= 0.8;
        cornerX = OUT_X + distance;
//        if (distance == 0 && changeLevel) {
//            changeLevel = false;
//            nextLevel();
//        }
        return distance != 0;
    }
}
