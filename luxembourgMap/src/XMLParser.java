import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser
{
    private ArrayList<node> nodesList=new ArrayList<>();
    private ArrayList<arc> edgesList=new ArrayList<>();


    public ArrayList<node> getNodesList() {
        return nodesList;
    }

    public ArrayList<arc> getEdgesList() {
        return edgesList;
    }

    public XMLParser(){
        try {
            File inputFile = new File("map2.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);

            this.nodesList=userhandler.getNodesList();
            this.edgesList=userhandler.getEdgesList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class UserHandler extends DefaultHandler {
    private ArrayList<node> nodesList=new ArrayList<>();
    private ArrayList<arc> edgesList=new ArrayList<>();

    public ArrayList<arc> getEdgesList() {
        return edgesList;
    }

    public ArrayList<node> getNodesList() {
        return nodesList;
    }

    @Override
    public void startElement(
            String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("node")) {

            String id = attributes.getValue("id");
            String longitude = attributes.getValue("longitude");
            String latitude = attributes.getValue("latitude");

            node n = new node(Integer.parseInt(id), Integer.parseInt(latitude), Integer.parseInt(longitude));
            nodesList.add(n);
        } else if (qName.equalsIgnoreCase("arc")) {


            String from = attributes.getValue("from");
            String to = attributes.getValue("to");
            String length = attributes.getValue("length");
      //      node n=new node(Integer.parseInt(from));


         arc a = new arc(Integer.parseInt(from), Integer.parseInt(to), Integer.parseInt(length));
         edgesList.add(a);
        }

    }
}
