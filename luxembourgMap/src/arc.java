import java.awt.*;
import java.awt.geom.Line2D;
import java.util.*;

public class arc {
    private int from;
    private int to;
    private int  length;



    public arc(int from, int to){
        this.from=from;
        this.to=to;

    }

    public arc(int from, int to, int length){
        this.from=from;
        this.to=to;
        this.length=length;
    }
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void drawArc(Graphics g, ArrayList<node> nodesList, int minLat,
                        int maxLong, int maxLat, int minLong, double scale, Color color)
    {
        g.setColor(color);
        double x1=(nodesList.get(from).getLatitude()-minLat)
                /((maxLat-minLat)/ scale);
        double y1=(nodesList.get(from).getLongitude()-minLong)/
                ((maxLong -minLong)/ scale);
        double x2=(nodesList.get(to).getLatitude()-minLat)/
                ((maxLat -minLat)/ scale);
        double y2=(nodesList.get(to).getLongitude()-minLong)/
                ((maxLong -minLong)/ scale);
        Shape s=new Line2D.Double(y1, x1, y2, x2);
        Graphics2D g2D=(Graphics2D) g;
        g2D.draw(s);



    }
}
