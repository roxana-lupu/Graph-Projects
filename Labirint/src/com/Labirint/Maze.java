package com.Labirint;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Maze extends JFrame {
    private int[][] maze = readMatrixFromFile();
    private ArrayList<Position> exitPoints = new ArrayList<Position>();
    private int index=-1;
    private Position entryPoint = new Position();
    JButton next = new JButton("Next");
    JButton back = new JButton("Back");



    public Maze() {
        maze=borderMatrix();
        setTitle("Labirint");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(back);
        add(next);


        next.setFocusable(false);
        back.setFocusable(false);
        back.setEnabled(false);
        setLayout(new FlowLayout());

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(index>0) {
                    next.setEnabled(true);
                    exitPoints.clear();
                    index--;
                    repaint();

                }else if(index==0){
                    back.setEnabled(false);
                    
                }


            }

        });

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(index<exitPoints.size()-1){
                    back.setEnabled(true);
                    exitPoints.clear();
                    index++;
                    repaint();

                }
                else{
                    next.setEnabled(false);
                    exitPoints.clear();

                }

            }

        });






    }




    @Override
    public void paint(Graphics g) {
            super.paint(g);
            g.translate(70, 70);
        for(int row=0; row<maze.length; ++row){
            for(int column=0; column<maze[row].length; ++column){
                  Color color;
                  switch(maze[row][column]){
                      case 0:  color=Color.BLACK; break;
                      case 2: color=Color.BLUE;
                      entryPoint.SetValues(row, column);
                      break;
                      case 3: color= Color.red;
                      Position p=new Position(row, column);
                      exitPoints.add(p);
                      break;


                      default: color= Color.WHITE; break;
                  }


                  g.setColor(color);
                  g.fillRect(40*column, 40*row, 40, 40);
                  g.setColor(Color.BLACK);
                  g.drawRect(40*column, 40*row, 40, 40);
            }
        }
        if(index!=-1) {
            BFS search = new BFS(maze, entryPoint, exitPoints.get(index));
            LinkedList<Position> path = search.findPath();

            for (Position index : path) {

                if (index != path.getFirst()) {

                    g.setColor(Color.GREEN);
                    g.fillRect(40 * index.getColumn(), 40 * index.getRow(), 40, 40);
                    g.setColor(Color.BLACK);
                    g.drawRect(40 * index.getColumn(), 40 * index.getRow(), 40, 40);
                    if (index == path.getLast()) {
                        g.setColor(Color.RED);
                        g.fillOval(40 * index.getColumn(), 40 * index.getRow(), 40, 40);
                    }


                }

            }
        }






    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {

                    Maze maze = new Maze();
                    maze.setVisible(true);










            }
        });




    }
    public int[][] borderMatrix(){
        int[][] bordered = new int[maze.length+2][maze[0].length+2];
        for(int row=0; row<maze.length; ++row) {
            for (int column = 0; column < maze[row].length; ++column) {
                bordered[row+1][column+1]=maze[row][column];
            }
        }
        return bordered;

    }


    public static int[][] readMatrixFromFile() {
        try {
            Scanner read = new Scanner(new File("C:\\Users\\Roxana\\IdeaProjects\\Labirint\\src\\com\\Labirint\\Matrix.txt"));
            int numberRows = read.nextInt();
            int numberColumns = read.nextInt();
            int[][] matrix = new int[numberRows][numberColumns];

            for (int i = 0; i < numberRows; ++i) {
                for (int j = 0; j < numberColumns; ++j) {
                    matrix[i][j] = read.nextInt();

                }
            }
            read.close();
            return matrix;


        } catch (Exception ex) {
            return null;
        }
    }



}


