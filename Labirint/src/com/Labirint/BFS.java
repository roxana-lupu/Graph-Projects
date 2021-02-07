package com.Labirint;
import java.util.*;

public class BFS {

    private int[][] maze;
    private Position entryPoint;
    private Position exitPoint;



    public BFS(int [][]maze, Position entryPoint, Position exitPoint){
        this.maze=maze;
        this.entryPoint=entryPoint;
        this.exitPoint=exitPoint;
    }
    public LinkedList<Position> findExit() {

        LinkedList<Position> path = new LinkedList<Position>();
       = new HashSet<>(); Set<Position> visited

        Queue<Position> queue=new LinkedList<>();
        queue.add(entryPoint);

        while(!queue.isEmpty()){
            Position current=queue.poll();
            if(current.equal(exitPoint)) {
                path.add(current);
                return path;
            }
            else if(!ContainsPosition(visited, current)) {
                visited.add(current);
                path.add(current);


                if (maze[current.getRow()][current.getColumn() - 1] != 0) {
                    Position p = new Position(current.getRow(), current.getColumn() - 1, current);
                    if(!ContainsPosition(visited, p)) {
                        queue.add(p);
                    }
                }
                if (maze[current.getRow() - 1][current.getColumn()] != 0) {
                    Position p = new Position(current.getRow() - 1, current.getColumn());
                    if(!ContainsPosition(visited, p)) {
                        p.setPrevious(current);
                        queue.add(p);
                    }


                }
                if (maze[current.getRow()][current.getColumn() + 1] != 0) {
                    Position p = new Position(current.getRow(), current.getColumn() + 1);
                    if(!ContainsPosition(visited, p)) {
                        p.setPrevious(current);
                        queue.add(p);
                    }
                }
                if (maze[current.getRow() + 1][current.getColumn()] != 0) {
                    Position p = new Position(current.getRow() + 1, current.getColumn());
                    if(!ContainsPosition(visited, p)) {
                        p.setPrevious(current);
                        queue.add(p);
                    }


                }
            }


        }
        return path;
    }
    public LinkedList<Position> restorePath(LinkedList<Position> path){
        if(!path.getLast().equal(exitPoint)){
            path.clear();
            path.add(entryPoint);
            return path;
        }

        LinkedList<Position> newPath=new LinkedList<>();
        newPath.push(path.getLast());
        Position current= path.getLast();
        while(current!=entryPoint){
            current=current.getPrevious();
            newPath.push(current);
        }
        return newPath;
    }
    public boolean ContainsPosition( Set<Position> visited , Position position) {
        Iterator iterator = visited.iterator();
       boolean found=false;
        while(iterator.hasNext()){
            Position index = (Position)iterator.next();
            if(index.getRow()==position.getRow() && index.getColumn()==position.getColumn()){
                found=true;
                break;
            }

        }
        return found;
    }
    LinkedList<Position> findPath(){
        LinkedList<Position> path=findExit();
        path=restorePath(path);
        return path;
    }


}

