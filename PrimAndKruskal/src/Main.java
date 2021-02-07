import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        FileObj file=new FileObj();
        Vector<Vector<Integer>> adjMatrix=new Vector<>();
        file.openFile();
        file.readFile(adjMatrix);
        file.closeFile();

                System.out.println("Prin algoritmul Prim:");
                Prim objPrim = new Prim(adjMatrix);
                objPrim.Algorithm();

                System.out.println("Prin algoritmul Kruskal:");
                Kruskal objKruskal = new Kruskal(adjMatrix);
                objKruskal.Algorithm();



    }
}
