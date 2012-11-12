/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.records;

import battleships.game.maps.Grid;
import battleships.game.ships.BattleShip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author edster
 */
public class UserData {
        //player 1
    private String userName_1;
    private Grid[][] mapEnemy_single_1;
    private BattleShip[] shipEnemy_single_1;
    private Grid[][] mapUser_single_1;
    private BattleShip[] shipUser_single_1;
    private Grid[][] mapEnemy_campaign_1;
    private BattleShip[] shipEnemy_campaign_1;
    private Grid[][] mapUser_campaign_1;
    private BattleShip[] shipUser_campaign_1; 
    //player 2
    private String userName_2;
    private Grid[][] mapEnemy_single_2;
    private BattleShip[] shipEnemy_single_2;
    private Grid[][] mapUser_single_2;
    private BattleShip[] shipUser_single_2;
    private Grid[][] mapEnemy_campaign_2;
    private BattleShip[] shipEnemy_campaign_2;
    private Grid[][] mapUser_campaign_2;
    private BattleShip[] shipUser_campaign_2;   
    //player 3
    private String userName_3;
    private Grid[][] mapEnemy_single_3;
    private BattleShip[] shipEnemy_single_3;
    private Grid[][] mapUser_single_3;
    private BattleShip[] shipUser_single_3;
    private Grid[][] mapEnemy_campaign_3;
    private BattleShip[] shipEnemy_campaign_3;
    private Grid[][] mapUser_campaign_3;
    private BattleShip[] shipUser_campaign_3; 
    
    
    public UserData(){
        
    }
    
    public boolean save(){
        return true;
    }
    
    public boolean load(byte[] record){
         if (record == null) {
            return false;
        }
        try {
            DataInputStream din = new DataInputStream(new ByteArrayInputStream(record));
            userName_1 = din.readUTF();
            userName_2 = din.readUTF();
            userName_3 = din.readUTF();            
//          initDashboard(din.readInt());
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
    public byte[] getSnapshot(){
        
        ByteArrayOutputStream bout = null;
        try {
            bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
            dout.writeChars(userName_1);            
            dout.writeChars(userName_2);            
            dout.writeChars(userName_3);
            
//            dout.writeInt(lives.size());
//            dout.writeBoolean(aiming);
//            dout.writeInt(plate.getX());
//            dout.writeInt(plate.getY());
//            dout.writeInt(ball.getX());
//            dout.writeInt(ball.getY());
//            dout.writeInt(ball.getVelocityX());
//            dout.writeInt(ball.getVelocityY());
//            bricks.writeTo(dout);
            return bout.toByteArray();
        }
        catch (IOException e) {
        }
        finally {
            try {
                if (bout != null) {
                    bout.close();
                }
            }
            catch (IOException e) {
            }
        }
        return new byte[0];
    }
    
    public String getNameUser1(){
        return userName_1;
    }
    public String getNameUser2(){
        return userName_2;
    }
    public String getNameUser3(){
        return userName_3;
    }
    public void reset(){
        userName_1 = "PE";
        userName_2 = "";
        userName_3 = "Andresita Martinez";
    }
}
