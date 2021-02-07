package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Arc
{
    private Point start;
    private Point end;
    private int nodeStart, nodeEnd;


    public Arc(Point start, Point end, int nodeStart, int nodeEnd)
    {
        this.start = start;
        this.end = end;
        this.nodeStart=nodeStart;
        this.nodeEnd=nodeEnd;

    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public int getNodeEnd() {
        return nodeEnd;
    }

    public int getNodeStart() {
        return nodeStart;
    }

    public void drawArc(Graphics g)
    {
        if (start != null)
        {
            g.setColor(Color.BLACK);
            g.drawLine(start.x, start.y, end.x, end.y);

        }
    }
    public void drawArrowLine(Graphics g) {
        int d=10, h=7;
        int dx = end.x - start.x,
                dy = end.y - start.y;

        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d-20,
                xn = xm,
                ym = h,
                yn = -h,
                x;
        double sin = dy / D,
                cos = dx / D;

        x = xm*cos - ym*sin + start.x;
        ym = xm*sin + ym*cos + start.y;
        xm = x;

        x = xn*cos - yn*sin + start.x;
        yn = xn*sin + yn*cos + start.y;
        xn = x;

        int[] xpoints = {end.x, (int) xm, (int) xn};
        int[] ypoints = {end.y, (int) ym, (int) yn};

        g.setColor(Color.BLACK);

        g.drawLine(start.x, start.y, end.x, end.y);
        g.fillPolygon(xpoints, ypoints, 3);


    }
}

