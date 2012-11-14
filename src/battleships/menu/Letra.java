/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.menu;

import battleships.game.Resources;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author edster
 */
public class Letra {
    
    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;
    public static final int D = 3;
    public static final int E = 4;
    public static final int F = 5;
    public static final int G = 6;
    public static final int H = 7;
    public static final int I = 8;
    public static final int J = 9;
    public static final int K = 10;
    public static final int L = 11;
    public static final int M = 12;
    public static final int N = 13;
    public static final int Enie = 14;
    public static final int O = 15;
    public static final int P = 16;
    public static final int Q = 17;
    public static final int R = 18;
    public static final int S = 19;
    public static final int T = 20;
    public static final int U = 21;
    public static final int V = 22;
    public static final int W = 23;
    public static final int X = 24;
    public static final int Y = 25;
    public static final int Z = 26;
    public static final int guion = 27;
    
    public static String obtenerLetra(int codigo){
        switch(codigo){
            case A:
                return "A";
            case B:
                return "B";
            case C:
                return "C";
            case D:
                return "D";
            case E:
                return "E";
            case F:
                return "F";
            case G:
                return "G";
            case H:
                return "H";
            case I:
                return "I";
            case J:
                return "J";
            case K:
                return "K";
            case L:
                return "L";
            case M:
                return "M";
            case N:
                return "N";
            case O:
                return "O";
            case P:
                return "P";
            case Q:
                return "Q";
            case R:
                return "R";
            case S:
                return "S";
            case T:
                return "T";
            case U:
                return "U";
            case V:
                return "V";
            case W:
                return "W";
            case X:
                return "X";
            case Y:
                return "Y";
            case Z:
                return "Z";
            case guion:
                return "_";
            case Enie:
                return "Ñ";
        }
        return "";
    }
}
    
    