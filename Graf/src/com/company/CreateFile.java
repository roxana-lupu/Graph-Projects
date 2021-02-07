package com.company;
import java.util.Formatter;

public class CreateFile  {

    private Formatter file;

    public void OpenFile() {
        try {
            file = new Formatter("adiacentMatrix.txt");

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void AddMatrix(int [][] matrix) {
        file.format("%d", matrix.length);
        file.format("\n");
        for(int[] row: matrix)
        {
            for(int col: row )
            {
                file.format("%d", col);
                file.format("%c",' ');
            }
            file.format("\n");
        }
    }
    public void CloseFile() {
      file.close();
    }
}