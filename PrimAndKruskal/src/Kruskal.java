import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Kruskal {
    boolean isConnected(){

        for(int i=0; i<adjMatrix.size(); ++i){
            boolean found=false;
            for(int j=0; j<adjMatrix.size(); ++j){
                if(adjMatrix.elementAt(i).elementAt(j)!=0){
                    found=true;
                    break;
                }
            }
            if(!found){
                return false;
            }
        }
        return true;
    }

    public class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge edge1, Edge edge2) {
            if (edge1.getCost() <= edge2.getCost()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    static class Edge{
        private Integer sursa;
        private Integer destinatie;
        private Integer cost;
        public Edge(Integer sursa, Integer destinatie, Integer cost){
            this.sursa=sursa;
            this.destinatie=destinatie;
            this.cost=cost;
        }

        public int getCost() {
            return cost;
        }
    }
    private Vector<Vector<Integer>> adjMatrix=new Vector<>();
    private Vector<Edge> listaArce=new Vector<>();
    public Kruskal(Vector<Vector<Integer>> adjMatrix) {
        this.adjMatrix = adjMatrix;
        for(int i=0; i<adjMatrix.size(); ++i){
            for(int j=0; j<adjMatrix.size(); ++j){
                if(j>i){
                    if(adjMatrix.elementAt(i).elementAt(j)!=0){
                        Edge edge=new Edge(i, j, adjMatrix.elementAt(i).elementAt(j));
                        listaArce.add(edge);
                    }

                }
            }
        }
    }
    public void Algorithm(){
        if(isConnected()) {
            int numarNoduri = adjMatrix.size();

            Vector<Integer> listaParinti = new Vector<>();
            listaParinti.setSize(numarNoduri);
            for (int i = 0; i < numarNoduri; ++i) {
                listaParinti.set(i, i);
            }

            Vector<Integer> value = new Vector<>();
            value.setSize(numarNoduri);
            Collections.fill(value, 0);

            Vector<Edge> output = new Vector<>();

            EdgeComparator edgeComparator = new EdgeComparator();
            listaArce.sort(edgeComparator);

            for (Edge edge : listaArce) {
                int radacina1 = FindParent(edge.sursa, listaParinti);
                int radacina2 = FindParent(edge.destinatie, listaParinti);
                if (radacina1 != radacina2) {
                    output.add(edge);

                    if (value.elementAt(radacina1) < value.elementAt(radacina2)) {
                        listaParinti.set(radacina1, radacina2);
                        int toIncrease = value.elementAt(radacina2);
                        ++toIncrease;
                        value.set(radacina2, toIncrease);
                    } else {
                        listaParinti.set(radacina2, radacina1);
                        int toIncrease = value.elementAt(radacina1);
                        ++toIncrease;
                        value.set(radacina1, toIncrease);
                    }
                }
            }
            System.out.println("Edges: ");
            int total = 0;
            for (Edge edge : output) {
                total += edge.cost;
                System.out.println("[" + edge.sursa + " ," + edge.destinatie + "] cu costul de: " + edge.cost );
            }
            System.out.println("Total: " + total);
        }
        else{
            System.out.println("This graph hasn't all connected components");
        }

    }
    Integer FindParent(Integer node, Vector<Integer> listaParinti){
        int nodecopy=node;
        while(true){
            if(listaParinti.elementAt(nodecopy)==nodecopy){
                return nodecopy;
            }
            else{
                nodecopy=listaParinti.elementAt(nodecopy);
            }
        }
    }

}
/*
6
        0 4 1 5 0 0
        4 0 0 2 3 3
        1 0 0 2 8 0
        5 2 2 0 1 0
        0 3 8 1 0 3
        0 3 0 0 3 0
*/

