/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.game;

import battleships.ImageHelper;
import battleships.menu.Letra;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
/**
 *
 * @author edster
 */
public class Resources {
    private static boolean initialized = false;
    public Image background;    
    public Image signOn;
    public Image water;
    public Image portaaviones;
    public Image acorazado;
    public Image destructor;
    public Image submarino;
    public Image espia;
    public Image mira;
    public Image fondo;
    public Image girar;
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
        water = null;
        portaaviones = null;
        acorazado = null;
        destructor = null;
        submarino = null;
        espia = null;
        mira = null;
        fondo = null;
        girar = null;
        fondo_letra_izq = null;
        fondo_letra_der = null;
        fondo_letra_cent = null;
                    
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
            fondo = ih.loadImage("/fondo.png",scaling);
            water = ih.loadImage("/water.png", scaling);
            portaaviones = ih.loadImage("/PortaAviones.png", scaling);
            acorazado = ih.loadImage("/Acorazado.png", scaling);
            destructor = ih.loadImage("/Destructor.png", scaling);
            submarino = ih.loadImage("/Submarino.png", scaling);
            espia = ih.loadImage("/Espia.png", scaling);
            mira = ih.loadImage("/mira.png", scaling);
            girar = ih.loadImage("/girar.png",scaling);
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
    
    public Image rotate90(Image image){        
        ImageHelper ih = ImageHelper.getInstance();
        return ih.transformImage(image, Sprite.TRANS_ROT270);
    }
}
