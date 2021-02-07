import com.sun.nio.sctp.SctpStandardSocketOptions;

import java.util.*;



public class Prim {



    private Vector<Vector<Integer>> adjMatrix = new Vector<>();


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
    int SelectMin(Vector<Integer> value, Vector<Boolean> visited) {
        int min = Integer.MAX_VALUE;
        int nod = 0;
        for (int i = 0; i < adjMatrix.size(); ++i) {
            if (visited.elementAt(i).equals(false) &&
                    value.elementAt(i) < min) {
                nod = i;
                min = value.elementAt(i);
            }
        }
        return nod;
    }

    public Prim(Vector<Vector<Integer>> adjMatrix) {
        this.adjMatrix = adjMatrix;
    }

    public void Algorithm() {

       if(isConnected()) {
           Vector<Integer> listaParinti = new Vector<>();
           listaParinti.setSize(adjMatrix.size());

           Vector<Boolean> visited = new Vector<>();
           visited.setSize(adjMatrix.size());
           Collections.fill(visited, false);

           Vector<Integer> value = new Vector<>();
           value.setSize(adjMatrix.size());
           Collections.fill(value, Integer.MAX_VALUE);


           listaParinti.set(0, -1);
           value.set(0, 0);
           int picked;

           for (int i = 0; i < adjMatrix.size()-1; ++i) {

               picked = SelectMin(value, visited);
               visited.set(picked, true);

               
               for (int j = 0; j < adjMatrix.size(); ++j) {
                   if (adjMatrix.elementAt(picked).elementAt(j) != 0 &&
                           visited.elementAt(j).equals(false)
                           && adjMatrix.elementAt(picked).elementAt(j) < value.elementAt(j)) {
                       value.set(j, adjMatrix.elementAt(picked).elementAt(j));
                       listaParinti.set(j, picked);
                   }
               }

           }

           for(int i=1;i<adjMatrix.size();++i) {
               System.out.println("[" + listaParinti.elementAt(i) + ", " +i+ "]" + " cu costul de: " + adjMatrix.elementAt(listaParinti.elementAt(i)).elementAt(i));
           }
           System.out.println("Cu costul de: " +value.stream()
                   .mapToInt(Integer::valueOf)
                   .sum());
       }
       else{
           System.out.println("This graph hasn't all connected components");
       }

    }
    }


/*
                6
                        0 4 6 0 0 0
                        4 0 6 3 4 0
                        6 6 0 1 8 0
                        0 3 1 0 2 3
                        0 4 8 2 0 7
                        0 0 0 3 7 0*/
