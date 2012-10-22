/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation.
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 * See LICENSE.TXT for license information.
 */

package battleships.effects;

import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

public class ElectricArc {

    class Point {

        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void paint(Graphics g, int x, int y, int amplitude, int length) {
        Vector arc = generateArc(x, y, amplitude, length);
        ElectricArc.Point start = (ElectricArc.Point) arc.elementAt(0);
        ElectricArc.Point end;
        for (int i = 1; i < arc.size(); i++) {
            end = (ElectricArc.Point) arc.elementAt(i);
            g.drawLine(start.x, start.y, end.x, end.y);
            start = end;
        }
    }

    public Vector generateArc(int x, int y, int amplitude, int lenght) {
        Vector arc = new Vector();
        arc.addElement(new ElectricArc.Point(x, y));
        Random rnd = new Random();
        int points = (int) (lenght / 2 + rnd.nextInt((int) (lenght / 2)));
        int dirX = rnd.nextInt(3) - 1;
        int dirY = rnd.nextInt(3) - 1;
        for (int i = 1; i < points; i++) {
            int signX = (rnd.nextInt(2) == 0) ? -1 : 1;
            int signY = (rnd.nextInt(2) == 0) ? -1 : 1;
            arc.addElement(new ElectricArc.Point(((ElectricArc.Point) (arc.elementAt(i - 1))).x + dirX * amplitude + signX * rnd.nextInt(
                    amplitude),
                                     ((ElectricArc.Point) (arc.elementAt(i - 1))).y + dirY * amplitude + signY * rnd.nextInt(
                    amplitude)));
        }
        return arc;
    }
}
