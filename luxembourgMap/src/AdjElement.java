import java.util.ArrayList;

public class AdjElement {

    private ArrayList<arc> neighbors;
    public AdjElement( ArrayList<arc> neighbors){

        this.neighbors=neighbors;
    }
    public AdjElement(){
        this.neighbors=new ArrayList<>();
    }
    public void addElem(arc a){
        this.neighbors.add(a);
    }
    public ArrayList<arc> getNeighbors() {
        return neighbors;
    }


}
