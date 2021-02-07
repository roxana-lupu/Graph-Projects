package com.company;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;


public class MyPanel extends JPanel {

    JButton OrientedButton = new JButton("Oriented Graph");
    JButton UnorientedButton = new JButton("Unoriented Graph");
    JButton TopologicalSort = new JButton("Topological Sort");
    JButton ConnectedComponents = new JButton("Connected Components");
    JButton StronglyConnectedComponents= new JButton(" Strongly Connected Components");
    private static final long serialVersionUID = 1L;
    private int nodeNr = 0;
    private int node_diam = 30;
    private Vector<Node> listaNoduri;
    private Vector<Arc> listaArce;
    private int[][] matrix = new int[nodeNr][];
    Point pointStart = null;
    Point pointEnd = null;
    boolean isDragging = false;
    CreateFile m_file = new CreateFile();
    boolean oriented = false;
    boolean unoriented = false;
    boolean conected=false;
    boolean strongly=false;
    private Vector<Vector<Integer>>components;
    private Vector<Vector<Integer>> stronglyComponents;
    public MyPanel() {

        listaNoduri = new Vector<Node>();
        listaArce = new Vector<Arc>();

        OrientedButton.setBackground(Color.WHITE);
        OrientedButton.setFocusable(false);
        UnorientedButton.setBackground(Color.WHITE);
        UnorientedButton.setFocusable(false);
        TopologicalSort.setBackground(Color.WHITE);
        TopologicalSort.setFocusable(false);
        TopologicalSort.setEnabled(false);
        ConnectedComponents.setBackground(Color.WHITE);
        ConnectedComponents.setFocusable(false);
        ConnectedComponents.setEnabled(false);
        StronglyConnectedComponents.setBackground(Color.WHITE);
        StronglyConnectedComponents.setFocusable(false);
        StronglyConnectedComponents.setEnabled(false);

        StronglyConnectedComponents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             strongly=true;
             StronglyConnectedComponents comp=new StronglyConnectedComponents(listaNoduri, listaArce);
             stronglyComponents=new Vector<>();
             stronglyComponents=comp.getComponents();
             repaint();
            }
        });
        TopologicalSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TopologicalSort sortingAlg = new TopologicalSort(listaNoduri, listaArce);
                sortingAlg.TopSort();
            }
        });
        ConnectedComponents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conected=true;
                ConnectedComponents comp = new ConnectedComponents(listaNoduri, listaArce);
                components=new Vector<>();
                components=comp.getElements();
                repaint();
            }
        });


        OrientedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StronglyConnectedComponents.setEnabled(true);
                ConnectedComponents.setEnabled(false);
                TopologicalSort.setEnabled(true);
                OrientedButton.setBackground(new java.awt.Color(162,185,185));
                UnorientedButton.setBackground(Color.WHITE);
                oriented = true;
                unoriented = false;
                listaNoduri.clear();
                listaArce.clear();
                nodeNr = 0;
                matrix = new int[nodeNr][];
                m_file.OpenFile();
                m_file.AddMatrix(matrix);
                m_file.CloseFile();
                repaint();
            }
        });
        UnorientedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StronglyConnectedComponents.setEnabled(false);
                ConnectedComponents.setEnabled(true);
                TopologicalSort.setEnabled(false);
                UnorientedButton.setBackground(new java.awt.Color(162,185,185));
                OrientedButton.setBackground(Color.WHITE);
                oriented = false;
                unoriented = true;
                listaNoduri.clear();
                listaArce.clear();
                nodeNr = 0;
                matrix = new int[nodeNr][];
                m_file.OpenFile();
                m_file.AddMatrix(matrix);
                m_file.CloseFile();
                repaint();
            }
        });


        // borderul panel-ului
        setBorder(BorderFactory.createLineBorder(Color.black));

        add(OrientedButton);
        add(UnorientedButton);
        add(TopologicalSort);
        add(ConnectedComponents);
        add(StronglyConnectedComponents);

            addMouseListener(new MouseAdapter() {
                //evenimentul care se produce la apasarea mousse-ului
                public void mousePressed(MouseEvent e) {
                    pointStart = e.getPoint();
                }

                //evenimentul care se produce la eliberarea mousse-ului
                public void mouseReleased(MouseEvent e) {
                    if (!isDragging) {
                        addNode(e.getX(), e.getY());
                    } else {


                        if (canIDrawArc(pointStart) && canIDrawArc(pointEnd)) {
                            Point nodeStartPoint=identifyNodePoint(pointStart,listaNoduri);
                            Point nodeEndPoint=identifyNodePoint(pointEnd,listaNoduri);
                            nodeStartPoint.x+=15;
                            nodeStartPoint.y+=15;
                            nodeEndPoint.x+=15;
                            nodeEndPoint.y+=15;
                            int i = identifyNodeNumber(pointStart, listaNoduri);
                            int j = identifyNodeNumber(pointEnd, listaNoduri);
                            if (unoriented) {
                                if (matrix[i][j] != 1 && matrix[j][i] != 1) {

                                    Arc arc = new Arc(nodeStartPoint, nodeEndPoint, i, j);
                                    listaArce.add(arc);
                                    matrix[i][j] = 1;
                                    matrix[j][i] = 1;
                                    m_file.OpenFile();
                                    m_file.AddMatrix(matrix);
                                    m_file.CloseFile();
                                }
                            } else if (oriented) {

                                if (matrix[i][j] != 1) {
                                        Arc arc = new Arc(nodeStartPoint, nodeEndPoint, i, j);
                                        listaArce.add(arc);
                                        matrix[i][j] = 1;
                                        m_file.OpenFile();
                                        m_file.AddMatrix(matrix);
                                        m_file.CloseFile();
                                  }
                                repaint();
                            }


                        }
                    }
                    pointStart = null;
                    isDragging = false;
                    repaint();

                }

            });


            addMouseMotionListener(new MouseMotionAdapter() {
                //evenimentul care se produce la drag&drop pe mousse
                public void mouseDragged(MouseEvent e) {
                    pointEnd = e.getPoint();
                    isDragging = true;
                    repaint();
                }
            });


        }

        //metoda care se apeleaza la eliberarea mouse-ului
        private void addNode ( int x, int y){
        if(oriented || unoriented){
            Node newNode = new Node(x, y, nodeNr);
            if (canIDrawNode(newNode)) {
                listaNoduri.add(newNode);
                nodeNr++;
                //actualizareMatrice
                m_file.OpenFile();
                matrix = AddNodesToMatrix(matrix, nodeNr);
                m_file.AddMatrix(matrix);
                m_file.CloseFile();
                repaint();
            }

            }

        }

        private int[][] AddNodesToMatrix ( int[][] matrix, int nodeNr){
            int[][] extendedMatrix = new int[nodeNr][];
            for (int i = 0; i < extendedMatrix.length; i++) {
                extendedMatrix[i] = new int[nodeNr];
                for (int j = 0; j < extendedMatrix.length; j++) {
                    extendedMatrix[i][j] = 0;
                }
            }

            for (int i = 0; i < matrix.length; i++) {

                for (int j = 0; j < matrix.length; j++) {
                    extendedMatrix[i][j] = matrix[i][j];
                }
            }
            return extendedMatrix;


        }


        private boolean canIDrawNode (Node newNode){
            for (Node node : listaNoduri) {
                if (node.checkCollision(newNode.getCoordX(), newNode.getCoordY())) {
                    return false;
                }
            }
            return true;
        }

        private boolean canIDrawArc (Point point){
            if (listaNoduri.size() == 0) {
                return true;
            }
            for (Node node : listaNoduri) {
                if (node.checkCollision(point.x, point.y)) {
                    return true;
                }
            }
            return false;
        }

    public Point identifyNodePoint (Point point, Vector < Node > list){
        for (Node node : list) {
            if (node.checkCollision(point.x, point.y)) {
                return new Point(node.getCoordX(),node.getCoordY());
            }
        }
        return null;
    }
        public int identifyNodeNumber (Point point, Vector < Node > list){
            for (Node node : list) {
                if (node.checkCollision(point.x, point.y)) {
                    return node.getNumber();
                }
            }
            return -1;
        }


        protected void paintComponent (Graphics g){
            super.paintComponent(g);

            if (unoriented) {
                for (Arc a : listaArce) {

                    a.drawArc(g);
                }

                if (pointStart != null) {
                    g.setColor(Color.BLACK);
                    g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
                }
            } else if (oriented) {
                for (Arc a : listaArce) {
                    a.drawArrowLine(g);
                }

                if (pointStart != null) {
                    g.setColor(Color.BLACK);
                    g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
                }

            }

            if(conected &&components!=null && components.size()!=0){
                for (int i=0;i<components.size();i++){
                    Color color = new Color((int)(Math.random() * 0x1000000));
                    for (int j = 0; j< components.get(i).size(); j++){

                        listaNoduri.elementAt(components.get(i).get(j)).drawNode(g, node_diam,color);

                    }
                }
                conected=false;

            } else if(strongly && stronglyComponents!=null && stronglyComponents.size()!=0){
                for (int i=0;i<stronglyComponents.size();i++){
                    Color color = new Color((int)(Math.random() * 0x1000000));
                    for (int j = 0; j< stronglyComponents.get(i).size(); j++){
                        listaNoduri.elementAt(stronglyComponents.get(i).get(j)).drawNode(g, node_diam, color);
                    }
                }
                strongly=false;
            }
            else {
                for (int i = 0; i < listaNoduri.size(); i++) {
                listaNoduri.elementAt(i).drawNode(g, node_diam);
            }
        }

}



}
