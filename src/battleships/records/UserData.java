/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.records;

import battleships.game.maps.Grid;
import battleships.game.ships.BattleShip;
import battleships.menu.Letra;
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
        
    //Estático
    public static final int PERFIL_A = 1;
    public static final int PERFIL_B = 2;
    public static final int PERFIL_C = 3;
    
    //player 1
    private int[] userName_1;
    private Grid[][] mapEnemy_single_1;
    private BattleShip[] shipEnemy_single_1;
    private Grid[][] mapUser_single_1;
    private BattleShip[] shipUser_single_1;
    private Grid[][] mapEnemy_campaign_1;
    private BattleShip[] shipEnemy_campaign_1;
    private Grid[][] mapUser_campaign_1;
    private BattleShip[] shipUser_campaign_1;
    private int dif_user_1;
    //player 2
    private int[] userName_2;
    private Grid[][] mapEnemy_single_2;
    private BattleShip[] shipEnemy_single_2;
    private Grid[][] mapUser_single_2;
    private BattleShip[] shipUser_single_2;
    private Grid[][] mapEnemy_campaign_2;
    private BattleShip[] shipEnemy_campaign_2;
    private Grid[][] mapUser_campaign_2;
    private BattleShip[] shipUser_campaign_2;
    private int dif_user_2;
    //player 3
    private int[] userName_3;
    private Grid[][] mapEnemy_single_3;
    private BattleShip[] shipEnemy_single_3;
    private Grid[][] mapUser_single_3;
    private BattleShip[] shipUser_single_3;
    private Grid[][] mapEnemy_campaign_3;
    private BattleShip[] shipEnemy_campaign_3;
    private Grid[][] mapUser_campaign_3;
    private BattleShip[] shipUser_campaign_3;
    private int dif_user_3;
    
    //general\
    private int usuarioActual;
    
    public UserData(){
        
    }
    
    public boolean save(){
        return true;
    }
    
    public boolean load(byte[] record){
         if (record == null) {
            return false;
        }
//        try {
//            DataInputStream din = new DataInputStream(new ByteArrayInputStream(record));
////            userName_1 = din.readUTF();
////            userName_2 = din.readUTF();
////            userName_3 = din.readUTF();            
////          initDashboard(din.readInt());
////            plate.setPosition(din.readInt(), din.readInt());
////            ball.setPosition(din.readInt(), din.readInt());
////            ball.setVelocityX(din.readInt());
////            ball.setVelocityY(din.readInt());
////            bricks.load(this, bricks.readFrom(din));
//            return true;
//        }
////        catch (IOException e) {
////        }
        return false;
    }
    public byte[] getSnapshot(){
        
        ByteArrayOutputStream bout = null;
        try {
            bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
//            dout.writeChars(userName_1);            
//            dout.writeChars(userName_2);            
//            dout.writeChars(userName_3);
            
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
//        catch (IOException e) {
//        }
        finally {
            try {
                if (bout != null) {
                    bout.close();
                }
            }
            catch (IOException e) {
            }
        }
//        return new byte[0];
    }
    
    public int getDificultad(int userNumber)
    {
        int ret = 0;
        switch(userNumber)
        {
            case 1:
            {
             ret = dif_user_1;
            break;
            }
            case 2:
            {
            ret = dif_user_2;
            break;
            }
            case 3:
            {
                ret = dif_user_3;
                break;
            }
        }     
        return ret;
    }
    public boolean existeNameUser1(){
        boolean done = true;
        if (userName_1[0] == Letra.guion){
            done = false;
        }
        return done;
    }
    
    public boolean existeNameUser2(){
        boolean done = true;
        if (userName_2[0] == Letra.guion){
            done = false;
        }
        return done;
    }
    
    public boolean existeNameUser3(){
        boolean done = true;
        if (userName_3[0] == Letra.guion){
            done = false;
        }
        return done;
    }
    
    public void reset(){
        userName_1 = new int[5];        
        userName_2 = new int[5];
        userName_3 = new int[5];
        
        for(int i = 0; i< 5; i++){
            userName_1[i] = Letra.guion;
            userName_2[i] = Letra.guion;
            userName_3[i] = Letra.guion;
        }
    }

    public int getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(int usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    public int[] getNombreUsuarioActual(){
        switch(usuarioActual){
            case PERFIL_A:
                return userName_1;
            case PERFIL_B:
                return userName_2;
            case PERFIL_C:
                return userName_3;
        }
        return null;
    }
    
    public int[] getNombreUsuario(int usuario){
        switch(usuario){
            case PERFIL_A:
                return userName_1;
            case PERFIL_B:
                return userName_2;
            case PERFIL_C:
                return userName_3;
        }
        return null;
    }
    
    public void setNombreUsuario(int[] nombre){
        switch(usuarioActual){
            case PERFIL_A:
                userName_1 = nombre;
                break;
            case PERFIL_B:
                userName_2 = nombre;
                break;  
            case PERFIL_C:
                userName_3 = nombre;
                break;    
        }
    }       
}
