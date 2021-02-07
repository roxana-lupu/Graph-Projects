package com.Labirint;

public class Position {

    private int row, column;
    private Position previous;
    public Position(){

    }
    Position(int row, int column){
        this.row=row;
        this.column=column;
    }
    Position(int row, int column, Position previous){
        this.row=row;
        this.column=column;

        this.previous=previous;
    }
    public void SetValues(int row, int column){
        this.row=row;
        this.column=column;
    }

    public boolean equal(Position p)
    {
        return this.row == p.getRow() && this.column == p.getColumn();
    }


    public Position getPrevious() {
        return previous;
    }

    public void setPrevious(Position previous) {
        this.previous = previous;
    }



    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
