/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships;

/**
 *
 * @author edster
 */
public class ArrayUtils {
    
    public static double getLast(double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array cannot be null");
        }
        return array[array.length - 1];
    }

    public static int getLast(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array cannot be null");
        }
        return array[array.length - 1];
    }

    public static Object getLast(Object[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array cannot be null");
        }
        return array[array.length - 1];
    }
    
//     public static int getMatchPosition(String[][] array, int columna, String buscar) {
//        int number = -1;
//        if (array == null) {
//            throw new IllegalArgumentException("array cannot be null");
//        }
//        for(int i = 0; i < array.length; i++)
//        {
//            if (array[i][columna].equals(buscar)) {
//                number = i;
//                break;
//            }
//        }
//        return number;
//    }
    
}
