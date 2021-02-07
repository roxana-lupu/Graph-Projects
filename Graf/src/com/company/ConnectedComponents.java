package com.company;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class ConnectedComponents {
    private Vector<Integer> listaNoduriInt=new Vector<>();
    private Vector<Arc> listaArce;
    private Vector<Vector<Integer>> components=new Vector<>();

    private Vector<Boolean> visited=new Vector<Boolean>();

    public ConnectedComponents(Vector<Node> listaNoduri,  Vector<Arc> listaArce){
        if(listaArce.size()>0) {
            for (Node n : listaNoduri) {
                listaNoduriInt.add(n.getNumber());
            }

            this.listaArce = listaArce;
        }
        else{
            System.out.println("Nu are componente conexe!");
        }
    }
    Vector<Vector<Integer>> getElements() {

        for (int i = 0; i < listaNoduriInt.size(); i++) {
            visited.add(false);
        }
        for (int i = 0; i < listaNoduriInt.size(); ++i) {
            if (!visited.get(listaNoduriInt.elementAt(i))) {

                Vector<Integer> component = new Vector<>();
                component = DFS(listaNoduriInt.elementAt(i));
                components.add(component);


            }
        }
    return components;

    }

    Vector<Integer> DFS(Integer node) {

        Vector<Integer> componenta=new Vector<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(node);

        while(!stack.empty())
        {
            Integer current = stack.peek();
            stack.pop();
            if(!visited.get(current))
            {
                componenta.add(current);
                visited.set(current, true);
            }
            for(Arc arc: listaArce) {
                if(arc.getNodeStart()==current) {

                    if(!visited.get(arc.getNodeEnd())){
                        stack.push(arc.getNodeEnd());
                   }
                }else if(arc.getNodeEnd()==current){
                    if(!visited.get(arc.getNodeStart())){
                        stack.push(arc.getNodeStart());
                    }
                }
            }
        }
        return componenta;
    }

}
