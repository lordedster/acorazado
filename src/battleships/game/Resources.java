/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game;

import battleships.ImageHelper;
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
    public void loadResources() {
        if (!initialized) {
            ImageHelper ih = ImageHelper.getInstance();
            background = ih.loadImage("/background.png", scaling);
            signOn = ih.loadImage("/acorazados_on.png", scaling);
            signOff = ih.loadImage("/acorazados_off.png", scaling);
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
            fondo = ih.loadImage("/fondo.png",scaling);
            girar = ih.loadImage("/girar.png",scaling);
            buttonBarcos = ih.loadImage("/barcos.png",scaling);
            
            
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
}
