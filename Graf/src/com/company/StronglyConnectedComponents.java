package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class StronglyConnectedComponents {
    private Vector<Arc> listaArce=new Vector<>();
    private Vector<Integer> listaNoduriInt=new Vector<>();
    private Vector<Vector<Integer>> components=new Vector<>();



    public StronglyConnectedComponents(Vector<Node> listaNoduri, Vector<Arc> listaArce) {

        if (listaArce.size() > 0) {
            for (Node n : listaNoduri) {
                listaNoduriInt.add(n.getNumber());
            }

            this.listaArce = listaArce;
        } else {
            System.out.println("Nu are componente tare conexe");
        }
    }



    Vector<Vector<Integer>> getComponents(){
        Vector<Boolean> visited=new Vector<Boolean>();

        for (int i = 0; i < listaNoduriInt.size(); i++) {
            visited.add(false);
        }

        for(int i=0;i<listaNoduriInt.size();++i){
            if(!visited.get(listaNoduriInt.elementAt(i))){


                Vector<Integer> componenta=new Vector<>();
                componenta.add(listaNoduriInt.elementAt(i));
                visited.set(listaNoduriInt.elementAt(i), true);


                for(int j=i+1;j<listaNoduriInt.size();++j){
                        if(!visited.get(listaNoduriInt.elementAt(j)) &&
                                ExistChainBetween(listaNoduriInt.elementAt(i),listaNoduriInt.elementAt(j)) &&
                                ExistChainBetween(listaNoduriInt.elementAt(j),listaNoduriInt.elementAt(i))){

                            componenta.add(listaNoduriInt.elementAt(j));
                            visited.set(listaNoduriInt.elementAt(j),true);
                        }

                }
                components.add(componenta);

            }
        }
        return components;
    }

    private boolean ExistChainBetween(int n1, int n2){

        Vector<Boolean> visited=new Vector<Boolean>();
        for (int i = 0; i < listaNoduriInt.size(); i++) {
            visited.add(false);
        }
        Queue<Integer> coada=new LinkedList<Integer>();
        coada.add(n1);
        visited.set(n1, true);
        while(!coada.isEmpty()){
            int current=coada.peek();
            if(current==n2){
                return true;
            }
            for(Arc arc:listaArce){
                if(arc.getNodeStart()==current && !visited.get(arc.getNodeEnd())){
                    coada.add(arc.getNodeEnd());
                    visited.set(arc.getNodeEnd(), true);
                }
            }

            coada.remove();
        }
        return false;
    }
}