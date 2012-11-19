/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game;

/**
 *
 * @author Administrador
 */




public class mpComunication extends Thread {
    
    private BtCommunication btc;
    private boolean datosListos;
    private int int0;
    private int int1;
    private int int2;
    private int int3;
    private int int4;
    private int int5;
    private int int6;
    private int int7;

    public boolean isDatosListos() {
        return datosListos;
    }

    public void setDatosListos(boolean datosListos) {
        this.datosListos = datosListos;
    }

    public int getInt0() {
        return int0;
    }

    public void setInt0(int int0) {
        this.int0 = int0;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }

    public int getInt3() {
        return int3;
    }

    public void setInt3(int int3) {
        this.int3 = int3;
    }

    public int getInt4() {
        return int4;
    }

    public void setInt4(int int4) {
        this.int4 = int4;
    }

    public int getInt5() {
        return int5;
    }

    public void setInt5(int int5) {
        this.int5 = int5;
    }

    public int getInt6() {
        return int6;
    }

    public void setInt6(int int6) {
        this.int6 = int6;
    }

    public int getInt7() {
        return int7;
    }

    public void setInt7(int int7) {
        this.int7 = int7;
    }
    
    int mysybc = 0;
    int enemysybc = 0;
     /*
        * char 0 = 1 ataque, 2 respuesta ataque
        * char 1 = sycn del 0 al 9, ventana
        * char 2 = x
        * char 3 = y 
        * char 4 = resultado, tocado undido / arma especial
        * char 5 = barco
        * char 6 = orientacion
        */
    
public mpComunication()
{
    btc = new BtCommunication();
}
    
public void run() 
    {
        leer();
    }

   public void leer()
{
    while(btc.connectionAlive())
        {
                   String datos = btc.readData();   
       if(!datos.equals("") && !datosListos)
       {
           char[] datosa = datos.toCharArray();
           if(datosa[0]!='\0')
           {
               int0 = Integer.parseInt(""+datosa[0]);
           }
           if(datosa[1]!='\0')
           {
           int1 = Integer.parseInt(""+datosa[1]);
           }
           if(datosa[2]!='\0')
           {
           int2 = Integer.parseInt(""+datosa[2]);
           }
           if(datosa[3]!='\0')
           {
           int3 = Integer.parseInt(""+datosa[3]);
           }
           if(datosa[4]!='\0')
           {
           int4 = Integer.parseInt(""+datosa[4]);
           }
           if(datosa[5]!='\0')
           {
           int5 = Integer.parseInt(""+datosa[5]);
           }
           if(datosa[6]!='\0')
           {
           int6 = Integer.parseInt(""+datosa[6]);
           }
           if(datosa[7]!='\0')
           {
           int7 = Integer.parseInt(""+datosa[7]);
           }
           datosListos = true;
         }
 
        }    
}
   
   public Shoot recuperarDatosDisparo()
   {
       Shoot s = new Shoot();
       s.setX(int2);
       s.setY(int3);
       s.setArma(int4);
       
       return s;
   }
   
   public void enviarDatosDisparo(Shoot s)
   {
       String ss = "1";
       mysybc++;
       ss = ss + mysybc;
       ss = ss + s.getX();
       ss = ss + s.getY();
       ss = ss + s.getArma();
       ss = ss + "0";
       ss = ss + "0";
       
       btc.writeMessage(ss); 
   }
   
   public void responderDatos(String ss)
   {
       btc.writeMessage(ss);
   }
   
   
   
   public void startServer()
   {
       btc.waitForClient();
   }
   
   public void startClient()
   {
       btc.connectToServer();
   }
   
}


