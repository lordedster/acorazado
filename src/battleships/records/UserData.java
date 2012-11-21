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
    public static final int PERFIL_A = 0;
    public static final int PERFIL_B = 1;
    public static final int PERFIL_C = 2;
    
    //player 1
    private int[] userName_1;
    private boolean guardado_user_1;
    private int[][] mapEnemy_single_1;
    private int[][] shipEnemy_single_1;
    private int[][] mapUser_single_1;
    private int[][] shipUser_single_1;
    private Grid[][] mapEnemy_campaign_1;
    private BattleShip[] shipEnemy_campaign_1;
    private Grid[][] mapUser_campaign_1;
    private BattleShip[] shipUser_campaign_1;
    private int dif_user_1;
    //player 2
    private int[] userName_2;
    private boolean guardado_user_2;
    private int[][] mapEnemy_single_2;
    private int[][] shipEnemy_single_2;
    private int[][] mapUser_single_2;
    private int[][] shipUser_single_2;
    private Grid[][] mapEnemy_campaign_2;
    private BattleShip[] shipEnemy_campaign_2;
    private Grid[][] mapUser_campaign_2;
    private BattleShip[] shipUser_campaign_2;
    private int dif_user_2;
    //player 3
    private int[] userName_3;    
    private boolean guardado_user_3;
    private int[][] mapEnemy_single_3;
    private int[][] shipEnemy_single_3;
    private int[][] mapUser_single_3;
    private int[][] shipUser_single_3;
    private Grid[][] mapEnemy_campaign_3;
    private BattleShip[] shipEnemy_campaign_3;
    private Grid[][] mapUser_campaign_3;
    private BattleShip[] shipUser_campaign_3;
    private int dif_user_3;
    
    //general\
    private int usuarioActual;
    
    public UserData(){
        reset();        
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
            for(int i = 0; i < userName_1.length; i++){       
                userName_1[i] = din.readInt();
            }
            for(int i = 0; i < userName_2.length; i++){       
                userName_2[i] = din.readInt();
            }
            for(int i = 0; i < userName_3.length; i++){       
                userName_3[i] = din.readInt();
            } 
            dif_user_1 = din.readInt();
            dif_user_2 = din.readInt();
            dif_user_3 = din.readInt();
            guardado_user_1 = din.readBoolean();
            guardado_user_2 = din.readBoolean();
            guardado_user_3 = din.readBoolean();
            
            /* mapas y barcos enemigos singleplayer */
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    mapEnemy_single_1[i][j] = din.readInt();
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    mapEnemy_single_2[i][j] = din.readInt();
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    mapEnemy_single_3[i][j] = din.readInt();
                }
            }
            
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    shipEnemy_single_1[i][j] = din.readInt();
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    shipEnemy_single_2[i][j] = din.readInt();
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    shipEnemy_single_3[i][j] = din.readInt();
                }
            } 
            
            /* mapas y barcos amigos singleplayer */
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    mapUser_single_1[i][j] = din.readInt();
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    mapUser_single_2[i][j] = din.readInt();
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    mapUser_single_3[i][j] = din.readInt();
                }
            }
            
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    shipUser_single_1[i][j] = din.readInt();
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    shipUser_single_2[i][j] = din.readInt();
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    shipUser_single_3[i][j] = din.readInt();
                }
            }
////          initDashboard(din.readInt());
////            plate.setPosition(din.readInt(), din.readInt());
////            ball.setPosition(din.readInt(), din.readInt());
////            ball.setVelocityX(din.readInt());
////            ball.setVelocityY(din.readInt());
////            bricks.load(this, bricks.readFrom(din));
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
            for(int i = 0; i < userName_1.length; i++){                
                dout.writeInt(userName_1[i]);     
            }
            for(int i = 0; i < userName_2.length; i++){
                 dout.writeInt(userName_2[i]);     
            }
            for(int i = 0; i < userName_3.length; i++){
                 dout.writeInt(userName_3[i]);     
            }  
            dout.writeInt(dif_user_1);     
            dout.writeInt(dif_user_2);     
            dout.writeInt(dif_user_3);   
            dout.writeBoolean(guardado_user_1);
            dout.writeBoolean(guardado_user_2);
            dout.writeBoolean(guardado_user_3);
            
            /* mapas y barcos enemigos single player */
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    dout.writeInt(mapEnemy_single_1[i][j]);
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    dout.writeInt(mapEnemy_single_2[i][j]);
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    dout.writeInt(mapEnemy_single_3[i][j]);
                }
            }
            
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    dout.writeInt(shipEnemy_single_1[i][j]);
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    dout.writeInt(shipEnemy_single_2[i][j]);
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    dout.writeInt(shipEnemy_single_3[i][j]);
                }
            }
            
            /* mapas y barcos amigos singleplayer */
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    dout.writeInt(mapUser_single_1[i][j]);
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    dout.writeInt(mapUser_single_2[i][j]);
                }
            }
            
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    dout.writeInt(mapUser_single_3[i][j]);
                }
            }
            
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    dout.writeInt(shipUser_single_1[i][j]);
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    dout.writeInt(shipUser_single_2[i][j]);
                }
            }
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    dout.writeInt(shipUser_single_3[i][j]);
                }
            }
            
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
    
    public int getDificultad(int userNumber)
    {
        int ret = 0;
        switch(userNumber)
        {
            case 1:            
                ret = dif_user_1;
                break;
            case 2:
                ret = dif_user_2;
                break;
            case 3:
                ret = dif_user_3;
                break;
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
        dif_user_1 = 0;
        dif_user_2 = 0;
        dif_user_3 = 0;
        guardado_user_1 = false;
        guardado_user_2 = false;
        guardado_user_3 = false;
        
        /*mapas y barcos enemigos singleplayer */
        mapEnemy_single_1 = new int[10][10];
        mapEnemy_single_2 = new int[10][10];
        mapEnemy_single_3 = new int[10][10];
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapEnemy_single_1[i][j] = -1;
                mapEnemy_single_2[i][j] = -1;
                mapEnemy_single_3[i][j] = -1;
            }            
        }        
        shipEnemy_single_1 = new int[5][5];        
        shipEnemy_single_2 = new int[5][5];        
        shipEnemy_single_3 = new int[5][5];
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                shipEnemy_single_1[i][j] = -1;
                shipEnemy_single_2[i][j] = -1;
                shipEnemy_single_3[i][j] = -1;
            }            
        }
        
        /* mapas y barcos amigos single player */
        
        mapUser_single_1 = new int[10][10];
        mapUser_single_2 = new int[10][10];
        mapUser_single_3 = new int[10][10];
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapUser_single_1[i][j] = -1;
                mapUser_single_2[i][j] = -1;
                mapUser_single_3[i][j] = -1;
            }            
        }        
        shipUser_single_1 = new int[5][5];        
        shipUser_single_2 = new int[5][5];        
        shipUser_single_3 = new int[5][5];
        
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                shipUser_single_1[i][j] = -1;
                shipUser_single_2[i][j] = -1;
                shipUser_single_3[i][j] = -1;
            }            
        }
    }

    public int getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(int usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
  
    
    public int getDificultadUsuarioActual(){
        switch(usuarioActual){
            case PERFIL_A:
                return dif_user_1;
            case PERFIL_B:
                return dif_user_2;
            case PERFIL_C:
                return dif_user_3;
        }
        return 0;
    }
    
    public String getNombreUsuario(int usuario){
        switch(usuario){
            case PERFIL_A:
                return tranformarNombre(userName_1);
            case PERFIL_B:
                return tranformarNombre(userName_2);
            case PERFIL_C:
                return tranformarNombre(userName_3);
        }
        return null;
    }
        public String getNombreUsuarioActual(){
        switch(usuarioActual){
            case PERFIL_A:
                return tranformarNombre(userName_1);
            case PERFIL_B:
                return tranformarNombre(userName_2);
            case PERFIL_C:
                return tranformarNombre(userName_3);
        }
        return null;
    }
        
    private String tranformarNombre(int[] nombre){
        String n = "";
        for(int i = 0; i < nombre.length; i++){
            if (nombre[i] != Letra.guion){
                n = n + Letra.obtenerLetra(nombre[i]);
            }
        }        
        return n;
    }
    
    public void setNombreUsuarioActual(int[] nombre){
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
    
    public void setDificultadUsuarioActual(int dif){
        switch(usuarioActual){
            case PERFIL_A:
                dif_user_1 = dif;
                break;
            case PERFIL_B:
                dif_user_2 = dif;
                break;  
            case PERFIL_C:
                dif_user_3 = dif;
                break;    
        }
    }  
    
    public void eliminarUsuario(int usuario){
         switch(usuario){
            case PERFIL_A:
                for(int i = 0; i< 5; i++){
                    userName_1[i] = Letra.guion;
                }
                break;
            case PERFIL_B:
                for(int i = 0; i< 5; i++){
                    userName_2[i] = Letra.guion;
                }
                break;  
            case PERFIL_C:
                for(int i = 0; i< 5; i++){
                    userName_3[i] = Letra.guion;
                }
                break;    
        }
    }
    
    public boolean getGuardadoUsuarioActual(){
        switch(usuarioActual){
            case PERFIL_A:
                return guardado_user_1;
            case PERFIL_B:
                return guardado_user_2;
            case PERFIL_C:
                return guardado_user_3;
        }
        return false;
    }
    
    public void setMapaEnemySigle(int[][] map){
        switch(usuarioActual){
            case UserData.PERFIL_A:
                mapEnemy_single_1 = map;
                break;
            case UserData.PERFIL_B:
                mapEnemy_single_2 = map;
                break;
            case UserData.PERFIL_C:                
                mapEnemy_single_3 = map;
                break;
        }
    }
    
    
    public void setMapaFriendSigle(int[][] map){
        switch(usuarioActual){
            case UserData.PERFIL_A:
                mapUser_single_1 = map;
                break;
            case UserData.PERFIL_B:
                mapUser_single_2 = map;
                break;
            case UserData.PERFIL_C:                
                mapUser_single_3 = map;
                break;
        }
    }
    
    public void setShipsEnemySigle(int[][] ships){
        switch(usuarioActual){
            case UserData.PERFIL_A:
                shipEnemy_single_1 = ships;
                break;
            case UserData.PERFIL_B:
                shipEnemy_single_2 = ships;
                break;
            case UserData.PERFIL_C:                
                shipEnemy_single_3 = ships;
                break;
        }
    }
    
    
     public void setShipsFriendSigle(int[][] ships){
        switch(usuarioActual){
            case UserData.PERFIL_A:
                shipUser_single_1 = ships;
                break;
            case UserData.PERFIL_B:
                shipUser_single_2 = ships;
                break;
            case UserData.PERFIL_C:                
                shipUser_single_3 = ships;
                break;
        }
    }
     
     public void setGuardadoSingle(boolean b){
        switch(usuarioActual){
            case UserData.PERFIL_A:
                guardado_user_1 = b;
                break;
            case UserData.PERFIL_B:
                guardado_user_2 = b;
                break;
            case UserData.PERFIL_C:                
                guardado_user_3 = b;
                break;
        }
     }
     
     public int[][] getMapaEnemySingle(){         
        switch(usuarioActual){
            case UserData.PERFIL_A:
                return mapEnemy_single_1;
            case UserData.PERFIL_B:
                return mapEnemy_single_2;
            case UserData.PERFIL_C:     
                return mapEnemy_single_3;
        }
        return null;
     }
     
     
     public int[][] getMapaFriendSingle(){         
        switch(usuarioActual){
            case UserData.PERFIL_A:
                return mapUser_single_1;
            case UserData.PERFIL_B:
                return mapUser_single_2;
            case UserData.PERFIL_C:     
                return mapUser_single_3;
        }
        return null;
     }
     
     public int[][] getBarcosEnemySingle(){                 
        switch(usuarioActual){
            case UserData.PERFIL_A:
                return shipEnemy_single_1;
            case UserData.PERFIL_B:
                return shipEnemy_single_2;
            case UserData.PERFIL_C:     
                return shipEnemy_single_3;
        }
        return null;
     }
     
     public int[][] getBarcosFriendSingle(){                 
        switch(usuarioActual){
            case UserData.PERFIL_A:
                return shipUser_single_1;
            case UserData.PERFIL_B:
                return shipUser_single_2;
            case UserData.PERFIL_C:     
                return shipUser_single_3;
        }
        return null;
     }     
    //public 
}
