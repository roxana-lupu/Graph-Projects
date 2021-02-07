package com.company;


import com.sun.source.tree.OpensTree;

import java.awt.*;
import java.util.Optional;

public class Node
{
    private int coordX;
    private int coordY;
    private int number;

    public Node(int coordX, int coordY, int number)
    {
        this.coordX = coordX;
        this.coordY = coordY;
        this.number = number;
    }
    public Node(){}

    public int getCoordX() {
        return coordX;
    }
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }
    public int getCoordY() {
        return coordY;
    }
    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public boolean checkCollision(int coordX, int coordY) {
        float firstSide = Math.abs(this.coordX - coordX);
        float secondSide= Math.abs(this.coordY - coordY);
        firstSide = firstSide * firstSide;
        secondSide = secondSide * secondSide;
        float distance = (float) Math.sqrt(firstSide+secondSide);
        if(distance < 40){
            return true;
        }
        return false;
    }



    public void drawNode(Graphics g, int node_diam, Color... color)
    {
        Color nodeColor = color.length!=0?color[0]: new Color(162, 185, 185);
        g.setColor(nodeColor);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.fillOval(coordX, coordY, node_diam, node_diam);
        g.setColor(Color.BLACK);
        g.drawOval(coordX, coordY, node_diam, node_diam);
        int label=number+1;
        if(number < 10)
            g.drawString(((Integer)label).toString(), coordX+13, coordY+20);
        else
            g.drawString(((Integer)label).toString(), coordX+8, coordY+20);
    }
}
