import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class myPanel extends JPanel {

    private ArrayList<node> nodesList;
    private ArrayList<arc> edgesList;
    private ArrayList<arc> path;
    private Map<Integer, AdjElement> adjList;

    node minLat;
    node maxLat;
    node maxLong;
    node minLong;
    JButton DijkstraBtn=new JButton("Dijkstra");
    boolean dijkstra=false;
    JButton BellmanFordBtn=new JButton("BellmanFord");
    boolean bellmanFord=false;
    int count=0;
    node pointStart = new node();
    node pointEnd = new node();
    public myPanel() {
        BellmanFordBtn.setFocusable(false);
        DijkstraBtn.setFocusable(false);
        XMLParser read = new XMLParser();
        adjList = new HashMap<>();
        nodesList = new ArrayList<>();
        nodesList = read.getNodesList();
        edgesList = new ArrayList<>();
        edgesList = read.getEdgesList();
        path = new ArrayList<>();
        minLat = Collections.min(nodesList, Comparator.comparing(node::getLatitude));
        maxLat = Collections.max(nodesList, Comparator.comparing(node::getLatitude));
        minLong = Collections.min(nodesList, Comparator.comparing(node::getLongitude));
        maxLong = Collections.max(nodesList, Comparator.comparing(node::getLongitude));
        initAdj();



        BellmanFordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                path = null;
                repaint();
                bellmanFord = true;
                count = 0;
                pointStart = new node();
                pointEnd = new node();
                dijkstra = false;

            }
        });
        DijkstraBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                path=null;
                repaint();
                dijkstra = true;
                count = 0;
                pointStart = new node();
                pointEnd = new node();
                bellmanFord = false;
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (dijkstra || bellmanFord) {
                    count++;
                    if (count < 2) {

                        setLat(pointStart, me.getX());
                        setLong(pointStart, me.getY());
                        pointStart = identifyNode(pointStart);
                        repaint();
                    } else if (count == 2) {
                        setLat(pointEnd, me.getX());
                        setLong(pointEnd, me.getY());
                        pointEnd = identifyNode(pointEnd);
                        repaint();
                        initAdj();
                        if (dijkstra) {
                            path = restorePath(DijkstraAlg(pointStart.getId(), pointEnd.getId()));
                            repaint();
                        } else if (bellmanFord) {
                            path = restorePath(BellmanFordAlg(pointStart.getId(), pointEnd.getId()));
                            repaint();
                        }
                    }
                } else {
                    System.out.println("Punctele au fost alese!");
                }
            }
        });
    }

    ArrayList<arc> restorePath(ArrayList<Integer> arr){
        int index=pointEnd.getId();
        ArrayList<arc> path=new ArrayList<>();
        do{
            int aux=arr.get(index);
            arc a=new arc(aux, index);
            path.add(a);
            index=aux;
        }while(index!= pointStart.getId());

        return path;
    }
    void initAdj(){
        for(node n:nodesList){
            adjList.put(n.getId(), new AdjElement());
        }
        for(arc a: edgesList){
            adjList.get(a.getFrom()).addElem(a);
            adjList.replace(a.getFrom(), adjList.get(a.getFrom()));
        }


    }
    node identifyNode(node nod){
        ArrayList<node> nearest=new ArrayList<>();
        node dumb=new node();
        int min=Integer.MAX_VALUE;
        for(node n:nodesList){
            if(nod.getLongitude()==n.getLongitude() && nod.getLatitude()==n.getLatitude()){
                return n;
            }
            if(n.checkCollision(nod.getLatitude(), nod.getLongitude())!=-1.00f){

                if((float)min>n.checkCollision(nod.getLatitude(), nod.getLongitude()))
                {
                    nearest.add(n);
                    min=(int)(n.checkCollision(nod.getLatitude(), nod.getLongitude()));
                }

            }
        }
        if(nearest.size()!=0){
            return nearest.get(nearest.size()-1);
        }
        else{
            return dumb;
        }
    }
    public ArrayList<Integer> BellmanFordAlg(int nodeStart, int nodeEnd) {
        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(nodesList.size(), Integer.MAX_VALUE));
        ArrayList<Integer> parents = new ArrayList<>(Collections.nCopies(nodesList.size(), -1));
        dist.set(nodeStart, 0);
        boolean found;
        for (int i = 0; i < nodesList.size() - 1; i++) {
            found = false;
            for(int j=0; j<edgesList.size(); ++j){
                int from = edgesList.get(j).getFrom();
               int to = edgesList.get(j).getTo();
               int length = edgesList.get(j).getLength();
                if (dist.get(from) != Integer.MAX_VALUE && dist.get(from) + length < dist.get(to)) {
                    parents.set(to, from);
                    dist.set(to, dist.get(from) + length);
                    found = true;
                }

            }

            if (!found) {
                return parents;
            }
        }
        return null;
    }

    public ArrayList<Integer> DijkstraAlg(int nodeStart, int nodeEnd) {
        ArrayList<Integer> dist = new ArrayList<>(Collections.nCopies(nodesList.size(), Integer.MAX_VALUE));
        ArrayList<Integer> parents = new ArrayList<>(Collections.nCopies(nodesList.size(), -1));
        ArrayList<Boolean> visited = new ArrayList<>(Collections.nCopies(nodesList.size(), false));
        PriorityQueue<arc> minHeap=new PriorityQueue<>(Comparator.comparingInt(arc::getLength));
        if(nodeStart==nodeEnd) return null;
        dist.set(nodeStart, 0);
        minHeap.add(new arc(nodeStart,nodeStart,0));
        while(!minHeap.isEmpty()){
            int currentNode=minHeap.peek().getTo();

            if (visited.get(currentNode).equals(false)) {

                visited.set(currentNode, true);

                if (currentNode == nodeEnd) {
                    return parents;
                }

                ArrayList<arc> neighbors = adjList.get(currentNode).getNeighbors();
                for (arc neighbor : neighbors) {

                    int distance = neighbor.getLength();
                    int neigbor = neighbor.getTo();

                    if (visited.get(neigbor).equals(false)
                            && dist.get(currentNode) + distance < dist.get(neigbor)) {

                        dist.set(neigbor, dist.get(currentNode) + distance);

                        arc a = new arc(currentNode, neigbor, dist.get(neigbor));
                        parents.set(neigbor, currentNode);
                        minHeap.add(a);

                    }
                }
            }
            minHeap.poll();


        }
        return null;
    }

    private void setLat(node n, int coord) {
        n.setLatitude((int) (coord * ((maxLat.getLatitude() - minLat.getLatitude()) / getScale()) + minLat.getLatitude()));
    }

    private void setLong(node n, int coord) {
        n.setLongitude((int) (coord * ((maxLong.getLongitude() - minLong.getLongitude()) / getScale()) + minLong.getLongitude()));
    }
    private double getScale(){
        return Math.min(this.getWidth(), this.getHeight())-50.00f;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (arc arc : edgesList) {
            arc.drawArc(g, nodesList,
                    minLat.getLatitude(),
                    maxLong.getLongitude(),
                    maxLat.getLatitude(),
                    minLong.getLongitude(), getScale(), Color.BLACK);
        }


        if(dijkstra && path!=null){
            for (arc arc : path) {
                double lat1 = nodesList.get(arc.getFrom()).getLatitude();
                lat1 = nodesList.get(arc.getFrom()).transformLat((int) lat1, minLat.getLatitude(), maxLat.getLatitude(), getScale());
                double long1 = nodesList.get(arc.getFrom()).getLongitude();
                long1 = nodesList.get(arc.getFrom()).transformLong((int) long1, minLong.getLongitude(), maxLong.getLongitude(), getScale());
                node n = new node();
                n.drawRoad(g, lat1, long1);
            }
        }else if(bellmanFord && path!=null){
            for (arc arc : path) {
                double lat1 = nodesList.get(arc.getFrom()).getLatitude();
                lat1 = nodesList.get(arc.getFrom()).transformLat((int) lat1, minLat.getLatitude(), maxLat.getLatitude(), getScale());
                double long1 = nodesList.get(arc.getFrom()).getLongitude();
                long1 = nodesList.get(arc.getFrom()).transformLong((int) long1, minLong.getLongitude(), maxLong.getLongitude(), getScale());
                node n = new node();
                n.drawRoad(g, lat1, long1);
            }
        }
        if(dijkstra||bellmanFord){
            if(!pointStart.equals(new node())) {
                pointStart.drawNode(g, pointStart.transformLat(pointStart.getLatitude(), minLat.getLatitude(), maxLat.getLatitude(), getScale()),
                        pointStart.transformLong(pointStart.getLongitude(), minLong.getLongitude(), maxLong.getLongitude(), getScale()));
            }


            if(!pointEnd.equals(new node())) {
                pointEnd.drawNode(g, pointEnd.transformLat(pointEnd.getLatitude(), minLat.getLatitude(), maxLat.getLatitude(), getScale()),
                        pointEnd.transformLong(pointEnd.getLongitude(), minLong.getLongitude(), maxLong.getLongitude(), getScale()));
            }
        }
    }
}

