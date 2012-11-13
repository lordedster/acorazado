/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game;

import battleships.ImageHelper;
import battleships.menu.Letra;
import javax.microedition.lcdui.Image;
/**
 *
 * @author edster
 */
public class Resources {
    private static boolean initialized = false;
    public Image background;    
    public Image signOn;
    public Image signOff;
    public Image water;
    public Image dashboard;
    public Image menuButton;
    public Image portaaviones;
    public Image portaaviones_hor;
    public Image acorazado;
    public Image acorazado_hor;
    public Image destructor;
    public Image destructor_hor;
    public Image submarino;
    public Image submarino_hor;
    public Image espia;
    public Image espia_hor;
    public Image automatico;
    public Image button_ok;
    public Image button_cancel;
    public Image mira;
    public Image fondo;
    public Image girar;
    public Image buttonBarcos;
    public Image vacio;
    public Image fondo_letra_izq;
    public Image fondo_letra_der;
    public Image fondo_letra_cent;
    
    public Image s_portaaviones_0;
    public Image s_portaaviones_1;
    public Image s_portaaviones_2;
    public Image s_portaaviones_3;
    public Image s_portaaviones_4;
    public Image s_acorazado_0;
    public Image s_acorazado_1;
    public Image s_acorazado_2;
    public Image s_acorazado_3;
    public Image s_submarino_0;
    public Image s_submarino_1;
    public Image s_submarino_2;
    public Image s_destructor_0;
    public Image s_destructor_1;
    public Image s_destructor_2;
    public Image s_espia_0;
    public Image s_espia_1;
    
    public Image s_portaaviones_0_ver;
    public Image s_portaaviones_1_ver;
    public Image s_portaaviones_2_ver;
    public Image s_portaaviones_3_ver;
    public Image s_portaaviones_4_ver;
    public Image s_acorazado_0_ver;
    public Image s_acorazado_1_ver;
    public Image s_acorazado_2_ver;
    public Image s_acorazado_3_ver;
    public Image s_submarino_0_ver;
    public Image s_submarino_1_ver;
    public Image s_submarino_2_ver;
    public Image s_destructor_0_ver;
    public Image s_destructor_1_ver;
    public Image s_destructor_2_ver;
    public Image s_espia_0_ver;
    public Image s_espia_1_ver;
    
     public Image letra_A;
     public Image letra_B;
     public Image letra_C;
     public Image letra_D;
     public Image letra_E;
     public Image letra_F;
     public Image letra_G;
     public Image letra_H;
     public Image letra_I;
     public Image letra_J;
     public Image letra_K;
     public Image letra_L;
     public Image letra_M;
     public Image letra_N;
     public Image letra_Enie;
     public Image letra_O;
     public Image letra_P;
     public Image letra_Q;
     public Image letra_R;
     public Image letra_S;
     public Image letra_T;
     public Image letra_U;
     public Image letra_V;
     public Image letra_W;
     public Image letra_X;
     public Image letra_Y;
     public Image letra_Z;
    
    /** 
     * Recursos de AUDIO
     */
    
    public final String SAMPLE_BUTTON = "/button.mp3";    
    public final String SAMPLE_ELECTRICITY = "/electricity.mp3";
    public final String SAMPLE_TRANSITION = "/transition.mp3";
    public final String SAMPLE_BONUS = "/bonus.mp3";
    
    public float scaling = 1.0f;
    
    public Resources(float scaling) {
        this.scaling = scaling;
        loadResources();
    }
    
        /**
     * Free resources
     */
    public void freeResources() {
        initialized = false;
        background = null;
        signOn = null;
        signOff = null;
        water = null;
        dashboard = null;
        menuButton = null;
        portaaviones = null;
        portaaviones_hor = null;
        acorazado = null;
        acorazado_hor = null;
        destructor = null;
        destructor_hor = null;
        submarino = null;
        submarino_hor = null;
        espia = null;
        espia_hor = null;
        automatico = null;
        button_cancel = null;
        button_ok = null;
        mira = null;
        fondo = null;
        girar = null;
        buttonBarcos = null;
        vacio = null;
        fondo_letra_izq = null;
        fondo_letra_der = null;
        fondo_letra_cent = null;
       
        letra_A = null;
        letra_B = null;
        letra_C = null;
        letra_D = null;
        letra_E = null;
        letra_F = null;
        letra_G = null;
        letra_H = null;
        letra_I = null;
        letra_J = null;
        letra_K = null;
        letra_L = null;
        letra_M = null;
        letra_M = null;
        letra_Enie = null;
        letra_O = null;
        letra_P = null;
        letra_Q = null;
        letra_R = null;
        letra_S = null;
        letra_T = null;
        letra_U = null;
        letra_V = null;
        letra_W = null;
        letra_X = null;
        letra_Y = null;
        letra_Z = null;
        
        s_portaaviones_0 = null;
        s_portaaviones_1 = null;
        s_portaaviones_2 = null;
        s_portaaviones_3 = null;
        s_portaaviones_4 = null;
        s_acorazado_0 = null;
        s_acorazado_1 = null;
        s_acorazado_2 = null;
        s_acorazado_3 = null;
        s_submarino_0 = null;
        s_submarino_1 = null;
        s_submarino_2 = null;
        
        s_portaaviones_0_ver = null;
        s_portaaviones_1_ver = null;
        s_portaaviones_2_ver = null;
        s_portaaviones_3_ver = null;
        s_portaaviones_4_ver = null;
        s_acorazado_0_ver = null;
        s_acorazado_1_ver = null;
        s_acorazado_2_ver = null;
        s_acorazado_3_ver = null;
        s_submarino_0_ver = null;
        s_submarino_1_ver = null;
        s_submarino_2_ver = null;
        s_destructor_0_ver = null;
        s_destructor_1_ver = null;
        s_destructor_2_ver = null;
        s_espia_0_ver = null;
        s_espia_1_ver = null;
        s_destructor_0 = null;
        s_destructor_1 = null;
        s_destructor_2 = null;
        s_espia_0 = null;
        s_espia_1 = null;
        
        
    }
    
    /**
     * Load resources
     */    
    private void loadLetras(ImageHelper ih) {
        letra_A = ih.loadImage("/letras/A.png",scaling);
        letra_B = ih.loadImage("/letras/B.png",scaling);
        letra_C = ih.loadImage("/letras/C.png",scaling);
        letra_D = ih.loadImage("/letras/D.png",scaling);
        letra_E = ih.loadImage("/letras/E.png",scaling);
        letra_F = ih.loadImage("/letras/F.png",scaling);
        letra_G = ih.loadImage("/letras/G.png",scaling);
        letra_H = ih.loadImage("/letras/H.png",scaling);
        letra_I = ih.loadImage("/letras/I.png",scaling);
        letra_J = ih.loadImage("/letras/J.png",scaling);
        letra_K = ih.loadImage("/letras/K.png",scaling);
        letra_L = ih.loadImage("/letras/L.png",scaling);
        letra_M = ih.loadImage("/letras/M.png",scaling);
        letra_N = ih.loadImage("/letras/N.png",scaling);
        letra_Enie = ih.loadImage("/letras/Enie.png",scaling);
        letra_O = ih.loadImage("/letras/O.png",scaling);
        letra_P = ih.loadImage("/letras/P.png",scaling);
        letra_Q = ih.loadImage("/letras/Q.png",scaling);
        letra_R = ih.loadImage("/letras/R.png",scaling);
        letra_S = ih.loadImage("/letras/S.png",scaling);
        letra_T = ih.loadImage("/letras/T.png",scaling);
        letra_U = ih.loadImage("/letras/U.png",scaling);
        letra_V = ih.loadImage("/letras/V.png",scaling);
        letra_W = ih.loadImage("/letras/W.png",scaling);
        letra_X = ih.loadImage("/letras/X.png",scaling);
        letra_Y = ih.loadImage("/letras/Y.png",scaling);
        letra_Z = ih.loadImage("/letras/Z.png",scaling);
    }
    
    public void loadResources() {
        if (!initialized) {
            ImageHelper ih = ImageHelper.getInstance(); 
            background = ih.loadImage("/background.png", scaling);
            vacio = ih.loadImage("/vacio.png",scaling);
            signOn = ih.loadImage("/acorazados_on.png", scaling);
            signOff = ih.loadImage("/acorazados_off.png", scaling);
            fondo = ih.loadImage("/fondo.png",scaling);
            loadLetras(ih);           
            water = ih.loadImage("/water.png", scaling);
            dashboard = ih.loadImage("/dashboard.png",scaling);
            menuButton = ih.loadImage("/menu_button.png", scaling);
            portaaviones = ih.loadImage("/PortaAviones.png", scaling);
            portaaviones_hor = ih.loadImage("/PortaAviones-hor.png", scaling);
            acorazado = ih.loadImage("/Acorazado.png", scaling);
            acorazado_hor = ih.loadImage("/Acorazado-hor.png", scaling);
            destructor = ih.loadImage("/Destructor.png", scaling);
            destructor_hor = ih.loadImage("/Destructor-hor.png", scaling);
            submarino = ih.loadImage("/Submarino.png", scaling);
            submarino_hor = ih.loadImage("/Submarino-hor.png", scaling);
            espia = ih.loadImage("/Espia.png", scaling);
            espia_hor = ih.loadImage("/Espia-hor.png", scaling);
            automatico = ih.loadImage("/automatico.png", scaling);
            button_ok = ih.loadImage("/button_ok.png", scaling);
            button_cancel = ih.loadImage("/button_cancel.png", scaling);
            mira = ih.loadImage("/mira.png", scaling);
            girar = ih.loadImage("/girar.png",scaling);
            buttonBarcos = ih.loadImage("/barcos.png",scaling);
            fondo_letra_izq = ih.loadImage("/fondo_letra_izq.png",scaling);
            fondo_letra_der = ih.loadImage("/fondo_letra_der.png",scaling);
            fondo_letra_cent = ih.loadImage("/fondo_letra_cent.png",scaling);
            
            
            s_portaaviones_0 = ih.loadImage("/barco_part/port0.png",scaling);
            s_portaaviones_1 = ih.loadImage("/barco_part/port1.png",scaling);
            s_portaaviones_2 = ih.loadImage("/barco_part/port2.png",scaling);
            s_portaaviones_3 = ih.loadImage("/barco_part/port3.png",scaling);
            s_portaaviones_4 = ih.loadImage("/barco_part/port4.png",scaling);
            s_acorazado_0 = ih.loadImage("/barco_part/aco0.png",scaling);
            s_acorazado_1 = ih.loadImage("/barco_part/aco1.png",scaling);
            s_acorazado_2 = ih.loadImage("/barco_part/aco2.png",scaling);
            s_acorazado_3 = ih.loadImage("/barco_part/aco3.png",scaling);
            s_submarino_0 = ih.loadImage("/barco_part/sub0.png",scaling);
            s_submarino_1 = ih.loadImage("/barco_part/sub1.png",scaling);
            s_submarino_2 = ih.loadImage("/barco_part/sub2.png",scaling);
            s_destructor_0 = ih.loadImage("/barco_part/des0.png",scaling);
            s_destructor_1 = ih.loadImage("/barco_part/des1.png",scaling);
            s_destructor_2 = ih.loadImage("/barco_part/des2.png",scaling);
            s_espia_0 = ih.loadImage("/barco_part/espia0.png",scaling);
            s_espia_1 = ih.loadImage("/barco_part/espia1.png",scaling);
            
            s_portaaviones_0_ver = ih.loadImage("/barco_part/port0_ver.png",scaling);
            s_portaaviones_1_ver = ih.loadImage("/barco_part/port1_ver.png",scaling);
            s_portaaviones_2_ver = ih.loadImage("/barco_part/port2_ver.png",scaling);
            s_portaaviones_3_ver = ih.loadImage("/barco_part/port3_ver.png",scaling);
            s_portaaviones_4_ver = ih.loadImage("/barco_part/port4_ver.png",scaling);
            s_acorazado_0_ver = ih.loadImage("/barco_part/aco0_ver.png",scaling);
            s_acorazado_1_ver = ih.loadImage("/barco_part/aco1_ver.png",scaling);
            s_acorazado_2_ver = ih.loadImage("/barco_part/aco2_ver.png",scaling);
            s_acorazado_3_ver = ih.loadImage("/barco_part/aco3_ver.png",scaling);
            s_submarino_0_ver = ih.loadImage("/barco_part/sub0_ver.png",scaling);
            s_submarino_1_ver = ih.loadImage("/barco_part/sub1_ver.png",scaling);
            s_submarino_2_ver = ih.loadImage("/barco_part/sub2_ver.png",scaling);
            s_destructor_0_ver = ih.loadImage("/barco_part/des0_ver.png",scaling);
            s_destructor_1_ver = ih.loadImage("/barco_part/des1_ver.png",scaling);
            s_destructor_2_ver = ih.loadImage("/barco_part/des2_ver.png",scaling);
            s_espia_0_ver = ih.loadImage("/barco_part/espia0_ver.png",scaling);
            s_espia_1_ver = ih.loadImage("/barco_part/espia1_ver.png",scaling);
            
            initialized = true;
        }
    }
    
   /**
   * Scale a value and return an integer using symmetrical rounding
   */
    public int scale(double value) {
        double result = scaling * value;
        return (result < 0) ? (int) (result - 0.5) : (int) (result + 0.5);
    }   
    
    public Image getLetra(int codigo){
        switch(codigo){
            case Letra.A:
                return letra_A;
            case Letra.B:
                return letra_B;
            case Letra.C:
                return letra_C;
            case Letra.D:
                return letra_D;
            case Letra.E:
                return letra_E;
            case Letra.F:
                return letra_F;
            case Letra.G:
                return letra_G;
            case Letra.H:
                return letra_H;
            case Letra.I:
                return letra_I;
            case Letra.J:
                return letra_J;
            case Letra.K:
                return letra_K;
            case Letra.L:
                return letra_L;
            case Letra.M:
                return letra_M;
            case Letra.N:
                return letra_N;
            case Letra.Enie:
                return letra_Enie;
            case Letra.O:
                return letra_O;
            case Letra.P:
                return letra_P;
            case Letra.Q:
                return letra_Q;
            case Letra.R:
                return letra_R;
            case Letra.S:
                return letra_S;
            case Letra.T:
                return letra_T;
            case Letra.U:
                return letra_U;
            case Letra.V:
                return letra_V;
            case Letra.W:
                return letra_W;
            case Letra.X:
                return letra_X;
            case Letra.Y:
                return letra_Y;
            case Letra.Z:
                return letra_Z;
        }
        return null;
    }
     
}
