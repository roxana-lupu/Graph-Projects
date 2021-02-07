import java.util.Scanner;
import java.util.Vector;

public class FileObj {
    private Scanner read;

    public void openFile(){
        try{
            read= new Scanner(new java.io.File("matrix.txt"));
        }catch (Exception e){
            System.out.println("Could not find file.");
        }
    }
    public void readFile(Vector<Vector<Integer>> adjMatrix){
        while(read.hasNext()) {

            Integer size = read.nextInt();
            for (int i = 0; i < size; ++i) {
                Vector<Integer> node = new Vector<>();
                for (int j = 0; j < size; ++j) {
                    node.add(read.nextInt());
                }
                adjMatrix.add(node);
            }
        }
    }
    public void closeFile(){
        read.close();
    }
}
