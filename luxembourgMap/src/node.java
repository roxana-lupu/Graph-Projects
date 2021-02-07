import java.awt.*;
import java.awt.geom.Line2D;

public class node {
    private int id;
    private int longitude;
    private int  latitude;
   public node(int id, int latitude, int longitude){
       this.id=id;
       this.latitude=latitude;
       this.longitude=longitude;
   }
   public node(int id){
       this.id=id;
   }
   public node(){
       this.id=0;
       this.latitude=0;
       this.longitude=0;
   }
   public node(int latitude, int longitude){
       this.latitude=latitude;
       this.longitude=longitude;
   }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getLongitude() {
        return longitude;
    }
    public double transformLat(int lat, int minLat, int maxLat, double scale){
       return (lat-minLat) /((maxLat-minLat)/ scale);
    }
    public double transformLong(int longt, int minLong, int maxLong, double scale){
        return (longt-minLong)/((maxLong -minLong)/ scale);
    }
    public void drawRoad(Graphics g, double coordX, double coordY){
        g.setColor(Color.blue);
        g.fillOval((int)coordX, (int)coordY, 3, 3);
        g.setColor(Color.blue);
        g.drawOval((int)coordX, (int)coordY, 3, 3);

    }
    public void drawNode(Graphics g, double coordX, double coordY)
    {

        g.setColor(Color.red);

        g.fillOval((int)coordX, (int)coordY, 7, 7);
        g.setColor(Color.BLACK);
        g.drawOval((int)coordX, (int)coordY, 7, 7);

    }

    public float checkCollision(int coordX, int coordY) {
        float firstSide = Math.abs(this.latitude - coordX);
        float secondSide= Math.abs(this.longitude - coordY);
        firstSide = firstSide * firstSide;
        secondSide = secondSide * secondSide;
        float distance = (float) Math.sqrt(firstSide+secondSide);
        if(distance < 1000.0f){
            return distance;
        }
        return -1.0f;
    }


}

