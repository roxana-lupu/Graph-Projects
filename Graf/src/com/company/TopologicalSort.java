package com.company;

import java.util.*;

public class TopologicalSort {
    private Vector<Node> listaNoduri;
    private Vector<Arc> listaArce;
    public TopologicalSort(Vector<Node> listaNoduri,  Vector<Arc> listaArce){
       this.listaNoduri=listaNoduri;
       this.listaArce=listaArce;
   }
   private boolean hasCycle() {
       Vector<Integer> listaNoduriInt=new Vector<>();
       for (Node n : listaNoduri) {
           listaNoduriInt.add(n.getNumber());
       }
       Stack<Integer> nodeStack=new Stack<>();

       HashMap<Integer, Integer> KeysValues = new HashMap<>();
       for(int i=0; i<listaNoduriInt.size(); i++){
           KeysValues.put(listaNoduriInt.get(i), -1);
       }
       //-1-nevizitat
       //0-vizitat si in stiva
       //1-vizitat si scos din stiva
       nodeStack.push(listaNoduriInt.elementAt(0));
       KeysValues.put(listaNoduriInt.elementAt(0), 0);
       while(!nodeStack.empty()){
           Integer current=nodeStack.peek();


                  boolean noNext=true;


                      for (Arc arc : listaArce) {
                          if (arc.getNodeStart() == current && KeysValues.get(arc.getNodeEnd())==-1) {
                              nodeStack.push(arc.getNodeEnd());
                              KeysValues.put(arc.getNodeEnd(), 0);
                              noNext = false;
                          } else if(arc.getNodeStart() == current && KeysValues.get(arc.getNodeEnd())==0){
                               return true;
                          }
                      }

                  if(noNext){
                      KeysValues.put(current, 1);
                      nodeStack.pop();
                  }


       }
       return false;

   }
   public void TopSort(){
        if(hasCycle()) {
            System.out.println("Contine ciclu, introduceti un graf fara cicluri");
        }else {
            Set<Node> visited = new HashSet<>();
            Stack<Node> nodeStack = new Stack<>();
            Stack<Node> output = new Stack<>();

            for (Node node : listaNoduri) {
                if (!visited.contains(node)) {
                    visited.add(node);
                    nodeStack.push(node);

                    while (!nodeStack.empty()) {
                        Node current = nodeStack.peek();
                        nodeStack.pop();

                        while (hasNonVisitedChild(current, visited)) {
                            nodeStack.push(current);
                            current = nextNonVisitedChild(current, visited);
                            visited.add(current);

                        }

                        output.push(current);
                    }
                }
            }


            while (!output.empty()) {
                System.out.println(output.peek().getNumber() + 1);
                output.pop();
            }
        }


   }

   private boolean hasNonVisitedChild(Node node, Set<Node> visited){
        Vector<Node> children=getChildren(node);
        for(Node child: children){
            if(!visited.contains(child)){
                return true;
            }
        }
        return false;
   }
    private Node nextNonVisitedChild(Node node, Set<Node> visited){
        Vector<Node> children=getChildren(node);
        for(Node child : children){
            if(!visited.contains(child)){
                return child;
            }
        }
        return null;
    }


    private Vector<Node>  getChildren(Node startNode){
        Vector<Node> children=new Vector<>();
        for(Arc arc : listaArce){
            if(arc.getNodeStart()==startNode.getNumber()){
                children.add(identifyNodeByNumber(arc.getNodeEnd()));
            }
        }
        return children;
    }
    
    private Node identifyNodeByNumber(int nodeNumber){
        for(Node n: listaNoduri){
            if(n.getNumber()==nodeNumber){
                return n;
            }
        }
        return null;
    }

}
